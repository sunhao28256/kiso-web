package cn.xdaima.kiso.mvc.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.xdaima.kiso.aop.annotation.Aspect;
import cn.xdaima.kiso.ioc.annotation.Bean;
import cn.xdaima.kiso.ioc.annotation.Service;
import cn.xdaima.kiso.mvc.annotation.Controller;
import cn.xdaima.kiso.util.ClassScanner;
import cn.xdaima.kiso.util.DefaultClassScanner;

/**
 * 初始化相关 Bean 类
 * 
 * @since 1.0
 */
public class BeanFactory {

	/**
	 * Bean Map（Bean 类 => Bean 实例）
	 */
	private static final Map<Class<?>, Object> beanMap = new HashMap<Class<?>, Object>();

	private static ClassScanner classScanner = new DefaultClassScanner();
	public static void init() {
		try {

			// 获取应用包路径下所有的类
			List<Class<?>> classList = classScanner.getClassList("cn.xdaima.xweb");
			for (Class<?> cls : classList) {
				// 处理带有 Bean/Service/Action/Aspect 注解的类
				if (cls.isAnnotationPresent(Bean.class) || cls.isAnnotationPresent(Service.class) || cls.isAnnotationPresent(Controller.class) || cls.isAnnotationPresent(Aspect.class)) {
					// 创建 Bean 实例
					Object beanInstance = cls.newInstance();
					// 将 Bean 实例放入 Bean Map 中（键为 Bean 类，值为 Bean 实例）
					beanMap.put(cls, beanInstance);
				}
			}
		} catch (Exception e) {
			throw new Error("初始化 BeanHelper 出错！", e);
		}
	}

	/**
	 * 获取 Bean Map
	 */
	public static Map<Class<?>, Object> getBeanMap() {
		return beanMap;
	}

	/**
	 * 获取 Bean 实例
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(Class<T> cls) {
		if (!beanMap.containsKey(cls)) {
			throw new RuntimeException("无法根据类名获取实例！" + cls);
		}
		return (T) beanMap.get(cls);
	}

	/**
	 * 设置 Bean 实例
	 */
	public static void setBean(Class<?> cls, Object obj) {
		beanMap.put(cls, obj);
	}
}
