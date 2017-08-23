package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class RegisterServerEntity implements Serializable {
	private Integer id;
	
	/**
	 * 名字
	 */
	private String name;
	
	/**
	 * ip
	 */
	private String ip;
	
	/**
	 * 端口
	 */
	private Integer port;
	
	/**
	 * 参数
	 */
	private String params;
	
	/**
	 * 最后注册时间
	 */
	private Date loginTime;
	
	/**
	 * 最后取消注册时间
	 */
	private Date logoutTime;
	
	/**
	 * 是否活动
	 */
	private Integer active;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	public Date getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}
	
}
