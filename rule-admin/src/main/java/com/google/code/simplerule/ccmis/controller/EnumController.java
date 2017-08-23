package com.google.code.simplerule.ccmis.controller;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.ccmis.controller.entity.EnumInfo;
import com.google.code.simplerule.proxy.risk.enums.EnumFactory;

@Controller
@RequestMapping("/risk/enums")
public class EnumController extends BaseController{
    /**
     * 请求枚举类型
     *
     * @param request
     * @param respnose
     */
	@RequestMapping("/getEnum")
    public void getEnum(HttpServletRequest request, HttpServletResponse respnose) {
        EnumInfo param = new EnumInfo();
        bindObject(request, param);

        JsonResult jr = new JsonResult();
        List<EnumInfo> list = new ArrayList<EnumInfo>();
        try {
            Map map = EnumFactory.getEnumMap(param.getName(), param.getType());
            Set set = map.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                EnumInfo enumInfo_ = new EnumInfo();
                Object obj = it.next();
                    if (obj != null && !"NULL".equals(obj) && map.get(obj)!=null) {
                        enumInfo_.setCode(obj.toString());
                        enumInfo_.setName(map.get(obj).toString());
                        list.add(enumInfo_);
                }
            }
            //杩斿洖
            jr.setList(list);
            jr.setTotalSize(list.size());
            jr.setSuccess(true);
        } catch (Exception e) {
            jr.setSuccess(false);
            jr.setMessage(e.getMessage());
        }
        this.outJsonString(respnose, jr);
    }

	@RequestMapping("/getEnumOfEasyui")
    public void getEnumOfEasyui(HttpServletRequest request, HttpServletResponse respnose) {
        EnumInfo param = new EnumInfo();
        bindObject(request, param);
        List<EnumInfo> list = new ArrayList<EnumInfo>();
        try {
            Map map = EnumFactory.getEnumMap(param.getName(), param.getType());
            Set set = map.keySet();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                EnumInfo enumInfo_ = new EnumInfo();
                Object obj = it.next();
                    if (obj != null && !"NULL".equals(obj) && map.get(obj)!=null) {
                        enumInfo_.setCode(obj.toString());
                        enumInfo_.setName(map.get(obj).toString());
                        list.add(enumInfo_);
                }
            }
        } catch (Exception e) {
        }
        this.outJsonString(respnose, list);
    }
}
