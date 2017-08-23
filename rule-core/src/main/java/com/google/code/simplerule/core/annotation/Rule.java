package com.google.code.simplerule.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规则描述
 * @author drizzt
 *
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Rule {
	/**
	 * 规则接口名称
	 * @return
	 */
	String value();
	/**
	 * 版本
	 * @return
	 */
	String version() default("1.0");
	/**
	 * 参数类型
	 * @return
	 */
	Class param();
	/**
	 * 返回类型
	 * @return
	 */
	Class result();
}
