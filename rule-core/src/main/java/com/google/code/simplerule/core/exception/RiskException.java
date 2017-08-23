package com.google.code.simplerule.core.exception;

import com.google.code.simplerule.core.processor.RiskCode;

/**
 * 规则异常
 * @author drizzt
 *
 */
public class RiskException extends Exception {
	private static final long serialVersionUID = 2138996649786347697L;
	
	protected String code;
	
	/**
	 * 构造
	 */
	public RiskException() {
		super();
		code = RiskCode.Exception;
	}
	
	/**
	 * 构造
	 * @param code
	 */
	public RiskException(String code) {
		super();
		this.code = code;
	}
	
	/**
	 * 构造
	 * @param code
	 * @param message
	 */
	public RiskException(String code, String message) {
		super(message);
		this.code = code;
	}

	/**
	 * 返回异常代码
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置异常代码
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}
}
