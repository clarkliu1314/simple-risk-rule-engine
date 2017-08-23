package com.google.code.simplerule.core.rule;

import java.util.Date;

/**
 * 规则监控类
 * @author drizzt
 *
 */
public class RuleMonitor {
	public RuleMonitor() {
	}
	private Date lastVisit;
	
	private int success;
	
	private int failure;
	
	private long averageTime;
	
	private long maxTime;
	
	private int health = 10;

	/**
	 * 最后访问时间
	 * @return
	 */
	public Date getLastVisit() {
		return lastVisit;
	}

	/**
	 * 最后访问时间
	 * @param start
	 */
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}

	/**
	 * 成功执行数
	 * @return
	 */
	public int getSuccess() {
		return success;
	}

	/**
	 * 成功执行数
	 * @param success
	 */
	public void setSuccess(int success) {
		this.success = success;
	}

	/**
	 * 失败数
	 * @return
	 */
	public int getFailure() {
		return failure;
	}

	/**
	 * 失败数
	 * @param failure
	 */
	public void setFailure(int failure) {
		this.failure = failure;
	}

	/**
	 * 平均执行时间
	 * @return
	 */
	public long getAverageTime() {
		return averageTime;
	}

	/**
	 * 平均执行时间
	 * @param averageTime
	 */
	public void setAverageTime(long averageTime) {
		this.averageTime = averageTime;
	}

	/**
	 * 最长执行时间
	 * @return
	 */
	public long getMaxTime() {
		return maxTime;
	}

	/**
	 * 最长执行时间
	 * @param maxTime
	 */
	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
