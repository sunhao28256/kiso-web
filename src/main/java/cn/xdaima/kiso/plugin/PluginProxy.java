package cn.xdaima.kiso.plugin;

import java.util.List;

import cn.xdaima.kiso.aop.proxy.Proxy;

public abstract class PluginProxy implements Proxy {
	public abstract List<Class<?>> getTargetClassList();

}
