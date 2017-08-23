package com.google.code.simplerule.core.rule;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;

/**
 * 规则日志记录器
 * @author drizzt
 *
 */
public class NoneRiskLogger implements RiskLogger {
	
	protected Logger logger = LoggerFactory.getLogger(NoneRiskLogger.class);
	
	FactorField[] noneFields = new FactorField[0];
	
	/**
	 * 日志记录所需参数
	 * @return
	 */
	public FactorField[] getArguments() {
		return noneFields;
	}
	
	/**
	 * 设置日志记录所需参数
	 * @return
	 */
	public void setArguments(FactorField[] fields) {
		this.noneFields = fields;
	}
	
	/**
	 * 写日志
	 * @param params 调用参数
	 * @param result 执行结果
	 * @param tick 执行时间
	 * @param ruleInfo 命中规则信息
	 */
	public void writeLog(String inerfaceName, Map params, RiskResult result, long tick, String ruleInfo) {
		logger.info("RiskLogger inerfaceName:" + inerfaceName + ", params:" + params + ", result:" + result + ", tick:"
				+ tick + ", ruleInfo:" + ruleInfo);
	}
}
