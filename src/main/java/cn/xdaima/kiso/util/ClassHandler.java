package cn.xdaima.kiso.util;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月29日 上午10:41:56
 * @description :
 */
public interface ClassHandler {
	/**
	 * @param clazz
	 * @return
	 * @author sunhao
	 * @date 2015年10月29日 上午10:42:24
	 * @description :  判断是否为可添加的类
	 */
	public abstract boolean isCanAdd(final Class<?> clazz);
}
