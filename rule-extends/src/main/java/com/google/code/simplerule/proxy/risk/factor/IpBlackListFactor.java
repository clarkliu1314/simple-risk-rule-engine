package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.code.simplerule.proxy.risk.service.BlackListService;

@Service
public class IpBlackListFactor extends BlackListFactor {
	@Override
	public String getName() {
		return "IP黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "ip";
	}

	@Override
	protected String getBlackListName() {
		return "ip";
	}
	
	@Override
	protected String getBlackListDesc() {
		return "IP地址";
	}
}
