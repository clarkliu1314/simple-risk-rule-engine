package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.RulesEntity;

@Service
public class RulesDraftDao extends RiskSqlSessionDaoSupport {

	public List<RulesEntity> findByInterfaceName(String interfaceName) {
		return this.getSqlSession().selectList("RulesDraftMapper.findByInterfaceName", interfaceName);
	}

	public List<RulesEntity> findByInterfaceNameAndOprType(Map<String,Object> param){
		return this.getSqlSession().selectList("findByInterfaceAndOprType",param);
	}
	public long insert(RulesEntity re) {
		this.getSqlSession().insert("RulesDraftMapper.insert", re);
		return re.getId();
	}
	
	public RulesEntity findByRuleId(Long ruleId){
		return this.getSqlSession().selectOne("RulesDraftMapper.findById",ruleId);
	}

	public long update(RulesEntity re){
		this.getSqlSession().update("RulesDraftMapper.update",re);
		return re.getId();
	}
	
	public void delete(Long ruleId){
		this.getSqlSession().delete("RulesDraftMapper.delete",ruleId);
	}
	
	public String findLastNoByInterfaceName(String interfaceName){
		return this.getSqlSession().selectOne("RulesDraftMapper.findLastNo",interfaceName);
	}

	public RulesEntity findByNo(String no){
		return this.getSqlSession().selectOne("RulesDraftMapper.findByNo", no);
	}

	public void deleteByNo(String no){
	  this.getSqlSession().selectOne("RulesDraftMapper.deleteByNo",no);
	}

}
