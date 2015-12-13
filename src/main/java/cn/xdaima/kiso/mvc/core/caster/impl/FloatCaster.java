package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class FloatCaster implements TypeCaster<Float> {

	@Override
	public Float cast(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return Float.parseFloat(str);
	}

}
