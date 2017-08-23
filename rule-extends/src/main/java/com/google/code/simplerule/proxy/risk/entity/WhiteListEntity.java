package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class WhiteListEntity extends BaseEntity implements Serializable{

	private Long id;
	private String interfaceName;
	private String interfaceNameDescription;
	private String type;
	private String typeDescrption;
	private String value;
	private Integer status;
	private String statusDescription;
	private String reason;
	private String operator;
	private Date createTime;
	private String keyVersion;
	private Date updateTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getInterfaceName() {
		return interfaceName;
	}
	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getStatusDescription() {
		return statusDescription;
	}
	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getTypeDescrption() {
		return typeDescrption;
	}
	public void setTypeDescrption(String typeDescrption) {
		this.typeDescrption = typeDescrption;
	}
	public String getKeyVersion() {
		return keyVersion;
	}
	public void setKeyVersion(String keyVersion) {
		this.keyVersion = keyVersion;
	}
	public String getInterfaceNameDescription() {
		return interfaceNameDescription;
	}
	public void setInterfaceNameDescription(String interfaceNameDescription) {
		this.interfaceNameDescription = interfaceNameDescription;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	

}
