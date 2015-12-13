package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class IntegerCaster implements TypeCaster<Integer> {

	@Override
	public Integer cast(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return Integer.parseInt(str);
	}

}
