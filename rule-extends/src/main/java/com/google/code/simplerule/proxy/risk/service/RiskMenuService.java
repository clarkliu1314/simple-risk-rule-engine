package com.google.code.simplerule.proxy.risk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.code.simplerule.proxy.risk.dao.RiskMenuDao;
import com.google.code.simplerule.proxy.risk.entity.RiskMenuEntity;
import com.google.code.simplerule.proxy.risk.entity.common.StringFormatUtil;
import com.google.code.simplerule.redis.queue.RedisQueue;

@Service
public class RiskMenuService {
	@Autowired
	private RiskMenuDao riskMenuDao;
	@Resource
	private RedisQueue redisQueue;

	public RiskMenuEntity addNotExist(String code, String name,	String interfaceName, RiskMenuEntity parent) {
		checkData(null, code, interfaceName,parent);
		
		RiskMenuEntity e = new RiskMenuEntity();
		e.setCode(code);
		e.setName(name);
		e.setInterfaceName(interfaceName);
		e.setCreateTime(new Date());
		if (parent == null) {
			e.setParentId(0);
		} else {
			e.setParentId(parent.getId());
		}
		e.setNumber(getNumberByParent(e.getParentId()));
		riskMenuDao.insert(e);

		return e;
	}

	private void checkData(Integer id,String code, String interfaceName,RiskMenuEntity parent) {
		RiskMenuEntity exist = riskMenuDao.getByCode(code);

		if (exist != null && !exist.getId().equals(id))
			throw new RuntimeException("编码为：["+code+"] 的风控菜单已经存在，编码不能重复");
		if(parent != null && StringUtils.isNotBlank(parent.getInterfaceName()))
			throw new RuntimeException("上级菜单不能为接口！");
		if(parent == null && StringUtils.isNotBlank(interfaceName))
			throw new RuntimeException("请先添加接口所属的菜单");
	}
	
	public void updateMenu(RiskMenuEntity menuEntity){
		RiskMenuEntity parentMenu = getById(menuEntity.getParentId());
		checkData(menuEntity.getId(), menuEntity.getCode(), menuEntity.getInterfaceName(),parentMenu);
		
		riskMenuDao.update(menuEntity);
	}
	
	public void deleteMenu(Integer menuID){
		riskMenuDao.delete(menuID);
	}

	private String getNumberByParent(int parentId) {
		RiskMenuEntity e = riskMenuDao.getNumberByParent(parentId);
		if (e == null || e.getNumber() == null)
			return "01";

		int n = Integer.valueOf(e.getNumber());
		String s = String.valueOf(n + 1);
		if (s.length() < 2) {
			s = "0" + s;
		}
		return s;
	}

	public Map<RiskMenuEntity, List<RiskMenuEntity>> getMenuList() {

		List<RiskMenuEntity> list = riskMenuDao.findAll();
		Map<RiskMenuEntity, List<RiskMenuEntity>> result = new HashMap<RiskMenuEntity, List<RiskMenuEntity>>();

		for (RiskMenuEntity riskMenuEntity : list) {
			if (riskMenuEntity.getParentId() == 0) {
				result.put(riskMenuEntity, new ArrayList<RiskMenuEntity>());
				list.remove(riskMenuEntity);
			}
		}

		for (RiskMenuEntity riskMenuEntity : result.keySet()) {
			for (RiskMenuEntity entity : list) {
				if (entity.getParentId() == riskMenuEntity.getId()) {
					result.get(riskMenuEntity).add(entity);
				}
			}
		}
		return result;
	}

	public List<RiskMenuEntity> getRootList() {
		return riskMenuDao.getRootMenu();

	}

	public List<RiskMenuEntity> getMenuListByParentId(Integer parentId) {
		List<RiskMenuEntity> list = riskMenuDao.getByParentId(parentId);
		for (RiskMenuEntity riskMenuEntity : list) {
			int count = riskMenuDao.getCountByParentId(riskMenuEntity.getId());
			if (count <= 0) {
				riskMenuEntity.setIsParent(false);
			}
		}
		return list;
	}

	@PostConstruct
	public void initRuleNo() {
		List<RiskMenuEntity> ml = riskMenuDao.findAll();
		Jedis jedis = redisQueue.getJedisPool().getResource();
		try {
			for (RiskMenuEntity riskMenuEntity : ml) {
				String key = StringFormatUtil.encode("MENU"+riskMenuEntity.getCode());
				jedis.set(key, riskMenuEntity.getNumber());
			}
			redisQueue.getJedisPool().returnResource(jedis);
		} catch (Exception e) {
			if (jedis != null)
				redisQueue.getJedisPool().returnBrokenResource(jedis);

		}

	}

	public RiskMenuEntity getById(Integer id) {
		if(id == null)
			return null;
		
		return riskMenuDao.getById(id);
	}

	public List<RiskMenuEntity> findAll() {
		return riskMenuDao.findAll();
	}
}
