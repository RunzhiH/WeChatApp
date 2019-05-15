package com.jisu.WeChatApp.daoSelf;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MessageMapperSelf {
	/**
	 * 获取带推送消息的订单信息
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getOrderInfoWithMessageInfo(Map<String, String> map);

	/**
	 * 获取带推送消息和商家信息的订单详情
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getOrderInfoWithMessageInfoAndShopInfo(Map<String, String> map);

	/**
	 * 获取带推送消息和服务人员的订单详情
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getOrderInfoWithMessageInfoAndServerMemberInfo(Map<String, String> map);

	/**
	 * 获取带推送消息和下单人信息的订单详情
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getOrderInfoWithMessageInfoAndMemberInfo(Map<String, String> map);

	/**
	 * 获取带推送消息和下单人信息的订单详情
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getRefundOrderInfoWithMessageInfoAndMemberInfo(Map<String, String> map);

	/**
	 * 删除过期的formid
	 * 
	 * @return
	 */
	public int deleteFormIdByOvertime();
}
