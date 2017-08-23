package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class FactorEntity extends BaseEntity implements Serializable{
  private String name;
  private String className;
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getClassName() {
	return className;
}
public void setClassName(String className) {
	this.className = className;
}

	
}
