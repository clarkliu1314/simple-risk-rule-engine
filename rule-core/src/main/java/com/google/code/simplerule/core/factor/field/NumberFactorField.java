package com.google.code.simplerule.core.factor.field;

import com.google.code.simplerule.core.exception.RiskValidationException;

/**
 * 数字型
 * @author drizzt
 *
 */
public class NumberFactorField extends AbstractFactorField {
	public NumberFactorField() {
		super();
	}
	
	public NumberFactorField(String name) {
		super(name);
	}

	public NumberFactorField(String name,String description) {
		super(name,description);
	}
	@Override
	public Class getType() {
		return Integer.class;
	}

	@Override
	public Object convert(Object value) throws RiskValidationException {
		checkData(value);
		return Long.valueOf(String.valueOf(value));
	}
}
