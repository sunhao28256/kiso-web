package cn.xdaima.kiso.mvc.core;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaima.kiso.mvc.annotation.Controller;
import cn.xdaima.kiso.mvc.annotation.Request;
import cn.xdaima.kiso.mvc.constant.RequestType;
import cn.xdaima.kiso.mvc.entity.Handler;
import cn.xdaima.kiso.mvc.entity.Requester;
import cn.xdaima.kiso.util.ClassScanner;
import cn.xdaima.kiso.util.DefaultClassScanner;

public class ActionHelper {
	protected final static Logger logger = LoggerFactory.getLogger(ClassScanner.class);
	private static String basePackage = "cn.xdaima.xweb";
	private static final Map<Requester, Handler> actionMap = new LinkedHashMap<Requester, Handler>();

	private static ClassScanner classScanner = new DefaultClassScanner();

	public static void init() {
		// 扫描初始化basePackage中所有加@Action的类
		List<Class<?>> controllerClassList = getControllerClassList();
		for (Class<?> clazz : controllerClassList) {
			try {
				// 获取所有controller的所有方法
				Method[] methods = clazz.getDeclaredMethods();
				if (ArrayUtils.isEmpty(methods)) {
					continue;
				}
				for (Method method : methods) {
					if (method.isAnnotationPresent(Request.class)) {
						String requestPath = method.getAnnotation(Request.class).value();
						List<String> pathParamsList = null;
						if (requestPath.matches(".+\\{\\w+\\}.*")) {
							pathParamsList = new ArrayList<String>();
							Matcher requestPathMatcher = Pattern.compile("\\{\\w+\\}").matcher(requestPath);
							while (requestPathMatcher.find()) {
								String group = requestPathMatcher.group();
								String paramName = group.substring(1, group.length() - 1);
								pathParamsList.add(paramName);
							}

							requestPath = requestPath.replaceAll("\\{\\w+\\}", "(\\\\w+)");
						}
						getActionMap().put(new Requester(RequestType.GET, requestPath), new Handler(clazz, method, pathParamsList));
					}

				}
			} catch (Exception e) {
				logger.error("", e);
			}
		}
	}

	public static List<Class<?>> getControllerClassList() {
		return classScanner.getClassListByAnnotation(basePackage, Controller.class);
	}

	public static Map<Requester, Handler> getActionMap() {
		return actionMap;
	}

}
