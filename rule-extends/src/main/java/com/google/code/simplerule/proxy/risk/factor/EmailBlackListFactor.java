package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.stereotype.Service;

@Service
public class EmailBlackListFactor extends BlackListFactor {

	@Override
	public String getName() {
		return "邮箱黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "email";
	}

	@Override
	protected String getBlackListName() {
		return "email";
	}

	@Override
	protected String getBlackListDesc() {
		return "邮箱";
	}

}
