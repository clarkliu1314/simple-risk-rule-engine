package com.google.code.simplerule.proxy.risk.monitor;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

import com.google.code.simplerule.core.util.NetUtils;
import com.google.code.simplerule.proxy.risk.monitor.watcher.Watcher;

public class MonitorServer {
	protected Watcher watcher = null;
	protected String name;
	protected String ip;
	protected int port;
	
	public MonitorServer(Watcher w, String name, int port) {
		watcher = w;
		
		ip = getIP();
		this.name = ip + ":" + name;
		this.port = port;
	}
	
	public void register() {
		Map map = new HashMap();
		map.put("ip", ip);
		map.put("port", port);
		
		watcher.register(name, map);
	}
	
	public void addParameters(Map m) {
		watcher.addParameters(name, m);
	}
	
	public void unregister() {
		watcher.unregister(name);
	}

	protected String getIP() {
		String ip = "localhost";
//		InetAddress addr = NetUtils.getLocalAddress();
//		if (addr != null) {
//			ip = addr.getHostAddress();
//		}
//		else {
//			ip = "unknow_host";
//		}
		return ip;
	}
}
