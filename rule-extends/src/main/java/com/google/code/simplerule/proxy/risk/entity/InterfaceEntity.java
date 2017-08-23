package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class InterfaceEntity extends BaseEntity implements Serializable{
	private Long id;
	private String systemName;
	private String interfaceName;
	private String description;
	private String eventDescription;
	private Date createTime;
	private Integer status;
	private Integer timeoutPeriod;
	private Date updateTime;
	public String getSystemName() {
		return systemName;
	}
	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEventDescription() {
		return eventDescription;
	}
	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTimeoutPeriod() {
		return timeoutPeriod;
	}
	public void setTimeoutPeriod(Integer timeoutPeriod) {
		this.timeoutPeriod = timeoutPeriod;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
