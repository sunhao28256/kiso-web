package cn.xdaima.kiso.mvc.core.caster;

import java.util.HashMap;
import java.util.Map;

import cn.xdaima.kiso.mvc.core.caster.impl.DoubleArraysCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.DoubleBaseArraysCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.DoubleBaseCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.DoubleCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.FloatBaseCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.FloatCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.IntBaseCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.IntegerCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.LongBaseCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.LongCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.StringArraysCaster;
import cn.xdaima.kiso.mvc.core.caster.impl.StringCaster;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月5日 下午8:58:18
 * @description :
 */
public class TypeParser {
	/*static Class<?>[] types = { //
			Integer.class,//
			int.class,//
			Integer[].class,//
			int[].class,//
			Float.class,//
			float.class,//
			float[].class,//
			Float[].class,//
			Double.class,//
			double.class,//
			Double[].class,//
			double[].class,//
			Long.class, //
			long.class,//
			Long[].class, //
			long[].class,//
			String.class,
			String[].class
	};*/
	private static final Map<Class<?>, TypeCaster<?>> data = new HashMap<Class<?>, TypeCaster<?>>();
	static {
		data.put(double.class, new DoubleBaseCaster());
		data.put(Double.class, new DoubleCaster());
		data.put(double[].class, new DoubleBaseArraysCaster());
		data.put(Double[].class, new DoubleArraysCaster());
		data.put(float.class, new FloatBaseCaster());
		data.put(Float.class, new FloatCaster());
		data.put(int.class, new IntBaseCaster());
		data.put(Integer.class, new IntegerCaster());
		data.put(long.class, new LongBaseCaster());
		data.put(Long.class, new LongCaster());
		data.put(String.class, new StringCaster());
		data.put(String[].class, new StringArraysCaster());
	}

	public static TypeCaster<?> getTypeCaster(Class<?> clazz) {
		return data.get(clazz);
	}
}
