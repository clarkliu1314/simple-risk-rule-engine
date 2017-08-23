package com.google.code.simplerule.core.runner;

import java.util.Map;

import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RuleContext;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventProcessor;
import com.lmax.disruptor.Sequence;

public class ProcessEvent {
	private RiskInterface inter;
	private Map param;
	private RiskResult result;
	
	public volatile boolean complete = false;
	
    public static final EventFactory<ProcessEvent> FACTORY = new EventFactory<ProcessEvent>() {
        @Override
        public ProcessEvent newInstance() {
            return new ProcessEvent();
        }
    };
    
	public RiskInterface getInterface() {
		return inter;
	}

	public void setInterface(RiskInterface inter) {
		this.inter = inter;
	}

	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	public RiskResult getResult() {
		return result;
	}

	public void setResult(RiskResult result) {
		this.result = result;
	}
}
