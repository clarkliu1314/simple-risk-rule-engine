package com.google.code.simplerule.core.exception;

import com.google.code.simplerule.core.processor.RiskCode;

/**
 * 规则验证异常
 * @author drizzt
 *
 */
public class RiskValidationException extends Exception {
	private static final long serialVersionUID = 2138996649786347697L;
	
	/**
	 * 构造
	 * @param code
	 */
	public RiskValidationException() {
		super();
	}
	
	/**
	 * 构造
	 * @param code
	 * @param message
	 */
	public RiskValidationException(String message) {
		super(message);
	}
}
