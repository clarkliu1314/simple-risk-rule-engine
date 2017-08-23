package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Map;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class RuleConditionEntity extends BaseEntity implements Serializable {

    private Long id;
    private Long ruleId;
    private String riskFactor;
    private String riskConvertor;
    private String riskFactorDescription;
    private String checkCondition;
    private String checkConditionDescription;
    private String checkValue;
    private int logicalOperator;
    private String connector;
    private String riskFactorParam;
    private boolean isContainParam = false;
    private String externalParam;
    private Map<String,Object> resultEnumValue;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getRuleId() {
        return ruleId;
    }
    public void setRuleId(Long ruleId) {
        this.ruleId = ruleId;
    }
    public String getRiskFactor() {
        return riskFactor;
    }
    public void setRiskFactor(String riskFactor) {
        this.riskFactor = riskFactor;
    }
    public String getCheckCondition() {
        return checkCondition;
    }
    public void setCheckCondition(String checkCondition) {
        this.checkCondition = checkCondition;
    }
    public String getCheckValue() {
        return checkValue;
    }
    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }
    public int getLogicalOperator() {
        return logicalOperator;
    }
    public void setLogicalOperator(int logicalOperator) {
        this.logicalOperator = logicalOperator;
    }
    public String getRiskFactorDescription() {
        return riskFactorDescription;
    }
    public void setRiskFactorDescription(String riskFactorDescription) {
        this.riskFactorDescription = riskFactorDescription;
    }
    public String getCheckConditionDescription() {
        return checkConditionDescription;
    }
    public void setCheckConditionDescription(String checkConditionDescription) {
        this.checkConditionDescription = checkConditionDescription;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }
	public String getRiskFactorParam() {
		return riskFactorParam;
	}
	public void setRiskFactorParam(String riskFactorParam) {
		this.riskFactorParam = riskFactorParam;
	}
	public boolean isContainParam() {
		return isContainParam;
	}
	public void setContainParam(boolean isContainParam) {
		this.isContainParam = isContainParam;
	}
	public String getRiskConvertor() {
		return riskConvertor;
	}
	public void setRiskConvertor(String riskConvertor) {
		this.riskConvertor = riskConvertor;
	}
	public Map<String,Object> getResultEnumValue() {
		return resultEnumValue;
	}
	public void setResultEnumValue(Map<String,Object> resultEnumValue) {
		this.resultEnumValue = resultEnumValue;
	}
	public String getExternalParam() {
		return externalParam;
	}
	public void setExternalParam(String externalParam) {
		this.externalParam = externalParam;
	}

}