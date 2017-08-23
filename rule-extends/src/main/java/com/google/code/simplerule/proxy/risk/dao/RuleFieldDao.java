package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.entity.RuleFieldEntity;

@Service
public class RuleFieldDao extends RiskSqlSessionDaoSupport {
	public void insert(RuleFieldEntity e) {
		this.getSqlSession().insert("RuleFieldMapper.insert", e);
	}

	public void update(RuleFieldEntity e) {
		this.getSqlSession().update("RuleFieldMapper.update", e);
	}
	
	public List<RuleFieldEntity> queryLimit(Map<String,Object> paramMap){
		return this.getSqlSession().selectList("RuleFieldMapper.queryLimit",paramMap);
	}
	
	public RuleFieldEntity queryOne(Map<String,Object> paramMap){
		return this.getSqlSession().selectOne("RuleFieldMapper.queryLimit",paramMap);
	}
	
	public int queryCount(Map<String,Object> paramMap){
		return (Integer)this.getSqlSession().selectOne("RuleFieldMapper.getCount",paramMap);
		
	}

	public int delete(Map<String,Object> paramMap) {
		return (Integer) this.getSqlSession().delete("RuleFieldMapper.deleteById", paramMap);
	}
	
	public List<RuleFieldEntity> queryCategories(){
		return this.getSqlSession().selectList("RuleFieldMapper.getCategories");
		
	}
	
	public List<RuleFieldEntity> queryByCategory(Map<String,Object> paramMap){
		return this.getSqlSession().selectList("RuleFieldMapper.getByCategory",paramMap);
	}

	public List<RuleFieldEntity> listOrderByCategory() {
		return this.getSqlSession().selectList("RuleFieldMapper.listOrderByCategory", null);
	}

	public RuleFieldEntity queryById(long id){
		return this.getSqlSession().selectOne("RuleFieldMapper.queryById", id);
	}
}
