package com.google.code.simplerule.core.processor;

import java.util.List;
import java.util.Map;

import org.aopalliance.intercept.ConstructorInvocation;

import com.google.code.simplerule.core.annotation.Rule;
import com.google.code.simplerule.core.help.HelpInfo;
//import com.google.code.simplerule.core.monitor.ServerStatus;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RiskRule;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.core.rule.RuleMonitor;

/**
 * 规则规则执行接口
 * @author drizzt
 *
 */
public interface RuleProcessor {
	void initialize();
	
	/**
	 * 执行接口
	 * @param interfaceName 接口
	 * @param param 参数
	 * @return 结果
	 */
	RiskResult process(String interfaceName, Map param);

	/**
	 * 帮助信息
	 * @param interfaceName 接口
	 * @return 结果
	 */
	HelpInfo getHelpInfo(String interfaceName);

	/**
	 * 从库更新接口
	 * @param interfaceName 接口
	 * @return 状态
	 */
	int updateRuleInfo(String interfaceName);
	
	/**
	 * 得到支持的规则列表
	 * @return 规则列表
	 */
	Map<String, RiskInterface> getRuleList();

	/**
	 * 得到当前运行状态
	 * @return
	 */
//	ServerStatus getServerStatus();
	
	/**
	 * 查询某个系统下的所有规则因素
	 * @param business 子系统名
	 * @return
	 */
	List<RiskFactor> findFactors(String business);
	
	/**
	 * 查询某个系统下的所有规则处理器
	 * @param business
	 * @return
	 */
	List<RiskHandler> findHandlers(String business);
	
	/**
	 * 查询规则接口列表
	 * @return
	 */
	List<RiskInterface> findInterfaces();

	/**
	 * 得到此接口监控信息
	 * @param interfaceName
	 * @return
	 */
	RuleMonitor getInterfaceMonitor(String interfaceName);
}
