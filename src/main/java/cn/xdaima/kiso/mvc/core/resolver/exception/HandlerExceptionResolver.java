package cn.xdaima.kiso.mvc.core.resolver.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月3日 上午9:54:40
 * @description : 异常解析器
 */
public interface HandlerExceptionResolver {
	 void resolveHandlerException(HttpServletRequest request, HttpServletResponse response, Exception e);
}
