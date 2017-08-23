package com.google.code.simplerule.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Bean实用工具
 * @author 韩彦伟
 * @since 2013-5-15
 */
public class CommonBeanUtils {

	public static Object getAttributeValue(Object bean , String attr){
		try{
			String getMethodName = "get"+attr.substring(0, 1).toUpperCase()+attr.substring(1);
			Method getMethod = bean.getClass().getMethod(getMethodName);
			
			return getMethod.invoke(bean);
		}catch(Exception ex){
			throw new RuntimeException(ex.getMessage());
		}		
	}
	
	public static void setAttributeValue(Object bean , String attr, Object value){
		try{
			String setMethodName = "set"+attr.substring(0, 1).toUpperCase()+attr.substring(1);
			Method setMethod = bean.getClass().getMethod(setMethodName, value.getClass());
			
			setMethod.invoke(bean, value);
		}catch(Exception ex){
			throw new RuntimeException(ex.getMessage());
		}		
	}
	
	public static void copyProperties(Object destObj, Object orignObj){
		Field[] defineFields = orignObj.getClass().getDeclaredFields();
		for(Field defineField : defineFields){
			try{
				String attrName = defineField.getName();
				Object value = getAttributeValue(orignObj, attrName);
				setAttributeValue(destObj, attrName, value);
			}catch(Exception ex){
				continue;
			}
		}
	}
}
