package com.google.code.simplerule.ccmis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.google.code.simplerule.ccmis.controller.entity.ServerEntity;
import com.google.code.simplerule.core.rule.RuleMonitor;
import com.google.code.simplerule.proxy.risk.monitor.RiskMonitoring;
import com.google.code.simplerule.proxy.risk.monitor.ServerInfo;
import com.google.code.simplerule.proxy.risk.monitor.ServerStatus;

@Controller
@RequestMapping("/risk/server")
public class ServerController {
	private final Logger logger = LoggerFactory.getLogger(ServerController.class);
	@Autowired
	private RiskMonitoring monitor;

	@RequestMapping("/status")
	@ResponseBody
	public String list(String type, String host, String name, HttpServletRequest request,HttpServletResponse response) {
	    List<ServerEntity> list = findServerInfo(type, host, name, request);
	    if (list == null)
	    	return "{success:'false',info:'没有要显示的列表。'}";
	    
        return JSON.toJSONString(list);
	}

	private List<ServerEntity> findServerInfo(String type, String host,
			String name, HttpServletRequest request) {
		List<ServerInfo> list = monitor.listServers();
		List<ServerInfo> old = (List)request.getSession().getAttribute("servers");
		list = merge(list, old);
		request.getSession().setAttribute("servers", list);
		
		if (list == null || list.size() < 1)
			return null;
		
		List<ServerEntity> status = null;
		if (type == null || type.equals("") || type.equals("detail")) {
			status = findInterfaceDetail(list, host, name);
		}
		else {
			status = findServerStatus(list);
		}
		return status;
	}

	private List<ServerEntity> findServerStatus(List<ServerInfo> list) {
		List<ServerEntity> ss = new ArrayList();
		for (ServerInfo s : list) {
			ServerEntity se = new ServerEntity();
			se.setAverage(0);
			se.setMax(0);
			se.setHost(s.getIp());
			se.setName("");
			try {
				ServerStatus sstatus = monitor.getStatus(s);
				se.setFailure(sstatus.getFailure());
				se.setHealth(sstatus.getHealth());
				se.setSuccess(sstatus.getSuccess());
				se.setStatus(getStatus(se.getHealth()));
			}
			catch (Exception e) {
				logger.error("get server status error." + s.getName(), e);
				se.setHealth(0);
				se.setStatus(getStatus(se.getHealth()));
			}
			
			ss.add(se);
		}
		return ss;
	}

	private String getStatus(int health) {
		if (health < 1)
			return "Error";
		if (health < 7)
			return "Bad";
		if (health < 9)
			return "General";
		return "Good";
	}

	private List<ServerEntity> findInterfaceDetail(List<ServerInfo> list,
			String host, String name) {
		List<ServerEntity> ss = new ArrayList();
		for (ServerInfo s : list) {
			if (host != null && !host.equals("") && !host.equals(s.getIp()))
				continue;
			
			List<String> interfaces = monitor.listInterfaces(s);
			if (interfaces == null || interfaces.size() < 1)
				continue;
			for (String i : interfaces) {
				if (name != null && !name.equals("") && !name.equals(i))
					continue;
				
				ServerEntity se = new ServerEntity();
				se.setHost(s.getIp());
				se.setName(i);
				try {
					RuleMonitor m = monitor.getInterfaceStatus(s, i);
					se.setAverage(m.getAverageTime());
					se.setFailure(m.getFailure());
					se.setHealth(m.getHealth());
					se.setMax(m.getMaxTime());
					se.setSuccess(m.getSuccess());
					se.setStatus(getStatus(se.getHealth()));
				}
				catch (Exception e) {
					logger.error("get server status error." + s.getName(), e);
					se.setHealth(0);
					se.setStatus(getStatus(se.getHealth()));
				}
				
				ss.add(se);
			}
		}
		return ss;
	}

	private List<ServerInfo> merge(List<ServerInfo> list, List<ServerInfo> old) {
		if (old == null)
			return list;
		if (list == null || list.size() < 1)
			return old;
		
		List<ServerInfo> ss = new ArrayList();
		for (ServerInfo s : list) {
			ss.add(s);
		}
		for (ServerInfo s : old) {
			boolean found = false;
			for (ServerInfo s2 : ss) {
				if (s.getName().equals(s2.getName())) {
					found = true;
					break;
				}
			}
			if (!found) {
				ss.add(s);
			}
		}
		return ss;
	}
}
