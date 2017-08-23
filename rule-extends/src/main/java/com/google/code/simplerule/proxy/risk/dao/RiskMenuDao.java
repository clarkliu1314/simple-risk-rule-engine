package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;

@Service
public class RiskMenuDao extends RiskSqlSessionDaoSupport {
	public List<RiskMenuEntity> findAll() {
		return this.getSqlSession().selectList("RiskMenuMapper.selectAll");
	}
	
	public RiskMenuEntity getById(int id) {
		return this.getSqlSession().selectOne("RiskMenuMapper.getById", id);
	}
	
	public RiskMenuEntity getByCode(String code) {
		return this.getSqlSession().selectOne("RiskMenuMapper.getByCode", code);
	}

	public void update(RiskMenuEntity entity) {
		this.getSqlSession().update("RiskMenuMapper.update", entity);
	}

	public void insert(RiskMenuEntity entity) {
		this.getSqlSession().insert("RiskMenuMapper.insert", entity);
	}
	
	public void delete(Integer menuID) {
		this.getSqlSession().insert("RiskMenuMapper.delete", menuID);
	}
	
	public List<RiskMenuEntity> getRootMenu(){
	return this.getSqlSession().selectList("RiskMenuMapper.getRootMenuList");
	}
	
	public List<RiskMenuEntity> getByParentId(Integer parentId){
		return this.getSqlSession().selectList("RiskMenuMapper.getByParentId",parentId);
	}
	
	public int getCountByParentId(Integer parentId){
		return (Integer)this.getSqlSession().selectOne("RiskMenuMapper.getCountByParentId", parentId);
	}

	public RiskMenuEntity getNumberByParent(int parentId) {
		return (RiskMenuEntity)this.getSqlSession().selectOne("RiskMenuMapper.getMaxNumberByParentId", parentId);
	}
}
