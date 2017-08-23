package com.google.code.simplerule.ccmis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.google.code.simplerule.core.monitor.RiskMonitoring;
import com.google.code.simplerule.core.exception.RiskValidationException;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.processor.ProxyRuleProcessor;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.factory.PackageRuleFactory;
import com.google.code.simplerule.proxy.risk.entity.FactorEntity;
import com.google.code.simplerule.proxy.risk.entity.FactorFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.OperatorEntity;
import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleConditionEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleHandlerEntity;
import com.google.code.simplerule.proxy.risk.entity.RulesEntity;
import com.google.code.simplerule.proxy.risk.entity.common.Constants;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;
import com.google.code.simplerule.proxy.risk.service.RiskMenuService;
import com.google.code.simplerule.proxy.risk.service.RulesDraftService;
import com.google.code.simplerule.proxy.risk.service.RulesService;

@Controller
@RequestMapping("/risk/rules")
public class RulesController extends BaseController {

	@Autowired
	private RulesService rulesService;
	@Autowired
	private RulesDraftService rulesDraftService;
	@Autowired
	private RiskMenuService riskMenuService;
	@Autowired
	private ProxyRuleProcessor proxyRuleProcessor;
	@Autowired
	private PackageRuleFactory packageRuleFactory;
//	@Autowired
//	private RiskMonitoring monitor;

