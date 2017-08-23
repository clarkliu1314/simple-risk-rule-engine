package com.google.code.simplerule.proxy.risk.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.proxy.risk.dao.InterfaceLogFieldDao;
import com.google.code.simplerule.proxy.risk.entity.InterfaceLogFieldEntity;

@Service
public class InterfaceLogFieldService {
	private final Logger logger = LoggerFactory.getLogger(InterfaceLogFieldService.class);
	
	@Autowired
	private InterfaceLogFieldDao interfaceLogFieldDao;

	public List<InterfaceLogFieldEntity> findByInterfaceName(String interfaceName) {
		return interfaceLogFieldDao.findByInterfaceName(interfaceName);
	}

	public boolean save(String interfaceName, String[] fields) {
		try {
			interfaceLogFieldDao.deleteByInterface(interfaceName);
			
			for (String f : fields) {
				InterfaceLogFieldEntity e = new InterfaceLogFieldEntity();
				e.setInterfaceName(interfaceName);
				e.setFieldId(Long.valueOf(f));
				
				interfaceLogFieldDao.insert(e);
			}
			
			return true;
		}
		catch (Exception e) {
			logger.error("error save " + interfaceName, e);
			return false;
		}
	}
}
