package com.jisu.WeChatApp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.OrderInfoMapper;
import com.jisu.WeChatApp.daoSelf.OrderInfoMapperSelf;
import com.jisu.WeChatApp.daoSelf.WalletChangeRecordMapperSelf;
import com.jisu.WeChatApp.daoSelf.WalletInfoMapperSelf;
import com.jisu.WeChatApp.entity.OrderSearchDTO;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.PageDataResult;
import com.jisu.WeChatApp.service.OrderInfoService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

@Service("OrderInfoServiceImpl")
public class OrderInfoServiceImpl implements OrderInfoService {

	@Autowired
	private OrderInfoMapper orderInfoMapper;

	@Autowired
	private OrderInfoMapperSelf orderInfoMapperSelf;
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private WalletChangeRecordMapperSelf walletChangeRecordMapperSelf;
	@Autowired
	private WalletInfoMapperSelf walletInfoMapperSelf;
	@Autowired
	private SendMessageServiceImpl sendMessageServiceImpl;

	@Override
	public Map<String, String> OrderPayAfter(String order_id) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		int order_status = orderInfo.getOrderStatus();
		int pay_way = orderInfo.getPayWay();
		if (7 == order_status) {
			// 订单已支付
			// 修改订单状态
			orderInfo.setPayTime(new Date());
			orderInfo.setOrderStatus(1);
			orderInfoMapper.updateByPrimaryKey(orderInfo);
			// 订单结算
			// 发送消息
			Map<String, String> msg = new HashMap<String, String>();
			msg.put("order_id", order_id);
			sendMessageServiceImpl.sendOrderPayMessage(msg);
			sendMessageServiceImpl.sendOrderCheckMessageToShop(msg);
			sendMessageServiceImpl.sendAddOrderMessage(msg);
			// 发送消息结束
		}
		return null;
	}

	@Override
	public int AfterOrderAllCheck(String order_id) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		int shop_is_check = orderInfo.getShopIsCheck();
		int server_member_is_check = orderInfo.getServerMemebrIsChenck();
		if (1 == shop_is_check && 1 == server_member_is_check) {
			orderInfo.setOrderStatus(8);
			orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
			// 发送消息
			Map<String, String> msg = new HashMap<String, String>();
			msg.put("order_id", order_id);
			sendMessageServiceImpl.sendOrderIsCheckMessage(msg);
			// 发送消息结束
		}
		if (2 == shop_is_check) {

		}
		return 0;
	}

	@Override
	public List<Map<String, String>> getOrderListByCondition(Map<String, String> condition) {
		// TODO Auto-generated method stub
		return orderInfoMapperSelf.getOrderListByCondition(condition);
	}

	@Override
	public List<String> getCloseOrderIdList() {
		// TODO Auto-generated method stub
		return orderInfoMapperSelf.getCloseOrderIdList();
	}

	@Override
	public List<String> getFinishOrderIdList() {
		// TODO Auto-generated method stub
		return orderInfoMapperSelf.getFinishOrderIdList();
	}

	@Override
	public void closeOrder() {
		// TODO Auto-generated method stub

	}

	@Override
	public void finishOrder() {
		// TODO Auto-generated method stub
		orderInfoMapperSelf.finishOrder();
	}

	@Override
	public int settlementOrder(String order_id) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		if (3 == orderInfo.getOrderStatus()) {
			String shop_id = orderInfo.getShopId();
			if (StringUtils.isNotBlank(shop_id)) {
				// 结算服务店家收益
				String ServerShop_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
				Map<String, String> order_shop_map = new HashMap<String, String>();
				order_shop_map.put("order_id", order_id);
				order_shop_map.put("record_id", ServerShop_record_id);
				int num_order_shop = walletChangeRecordMapperSelf.insertWalletChangeRecordForServerShop(order_shop_map);
				if (num_order_shop > 0) {
					Map<String, String> record_id_map = new HashMap<String, String>();
					record_id_map.put("record_id", ServerShop_record_id);
					walletInfoMapperSelf.updateWalletInfoByRecordId(record_id_map);
				}
				// 结算服务店家收益结束

				// 结算推荐店家收益
				String sharaShop_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
				Map<String, String> share_shop_map = new HashMap<String, String>();
				share_shop_map.put("order_id", order_id);
				share_shop_map.put("record_id", sharaShop_record_id);
				int num_share_shop = walletChangeRecordMapperSelf.insertWalletChangeRecordForShareShop(share_shop_map);
				if (num_share_shop > 0) {
					Map<String, String> record_id_map = new HashMap<String, String>();
					record_id_map.put("record_id", sharaShop_record_id);
					walletInfoMapperSelf.updateWalletInfoByRecordId(record_id_map);
				}
				// 结算推荐店家收益结束
			} else {
				// 结算推荐店家收益
				String sharaShop_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
				Map<String, String> share_shop_map = new HashMap<String, String>();
				share_shop_map.put("order_id", order_id);
				share_shop_map.put("record_id", sharaShop_record_id);
				int num_share_shop = walletChangeRecordMapperSelf.insertWalletChangeRecordForFirstShareShop(share_shop_map);
				if (num_share_shop > 0) {
					Map<String, String> record_id_map = new HashMap<String, String>();
					record_id_map.put("record_id", sharaShop_record_id);
					walletInfoMapperSelf.updateWalletInfoByRecordId(record_id_map);
				}
				// 结算推荐店家收益结束
			}

			// 结算服务人员收益
			String server_memebr_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			Map<String, String> server_memebr_map = new HashMap<String, String>();
			server_memebr_map.put("order_id", order_id);
			server_memebr_map.put("record_id", server_memebr_record_id);
			int num_server_member = walletChangeRecordMapperSelf.insertWalletChangeRecordForServerMember(server_memebr_map);
			if (num_server_member > 0) {
				Map<String, String> record_id_map = new HashMap<String, String>();
				record_id_map.put("record_id", server_memebr_record_id);
				walletInfoMapperSelf.updateWalletInfoByRecordId(record_id_map);
			}
			// 结算服务人员收益结束
		}

		return 0;
	}

	@Override
	public int closeOrderByOrderId(String order_id) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderId(order_id);
		orderInfo.setOrderStatus(5);
		return orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
	}

	@Override
	public PageDataResult getOrderList(Integer page, Integer limit, OrderSearchDTO orderSearch) {
		// TODO Auto-generated method stub
		PageDataResult pdr = new PageDataResult();
		PageHelper.startPage(page, limit);
		List<Map<String, String>> order_list = orderInfoMapperSelf.getOrderList(orderSearch);
		// 获取分页查询后的数据
		PageInfo<Map<String, String>> pageInfo = new PageInfo<Map<String, String>>(order_list);
		// 设置获取到的总记录数total：
		pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
		pdr.setList(pageInfo.getList());
		return pdr;
	}

	public OrderInfo getOrder(String order_id) {
		// TODO Auto-generated method stub
		return orderInfoMapper.selectByPrimaryKey(order_id);
	}

	public void updateServerMember(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		int num = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		if (num > 0) {
			// 发送消息
			Map<String, String> msg = new HashMap<String, String>();
			msg.put("order_id", orderInfo.getOrderId());
			sendMessageServiceImpl.sendOrderIsCheckMessage(msg);
			// 发送消息结束
		}
	}

	public Map<String, String> getOrderDatil(String order_id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("order_id", order_id);
		return orderInfoMapperSelf.getOrderDatil(map);
	}
}
