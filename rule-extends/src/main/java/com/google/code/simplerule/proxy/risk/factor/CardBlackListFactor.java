package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.stereotype.Service;
@Service
public class CardBlackListFactor extends BlackListFactor {
	@Override
	public String getName() {
		return "银行卡黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "card";
	}

	@Override
	protected String getBlackListName() {
		return "bankCardNo";
	}

	@Override
	protected String getBlackListDesc() {
		return "银行卡号";
	}
}
