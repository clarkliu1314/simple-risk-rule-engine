package com.google.code.simplerule.core.rule;

import java.util.Map;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;

/**
 * 风险因素
 * @author drizzt
 *
 */
public interface RiskFactor extends Cloneable {
	/**
	 * 风险因素名
	 * @return
	 */
	String getName();
	
	/**
	 * 子系统名
	 * @return
	 */
	String getSystemName();
	
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
	 * 设置系统中需要设置的参数
	 * @param args
	 */
	void setInternalArguments(Object[] args) throws RiskValidationException;
	
	/**
	 * 设置系统中需要设置的参数的值
	 */
	Object[] getInternalValues();
	
	/**
	 * 结果类型
	 * @return
	 */
	FactorField getResult();
	
	/**
	 * 执行因素
	 * @param context 上下文
	 * @param externalArgs 外部参数
	 * @return
	 */
	Object execute(RuleContext context, Object[] externalArgs);
	
	/**
	 * 所支持的条件判断
	 * @return
	 */
	ConditionalOperator[] supportedOperators();
	
	/**
	 * 获取转换器
	 * @return
	 */
	RiskConvertor getRiskConvertor();
	
	/**
	 * 设置转换器
	 * @return
	 */
	void setRiskConvertor(RiskConvertor riskConvertor);

	/**
	 * 设置外部参数
	 * @param params
	 */
	void setExternalArguments(FactorField[] arguments);
}
