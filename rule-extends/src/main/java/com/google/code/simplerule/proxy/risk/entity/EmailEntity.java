package com.google.code.simplerule.proxy.risk.entity;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;
import java.io.Serializable;

/**
 * @author:sunny
 * @since:2013-04-08
 * @version:1.0.0
 * @description:邮件实体
 */
public class EmailEntity extends BaseEntity implements Serializable {

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
     * 收件人
     */
    private String to;

    /**
     * 主题人
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String text;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

}
