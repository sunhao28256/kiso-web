package cn.xdaima.kiso.aop.handler;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.aop.AspectProxy;
import cn.xdaima.kiso.aop.annotation.Aspect;
import cn.xdaima.kiso.aop.proxy.Proxy;
import cn.xdaima.kiso.aop.proxy.ProxyManager;
import cn.xdaima.kiso.mvc.core.BeanFactory;
import cn.xdaima.kiso.util.ClassUtil;
import cn.xdaima.kiso.util.DefaultClassScanner;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月7日 下午10:45:03
 * @description :
 */
public class AopHandler {
	static DefaultClassScanner classScanner = new DefaultClassScanner();

	void init() {
		try {
			Map<Class<?>, List<Class<?>>> proxyMap = createProxyMap();
			Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
			// 遍历 Target Map
			for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
				// 分别获取 map 中的 key 与 value
				Class<?> targetClass = targetEntry.getKey();
				List<Proxy> proxyList = targetEntry.getValue();
				// 创建代理实例
				Object proxyInstance = ProxyManager.createProxy(targetClass, proxyList);
				// 用代理实例覆盖目标实例，并放入 Bean 容器中
				BeanFactory.setBean(targetClass, proxyInstance);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, List<Class<?>>> proxyMap) throws InstantiationException, IllegalAccessException {
		Map<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
		// 遍历 Proxy Map
		for (Map.Entry<Class<?>, List<Class<?>>> proxyEntry : proxyMap.entrySet()) {
			// 分别获取 map 中的 key 与 value
			Class<?> proxyClass = proxyEntry.getKey();
			List<Class<?>> targetClassList = proxyEntry.getValue();
			// 遍历目标类列表
			for (Class<?> targetClass : targetClassList) {
				// 创建代理类（切面类）实例
				Proxy baseAspect = (Proxy) proxyClass.newInstance();
				// 初始化 Target Map
				if (targetMap.containsKey(targetClass)) {
					targetMap.get(targetClass).add(baseAspect);
				} else {
					List<Proxy> baseAspectList = new ArrayList<Proxy>();
					baseAspectList.add(baseAspect);
					targetMap.put(targetClass, baseAspectList);
				}
			}
		}
		return targetMap;
	}

	private static Map<Class<?>, List<Class<?>>> createProxyMap() throws Exception {
		Map<Class<?>, List<Class<?>>> proxyMap = new LinkedHashMap<Class<?>, List<Class<?>>>();
		// 添加相关代理
		addAspectProxy(proxyMap); // 切面代理

		return proxyMap;
	}

	static String PLUGIN_PACKAGE = "org.smart4j.plugin";

	private static void addAspectProxy(Map<Class<?>, List<Class<?>>> proxyMap) {
		List<Class<?>> aspectProxyClassList = classScanner.getClassListBySuper(AspectProxy.class);
		aspectProxyClassList.addAll(classScanner.getClassListBySuper(PLUGIN_PACKAGE, AspectProxy.class));
		for (Class<?> aspectProxyClass : aspectProxyClassList) {
			if (aspectProxyClass.isAnnotationPresent(Aspect.class)) {
				// 获取 Aspect 注解
				Aspect aspect = aspectProxyClass.getAnnotation(Aspect.class);
				// 创建目标类列表
				List<Class<?>> targetClassList = createTargetClassList(aspect);
				// 初始化 Proxy Map
				proxyMap.put(aspectProxyClass, targetClassList);
			}
		}
	}

	private static List<Class<?>> createTargetClassList(Aspect aspect) {
		List<Class<?>> targetClassList = new ArrayList<Class<?>>();
		// 获取 Aspect 注解的相关属性
		String pkg = aspect.pkg();
		String cls = aspect.cls();
		Class<? extends Annotation> annotation = aspect.annotation();
		// 若包名不为空，则需进一步判断类名是否为空
		if (StringUtils.isNotEmpty(pkg)) {
			if (StringUtils.isNotEmpty(cls)) {
				// 若类名不为空，则仅添加该类
				targetClassList.add(ClassUtil.loadClass(pkg + "." + cls, false));
			} else {
				// 若注解不为空且不是 Aspect 注解，则添加指定包名下带有该注解的所有类
				if (annotation != null && !annotation.equals(Aspect.class)) {
					targetClassList.addAll(classScanner.getClassListByAnnotation(pkg, annotation));
				} else {
					// 否则添加该包名下所有类
					targetClassList.addAll(classScanner.getClassList(pkg));
				}
			}
		} else {
			// 若注解不为空且不是 Aspect 注解，则添加应用包名下带有该注解的所有类
			if (annotation != null && !annotation.equals(Aspect.class)) {
				targetClassList.addAll(classScanner.getClassListByAnnotation(annotation));
			}
		}
		return targetClassList;
	}
}
