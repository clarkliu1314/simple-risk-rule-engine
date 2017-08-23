package com.google.code.simplerule.ccmis.controller;

import java.beans.PropertyEditorSupport;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.ServletRequestDataBinder;

public class BaseController {
	    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);
	    private static DateFormat normalDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	    private static DateFormat otherDateFormat = new SimpleDateFormat("yyyy-MM-dd");


	    /**
	     * 请求信息封装到对象
	     *
	     * @param request 请求信息
	     * @param object  封装对象
	     */
	    protected void bindObject(HttpServletRequest request, Object object) {
	        ServletRequestDataBinder binder = new ServletRequestDataBinder(object);
	        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
	            public void setAsText(String value) {
	                try {
	                    setValue(normalDateFormat.parse(value));
	                } catch (ParseException e) {
	                    try {
	                        setValue(otherDateFormat.parse(value));
	                    } catch (ParseException e1) {
	                        setValue(null);
	                    }
	                }
	            }

	            public String getAsText() {
	                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format((Date) getValue());
	            }

	        });
	        binder.bind(request);
	    }

	    /**
	     * 输出json串,指定contenttype
	     *
	     * @param response
	     * @param object
	     * @param ContentType
	     */
	    public void outJsonString(HttpServletResponse response, Object object, String ContentType) {
	        response.setContentType(ContentType);
	        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        response.setHeader("Pragma", "no-cache");
	        try {
	            PrintWriter out = response.getWriter();
	            out.write(toJson(object));
	            out.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * 输出json串
	     *
	     * @param response 响应信息
	     * @param json     Json串
	     */
	    public void outJsonString(HttpServletResponse response, String json) {
	        response.setContentType("text/javascript;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        response.setHeader("Pragma", "no-cache");
	        try {
	            PrintWriter out = response.getWriter();
	            out.write(json);
	            out.close();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    /**
	     * 输出json串
	     *
	     * @param response 响应信息
	     * @param object   转Json串的对象
	     */
	    public void outJsonString(HttpServletResponse response, Object object) {
	        response.setContentType("text/javascript;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        response.setHeader("Pragma", "no-cache");
	        try {
	            PrintWriter out = response.getWriter();
	            out.write(toJson(object));
	            out.close();
	        } catch (IOException e) {
	            logger.warn("BaseAjaxController | outJsonString | " + object + " | error:" + e.getMessage());
	        }
	    }

	    /**
	     * 输出json串
	     *
	     * @param response 响应信息
	     * @param object   转Json串的对象
	     * @param pros     需要转Json的属性
	     */
	    public void outJsonString(HttpServletResponse response, Object object, String[] pros) {
	        response.setContentType("text/javascript;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        response.setHeader("Pragma", "no-cache");
	        try {
	            PrintWriter out = response.getWriter();
	            out.write(toJson(object, pros));
	            out.close();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    /**
	     * 输出json串
	     *
	     * @param response 响应信息
	     * @param object   转Json串的对象
	     * @param pros     不需要转Json的属性
	     */
	    public void outJsonString2(HttpServletResponse response, Object object, String[] pros) {
	        response.setContentType("text/javascript;charset=UTF-8");
	        response.setHeader("Cache-Control", "no-store, max-age=0, no-cache, must-revalidate");
	        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
	        response.setHeader("Pragma", "no-cache");
	        try {
	            PrintWriter out = response.getWriter();
	            out.write(toJson2(object, pros));
	            out.close();
	        } catch (IOException e) {
	            throw new RuntimeException(e);
	        }
	    }

	    /**
	     * * 将某一个对象中的 一部分属性 转换为json格式的字符串
	     *
	     * @param obj 转Json对象
	     * @return 返回Json串
	     */
	    public static String toJson(Object obj) {
	        return toJson(obj, null);
	    }

	    /**
	     * * 将某一个对象中的 一部分属性 转换为json格式的字符串
	     *
	     * @param obj  转Json对象
	     * @param pros 需要转换为json的属性
	     * @return 返回Json串
	     */
	    public static String toJson(Object obj, String[] pros) {
	        JsonConfig jsonConfig = new JsonConfig();
	        jsonConfig.registerJsonValueProcessor(Date.class, new MyDateJsonValueProcessor());
	        jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new MyDateJsonValueProcessor());
	        jsonConfig.registerJsonValueProcessor(Timestamp.class, new MyDateJsonValueProcessor());
	        IgnoreFieldPropertyFilterImpl f = new IgnoreFieldPropertyFilterImpl(pros);
	        jsonConfig.setJsonPropertyFilter(f);
	        String jsonStr = null;
	        if (obj instanceof Collection) {
	            jsonStr = JSONArray.fromObject(obj, jsonConfig).toString();
	        } else {
	            jsonStr = JSONObject.fromObject(obj, jsonConfig).toString();
	        }
	        return jsonStr;
	    }


	    /**
	     * * 将某一个对象中的 一部分属性 转换为json格式的字符串
	     *
	     * @param obj  转Json对象
	     * @param pros 不需要转换为json的属性
	     * @return 返回Json串
	     */
	    public static String toJson2(Object obj, String[] pros) {
	        JsonConfig jsonConfig = new JsonConfig();
	        jsonConfig.registerJsonValueProcessor(Date.class, new MyDateJsonValueProcessor());
	        jsonConfig.registerJsonValueProcessor(java.sql.Date.class, new MyDateJsonValueProcessor());
	        jsonConfig.registerJsonValueProcessor(Timestamp.class, new MyDateJsonValueProcessor());
	        NotIgnoreFieldPropertyFilterImpl f = new NotIgnoreFieldPropertyFilterImpl(pros);
	        jsonConfig.setJsonPropertyFilter(f);
	        String jsonStr = null;
	        if (obj instanceof Collection) {
	            jsonStr = JSONArray.fromObject(obj, jsonConfig).toString();
	        } else {
	            jsonStr = JSONObject.fromObject(obj, jsonConfig).toString();
	        }
	        return jsonStr;
	    }

	}

	/**
	 * 处理json中日期对象   将日期输出为毫秒数
	 */
	class MyDateJsonValueProcessor implements net.sf.json.processors.JsonValueProcessor {

	    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
	        if (null == value) {
	            return "";
	        } else {
	            if (value instanceof Date || value instanceof java.sql.Date || value instanceof Timestamp) {
	                if (value == null) {
	                    return "";
	                }
	                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                return df.format(value);
	            }
	        }
	        return value.toString();
	    }

	    public Object processObjectValue(String s, Object object, JsonConfig jsonConfig) {
	        if (null == object) {
	            return "";
	        } else {

	            if (object instanceof Date || object instanceof java.sql.Date || object instanceof Timestamp) {
	                if (object == null) {
	                    return "";
	                }
	                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                return df.format(object);
	            }
	        }
	        return object.toString();
	    }
	}

	/**
	 * json属性过滤类
	 */
	class IgnoreFieldPropertyFilterImpl implements PropertyFilter {

	    /**
	     * 不需要过滤的属性名称
	     */
	    private String[] fields;

	    public IgnoreFieldPropertyFilterImpl() {
	    }

	    public IgnoreFieldPropertyFilterImpl(String[] pars) {
	        this.fields = pars;
	    }

	    public boolean apply(Object source, String name, Object value) {

	        if (value == null) {     //值为空的属性 不转换为json
	            return true;
	        }

	        if (fields == null) {     // fields为空 代表所有的非空属性都转换为json
	            return false;
	        }

	        if (fields != null && fields.length > 0) {
	            if (juge(fields, name)) {
	                return true;
	            } else {
	                return false;
	            }
	        }
	        return false;
	    }

	    /**
	     * 不需要过滤的属性
	     *
	     * @param s
	     * @param s2
	     * @return
	     */
	    public boolean juge(String[] s, String s2) {
	        for (String sl : s) {
	            if (s2.equals(sl)) {
	                return false;
	            }
	        }
	        return true;
	    }
	}

	/**
	 * json属性过滤类
	 */
	class NotIgnoreFieldPropertyFilterImpl implements PropertyFilter {

	    /**
	     * 不需要过滤的属性名称
	     */
	    private String[] fields;

	    public NotIgnoreFieldPropertyFilterImpl() {
	    }

	    public NotIgnoreFieldPropertyFilterImpl(String[] pars) {
	        this.fields = pars;
	    }

	    public boolean apply(Object source, String name, Object value) {

	        if (value == null) {     //值为空的属性 不转换为json
	            return true;
	        }

	        if (fields == null) {     // fields为空 代表所有的非空属性都转换为json
	            return false;
	        }

	        if (fields != null && fields.length > 0) {
	            return !juge(fields, name);
	        }

	        return false;
	    }

	    /**
	     * 不需要过滤的属性
	     *
	     * @param s
	     * @param s2
	     * @return
	     */
	    public boolean juge(String[] s, String s2) {
	        for (String sl : s) {
	            if (s2.equals(sl)) {
	                return false;
	            }
	        }
	        return true;
	    }
}
