package com.google.code.simplerule.core.rule.impl;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.BooleanFactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.rule.BaseRiskFactor;
import com.google.code.simplerule.core.rule.RuleContext;

/**
 * 风险因素类型字段
 * @author drizzt
 *
 */
//@Service
public class IpBlackListFactor extends BaseRiskFactor {
	private static FactorField[] externalFields = new FactorField[] {
		new StringFactorField("clientIp", "clientIp"),	
	};
	
	private static FactorField[] internalFields = null;
	private Object[] internalArguments = null;
	private Object[] internalValues = null;
	private FactorField result = null;
	
	
	public IpBlackListFactor () {
		
	}

	@Override
	public String getSystemName() {
		return "实时清结算系统";
	}

	@Override
	public FactorField[] getExternalArguments() {
		return externalFields;
	}

	@Override
	public FactorField[] getInternalArguments() {
		return internalFields;
	}
	
	@Override
	public void setInternalArguments(Object[] args)
			throws RiskValidationException {
		this.internalArguments = args;
		
	}

	@Override
	public Object[] getInternalValues() {
		return internalValues;
	}

	@Override
	public FactorField getResult() {
		return new BooleanFactorField("","布尔类型值");
	}

	@Override
	public Object execute(RuleContext context, Object[] externalArgs) {
		// TODO Auto-generated method stub
		System.err.println("IpBlackListFactor...execute externalArgs[0]:"+externalArgs[0]);
		if (externalArgs[0]!=null && !"127.0.0.1".equals(externalArgs[0])) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}

	@Override
	public ConditionalOperator[] supportedOperators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setExternalArguments(FactorField[] arguments) {
		this.externalFields = arguments;
		
	}

	@Override
	public String getName() {
		return IpBlackListFactor.class.getName();
	}
}
