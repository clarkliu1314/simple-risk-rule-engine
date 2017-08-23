package com.google.code.simplerule.ccmis.controller;

import java.text.SimpleDateFormat;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

//import com.jd.payment.core.ccmis.common.CookieContext;
import com.google.code.simplerule.proxy.risk.dto.CommonResult;
import com.google.code.simplerule.proxy.risk.dto.ManualAuditDataDto;
import com.google.code.simplerule.proxy.risk.entity.ManualAuditDataEntity;
import com.google.code.simplerule.proxy.risk.service.ManualAuditDataService;

/**
 * @author    sunny
 * @since     2013-5-17
 * @version   1.0.0
 * @comment   ManualAuditDataController.java
 */
@Controller
public class ManualAuditDataController extends BaseController{

	@Resource
	private ManualAuditDataService manualAuditDataService;
	
	@RequestMapping("/risk/manualAuditData/index")
	private String index(){
		return "risk/manualAuditData/manualAuditData";
	}
	
	@RequestMapping("/risk/manualAuditData")
	private String manualAuditData(){
		return index();
	}
	
	/**
	 * 查询分页数据
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/risk/manualAuditData/selectPaging")
	@ResponseBody
	public String selectPaging(HttpServletRequest request, HttpServletResponse response){
		long start = System.currentTimeMillis();
		ManualAuditDataEntity entity = new ManualAuditDataEntity();
		bindObject(request,entity);
		String date = entity.getManualAuditDate().replace("-", "");
		entity.setManualAuditDate(date);
		CommonResult<ManualAuditDataEntity> result = new CommonResult<ManualAuditDataEntity>();//manualAuditDataService.selectPaging(entity);
		long end = System.currentTimeMillis();
		System.out.println("总时间:" + (end-start));
		return toJson(result);
	}
	
	/**
	 * 人工审核
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/risk/manualAuditData/manualAudit")
	@ResponseBody
	public String manualAudit(HttpServletRequest request, HttpServletResponse response){
		ManualAuditDataEntity entity = new ManualAuditDataEntity();
		bindObject(request,entity);
		
		//获取操作人
//		CookieContext context = CookieContext.getCookieContext(request, response);
//		entity.setOperator(context.getUsername());
//		
//		manualAuditDataService.manualAudit(entity);
		CommonResult result = new CommonResult();
		result.setSuccess(true);
		result.setMessage("审核成功。");
		return toJson(result);
	}
	
}
