package com.google.code.simplerule.core.factor;

/**
 * 风险因素条件操作符
 * @author drizzt
 *
 */
public interface ConditionalOperator {
	/**
	 * 操作符判断
	 * @param objs
	 * @return
	 */
	boolean operate(Object...objs);
	
	/**
	 * 操作符名称
	 * @return
	 */
	String getName();
	
	/**
	 * 操作符描述
	 * @return
	 */
	String getDescription();
}
