package com.google.code.simplerule.proxy.risk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.dao.SystemDao;
import com.google.code.simplerule.proxy.risk.entity.SystemEntity;

import java.util.List;

@Service
public class SystemService {
	@Autowired
	private SystemDao systemDao;

	public boolean exist(String systemName) {
		SystemEntity e = systemDao.getByName(systemName);
		return e != null;
	}

	public void addByName(String systemName, String desc) {
		SystemEntity e = new SystemEntity();
		e.setSystemName(systemName);
		e.setDescription(desc);
		e.setStatus(1);
		systemDao.insert(e);
	}

    public List<SystemEntity> selectAll(){
        return systemDao.selectAll();
    }
}
