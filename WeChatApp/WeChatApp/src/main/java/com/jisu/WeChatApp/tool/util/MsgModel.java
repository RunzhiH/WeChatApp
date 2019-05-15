package com.jisu.WeChatApp.tool.util;

/**
 * 通用消息类
 * 
 * @author hongrunzhi
 *
 */
public class MsgModel {
	/**
	 * 成功
	 */
	public static final String SUCCESS = "200";
	/**
	 * 失败
	 */
	public static final String ERROR = "500";
	/**
	 * 警告
	 */
	public static final String WORRING = "400";

	String message;

	Object context;

	String status;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getContext() {
		return context;
	}

	public void setContext(Object context) {
		this.context = context;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
