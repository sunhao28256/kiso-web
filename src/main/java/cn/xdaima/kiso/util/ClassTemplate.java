package cn.xdaima.kiso.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public  class ClassTemplate {

	protected final Logger logger = LoggerFactory.getLogger(ClassScanner.class);
	private ClassHandler classHandler;
	private String packageName;

	public ClassTemplate(String packageName, ClassHandler classHandler) {
		super();
		this.classHandler = classHandler;
		this.packageName = packageName;
	}

	public List<Class<?>> scannerClassList() {
		List<String> classNameList = new ArrayList<String>();
		try {
			Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(packageName.replace(".", "/"));
			URL url = null;
			while (resources.hasMoreElements()) {
				url = (URL) resources.nextElement();
				if (url != null) {
					// 获取协议名称file jar
					String protocol = url.getProtocol();
					if ("file".equals(protocol)) {
						// 在java中获取文件路径的时候，有时候会获取到空格，但是在中文编码环境下，空格会变成“%20”从而使得路径错误
						String packagePath = url.getPath().replaceAll("%20", " ");
						packagePath = URLDecoder.decode(packagePath, "utf-8");
						List<String> subClassNameList = getClassNameList(packageName, packagePath);
						if (CollectionUtils.isNotEmpty(subClassNameList)) {
							classNameList.addAll(subClassNameList);
						}
					} else if ("jar".equals(protocol)) {
						// 若在 jar 包中，则解析 jar 包中的 entry
						JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
						JarFile jarFile = jarURLConnection.getJarFile();
						Enumeration<JarEntry> jarEntries = jarFile.entries();
						while (jarEntries.hasMoreElements()) {
							JarEntry jarEntry = jarEntries.nextElement();
							String jarEntryName = jarEntry.getName();
							// 判断该 entry 是否为 class
							if (jarEntryName.endsWith(".class")) {
								// 获取类名
								String className = jarEntryName.substring(0, jarEntryName.lastIndexOf(".")).replaceAll("/", ".");
								// 执行添加类操作
								classNameList.add(className);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			logger.error("获取类出错", e);
		}
		if (CollectionUtils.isEmpty(classNameList)) {
			return Collections.emptyList();
		}
		//
		List<Class<?>> classList = new ArrayList<Class<?>>();
		for (String className : classNameList) {
			Class<?> clazz = createClass(className);
			if (classHandler.isCanAdd(clazz)) {
				classList.add(clazz);
			}
		}
		return classList;
	};

	public List<String> getClassNameList(String packageName, String packagePath) {
		return getClassNames(packageName, packagePath, new ArrayList<String>());
	}

	private List<String> getClassNames(String packageName, String packagePath, List<String> classNameList) {
		if (classNameList == null) {
			classNameList = new ArrayList<String>();
		}
		File[] files = new File(packagePath).listFiles(new FileFilter() {
			@Override
			public boolean accept(File file) {
				return (file.isFile() && file.getName().endsWith(".class")) || file.isDirectory();
			}
		});
		String fileName = "";
		for (File file : files) {
			fileName = file.getName();
			if (file.isFile()) {
				// 获取类名
				String className = fileName.substring(0, fileName.lastIndexOf("."));
				if (StringUtils.isNotEmpty(packageName)) {
					className = packageName + "." + className;
				}
				classNameList.add(className);
			} else {
				// 获取子包
				String subPackagePath = fileName;
				if (StringUtils.isNotEmpty(packagePath)) {
					subPackagePath = packagePath + "/" + subPackagePath;
				}
				// 子包名
				String subPackageName = fileName;
				if (StringUtils.isNotEmpty(packageName)) {
					subPackageName = packageName + "." + subPackageName;
				}
				// 递归调用
				getClassNames(subPackageName, subPackagePath, classNameList);
			}
		}
		return classNameList;
	}

	public Class<?> createClass(String className) {
		try {
			Class<?> clazz = Class.forName(className);
			return clazz;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	};

}