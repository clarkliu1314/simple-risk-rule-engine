package com.google.code.simplerule.core.factor.field;

import java.util.Iterator;
import java.util.Map;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.FactorField;

/**
 * 字符串型
 * @author drizzt
 *
 */
public class StringFactorField extends AbstractFactorField {
	public StringFactorField(String name, String desc) {
		super(name, desc);
	}
	
	public StringFactorField(String name) {
		super(name);
	}
	
	public StringFactorField() {
		super();
	}

	@Override
	public Class getType() {
		return String.class;
	}
	
	@Override
	public Object convert(Object value) throws RiskValidationException {
		checkData(value);
		return String.valueOf(value);
	}
}
