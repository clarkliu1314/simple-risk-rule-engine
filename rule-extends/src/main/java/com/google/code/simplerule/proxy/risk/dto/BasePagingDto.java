package com.google.code.simplerule.proxy.risk.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * @author sunny
 * @version 1.0.0
 * @since 2013-2013-4-15
 * @description BasePagingDto.java
 */

public class BasePagingDto implements Serializable{

	private static final long serialVersionUID = 7750650409537059899L;

	/**
	 * 分页查询开始
	 */
	private int start;

	/**
	 * 分页查询结束
	 */
	private int end;
	
	/**
	 * 分页查询 一次查询条数easyui
	 */
	private int rows;
	
	/**
	 * 分页查询 一次查询条数easyui
	 */
	private int limit;
	
	/**
	 * 查询的页数
	 */
	private int page;
	
	
	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = (page - 1) * rows;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = page * rows;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		setLimit(rows);
		setStart(0);
		setEnd(0);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		setLimit(rows);
		setStart(0);
		setEnd(0);
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
