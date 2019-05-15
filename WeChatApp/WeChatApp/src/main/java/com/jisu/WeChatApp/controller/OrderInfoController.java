package com.jisu.WeChatApp.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.OrderInfoMapper;
import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.pojo.ShopServer;
import com.jisu.WeChatApp.service.impl.MemberInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.OrderInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.SendMessageServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.PayUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

@RequestMapping("/api/order")
@RestController
public class OrderInfoController {
	@Autowired
	private ShopServerMapper shopServerMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private MemberInfoServiceImpl memberInfoServiceImpl;
	@Autowired
	private OrderInfoServiceImpl orderInfoServiceImpl;
	@Autowired
	private ShopInfoMapper shopInfoMapper;
	@Autowired
	private SendMessageServiceImpl sendMessageServiceImpl;
	@Autowired
	private MemberInfoMapper memberInfoMapper;

	/**
	 * 下单
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/addOrder", method = RequestMethod.POST)
	public MsgModel addOrder(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		MsgModel msg = new MsgModel();

		String member_no = request.getParameter("member_no");
		String shop_server_id = request.getParameter("shop_server_id");
		String order_num = request.getParameter("order_num");
		String shop_id = request.getParameter("shop_id");
		String server_example_photo = request.getParameter("server_example_photo");
		String phone = request.getParameter("phone");
		String nickname = request.getParameter("nickname");
		String appointment_time = request.getParameter("appointment_time");// 预约时间
		String server_address = "";

		// 获取商家服务信息
		ShopServer shopServer = shopServerMapper.selectByPrimaryKey(shop_server_id);
		if (null == shopServer) {
			msg.setMessage("未获取到服务项目信息");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		BigDecimal server_price = shopServer.getServerPrice();
		BigDecimal server_sale_price = shopServer.getServerSalePrice();
		String server_name=shopServer.getServerName();
		// 获取商家服务信息结束

		// 服务地址
		if (StringUtils.isNotBlank(shop_id)) {
			// 是否绑定商家
			MemberInfo member_info = memberInfoMapper.selectByPrimaryKey(member_no);
			String share_shop_id = member_info.getShareShopId();
			if (StringUtils.isBlank(share_shop_id)) {
				// 绑定商家
				member_info.setShareShopId(shop_id);
				member_info.setShareShopTime(new Date());
				memberInfoMapper.updateByPrimaryKeySelective(member_info);
				// 绑定商家结束
			}
			// 是否绑定商家结束

			ShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(shop_id);
			server_address = shopInfo.getShopAddress();
		} else {
			server_address = request.getParameter("server_address");
			if (StringUtils.isBlank(server_address)) {
				msg.setMessage("服务地址不能未空");
				msg.setStatus(MsgModel.ERROR);
				return msg;
			}
		}
		// 服务地址结束

		// 创建订单
		String order_desc = request.getParameter("order_desc");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setCreateTime(new Date());
		orderInfo.setMemberNo(member_no);
		orderInfo.setOrderId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		orderInfo.setOrderNum(Integer.valueOf(order_num));
		orderInfo.setShopServerId(shop_server_id);
		orderInfo.setOrderCode(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_ONLY, 16, null));
		orderInfo.setOrderPrice(server_price);
		orderInfo.setServerName(server_name);
		orderInfo.setPayPrice(server_sale_price);
		orderInfo.setOrderStatus(0);
		orderInfo.setOrderDesc(order_desc);
		orderInfo.setServerAddress(server_address);
		orderInfo.setAppointmentTime(appointment_time);
		orderInfo.setOrderPhone(phone);
		orderInfo.setOrderNickname(nickname);
		orderInfo.setServerMemberName(request.getParameter("server_member_name"));
		if (StringUtils.isNotBlank(server_example_photo)) {
			orderInfo.setServerExamplePhoto(server_example_photo);
		}
		int num = orderInfoMapper.insertSelective(orderInfo);
		// 创建订单结束
		if (num > 0) {
			msg.setContext(orderInfo);
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 支付订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/payOrder")
	public MsgModel payOrder(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String order_id = request.getParameter("order_id");
		String pay_way = request.getParameter("pay_way");
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		String order_desc = orderInfo.getOrderDesc();
		BigDecimal pay_price = orderInfo.getPayPrice();
		String member_no = orderInfo.getMemberNo();
		int order_status = orderInfo.getOrderStatus();
		if (0 != order_status) { // 订单已支付
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("订单已支付");

		}
		MemberInfo memberInfo = memberInfoServiceImpl.getMemberInfoByMemberNo(member_no);
		String open_id = memberInfo.getOpenid();

		String notify_url = PropertyUtil.getProperty("shop.host") + "/order/wxPayNotify";
		String appid = PropertyUtil.getProperty("wx.appid");
		String mch_key = PropertyUtil.getProperty("wx.mch_key");

		// 修改订单状态为支付中
		orderInfo.setOrderStatus(7);
		orderInfo.setPayWay(Integer.valueOf(pay_way));
		orderInfoMapper.updateByPrimaryKeySelective(orderInfo);

		String nonceStr = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		String result = PayUtils.wxPay(request, order_id, order_desc, order_id, String.valueOf(pay_price), notify_url, open_id);
		try {
			// 将解析结果存储在HashMap中
			Map map = PayUtils.doXMLParse(result);

			String return_code = (String) map.get("return_code");// 返回状态码
			Map<String, Object> result_map = new HashMap<String, Object>();// 返回给小程序端需要的参数
			if (return_code.equals("SUCCESS")) {
				String prepay_id = (String) map.get("prepay_id");// 返回的预付单信息
				result_map.put("nonceStr", nonceStr);
				result_map.put("package", "prepay_id=" + prepay_id);
				Long timeStamp = System.currentTimeMillis() / 1000;
				result_map.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
				// 拼接签名需要的参数
				String stringSignTemp = "appId=" + appid + "&nonceStr=" + nonceStr + "&package=prepay_id=" + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
				// 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
				String paySign = PayUtils.sign(stringSignTemp, mch_key, "utf-8").toUpperCase();
				result_map.put("paySign", paySign);
				result_map.put("appid", appid);
				msg.setStatus(MsgModel.SUCCESS);
				msg.setContext(result_map);
			} else {
				// 修改订单状态
				orderInfo.setOrderStatus(0);
				orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
				// 修改订单状态结束
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 微信回调
	 * 
	 * @return
	 */
	@RequestMapping("/wxPayNotify")
	public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) {
		String mch_key = PropertyUtil.getProperty("wx.mch_key");
		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream()));

			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			br.close(); // sb为微信返回的xml

			String notityXml = sb.toString();
			String resXml = "";
			System.out.println("接收到的报文：" + notityXml);
			Map map = PayUtils.doXMLParse(notityXml);
			String returnCode = (String) map.get("return_code");
			if ("SUCCESS".equals(returnCode)) { // 验证签名是否正确
				@SuppressWarnings("unchecked")
				Map<String, String> validParams = PayUtils.paraFilter(map); // 回调验签时需要去除sign和空值参数
				String validStr = PayUtils.createLinkString(validParams);// 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
				String sign = PayUtils.sign(validStr, mch_key, "utf-8").toUpperCase();// 拼装生成服务器端验证的签名
				// 根据微信官网的介绍，此处不仅对回调的参数进行验签，还需要对返回的金额与系统订单的金额进行比对等
				if (sign.equals(map.get("sign"))) {
					String mch_id = (String) map.get("mch_id"); // 商户号
					// String openid = (String) map.get("openid"); // 用户标识
					String out_trade_no = (String) map.get("out_trade_no"); // 商户订单号
					// String total_fee = (String) map.get("total_fee");
					// String transaction_id = (String) map.get("transaction_id"); // 微信支付订单号
					/** 此处添加自己的业务逻辑代码start **/
					OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(out_trade_no);
					// orderInfoServiceImpl.OrderPayAfter(out_trade_no);
					/** 此处添加自己的业务逻辑代码end **/
					// 通知微信服务器已经支付成功
					if (!PropertyUtil.getProperty("wx.mch_id").equals(mch_id)) {
						resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[参数错误]]></return_msg>" + "</xml> ";
					} else {
						// 注意特殊情况：订单已经退款，但收到了支付结果成功的通知，不应把商户侧订单状态从退款改成支付成功
						if (7 == orderInfo.getOrderStatus()) {// 支付的状态判断
							// 支付完成后执行
							orderInfoServiceImpl.OrderPayAfter(out_trade_no);
							resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
						} else {
							resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>" + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";
							// logger.info("订单已处理");
						}
					}
				} else {
					// logger.info("支付失败,错误信息：" + notifyMap.get("err_code"));
					resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
				}
			} else { // 签名错误，如果数据里没有sign字段，也认为是签名错误
				resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>" + "<return_msg><![CDATA[通知签名验证失败]]></return_msg>" + "</xml> ";
				// logger.info("通知签名验证失败");
			}
			// ------------------------------ //处理业务完毕 //------------------------------
			BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
			out.write(resXml.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 根据条件获取订单列表
	 * 
	 * @return
	 */
	@RequestMapping("getOrderListByConidition")
	public MsgModel getOrderListByConidition(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("order_status", request.getParameter("order_status"));
		condition.put("order_type", request.getParameter("order_type"));
		condition.put("nickname", request.getParameter("nickname"));
		condition.put("shop_id", request.getParameter("shop_id"));
		condition.put("order_desc", request.getParameter("order_desc"));
		condition.put("pay_way", request.getParameter("pay_way"));
		condition.put("order_code", request.getParameter("order_code"));
		condition.put("ordre_member", request.getParameter("ordre_member"));
		condition.put("shop_is_check", request.getParameter("shop_is_check"));
		condition.put("server_member_is_check", request.getParameter("server_member_is_check"));
		condition.put("server_member_no", request.getParameter("server_member_no"));
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end) && StringUtils.isBlank(begin)) {
			condition.put("begin", begin);
			condition.put("end", end);
		}
		List<Map<String, String>> order_list = orderInfoServiceImpl.getOrderListByCondition(condition);
		MsgModel msg = new MsgModel();
		msg.setContext(order_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 预约确认
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("checkOrder")
	public MsgModel checkOrder(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String check_type = request.getParameter("check_type");
		String order_id = request.getParameter("order_id");
		String is_check = request.getParameter("is_check");
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		if ("0".equals(check_type)) {
			orderInfo.setShopIsCheck(Integer.valueOf(is_check));
		} else if ("1".equals(check_type)) {
			orderInfo.setServerMemebrIsChenck(Integer.valueOf(is_check));
		} else {
			msg.setStatus(MsgModel.WORRING);
			msg.setMessage("确认类型错误");
			return msg;
		}
		int update_num = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		if (update_num > 0) {
			// 全部预约确认后修改订单状态
			orderInfoServiceImpl.AfterOrderAllCheck(order_id);
			// 全部预约确认后修改订单状态结束
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 修改预约信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "updateOrderAppointmentInfo", method = RequestMethod.POST)
	public MsgModel updateOrderAppointmentInfo(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String order_id = request.getParameter("order_id");
		OrderInfo order_info = orderInfoMapper.selectByPrimaryKey(order_id);
		String order_member_no = order_info.getMemberNo();

		if (order_member_no.equals(member_no)) {
			msg.setMessage("非下单人不可修改");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		String shop_id = request.getParameter("shop_id");
		String server_address = "";
		// 服务地址
		if (StringUtils.isNotBlank(shop_id)) {
			ShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(shop_id);
			server_address = shopInfo.getShopAddress();
		} else {
			server_address = request.getParameter("server_address");
			if (StringUtils.isBlank(server_address)) {
				msg.setMessage("服务地址不能未空");
				msg.setStatus(MsgModel.ERROR);
				return msg;
			}
		}
		// 服务地址结束
		String appointment_time = request.getParameter("appointment_time");// 预约时间
		order_info.setShopId(shop_id);
		order_info.setServerAddress(server_address);
		order_info.setAppointmentTime(appointment_time);
		int update_num = orderInfoMapper.updateByPrimaryKeySelective(order_info);
		if (update_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 完成服务后修改状态
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateOrderStatusForFinishServer", method = RequestMethod.POST)
	public MsgModel updateOrderStatusForFinishServer(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String order_id = request.getParameter("order_id");
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		if (1 == orderInfo.getOrderStatus()) {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("订单还未支付");
			return msg;
		}
		if (orderInfo.getServerMemberNo().equals(member_no)) {
			orderInfo.setOrderStatus(2);
		} else {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("您不是该订单的技师");
		}
		return null;
	}

	/**
	 * 修改服务人员
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updataOrderServerMember", method = RequestMethod.POST)
	public MsgModel updataOrderServerMember(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		String order_id = request.getParameter("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderId(order_id);
		orderInfo.setServerMemberNo(member_no);
		int num = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		MsgModel msg = new MsgModel();
		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
			Map<String, String> msg_map = new HashMap<String, String>();
			msg_map.put("order_id", order_id);
			sendMessageServiceImpl.sendOrderCheckMessageToServer(msg_map);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return null;
	}

	/**
	 * 关闭订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("closeOrderById")
	public MsgModel closeOrderById(HttpServletRequest request, HttpServletResponse response) {
		String order_id = request.getParameter("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderId(order_id);
		orderInfo.setOrderStatus(5);
		orderInfo.setCloseTime(new Date());
		int num = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		MsgModel msg = new MsgModel();
		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}
	
	@RequestMapping("getOrderDatilByOrderId")
	public MsgModel getOrderDatilByOrderId(HttpServletRequest request) {
		String order_id= request.getParameter("order_id");
		Map<String, String> order_datil=orderInfoServiceImpl.getOrderDatil(order_id);
		MsgModel msg= new MsgModel();
		msg.setContext(order_datil);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
