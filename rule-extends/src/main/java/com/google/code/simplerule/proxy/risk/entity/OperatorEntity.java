package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class OperatorEntity extends BaseEntity implements Serializable{
    private String name;
	private String className;
	private String description;
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
