package cn.xdaima.kiso.mvc.entity;

import java.util.Collections;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月2日 下午5:38:36
 * @description : 视图数据封装
 */
public class ViewModel {
	private String path;
	private Map<String, Object> data = Collections.emptyMap();

	public ViewModel addAttrivate(String key, Object value) {
		this.data.put(key, value);
		return this;
	}

	public boolean isRedirect() {
		return StringUtils.isEmpty(path) || path.startsWith("/");
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

}
