package com.google.code.simplerule.core.help;

import java.util.List;

/**
 * 接口帮助信息
 * @author drizzt
 *
 */
public class HelpInfo {
	private String interfaceName;
	
	private String description;
	
	private List<FieldInfo> paramInfos;
	
	private List<FieldInfo> resultInfos;

	/**
	 * 得到参数
	 * @return
	 */
	public List<FieldInfo> getParamInfos() {
		return paramInfos;
	}

	/**
	 * 设置参数
	 * @param paramInfos
	 */
	public void setParamInfos(List<FieldInfo> paramInfos) {
		this.paramInfos = paramInfos;
	}

	/**
	 * 得到结果
	 * @return
	 */
	public List<FieldInfo> getResultInfos() {
		return resultInfos;
	}

	/**
	 * 设置结果描述
	 * @param resultInfos
	 */
	public void setResultInfos(List<FieldInfo> resultInfos) {
		this.resultInfos = resultInfos;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
