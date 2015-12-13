package cn.xdaima.kiso.mvc.core.resolver.view;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月2日 下午5:11:58
 * @description : 视图解析器
 */
public interface ViewResolver {
	/**
	 * @param request
	 * @param response
	 * @param actionResult
	 * @author sunhao
	 * @date 2015年11月2日 下午5:14:15
	 * @description : 视图数据解析
	 */
	void resolverView(HttpServletRequest request, HttpServletResponse response, Object actionResult);
}
