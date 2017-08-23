package com.google.code.simplerule.proxy.risk.dao;

import java.util.HashMap;
import java.util.List;
import org.springframework.stereotype.Service;
import com.google.code.simplerule.proxy.risk.entity.RuleHandlerEntity;

@Service
public class RuleHandlerDao extends RiskSqlSessionDaoSupport {
	public List<RuleHandlerEntity> findByRuleId(long ruleId) {
		return this.getSqlSession().selectList("RuleHandlerMapper.findByRuleId", ruleId);
	}
	
	public RuleHandlerEntity getById(int id) {
		return this.getSqlSession().selectOne("RuleHandlerMapper.getById", id);
	}

	public void insert(RuleHandlerEntity entity) {
		this.getSqlSession().insert("RuleHandlerMapper.insert", entity);
	}
	
	public void delete(long id) {
		this.getSqlSession().delete("RuleHandlerMapper.delete", id);
	}
	
	public void deleteByRuleId(long ruleId) {
		this.getSqlSession().delete("RuleHandlerMapper.deleteByRuleId", ruleId);
	}
}
