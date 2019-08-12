package com.jisu.WeChatApp.timeTask;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jisu.WeChatApp.service.impl.OrderInfoServiceImpl;

@Component
@EnableScheduling
public class AutoFinishOrderTimeTask {
	@Autowired
	private OrderInfoServiceImpl orderInfoServiceImpl;

	/**
	 * 自动完成订单
	 */
//	@Scheduled(fixedRate = 5000)
//	public void AutoFinishOrder() {
//		// 获取可完成的订单id
//		List<String> order_id_list = orderInfoServiceImpl.getFinishOrderIdList();
//		// 获取可完成的订单id结束
//		// 完成订单
//		orderInfoServiceImpl.finishOrder();
//		// 完成订单结束
//		for (String order_id : order_id_list) {
//			// 结算收益
//			orderInfoServiceImpl.settlementOrder(order_id);
//			// 结束收益结束
//		}
//
//	}
}
