package com.google.code.simplerule.proxy.risk.factor;

import org.springframework.stereotype.Service;

import com.google.code.simplerule.core.factor.FactorField;

@Service
public class AccountBlackListFactor extends BlackListFactor {

	@Override
	public String getName() {
		return "账户名黑名单";
	}

	@Override
	protected String getBlackListType() {
		return "account";
	}

	@Override
	protected String getBlackListName() {
		return "accountNo";
	}

	@Override
	protected String getBlackListDesc() {
		return "账户名";
	}
}
