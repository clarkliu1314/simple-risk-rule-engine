package com.google.code.simplerule.proxy.risk.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.BlackListEntity;

@Service
public class BlackListDao extends RiskSqlSessionDaoSupport {

	public List<BlackListEntity> search(String interfaceName, String type, String value) {
		BlackListEntity entity = new BlackListEntity();
		entity.setInterfaceName(interfaceName);
		entity.setType(type);
		entity.setValue(value);
		entity.setStatus(1);
		return this.getSqlSession().selectList("BlackListMapper.search", entity);
	}

	public List<BlackListEntity> query(HashMap<String, Object> paramMap) {
		return this.getSqlSession().selectList("BlackListMapper.query",paramMap);
	}

	public int queryCount(HashMap<String, Object> paramMap) {
		return (Integer)this.getSqlSession().selectOne("BlackListMapper.getCount",paramMap);
	}

	public void update(BlackListEntity entity) {
		this.getSqlSession().update("BlackListMapper.update", entity);
	}

	public void insert(BlackListEntity entity) {
		this.getSqlSession().insert("BlackListMapper.insert", entity);
	}
	
	public BlackListEntity getById(String id){
		return this.getSqlSession().selectOne("BlackListMapper.getById",id);
	}
	
	
}
