package cn.xdaima.kiso.plugin;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月9日 下午8:32:21
 * @description : 
 */
public interface Plugin {
	/**
	 * @author sunhao
	 * @date 2015年11月9日 下午8:32:52
	 * @description : 初始化插件
	 */
	void init();
	/**
	 * @author sunhao
	 * @date 2015年11月9日 下午8:34:10
	 * @description : 销毁插件
	 */
	void destory();
}
