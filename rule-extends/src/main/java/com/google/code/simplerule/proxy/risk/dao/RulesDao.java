package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.RulesEntity;

@Service
public class RulesDao extends RiskSqlSessionDaoSupport {
	public List<RulesEntity> findByInterfaceName(String interfaceName) {
		return this.getSqlSession().selectList("RulesMapper.findByInterfaceName", interfaceName);
	}

	public long insert(RulesEntity re) {
		this.getSqlSession().insert("RulesMapper.insert", re);
		return re.getId();
	}
	
	public RulesEntity findByRuleId(Long ruleId){
		return this.getSqlSession().selectOne("RulesMapper.findById",ruleId);
	}

	public long update(RulesEntity re){
		this.getSqlSession().update("RulesMapper.update",re);
		return re.getId();
	}
	
	public void delete(Long ruleId){
		this.getSqlSession().delete("RulesMapper.delete",ruleId);
	}
	
	public String findLastNoByInterfaceName(String interfaceName){
		return this.getSqlSession().selectOne("RulesMapper.findLastNo",interfaceName);
	}
	
	public RulesEntity findByNo(String rulesNo){
       return this.getSqlSession().selectOne("RulesMapper.findByNo", rulesNo);
	}
	

	public void deleteByNo(String no){
	  this.getSqlSession().selectOne("RulesMapper.deleteByNo",no);
	}
}
