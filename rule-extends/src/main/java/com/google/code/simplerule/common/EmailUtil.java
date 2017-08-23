package com.google.code.simplerule.common;

import com.google.code.simplerule.proxy.risk.entity.EmailEntity;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Properties;
import com.google.code.simplerule.proxy.risk.*;

/**
 * @author:sunny
 * @since:2013-04-08
 * @version:1.0.0
 * @description:邮件工具类
 */
public class EmailUtil {

    private static final  Logger logger = Logger.getLogger(EmailUtil.class);

    public static void sendEmail(EmailEntity emailEntity) throws MessagingException {
        try {
            JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
            //设置服务器，用户名 密码
            mailSender.setHost(emailEntity.getHost());
            mailSender.setUsername(emailEntity.getUsername()) ;
            mailSender.setPassword(emailEntity.getPassword()) ;

            //设定邮件服务器，建立邮件消息
            //建立邮件消息,发送简单邮件和html邮件的区别
            //注意这里的boolean,等于真的时候才能嵌套图片，在构建MimeMessageHelper时候，所给定的值是true表示启用.multipart模式
            MimeMessage mailMessage = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(mailMessage,true,"utf-8");

            //设置收件人，寄件人 ,true 表示启动HTML格式的邮件，设置附件
            messageHelper.setTo(emailEntity.getTo());
            messageHelper.setFrom(emailEntity.getTo());
            messageHelper.setSubject(emailEntity.getSubject());
            messageHelper.setText(emailEntity.getText(),true);

//        FileSystemResource img = new FileSystemResource(new File("g:/123.jpg"));
//        messageHelper.addInline("aaa",img);

            // 设置属性，参数设为true，让服务器进行认证,认证用户名和密码是否正确
            Properties prop = new Properties() ;
            prop.put("mail.smtp.auth", "true") ;
            prop.put("mail.smtp.timeout", "25000") ;
            mailSender.setJavaMailProperties(prop);

            //发送邮件
            mailSender.send(mailMessage);

            logger.info("发送邮件成功。");
        }catch (Exception e){
            logger.info("发送邮件异常 " + e.getMessage());
        }

    }

    /**
     * test
     * @param args
     */
    public static void main(String[]args){
        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setHost("smtp.jd.com");
//        emailEntity.setUsername("payrisk");
//        emailEntity.setPassword("riskpay123456+");

        emailEntity.setUsername("sunlin");
        emailEntity.setPassword("xiaozhi901017+");

        emailEntity.setFrom("payrisk@jd.com");
        emailEntity.setTo("sunlin@jd.com");
        emailEntity.setSubject("风控邮件");
        emailEntity.setText("测试风控发送邮件");

        try {
            EmailUtil.sendEmail(emailEntity);
        } catch (MessagingException e) {
            logger.error("发送邮件失败。" + e.getMessage());
        }
    }

}
