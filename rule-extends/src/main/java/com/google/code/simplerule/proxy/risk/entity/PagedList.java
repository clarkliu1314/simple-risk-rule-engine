package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.List;

public class PagedList<T> implements Serializable {
	/**
     * 返回的数据集合
     */
    List<T> list;

    /**
     * 符合条件的数据条数
     */
    int totalSize;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
