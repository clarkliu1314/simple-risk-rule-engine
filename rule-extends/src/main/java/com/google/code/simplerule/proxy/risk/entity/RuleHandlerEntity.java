package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class RuleHandlerEntity extends BaseEntity implements Serializable {
	private Long id;
	private Long ruleId;
	private String command;
	private String description;
	private String commandValue;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getRuleId() {
		return ruleId;
	}
	public void setRuleId(Long ruleId) {
		this.ruleId = ruleId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getCommandValue() {
		return commandValue;
	}
	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
