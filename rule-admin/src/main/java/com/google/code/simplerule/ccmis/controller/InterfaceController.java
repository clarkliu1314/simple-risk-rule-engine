package com.google.code.simplerule.ccmis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.ccmis.controller.entity.EnumInfo;
import com.google.code.simplerule.ccmis.controller.entity.MapEntity;
//import com.google.code.simplerule.core.monitor.RiskMonitoring;
import com.google.code.simplerule.core.processor.RuleProcessor;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.util.CommonBeanUtils;
import com.google.code.simplerule.core.util.TreeGridJsonUtils;
import com.google.code.simplerule.proxy.risk.entity.InterfaceEntity;
import com.google.code.simplerule.proxy.risk.entity.InterfaceLogFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;
import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntityWrapper;
import com.google.code.simplerule.proxy.risk.entity.common.Constants;
import com.google.code.simplerule.proxy.risk.enums.EnumFactory;
import com.google.code.simplerule.proxy.risk.service.InterfaceLogFieldService;
import com.google.code.simplerule.proxy.risk.service.InterfaceService;
import com.google.code.simplerule.proxy.risk.service.RiskMenuService;
import com.google.code.simplerule.proxy.risk.service.RulesService;

@Controller
@RequestMapping("/risk/interface")
public class InterfaceController extends BaseController {
	private final Logger logger = LoggerFactory
			.getLogger(InterfaceController.class);

//	private RiskMonitoring monitor;
	@Autowired
	private InterfaceService service;
	@Autowired
	private RulesService rulesService;
	@Autowired
	private RuleProcessor processor;
	@Autowired
	private RiskMenuService riskMenuService;
	@Autowired
	private InterfaceLogFieldService interfaceLogFieldService;

