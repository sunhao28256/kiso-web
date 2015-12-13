package cn.xdaima.kiso.util;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月28日 下午2:40:42
 * @description : 类扫描器
 */
public interface ClassScanner {

	/**
	 * 获取指定包名中的所有类
	 */
	List<Class<?>> getClassList(final String packageName);

	/**
	 * 获取指定包名中指定注解的相关类
	 */
	List<Class<?>> getClassListByAnnotation(final String packageName,final Class<? extends Annotation> annotationClass);

	/**
	 * 获取指定包名中指定父类或接口的相关类
	 */
	List<Class<?>> getClassListBySuper(final String packageName,final Class<?> superClass);

	List<Class<?>> getClassListBySuper(final Class<?> superClass);
}
