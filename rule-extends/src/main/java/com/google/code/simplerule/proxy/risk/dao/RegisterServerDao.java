package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.RegisterServerEntity;

@Service
public class RegisterServerDao extends RiskSqlSessionDaoSupport {
	public List<RegisterServerEntity> findAll() {
		return this.getSqlSession().selectList("RegisterServerMapper.selectAll");
	}
	
	public RegisterServerEntity getByName(String name) {
		return this.getSqlSession().selectOne("RegisterServerMapper.getByName", name);
	}
	
	public void save(RegisterServerEntity e) {
		if (e.getId() == null) {
			this.getSqlSession().insert("RegisterServerMapper.insert", e);
		}
		else {
			this.getSqlSession().update("RegisterServerMapper.update", e);
		}
	}
}
