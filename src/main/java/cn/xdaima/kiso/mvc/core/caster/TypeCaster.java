package cn.xdaima.kiso.mvc.core.caster;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月7日 下午9:23:53
 * @description : @param <T>
 */
public interface TypeCaster<T> {
	/**
	 * @param str
	 * @return
	 * @author sunhao
	 * @date 2015年11月7日 下午9:23:55
	 * @description : 
	 */
	T cast(String str);
}
