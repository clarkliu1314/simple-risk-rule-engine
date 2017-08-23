package com.google.code.simplerule.ccmis.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.ccmis.utils.FileUtil;


@Controller
@RequestMapping("/risk/riskLog")
public class RiskLogController extends BaseController {
	
//	@Autowired
//	private RiskLogService riskLogService;
	private final Logger logger = LoggerFactory.getLogger(RiskLogController.class);
	
	@RequestMapping("/list")
	public void list(HttpServletRequest request,HttpServletResponse response) {
		
		Stack<String> lastRowStack = null;
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serviceName", StringUtils.isBlank(request.getParameter("serviceName")) ? null 
				: request.getParameter("serviceName"));
		paramMap.put("code", StringUtils.isBlank(request.getParameter("code")) ? null 
				: request.getParameter("code"));
		paramMap.put("startCreateDate", StringUtils.isBlank(request.getParameter("startCreateDate")) ? null 
				: request.getParameter("startCreateDate"));
		paramMap.put("endCreateDate", StringUtils.isBlank(request.getParameter("endCreateDate")) ? null 
				: request.getParameter("endCreateDate"));
		paramMap.put("lastRow", StringUtils.isBlank(request.getParameter("lastRow")) ? null 
				: request.getParameter("lastRow"));
		
		if(StringUtils.isBlank(request.getParameter("lastRow")))
		{
			lastRowStack =  new Stack<String>();
			lastRowStack.push(null);
			
		}
		else if(request.getParameter("lastRow").equals("previous"))
		{
			lastRowStack = (Stack)request.getSession().getAttribute("lastRowStack");
			if(lastRowStack.size() == 0)
			{
				paramMap.put("lastRow", null);
			}
			else
			{	
				lastRowStack.pop();
				paramMap.put("lastRow", lastRowStack.peek());
			}	
		}
		else
		{
			lastRowStack = (Stack)request.getSession().getAttribute("lastRowStack");
			lastRowStack.push(request.getParameter("lastRow"));
			paramMap.put("lastRow", request.getParameter("lastRow"));
		}
		
		request.getSession().setAttribute("lastRowStack", lastRowStack);
	    
	    JsonResult jr = new JsonResult();
//	    PageTagResultDTO<RiskLogEntity> pageTagResultDTO =  riskLogService.query(paramMap);
	    
//        jr.setList(pageTagResultDTO.getList());
//        jr.setLastRow(pageTagResultDTO.getLastRow());
        jr.setSuccess(true);
        
        this.outJsonString(response, JSON.toJSONString(jr));
	}
	
	@RequestMapping("/exportExcel")
	public void exportExcel(HttpServletRequest request,HttpServletResponse response) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("serviceName", StringUtils.isBlank(request.getParameter("serviceName")) ? null 
				: request.getParameter("serviceName"));
		paramMap.put("code", StringUtils.isBlank(request.getParameter("code")) ? null 
				: request.getParameter("code"));
		paramMap.put("startCreateDate", StringUtils.isBlank(request.getParameter("startCreateDate")) ? null 
				: request.getParameter("startCreateDate"));
		paramMap.put("endCreateDate", StringUtils.isBlank(request.getParameter("endCreateDate")) ? null 
				: request.getParameter("endCreateDate"));
		
	    HSSFWorkbook wb = new HSSFWorkbook();//riskLogService.query4Excel(paramMap);
		
		FileUtil.export(request, response, wb);
	}
	
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
}
