package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jisu.WeChatApp.entity.OrderSearchDTO;

@Mapper
public interface OrderInfoMapperSelf {
	/**
	 * 获取需要关闭订单的id列表
	 * 
	 * @return
	 */
	public List<String> getCloseOrderIdList();

	/**
	 * 根据条件获取订单列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getOrderListByCondition(Map<String, String> condition);

	/**
	 * 获取可完成订单列表
	 * 
	 * @return
	 */
	public List<String> getFinishOrderIdList();

	/**
	 * 完成订单
	 * 
	 * @return
	 */
	public int finishOrder();

	public List<Map<String, String>> getOrderList(@Param(value = "orderSearch") OrderSearchDTO orderSearch);

	public Map<String, String> getOrderDatil(Map<String, String> map);

	public List<Map<String, String>> getRefundOrderList(@Param(value = "orderSearch") OrderSearchDTO orderSearchDTO);
}
