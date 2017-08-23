package com.google.code.simplerule.core.factor;

import java.util.Map;

import com.google.code.simplerule.core.exception.RiskValidationException;

/**
 * 风险因素类型字段
 * @author drizzt
 *
 */
public interface FactorField {
	/**
	 * 类型
	 * @return
	 */
	Class getType();
	
	/**
	 * 名称
	 * @return
	 */
	String getName();
	
	/**
	 * 设置名称
	 * @param name
	 */
	void setName(String name);
	
	/**
	 * 描述
	 * @return
	 */
	String getDescription();
	
	/**
	 * 设置描述
	 * @param desc
	 */
	void setDescription(String desc);
	
	/**
	 * 从参数中提取
	 * @param map
	 * @return
	 */
	Object extractArgument(Map map);

	/**
	 * 转化为此类型
	 * @param value
	 * @return
	 */
	Object convert(Object value) throws RiskValidationException;
	
	/**
	 * 设置数据检查的正则表达式
	 * @param pattern
	 */
	void setValidationRegex(String pattern);
	
	/**
	 * 设置值的枚举
	 * @param map
	 */
	void setValueEnum(Map map);
	
	/**
	 * 获得值的枚举
	 * @return
	 */
	Map getValueEnum();
}
