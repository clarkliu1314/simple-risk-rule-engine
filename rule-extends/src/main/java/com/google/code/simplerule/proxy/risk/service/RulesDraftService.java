package com.google.code.simplerule.proxy.risk.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.ConditionalOperator;
import com.google.code.simplerule.core.rule.RiskFactor;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.proxy.risk.dao.RuleConditionDao;
import com.google.code.simplerule.proxy.risk.dao.RuleConditionDraftDao;
import com.google.code.simplerule.proxy.risk.dao.RuleFieldDao;
import com.google.code.simplerule.proxy.risk.dao.RuleHandlerDao;
import com.google.code.simplerule.proxy.risk.dao.RuleHandlerDraftDao;
import com.google.code.simplerule.proxy.risk.dao.RulesDao;
import com.google.code.simplerule.proxy.risk.dao.RulesDraftDao;
import com.google.code.simplerule.proxy.risk.entity.FactorEntity;
import com.google.code.simplerule.proxy.risk.entity.OperatorEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleConditionEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleFieldEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleHandlerEntity;
import com.google.code.simplerule.proxy.risk.entity.RulesEntity;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;

@Service
public class RulesDraftService {

	@Autowired
	private RulesDraftDao rulesDraftDao;
	@Autowired
	private RulesDao rulesDao;
	@Autowired
    private RuleFieldDao ruleFieldDao;
	@Autowired
	private RuleConditionDraftDao ruleConditionDraftDao;
	@Autowired
	private RuleConditionDao rulesCondtionDao;
	@Autowired
	private RuleHandlerDao rulesHandlerDao;
	@Autowired
	private RuleHandlerDraftDao ruleHandlerDraftDao;

	public long updateAndSave(RulesEntity rule,
			List<RuleConditionEntity> conditions,
			List<RuleHandlerEntity> handlers) {
		if (rule == null)
			return 0;
		RulesEntity re = rulesDao.findByNo(rule.getNo());
		long c = 0;
		if (re != null) {
			if(rule.getOperateType() == null)
			rule.setOperateType(2);
			rule.setAuditStatus(0);
			rulesDraftDao.deleteByNo(rule.getNo());
			c = rulesDraftDao.insert(rule);
		} else {
			rule.setOperateType(0);
			rule.setAuditStatus(0);
			rulesDraftDao.deleteByNo(rule.getNo());
			c = rulesDraftDao.insert(rule);
		}
		cleanRule(rule.getId());
		for (RuleConditionEntity ruleConditionEntity : conditions) {
			ruleConditionEntity.setRuleId(rule.getId());
			ruleConditionDraftDao.insert(ruleConditionEntity);
		}
		for (RuleHandlerEntity ruleHandlerEntity : handlers) {
			ruleHandlerEntity.setRuleId(rule.getId());
			ruleHandlerDraftDao.insert(ruleHandlerEntity);
		}
		return c;
	}

	public List<RulesEntity> findByInterfaceName(String interfaceName) {
		return rulesDraftDao.findByInterfaceName(interfaceName);
	}

	public RulesEntity findByRuleId(Long ruleId) {
		return rulesDraftDao.findByRuleId(ruleId);
	}

	public List<RuleConditionEntity> findRuleConditionByRuleId(long ruleId) {
		List<RuleConditionEntity> list = ruleConditionDraftDao
				.findByRuleId(ruleId);
		for (RuleConditionEntity ruleConditionEntity : list) {
			System.out.println(ruleConditionEntity.getExternalParam());
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

	public List<RuleHandlerEntity> findRuleHandlerByRuleId(long ruleId) {
		List<RuleHandlerEntity> list = ruleHandlerDraftDao.findByRuleId(ruleId);
		list = ruleHandlerDraftDao.findByRuleId(ruleId);
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

	public RulesEntity findByNo(String rulesNo) {
		return rulesDraftDao.findByNo(rulesNo);
	}

	public void auditThroughNewOrUpdate(String rulesNo, String operator) {
		RulesEntity rule = rulesDraftDao.findByNo(rulesNo);
		if (rule == null)
			return;
		Long ruleId = rule.getId();
		List<RuleConditionEntity> conditions = findRuleConditionByRuleId(rule
				.getId());
		List<RuleHandlerEntity> handlers = findRuleHandlerByRuleId(rule.getId());
		RulesEntity r = rulesDao.findByNo(rulesNo);
		if (r == null) {
			rulesDao.insert(rule);
		} else {
			rulesDao.delete(r.getId());
			rulesCondtionDao.deleteByRuleId(r.getId());
			rulesHandlerDao.deleteByRuleId(r.getId());
			rulesDao.insert(rule);
		}
		for (RuleConditionEntity ruleConditionEntity : conditions) {
			if(ruleConditionEntity.getExternalParam() != null)
			ruleConditionEntity.setExternalParam(ruleConditionEntity.getExternalParam().split(":")[0]);
			ruleConditionEntity.setRuleId(rule.getId());
			rulesCondtionDao.insert(ruleConditionEntity);
		}
		for (RuleHandlerEntity ruleHandlerEntity : handlers) {
			ruleHandlerEntity.setRuleId(rule.getId());
			rulesHandlerDao.insert(ruleHandlerEntity);
		}
		rulesDraftDao.delete(ruleId);

	}

	public void auditThroughDelete(String rulesNo, String operator) {
		RulesEntity rule = rulesDraftDao.findByNo(rulesNo);
		if (rule == null)
			return;
		RulesEntity r = rulesDao.findByNo(rulesNo);
		rulesDao.delete(r.getId());
		rulesCondtionDao.deleteByRuleId(r.getId());
		rulesHandlerDao.deleteByRuleId(r.getId());
		rulesDraftDao.deleteByNo(rulesNo);
	}

	public void auditRefuse(String rulesNo, String operator) {
		RulesEntity rule = rulesDraftDao.findByNo(rulesNo);
		if (rule == null)
			return;
		if (rule.getOperateType() == 0)
		rulesDao.deleteByNo(rulesNo);
		rulesDraftDao.deleteByNo(rulesNo);
	}

	public void refuseAndEdit(String rulesNo, String reason ,String operator) {
		RulesEntity rule = rulesDraftDao.findByNo(rulesNo);
		if (rule == null)
			return;
		rule.setAuditStatus(2);
		rule.setOperator(operator);
		rule.setRefuseReason(reason);
		rulesDraftDao.update(rule);
	}
	
	public void cleanRule(long ruleId) {
		ruleConditionDraftDao.deleteByRuleId(ruleId);
		ruleHandlerDraftDao.deleteByRuleId(ruleId);
	}

}
