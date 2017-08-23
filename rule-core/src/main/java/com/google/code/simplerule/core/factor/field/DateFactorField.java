package com.google.code.simplerule.core.factor.field;

import java.util.Date;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.util.DateUtil;

/**
 * 日期型
 * @author drizzt
 *
 */
public class DateFactorField extends AbstractFactorField {
	public DateFactorField() {
		super();
	}

	public DateFactorField(String name) {
		super(name);
	}
	

	@Override
	public Class getType() {
		return Date.class;
	}

	@Override
	public Object convert(Object value) throws RiskValidationException {
		checkData(value);
		return DateUtil.getDateString(value.toString(), "yyyy-MM-dd HH-mm");
	}
}
