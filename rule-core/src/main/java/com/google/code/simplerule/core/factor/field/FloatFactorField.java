package com.google.code.simplerule.core.factor.field;

import com.google.code.simplerule.core.exception.RiskValidationException;

/**
 * 浮点型
 * @author drizzt
 *
 */
public class FloatFactorField extends AbstractFactorField {
	public FloatFactorField(String name, String desc, String regex) {
		super(name, desc, regex);
	}
	
	public FloatFactorField(String name, String desc) {
		super(name, desc);
	}
	
	public FloatFactorField(String name) {
		super(name);
	}

	public FloatFactorField() {
		super();
	}
	
	@Override
	public Class getType() {
		return Float.class;
	}

	@Override
	public Object convert(Object value) throws RiskValidationException {
		checkData(value);
		return Float.valueOf(String.valueOf(value));
	}
}
