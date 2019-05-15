package com.jisu.WeChatApp.service;

import java.util.Map;

public interface SendMessageService {
	// 获取推送消息
	public Object getSendMessageContext(Map<String, String> msg);

	/**
	 * 发送下单消息推送
	 * 
	 * @param msg
	 */
	public void sendAddOrderMessage(Map<String, String> msg);

	/**
	 * 发送订单预约确认推送该商家
	 * 
	 * @param msg
	 */
	public void sendOrderCheckMessageToShop(Map<String, String> msg);

	/**
	 * 发送订单预约确认推送该商家
	 * 
	 * @param msg
	 */
	public void sendOrderCheckMessageToServer(Map<String, String> msg);

	/**
	 * 发送订单支付成功推送消息
	 * 
	 * @param msg
	 */
	public void sendOrderPayMessage(Map<String, String> msg);

	/**
	 * 发送预约预约已确认消息推送
	 * 
	 * @param msg
	 */
	public void sendOrderIsCheckMessage(Map<String, String> msg);

	/**
	 * 删除超时的formid
	 * 
	 * @return
	 */
	public int deleteFormIdByOvertime();

	/**
	 * 发送订单未确认消息
	 * 
	 * @param map
	 */
	public void sendOrderNotChckeMessage(Map<String, String> map);
}
