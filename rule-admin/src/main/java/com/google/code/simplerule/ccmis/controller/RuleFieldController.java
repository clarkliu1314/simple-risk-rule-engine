package com.google.code.simplerule.ccmis.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.entity.RuleFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.common.ComboTreeEntity;
import com.google.code.simplerule.proxy.risk.entity.common.Constants;
import com.google.code.simplerule.proxy.risk.service.RuleFieldService;

@Controller
@RequestMapping("/risk/ruleFied")
public class RuleFieldController extends BaseController {

	private final Logger logger = LoggerFactory
			.getLogger(RuleFieldController.class);

	@Autowired
	private RuleFieldService ruleFieldService;

	@RequestMapping("/getGird")
	public void getGird(Integer rows, Integer page, HttpServletRequest request, HttpServletResponse response) {
		
		RuleFieldEntity entity = new RuleFieldEntity();
		bindObject(request, entity);
		
		if (page == null) {
			page = new Integer(0);
		}
		if (rows == null) {
			rows = Integer.MAX_VALUE;
		}
		entity.setStart((page-1) * rows);
		entity.setLimit(rows);
		
		Map<String, Object> jsonResult = new HashMap<String, Object>();
		
		try {
			PageTagResultDTO<RuleFieldEntity> result = ruleFieldService.queryLimit(entity);
			jsonResult.put("total", result.getTotalSize());
			jsonResult.put("rows", result.getList());
		} catch (Exception e) {
			logger.error("RuleFieldController | getGird | error:", e);
		} finally {
			this.outJsonString(response, jsonResult);
		}
	}
	
	@RequestMapping("/listOrderByCategory.json")
	public void listOrderByCategory(HttpServletRequest request, HttpServletResponse response) {
		RuleFieldEntity entity = new RuleFieldEntity();
		bindObject(request, entity);
		
		List<RuleFieldEntity> result = null;
		
		try {
			result = ruleFieldService.listOrderByCategory();
		} catch (Exception e) {
			logger.error("RuleFieldController | getGird | error:", e);
		} finally {
			this.outJsonString(response, result);
		}
	}

	@RequestMapping("/save")
	public void save(HttpServletRequest request, HttpServletResponse response,
			RuleFieldEntity entity) {

		JsonResult jr = new JsonResult();

		try {
			Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
			JSONObject user = (JSONObject) JSON.toJSON(obj);
			String erp = (String) user.get(Constants.ERP_LOGIN_NAME);
			entity.setOperator(erp);
			ruleFieldService.saveRuleFieldEntity(entity);
			jr.setSuccess(true);
			jr.setMessage("新增成功。");
		} catch (Exception e) {
			jr.setSuccess(true);
			jr.setMessage("新增失败：" + e.getMessage());
			logger.error("RuleFieldController | save | error:", e);
		}

		this.outJsonString(response, jr);
	}

	@RequestMapping("/modify")
	public void modify(HttpServletResponse response, RuleFieldEntity entity,
			HttpServletRequest request) {

		JsonResult jr = new JsonResult();

		try {
			Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
			JSONObject user = (JSONObject) JSON.toJSON(obj);
			String erp = (String) user.get(Constants.ERP_LOGIN_NAME);
			entity.setOperator(erp);
			ruleFieldService.modifyRuleFieldEntity(entity);
			jr.setSuccess(true);
			jr.setMessage("修改成功。");
		} catch (Exception e) {
			jr.setSuccess(true);
			jr.setMessage("修改失败：" + e.getMessage());
			logger.error("RuleFieldController | modify | error:", e);
		}

		this.outJsonString(response, jr);
	}

	@RequestMapping("/delete")
	public void deleteById(Long id, HttpServletResponse response) {
		JsonResult jr = new JsonResult();

		try {
			ruleFieldService.deleteById(id);
			jr.setSuccess(true);
			jr.setMessage("删除成功。");
		} catch (Exception e) {
			jr.setSuccess(true);
			jr.setMessage("删除失败：" + e.getMessage());
			logger.error("RuleFieldController | deleteById | error:", e);
		}

		this.outJsonString(response, jr);
	}
	
	@RequestMapping("/queryAllOnCategory")
	public void queryAllOnCategory(Long id, HttpServletResponse response) {
		 List<ComboTreeEntity> list = ruleFieldService.queryAllOnCategory();
		 this.outJsonString(response, list);
	}
	

}
