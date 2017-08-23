package com.google.code.simplerule.proxy.risk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RiskInterface;
import com.google.code.simplerule.core.rule.RiskRule;
import com.google.code.simplerule.core.rule.RuleCondition;
import com.google.code.simplerule.core.rule.condition.FactorCondition;
import com.google.code.simplerule.proxy.risk.dao.RuleConditionDao;
import com.google.code.simplerule.proxy.risk.dao.RuleConditionDraftDao;
import com.google.code.simplerule.proxy.risk.dao.RuleFieldDao;
import com.google.code.simplerule.proxy.risk.dao.RuleHandlerDao;
import com.google.code.simplerule.proxy.risk.dao.RuleHandlerDraftDao;
import com.google.code.simplerule.proxy.risk.dao.RulesDao;
import com.google.code.simplerule.proxy.risk.dao.RulesDraftDao;
import com.google.code.simplerule.proxy.risk.entity.FactorEntity;
import com.google.code.simplerule.proxy.risk.entity.FactorFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.OperatorEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleConditionEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleHandlerEntity;
import com.google.code.simplerule.proxy.risk.entity.RulesEntity;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;
import com.google.code.simplerule.proxy.risk.entity.common.StringFormatUtil;
import com.google.code.simplerule.redis.queue.RedisQueue;

@Service
public class RulesService {
	@Autowired
	private RulesDao rulesDao;
	@Autowired
	private RulesDraftDao rulesDraftDao;
	@Autowired
    private RuleFieldDao ruleFieldDao;
	@Autowired
	private RuleConditionDao ruleConditionDao;
	@Autowired
	private RuleConditionDraftDao ruleConditionDraftDao;
	@Autowired
	private RuleHandlerDao ruleHandlerDao;
	@Autowired
	private RuleHandlerDraftDao ruleHandlerDraftDao;
	@Resource
	private RedisQueue redisQueue;

	public boolean addFromRiskInterface(RiskInterface ri, String erp) {
		if (!ri.isAvailable())
			return false;

		List<RulesEntity> list = rulesDao.findByInterfaceName(ri
				.getInterfaceName());
		if (list != null && list.size() > 0)
			return false;

		int count = ri.getRules().size();
		for (int i = 0; i < count; i++) {
			RiskRule r = ri.getRules().get(i);
			RulesEntity re = new RulesEntity();
			if (r.getName() != null && !r.getName().equals("")) {
				re.setName(r.getName());
			} else {
				re.setName(getDefaultName(ri, i));
			}
			re.setLevel(r.getLevel());
			re.setInterfaceName(ri.getInterfaceName());
			re.setNo(getRuleNumber(ri.getInterfaceName()));
			re.setPriority(1);
			re.setStatus(1);
			re.setCreatePerson(erp);
			long ruleId = rulesDao.insert(re);
			if (ruleId < 0)
				return false;

			addRuleConditions(r.getConditionChain(), ruleId);
			addRuleHandlers(r.getHandlerChain(), ruleId);
		}

		return true;
	}

	private void addRuleHandlers(List<RiskHandler> handlers, long ruleId) {
		for (RiskHandler rc : handlers) {
			RuleHandlerEntity he = new RuleHandlerEntity();
			he.setRuleId(ruleId);
			he.setCommand(rc.getClass().getName());
			he.setCommandValue(getCommandValue(rc));

			ruleHandlerDao.insert(he);
		}
	}

	private String getCommandValue(RiskHandler rc) {
		Object[] objs = rc.getArguments();
		if (objs == null || objs.length < 1)
			return "";
		String str = "";
		for (Object obj : objs) {
			str += obj.toString().replace("/", "/`") + ",";
		}
		return str;
	}

