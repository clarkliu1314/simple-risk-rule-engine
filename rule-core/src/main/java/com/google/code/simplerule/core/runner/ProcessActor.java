package com.google.code.simplerule.core.runner;

import java.util.Map;

import org.agilewiki.jactor.lpc.JLPCActor;

import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;

public class ProcessActor extends JLPCActor {
	private RiskInterface inter;
	private Map param;
	private RiskResult result;
	
	public ProcessActor(RiskInterface inter, Map param) {
		this.inter = inter;
		this.param = param;
	}

	public void start() throws Exception {
		result = inter.processRule(param);
	}

	public Object getResult() {
		return result;
	}
}
