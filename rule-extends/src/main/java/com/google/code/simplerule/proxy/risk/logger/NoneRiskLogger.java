package com.google.code.simplerule.proxy.risk.logger;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskLogger;
import com.google.code.simplerule.core.rule.RuleContext;

@Service
public class NoneRiskLogger implements RiskLogger {

	@Override
	public FactorField[] getArguments() {
		return null;
	}

	@Override
	public void setArguments(FactorField[] fields) {
	}

	@Override
	public void writeLog(String inerfaceName, Map params, RiskResult result,
			long tick, String ruleInfo) {
	}

}
