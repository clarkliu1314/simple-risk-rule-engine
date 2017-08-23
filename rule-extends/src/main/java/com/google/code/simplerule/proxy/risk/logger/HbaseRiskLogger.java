package com.google.code.simplerule.proxy.risk.logger;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.simplerule.core.factor.FactorField;
import com.google.code.simplerule.core.result.RiskResult;
import com.google.code.simplerule.core.rule.RiskLogger;
//import com.google.code.simplerule.hbase.dao.RiskLogDao;
import org.apache.commons.lang.StringUtils;

public class HbaseRiskLogger implements RiskLogger {
//	@Autowired
//	private RiskLogDao riskLogDao;
	
	@Override
	public void writeLog(String inerfaceName, Map params, RiskResult result, long tick,
			String ruleInfo) {
		
		params.put("SERVICE_NAME", inerfaceName);
		params.put("EXECUTE_TIME", String.valueOf(tick));
		params.put("CODE", result.getCode());
		
		if(result.getExtendInfo() != null) params.put("EXTEND_INFO", result.getExtendInfo());
		if(result.getReason() != null) params.put("REASON", result.getReason());
		if(result.getDescription() != null) params.put("DESCRIPTION", result.getDescription());
		
		//riskLogDao = new RiskLogDaoImpl("hadoop1");
//		riskLogDao.insert(params);
	}

	private FactorField[] arguments;
	
	@Override
	public FactorField[] getArguments() {
		return arguments;
	}

	@Override
	public void setArguments(FactorField[] fields) {
		arguments = fields;
	}
}
