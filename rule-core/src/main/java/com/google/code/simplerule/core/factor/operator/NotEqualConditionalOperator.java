package com.google.code.simplerule.core.factor.operator;

import com.google.code.simplerule.core.factor.ConditionalOperator;

/**
 * 不等于 
 * @author drizzt
 *
 */
public class NotEqualConditionalOperator implements ConditionalOperator {

	@Override
	public boolean operate(Object... objs) {
		Object result = objs[0];
		Object setValue = objs[1];
		return !result.equals(setValue);
	}

	@Override
	public String getName() {
		return "不等于";
	}

	@Override
	public String getDescription() {
		return "不等于(!=)";
	}

}