	@RequestMapping(value="")
	public String index(){
		return "risk/interface/interface";
	}
	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) {

		InterfaceEntity param = new InterfaceEntity();
		bindObject(request, param);
		JsonResult jr = new JsonResult();
		PageTagResultDTO<InterfaceEntity> result = service.queryLimit(param);
		jr.setList(result.getList());
		jr.setTotalSize(result.getTotalSize());
		jr.setSuccess(true);
		this.outJsonString(response, jr);
	}
	
	@RequestMapping("/listAll")
	public void list2(HttpServletRequest request, HttpServletResponse response) {
		List<InterfaceEntity> list = service.findList();
		this.outJsonString(response, list);
	}
	
	@RequestMapping("/getInterfaceTreeList")
	public void getInterfaceTreeList(HttpServletRequest request, HttpServletResponse response)  throws Exception{
		List<RiskMenuEntity> menuList = riskMenuService.findAll();
		
		if(
				//CollectionUtils.isEmpty(menuList)
				menuList ==null || menuList.size()==0) {
			outJsonString(response, StringUtils.EMPTY);
		}
		
		List<InterfaceEntity> interfaceList = service.findList();
		Map<String, InterfaceEntity> interfaceEntityMap = new HashMap<String, InterfaceEntity>();
		if(interfaceList !=null || interfaceList.size()>0){
			for(InterfaceEntity interfaceEntity : interfaceList){
				interfaceEntityMap.put(interfaceEntity.getInterfaceName(), interfaceEntity);
			}
		}
		
		List<RiskMenuEntityWrapper> wrapperList = new ArrayList<RiskMenuEntityWrapper>();
		for(RiskMenuEntity menuEntity : menuList){
			RiskMenuEntityWrapper entityWrapper = new RiskMenuEntityWrapper();
			CommonBeanUtils.copyProperties(entityWrapper, menuEntity);
			
			String interfaceName = entityWrapper.getInterfaceName();
			InterfaceEntity interfaceEntity = interfaceEntityMap.get(interfaceName);
			if(interfaceEntity != null){
				//entityWrapper中保留的ID始终是菜单的ID
				Integer menuID = entityWrapper.getId();
				CommonBeanUtils.copyProperties(entityWrapper, interfaceEntity);
				entityWrapper.setId(menuID);	
				
				//接口的ID放在interfaceid中
				entityWrapper.setInterfaceId(Integer.parseInt(interfaceEntity.getId().toString()));
			}
			
			wrapperList.add(entityWrapper);
		}
		
		String jsonStr = TreeGridJsonUtils.parseToTreeJson(wrapperList, "id", "parentId");
		
		outJsonString(response, jsonStr);
	}

	@RequestMapping("/add")
	public void add(HttpServletRequest request, HttpServletResponse response) {
		InterfaceEntity param = new InterfaceEntity();
		bindObject(request, param);

		JsonResult jr = new JsonResult();
		try {
			service.add(param);
			jr.setSuccess(true);
		} catch (Exception e) {
			logger.warn("InterfaceController | add | " + param + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		this.outJsonString(response, jr);
	}
	
	@RequestMapping("/addMenu")
	public void addMenu(HttpServletRequest request, HttpServletResponse response) {
		RiskMenuEntityWrapper entityWrapper = new RiskMenuEntityWrapper();
		bindObject(request, entityWrapper);
		
		JsonResult jr = new JsonResult();
		try {
			RiskMenuEntity parentMenu = riskMenuService.getById(entityWrapper.getParentId());
			riskMenuService.addNotExist(entityWrapper.getCode(), entityWrapper.getName(), entityWrapper.getInterfaceName(), parentMenu);

			jr.setSuccess(true);
			jr.setMessage("成功新增规则菜单");
		} catch (Exception e) {
			logger.warn("InterfaceController | addInterface | " + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		
		outJsonString(response, jr);
	}
	
	@RequestMapping("/addInterface")
	public void addInterface(HttpServletRequest request, HttpServletResponse response) {
		RiskMenuEntityWrapper entityWrapper = new RiskMenuEntityWrapper();
		bindObject(request, entityWrapper);
		
		JsonResult jr = new JsonResult();
		try {
			RiskMenuEntity parentMenu = riskMenuService.getById(entityWrapper.getParentId());
			riskMenuService.addNotExist(entityWrapper.getCode(), entityWrapper.getName(), entityWrapper.getInterfaceName(), parentMenu);
			
			InterfaceEntity interfaceEntity = new InterfaceEntity();
			CommonBeanUtils.copyProperties(interfaceEntity, entityWrapper);
			service.add(interfaceEntity);
			riskMenuService.initRuleNo();
			jr.setSuccess(true);
			jr.setMessage("成功新增规则接口");
		} catch (Exception e) {
			logger.warn("InterfaceController | addInterface | " + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		
		outJsonString(response, jr);
	}
	
	@RequestMapping("/updateMenu")
	public void updateMenu(HttpServletRequest request, HttpServletResponse response) {
		RiskMenuEntityWrapper entityWrapper = new RiskMenuEntityWrapper();
		bindObject(request, entityWrapper);
		
		JsonResult jr = new JsonResult();
		try {
			RiskMenuEntity menuEntity = new RiskMenuEntity();
			CommonBeanUtils.copyProperties(menuEntity, entityWrapper);
			riskMenuService.updateMenu(menuEntity);
			riskMenuService.initRuleNo();
			jr.setSuccess(true);
			jr.setMessage("成功修改规则菜单");
		} catch (Exception e) {
			logger.warn("InterfaceController | updateMenu | " + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		
		outJsonString(response, jr);
	}
	
	@RequestMapping("/updateInterface")
	public void updateInterface(HttpServletRequest request, HttpServletResponse response) {
		RiskMenuEntityWrapper entityWrapper = new RiskMenuEntityWrapper();
		bindObject(request, entityWrapper);
		
		JsonResult jr = new JsonResult();
		try {
			RiskMenuEntity menuEntity = new RiskMenuEntity();
			CommonBeanUtils.copyProperties(menuEntity, entityWrapper);
			riskMenuService.updateMenu(menuEntity);
			
			InterfaceEntity interfaceEntity = new InterfaceEntity();
			CommonBeanUtils.copyProperties(interfaceEntity, entityWrapper);
			interfaceEntity.setId(Long.parseLong(entityWrapper.getInterfaceId().toString()));
			
			interfaceEntity.setUpdateTime(new Date());
			
			service.save(interfaceEntity);
			
			jr.setSuccess(true);
			jr.setMessage("成功修改规则接口");
		} catch (Exception e) {
			logger.warn("InterfaceController | updateInterface | " + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		
		outJsonString(response, jr);
	}
	
	@RequestMapping("/delete")
	public void delete(HttpServletRequest request, HttpServletResponse response) {
		RiskMenuEntityWrapper entityWrapper = new RiskMenuEntityWrapper();
		bindObject(request, entityWrapper);
		
		JsonResult jr = new JsonResult();
		try {
			List<RiskMenuEntity> menuEntityList = riskMenuService.getMenuListByParentId(entityWrapper.getId());
			if(menuEntityList !=null || menuEntityList.size()>0)
				throw new Exception("该菜单包含下级，请先删除所有下级！");
			
			RiskMenuEntity menu = riskMenuService.getById(entityWrapper.getId());
			riskMenuService.deleteMenu(menu.getId());
			
			String interfaceName = menu.getInterfaceName();
			if(StringUtils.isNotBlank(interfaceName))
				service.deleteByInterface(interfaceName);
						
			jr.setSuccess(true);
		} catch (Exception e) {
			logger.warn("InterfaceController | delete | " + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		
		outJsonString(response, jr);
	}

	@RequestMapping("/modify")
	public void modify(HttpServletRequest request, HttpServletResponse response) {
		InterfaceEntity param = new InterfaceEntity();
		bindObject(request, param);

		JsonResult jr = new JsonResult();
		try {
			service.modify(param);
			jr.setSuccess(true);
		} catch (Exception e) {
			logger.warn("InterfaceController | modify | " + param + " | error:"
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}
		this.outJsonString(response, jr);
	}

	@RequestMapping("/import")
	public void importList(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jr = new JsonResult();
		try {
			Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
			JSONObject user = (JSONObject) JSON.toJSON(obj);
			String erp = (String) user.get(Constants.ERP_LOGIN_NAME);
			List<RiskInterface> list = processor.findInterfaces();
			if (list == null) {
				jr.setSuccess(false);
				jr.setMessage("-2:没有可导入的接口。");
				this.outJsonString(response, jr);
			}

			int sum = 0;
			int status = 0;
			for (RiskInterface r : list) {
				sum += service.addByInterface(r.getInterfaceName(),
						r.getDescription());
				rulesService.addFromRiskInterface(r, erp);
//				monitor.runCommand("notifyChanged", r.getInterfaceName());
			}
			jr.setSuccess(true);
			jr.setMessage("0:导入数" + sum + "。");
			this.outJsonString(response, jr);
		} catch (Exception e) {
			logger.error("importList", e);
			jr.setSuccess(false);
			jr.setMessage("-3:执行异常");
			this.outJsonString(response, jr);
		}
	}

	@RequestMapping("/notify")
	@ResponseBody
	public String notify(String name) {
//		return String.valueOf(monitor.runCommand("notifyChanged", name));
		return "OK";
	}

	public String save(InterfaceEntity entity) {
		return "";
	}

	public String disable(long id) {
		return "";
	}

	@RequestMapping("/InterfaceEnum")
	public void getInterfaceEnum(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jr = new JsonResult();
		List<InterfaceEntity> list = service.findList();
		List<EnumInfo> enumList = new ArrayList<EnumInfo>();
		for (InterfaceEntity interfaceEntity : list) {
			EnumInfo enumInfo_ = new EnumInfo();
			enumInfo_.setCode(interfaceEntity.getInterfaceName());
			enumInfo_.setName(interfaceEntity.getDescription());
			enumList.add(enumInfo_);
		}
		jr.setList(enumList);
		jr.setTotalSize(enumList.size());
		jr.setSuccess(true);
		this.outJsonString(response, jr);

	}

	@RequestMapping("/findLog")
	@ResponseBody
	public String findLog(HttpServletRequest request) {
		String level = request.getParameter("level");
		String date = request.getParameter("date");
		String maxline = request.getParameter("maxline");
		String key = request.getParameter("key");
		return "OK2";
//		return monitor.findLog(null, level, date, maxline == null ? 100
//				: Integer.parseInt(maxline), key);

	}
	
	/**
	 * 规则接口日志字段列表
	 * @param interfaceName
	 * @return
	 */
	@RequestMapping("/logFields")
	public String logFields() {
		return "interface/log_fields";
	}
	
	/**
	 * 规则接口日志字段列表
	 * @param interfaceName
	 * @return
	 */
	@RequestMapping("/logFieldList.json")
	public void logFieldList(String interfaceName, HttpServletRequest request,
			HttpServletResponse response) {
		List<InterfaceLogFieldEntity> list = interfaceLogFieldService.findByInterfaceName(interfaceName);
//		if (list !=null && list.size()>0) {
		List<MapEntity> map = logFieldsConvertToMap(list);
		
		this.outJsonString(response, map);
//		} else {
//			outJsonString(response, StringUtils.EMPTY);
//		}
	}

	private List<MapEntity> logFieldsConvertToMap(
			List<InterfaceLogFieldEntity> list) {
		if (list == null || list.size() < 1)
			return null;
		
		List<MapEntity> map = new ArrayList();
		String name = null;
		String fields = null;
		for (InterfaceLogFieldEntity e : list) {
			if (name == null) {
				name = e.getInterfaceName();
				fields = "";
			}
			if (!name.equals(e.getInterfaceName())) {
				map.add(new MapEntity(name, fields.substring(0, fields.length() - 1)));
				
				name = e.getInterfaceName();
				fields = "";
			}
			
			fields += e.getFieldDescription() + ",";
		}
		
		map.add(new MapEntity(name, fields.substring(0, fields.length() - 1)));
		
		return map;
	}
	
	@RequestMapping("/detailFields.json")
	public void detailFields(String interfaceName, HttpServletRequest request,
			HttpServletResponse response) {
		List<InterfaceLogFieldEntity> list = interfaceLogFieldService.findByInterfaceName(interfaceName);
		List<MapEntity> map = logFieldsConvertToIdMap(list);
		if (list !=null && list.size()>0){
			this.outJsonString(response, map.get(0));
		} else {
			outJsonString(response, StringUtils.EMPTY);
		}
	}
	
	private List<MapEntity> logFieldsConvertToIdMap(
			List<InterfaceLogFieldEntity> list) {
		if (list == null || list.size() < 1)
			return null;
		
		List<MapEntity> map = new ArrayList();
		String name = null;
		String fields = null;
		for (InterfaceLogFieldEntity e : list) {
			if (name == null) {
				name = e.getInterfaceName();
				fields = "";
			}
			if (!name.equals(e.getInterfaceName())) {
				map.add(new MapEntity(name, fields.substring(0, fields.length() - 1)));
				
				name = e.getInterfaceName();
				fields = "";
			}
			
			fields += e.getFieldId() + ",";
		}
		
		map.add(new MapEntity(name, fields.substring(0, fields.length() - 1)));
		
		return map;
	}

	@RequestMapping("/saveFields")
	@ResponseBody
	public String saveFields(String interfaceName, String fields, HttpServletRequest request,
			HttpServletResponse response) {
		if (interfaceName == null || interfaceName.equals("") || fields == null || fields.equals(""))
			return "-1";
		
		String[] ss = fields.split(",");
		if (!interfaceLogFieldService.save(interfaceName, ss))
			return "-2";
		
		return "0";
	}
	
	@RequestMapping("/getInterfaceStateEnum")
	public void getInterfaceStateEnum( HttpServletRequest request, HttpServletResponse response){
		  EnumInfo param = new EnumInfo();
	      bindObject(request, param);

	      List<EnumInfo> list = new ArrayList<EnumInfo>();
	      try {
	           Map map = EnumFactory.getEnumMap(param.getName(), param.getType());
	           Set set = map.keySet();
	           Iterator it = set.iterator();
	           while (it.hasNext()) {
	               EnumInfo enumInfo_ = new EnumInfo();
	               Object obj = it.next();
	                   if (obj != null && !"NULL".equals(obj) && map.get(obj)!=null) {
	                       enumInfo_.setCode(obj.toString());
	                       enumInfo_.setName(map.get(obj).toString());
	                       list.add(enumInfo_);
	                }
	            }
	           
	           outJsonString(response, list);
	        }catch(Exception ex){
	        	throw new  RuntimeException(ex.getMessage());
	        }
	}
}
