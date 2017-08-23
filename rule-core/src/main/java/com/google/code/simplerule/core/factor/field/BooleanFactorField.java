package com.google.code.simplerule.core.factor.field;

import java.util.HashMap;
import java.util.Map;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.FactorField;

/**
 * bool型参数类型
 * @author drizzt
 *
 */
public class BooleanFactorField extends AbstractFactorField {
	public BooleanFactorField() {
		super();
		
		setValueEnum(booleanMap);
	}

	public BooleanFactorField(String name) {
		super(name);
		setValueEnum(booleanMap);
	}

	public BooleanFactorField(String name,String description) {
		super(name,description);
		setValueEnum(booleanMap);
	}

	@Override
	public Class getType() {
		return Boolean.class;
	}

	@Override
	public Object convert(Object value) throws RiskValidationException {
		checkData(value);
		return Boolean.valueOf(String.valueOf(value));
	}

	/**
	 * 构造一个
	 */
	private static Map booleanMap = null;
	static {
		booleanMap = new HashMap(2);
		booleanMap.put("真(TRUE)", true);
		booleanMap.put("假(FALSE)", false);
	}
}
