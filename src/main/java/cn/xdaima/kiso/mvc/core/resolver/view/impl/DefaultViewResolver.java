package cn.xdaima.kiso.mvc.core.resolver.view.impl;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;

import cn.xdaima.kiso.mvc.core.resolver.view.ViewResolver;
import cn.xdaima.kiso.mvc.entity.ViewModel;

import com.alibaba.fastjson.JSON;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月2日 下午5:15:55
 * @description : 默认的视图解析器
 */
public class DefaultViewResolver implements ViewResolver {
	private static final String JSP_BASE_PATH = "/";

	@Override
	public void resolverView(HttpServletRequest request, HttpServletResponse response, Object actionResult) {
		if (actionResult == null) {
			return;
		}
		// 跳转到页面
		if (actionResult instanceof ViewModel) {
			ViewModel viewModel = (ViewModel) actionResult;
			if (viewModel.isRedirect()) {
				redirectRequest(viewModel.getPath(), request, response);
			} else {
				String path = JSP_BASE_PATH + viewModel.getPath();
				Map<String, Object> data = viewModel.getData();
				if (MapUtils.isNotEmpty(data)) {
					for (Map.Entry<String, Object> entry : data.entrySet()) {
						request.setAttribute(entry.getKey(), entry.getValue());
					}
				}
				forwardRequest(path, request, response);
			}
		}else{
			writeJSON(response, actionResult);
		}
		// 若为json

	}

	public static void redirectRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			response.sendRedirect(request.getContextPath() + path);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 转发请求
	 */
	public static void forwardRequest(String path, HttpServletRequest request, HttpServletResponse response) {
		try {
			request.getRequestDispatcher(path).forward(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将数据以 JSON 格式写入响应中
	 */
	public static void writeJSON(HttpServletResponse response, Object data) {
		try {
			// 设置响应头
			response.setContentType("application/json"); // 指定内容类型为 JSON 格式
			response.setCharacterEncoding("UTF-8"); // 防止中文乱码
			// 向响应中写入数据
			PrintWriter writer = response.getWriter();
			writer.write(JSON.toJSONString(data)); // 转为 JSON 字符串
			writer.flush();
			writer.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
