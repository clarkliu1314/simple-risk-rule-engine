package com.google.code.simplerule.core.rule.condition;

import java.util.Map;

import com.google.code.simplerule.core.exception.RiskException;
import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.processor.RiskCode;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RuleCondition;
import com.google.code.simplerule.core.rule.RuleContext;

/**
 * 风险因素条件
 * @author drizzt
 *
 */
public class FactorCondition implements RuleCondition {
	private RiskFactor factor;
	private ConditionalOperator operator;
	private Object value;
    private String connector;
	
	public FactorCondition(RiskFactor factor, ConditionalOperator operator, Object value,String connector) throws RiskValidationException {
		this.factor = factor;
		this.operator = operator;
		this.value = factor.getResult().convert(value);
        this.connector = connector;
	}
	
	public RiskFactor getFactor() {
		return factor;
	}
	
	public ConditionalOperator getConditionalOperator() {
		return operator;
	}
	
	public Object getValue() {
		return value;
	}
	
	@Override
	public boolean execute(RuleContext context, Map map) throws RiskException {
		Object obj = null;
		Object[] objs = null;
		try {
			objs = getArgumentObjects(factor, map);
			obj = factor.execute(context, objs);
		} catch (RiskException e) {
			throw e;
		} catch (RiskValidationException ve) {
			throw new RiskException(RiskCode.ValidateError, ve.getMessage());
		}
		boolean ret = operator.operate(obj, value);
		if (ret) {
			context.addConditionTrack(getConditionTrack(factor, operator, objs, value));
		}
		return ret;
	}

    private String getConditionTrack(RiskFactor f, ConditionalOperator opt, Object[] objs, Object v) {
		String str = "命中条件:" + f.getName() + ",";
		if (objs != null && objs.length > 0) {
			int size = objs.length;
			for (int i=0; i<size; i++) {
				Object o = objs[i];
				str += f.getExternalArguments()[i].getDescription() + "=" + o.toString() + ",";
			}
			str += ";";
		}
		str += opt.getName() + v.toString();
		return str;
	}

	private Object[] getArgumentObjects(RiskFactor f, Map map) throws RiskException, RiskValidationException {
		FactorField[] fs = f.getExternalArguments();
		if (fs == null)
			return null;
		
		int count = fs.length;
		Object[] objs = new Object[count];
		for (int i=0; i<count; i++) {
			FactorField ff = fs[i];
			if (!map.containsKey(ff.getName()))
				throw new RiskException(RiskCode.ValidateError, ff.getName() + " not found.");
			objs[i] = ff.convert(map.get(ff.getName()));
		}
		return objs;
	}

    public ConditionalOperator getOperator() {
        return operator;
    }

    public void setOperator(ConditionalOperator operator) {
        this.operator = operator;
    }

    public String getConnector() {
        return connector;
    }

    public void setConnector(String connector) {
        this.connector = connector;
    }
}
