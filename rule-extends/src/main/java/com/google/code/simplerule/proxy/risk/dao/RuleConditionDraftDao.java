package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.RuleConditionEntity;

@Service
public class RuleConditionDraftDao extends RiskSqlSessionDaoSupport {
	public List<RuleConditionEntity> findByRuleId(long ruleId) {
		return this.getSqlSession().selectList("RuleConditionDraftMapper.findByRuleId", ruleId);
	}
	
	public RuleConditionEntity getById(int id) {
		return this.getSqlSession().selectOne("RuleConditionDraftMapper.getById", id);
	}

	public void insert(RuleConditionEntity entity) {
		this.getSqlSession().insert("RuleConditionDraftMapper.insert", entity);
	}
	
	public void delete(long id) {
		this.getSqlSession().delete("RuleConditionDraftMapper.delete", id);
	}
	
	public void deleteByRuleId(long ruleId) {
		this.getSqlSession().delete("RuleConditionDraftMapper.deleteByRuleId", ruleId);
	}
}