	private void addRuleConditions(List<RuleCondition> conditions, long ruleId) {
		for (RuleCondition rc : conditions) {
			if (!rc.getClass().equals(FactorCondition.class))
				continue;

			FactorCondition fc = (FactorCondition) rc;
			RuleConditionEntity ce = new RuleConditionEntity();
			ce.setCheckCondition(fc.getConditionalOperator().getClass()
					.getName());
			ce.setCheckValue(fc.getValue().toString());
			ce.setRiskFactor(fc.getFactor().getClass().getName());
			if(fc.getFactor().getRiskConvertor() != null)
				ce.setRiskConvertor(fc.getFactor().getRiskConvertor().getClass().getName());
			ce.setRuleId(ruleId);
			ce.setConnector(fc.getConnector());
			ce.setRiskFactorParam(getRiskFactorParams(fc.getFactor()));

			ruleConditionDao.insert(ce);
		}
	}

	private String getRiskFactorParams(RiskFactor f) {
		Object[] objs = f.getInternalValues();
		if (objs == null || objs.length < 1)
			return null;
		String str = "";
		for (Object obj : objs) {
			str += obj.toString() + ",";
		}
		if (str.length() > 0) {
			str = str.substring(0, str.length() - 1);
		}
		return str;
	}

	private String getDefaultName(RiskInterface ri, int i) {
		String name = ri.getInterfaceName();
		int index = name.lastIndexOf("\\.");
		if (index > -1) {
			name = name.substring(index + 1);
		}

		return name + (i + 1);
	}

	public long addRules(RulesEntity entity) {
		return rulesDao.insert(entity);
	}

	public List<RulesEntity> findByInterfaceName(String interfaceName) {
		return rulesDao.findByInterfaceName(interfaceName);
	}

	public List<RulesEntity> findByInterfaceNameforAudit(String interfaceName,
			String erp) {
		List<RulesEntity> list = rulesDao.findByInterfaceName(interfaceName);
		for (RulesEntity rulesEntity : list) {
			RulesEntity rules = rulesDraftDao.findByNo(rulesEntity.getNo());
			if (rules != null) {
				if (rules.getAuditStatus() == 0 || rules.getAuditStatus() == 2)
					BeanUtils.copyProperties(rules, rulesEntity);
			}
			if (erp.equalsIgnoreCase(rulesEntity.getCreatePerson()))
				rulesEntity.setIsowner(true);

		}
	/*	Map<String, Object> param = new HashMap<String, Object>();
		param.put("interfaceName", interfaceName);
		param.put("operateType", 0);
		param.put("auditStatus", 0);
		List<RulesEntity> hl = rulesDraftDao
				.findByInterfaceNameAndOprType(param);
		for (RulesEntity rulesEntity : hl) {
			if (rulesEntity.getCreatePerson().equalsIgnoreCase(erp))
				rulesEntity.setIsowner(true);
		}
		list.addAll(hl);*/
		return list;

	}

	public RulesEntity findByRuleId(Long ruleId) {
		return rulesDao.findByRuleId(ruleId);
	}

	public List<RuleConditionEntity> findRuleConditionByRuleId(long ruleId) {
		List<RuleConditionEntity> list = ruleConditionDao.findByRuleId(ruleId);
		for (RuleConditionEntity ruleConditionEntity : list) {
			if(ruleConditionEntity.getExternalParam() != null){
			String[] fieldIds = ruleConditionEntity.getExternalParam().split(",");
			String fieldsDes = "";
			for (String id : fieldIds) {
				RuleFieldEntity field =	ruleFieldDao.queryById(Long.parseLong(id));
				fieldsDes += field.getDescription() + ",";
			}
			fieldsDes = ruleConditionEntity.getExternalParam()+":"+fieldsDes;
			ruleConditionEntity.setExternalParam(fieldsDes.substring(0,fieldsDes.length()-1));
			}
			RiskFactor factor = (RiskFactor) ObjectUtils
					.loadClass(ruleConditionEntity.getRiskFactor());
			ConditionalOperator operator = (ConditionalOperator) ObjectUtils
					.loadClass(ruleConditionEntity.getCheckCondition());
			ruleConditionEntity.setRiskFactorDescription(factor.getName());
			ruleConditionEntity.setCheckConditionDescription(operator
					.getDescription());
			ruleConditionEntity.setResultEnumValue(factor.getResult().getValueEnum());
			if (factor.getInternalArguments() != null)
				if (factor.getInternalArguments().length > 0)
					ruleConditionEntity.setContainParam(true);
		}
		return list;
	}

