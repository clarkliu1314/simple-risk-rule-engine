package com.google.code.simplerule.core.rule;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;

/**
 * 数据收集器
 * @author drizzt
 *
 */
public interface RuleCollector {
	/**
	 * 外部需要传入的参数
	 * @return
	 */
	FactorField[] getExternalArguments();
	
	/**
	 * 系统中需要设置的参数
	 * @return
	 */
	FactorField[] getInternalArguments();
	
	/**
	 * 得到收集器key
	 * @return
	 */
	String getCollectorKey();
	
	/**
	 * 收集
	 * @param interfaceName
	 * @param externalArgs
	 * @param result
	 */
	void collect(String interfaceName, Object[] externalArgs, RiskResult result);
}
