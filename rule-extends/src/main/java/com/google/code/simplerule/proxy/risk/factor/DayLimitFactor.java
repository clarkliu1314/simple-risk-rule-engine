package com.google.code.simplerule.proxy.risk.factor;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.google.code.simplerule.core.processor.RiskCode;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.BaseRiskFactor;
import com.google.code.simplerule.core.rule.RuleCollector;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.proxy.risk.enums.SystemEnum;
import com.google.code.simplerule.proxy.risk.service.AmountLimitService;

@Service
public class DayLimitFactor extends BaseRiskFactor implements RuleCollector {
	@Autowired
	private AmountLimitService amountLimitService;
	
	@Override
	public String getName() {
		return "日交易限额";
	}

	protected FactorField[] externalArguments = new FactorField[]{new StringFactorField("no", "累计号"), new FloatFactorField("tradeAmount", "交易金额")};
	
	@Override
	public FactorField[] getExternalArguments() {
		return externalArguments;
	}

	protected final static FactorField fResult = new FloatFactorField("","浮点类型值", FloatFactorField.MONEY_PATTERN);
	
	@Override
	public FactorField getResult() {
		return fResult;
	}
	
	private final int timeout = 60 * 60 * 25;
	
	@Override
	public Object execute(RuleContext context, Object... args) {
		float f = Float.valueOf(args[1].toString());
		String key = context.getInterfaceName() + ",dl," + externalArguments[0].getName() + "," + externalArguments[1].getName() + "," + args[0].toString() + "," + getToday();
		long m = amountLimitService.get(key, timeout);
		return ((float)m) / 100.0 + f;
	}

	private String getToday() {
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
		return sf.format(new Date());
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

	@Override
	public String getCollectorKey() {
		return "dl," + externalArguments[0].getName() + "," + externalArguments[1].getName();
	}

	@Override
	public void collect(String interfaceName, Object[] args,
			RiskResult result) {
		if (!result.getCode().equals(RiskCode.Success))
			return;
		
		float f = Float.valueOf(args[1].toString());
		String key = interfaceName + ",dl," + externalArguments[0].getName() + "," + externalArguments[1].getName() + "," + args[0].toString() + "," + getToday();
		amountLimitService.addup(key, timeout, (long)(f * 100.0));
	}

}