	public List<FactorEntity> getFactorList(List<RiskFactor> list) {
		List<FactorEntity> result = new ArrayList<FactorEntity>();
		for (RiskFactor riskFactor : list) {
			FactorEntity entity = new FactorEntity();
			entity.setClassName(riskFactor.getClass().getName());
			entity.setName(riskFactor.getName());
			result.add(entity);
		}

		return result;
	}

	public List<List<OperatorEntity>> getConditionList(List<RiskFactor> list) {
		List<List<OperatorEntity>> result = new ArrayList<List<OperatorEntity>>();
		for (RiskFactor riskFactor : list) {
			ConditionalOperator[] arr = riskFactor.supportedOperators();
			List<OperatorEntity> el = new ArrayList<OperatorEntity>();
			for (ConditionalOperator conditionalOperator : arr) {
				OperatorEntity operator = new OperatorEntity();
				operator.setName(conditionalOperator.getName());
				operator.setClassName(conditionalOperator.getClass().getName());
				operator.setDescription(conditionalOperator.getDescription());
				el.add(operator);
			}
			result.add(el);
		}
		return result;
	}

	public List<RiskFactor> getFactorsByConditions(
			List<RuleConditionEntity> conditions) {
		List<RiskFactor> result = new ArrayList<RiskFactor>();
		for (RuleConditionEntity ruleConditionEntity : conditions) {
			RiskFactor factor = (RiskFactor) ObjectUtils
					.loadClass(ruleConditionEntity.getRiskFactor());
			result.add(factor);
		}
		return result;
	}

	public List<OperatorEntity> getOperatorsByFactor(String className) {
		if (StringUtils.isBlank(className))
			return null;
		RiskFactor factor = (RiskFactor) ObjectUtils.loadClass(className);
		ConditionalOperator[] arr = factor.supportedOperators();
		List<OperatorEntity> result = new ArrayList<OperatorEntity>();
		for (ConditionalOperator conditionalOperator : arr) {
			OperatorEntity operator = new OperatorEntity();
			operator.setName(conditionalOperator.getName());
			operator.setClassName(conditionalOperator.getClass().getName());
			operator.setDescription(conditionalOperator.getDescription());
			result.add(operator);
		}
		return result;
	}

	public List<FactorFieldEntity> getParamsByFactor(String className) {

		if (StringUtils.isBlank(className))
			return null;
		List<FactorFieldEntity> result = new ArrayList<FactorFieldEntity>();
		RiskFactor factor = (RiskFactor) ObjectUtils.loadClass(className);
		FactorField[] params = factor.getInternalArguments();
		if (params != null)
			for (FactorField factorField : params) {
				FactorFieldEntity entity = new FactorFieldEntity();
				entity.setName(factorField.getName());
				entity.setDescription(factorField.getDescription());
				entity.setType(factorField.getType().getName());
				result.add(entity);
			}
		return result;

	}

	public List<RuleHandlerEntity> findRuleHandlerByRuleId(long ruleId) {
		return ruleHandlerDao.findByRuleId(ruleId);
	}

	public List<RuleHandlerEntity> getRuleHandlerList(List<RiskHandler> handlers) {
		List<RuleHandlerEntity> result = new ArrayList<RuleHandlerEntity>();
		for (RiskHandler riskHandler : handlers) {
			RuleHandlerEntity entity = new RuleHandlerEntity();
			entity.setCommand(riskHandler.getClass().getName());
			entity.setDescription(riskHandler.getName());
			result.add(entity);
		}

		return result;
	}

