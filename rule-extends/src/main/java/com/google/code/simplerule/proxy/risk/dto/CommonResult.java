package com.google.code.simplerule.proxy.risk.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author:  孙林
 * @since: 2012-7-23
 * @version: 1.0.0
 */
public class CommonResult<T> implements Serializable {
	
	private static final long serialVersionUID = -3071073927996645716L;
	
	private boolean success;
    private Integer code;
    private Object object;
    private List<T> rows;;
    private String message;
    private Long total = 0L;
    
	public boolean getSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public Object getObject() {
		return object;
	}
	public void setObject(Object object) {
		this.object = object;
	}
	public List<T> getRows() {
		return rows;
	}
	public void setRows(List<T> rows) {
		this.rows = rows;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(Long total) {
		this.total = total;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
   
}
