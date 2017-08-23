package com.google.code.simplerule.proxy.risk.service;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;

import com.google.code.simplerule.common.EmailConfig;
import com.google.code.simplerule.redis.queue.RedisQueue;

/**
 * @author:sunny
 * @since:2013-04-09
 * @version:1.0.0
 * @description:EmailConfigService
 */
@Service
public class EmailService {

    @Resource
    private RedisQueue redisQueue;
    private static final Logger log = Logger.getLogger(EmailService.class);

    public EmailConfig selectEmailConfig(){
        EmailConfig config = new EmailConfig();

        Jedis j = redisQueue.getJedisPool().getResource();
        try {
            config.setHost(j.get("risk.email.host"));
            config.setUsername(j.get("risk.email.username"));
            config.setPassword(j.get("risk.email.password"));
            config.setFrom(j.get("risk.email.from"));
        }  finally {
            redisQueue.getJedisPool().returnResource(j);
        }

        return config;
    }

    public boolean setEmailConfig(EmailConfig emailConfig){
        //把配置放入redis
        Jedis jedis = redisQueue.getJedisPool().getResource();
        try {
            jedis.set("risk.email.host", emailConfig.getHost());
            jedis.set("risk.email.username", emailConfig.getUsername());
            jedis.set("risk.email.password", emailConfig.getPassword());
            jedis.set("risk.email.from", emailConfig.getFrom());
        } finally {
            redisQueue.getJedisPool().returnResource(jedis);
        }
        return true;
    }

}
