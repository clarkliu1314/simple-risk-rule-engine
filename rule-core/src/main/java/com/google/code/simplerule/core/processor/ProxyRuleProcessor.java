package com.google.code.simplerule.core.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.simplerule.core.help.HelpInfo;
//import com.google.code.simplerule.core.monitor.ServerStatus;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.core.rule.RuleFactory;
import com.google.code.simplerule.core.rule.RuleMonitor;

/**
 * 用于代理执行器，只能获得数据，不能执行
 * @author drizzt
 *
 */
public class ProxyRuleProcessor implements RuleProcessor {
	protected RuleFactory ruleFactory;
	
	public ProxyRuleProcessor(RuleFactory factory) {
		this.ruleFactory = factory;
		mappers = new ArrayList<RiskInterface>();
	}
	
	protected List<RiskFactor> factors = null;
	protected List<RiskHandler> handlers = null;
	protected List<RiskInterface> mappers;
	
//	@Autowired
	public void setFactors(List<RiskFactor> rs) {
		factors = rs;
	}
	
//	@Autowired
	public void setHandlers(List<RiskHandler> rhs) {
		handlers = rhs;
	}
	
	@Override
	public void initialize() {
		List<RiskInterface> list = ruleFactory.loadRiskBusiness();
		if (list == null || list.size() < 1)
			return;
		
		for (RiskInterface rb : list) {
			mappers.add(rb);
		}
	}

	@Override
	public RiskResult process(String interfaceName, Map param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HelpInfo getHelpInfo(String interfaceName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateRuleInfo(String interfaceName) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Map<String, RiskInterface> getRuleList() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public ServerStatus getServerStatus() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	@Override
	public List<RiskFactor> findFactors(String business) {
		return factors;
	}

	@Override
	public List<RiskHandler> findHandlers(String business) {
		return handlers;
	}

	@Override
	public List<RiskInterface> findInterfaces() {
		return mappers;
	}

	@Override
	public RuleMonitor getInterfaceMonitor(String interfaceName) {
		// TODO Auto-generated method stub
		return null;
	}

}
