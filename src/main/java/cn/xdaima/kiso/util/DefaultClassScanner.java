package cn.xdaima.kiso.util;

import java.lang.annotation.Annotation;
import java.util.List;

public class DefaultClassScanner implements ClassScanner {

	@Override
	public List<Class<?>> getClassList(final String packageName) {
		return new ClassTemplate(packageName, new ClassHandler() {
			@Override
			public boolean isCanAdd(Class<?> clazz) {
				 String className = clazz.getName();
	                String pkgName = className.substring(0, className.lastIndexOf("."));
	                return pkgName.startsWith(packageName);
			}
		}).scannerClassList();
	}

	@Override
	public List<Class<?>> getClassListByAnnotation(final String packageName,final  Class<? extends Annotation> annotationClass) {
		return new ClassTemplate(packageName, new ClassHandler() {

			@Override
			public boolean isCanAdd(Class<?> clazz) {
				  return clazz.isAnnotationPresent(annotationClass);
			}
		}).scannerClassList();
	}

	@Override
	public List<Class<?>> getClassListBySuper(final String packageName,final Class<?> superClass) {
		return new ClassTemplate(packageName, new ClassHandler() {

			@Override
			public boolean isCanAdd(Class<?> clazz) {
				 return superClass.isAssignableFrom(clazz) && !superClass.equals(clazz);
			}
		}).scannerClassList();
	}

	private String basePackage;

	@Override
	public List<Class<?>> getClassListBySuper(final Class<?> superClass) {
		return getClassListBySuper(basePackage, superClass);
	}

	public List<Class<?>> getClassListByAnnotation(Class<? extends Annotation> annotation) {
		return getClassListByAnnotation(basePackage, annotation);
	}

}
