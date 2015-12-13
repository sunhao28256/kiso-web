package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class LongCaster implements TypeCaster<Long> {

	@Override
	public Long cast(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return Long.parseLong(str);
	}

}
