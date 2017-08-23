package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.stereotype.Service;

@Service
public class NameBlackListFactor extends BlackListFactor {

	@Override
	public String getName() {
		return "消费者姓名黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "name";
	}

	@Override
	protected String getBlackListName() {
		return "cardHolderName";
	}

	@Override
	protected String getBlackListDesc() {
		return "消费者姓名";
	}

}
