package com.google.code.simplerule.proxy.risk.monitor;

import java.util.List;

import com.google.code.simplerule.core.rule.RuleMonitor;

/**
 * 风控监控接口
 * @author drizzt
 *
 */
public interface RiskMonitoring {
	/**
	 * 得到现在运行的机器列表
	 * @return 机器列表
	 */
	List<ServerInfo> listServers();
	
	/**
	 * 从某个机器得到风控接口列表
	 * @param info 机器信息
	 * @return 风控列表
	 */
	List<String> listInterfaces(ServerInfo info);
	
	/**
	 * 得到监控状态
	 * @param info 机器信息
	 * @return 监控状态
	 */
	ServerStatus getStatus(ServerInfo info);
	
	/**
	 * 得到某个风控接口的监控信息
	 * @param info 机器
	 * @param interfaceName 风控接口
	 * @return 状态
	 */
	RuleMonitor getInterfaceStatus(ServerInfo info, String interfaceName);

	/**
	 * 执行某个命令
	 * @param cmd 命令
	 * @param param 参数
	 * @return 执行结果
	 */
	int runCommand(String cmd, Object... param);
	
	/**
	 * 查询日志
	 * @param info 服务器信息，null则查所有的
	 * @param level 日志级别DEBUG、INFO、WARN、ERROR、FATAL
	 * @param date 日期，yyyy-MM-dd
	 * @param maxline 最大行数
	 * @param key 查询关键字
	 * @return 日志文本
	 */
	String findLog(ServerInfo info, String level, String date, int maxline, String key);
}
