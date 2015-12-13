package cn.xdaima.kiso.mvc.core.handler;

import cn.xdaima.kiso.mvc.entity.Handler;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月30日 下午1:15:05
 * @description : 
 */
public interface HandlerMapping {

    /**
     * @param currentRequestMethod
     * @param currentRequestPath
     * @return
     * @author sunhao
     * @date 2015年10月30日 下午1:15:32
     * @description : 通过请求方式和请求路径获取handler
     */
    Handler getHandler(String currentRequestMethod, String currentRequestPath);
}
