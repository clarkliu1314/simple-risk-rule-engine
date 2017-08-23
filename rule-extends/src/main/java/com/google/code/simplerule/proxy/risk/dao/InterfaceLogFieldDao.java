package com.google.code.simplerule.proxy.risk.dao;

import java.util.List;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.entity.InterfaceLogFieldEntity;

@Service
public class InterfaceLogFieldDao extends RiskSqlSessionDaoSupport {
	public List<InterfaceLogFieldEntity> findByInterfaceName(
			String interfaceName) {
		if (interfaceName != null && interfaceName.equals("")) {
			interfaceName = null;
		}
		return this.getSqlSession().selectList("InterfaceLogFieldMapper.findByInterfaceName", interfaceName);
	}

	public void deleteByInterface(String interfaceName) {
		this.getSqlSession().delete("InterfaceLogFieldMapper.deleteByInterfaceName", interfaceName);
	}

	public void insert(InterfaceLogFieldEntity e) {
		this.getSqlSession().insert("InterfaceLogFieldMapper.insert", e);
	}
	
}
