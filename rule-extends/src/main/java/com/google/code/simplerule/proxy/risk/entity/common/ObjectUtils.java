package com.google.code.simplerule.proxy.risk.entity.common;

import java.lang.reflect.Field;
import java.util.HashMap;

public class ObjectUtils {
	public static HashMap<String, Object> objectToMap(Object obj) {
		if (obj == null) {
			throw new RuntimeException("对象为空");
		}

		Class clazz = obj.getClass();
		HashMap map = new HashMap();
		getClass(clazz, map, obj);
		return map;
	}

	private static void getClass(Class clazz, HashMap<String, Object> map,
			Object obj) {
		if (clazz.getSimpleName().equals("Object")) {
			return;
		}

		Field[] fields = clazz.getDeclaredFields();
		if ((fields == null) || (fields.length <= 0)) {
			throw new RuntimeException("当前对象中没有任何属性值");
		}

		for (int i = 0; i < fields.length; i++) {
			fields[i].setAccessible(true);
			String name = fields[i].getName();
			Object value = null;
			try {
				value = fields[i].get(obj);
			} catch (IllegalAccessException e) {
				throw new RuntimeException();
			}
			map.put(name, value);
		}

		Class superClzz = clazz.getSuperclass();
		getClass(superClzz, map, obj);
	}

	public static Object loadClass(String name) {
		try {
			Class c = Class.forName(name);
			return c.newInstance();
		} catch (Exception e) {
			return null;
		}
	}
}