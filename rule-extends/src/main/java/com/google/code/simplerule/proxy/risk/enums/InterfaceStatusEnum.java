package com.google.code.simplerule.proxy.risk.enums;


import java.util.LinkedHashMap;
import java.util.Map;

public enum InterfaceStatusEnum
{
  NULL(null, null, null), 
  INACTIVE(Integer.valueOf(0), "INACTIVE", "禁用"), 
  ACTIVE(Integer.valueOf(1), "ACTIVE", "启用");

  private Integer code;
  private String name;
  private String description;

  private InterfaceStatusEnum(String description)
  {
    this.description = description;
  }

  private InterfaceStatusEnum(Integer code, String description)
  {
    this.code = code;
    this.description = description;
  }

  private InterfaceStatusEnum(String name, String description)
  {
    this.name = name;
    this.description = description;
  }

  private InterfaceStatusEnum(Integer code, String name, String description)
  {
    this.code = code;
    this.name = name;
    this.description = description;
  }

  public Integer toCode()
  {
    return Integer.valueOf(this.code == null ? ordinal() : this.code.intValue());
  }

  public String toName()
  {
    return this.name == null ? name() : this.name;
  }

  public String toDescription()
  {
    return this.description;
  }

  public String toString()
  {
    return this.description;
  }

  public static InterfaceStatusEnum enumValueOf(Integer code)
  {
    InterfaceStatusEnum[] values = values();
    InterfaceStatusEnum v = NULL;
    for (int i = 1; i < values.length; i++) {
      if ((code != null) && (code.equals(values[i].toCode()))) {
        v = values[i];
        break;
      }
    }
    return v;
  }

  public static InterfaceStatusEnum enumValueOf(String name)
  {
    InterfaceStatusEnum[] values = values();
    InterfaceStatusEnum v = NULL;
    for (int i = 0; i < values.length; i++) {
      if ((name != null) && (name.equalsIgnoreCase(values[i].toName()))) {
        v = values[i];
        break;
      }
    }
    return v;
  }

  public static Map<Integer, String> toCodeDescriptionMap()
  {
    LinkedHashMap map = new LinkedHashMap();
    for (int i = 0; i < values().length; i++) {
      if (values()[i] != NULL) {
        map.put(values()[i].toCode(), values()[i].toDescription());
      }
    }
    return map;
  }

  public static Map<String, String> toNameDescriptionMap()
  {
    LinkedHashMap map = new LinkedHashMap();
    for (int i = 0; i < values().length; i++) {
      if (values()[i] != NULL) {
        map.put(values()[i].toName(), values()[i].toDescription());
      }
    }
    return map;
  }
}