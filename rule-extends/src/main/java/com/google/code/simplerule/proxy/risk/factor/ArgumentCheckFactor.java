package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.BooleanFactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.factor.operator.ContainConditionalOperator;
import com.google.code.simplerule.core.factor.operator.EqualConditionalOperator;
import com.google.code.simplerule.core.factor.operator.NotContainConditionalOperator;
import com.google.code.simplerule.core.factor.operator.NotEqualConditionalOperator;
import com.google.code.simplerule.core.rule.BaseRiskFactor;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.proxy.risk.enums.SystemEnum;

/**
 * 参数判断
 * @author drizzt
 *
 */
@Service
public class ArgumentCheckFactor extends BaseRiskFactor {

	@Override
	public String getName() {
		return "参数判断";
	}

	@Override
	public String getSystemName() {
		return SystemEnum.Risk;
	}

	protected FactorField[] externalArguments = new FactorField[]{new StringFactorField("argument", "参数名")};
	
	@Override
	public FactorField[] getExternalArguments() {
		return externalArguments;
	}

	@Override
	public FactorField[] getInternalArguments() {
		return null;
	}

	@Override
	public void setInternalArguments(Object[] args)
			throws RiskValidationException {
	}

	@Override
	public Object[] getInternalValues() {
		return null;
	}

	@Override
	public FactorField getResult() {
		return new StringFactorField("","任意类型");
	}

	@Override
	public Object execute(RuleContext context, Object[] externalArgs) {
		return externalArgs[0];
	}

	protected final ConditionalOperator[] cos = new ConditionalOperator[] {
			new EqualConditionalOperator(),
			new NotEqualConditionalOperator(),
			new ContainConditionalOperator(),
			new NotContainConditionalOperator()
	};
	
	@Override
	public ConditionalOperator[] supportedOperators() {
		return cos;
	}

	@Override
	public void setExternalArguments(FactorField[] arguments) {
		externalArguments = arguments;
	}

}
