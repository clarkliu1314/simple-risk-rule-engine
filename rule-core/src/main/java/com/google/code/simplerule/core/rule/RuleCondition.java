package com.google.code.simplerule.core.rule;

import java.util.Map;
import com.google.code.simplerule.core.exception.RiskException;

/**
 * 规则处理条件，即规则条件部分的接口
 * @author drizzt
 *
 */
public interface RuleCondition {

	/**
	 * 执行条件
	 * @param context
	 * @param map
	 * @return
	 * @throws RiskException
	 */
	boolean execute(RuleContext context, Map map) throws RiskException;

	/**
	 * 得到此条件的连接器
	 * @return
	 */
    public Object getConnector() ;

}
