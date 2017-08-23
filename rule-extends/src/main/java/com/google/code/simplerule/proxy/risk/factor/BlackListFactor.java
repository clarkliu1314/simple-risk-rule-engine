package com.google.code.simplerule.proxy.risk.factor;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.BooleanFactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.factor.operator.EqualConditionalOperator;
import com.google.code.simplerule.core.factor.operator.NotEqualConditionalOperator;
import com.google.code.simplerule.core.rule.BaseRiskFactor;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.proxy.risk.service.BlackListService;

public abstract class BlackListFactor extends BaseRiskFactor {
	@Autowired
	protected BlackListService blackListService;
	
	@Override
	public String getSystemName() {
		return "风控";
	}
	
	@Override
	public abstract String getName();

	protected final FactorField[] fas = new FactorField[]{new StringFactorField(getBlackListName(), getBlackListDesc())};
	
	@Override
	public FactorField[] getExternalArguments() {
		return fas;
	}

	@Override
	public FactorField[] getInternalArguments() {
		return null;
	}
	
	@Override
	public void setInternalArguments(Object[] args) {
	}

	@Override
	public FactorField getResult() {
		return new BooleanFactorField("","布尔类型值");
	}
	
	protected abstract String getBlackListType();
	
	protected abstract String getBlackListName();
	
	protected abstract String getBlackListDesc();

	@Override
	public Object execute(RuleContext context, Object... args) {
		return blackListService.inList(context.getInterfaceName(), getBlackListType(), args[0].toString());
	}

	public final ConditionalOperator[] cos = new ConditionalOperator[] {
			new EqualConditionalOperator(),
			new NotEqualConditionalOperator()
	};
	
	@Override
	public ConditionalOperator[] supportedOperators() {
		return cos;
	}
	
	@Override
	public Object[] getInternalValues() {
		return null;
	}
	
	@Override
	public void setExternalArguments(FactorField[] arguments) {
		// TODO Auto-generated method stub
		
	}
}
