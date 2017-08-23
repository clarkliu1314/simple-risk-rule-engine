package com.google.code.simplerule.core.factor.field;

import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.FactorField;

/**
 * 抽象规则参数类型
 * @author drizzt
 *
 */
public abstract class AbstractFactorField implements FactorField {
	/**
	 * 邮件验证
	 */
	public static final String MAIL_PATTERN = "^[\\w]+@[\\w\\.]+$";
	/**
	 * 手机号验证
	 */
	public static final String MOBILE_PATTERN = "^[\\d\\+]{11, 20}$";
	/**
	 * 钱验证
	 */
	public static final String MONEY_PATTERN = "^[\\d\\.]+$";
	
	protected String name;
	protected String description;
	
	
	/**
	 * 用于数据检查的正则
	 */
	protected Pattern pattern;

	protected Map valueEnum = null;
	
	
	public AbstractFactorField() {
		this(null, null);
	}
	
	public AbstractFactorField(String name) {
		this(name, null);
	}
	
	public AbstractFactorField(String name, String desc) {
		this(name, desc, null);
	}
	
	public AbstractFactorField(String name, String desc, String regex) {
		this.name = name;
		this.description = desc;
		if (regex != null) {
			setValidationRegex(regex);
		}
	}

	@Override
	public abstract Class getType();

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getDescription() {
		return description;
	}
	
	@Override
	public void setDescription(String desc) {
		description = desc;
	}

	@Override
	public Object extractArgument(Map map) {
		if (map == null || map.size() < 1)
			return null;
		
		Iterator it = map.keySet().iterator();
        while (it.hasNext()) {
        	Map.Entry e = (Map.Entry)it.next();
        	if (name.equals(e.getKey().toString()))
        		return e.getValue();
        }
		return null;
	}
	
	@Override
	public void setValidationRegex(String pattern) {
		this.pattern = Pattern.compile(pattern, Pattern.CASE_INSENSITIVE);
	}
	
	/**
	 * 检查数据
	 * @param value
	 * @throws RiskValidationException
	 */
	protected void checkData(Object value) throws RiskValidationException {
		if (value == null)
			throw new RiskValidationException(description + "必须输入。");
		if (pattern == null)
			return;
		Matcher m = pattern.matcher(value.toString());
		if (m == null || !m.find())
			throw new RiskValidationException("必须填写正确的" + description + "。");
	}
	
	
	/**
	 * 设置值的枚举
	 * @param map
	 */
	public void setValueEnum(Map map) {
		valueEnum = map;
	}
	
	/**
	 * 获得值的枚举
	 * @return
	 */
	public Map getValueEnum() {
		return valueEnum;
	}
}
