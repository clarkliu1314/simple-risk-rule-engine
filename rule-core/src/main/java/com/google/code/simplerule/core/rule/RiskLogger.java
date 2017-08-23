package com.google.code.simplerule.core.rule;

import java.util.Map;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;

/**
 * 规则日志记录器
 * @author drizzt
 *
 */
public interface RiskLogger {
	/**
	 * 日志记录所需参数
	 * @return
	 */
	FactorField[] getArguments();
	
	/**
	 * 设置日志记录所需参数
	 * @return
	 */
	void setArguments(FactorField[] fields);
	
	/**
	 * 写日志
	 * @param params 调用参数
	 * @param result 执行结果
	 * @param tick 执行时间
	 * @param ruleInfo 命中规则信息
	 */
	void writeLog(String inerfaceName, Map params, RiskResult result, long tick, String ruleInfo);
}
