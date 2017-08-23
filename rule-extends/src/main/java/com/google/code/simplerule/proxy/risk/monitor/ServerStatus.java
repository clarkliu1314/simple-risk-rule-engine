package com.google.code.simplerule.proxy.risk.monitor;

import java.util.Date;

/**
 * 服务器状态
 * @author drizzt
 *
 */
public class ServerStatus {
	private Date start;
	
	private int success;
	
	private int failure;
	
	private int health = 10;

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public int getSuccess() {
		return success;
	}

	public void setSuccess(int success) {
		this.success = success;
	}

	public int getFailure() {
		return failure;
	}

	public void setFailure(int failure) {
		this.failure = failure;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}
}
