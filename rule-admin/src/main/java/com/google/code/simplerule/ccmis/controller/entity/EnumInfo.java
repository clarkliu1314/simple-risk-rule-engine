package com.google.code.simplerule.ccmis.controller.entity;



import java.io.Serializable;


public class EnumInfo implements Serializable {

   
    private String type;

    private String code;

    private String name;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
