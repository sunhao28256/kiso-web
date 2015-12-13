package cn.xdaima.kiso.mvc.core;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaima.kiso.mvc.core.handler.HandlerInvoker;
import cn.xdaima.kiso.mvc.core.handler.HandlerMapping;
import cn.xdaima.kiso.mvc.core.handler.impl.DefaultHandlerInvoker;
import cn.xdaima.kiso.mvc.core.handler.impl.DefaultHandlerMapping;
import cn.xdaima.kiso.mvc.core.resolver.exception.HandlerExceptionResolver;
import cn.xdaima.kiso.mvc.core.resolver.exception.impl.DefaultHandlerExceptionResolver;
import cn.xdaima.kiso.mvc.entity.Handler;
import cn.xdaima.kiso.util.WebUtil;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月28日 下午1:49:16
 * @description : xweb servlet核心分发器
 */
@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet {

	private static final Logger logger = LoggerFactory.getLogger(DispatcherServlet.class);
	private static final long serialVersionUID = 1L;
	private static final String UTF_8 = "UTF-8";
	private HandlerInvoker handlerInvoker = new DefaultHandlerInvoker();
	private HandlerMapping handlerMapping = new DefaultHandlerMapping();
	private HandlerExceptionResolver exceptionResolver = new DefaultHandlerExceptionResolver();

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置请求编码方式
		request.setCharacterEncoding(UTF_8);
		// // 获取当前请求相关数据
		String currentRequestMethod = request.getMethod();
		String currentRequestPath = WebUtil.getRequestPath(request);
		logger.debug("[xweb] {}:{}", currentRequestMethod, currentRequestPath);
		// 初始化web容器
		XwebContext.init(request, response);
		try {
			Handler handler = handlerMapping.getHandler(currentRequestMethod, currentRequestPath);
			if (handler==null) {
				//返回 404
			}
			handlerInvoker.invokeHandler(request, response, handler);
		} catch (Exception e) {
			// 异常处理
			logger.error("", e);
			exceptionResolver.resolveHandlerException(request, response, e);
		}finally{
			XwebContext.destroy();//销毁
		}
	}

}
