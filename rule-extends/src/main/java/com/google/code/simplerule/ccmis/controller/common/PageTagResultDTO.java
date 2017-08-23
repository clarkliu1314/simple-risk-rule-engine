package com.google.code.simplerule.ccmis.controller.common;

import java.io.Serializable;
import java.util.List;

public class PageTagResultDTO<T>
  implements Serializable
{
	List<T> list;
	int totalSize;
	private String lastRow;

	public List<T> getList()
	{
		return this.list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public int getTotalSize() {
		return this.totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
  
  	public String getLastRow() {
		return lastRow;
	}
	public void setLastRow(String lastRow) {
		this.lastRow = lastRow;
	}
	
}