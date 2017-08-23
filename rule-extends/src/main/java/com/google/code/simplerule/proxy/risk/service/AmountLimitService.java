package com.google.code.simplerule.proxy.risk.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.code.simplerule.redis.queue.RedisQueue;

@Service
public class AmountLimitService {
	protected Logger logger = LoggerFactory.getLogger(AmountLimitService.class);
	@Autowired
	private RedisQueue redisQueue;
	
	public long getAndAddup(String key, int seconds, long number) {
		Jedis j = redisQueue.getJedisPool().getResource();
		try {
			String ret = j.get(key);
			if (ret == null) {
				j.setex(key, seconds, String.valueOf(number));
			}
			else {
				j.incrBy(key, number);
			}
			redisQueue.getJedisPool().returnResource(j);
			
			return Long.valueOf(ret); 
		}
		catch (Exception e) {
			if (j != null)
				redisQueue.getJedisPool().returnBrokenResource(j);
			return 0;
		}
	}
	
	public long get(String key, int seconds) {
		Jedis j = redisQueue.getJedisPool().getResource();
		try {
			String ret = j.get(key);
			redisQueue.getJedisPool().returnResource(j);
			if (ret == null) {
				return 0;
			}
			else {
				return Long.valueOf(ret); 
			}
		}
		catch (Exception e) {
			if (j != null)
				redisQueue.getJedisPool().returnBrokenResource(j);
			return 0;
		}
	}

	public void addup(String key, int seconds, long number) {
		Jedis j = redisQueue.getJedisPool().getResource();
		try {
			String ret = j.get(key);
			if (ret == null) {
				j.setex(key, seconds, String.valueOf(number));
			}
			else {
				j.incrBy(key, number);
			}
			redisQueue.getJedisPool().returnResource(j);
		}
		catch (Exception e) {
			if (j != null)
				redisQueue.getJedisPool().returnBrokenResource(j);
		}
	}
}
