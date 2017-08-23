package com.google.code.simplerule.core.factor.operator;

import java.util.List;

import com.google.code.simplerule.core.factor.ConditionalOperator;

/**
 * 等于操作符
 * @author drizzt
 *
 */
public class EqualConditionalOperator implements ConditionalOperator {

	@Override
	public boolean operate(Object... objs) {
		Object result = objs[0];
		Object setValue = objs[1];
		return result.equals(setValue);
	}

	@Override
	public String getName() {
		return "等于";
	}

	@Override
	public String getDescription() {
		return "等于(==)";
	}

}
