package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class FloatBaseCaster implements TypeCaster<Float> {

	@Override
	public Float cast(String str) {
		if (StringUtils.isBlank(str)) {
			throw new ClassCastException();
		}
		return Float.parseFloat(str);
	}

}
