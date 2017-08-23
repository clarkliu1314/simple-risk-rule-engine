package com.google.code.simplerule.proxy.risk.enums;

import java.util.Map;

public class EnumFactory {

	public static Map getEnumMap(String enumName, String flag) {
		if ("PayInterfaceStatusEnum".equalsIgnoreCase(enumName)) {
			if ("name".equalsIgnoreCase(flag)) {
				return InterfaceStatusEnum.toNameDescriptionMap();
			} else if ("code".equalsIgnoreCase(flag)) {
				return InterfaceStatusEnum.toCodeDescriptionMap();
			}
		}else if ("BlackListTypeEnum".equalsIgnoreCase(enumName)) {
			if ("name".equalsIgnoreCase(flag)) {
				return BlackListTypeEnum.toNameDescriptionMap();
			} else if ("code".equalsIgnoreCase(flag)) {
				return BlackListTypeEnum.toCodeDescriptionMap();
			}
		}else if ("InterfaceStatusEnum".equalsIgnoreCase(enumName)) {
			if ("name".equalsIgnoreCase(flag)) {
				return InterfaceStatusEnum.toNameDescriptionMap();
			} else if ("code".equalsIgnoreCase(flag)) {
				return InterfaceStatusEnum.toCodeDescriptionMap();
			}
		}else if ("RiskAuditStatusEnum".equalsIgnoreCase(enumName)) {
			if ("name".equalsIgnoreCase(flag)) {
				return RiskAuditStatusEnum.toNameDescriptionMap();
			} else if ("code".equalsIgnoreCase(flag)) {
				return RiskAuditStatusEnum.toCodeDescriptionMap();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		System.out.println(getEnumMap("PayInterfaceStatusEnum", "code"));
	}

}
