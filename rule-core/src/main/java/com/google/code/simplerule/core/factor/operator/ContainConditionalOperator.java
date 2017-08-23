package com.google.code.simplerule.core.factor.operator;

import java.util.List;

import com.google.code.simplerule.core.factor.ConditionalOperator;

/**
 * 包含操作符
 * @author drizzt
 *
 */
public class ContainConditionalOperator implements ConditionalOperator {

	@Override
	public boolean operate(Object... objs) {
		String[] list = getList(objs[1]);
		Object setValue = objs[0];
		return contains(list, setValue);
	}

	private boolean contains(String[] list, Object val) {
		if (val == null)
			return false;
		for (String s : list) {
			if (s.trim().equals(val.toString().trim()))
				return true;
		}
		return false;
	}

	private String[] getList(Object obj) {
		if (obj == null)
			return null;
		return obj.toString().split(",");
	}

	@Override
	public String getName() {
		return "包含";
	}

	@Override
	public String getDescription() {
		return "包含(in)";
	}

}
