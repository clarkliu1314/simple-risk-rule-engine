package com.google.code.simplerule.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

public class ConvertUtil{
	
	private static final String SET = "set";

	public static <T> T convertMap2Bean(Map map, T t) {
		
		Class clazz = t.getClass();
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			String methodName = method.getName();
			if (methodName.startsWith(SET)) {
				String propertyName = methodName.substring(3,4).toLowerCase() + methodName.substring(4);
				Object value = map.get(propertyName);
				try {
					if (value != null)
						method.invoke(t, value.toString());
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
		return t;
	}
}
