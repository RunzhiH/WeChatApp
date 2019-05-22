package com.jisu.WeChatApp.entity;

public class OrderSearchDTO {
	private String orderCode;

	private String serverName;

	private String startTime;

	private String endTime;

	private String orderStatus;
	
	private String refundOrderStatus;
	
	public String getRefundOrderStatus() {
		return refundOrderStatus;
	}

	public void setRefundOrderStatus(String refundOrderStatus) {
		this.refundOrderStatus = refundOrderStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "OrderSearchDTO [orderCode=" + orderCode + ", serverName=" + serverName + ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
