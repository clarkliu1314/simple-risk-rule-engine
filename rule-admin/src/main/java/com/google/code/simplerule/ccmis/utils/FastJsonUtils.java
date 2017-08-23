/**
 *
 * Copyright (c) 2015 Brightoil Petroleum (Holdings) Limited. All rights reserved.
 *
 * This software is the confidential and proprietary information of
 * Brightoil Petroleum (Holdings) Limited. You shall not disclose such Confidential
 * Information.
 * 
 * 
 */

package com.google.code.simplerule.ccmis.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.parser.deserializer.DateDeserializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

//github.com/eishay/jvm-serializers/
//http://www.it165.net/pro/html/201404/11667.html
//http://www.imooo.com/web/javascript/229628.htm

public class FastJsonUtils {
    public static final String DEFAULT_DATE_FORMAT = JSON.DEFFAULT_DATE_FORMAT;
    public static SimpleDateFormat defaultDateFormat = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    
    private static SerializeConfig mapping = new SerializeConfig();   
     
    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer(DEFAULT_DATE_FORMAT));   
        ParserConfig.getGlobalInstance().putDeserializer(Date.class, new DateDeserializer());
    }  

    
    public static String bean2Json(Object obj) {
        return JSON.toJSONString(obj);
     }
      
     public static String bean2Json(Object obj,String dateFormat) {
         return JSON.toJSONStringWithDateFormat(obj, dateFormat, SerializerFeature.PrettyFormat);
    }
  
//     public static <T> T json2Bean(String jsonStr, Class<T> objClass) {
//         return JSON.parseObject(jsonStr, objClass);
//     }

 	public static <T> T json2Bean(String jsonStr,Class<T> objClass,String dateFormat){
 		
 		if (Date.class.getName().equals(objClass.getName())) {
// 			System.out.println("Date jsonStr:"+jsonStr);
// 			System.out.println("Date objClass:"+objClass);
// 			System.out.println("Date dateFormat:"+dateFormat);
 			return JSON.parseObject(jsonStr,objClass); 
 		}
// 		return JSON.parseObject(jsonStr,objClass);
 		return JSON.toJavaObject(JSON.parseObject(jsonStr), objClass);   
// 		return null;
// 		mapping.put(Date.class, new SimpleDateFormatSerializer(dateFormat));
 		
// 		System.out.println("JSON.parseObject("+jsonStr+")"+objClass+":"+JSON.parseObject(jsonStr));
 		
// 		return JSON.parseObject(jsonStr, objClass, JSON.DEFAULT_PARSER_FEATURE, new Feature[0]);
 			
// 		System.out.println("JSON.parseObject("+jsonStr+")"+objClass+":"+JSON.parseObject(jsonStr));
//
// 		//, mapping,JSON.DEFAULT_PARSER_FEATURE, new Feature[0]
//		return JSON.toJavaObject(JSON.parseObject(jsonStr), objClass);   
//
// 		} else {
// 			return null;
// 		}

	}

 }