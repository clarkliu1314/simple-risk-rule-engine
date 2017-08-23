package com.google.code.simplerule.ccmis.controller;

import com.alibaba.fastjson.JSON;
import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.entity.SystemEntity;
import com.google.code.simplerule.proxy.risk.service.SystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @EnglishDescription:SystemController
 * @ChineseDescription:SystemController
 * @author:sunny
 * @since:2013-02-21
 * @version:1.0.0
 */
@Controller
@RequestMapping("/risk/system")
public class SystemController extends BaseController {
    @Autowired
    private SystemService service;
    private final Logger logger = LoggerFactory.getLogger(SystemController.class);

    @RequestMapping("/selectList")
    public void list(HttpServletRequest request,HttpServletResponse response) {

    	logger.info("SystemController selectList..........");
        JsonResult jr = new JsonResult();
        List<SystemEntity> result =  service.selectAll();
        jr.setList(result);
        jr.setSuccess(true);
        this.outJsonString(response, jr);
    }
}
