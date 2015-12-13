package cn.xdaima.kiso.mvc.core.resolver.exception.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaima.kiso.mvc.core.resolver.exception.HandlerExceptionResolver;

public class DefaultHandlerExceptionResolver implements HandlerExceptionResolver {
	private static final Logger logger = LoggerFactory.getLogger(DefaultHandlerExceptionResolver.class);

	@Override
	public void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e) {
		// 判断异常原因
		Throwable cause = e.getCause();
		if (cause == null) {
			logger.error(e.getMessage(), e);
			return;
		}
		if (isAJAX(request)) {
			sendError(HttpServletResponse.SC_FORBIDDEN, "", response);
		}else{
			sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, cause.getMessage(), response);
		}
	}
    /**
     * 判断是否为 AJAX 请求
     */
    public static boolean isAJAX(HttpServletRequest request) {
        return request.getHeader("X-Requested-With") != null;
    }
    /**
     * 发送错误代码
     */
    public static void sendError(int code, String message, HttpServletResponse response) {
        try {
            response.sendError(code, message);
        } catch (Exception e) {
            logger.error("发送错误代码出错！", e);
            throw new RuntimeException(e);
        }
    }
}
