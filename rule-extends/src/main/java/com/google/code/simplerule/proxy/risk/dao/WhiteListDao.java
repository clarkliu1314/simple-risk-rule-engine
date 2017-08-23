package com.google.code.simplerule.proxy.risk.dao;

import java.util.HashMap;
import java.util.List;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.WhiteListEntity;

@Service
public class WhiteListDao extends RiskSqlSessionDaoSupport {


	public List<WhiteListEntity> search(String interfaceName, String type, String value) {
		WhiteListEntity entity = new WhiteListEntity();
		entity.setInterfaceName(interfaceName);
		entity.setType(type);
		entity.setValue(value);
		entity.setStatus(1);
		return this.getSqlSession().selectList("WhiteListMapper.search", entity);
	}

	public List<WhiteListEntity> query(HashMap<String, Object> paramMap) {
		return this.getSqlSession().selectList("WhiteListMapper.query",paramMap);
	}

	public int queryCount(HashMap<String, Object> paramMap) {
		return (Integer)this.getSqlSession().selectOne("WhiteListMapper.getCount",paramMap);
	}

	public void update(WhiteListEntity entity) {
		this.getSqlSession().update("WhiteListMapper.update", entity);
	}

	public void insert(WhiteListEntity entity) {
		this.getSqlSession().insert("WhiteListMapper.insert", entity);
	}
	
	public WhiteListEntity getById(String id){
		return this.getSqlSession().selectOne("WhiteListMapper.getById",id);
	}
	
	

}
