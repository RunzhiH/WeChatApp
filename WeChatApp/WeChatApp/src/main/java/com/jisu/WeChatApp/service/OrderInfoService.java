package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

import com.jisu.WeChatApp.entity.OrderSearchDTO;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.PageDataResult;

public interface OrderInfoService {
	/**
	 * 支付后执行
	 * 
	 * @param order_id
	 * @return
	 */
	public Map<String, String> OrderPayAfter(String order_id);

	/**
	 * 预定确认后执行
	 * 
	 * @param order_id
	 * @return
	 */
	public int AfterOrderAllCheck(String order_id);

	/**
	 * 根据条件获取订单列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getOrderListByCondition(Map<String, String> condition);

	/**
	 * 获取可关闭订单id列表
	 * 
	 * @return
	 */
	public List<String> getCloseOrderIdList();

	/**
	 * 获取可完成订单id列表
	 * 
	 * @return
	 */
	public List<String> getFinishOrderIdList();

	/**
	 * 关闭订单
	 * 
	 * @param order_id
	 */
	public void closeOrder();

	/**
	 * 完成订单
	 * 
	 * @param order_id
	 */
	public void finishOrder();

	/**
	 * 结算订单
	 * 
	 * @param order_id
	 * @return
	 */
	public int settlementOrder(String order_id);

	/**
	 * 关闭订单
	 * 
	 * @param order_id
	 * @return
	 */
	public int closeOrderByOrderId(String order_id);
	
	public PageDataResult getOrderList(Integer page, Integer limit, OrderSearchDTO orderSearchDTO);
	
	public OrderInfo getOrder(String order_id);
	
	public void updateServerMember(OrderInfo orderInfo);
	
	public Map<String, String> getOrderDatil(String order_id);
}
