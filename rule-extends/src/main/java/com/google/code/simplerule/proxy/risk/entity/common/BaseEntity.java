package com.google.code.simplerule.proxy.risk.entity.common;



import java.util.Date;

/**
 * 公用属性
 *
 */
public class BaseEntity {

    /**
     * 分页查询 开始数据
     */
    private int start;

    /**
     * 分页查询 一次查询条数
     */
    private int limit;

    /**
     * 分页查询结束Number
     */
    private int end;
    
    /**
     * Easyui分页查询查询条数
     */
    private int rows;
    
    /**
     * Easyui分页查询页数
     */
    private int page;
    
    /**
     * Easyui分页查询起始
     */
    private int pageStart;
    
    /**
     * Easyui分页查询结束
     */
    private int pageEnd;
    
    /**
     * 创建人
     */
    private String created;

    /**
     * 创建人Name
     */
    private String createdName;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 创建ip
     */
    private String createdIp;

    /**
     * 修改人
     */
    private String modified;

    /**
     * 修改人Name
     */
    private String modifiedName;

    /**
     * 修改时间
     */
    private Date modifiedDate;

    /**
     * 修改ip
     */
    private String modifiedIp;

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedIp() {
        return createdIp;
    }

    public void setCreatedIp(String createdIp) {
        this.createdIp = createdIp;
    }

    public String getModified() {
        return modified;
    }

    public void setModified(String modified) {
        this.modified = modified;
    }

    public String getModifiedName() {
        return modifiedName;
    }

    public void setModifiedName(String modifiedName) {
        this.modifiedName = modifiedName;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public String getModifiedIp() {
        return modifiedIp;
    }

    public void setModifiedIp(String modifiedIp) {
        this.modifiedIp = modifiedIp;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
        setEnd(start, limit);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
        setEnd(start, limit);
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void setEnd(int start, int limit) {
        this.end = start + limit;
    }

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
		this.pageStart = (rows * (page - 1)) + 1;
		this.pageEnd = rows * page;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
		this.pageStart = (rows * (page - 1)) + 1;
		this.pageEnd = rows * page;
	}

	public int getPageStart() {
		return pageStart;
	}

	public void setPageStart(int pageStart) {
		this.pageStart = pageStart;
	}

	public int getPageEnd() {
		return pageEnd;
	}

	public void setPageEnd(int pageEnd) {
		this.pageEnd = pageEnd;
	}
    
}
