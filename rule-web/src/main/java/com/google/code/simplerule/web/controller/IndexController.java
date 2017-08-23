package com.google.code.simplerule.web.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController extends BaseController {
	@RequestMapping("/")
	public String index(Model model, HttpServletRequest request) {
		return "index/index";
	}
	
	@RequestMapping("/info")
	public String info(Model model, HttpServletRequest request) {
		try {
			model.addAttribute("site", InetAddress.getLocalHost());
		} catch (UnknownHostException e) {
			e.printStackTrace();
			model.addAttribute("site", "unknow");
		}
		model.addAttribute("port", request.getServerPort());
		
		String ip = getRemoteIp(request);
		model.addAttribute("clientIp", ip);
		return "index/info";
	}

}
