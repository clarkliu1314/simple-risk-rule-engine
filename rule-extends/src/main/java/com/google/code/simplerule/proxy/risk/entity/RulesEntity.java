package com.google.code.simplerule.proxy.risk.entity;

import java.util.Date;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class RulesEntity extends BaseEntity {
	private Long id;
	private String no;
	private String name;
	private Date startTime;
	private Date endTime;
	private Integer status;
	/**
	 * 优先级，越大越先执行
	 */
	private Integer priority;
	/**
	 * 等级，1低、5中、9高
	 */
	private Integer level;
	private String createPerson;
	private Date createTime;
	private String interfaceName;
	/**
	 * 审核状态 0 待审核 ，1 审核通过，2打回修改.
	 */
	private Integer auditStatus;
	/**
	 * 操作人(erp账号)
	 */
	private String operator;
	/**
	 * 操作类型:0 add,1 delete,2 update
	 */
	private Integer operateType;
	
	/**
	 * 是否是拥有者
	 */
	private boolean isowner = false;
	
	/**
	 * 开始区间，每天的开始时间，格式如：08:30
	 */
	private String startInterval;
	
	/**
	 * 结束区间，每天的结束时间，格式如：08:30
	 */
	private String endInterval;
	
	/**
	 * 拒绝原因
	 */
	private String refuseReason;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getPriority() {
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getCreatePerson() {
		return createPerson;
	}

	public void setCreatePerson(String createPerson) {
		this.createPerson = createPerson;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getAuditStatus() {
		return auditStatus;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public Integer getOperateType() {
		return operateType;
	}

	public void setOperateType(Integer operateType) {
		this.operateType = operateType;
	}

	public boolean isIsowner() {
		return isowner;
	}

	public void setIsowner(boolean isowner) {
		this.isowner = isowner;
	}

	public String getStartInterval() {
		return startInterval;
	}

	public void setStartInterval(String startInterval) {
		this.startInterval = startInterval;
	}

	public String getEndInterval() {
		return endInterval;
	}

	public void setEndInterval(String endInterval) {
		this.endInterval = endInterval;
	}

	public String getRefuseReason() {
		return refuseReason;
	}

	public void setRefuseReason(String refuseReason) {
		this.refuseReason = refuseReason;
	}

}
