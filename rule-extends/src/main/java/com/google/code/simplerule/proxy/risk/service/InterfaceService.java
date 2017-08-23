package com.google.code.simplerule.proxy.risk.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.dao.InterfaceDao;
import com.google.code.simplerule.proxy.risk.entity.InterfaceEntity;
import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;

@Service
public class InterfaceService {
	@Autowired
	private InterfaceDao interfaceDao;
	@Autowired
	private SystemService systemService;
	@Autowired
	private RiskMenuService riskMenuService;

	public List<InterfaceEntity> findList() {
		return interfaceDao.findAll();
	}

	public int addByInterface(String name, String desc) throws Exception {
		if (exist(name))
			return 0;
		String[] codes = name.split("\\.");
		String[] names = desc.split("\\.");

		if (codes == null || names == null || codes.length != names.length
				|| codes.length != 3)
			return 0;

		String systemCode = codes[1];
		String systemName = names[1];
		if (!systemService.exist(systemCode)) {
			systemService.addByName(systemCode, systemName);
		}

		InterfaceEntity e = new InterfaceEntity();
		e.setSystemName(systemCode);
		e.setDescription(names[names.length - 1]);
		e.setEventDescription("");
		e.setInterfaceName(name);
		e.setCreateTime(new Date());
		e.setStatus(1);
		save(e);

		int count = codes.length;
		RiskMenuEntity parent = null;
		for (int i = 0; i < count; i++) {
			String code = codes[i];
			String n = names[i];

			if (i != (codes.length - 1)) {
				parent = riskMenuService.addNotExist(code, n, "", parent);
			} else {
				parent = riskMenuService.addNotExist(code, n, name, parent);
			}
		}
		riskMenuService.initRuleNo();
		return 1;
	}

	public void save(InterfaceEntity e) throws Exception {
		checkParam(e);
		if (e.getId() == null) {
			interfaceDao.insert(e);
		} else {
			interfaceDao.update(e);
		}
	}

	public void modify(InterfaceEntity e) throws Exception {
		checkParam(e);
		interfaceDao.update(e);
	}

	public void add(InterfaceEntity e) throws Exception {
		checkParam(e);
		List<InterfaceEntity> list = interfaceDao.getListByName(e
				.getInterfaceName());
		if (list != null && list.size() == 0) {
			interfaceDao.insert(e);
		} else {
			throw new Exception("风控接口名称不能重复");
		}
	}
	
	public void deleteByInterface(String interfaceName){
		interfaceDao.deleteByInterface(interfaceName);
	}

	private void checkParam(InterfaceEntity e) throws Exception {
        trim(e);
		if (e == null) {
			throw new Exception("风控接口不能为NULL");
		} else if (e.getInterfaceName() == null
				|| e.getInterfaceName().length() < 0) {
			throw new Exception("风控接口名称不能为空");
		} else if (e.getInterfaceName() != null
				&& e.getInterfaceName().length() > 100) {
			throw new Exception("风控接口名称长度不能超过100。");
		} else if (e.getDescription() == null
				|| e.getDescription().length() < 0) {
			throw new Exception("描述不能为空");
		} else if (e.getDescription() != null
				&& e.getDescription().length() > 200) {
			throw new Exception("描述长度不能超过200。");
		} else if (e.getEventDescription() != null
				&& e.getEventDescription().length() > 200) {
			throw new Exception("事件描述长度不能超过200。");
		} else if (e.getStatus() == null) {
			throw new Exception("状态不能为空");
		}
	}

    private void trim(InterfaceEntity e) throws Exception {
        if (e!=null){
            if (e.getSystemName()!=null){
                e.setSystemName(e.getSystemName().trim());
            }
            if (e.getInterfaceName()!=null){
                e.setInterfaceName(e.getInterfaceName().trim());
            }
            if (e.getDescription()!=null){
                e.setDescription(e.getDescription().trim());
            }
            if (e.getEventDescription()!=null){
                e.setEventDescription(e.getEventDescription().trim());
            }
        }
    }
    public boolean exist(String name) {
        InterfaceEntity e = interfaceDao.getByName(name);
        return e != null;
    }

	public InterfaceEntity getByName(String name) {
		return interfaceDao.getByName(name);
	}

	private String getSystemName(String name) {
		String[] ss = name.split("\\.");
		if (ss.length == 3)
			return ss[1];
		return ss[0];
	}

	public PageTagResultDTO<InterfaceEntity> queryLimit(InterfaceEntity param) {
		HashMap<String, Object> paramMap = null;
		paramMap = ObjectUtils.objectToMap(param);
		List<InterfaceEntity> list = interfaceDao.queryLimit(paramMap);
		int count = interfaceDao.queryCount(paramMap);
		PageTagResultDTO<InterfaceEntity> pageTagResultDTO = new PageTagResultDTO<InterfaceEntity>();
		pageTagResultDTO.setList(list);
		pageTagResultDTO.setTotalSize(count);
		return pageTagResultDTO;
	}
}
