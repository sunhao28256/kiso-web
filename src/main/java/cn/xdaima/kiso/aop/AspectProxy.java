package cn.xdaima.kiso.aop;

import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaima.kiso.aop.proxy.Proxy;
import cn.xdaima.kiso.aop.proxy.ProxyChain;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月8日 上午12:06:57
 * @description : 
 */
public abstract class AspectProxy implements Proxy {

	private static final Logger logger = LoggerFactory.getLogger(AspectProxy.class);

	@Override
	public final Object doProxy(ProxyChain proxyChain) throws Throwable {
		Object result = null;
		Class<?> cls = proxyChain.getTargetClass();
		Method method = proxyChain.getTargetMethod();
		Object[] params = proxyChain.getMethodParams();
		begin();
		try {
			if (intercept(cls, method, params)) {
				before(cls, method, params);
				result = proxyChain.doProxy(proxyChain);
				after(cls, method, params, result);
			} else {
				result = proxyChain.doProxy(proxyChain);
			}
		} catch (Exception e) {
			logger.error("AOP 异常", e);
			error(cls, method, params, e);
			throw e;
		} finally {
			end();
		}
		return result;
	}

	public void begin() {
	}

	public boolean intercept(Class<?> cls, Method method, Object[] params) throws Throwable {
		return true;
	}

	public void before(Class<?> cls, Method method, Object[] params) throws Throwable {
	}

	public void after(Class<?> cls, Method method, Object[] params, Object result) throws Throwable {
	}

	public void error(Class<?> cls, Method method, Object[] params, Throwable e) {
	}

	public void end() {
	}
}
