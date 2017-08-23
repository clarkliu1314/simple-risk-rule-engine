package com.google.code.simplerule.common;

import com.google.code.simplerule.redis.queue.RedisQueue;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;

/**
 * @author:sunny
 * @since:2013-04-09
 * @version:1.0.0
 * @description:邮件的配置
 */

public class EmailConfig {

    @Resource
    private RedisQueue redisQueue;

    /**
     * 邮件服务器
     */
    private String host;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 发件人
     */
    private String from;

    /**
     *加载配置数据到redis
     */
    public void init(){
        //查看redis是否有发送邮件配置
        EmailConfig config =  new EmailConfig();

        Jedis jedis = redisQueue.getJedisPool().getResource();
        try {
            config.setHost(jedis.get("risk.email.host"));
            config.setUsername(jedis.get("risk.email.username"));
            config.setPassword(jedis.get("risk.email.password"));
            config.setFrom(jedis.get("risk.email.from"));
        }  finally {
            redisQueue.getJedisPool().returnResource(jedis);
        }

        if (config == null ||
                config.getHost() == null || config.getHost().length() <= 0 ||
                config.getFrom() == null || config.getFrom().length() <= 0 ||
                config.getUsername() == null || config.getUsername().length() <= 0 ||
                config.getPassword() == null || config.getPassword().length() <= 0 ){
            //如果redis没有邮件配置，则加载properties文件中的配置到redisq
            try {
                jedis.set("risk.email.host", host);
                jedis.set("risk.email.username", username);
                jedis.set("risk.email.password", password);
                jedis.set("risk.email.from", from);
            } finally {
                redisQueue.getJedisPool().returnResource(jedis);
            }
        }
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
