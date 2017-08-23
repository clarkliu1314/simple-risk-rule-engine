package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 风控接口菜单实体包装器
 * @author 韩彦伟
 * @since 2013-5-15
 */
public class RiskMenuEntityWrapper implements Serializable{

	private static final long serialVersionUID = -2944664142358257836L;
	
	/**
	 * entityWrapper中保留的ID始终是菜单的ID
	 */
	private Integer id;
	private String name;
	private String code;
	private String interfaceName;
	private Integer parentId;
	
	private Date createTime;
	private String number;
	
	/**
	 * 接口ID
	 */
	private Integer interfaceId;
	private String description;
	private String eventDescription;
	private Integer status;
	private Integer timeoutPeriod;
	private Date updateTime;
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public Integer getInterfaceId() {
		return interfaceId;
	}
	public void setInterfaceId(Integer interfaceId) {
		this.interfaceId = interfaceId;
	}	
}
