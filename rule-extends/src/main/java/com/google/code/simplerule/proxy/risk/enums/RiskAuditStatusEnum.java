package com.google.code.simplerule.proxy.risk.enums;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author sunny
 * @since 2013-5-14
 * @version 1.0.0
 * @comment 风控审核状态
 */
public enum RiskAuditStatusEnum {

    NULL(null, null, null),

    /**
     * 等待审核
     */
    WaitForAudit(100001, "WaitForAudit", "等待审核"),
    
    /**
     * 通过审核
     */
    PassAudit(100002, "AlreadyAudit", "通过审核"),

    /**
     * 未过审核
     */
    NoPassAudit(100003, "NoPassAudit", "未过审核"),

    /**
     * 下一审核
     */
    SwitchToNextAudit(100004, "SwitchToNextAudit", "转入下一审核");
    
    private Integer code;
    private String name;
    private String description;

    /**
     * @param description 中文描述
     */
    private RiskAuditStatusEnum(String description) {
        this.description = description;
    }

    /**
     * @param code        数字编码
     * @param description 中文描述
     */
    private RiskAuditStatusEnum(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @param name        英文编码名称
     * @param description 中文描述
     */
    private RiskAuditStatusEnum(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * @param code        数字编码
     * @param name        英文编码名称
     * @param description 中文描述
     */
    private RiskAuditStatusEnum(Integer code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }


    /**
     * 获取枚举类型数值编码
     */
    public Integer toCode() {
        return this.code == null ? this.ordinal() : this.code;
    }

    /**
     * 获取枚举类型英文编码名称
     */
    public String toName() {
        return this.name == null ? this.name() : this.name;
    }

    /**
     * 获取枚举类型中文描述
     */
    public String toDescription() {
        return this.description;
    }

    /**
     * 获取枚举类型中文描述
     */
    public String toString() {
        return this.description;
    }

    /**
     * 按数值获取对应的枚举类型
     *
     * @param code 数值
     * @return 枚举类型
     */
    public static RiskAuditStatusEnum enumValueOf(Integer code) {
    	RiskAuditStatusEnum[] values = RiskAuditStatusEnum.values();
    	RiskAuditStatusEnum v = NULL;
        for (int i = 0; i < values.length; i++) {
            if (code.equals(values[i].toCode())) {
                v = values[i];
                break;
            }
        }
        return v;
    }

    /**
     * 按英文编码获取对应的枚举类型
     *
     * @param name 英文编码
     * @return 枚举类型
     */
    public static RiskAuditStatusEnum enumValueOf(String name) {
    	RiskAuditStatusEnum[] values = RiskAuditStatusEnum.values();
    	RiskAuditStatusEnum v = NULL;
        for (int i = 0; i < values.length; i++) {
            if (name.equalsIgnoreCase(values[i].toName())) {
                v = values[i];
                break;
            }
        }
        return v;
    }

    /**
     * 获取枚举类型的所有<数字编码,中文描述>对
     *
     * @return
     */
    public static Map<Integer, String> toCodeDescriptionMap() {
        LinkedHashMap<Integer, String> map = new LinkedHashMap<Integer, String>();
        for (int i = 0; i < RiskAuditStatusEnum.values().length; i++) {
            if(RiskAuditStatusEnum.values()[i] != NULL){
                map.put(RiskAuditStatusEnum.values()[i].toCode(), RiskAuditStatusEnum.values()[i].toDescription());
            }
        }
        return map;
    }

    /**
     * 获取枚举类型的所有<英文编码名称,中文描述>对
     *
     * @return
     */
    public static Map<String, String> toNameDescriptionMap() {
        LinkedHashMap<String, String> map = new LinkedHashMap<String, String>();
        for (int i = 0; i < RiskAuditStatusEnum.values().length; i++) {
            if(RiskAuditStatusEnum.values()[i] != NULL){
                map.put(RiskAuditStatusEnum.values()[i].toName(), RiskAuditStatusEnum.values()[i].toDescription());
            }
        }
        return map;
    }

}

