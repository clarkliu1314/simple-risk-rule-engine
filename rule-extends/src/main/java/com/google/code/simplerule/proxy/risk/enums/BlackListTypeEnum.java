package com.google.code.simplerule.proxy.risk.enums;

import java.util.LinkedHashMap;
import java.util.Map;

public enum BlackListTypeEnum {

	  NULL(null, null, null), 
	  CARD(Integer.valueOf(0), "idcard", "证件号码"), 
	  IDCARD(Integer.valueOf(1), "card", "银行卡号"),
	  MOBILE(Integer.valueOf(2), "mobile", "手机号"),
	  ACCOUNT(Integer.valueOf(3), "account", "个人账户"),
	  NAME(Integer.valueOf(4), "name", "消费者姓名"),
	  EMAIL(Integer.valueOf(5), "email", "邮箱");



	  

	  private Integer code;
	  private String name;
	  private String description;

	  private BlackListTypeEnum(String description)
	  {
	    this.description = description;
	  }

	  private BlackListTypeEnum(Integer code, String description)
	  {
	    this.code = code;
	    this.description = description;
	  }

	  private BlackListTypeEnum(String name, String description)
	  {
	    this.name = name;
	    this.description = description;
	  }

	  private BlackListTypeEnum(Integer code, String name, String description)
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

	  public static BlackListTypeEnum enumValueOf(Integer code)
	  {
	    BlackListTypeEnum[] values = values();
	    BlackListTypeEnum v = NULL;
	    for (int i = 1; i < values.length; i++) {
	      if ((code != null) && (code.equals(values[i].toCode()))) {
	        v = values[i];
	        break;
	      }
	    }
	    return v;
	  }

	  public static BlackListTypeEnum enumValueOf(String name)
	  {
	    BlackListTypeEnum[] values = values();
	    BlackListTypeEnum v = NULL;
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
