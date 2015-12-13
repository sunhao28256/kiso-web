package cn.xdaima.kiso.mvc.core.caster.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.caster.TypeCaster;

public class DoubleArraysCaster implements TypeCaster<Double[]> {

	@Override
	public Double[] cast(String str) {
		if (StringUtils.isBlank(str)) {
			return null;
		}
		String[] arrays = str.split(",");
		List<Double> doubles = new ArrayList<Double>(arrays.length);
		for (String s : arrays) {
			if (StringUtils.isBlank(str)) {
				doubles.add(null);
			} else {
				doubles.add(Double.parseDouble(s));
			}
		}
		return doubles.toArray(new Double[] {});
	}
}
