package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.InterfaceEntity;

@Service
public class InterfaceDao extends RiskSqlSessionDaoSupport {

	public InterfaceEntity getByName(String name) {
		return this.getSqlSession().selectOne("InterfaceMapper.getByName", name);
	}

    public List<InterfaceEntity> getListByName(String name) {
        return this.getSqlSession().selectList("InterfaceMapper.getByName", name);
    }

	public void insert(InterfaceEntity e) {
		this.getSqlSession().insert("InterfaceMapper.insert", e);
	}

	public void update(InterfaceEntity e) {
		this.getSqlSession().update("InterfaceMapper.update", e);
	}
	
	public void deleteByInterface(String interfaceName) {
		this.getSqlSession().update("InterfaceMapper.deleteByInterface", interfaceName);
	}
	
	public List<InterfaceEntity> queryLimit(Map<String,Object> paramMap){
		return this.getSqlSession().selectList("InterfaceMapper.queryLimit",paramMap);
	}
	
	public int queryCount(Map<String,Object> paramMap){
		return (Integer)this.getSqlSession().selectOne("InterfaceMapper.getCount",paramMap);
		
	}

	public List<InterfaceEntity> findAll() {
		return this.getSqlSession().selectList("InterfaceMapper.selectAll");
	}
	

}
