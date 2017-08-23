package com.google.code.simplerule.proxy.risk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.ShardedJedis;

import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.dao.WhiteListDao;
import com.google.code.simplerule.proxy.risk.dao.InterfaceDao;
import com.google.code.simplerule.proxy.risk.dao.WhiteListDao;
import com.google.code.simplerule.proxy.risk.entity.WhiteListEntity;
import com.google.code.simplerule.proxy.risk.entity.InterfaceEntity;
import com.google.code.simplerule.proxy.risk.entity.WhiteListEntity;
import com.google.code.simplerule.proxy.risk.entity.common.Constants;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;
import com.google.code.simplerule.proxy.risk.entity.common.StringFormatUtil;
import com.google.code.simplerule.proxy.risk.enums.BlackListTypeEnum;
import com.google.code.simplerule.proxy.risk.enums.InterfaceStatusEnum;
import com.google.code.simplerule.redis.queue.RedisQueue;

@Service
public class WhiteListService {

	@Autowired
	private WhiteListDao WhiteListDao;
	@Autowired
	private InterfaceDao interfaceDao;
	@Resource
	private RedisQueue redisQueue;

	public boolean inList(String interfaceName, String type, String value) {
		List<WhiteListEntity> list = WhiteListDao.search(interfaceName, type,
				value);
		return list != null && list.size() > 0;
	}

	public PageTagResultDTO<WhiteListEntity> query(WhiteListEntity param) {
		HashMap<String, Object> paramMap = null;
		paramMap = ObjectUtils.objectToMap(param);
		List<WhiteListEntity> list = WhiteListDao.query(paramMap);
		int count = WhiteListDao.queryCount(paramMap);
		PageTagResultDTO<WhiteListEntity> pageTagResultDTO = new PageTagResultDTO<WhiteListEntity>();
		List<WhiteListEntity> resultList = new ArrayList<WhiteListEntity>();
		if (list.size() > 0) {
			for (WhiteListEntity WhiteListEntity : list) {
				InterfaceEntity in = interfaceDao.getByName(WhiteListEntity
						.getInterfaceName());
				WhiteListEntity.setStatusDescription(InterfaceStatusEnum
						.enumValueOf(WhiteListEntity.getStatus())
						.toDescription());
				WhiteListEntity
						.setTypeDescrption(BlackListTypeEnum.enumValueOf(
								WhiteListEntity.getType()).toDescription());
				if (in != null)
					WhiteListEntity.setInterfaceNameDescription(in.getDescription());
				resultList.add(WhiteListEntity);
			}
		}
		pageTagResultDTO.setList(resultList);
		pageTagResultDTO.setTotalSize(count);
		return pageTagResultDTO;
	}

	public void save(WhiteListEntity entity) {
		if (entity.getId() != null) {
			WhiteListDao.update(entity);
		} else {
			WhiteListDao.insert(entity);
		}
	}
	
	public WhiteListEntity getById(String id){
		return WhiteListDao.getById(id);
	}

	public int getCount(HashMap<String, Object> paramMap) {
		return WhiteListDao.queryCount(paramMap);
	}


}
