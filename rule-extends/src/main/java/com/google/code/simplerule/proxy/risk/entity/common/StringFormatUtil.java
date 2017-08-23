package com.google.code.simplerule.proxy.risk.entity.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;

public class StringFormatUtil {
	private static final String STR_FORMAT = "0000"; 

	public static String encode(String str){
		 
		try { 
		MessageDigest md = MessageDigest.getInstance("MD5"); 
		md.update(str.getBytes()); 
		byte b[] = md.digest(); 

		int i; 

		StringBuffer buf = new StringBuffer(""); 
		for (int offset = 0; offset < b.length; offset++) { 
		i = b[offset]; 
		if(i<0) i+= 256; 
		if(i<16) 
		buf.append("0"); 
		buf.append(Integer.toHexString(i)); 
		} 

		return buf.toString();

		} catch (NoSuchAlgorithmException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		}
		return null; 
	}
	
	public static String formatNextRuleNo(String str){
		    Integer res = Integer.parseInt(str);
		    res++;
		    DecimalFormat df = new DecimalFormat(STR_FORMAT);
		    return df.format(res);
		}
}
