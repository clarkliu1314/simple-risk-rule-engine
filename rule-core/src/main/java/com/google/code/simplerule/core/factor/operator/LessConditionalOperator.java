package com.google.code.simplerule.core.factor.operator;

import java.util.Date;

import com.google.code.simplerule.core.factor.ConditionalOperator;

/**
 * 小于
 * @author drizzt
 *
 */
public class LessConditionalOperator implements ConditionalOperator {

	@Override
	public boolean operate(Object... objs) {
		Object result = objs[0];
		Object setValue = objs[1];
		if (result.getClass().equals(long.class)
			|| result.getClass().equals(Long.class)) {
			return (Long.valueOf(result.toString())) < (Long.valueOf(setValue.toString()));
		}
		if (result.getClass().equals(int.class)
			|| result.getClass().equals(Integer.class)
			|| result.getClass().equals(short.class)
			|| result.getClass().equals(Short.class)
			|| result.getClass().equals(byte.class)
			|| result.getClass().equals(Byte.class)) {
			return (Integer.valueOf(result.toString())) < (Integer.valueOf(setValue.toString()));
		}
		if (result.getClass().equals(float.class)
			|| result.getClass().equals(Float.class)) {
			return (Float.valueOf(result.toString())) < (Float.valueOf(setValue.toString()));
		}
		if (result.getClass().equals(Date.class)) {
			return ((Date)result).before((Date)setValue);
		}
		throw new IllegalArgumentException("参数错误，不能进行小于比较。" + result.toString());
	}

	@Override
	public String getName() {
		return "小于";
	}

	@Override
	public String getDescription() {
		return "小于(<)";
	}

}
