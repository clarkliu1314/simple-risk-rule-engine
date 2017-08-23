package com.google.code.simplerule.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 规则字段描述
 * @author drizzt
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Field {
	/**
	 * 描述
	 * @return 返回描述
	 */
	String description();
	/**
	 * 是否必须
	 * @return 返回是否必须
	 */
	boolean require();
}
