package com.google.code.simplerule.core.runner;

import java.util.Map;

import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;

/**
 * 默认规则执行类
 * @author drizzt
 *
 */
public class DefaultRunner implements ProcessRunner {
	@Override
	public RiskResult run(RiskInterface inter, Map param) throws Exception {
		return inter.processRule(param);
	}
}
