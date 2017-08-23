package com.google.code.simplerule.core.rule.impl;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RuleContext;

/**
 * 规则处理器
 * @author drizzt
 *
 */
@Service
public class RiskResultDefaultHandler implements RiskHandler {
	/**
	 * 执行处理器
	 * @param context
	 * @param result
	 * @return
	 */
	public RiskResult execute(RuleContext context, RiskResult result) {
		return null;
	}
	
	/**
	 * 处理器名
	 * @return
	 */
	public String getName() {
		return "BlacklistRishHandler";
	}
	
	/**
	 * 得到处理器参数
	 * @return
	 */
	public Object[] getArguments() {
		return null;
	}
	
	/**
	 * 设置处理器参数
	 * @param objects
	 */
	public void setArguments(Object...objects) {
		;
	}
	
	/**
	 * 处理器参数类型
	 * @return
	 */
	public FactorField[] getArgumentFields() {
		return null;
	}
}
