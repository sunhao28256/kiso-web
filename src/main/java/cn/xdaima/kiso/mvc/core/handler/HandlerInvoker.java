package cn.xdaima.kiso.mvc.core.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.xdaima.kiso.mvc.entity.Handler;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月30日 下午1:08:00
 * @description :
 */
public interface HandlerInvoker {
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @throws Exception
	 * @author sunhao
	 * @date 2015年10月30日 下午1:12:54
	 * @description : 
	 */
	void invokeHandler(HttpServletRequest request, HttpServletResponse response, Handler handler) throws Exception;
}
