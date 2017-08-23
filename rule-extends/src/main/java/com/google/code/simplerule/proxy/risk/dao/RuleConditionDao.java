package com.google.code.simplerule.proxy.risk.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;
import com.google.code.simplerule.proxy.risk.entity.RuleConditionEntity;

@Service
public class RuleConditionDao extends RiskSqlSessionDaoSupport {
	public List<RuleConditionEntity> findByRuleId(long ruleId) {
		return this.getSqlSession().selectList("RuleConditionMapper.findByRuleId", ruleId);
	}
	
	public RuleConditionEntity getById(int id) {
		return this.getSqlSession().selectOne("RuleConditionMapper.getById", id);
	}

	public void insert(RuleConditionEntity entity) {
		this.getSqlSession().insert("RuleConditionMapper.insert", entity);
	}
	
	public void delete(long id) {
		this.getSqlSession().delete("RuleConditionMapper.delete", id);
	}
	
	public void deleteByRuleId(long ruleId) {
		this.getSqlSession().delete("RuleConditionMapper.deleteByRuleId", ruleId);
	}
}
