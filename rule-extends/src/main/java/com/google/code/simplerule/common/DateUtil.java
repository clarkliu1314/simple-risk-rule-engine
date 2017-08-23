package com.google.code.simplerule.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @EnglishDescription:DateUtil
 * @ChineseDescription:日期工具类
 * @author:sunny
 * @since:2013-03-08
 * @version:1.0.0
 */
public class DateUtil {

    /**
     * 获取给定时间的天起始时间
     * @param date
     * @return
     */
    public static Date getDayStart(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        setStart(gc);
        return gc.getTime();
    }

    /**
     * 获取给定时间的天结束时间
     * @param date
     * @return
     */
    public static Date getDayEnd(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        setEnd(gc);
        return gc.getTime();
    }

    /**
     * 获取给定时间的周开始时间
     * @param date
     * @return
     */
    public static Date getWeekStart(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        //设置一周的第一天
        gc.setFirstDayOfWeek(Calendar.MONDAY);
        //设置取星期几
        gc.set(Calendar.DAY_OF_WEEK,Calendar.MONTH);
        setStart(gc);
        return gc.getTime();
    }

    /**
     * 获取给定时间的周结束时间
     * @param date
     * @return
     */
    public static Date getWeekEnd(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        //设置一周的第一天
        gc.setFirstDayOfWeek(Calendar.MONDAY);
        //设置取星期几
        gc.set(Calendar.DAY_OF_WEEK,Calendar.SUNDAY);
        setEnd(gc);
        return gc.getTime();
    }

    /**
     * 获取给定时间的月开始时间
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        gc.set(Calendar.DAY_OF_MONTH,1);
        setStart(gc);
        return gc.getTime();
    }

    /**
     * 获取给定时间的月结束时间
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date){
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(date);
        //把日期设置为当月第一天
        gc.set(Calendar.DATE, 1);
        //日期回滚一天，也就是最后一天
        gc.roll(Calendar.DATE, -1);
        setEnd(gc);
        return gc.getTime();
    }

    /**
     * 设置开始时间
     * @param calendar
     */
    public static void   setStart(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
    }

    /**
     * 设置结束时间
     * @param calendar
     */
    public static void   setEnd(Calendar calendar){
        calendar.set(Calendar.HOUR_OF_DAY,23);
        calendar.set(Calendar.SECOND,59);
        calendar.set(Calendar.MINUTE,59);
        calendar.set(Calendar.MILLISECOND,999);
    }

    public static void main(String []args){
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
        System.out.println(simpleDateFormat.format(getDayStart(date)));
        System.out.println(simpleDateFormat.format(getDayEnd(date)));
        System.out.println(simpleDateFormat.format(getWeekStart(date)));
        System.out.println(simpleDateFormat.format(getWeekEnd(date)));
        System.out.println(simpleDateFormat.format(getMonthStart(date)));
        System.out.println(simpleDateFormat.format(getMonthEnd(date)));
    }

}
