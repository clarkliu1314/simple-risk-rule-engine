package com.google.code.simplerule.core.help;

/**
 * 字段信息
 * @author drizzt
 *
 */
public class FieldInfo {
	private String name;
	
	private String type;
	
	private String description;
	
	private boolean require;

	/**
	 * 构造
	 * @param name 字段名
	 * @param type 类型
	 * @param description 描述
	 * @param require 是否必须
	 */
	public FieldInfo(String name, String type, String description, boolean require) {
		this.name = name;
		this.type = type;
		this.description = description;
		this.require = require;
	}

	/**
	 * 返回字段名
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置字段名
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 返回类型
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * 设置类型
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 返回描述
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * 设置描述
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 返回是否必须
	 * @return
	 */
	public boolean isRequire() {
		return require;
	}

	/**
	 * 设置是否必须
	 * @param require
	 */
	public void setRequire(boolean require) {
		this.require = require;
	}
	
	
}
