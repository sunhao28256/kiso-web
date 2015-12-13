package cn.xdaima.kiso.mvc.core.caster.impl;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class StringCaster implements TypeCaster<String> {

	@Override
	public String cast(String str) {
		return str;
	}

}
