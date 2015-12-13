package cn.xdaima.kiso.util;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class WebUtil {
	   /**
     * 获取请求路径
     */
    public static String getRequestPath(HttpServletRequest request) {
        String servletPath = request.getServletPath();
        String pathInfo = StringUtils.defaultIfEmpty(request.getPathInfo(), "");
        return servletPath + pathInfo;
    }
}
