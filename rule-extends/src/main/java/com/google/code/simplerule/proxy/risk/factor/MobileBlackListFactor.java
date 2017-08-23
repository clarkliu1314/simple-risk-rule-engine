package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.service.BlackListService;

@Service
public class MobileBlackListFactor extends BlackListFactor {
	@Override
	public String getName() {
		return "手机号黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "mobile";
	}

	@Override
	protected String getBlackListName() {
		return "mobile";
	}
	
	@Override
	protected String getBlackListDesc() {
		return "手机号码";
	}
}
