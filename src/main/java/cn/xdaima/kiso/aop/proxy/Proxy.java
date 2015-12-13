package cn.xdaima.kiso.aop.proxy;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月8日 上午12:06:50
 * @description : 
 */
public interface Proxy {

	/**
	 * @param chain
	 * @return
	 * @throws Throwable
	 * @author sunhao
	 * @date 2015年11月8日 上午12:07:09
	 * @description : 
	 */
	Object doProxy(ProxyChain chain) throws Throwable;
}
