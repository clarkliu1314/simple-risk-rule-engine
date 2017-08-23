package com.google.code.simplerule.core.rule;


/**
 * 风险因素装饰器
 * @author songlei
 *
 */
public abstract class BaseRiskFactor implements RiskFactor {

	private RiskConvertor riskConvertor;

	public RiskConvertor getRiskConvertor() {
		return riskConvertor;
	}

	public void setRiskConvertor(RiskConvertor riskConvertor) {
		this.riskConvertor = riskConvertor;
	}
}
