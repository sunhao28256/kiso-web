package cn.xdaima.kiso.mvc.core.caster.impl;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class DoubleCaster implements TypeCaster<Double> {

	@Override
	public Double cast(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		return Double.parseDouble(str);
	}

}