	@RequestMapping("/list")
	public void getRules(String id, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isBlank(id)) {
			return;
		}
		RiskMenuEntity menu = riskMenuService.getById(Integer.parseInt(id));
		if (menu == null) {
			return;
		}
		Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
		JSONObject user = (JSONObject) JSON.toJSON(obj);
		String erp = (String) user.get(Constants.ERP_LOGIN_NAME);
		List<RulesEntity> list = rulesService.findByInterfaceNameforAudit(
				menu.getInterfaceName(), erp);
		this.outJsonString(response, list);
	}

	@RequestMapping("/factors")
	public void getConditions(String rulesId,String flag, HttpServletRequest request,
			HttpServletResponse response) {
		RulesEntity rules;
		List<RuleConditionEntity> conditions = null;
		List<RuleHandlerEntity> handlers = null;
		if (StringUtils.isBlank(rulesId))
			return;
		if("draft".equals(flag)){
		  rules = rulesDraftService.findByRuleId(Long.valueOf(rulesId));
		  conditions = rulesDraftService.findRuleConditionByRuleId(Long.valueOf(rulesId));
		  handlers = rulesDraftService.findRuleHandlerByRuleId(Long.valueOf(rulesId));
		}else{
		 rules = rulesService.findByRuleId(Long.valueOf(rulesId));
		 conditions = rulesService
					.findRuleConditionByRuleId(Long.valueOf(rulesId));
		 handlers = rulesService
					.findRuleHandlerByRuleId(Long.valueOf(rulesId));
		}
		if (rules == null)
			return;
		Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
		JSONObject user = (JSONObject) JSON.toJSON(obj);
		String erp = (String) user.get(Constants.ERP_LOGIN_NAME);
		if (rules.getCreatePerson().equalsIgnoreCase(erp))
			rules.setIsowner(true);
		
		List<RiskFactor> factors = proxyRuleProcessor.findFactors(null);
		List<FactorEntity> factorList = rulesService.getFactorList(factors);
		List<List<OperatorEntity>> operators = rulesService
				.getConditionList(rulesService
						.getFactorsByConditions(conditions));
		List<RiskHandler> handlersList = proxyRuleProcessor.findHandlers(null);
		List<RuleHandlerEntity> hl = rulesService
				.getRuleHandlerList(handlersList);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("factors", factorList);
		result.put("conditions", conditions);
		result.put("operators", operators);
		result.put("rules", rules);
		result.put("handlers", handlers);
		result.put("handlerList", hl);
		this.outJsonString(response, result);

	}

	@RequestMapping("/getOperatorAndParams")
	public void getOperator(String factor, HttpServletRequest request,
			HttpServletResponse response) {
		List<OperatorEntity> operators = rulesService
				.getOperatorsByFactor(factor);
		List<FactorFieldEntity> params = rulesService.getParamsByFactor(factor);
		RiskFactor rf = (RiskFactor) ObjectUtils.loadClass(factor);
		Map<String, Object> result = new HashMap<String, Object>();
		FactorField field = rf.getResult();
		result.put("operators", operators);
		result.put("params", params);
		result.put("field", field);
		this.outJsonString(response, result);
	}

	@RequestMapping("/getFactorList")
	public void getFactorList(HttpServletRequest request,
			HttpServletResponse response) {
		List<RiskFactor> factors = proxyRuleProcessor.findFactors(null);
		List<FactorEntity> factorList = rulesService.getFactorList(factors);
		this.outJsonString(response, factorList);

	}

	@RequestMapping("/getHandlerList")
	public void getHandlers(HttpServletRequest request,
			HttpServletResponse response) {
		List<RiskHandler> handlers = proxyRuleProcessor.findHandlers(null);
		List<RuleHandlerEntity> result = rulesService
				.getRuleHandlerList(handlers);
		this.outJsonString(response, result);
	}

	@RequestMapping("/saveRules")
	public void saveRules(String rules, String conditions, String handlers,
			String command, HttpServletRequest request,
			HttpServletResponse response) {
		Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
		JSONObject user = (JSONObject) JSON.toJSON(obj);
		String erp = (String) user.get(Constants.ERP_LOGIN_NAME);

		RulesEntity rule = JSON.toJavaObject(JSON.parseObject(rules),
				RulesEntity.class);
		rule.setCreatePerson(erp);
		rule.setAuditStatus(0);
		rule.setOperator(erp);

		List conditionList = JSON.parseArray(conditions);
		List<RuleConditionEntity> clist = new ArrayList<RuleConditionEntity>();
		for (Object object : conditionList) {
			RuleConditionEntity entity = JSON.toJavaObject(
					JSON.parseObject(JSON.toJSONString(object)),
					RuleConditionEntity.class);
			clist.add(entity);
		}

		List handlerList = JSON.parseArray(handlers);
		List<RuleHandlerEntity> hlist = new ArrayList<RuleHandlerEntity>();
		for (Object object : handlerList) {
			RuleHandlerEntity entity = JSON.toJavaObject(
					JSON.parseObject(JSON.toJSONString(object)),
					RuleHandlerEntity.class);
			hlist.add(entity);
		}
		if (Constants.COMMAND_ADD.equalsIgnoreCase(command)) {
			String no = rulesService.getRuleNumber(rule.getInterfaceName());
			rule.setNo(no);
			rule.setOperateType(0);
			rulesService.addRules(rule);
		}
		rulesDraftService.updateAndSave(rule, clist, hlist);
		this.outJsonString(response,Constants.RESPONSE_SUCCESS);

	}

	@RequestMapping("/removeRules")
	public void removeRules(String ruleNo, HttpServletRequest request,
			HttpServletResponse response) {
		if (StringUtils.isBlank(ruleNo)) {
			return;
		}
		rulesService.deleteRulesByIdForAudit(ruleNo);
		this.outJsonString(response,Constants.RESPONSE_SUCCESS);

	}
	

	@RequestMapping("/getExternalArgumentsSize")
	public void getExternalArgumentsSize(String factor, HttpServletRequest request,
			HttpServletResponse response) {
		if(factor == null)
			return;
		RiskFactor rf = (RiskFactor) ObjectUtils.loadClass(factor);
	    int size = rf.getExternalArguments().length; 
		this.outJsonString(response,String.valueOf(size));

	}

	@RequestMapping("/validateCheckValue")
	public void validateCheckValue(String factor, String checkValue,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		RiskFactor rf = (RiskFactor) ObjectUtils.loadClass(factor);
		FactorField efield = rf.getResult();
		try {
			efield.convert(checkValue);
			result.put("code", Constants.SUCCESS_CODE);
		} catch (RiskValidationException e) {
			result.put("code", Constants.ERROR_CODE);
			result.put("message", e.getMessage());
			this.outJsonString(response, result);
		}
		this.outJsonString(response, result);

	}

	@RequestMapping("/validateParam")
	public void validateParam(String factor, String param,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		RiskFactor rf = (RiskFactor) ObjectUtils.loadClass(factor);
		FactorField[] fields = rf.getInternalArguments();
		String[] ps = param.split(",");
		for (int i = 0; i < ps.length; i++) {
			try {
				fields[i].convert(ps[i]);
				result.put("code", Constants.SUCCESS_CODE);
			} catch (RiskValidationException e) {
				result.put("code",Constants.ERROR_CODE);
				result.put("message", e.getMessage());
				this.outJsonString(response, result);

			}
		}
		this.outJsonString(response, result);

	}

	@RequestMapping("/validateHandler")
	public void validateHandler(String handler, String value, String type,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> result = new HashMap<String, String>();
		RiskHandler hr = (RiskHandler) ObjectUtils.loadClass(handler);
		FactorField[] fields = hr.getArgumentFields();
		try {
			if ("code".equalsIgnoreCase(type))
				fields[0].convert(value);
			if ("description".equalsIgnoreCase(type))
				fields[1].convert(value);
			result.put("code", Constants.SUCCESS_CODE);
		} catch (RiskValidationException e) {
			result.put("code", Constants.ERROR_CODE);
			result.put("message", e.getMessage());
			this.outJsonString(response, result);

		}
		this.outJsonString(response, result);

	}

}
