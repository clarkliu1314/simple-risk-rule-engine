package com.google.code.simplerule.core.runner;

import java.util.Map;

import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;

public interface ProcessRunner {

	RiskResult run(RiskInterface business, Map param) throws Exception;

}
