package com.google.code.simplerule.proxy.risk.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.dao.BlackListDao;
import com.google.code.simplerule.proxy.risk.dao.InterfaceDao;
import com.google.code.simplerule.proxy.risk.entity.BlackListEntity;
import com.google.code.simplerule.proxy.risk.entity.InterfaceEntity;
import com.google.code.simplerule.proxy.risk.entity.common.Constants;
import com.google.code.simplerule.proxy.risk.entity.common.ObjectUtils;
import com.google.code.simplerule.proxy.risk.entity.common.StringFormatUtil;
import com.google.code.simplerule.proxy.risk.enums.BlackListTypeEnum;
import com.google.code.simplerule.proxy.risk.enums.InterfaceStatusEnum;
import com.google.code.simplerule.redis.queue.RedisQueue;

@Service
public class BlackListService {
	@Autowired
	private BlackListDao blackListDao;
	@Autowired
	private InterfaceDao interfaceDao;
	@Resource
	private RedisQueue redisQueue;
	private static String ANTIMONEY_INTERFACENAME = "payment.risk.anti-money";
	
	public boolean inList(String interfaceName, String type, String value) {
		List<BlackListEntity> list = blackListDao.search(interfaceName, type,
				value);
		return list != null && list.size() > 0;
	}

	public PageTagResultDTO<BlackListEntity> query(BlackListEntity param) {
		HashMap<String, Object> paramMap = null;
		paramMap = ObjectUtils.objectToMap(param);
		List<BlackListEntity> list = blackListDao.query(paramMap);
		int count = blackListDao.queryCount(paramMap);
		PageTagResultDTO<BlackListEntity> pageTagResultDTO = new PageTagResultDTO<BlackListEntity>();
		List<BlackListEntity> resultList = new ArrayList<BlackListEntity>();
		if (list.size() > 0) {
			for (BlackListEntity blackListEntity : list) {
				if(!ANTIMONEY_INTERFACENAME.equals(param.getInterfaceName())){
					if(blackListEntity.getInterfaceName().equals(ANTIMONEY_INTERFACENAME))
						continue;
				}
				InterfaceEntity in = interfaceDao.getByName(blackListEntity
						.getInterfaceName());
				blackListEntity.setStatusDescription(InterfaceStatusEnum
						.enumValueOf(blackListEntity.getStatus())
						.toDescription());
				blackListEntity
						.setTypeDescrption(BlackListTypeEnum.enumValueOf(
								blackListEntity.getType()).toDescription());
				if (in != null)
					blackListEntity.setInterfaceNameDescription(in.getDescription());
				resultList.add(blackListEntity);
			}
		}
		pageTagResultDTO.setList(resultList);
		pageTagResultDTO.setTotalSize(count);
		return pageTagResultDTO;
	}

	public void save(BlackListEntity entity) {
		if (entity.getId() != null) {
			blackListDao.update(entity);
		} else {
			blackListDao.insert(entity);
		}
	}
	
	@PostConstruct
	public void initMapper() {
		Jedis jedis = redisQueue.getJedisPool().getResource();
		try {
				String account_key = StringFormatUtil.encode(BlackListTypeEnum.ACCOUNT.toName());
				String card_key = StringFormatUtil.encode(BlackListTypeEnum.CARD.toName());
				String mobile_key = StringFormatUtil.encode(BlackListTypeEnum.MOBILE.toName());
				String idcard_key = StringFormatUtil.encode(BlackListTypeEnum.IDCARD.toName());
				String name_key = StringFormatUtil.encode(BlackListTypeEnum.NAME.toName());
				String email_key = StringFormatUtil.encode(BlackListTypeEnum.EMAIL.toName());
				jedis.set(account_key,Constants.NOT_ENCRYPTION);
				jedis.set(card_key,Constants.NOT_ENCRYPTION);
				jedis.set(mobile_key, Constants.NOT_ENCRYPTION);
				jedis.set(idcard_key, Constants.ENCRYPTION);
				jedis.set(name_key, Constants.NOT_ENCRYPTION);
				jedis.set(email_key, Constants.NOT_ENCRYPTION);
			    redisQueue.getJedisPool().returnResource(jedis);
		} catch (Exception e) {
			if (jedis != null)
				redisQueue.getJedisPool().returnBrokenResource(jedis);

		}

	}
	
	public BlackListEntity getById(String id){
		return blackListDao.getById(id);
	}

	public int getCount(HashMap<String, Object> paramMap) {
		return blackListDao.queryCount(paramMap);
	}

}
