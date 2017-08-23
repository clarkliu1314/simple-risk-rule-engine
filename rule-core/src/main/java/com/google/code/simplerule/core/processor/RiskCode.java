package com.google.code.simplerule.core.processor;

import com.google.code.simplerule.core.result.RiskResult;

/**
 * 规则返回代码，0-100系统保留
 * @author drizzt
 *
 */
public class RiskCode {
	/**
	 * 成功
	 */
	public final static String Success = "R000000";
	/**
	 * 异常
	 */
	public final static String Exception = "R000001";
	/**
	 * 执行超时
	 */
	public final static String ProcessTimeOut = "R000002";
	/**
	 * 人工审核
	 */
	public final static String ManualAudit = "R000003";
	
	/**
	 * 没有找到这种规则
	 */
	public final static String NotFoundRule = "R000004";
	/**
	 * 验证失败
	 */
	public final static String ValidateError = "R000005";
	/**
	 * 网络错误
	 */
	public final static String NetworkError = "R000006";
	/**
	 * 中断当前流程
	 */
	public final static String BreakFlow = "R000007";
	/**
	 * 不通过
	 */
	public final static String NoPass = "R000020";
	
	/**
	 * 返回简单的规则结果
	 * @param code 代码
	 * @param desc 描述
	 * @return 规则结果
	 */
	public static RiskResult simpleResult(String code, String desc) {
		RiskResult result = new RiskResult();
		result.setCode(code);
		result.setDescription(desc);
		return result;
	}
}
