package com.google.code.simplerule.proxy.risk.dao;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.code.simplerule.proxy.risk.entity.RuleHandlerEntity;

@Service
public class RuleHandlerDraftDao extends RiskSqlSessionDaoSupport {
	public List<RuleHandlerEntity> findByRuleId(long ruleId) {
		return this.getSqlSession().selectList("RuleHandlerDraftMapper.findByRuleId", ruleId);
	}
	
	public RuleHandlerEntity getById(int id) {
		return this.getSqlSession().selectOne("RuleHandlerDraftMapper.getById", id);
	}

	public void insert(RuleHandlerEntity entity) {
		this.getSqlSession().insert("RuleHandlerDraftMapper.insert", entity);
	}
	
	public void delete(long id) {
		this.getSqlSession().delete("RuleHandlerDraftMapper.delete", id);
	}
	
	public void deleteByRuleId(long ruleId) {
		this.getSqlSession().delete("RuleHandlerDraftMapper.deleteByRuleId", ruleId);
	}
}
