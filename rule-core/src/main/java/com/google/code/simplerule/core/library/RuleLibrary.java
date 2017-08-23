package com.google.code.simplerule.core.library;

/**
 * 规则库
 * @author drizzt
 *
 */
public interface RuleLibrary {
	/**
	 * 从库中得到最新的规则
	 * @param interfaceName 接口名
	 * @return 规则文本
	 */
	String updateRule(String interfaceName);
	
	/**
	 * 提交本地规则到库
	 * @param filePath 文件名
	 * @return 是否成功
	 */
	boolean commitRule(String filePath);
	
	/**
	 * 从库里删除规则
	 * @param interfaceName 接口名
	 * @return 是否成功
	 */
	boolean removeRule(String interfaceName);
	
	/**
	 * 判断是否存在库中
	 * @param packageName 接口名
	 * @return 是否存在
	 */
	boolean isExist(String packageName);
}
