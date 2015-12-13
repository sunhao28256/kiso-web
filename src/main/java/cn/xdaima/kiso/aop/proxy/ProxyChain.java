package cn.xdaima.kiso.aop.proxy;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月7日 下午11:14:33
 * @description : 代理链
 */
public class ProxyChain implements Proxy {
	private final List<Proxy> proxys = new ArrayList<Proxy>();
	private int index = 0;
	private final Class<?> targetClass;
	private final Object targetObject;
	private final Method targetMethod;
	private final MethodProxy methodProxy;
	private final Object[] methodParams;

	public ProxyChain(Class<?> targetClass, Object targetObject, Method targetMethod, MethodProxy methodProxy, Object[] methodParams) {
		super();
		this.targetClass = targetClass;
		this.targetObject = targetObject;
		this.targetMethod = targetMethod;
		this.methodProxy = methodProxy;
		this.methodParams = methodParams;
	}

	public ProxyChain addProxy(Proxy proxy) {
		proxys.add(proxy);
		return this;
	}
	public ProxyChain addProxy(List<Proxy> proxys) {
		proxys.addAll(proxys);
		return this;
	}

	@Override
	public Object doProxy(ProxyChain chain) throws Throwable {
		if (index < proxys.size()) {
			Proxy proxy = proxys.get(index++);
			return proxy.doProxy(chain);
		}
		return methodProxy.invokeSuper(targetObject, methodParams);
	}

	public Class<?> getTargetClass() {
		return targetClass;
	}

	public Object getTargetObject() {
		return targetObject;
	}

	public Object[] getMethodParams() {
		return methodParams;
	}

	public Method getTargetMethod() {
		return targetMethod;
	}

	public MethodProxy getMethodProxy() {
		return methodProxy;
	}

}
