package com.google.code.simplerule.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.alibaba.fastjson.JSON;
import com.google.code.simplerule.core.exception.RiskException;
import com.google.code.simplerule.core.processor.RiskCode;

public class BaseController {

	protected Logger logger = LoggerFactory.getLogger(BaseController.class);
	
	protected String objectToJson(Object result) {
		return JSON.toJSONString(result);
	}

	protected Object jsonToObject(HttpServletRequest request, Class clzz) throws RiskException {
		return JSON.parseObject(receiveParam(request), clzz);
	}
	
	protected Map jsonToMap(HttpServletRequest request) throws RiskException {
		String p = receiveParam(request);
		logger.info("Recv request:" + p);
		return (Map)JSON.parse(p);
	}

	protected String getRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip != null && !ip.equals(""))
			return ip;
		ip = request.getHeader("X-Real-IP");
		return ip = ip != null ? ip : request.getRemoteAddr();
	}
	
	private String receiveParam(HttpServletRequest request) throws RiskException {
		InputStream in = null;
		StringBuffer sb = new StringBuffer("");
		try {
			in = request.getInputStream();
			if (in == null) {
				return null;
			}
			byte[] temp = new byte[1024];
			int num = 0;
			while ((num = in.read(temp)) > -1) {
				sb.append(new String(temp, 0, num, "utf-8"));
				temp = new byte[1024];
			}
			in.close();
		} catch (IOException e) {
			logger.error("通讯异常");
			throw new RiskException(RiskCode.NetworkError, "通讯异常");
		} finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException e1) {
				logger.error("通讯异常");
				throw new RiskException(RiskCode.NetworkError, "通讯异常");
			}
		}
		return sb.toString();
	}
}
