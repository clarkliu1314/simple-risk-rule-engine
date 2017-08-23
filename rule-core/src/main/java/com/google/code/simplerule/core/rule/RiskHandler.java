package com.google.code.simplerule.core.rule;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;

/**
 * 规则处理器
 * @author drizzt
 *
 */
public interface RiskHandler {
	/**
	 * 执行处理器
	 * @param context
	 * @param result
	 * @return
	 */
	RiskResult execute(RuleContext context, RiskResult result);
	
	/**
	 * 处理器名
	 * @return
	 */
	String getName();
	
	/**
	 * 得到处理器参数
	 * @return
	 */
	Object[] getArguments();
	
	/**
	 * 设置处理器参数
	 * @param objects
	 */
	void setArguments(Object...objects);
	
	/**
	 * 处理器参数类型
	 * @return
	 */
	FactorField[] getArgumentFields();
}
