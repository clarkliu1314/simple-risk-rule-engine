package com.google.code.simplerule.common;

import com.google.code.simplerule.proxy.risk.entity.EmailEntity;
import org.apache.log4j.Logger;
import javax.mail.MessagingException;

/**
 * @author:sunny
 * @since:2013-04-11
 * @version:1.0.0
 * @description:EmailRunnable
 */
public class EmailRunnable implements  Runnable {

    private EmailEntity emailEntity;

    private static final Logger log = Logger.getLogger(EmailRunnable.class);

    public EmailRunnable(EmailEntity emailEntity){
        this.emailEntity = emailEntity;
    }

    @Override
    public void run() {
        try {
            //发送邮件
            EmailUtil.sendEmail(emailEntity);
            log.info("发送邮件完成。");
        } catch (MessagingException e) {
            log.info("发送邮件异常。" + e.getMessage());
        }catch (Exception e){
            log.info("发送邮件异常。" + e.getMessage());
        }
    }

}
