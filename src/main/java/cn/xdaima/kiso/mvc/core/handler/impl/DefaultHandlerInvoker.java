package cn.xdaima.kiso.mvc.core.handler.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import cn.xdaima.kiso.mvc.core.BeanFactory;
import cn.xdaima.kiso.mvc.core.caster.TypeParser;
import cn.xdaima.kiso.mvc.core.handler.HandlerInvoker;
import cn.xdaima.kiso.mvc.core.resolver.view.ViewResolver;
import cn.xdaima.kiso.mvc.core.resolver.view.impl.DefaultViewResolver;
import cn.xdaima.kiso.mvc.entity.Handler;
import cn.xdaima.kiso.mvc.entity.Param;

import com.thoughtworks.paranamer.BytecodeReadingParanamer;
import com.thoughtworks.paranamer.Paranamer;

public class DefaultHandlerInvoker implements HandlerInvoker {

	private ViewResolver viewResolver = new DefaultViewResolver(); 
	@Override
	public void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception {
		// 获取 Action 相关信息
		Class<?> controllerClazz = handler.getControllerClazz();
		Method controllerMethod = handler.getControllerMethod();
		Object bean = BeanFactory.getBean(controllerClazz);
		// 创建 controller 方法的参数列表
		List<Object> actionMethodParamList = createControllerMethodParamList(request, handler);
		Object actionMethodResult = invokeActionMethod(controllerMethod, bean, actionMethodParamList);
		viewResolver.resolverView(request, response, actionMethodResult);
	}

	private List<Object> createControllerMethodParamList(HttpServletRequest request, Handler handler) {
		// 定义参数列表
		// 获取 Action 方法参数类型
		Class<?>[] actionParamTypes = handler.getControllerMethod().getParameterTypes();
		Map<String, Object> pathParamMap = createPathParamList(handler, actionParamTypes);
		Map<String, String> requestParamMap = getRequestParamMap(request);
		List<Object> paramList = new ArrayList<Object>(requestParamMap.size() + pathParamMap.size());
		// 获取参数名称列表
		Map<String, Param> paramsMap = getParameterNames(handler.getControllerMethod());
		Set<String> paramsMapKeys = paramsMap.keySet();
		for (String paramName : paramsMapKeys) {
			String value = requestParamMap.get(paramName);// 参数值
			Param param = null;
			if (StringUtils.isEmpty(value) && !pathParamMap.containsKey(paramName)) {
				// 如果没有参数 根据类型创建默认对象
				param = paramsMap.get(paramName);
				param.setParamValue("");
				paramList.add(param.getParamValue());
			} else if (pathParamMap.containsKey(paramName)) {
				paramList.add(pathParamMap.get(paramName));
			} else {
				param = paramsMap.get(paramName);
				param.setParamValue(requestParamMap.get(paramName));// TODO
				paramList.add(param.getParamValue());
			}
		}
		return paramList;
	}

	private static final ConcurrentHashMap<Method, Map<String, Param>> methodsParameterNames = new ConcurrentHashMap<Method, Map<String, Param>>();

	public static Map<String, Param> getParameterNames(Method method) {
		Map<String, Param> params = methodsParameterNames.get(method.toString());
		if (MapUtils.isNotEmpty(params)) {
			return params;
		}
		Paranamer paranamer = new BytecodeReadingParanamer();
		String[] parameterNames = paranamer.lookupParameterNames(method);
		Class<?>[] parameterTypes = method.getParameterTypes();
		params = new LinkedHashMap<String, Param>(parameterNames.length);
		for (int i = 0; i < parameterNames.length; i++) {
			params.put(parameterNames[i], new Param(parameterNames[i], parameterTypes[i], null/* 参数值 */));
		}
		methodsParameterNames.put(method, params);
		return params;
	}

	private Map<String, String> getRequestParamMap(HttpServletRequest request) {
		Map<String, String[]> parameterMap = request.getParameterMap();
		Map<String, String> paramMap = new LinkedHashMap<String, String>();
		Set<Entry<String, String[]>> entrySet = parameterMap.entrySet();
		for (Entry<String, String[]> entry : entrySet) {
			if (ArrayUtils.isEmpty(entry.getValue())) {
				continue;
			}
			String[] paramValues = entry.getValue();
			if (paramValues.length == 1) {
				paramMap.put(entry.getKey(), paramValues[0]);
			} else {
				StringBuilder paramValue = new StringBuilder("");
				for (int i = 0; i < paramValues.length; i++) {
					paramValue.append(paramValues[i]);
					if (i != paramValues.length - 1) {
						paramValue.append(",");
					}
				}
				paramMap.put(entry.getKey(), paramValue.toString());
			}
		}
		return paramMap;
	}

	private Object invokeActionMethod(Method actionMethod, Object actionInstance, List<Object> actionMethodParamList) throws IllegalAccessException, InvocationTargetException {
		// 通过反射调用 Action 方法
		actionMethod.setAccessible(true); // 取消类型安全检测（可提高反射性能）
		return actionMethod.invoke(actionInstance, actionMethodParamList.toArray());
	}

	private Map<String, Object> createPathParamList(Handler handler, Class<?>[] actionParamTypes) {
		// 返回参数列表
		List<String> pathParamNamesList = handler.getPathParamsList();
		if (CollectionUtils.isEmpty(pathParamNamesList)) {
			return Collections.emptyMap();
		}
		// 定义参数列表
		Map<String, Object> paramMap = new HashMap<String, Object>();
		List<Object> pathParamList = new ArrayList<Object>();
		// 遍历正则表达式中所匹配的组
		for (int i = 1; i <= handler.getRequestPathMatcher().groupCount(); i++) {
			// 获取请求参数
			String param = handler.getRequestPathMatcher().group(i);
			// 获取参数类型（支持四种类型：int/Integer、long/Long、double/Double、String）
			Class<?> paramType = actionParamTypes[i - 1];
			pathParamList.add(TypeParser.getTypeCaster(paramType).cast(param));
		}
		for (int i = 0; i < pathParamNamesList.size(); i++) {
			paramMap.put(pathParamNamesList.get(i), pathParamList.get(i));
		}
		return paramMap;
	}
}
