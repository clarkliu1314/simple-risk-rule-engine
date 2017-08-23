package com.google.code.simplerule.proxy.risk.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.SystemEntity;

import java.util.List;

@Service
public class SystemDao extends RiskSqlSessionDaoSupport {

	public SystemEntity getByName(String systemName) {
		return this.getSqlSession().selectOne("SystemMapper.getByName", systemName);
	}

	public void insert(SystemEntity e) {
		this.getSqlSession().insert("SystemMapper.insert", e);
	}

    public List<SystemEntity> selectAll(){
        return this.getSqlSession().selectList("SystemMapper.selectAll");
    }

}
