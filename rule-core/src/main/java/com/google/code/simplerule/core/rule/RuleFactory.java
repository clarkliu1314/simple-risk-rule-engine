package com.google.code.simplerule.core.rule;

import java.util.List;

/**
 * 规则构造工厂
 * @author drizzt
 *
 */
public interface RuleFactory {
	/**
	 * 加载所有规则接口
	 * @return
	 */
	List<RiskInterface> loadRiskBusiness();
	
	/**
	 * 通过名字加载规则接口
	 * @param interfaceName
	 * @return
	 */
	RiskInterface loadByName(String interfaceName);

	/**
	 * 加载日志记录器
	 * @param interfaceName
	 * @param logger
	 * @return
	 */
	RiskLogger loadRiskLoggerByName(String interfaceName, RiskLogger logger);
}
