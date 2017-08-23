package com.google.code.simplerule.core.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	public static String dateFormat(Date d, String format) {
		if (d == null)
			return "";
		DateFormat df = new SimpleDateFormat(format);
    	return df.format(d);
	}

	public static Date getDateString(String dateString, String format) {
		if (dateString == null || dateString.equals(""))
			return null;
		try {
			DateFormat df = new SimpleDateFormat(format);
			return df.parse(dateString);
		}
		catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}
}