	public void cleanRule(long ruleId) {
		ruleConditionDao.deleteByRuleId(ruleId);
		ruleHandlerDao.deleteByRuleId(ruleId);
	}

	public long updateAndSave(RulesEntity rule,
			List<RuleConditionEntity> conditions,
			List<RuleHandlerEntity> handlers) {
		RulesEntity re = rulesDao.findByRuleId(rule.getId());
		long c = 0;
		if (re != null)
			c = rulesDao.update(rule);
		else {
			c = rulesDao.insert(rule);
		}
		cleanRule(rule.getId());
		for (RuleConditionEntity ruleConditionEntity : conditions) {
			ruleConditionEntity.setRuleId(rule.getId());
			ruleConditionDao.insert(ruleConditionEntity);
		}
		for (RuleHandlerEntity ruleHandlerEntity : handlers) {
			ruleHandlerEntity.setRuleId(rule.getId());
			ruleHandlerDao.insert(ruleHandlerEntity);
		}
		return c;
	}

	public String getRuleNumber(String interfaceName) {
		Jedis jedis = redisQueue.getJedisPool().getResource();
		String noStr = "";
		if (StringUtils.isBlank(interfaceName))
			return null;
		String[] arr = interfaceName.split("\\.");
		try {
			for (String string : arr) {
				noStr += jedis.get(StringFormatUtil.encode("MENU"+string));
			}
			redisQueue.getJedisPool().returnResource(jedis);
		} catch (Exception e) {
			if (jedis != null)
				redisQueue.getJedisPool().returnBrokenResource(jedis);
		}
		redisQueue.getJedisPool().returnResource(jedis);
		String lastNo = rulesDao.findLastNoByInterfaceName(interfaceName);
		String draftNo = rulesDraftDao.findLastNoByInterfaceName(interfaceName);
		if (!StringUtils.isBlank(lastNo) && !StringUtils.isBlank(draftNo))
			if (Integer.parseInt(draftNo.substring(draftNo.length() - 4,
					lastNo.length())) > Integer.parseInt(lastNo.substring(
					lastNo.length() - 4, lastNo.length()))) {
				noStr += StringFormatUtil.formatNextRuleNo(draftNo.substring(
						draftNo.length() - 4, draftNo.length()));
				return noStr;
			}
		if (StringUtils.isBlank(lastNo)) {
			noStr += "0001";
			return noStr;
		}

		noStr += StringFormatUtil.formatNextRuleNo(lastNo.substring(
				lastNo.length() - 4, lastNo.length()));
		return noStr;

	}

	public void deleteRulesByIdForAudit(String ruleNo) {
		RulesEntity entity = rulesDao.findByNo(ruleNo);
		if (entity == null)
			return;
		List<RuleConditionEntity> conditions = ruleConditionDao
				.findByRuleId(entity.getId());
		List<RuleHandlerEntity> handlers = ruleHandlerDao.findByRuleId(entity
				.getId());
		RulesEntity draftRule = rulesDraftDao.findByNo(ruleNo);
		if (draftRule != null) {
			rulesDraftDao.deleteByNo(ruleNo);
			ruleConditionDraftDao.deleteByRuleId(draftRule.getId());
			ruleHandlerDraftDao.deleteByRuleId(draftRule.getId());
		}
		entity.setAuditStatus(0);
		entity.setOperateType(1);
		rulesDraftDao.insert(entity);
		for (RuleConditionEntity ruleConditionEntity : conditions) {
			ruleConditionEntity.setRuleId(entity.getId());
			ruleConditionDraftDao.insert(ruleConditionEntity);
		}
		for (RuleHandlerEntity ruleHandlerEntity : handlers) {
			ruleHandlerEntity.setRuleId(entity.getId());
			ruleHandlerDraftDao.insert(ruleHandlerEntity);
		}
	}

}
