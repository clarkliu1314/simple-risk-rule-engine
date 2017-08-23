package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.service.BlackListService;

@Service
public class IdCardBlackListFactor extends BlackListFactor {
	@Override
	public String getName() {
		return "证件号码黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "idcard";
	}

	@Override
	protected String getBlackListName() {
		return "idCardNo";
	}
	
	@Override
	protected String getBlackListDesc() {
		return "证件号";
	}
}
