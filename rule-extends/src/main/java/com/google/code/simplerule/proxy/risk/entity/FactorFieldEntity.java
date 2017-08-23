package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class FactorFieldEntity implements Serializable{
	
	private String name;
	private String type;
	private String description;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

}
