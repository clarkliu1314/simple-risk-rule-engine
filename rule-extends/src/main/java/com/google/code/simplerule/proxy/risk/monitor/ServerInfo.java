package com.google.code.simplerule.proxy.risk.monitor;

/**
 * 监控机器信息
 * @author drizzt
 *
 */
public class ServerInfo {
	private String name;
	
	private String ip;
	
	private int port;

	/**
	 * 名称
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置名称
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 得到ip
	 * @return
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置ip
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 得到端口
	 * @return
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置端口
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}
	
}
