package com.google.code.simplerule.core.result;

import java.io.Serializable;

import com.google.code.simplerule.core.annotation.Field;

/**
 * 规则结果，所有规则返回结果都要继承此类
 * @author drizzt
 *
 */
public class RiskResult implements Serializable {
    
	protected String code;
	
	protected String description;
	
	protected Object extendInfo;
	
	protected String reason;
	
	protected String executeTime;
	
	/**
	 * 返回代码
	 * @return
	 */
	@Field(description="返回代码", require=true)
	public String getCode() {
		return code;
	}

	/**
	 * 设置返回代码
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 得到描述
	 * @return
	 */
	@Field(description="描述", require=false)
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
	 * 得到此信息的扩展信息
	 * @return
	 */
	@Field(description="扩展信息", require=false)
	public Object getExtendInfo() {
		return extendInfo;
	}

	/**
	 * 设置此信息的扩展信息
	 * @param extendInfo
	 */
	public void setExtendInfo(Object extendInfo) {
		this.extendInfo = extendInfo;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getExecuteTime() {
		return executeTime;
	}

	public void setExecuteTime(String executeTime) {
		this.executeTime = executeTime;
	}
}
