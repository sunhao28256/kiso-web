package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class StringArraysCaster implements TypeCaster<String[]> {

	@Override
	public String[] cast(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return str.split(",");
	}

}
