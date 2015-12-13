package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class LongBaseCaster implements TypeCaster<Long> {

	@Override
	public Long cast(String str) {
		if (StringUtils.isBlank(str)) {
			throw new ClassCastException();
		}
		return Long.parseLong(str);
	}

}
