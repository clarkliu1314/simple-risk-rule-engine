package com.google.code.simplerule.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.simplerule.core.exception.RiskException;
import com.google.code.simplerule.core.help.HelpInfo;
import com.google.code.simplerule.core.processor.RiskCode;
import com.google.code.simplerule.core.processor.RuleProcessor;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskInterface;

@Controller
public class RiskController extends BaseController {
	@Resource
	private RuleProcessor processor;
	
	public RiskController() {
	}
	
	@RequestMapping(value = "/1.0/process/{interface}", method = RequestMethod.GET)
	public String process(@PathVariable("interface")String interfaceName) {
		return "redirect://1.0/process/" + interfaceName + "/status";
	}
	
	@RequestMapping(value = "/1.0/process/{interface}", method = RequestMethod.POST)
	@ResponseBody
	public String process(
			@PathVariable("interface")String interfaceName, 
			HttpServletRequest request) {
		RiskResult result = null;
		try {
			result = processor.process(interfaceName, parseToParam(interfaceName, request));
		}
		catch (RiskException re) {
			return objectToJson(exceptionResult(re));
		}
		catch (Exception e) {
			logger.error("json error.", e);
			return objectToJson(RiskCode.simpleResult(RiskCode.Exception, "Json error."));
		}
		return objectToJson(result);
	}
	
	private RiskResult exceptionResult(RiskException re) {
		RiskResult r = new RiskResult();
		r.setCode(re.getCode());
		r.setDescription(re.getMessage());
		return r;
	}

	@RequestMapping("/1.0/process")
	public String list(Model model) {
		Map<String, RiskInterface> rules = processor.getRuleList();
		model.addAttribute("list", rules);
		return "process/list";
	}
	
	@RequestMapping("/1.0/process/{interface}/status")
	public String status(@PathVariable("interface")String interfaceName, Model model) {
		model.addAttribute("name", interfaceName);
		model.addAttribute("monitor", processor.getInterfaceMonitor(interfaceName));
		return "process/status";
	}
	
	@RequestMapping("/1.0/process/{interface}/help")
	public String help(@PathVariable("interface")String interfaceName, Model model) {
		HelpInfo hi = processor.getHelpInfo(interfaceName);
		model.addAttribute("name", interfaceName);
		model.addAttribute("helpInfo", hi);
		return "process/help";
	}

	private Map parseToParam(String interfaceName, HttpServletRequest request) throws RiskException {
		Map map = jsonToMap(request);
		if (map == null) {
			map = new HashMap();
			map.put("clientIp", getRemoteIp(request));
		}
		return map;
	}
}
