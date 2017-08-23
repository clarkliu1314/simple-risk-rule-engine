package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.FloatFactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.factor.operator.EqualConditionalOperator;
import com.google.code.simplerule.core.factor.operator.GreaterConditionalOperator;
import com.google.code.simplerule.core.factor.operator.GreaterEqualConditionalOperator;
import com.google.code.simplerule.core.factor.operator.LessConditionalOperator;
import com.google.code.simplerule.core.factor.operator.LessEqualConditionalOperator;
import com.google.code.simplerule.core.rule.BaseRiskFactor;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.proxy.risk.enums.SystemEnum;

@Service
public class SingleLimitExFactor extends BaseRiskFactor {

	@Override
	public String getName() {
		return "单笔交易限额";
	}

	protected FactorField[] externalArguments = new FactorField[]{new FloatFactorField("tradeAmount", "交易金额")};
	
	@Override
	public FactorField[] getExternalArguments() {
		return externalArguments;
	}

	protected final static FactorField fResult = new FloatFactorField("","浮点类型值", FloatFactorField.MONEY_PATTERN);
	
	@Override
	public FactorField getResult() {
		return fResult;
	}
	
	@Override
	public Object execute(RuleContext context, Object... args) {
		return Float.valueOf(args[0].toString());
	}

	protected final static ConditionalOperator[] cos = new ConditionalOperator[] {
			new GreaterConditionalOperator(),
			new GreaterEqualConditionalOperator(),
			new LessConditionalOperator(),
			new LessEqualConditionalOperator(),
			new EqualConditionalOperator()
	};
	
	@Override
	public ConditionalOperator[] supportedOperators() {
		return cos;
	}

	@Override
	public FactorField[] getInternalArguments() {
		return null;
	}

	@Override
	public void setInternalArguments(Object[] args) {
	}

	@Override
	public String getSystemName() {
		return SystemEnum.Risk;
	}
	
	@Override
	public Object[] getInternalValues() {
		return null;
	}

	@Override
	public void setExternalArguments(FactorField[] arguments) {
		externalArguments = arguments;
	}
}
