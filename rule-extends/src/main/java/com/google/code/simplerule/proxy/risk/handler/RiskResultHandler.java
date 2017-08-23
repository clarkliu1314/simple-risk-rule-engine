package com.google.code.simplerule.proxy.risk.handler;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RuleContext;

@Service
public class RiskResultHandler implements RiskHandler {
	private Object[] arguments;

	@Override
	public RiskResult execute(RuleContext context, RiskResult result) {
		RiskResult r = new RiskResult();
		r.setCode(arguments[0].toString());
		r.setDescription(arguments[1].toString());
		return r;
	}

	@Override
	public String getName() {
		return "返回结果";
	}

	@Override
	public void setArguments(Object... objects) {
		arguments = objects;
	}

	protected final FactorField[] fas = new FactorField[]{
		new StringFactorField("code"),
		new StringFactorField("description")
	};
	
	@Override
	public FactorField[] getArgumentFields() {
		return fas;
	}

	@Override
	public Object[] getArguments() {
		return arguments;
	}

}
