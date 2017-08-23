package com.google.code.simplerule.proxy.risk.monitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.simplerule.common.DateUtil;
import com.google.code.simplerule.core.rule.RuleMonitor;
import com.google.code.simplerule.proxy.risk.monitor.thrift.InterfaceStatus;
import com.google.code.simplerule.proxy.risk.monitor.thrift.RuleMonitorService;
import com.google.code.simplerule.proxy.risk.monitor.watcher.Watcher;

public class WatcherRiskMonitor implements RiskMonitoring {
	private final Logger logger = LoggerFactory.getLogger(WatcherRiskMonitor.class);
	private Watcher watcher;
	
	public WatcherRiskMonitor(Watcher watcher) {
		this.watcher = watcher;
	}

	@Override
	public List<ServerInfo> listServers() {
		
		String[] servers = null;
		try {
			servers = watcher.findWatchers();
		} catch (Exception e) {
			logger.error("ListServers error.", e);
			return null;
		}
		if (servers == null || servers.length < 1)
			return null;
		
		List<ServerInfo> mis = new ArrayList<ServerInfo>();
		for (String s : servers) {
			try {
				Map params = watcher.getParameters(s);
				if (params == null || params.size() < 1)
					continue;
				
				ServerInfo si = new ServerInfo();
				si.setIp(String.valueOf(params.get("ip")));
				si.setPort(Integer.valueOf(String.valueOf(params.get("port"))));
				si.setName(s);
				
				if (!testServer(si, 3)) {
					watcher.unregister(si.getName());
				}
				mis.add(si);
			} catch (Exception e) {
				logger.error("get param error." + s, e);
				continue;
			}
		}
		
		return mis;
	}
	
	private boolean testServer(ServerInfo info, int max) {
		TTransport transport = null;
		for (int i=0; i<max; i++) {
			try {
				transport = new TSocket(info.getIp(), info.getPort());
				transport.open();
				TProtocol protocol = new TBinaryProtocol(transport);
					
				RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
				mc.echo("1");
				return true;
			}
			catch (Exception e) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e1) {
				}
				continue;
			}
			finally {
				transport.close();
			}
		}
		return false;
	}

	@Override
	public List<String> listInterfaces(ServerInfo info) {
		TTransport transport = null;
		try {
			transport = new TSocket(info.getIp(), info.getPort());
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			
			RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
			return mc.listInterfaces();
		}
		catch (Exception e) {
			logger.error("listInterfaces error." + info.getIp() + ":" + info.getPort(), e);
			return null;
		}
		finally {
			transport.close();
		}
	}

	@Override
	public ServerStatus getStatus(ServerInfo s) {
		TTransport transport = null;
		try {
			transport = new TSocket(s.getIp(), s.getPort());
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			
			RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
			return convertToServerStatus(mc.getStatus());
		}
		catch (Exception e) {
			logger.error("getStatus error." + s.getIp() + ":" + s.getPort(), e);
			return null;
		}
		finally {
			transport.close();
		}
	}

	private ServerStatus convertToServerStatus(
			com.google.code.simplerule.proxy.risk.monitor.thrift.ServerStatus ss) {
		ServerStatus s = new ServerStatus();
		s.setFailure(ss.getFailure());
		s.setHealth(ss.getHealth());
//		s.setStart(DateUtil.getDateString(ss.getStart(), "yyyy-MM-dd HH:mm"));
		s.setSuccess(ss.getSuccess());
		return s;
	}

	@Override
	public RuleMonitor getInterfaceStatus(ServerInfo s, String name) {
		TTransport transport = null;
		try {
			transport = new TSocket(s.getIp(), s.getPort());
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			
			RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
			return convertToInterfaceStatus(mc.getInterfaceStatus(name));
		}
		catch (Exception e) {
			logger.error("getInterfaceStatus error." + s.getIp() + ":" + s.getPort(), e);
			return null;
		}
		finally {
			transport.close();
		}
	}

	private RuleMonitor convertToInterfaceStatus(InterfaceStatus i) {
		RuleMonitor is = new RuleMonitor();
		is.setAverageTime(i.getAverageTime());
		is.setFailure(i.getFailure());
		is.setHealth(i.getHealth());
//		is.setLastVisit(DateUtil.getDateString(i.getLastVisit(), "yyyy-MM-dd HH:mm"));
		is.setMaxTime(i.getMaxTime());
		is.setSuccess(i.getSuccess());
		return is;
	}

	@Override
	public int runCommand(String cmd, Object... param) {
		if (cmd.equals("commitFile")) {
			if (param.length != 2)
				return -2;
			ServerInfo mi = (ServerInfo)param[0];
			return commitFile(mi.getIp(), mi.getPort(), param[1].toString());
		}
		
		if (cmd.equals("notifyChanged")) {
			if (param.length != 1)
				return -2;
			return notifyChanged(param[0].toString());
		}
		
		return -1;
	}

	private int notifyChanged(String name) {
		List<ServerInfo> servers = listServers();
		if (servers == null || servers.size() < 1)
			return 0;
		for (ServerInfo s : servers) {
			try {
				int ret = notifyServer(s, name);
				if (ret != 0) {
					logger.warn("notifyServer " + s.getName() + " error.code is " + ret);
				}
			}
			catch (Exception e) {
				logger.warn("notifyServer " + s.getName() + " error.", e);
			}
		}
		return 0;
	}

	private int notifyServer(ServerInfo s, String name) {
		TTransport transport = null;
		try {
			logger.warn("notify " + s.getName() + " " + name + " changed.");
			
			transport = new TSocket(s.getIp(), s.getPort());
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			
			RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
			return mc.notifyRuleChanged(name);
		}
		catch (Exception e) {
			logger.error("commitFile error." + s.getIp() + ":" + s.getPort(), e);
			return -3;
		}
		finally {
			transport.close();
		}
	}

	private int commitFile(String ip, int port, String name) {
		TTransport transport = null;
		try {
			transport = new TSocket(ip, port);
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			
			RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
			return mc.commitRule(name);
		}
		catch (Exception e) {
			logger.error("commitFile error." + ip + ":" + port, e);
			return -3;
		}
		finally {
			transport.close();
		}
	}

	@Override
	public String findLog(ServerInfo info, String level, String date,
			int maxline, String key) {
		if (info != null)
			return findOneLog(info, level, date, maxline, key);
		
		List<ServerInfo> list = listServers();
		if (list == null || list.size() < 1)
			return "ByRisk:no server.";
		StringBuilder sb = new StringBuilder();
		for (ServerInfo s : list) {
			sb.append(findOneLog(s, level, date, maxline, key));
		}
		return sb.toString();
	}

	private String findOneLog(ServerInfo s, String level, String date,
			int maxline, String key) {
		TTransport transport = null;
		try {
			transport = new TSocket(s.getIp(), s.getPort());
			transport.open();
			TProtocol protocol = new TBinaryProtocol(transport);
			
			RuleMonitorService.Client mc = new RuleMonitorService.Client(protocol);
			return s.getName() + ":" + mc.findLog(level, date, maxline, key);
		}
		catch (Exception e) {
			logger.error("find log error." + s.getIp() + ":" + s.getPort(), e);
			return s.getName() + ":connect error." + e.getMessage();
		}
		finally {
			transport.close();
		}
	}
}
