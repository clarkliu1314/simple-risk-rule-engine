package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.BooleanFactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.factor.operator.EqualConditionalOperator;
import com.google.code.simplerule.core.factor.operator.NotEqualConditionalOperator;
import com.google.code.simplerule.core.rule.BaseRiskFactor;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.proxy.risk.enums.SystemEnum;
import com.google.code.simplerule.proxy.risk.service.BlackListService;

/**
 * 黑名单，最终版
 * @author drizzt
 *
 */
@Service
public class BlackListExFactor extends BaseRiskFactor {
	@Autowired
	protected BlackListService blackListService;
	
	protected String blackListType;
	
	@Override
	public String getName() {
		return "黑名单";
	}

	@Override
	public String getSystemName() {
		return SystemEnum.Risk;
	}

	protected FactorField[] externalArguments = new FactorField[]{new StringFactorField("blackListField", "黑名单字段")};
	protected final FactorField[] internalArguments = new FactorField[]{new StringFactorField("blackListType", "黑名单分类")};
	
	@Override
	public FactorField[] getExternalArguments() {
		return externalArguments;
	}

	@Override
	public FactorField[] getInternalArguments() {
		return internalArguments;
	}

	@Override
	public void setInternalArguments(Object[] args)
			throws RiskValidationException {
		blackListType = args[0].toString();
	}

	@Override
	public Object[] getInternalValues() {
		return new Object[]{blackListType};
	}

	@Override
	public FactorField getResult() {
		return new BooleanFactorField("","布尔类型值");
	}

	@Override
	public Object execute(RuleContext context, Object[] externalArgs) {
		return blackListService.inList(context.getInterfaceName(), blackListType, externalArgs[0].toString());
	}

	protected final ConditionalOperator[] cos = new ConditionalOperator[] {
			new EqualConditionalOperator(),
			new NotEqualConditionalOperator()
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
