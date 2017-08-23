package com.google.code.simplerule.ccmis.controller.entity;

public class MapEntity {
	private String key;
	
	private String value;

	public MapEntity(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
