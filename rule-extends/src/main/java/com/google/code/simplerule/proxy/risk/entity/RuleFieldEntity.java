package com.google.code.simplerule.proxy.risk.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

import com.google.code.simplerule.proxy.risk.entity.common.BaseEntity;

public class RuleFieldEntity extends BaseEntity implements Serializable {
	private Long id;
	private String category;
	private String name;
	private String description;
	private String codes;
	private String codeinfos;
	private Date createTime;
	private String operator;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getCodes() {
		return codes;
	}
	public void setCodes(String codes) {
		this.codes = codes;
	}
	public String getCodeinfos() {
		return codeinfos;
	}
	public void setCodeinfos(String codeinfos) {
		this.codeinfos = codeinfos;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getCodeList() {
		
		if(StringUtils.isNotEmpty(codes) && StringUtils.isNotEmpty(codeinfos)) {
			String[] codeArray = codes.split(",");
			String[] infoArray = codeinfos.split(",");
			
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < infoArray.length; i++) {
				if(i != 0) {
					str.append(",");
				}
				str.append(codeArray[i]).append(":").append(infoArray[i]);
			}
			
			return str.toString();
		}
		
		return "";
	}
	
	
}
