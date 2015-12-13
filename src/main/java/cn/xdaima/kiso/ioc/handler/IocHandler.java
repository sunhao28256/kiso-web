package cn.xdaima.kiso.ioc.handler;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;

import cn.xdaima.kiso.exception.InitializationError;
import cn.xdaima.kiso.ioc.annotation.Inject;
import cn.xdaima.kiso.mvc.core.BeanFactory;
import cn.xdaima.kiso.util.ClassScanner;
import cn.xdaima.kiso.util.DefaultClassScanner;

public class IocHandler {
	private ClassScanner classScanner = new DefaultClassScanner();

	public void init() {
		Map<Class<?>, Object> beanMap = BeanFactory.getBeanMap();
		try {
			for (Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
				// 获取bean类与bean实例
				Class<?> beanClass = beanEntry.getKey();
				Object beanInstance = beanEntry.getValue();
				Field[] fields = beanClass.getDeclaredFields();
				if (ArrayUtils.isEmpty(fields)) {
					return;
				}
				for (Field beanField : fields) {
					if (!beanField.isAnnotationPresent(Inject.class)) {
						continue;
					}
					// 获取 Bean 字段对应的接口
					Class<?> interfaceClass = beanField.getClass();
					// 获取 Bean 字段对应的实现类
					Class<?> implementClass = findImplementClass(interfaceClass);
					// 从 Bean Map 中获取该实现类对应的实现类实例
					Object implementInstance = beanMap.get(implementClass);
					// 设置该 Bean 字段的值
					if (implementInstance != null) {
						beanField.setAccessible(true); // 将字段设置为 public
						beanField.set(beanInstance, implementInstance); // 设置字段初始值
					} else {
						throw new InitializationError("依赖注入失败！类名：" + beanClass.getSimpleName() + "，字段名：" + interfaceClass.getSimpleName());
					}
				}
			}
		} catch (Exception e) {
			throw new InitializationError("初始化 IocHelper 出错！", e);
		}
	}

	public Class<?> findImplementClass(Class<?> interfaceClass) {
		List<Class<?>> implementClassList = classScanner.getClassListBySuper(interfaceClass);
		if (CollectionUtils.isNotEmpty(implementClassList) && implementClassList.size() == 1) {
			// 获取第一个实现类
			return implementClassList.get(0);
		}
		throw new IllegalArgumentException();
	}
}
