package cn.xdaima.kiso.mvc.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月2日 下午4:12:48
 * @description :
 */
public class XwebContext {
	private static final ThreadLocal<XwebContext> xwebContext = new ThreadLocal<XwebContext>();
	private HttpServletRequest request;
	private HttpServletResponse response;

	public static void init(HttpServletRequest request, HttpServletResponse response) {
		XwebContext context = new XwebContext();
		context.request = request;
		context.response = response;
		xwebContext.set(context);
	}

	public static HttpServletRequest getRequest() {
		return xwebContext.get().request;
	}

	public static HttpServletResponse getResponse() {
		return xwebContext.get().response;
	}

	public static void destroy() {
		xwebContext.remove();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static javax.servlet.ServletContext getServletContext() {
		return getRequest().getServletContext();
	}

}
