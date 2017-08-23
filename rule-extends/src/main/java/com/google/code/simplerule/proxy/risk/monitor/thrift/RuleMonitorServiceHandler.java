package com.google.code.simplerule.proxy.risk.monitor.thrift;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Level;
import org.apache.thrift.TException;

import com.google.code.simplerule.core.processor.RuleProcessor;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleMonitor;
import com.google.code.simplerule.core.util.DateUtil;
import com.google.code.simplerule.log.LoggerDisposer;

/**
 * 监控服务实现类
 * @author drizzt
 *
 */
public class RuleMonitorServiceHandler implements RuleMonitorService.Iface {
	protected RuleProcessor processor = null;
	protected LoggerDisposer loggerDisposer = null;

	public RuleMonitorServiceHandler(RuleProcessor processor) {
		this.processor = processor;
		loggerDisposer = new LoggerDisposer();
	}

	@Override
	public List<String> listInterfaces() throws TException {
		Map<String, RiskInterface> map = processor.getRuleList();
		if (map == null || map.size() < 1)
			return null;
		List<String> list = new ArrayList();
		Iterator iter = map.entrySet().iterator(); 
		while (iter.hasNext()) {
		    Map.Entry entry = (Map.Entry) iter.next(); 
		    list.add(entry.getKey().toString());
		}
		return list;
	}

	@Override
	public int commitRule(String name) throws TException {
		return 0;
	}

	@Override
	public int notifyRuleChanged(String name) throws TException {
		return processor.updateRuleInfo(name);
	}

	@Override
	public String findLog(String level, String date, int maxline, String key)
			throws TException {
		return loggerDisposer.findLog(level, date, maxline, key);
	}

	@Override
	public int setLogLevel(String level) throws TException {
		Level l = getLevel(level);
		if (l == null)
			return 1;
		org.apache.log4j.Logger.getRootLogger().setLevel(l);
		return 0;
	}

	private Level getLevel(String level) {
		if (level.equals("DEBUG"))
			return Level.DEBUG;
		if (level.equals("INFO"))
			return Level.INFO;
		if (level.equals("WARN"))
			return Level.WARN;
		if (level.equals("ERROR"))
			return Level.ERROR;
		if (level.equals("FATAL"))
			return Level.FATAL;
		return null;
	}

	@Override
	public ServerStatus getStatus() throws TException {
		ServerStatus ss = new ServerStatus();//.getServerStatus();
		ServerStatus s = new ServerStatus();
		s.setFailure(ss.getFailure());
		s.setFailureIsSet(true);
		s.setHealth(ss.getHealth());
		s.setHealthIsSet(true);
//		s.setStart(DateUtil.dateFormat(ss.getStart(), "yyyy-MM-dd HH:mm"));
		s.setStartIsSet(true);
		s.setSuccess(ss.getSuccess());
		s.setSuccessIsSet(true);
		return s;
	}

	@Override
	public InterfaceStatus getInterfaceStatus(String name) throws TException {
		RuleMonitor rm = processor.getInterfaceMonitor(name);
		if (rm == null)
			return null;
		
		InterfaceStatus is = new InterfaceStatus();
		is.setAverageTime(rm.getAverageTime());
		is.setAverageTimeIsSet(true);
		is.setFailure(rm.getFailure());
		is.setFailureIsSet(true);
		is.setHealth(rm.getHealth());
		is.setHealthIsSet(true);
		is.setLastVisit(DateUtil.dateFormat(rm.getLastVisit(), "yyyy-MM-dd HH:mm"));
		is.setLastVisitIsSet(true);
		is.setMaxTime(rm.getMaxTime());
		is.setMaxTimeIsSet(true);
		is.setSuccess(rm.getSuccess());
		is.setSuccessIsSet(true);
		return is;
	}

	@Override
	public String echo(String str) throws TException {
		return str;
	}
	
}
