package com.google.code.simplerule.ccmis.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
//import com.jd.payment.facade.pci.results.PlaintextDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import redis.clients.jedis.Jedis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
//import com.jd.payment.facade.pci.EncryptFacade;
//import com.jd.payment.facade.pci.results.CiphertextDTO;
//import com.jd.payment.facade.pci.results.EncryptException;
import com.google.code.simplerule.ccmis.controller.common.JsonResult;
import com.google.code.simplerule.ccmis.controller.common.PageTagResultDTO;
import com.google.code.simplerule.proxy.risk.entity.BlackListEntity;
import com.google.code.simplerule.proxy.risk.entity.common.Constants;
import com.google.code.simplerule.proxy.risk.entity.common.StringFormatUtil;
import com.google.code.simplerule.proxy.risk.enums.BlackListTypeEnum;
import com.google.code.simplerule.proxy.risk.service.BlackListService;
import com.google.code.simplerule.redis.queue.RedisQueue;

@Controller
@RequestMapping("/risk/blacklist")
public class BlackListController extends BaseController {
	@Autowired
	private BlackListService blackListService;
//	@Resource
//	private EncryptFacade encryptFacade;
	@Resource
	private RedisQueue redisQueue;

	private final Logger logger = LoggerFactory
			.getLogger(BlackListController.class);

	@RequestMapping("/list")
	public void list(HttpServletRequest request, HttpServletResponse response) {
		Jedis jedis = redisQueue.getJedisPool().getResource();
		BlackListEntity param = new BlackListEntity();
		bindObject(request, param);
		JsonResult jr = new JsonResult();
		String[] p = { param.getValue() };
		if (param.getType() != null) {
			String key = StringFormatUtil.encode(param.getType());
//			if (Constants.ENCRYPTION.equals(jedis.get(key))) {
//				try {
//					CiphertextDTO dto = encryptFacade.encrypt(p);
//					param.setValue(dto.getCiphertext()[0]);
//				} catch (EncryptException e) {
//					logger.warn("query blacklist errors : " + e);
//				}
//			}
		}
		PageTagResultDTO<BlackListEntity> result = blackListService
				.query(param);
		;

		jr.setList(result.getList());
		jr.setTotalSize(result.getTotalSize());
		jr.setSuccess(true);
		this.outJsonString(response, jr);
	}

	@RequestMapping("/save")
	@ResponseBody
	public String save(HttpServletRequest request, HttpServletResponse response) {
		Jedis jedis = redisQueue.getJedisPool().getResource();
		BlackListEntity param = new BlackListEntity();
		bindObject(request, param);
		Object obj = request.getAttribute(Constants.HTTP_LOGIN_CONTEXT);
		JSONObject user = (JSONObject) JSON.toJSON(obj);
		String erp = (String) user.get(Constants.ERP_LOGIN_NAME);
		param.setOperator(erp);
		JsonResult jr = new JsonResult();
		
		String[] values = param.getValue().split(",");
		try {
			for (String value : values) {
			String[] p = { value.replace("\n","").trim() };
			String type = param.getType();
			String key = StringFormatUtil.encode(type);
			if (Constants.ENCRYPTION.equals(jedis.get(key)) && param.getId() == null ) {
//				CiphertextDTO dto = encryptFacade.encrypt(p);
//				param.setValue(dto.getCiphertext()[0]);
//				param.setKeyVersion(dto.getKeyVersion());
			}
			redisQueue.getJedisPool().returnResource(jedis);
			blackListService.save(param);
			param.setId(null);
			}
			jr.setSuccess(true);
		} catch (Exception e) {
			if (jedis != null)
				redisQueue.getJedisPool().returnBrokenResource(jedis);
			logger.warn("black list save error." + param, e);
			jr.setSuccess(false);
			jr.setMessage(e.getMessage());
		}

		return JSON.toJSONStringWithDateFormat(jr, "yyyy-MM-dd HH:mm:ss");
	}

	@RequestMapping("/doQueryOriginalValue")
	public void doQueryOriginalValue(HttpServletRequest request,
			HttpServletResponse response) {
		JsonResult jr = new JsonResult();
		String id = request.getParameter("id");
		BlackListEntity entity = blackListService.getById(id);
		if (entity == null)
			return;
		try {
			if (entity.getKeyVersion() != null) {
//				PlaintextDTO plaintextDTO = encryptFacade.decrypt(
//						entity.getKeyVersion(),
//						new String[] { entity.getValue() });
//				jr.setSuccess(true);
//				jr.setMessage("加密版本：" + plaintextDTO.getKeyVersion() + "原始值："
//						+ plaintextDTO.getPlaintext()[0]);
			} else {
				jr.setSuccess(true);
				jr.setMessage("原始值：" + entity.getValue());
			}
		} catch (Exception e) {
			logger.info("BlackListController | doQueryOriginalValue | error | "
					+ e.getMessage());
			jr.setSuccess(false);
			jr.setMessage("BlackListController | doQueryOriginalValue | error | "
					+ e.getMessage());
		} finally {
			this.outJsonString(response, jr);
		}
	}

}
