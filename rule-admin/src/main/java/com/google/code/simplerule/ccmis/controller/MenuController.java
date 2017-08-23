package com.google.code.simplerule.ccmis.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;
import com.google.code.simplerule.proxy.risk.service.RiskMenuService;

@Controller
@RequestMapping("/risk/menu")
public class MenuController extends BaseController {
	@Autowired
	private RiskMenuService riskMenuService;

	@RequestMapping("/getRootList")
	public void getRootList(HttpServletRequest request, HttpServletResponse response){
		List<RiskMenuEntity> list = riskMenuService.getRootList();
		this.outJsonString(response, list);
	}
	
	@RequestMapping("/getByParentId")
	public void getByParentId(HttpServletRequest request, HttpServletResponse response){
		String parentId = request.getParameter("id");
		List<RiskMenuEntity> list = null;
		if(!StringUtils.isBlank(parentId)){
			list = riskMenuService.getMenuListByParentId(Integer.valueOf(parentId));
		}
	
		this.outJsonString(response, list);
	}
	
	@RequestMapping("/menuList")
	public void getMenu(HttpServletRequest request, HttpServletResponse response) {

		Map<RiskMenuEntity, List<RiskMenuEntity>> result = riskMenuService
				.getMenuList();
		this.outJsonString(response, result);

	}

}
