package cn.xdaima.kiso.mvc.entity;

import cn.xdaima.kiso.mvc.constant.RequestType;

public class Requester {
	private RequestType requestType;
	private String requestPath;

	public RequestType getRequestType() {
		return requestType;
	}

	public void setRequestType(RequestType requestType) {
		this.requestType = requestType;
	}

	public String getRequestPath() {
		return requestPath;
	}

	public void setRequestPath(String requestPath) {
		this.requestPath = requestPath;
	}

	public Requester(RequestType requestType, String requestPath) {
		super();
		this.requestType = requestType;
		this.requestPath = requestPath;
	}

}
