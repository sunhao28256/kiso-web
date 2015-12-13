package cn.xdaima.kiso.mvc.core.handler.impl;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.xdaima.kiso.mvc.constant.RequestType;
import cn.xdaima.kiso.mvc.core.ActionHelper;
import cn.xdaima.kiso.mvc.core.handler.HandlerMapping;
import cn.xdaima.kiso.mvc.entity.Handler;
import cn.xdaima.kiso.mvc.entity.Requester;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月30日 下午1:17:05
 * @description : 默认映射处理器
 */
public class DefaultHandlerMapping implements HandlerMapping{

	@Override
	public Handler getHandler(String currentRequestType, String currentRequestPath) {
		 // 定义一个 Handler
        Handler handler = null;
        // 获取并遍历 Action 映射
        Map<Requester, Handler> actionMap = ActionHelper.getActionMap();
        for (Map.Entry<Requester, Handler> actionEntry : actionMap.entrySet()) {
            // 从 Requester 中获取 Request 相关属性
            Requester requester = actionEntry.getKey();
            RequestType requestType = requester.getRequestType();
            String requestPath = requester.getRequestPath(); // 正则表达式
            // 获取请求路径匹配器（使用正则表达式匹配请求路径并从中获取相应的请求参数）
            Matcher requestPathMatcher = Pattern.compile(requestPath).matcher(currentRequestPath);
            // 判断请求方法与请求路径是否同时匹配
            if (requestType.name().equalsIgnoreCase(currentRequestType) && requestPathMatcher.matches()) {
                // 获取 Handler 及其相关属性
                handler = actionEntry.getValue();
                // 设置请求路径匹配器
                if (handler != null) {
                    handler.setRequestPathMatcher(requestPathMatcher);
                }
                // 若成功匹配，则终止循环
                break;
            }
        }
        // 返回该 Handler
        return handler;
	}

}
