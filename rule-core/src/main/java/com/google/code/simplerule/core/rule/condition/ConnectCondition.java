package com.google.code.simplerule.core.rule.condition;

import java.util.Map;

import com.google.code.simplerule.core.rule.RuleCondition;
import com.google.code.simplerule.core.rule.RuleContext;

/**
 * 连接符条件
 * @author drizzt
 *
 */
public class ConnectCondition implements RuleCondition {
	public final static int AND = 1;
	public final static int OR = 2;
	private int connectSymbol = 1;
	
	public ConnectCondition(int symbol) {
		connectSymbol = symbol;
	}

	@Override
	public boolean execute(RuleContext context, Map map) {
		return true;
	}

    @Override
    public Object getConnector() {
        //todo implements the method
        return null;
    }
}
