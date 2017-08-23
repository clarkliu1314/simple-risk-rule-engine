package com.google.code.simplerule.proxy.risk.handler;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.google.code.simplerule.common.EmailConfig;
import com.google.code.simplerule.common.EmailRunnable;
import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.factor.field.StringFactorField;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskHandler;
import com.google.code.simplerule.core.rule.RuleContext;
import com.google.code.simplerule.proxy.risk.entity.EmailEntity;
import com.google.code.simplerule.proxy.risk.service.EmailService;

/**
 * @author:sunny
 * @since:2013-04-08
 * @version:1.0.0
 * @description:发送邮件处理方式
 */
public class RiskEmailHandler implements RiskHandler {

    @Resource
    private EmailService emailConfigService;
    public static final Logger log = Logger.getLogger(RiskEmailHandler.class);

    /**
     * 处理器参数
     */
    private Object[] arguments;

    /**
     * 处理器参数类型
     */
    protected final FactorField[] fas = new FactorField[]{
            new StringFactorField("to"),
            new StringFactorField("description")
    };

    @Override
    public RiskResult execute(RuleContext context, RiskResult result) {

        //获取邮件配置
        EmailConfig config =  emailConfigService.selectEmailConfig();

        //设置邮件
        EmailEntity emailEntity = new EmailEntity();
        BeanUtils.copyProperties(config,emailEntity);
        emailEntity.setTo(arguments[0].toString());
        emailEntity.setSubject("网银在线风险控制处理结果");
        emailEntity.setText(context.getRuleTrack());

        //启动线程发送邮件
        EmailRunnable runnable = new EmailRunnable(emailEntity);
        Thread thread = new Thread(runnable);
        thread.start();

        //返回
        RiskResult riskResult = new RiskResult();
        riskResult.setCode("R60000");
        riskResult.setDescription("网银在线风险控制结果邮件发送线程已经启动。");
        return riskResult;
    }

    @Override
    public String getName() {
        return "邮件处理器";
    }

    @Override
    public Object[] getArguments() {
        return arguments;
    }

    @Override
    public void setArguments(Object... objects) {
        arguments = objects;
    }

    @Override
    public FactorField[] getArgumentFields() {
        return fas;
    }
}
