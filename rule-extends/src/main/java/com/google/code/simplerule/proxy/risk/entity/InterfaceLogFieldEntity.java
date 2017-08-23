package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class InterfaceLogFieldEntity extends BaseEntity implements Serializable {
	private Long id;
	private String interfaceName;
	private Long fieldId;
	
	private String fieldName;
	private String fieldDescription;
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
	public Long getFieldId() {
		return fieldId;
	}
	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFieldDescription() {
		return fieldDescription;
	}
	public void setFieldDescription(String fieldDescription) {
		this.fieldDescription = fieldDescription;
	}
}
