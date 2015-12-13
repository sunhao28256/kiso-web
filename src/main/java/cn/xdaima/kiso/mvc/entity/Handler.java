package cn.xdaima.kiso.mvc.entity;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Matcher;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月29日 下午5:58:15
 * @description : Controller方法的相关信息
 */
public class Handler {
	private Class<?> controllerClazz;
	private Method controllerMethod;
	private Matcher requestPathMatcher;
	private List<String> pathParamsList;

	public Class<?> getControllerClazz() {
		return controllerClazz;
	}

	public void setControllerClazz(Class<?> controllerClazz) {
		this.controllerClazz = controllerClazz;
	}

	public Method getControllerMethod() {
		return controllerMethod;
	}

	public void setControllerMethod(Method controllerMethod) {
		this.controllerMethod = controllerMethod;
	}

	public Matcher getRequestPathMatcher() {
		return requestPathMatcher;
	}

	public void setRequestPathMatcher(Matcher requestPathMatcher) {
		this.requestPathMatcher = requestPathMatcher;
	}

	public List<String> getPathParamsList() {
		return pathParamsList;
	}

	public void setPathParamsList(List<String> pathParamsList) {
		this.pathParamsList = pathParamsList;
	}

	public Handler(Class<?> controllerClazz, Method controllerMethod, List<String> pathParamsList) {
		super();
		this.controllerClazz = controllerClazz;
		this.controllerMethod = controllerMethod;
		this.pathParamsList = pathParamsList;
	}

}
