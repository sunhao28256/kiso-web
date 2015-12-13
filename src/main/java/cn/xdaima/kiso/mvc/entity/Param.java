package cn.xdaima.kiso.mvc.entity;

import java.lang.reflect.Field;

import org.apache.commons.beanutils.BeanUtils;

/**
 *
 * @author sunhao
 * @email sunhao@jd.com
 * @date 2015年11月1日 下午3:52:24
 * @description :
 */
public class Param {
	private String paramName;
	private Class<?> paramType;
	private Object paramValue;

	public Param(String paramName, Class<?> paramType, String paramValue) {
		super();
		this.paramName = paramName;
		this.paramType = paramType;
		setParamValue(paramValue);
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public Class<?> getParamType() {
		return paramType;
	}

	public void setParamType(Class<?> paramType) {
		this.paramType = paramType;
	}

	public Object getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		if (paramValue == null) {
			return;
		}
		if ("".equals(paramValue)) {
			// TODO

			try {
				this.paramValue = paramType.newInstance();
				return;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (int.class.equals(paramType) || Integer.class.equals(paramType)) {
			this.paramValue = Integer.parseInt(paramValue);
			return;
		}
		if (long.class.equals(paramType) || Long.class.equals(paramType)) {
			this.paramValue = Long.parseLong(paramValue);
			return;
		}
		if (double.class.equals(paramType) || Double.class.equals(paramType)) {
			this.paramValue = Double.parseDouble(paramValue);
			return;
		}
		if (String.class.equals(paramType)) {
			this.paramValue = paramValue;
			return;
		}

		// TODO 其他引用类型
		Field[] fields = paramType.getDeclaredFields();
		for (Field field : fields) {
			if (!paramName.equals(field.getName())) {
				continue;
			}
			try {
				BeanUtils.setProperty(paramType, paramName, paramValue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paramName == null) ? 0 : paramName.hashCode());
		result = prime * result + ((paramType == null) ? 0 : paramType.hashCode());
		result = prime * result + ((paramValue == null) ? 0 : paramValue.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Param other = (Param) obj;
		if (paramName == null) {
			if (other.paramName != null)
				return false;
		} else if (!paramName.equals(other.paramName))
			return false;
		if (paramType == null) {
			if (other.paramType != null)
				return false;
		} else if (!paramType.equals(other.paramType))
			return false;
		if (paramValue == null) {
			if (other.paramValue != null)
				return false;
		} else if (!paramValue.equals(other.paramValue))
			return false;
		return true;
	}

}
