package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;
import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

/**
 * @author sunny
 * @since 2013-5-14
 * @version 1.0.0
 * @comment ManualAuditEntity.java
 */
public class ManualAuditDataEntity extends BaseEntity implements Serializable {

	/**
	 * HBase rowKey
	 */
	private String rowKey;
	
	/**
	 * 接口名
	 */
	private String interfaceName;

	/**
	 * 规则编号
	 */
	private String ruleNumber;

	/**
	 * 规则执行情况
	 */
	private String ruleTrack;

	/**
	 *审核状态 
	 */
	private String auditStatus;
	
	/**
	 * 审核状态展示数据
	 */
	private String auditStatusDisplayValue;
	
	/**
	 * 审核结果备注
	 */
	private String auditBackup;
	
	/**
	 * 操作人
	 */
	private String operator;
	
	/**
	 * 插入hbase的时间
	 */
	private String manualAuditDate;

	// 可选参数
	private Map param;
	
	//可选参数 查询结果参数
	
	/**
	 * 用户名
	 */
	private String username;
	
	/**
	 * 电话
	 */
	private String tel;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRuleNumber() {
		return ruleNumber;
	}

	public void setRuleNumber(String ruleNumber) {
		this.ruleNumber = ruleNumber;
	}

	public String getRuleTrack() {
		return ruleTrack;
	}

	public void setRuleTrack(String ruleTrack) {
		this.ruleTrack = ruleTrack;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	public String getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getAuditBackup() {
		return auditBackup;
	}

	public void setAuditBackup(String auditBackup) {
		this.auditBackup = auditBackup;
	}

	public String getRowKey() {
		return rowKey;
	}

	public void setRowKey(String rowKey) {
		this.rowKey = rowKey;
	}

	public String getManualAuditDate() {
		return manualAuditDate;
	}

	public void setManualAuditDate(String manualAuditDate) {
		this.manualAuditDate = manualAuditDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getAuditStatusDisplayValue() {
		return auditStatusDisplayValue;
	}

	public void setAuditStatusDisplayValue(String auditStatusDisplayValue) {
		this.auditStatusDisplayValue = auditStatusDisplayValue;
	}

}
