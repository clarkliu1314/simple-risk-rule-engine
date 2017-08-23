package com.google.code.simplerule.proxy.risk.monitor.watcher;

import java.util.Map;

/**
 * 监视器
 * @author drizzt
 *
 */
public interface Watcher {
	/**
	 * 注册监视器
	 * @param name 名字
	 * @param param 参数
	 */
	boolean register(String name, Map param);
	
	/**
	 * 添加参数
	 * @param name
	 * @param param
	 */
	void addParameters(String name, Map param);
	
	/**
	 * 查找所有监视器
	 * @return
	 */
	String[] findWatchers();
	
	/**
	 * 得到此监视器参数
	 * @param name
	 * @return
	 */
	Map getParameters(String name);
	
	/**
	 * 取消注册
	 * @param name
	 */
	void unregister(String name);
}
