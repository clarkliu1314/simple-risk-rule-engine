package com.google.code.simplerule.core.rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.code.simplerule.core.annotation.Rule;
import com.google.code.simplerule.core.result.RiskResult;

/**
 * 规则上下文
 * @author drizzt
 *
 */
public class RuleContext {
	private RiskInterface riskInterface;
	private RiskRule currentRule;
	private RiskResult lastResult;
	private Map param;
	
	private boolean isHit;
	private List<String> conditionTracks;

	public RuleContext() {
		conditionTracks = new ArrayList();
	}
	
	/**
	 * 规则接口
	 * @return
	 */
	public RiskInterface getRiskInterface() {
		return riskInterface;
	}

	public void setRiskInterface(RiskInterface riskInterface) {
		this.riskInterface = riskInterface;
	}

	/**
	 * 当前规则
	 * @return
	 */
	public RiskRule getCurrentRule() {
		return currentRule;
	}

	public void setCurrentRule(RiskRule currentRule) {
		this.currentRule = currentRule;
	}

	/**
	 * 最后的处理结果
	 * @return
	 */
	public RiskResult getLastResult() {
		return lastResult;
	}

	public void setLastResult(RiskResult lastResult) {
		this.lastResult = lastResult;
	}

	/**
	 * 规则参数
	 * @return
	 */
	public Map getParam() {
		return param;
	}

	public void setParam(Map param) {
		this.param = param;
	}

	/**
	 * 接口名称
	 * @return
	 */
	public String getInterfaceName() {
		return riskInterface.getInterfaceName();
	}
	
	/**
	 * 得到规则执行情况
	 * @return
	 */
	public String getRuleTrack() {
		if (!isHit)
			return "";
		
		String str = "命中规则：" + currentRule.getNumber() + "(" + currentRule.getName() + ");";
		for (String s : conditionTracks) {
			str += s + "。";
		}
		return str;
	}

	public boolean isHit() {
		return isHit;
	}

	public void setHit(boolean isHit) {
		this.isHit = isHit;
	}

	public void addConditionTrack(String conditionTrack) {
		conditionTracks.add(conditionTrack);
	}
}
