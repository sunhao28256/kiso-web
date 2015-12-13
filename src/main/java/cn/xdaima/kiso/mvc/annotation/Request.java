package cn.xdaima.kiso.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.xdaima.kiso.mvc.constant.RequestType;

/**
 * 定义请求
 *
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Request {

	String value();

	RequestType[] requestType();
}
