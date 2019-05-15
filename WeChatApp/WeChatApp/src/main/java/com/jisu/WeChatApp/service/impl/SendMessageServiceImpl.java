package com.jisu.WeChatApp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.FormInfoMapper;
import com.jisu.WeChatApp.daoSelf.MessageMapperSelf;
import com.jisu.WeChatApp.pojo.FormInfo;
import com.jisu.WeChatApp.pojo.FormInfoExample;
import com.jisu.WeChatApp.service.SendMessageService;
import com.jisu.WeChatApp.tool.util.MSGUtils;

@Service("SendMessageServiceImpl")
public class SendMessageServiceImpl implements SendMessageService {
	@Autowired
	private MessageMapperSelf messageMapperSelf;
	@Autowired
	private FormInfoMapper formInfoMapper;

	@Override
	public Object getSendMessageContext(Map<String, String> map2) {
		// TODO Auto-generated method stub
		String message_type = (String) map2.get("message_type");
		if ("1".equals(message_type)) { // 预约提醒(平台客服)
			// 获取带推送消息的订单详情
			Map<String, String> map = new HashMap<String, String>();
			map.put("order_id", (String) map2.get("order_id"));
			map.put("message_type", message_type);
			Map<String, String> message_context = messageMapperSelf.getOrderInfoWithMessageInfo(map);
			return message_context;
			// 获取带推送消息的订单详情结束
		} else if ("2".equals(message_type)) { // 商家接单提醒
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getOrderInfoWithMessageInfoAndShopInfo(map);
		} else if ("3".equals(message_type)) { // 服务人员接单提现
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getOrderInfoWithMessageInfoAndServerMemberInfo(map);
		} else if ("4".equals(message_type)) { // 支付成功提醒
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getOrderInfoWithMessageInfoAndMemberInfo(map);
		} else if ("5".equals(message_type)) { // 预约成功提醒
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getOrderInfoWithMessageInfoAndMemberInfo(map);
		} else if ("6".equals(message_type)) { // 服务完成提醒
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getOrderInfoWithMessageInfoAndMemberInfo(map);
		} else if ("7".equals(message_type)) { // 申请售后提醒
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getRefundOrderInfoWithMessageInfoAndMemberInfo(map);
		} else if ("8".equals(message_type)) {
			String order_id = (String) map2.get("order_id");
			Map<String, String> map = new HashMap<String, String>();
			map.put("message_type", message_type);
			map.put("order_id", order_id);
			return messageMapperSelf.getOrderInfoWithMessageInfoAndMemberInfo(map);
		}
		return null;
	}

	@Override
	public void sendAddOrderMessage(Map<String, String> msg) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("message_type", "1");
		map.put("order_id", msg.get("order_id"));

		Map<String, String> message_context = (Map<String, String>) getSendMessageContext(map);
		String formid = message_context.get("formid");
		MSGUtils.pushOneUser(formid, message_context);
		// formid标记为已使用
		FormInfoExample example = new FormInfoExample();
		example.createCriteria().andFormIdEqualTo(formid);
		FormInfo formInfo = new FormInfo();
		formInfo.setIsUse(1);
		formInfoMapper.updateByExampleSelective(formInfo, example);
		// formid标记为已使用结束
	}

	@Override
	public void sendOrderCheckMessageToShop(Map<String, String> msg) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("message_type", "2");
		map.put("order_id", msg.get("order_id"));
		Map<String, String> message_context = (Map<String, String>) getSendMessageContext(map);
		String formid = message_context.get("formid");
		MSGUtils.pushOneUser(formid, message_context);
		// formid标记为已使用
		FormInfoExample example = new FormInfoExample();
		example.createCriteria().andFormIdEqualTo(formid);
		FormInfo formInfo = new FormInfo();
		formInfo.setIsUse(1);
		formInfoMapper.updateByExampleSelective(formInfo, example);
		// formid标记为已使用结束
	}

	@Override
	public void sendOrderCheckMessageToServer(Map<String, String> msg) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("message_type", "3");
		map.put("order_id", msg.get("order_id"));
		Map<String, String> message_context = (Map<String, String>) getSendMessageContext(map);
		String formid = message_context.get("formid");
		MSGUtils.pushOneUser(formid, message_context);
		// formid标记为已使用
		FormInfoExample example = new FormInfoExample();
		example.createCriteria().andFormIdEqualTo(formid);
		FormInfo formInfo = new FormInfo();
		formInfo.setIsUse(1);
		formInfoMapper.updateByExampleSelective(formInfo, example);
		// formid标记为已使用结束
	}

	@Override
	public void sendOrderPayMessage(Map<String, String> msg) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("message_type", "4");
		map.put("order_id", msg.get("order_id"));
		Map<String, String> message_context = (Map<String, String>) getSendMessageContext(map);
		String formid = message_context.get("formid");
		MSGUtils.pushOneUser(formid, message_context);
		// formid标记为已使用
		FormInfoExample example = new FormInfoExample();
		example.createCriteria().andFormIdEqualTo(formid);
		FormInfo formInfo = new FormInfo();
		formInfo.setIsUse(1);
		formInfoMapper.updateByExampleSelective(formInfo, example);
		// formid标记为已使用结束
	}

	@Override
	public void sendOrderIsCheckMessage(Map<String, String> msg) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("message_type", "5");
		map.put("order_id", msg.get("order_id"));
		Map<String, String> message_context = (Map<String, String>) getSendMessageContext(map);
		String formid = message_context.get("formid");
		MSGUtils.pushOneUser(formid, message_context);
		// formid标记为已使用
		FormInfoExample example = new FormInfoExample();
		example.createCriteria().andFormIdEqualTo(formid);
		FormInfo formInfo = new FormInfo();
		formInfo.setIsUse(1);
		formInfoMapper.updateByExampleSelective(formInfo, example);
		// formid标记为已使用结束
	}

	@Override
	public int deleteFormIdByOvertime() {
		// TODO Auto-generated method stub
		return messageMapperSelf.deleteFormIdByOvertime();
	}

	@Override
	public void sendOrderNotChckeMessage(Map<String, String> msg) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("message_type", "8");
		map.put("order_id", msg.get("order_id"));
		Map<String, String> message_context = (Map<String, String>) getSendMessageContext(map);
		String formid = message_context.get("formid");
		MSGUtils.pushOneUser(formid, message_context);
		// formid标记为已使用
		FormInfoExample example = new FormInfoExample();
		example.createCriteria().andFormIdEqualTo(formid);
		FormInfo formInfo = new FormInfo();
		formInfo.setIsUse(1);
		formInfoMapper.updateByExampleSelective(formInfo, example);
		// formid标记为已使用结束
	}

}
