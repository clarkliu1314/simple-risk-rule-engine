package com.google.code.simplerule.ccmis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.common.EmailConfig;
import com.google.code.simplerule.proxy.risk.service.EmailService;

/**
 * @author:sunny
 * @since:2013-04-09
 * @version:1.0.0
 * @description:EmailController
 */

@Controller
@RequestMapping("/risk/email")
public class EmailController extends BaseController {

    @Resource
    private EmailService emailService;

    @RequestMapping("/setEmailConfig")
    public ModelAndView doQueryOriginalValue(HttpServletRequest request,HttpServletResponse response) {
        JsonResult jr = new JsonResult();
        EmailConfig emailConfig = new EmailConfig();
        bindObject(request,emailConfig);
        boolean flag =  emailService.setEmailConfig(emailConfig);

        if (flag){
            jr.setMessage("修改邮箱配置成功");
        }else {
            jr.setMessage("修改邮箱配置失败");
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("success.jsp");
        modelAndView.addObject("jr",jr);
        return modelAndView;
    }

}
