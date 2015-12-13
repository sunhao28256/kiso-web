package cn.xdaima.kiso.mvc.core;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.xdaima.kiso.util.ClassScanner;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年10月28日 下午2:04:54
 * @description : 容器监听器
 */
@WebListener
public class ContainerListener implements ServletContextListener {
	/** 基本包 */
	
	protected final Logger logger = LoggerFactory.getLogger(ClassScanner.class);
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//anction初始化
		ActionHelper.init();
		BeanFactory.init();

	}

	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
	}

}
