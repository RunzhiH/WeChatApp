package com.jisu.WeChatApp.controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.OrderInfoMapper;
import com.jisu.WeChatApp.entity.ServerMemberVO;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.service.impl.MemberInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.ServerServiceImpl;
import com.jisu.WeChatApp.service.impl.ShopInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.PayUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

@RestController
@RequestMapping("/api/pay")
public class PayController {
	@Autowired
	private ShopInfoServiceImpl shopInfoServiceImpl;
	@Autowired
	private ServerServiceImpl serverServiceImpl;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private MemberInfoServiceImpl memberInfoServiceImpl;

	@RequestMapping(value = "addPayOrder", method = RequestMethod.POST)
	public MsgModel addPayOrder(HttpServletRequest request) {
		String account_type = request.getParameter("account_type");// 1:门店入驻,2:技术人员入驻
		String member_no = request.getParameter("member_no");
		String shop_id= request.getParameter("shop_id");
		BigDecimal pay_price = null;
		String order_desc;
		MsgModel msg = new MsgModel();
		if ("1".equals(account_type)) {
			// 获取店铺信息
			Map<String, Object> shop_info = shopInfoServiceImpl.getshopInfoByMemberNo(member_no);
			if (MapUtils.isEmpty(shop_info)) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("未申请入驻商家");
				return msg;
			}
			pay_price = new BigDecimal(1980);
			order_desc = "申请入驻商家";
			// 获取店铺信息结束
		} else if ("2".equals(account_type)) {
			ServerMemberVO server_info = serverServiceImpl.getServerMemberByMemberNo(member_no);
			if (server_info == null) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("未申请入驻技术人员");
				return msg;
			}
			pay_price = new BigDecimal(800);
			order_desc = "申请入驻技术人员";
		} else {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("支付对象未知");
			return msg;
		}

		OrderInfo order_info = new OrderInfo();
		order_info.setOrderId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		order_info.setCreateTime(new Date());
		order_info.setOrderType(Integer.valueOf(account_type));
		order_info.setMemberNo(member_no);
		order_info.setOrderCode(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_NUM_ONLY, 16, null));
		order_info.setOrderDesc(order_desc);
		order_info.setOrderNum(1);
		order_info.setOrderPrice(pay_price);
		order_info.setOrderStatus(0);
		order_info.setPayPrice(pay_price);
		order_info.setShopId(shop_id);
		int num = orderInfoMapper.insertSelective(order_info);
		// 创建订单结束
		if (num > 0) {
			msg.setContext(order_info);
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
		String order_desc = orderInfo.getServerName();
		BigDecimal pay_price = orderInfo.getPayPrice().multiply(new BigDecimal(100));
		String member_no = orderInfo.getMemberNo();
		int order_status = orderInfo.getOrderStatus();
		if (0 != order_status) { // 订单已支付
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("订单已支付");
			return msg;
		}
		MemberInfo memberInfo = memberInfoServiceImpl.getMemberInfoByMemberNo(member_no);
		String open_id = memberInfo.getOpenid();

		String notify_url = PropertyUtil.getProperty("shop.host") + "/api/order/wxPayNotify";
		String appid = PropertyUtil.getProperty("wx.appid");
		String mch_key = PropertyUtil.getProperty("wx.mch_key");

		// 修改订单状态为支付中
		orderInfo.setOrderStatus(7);
		orderInfo.setPayWay(1);
		// orderInfo.setPayWay(Integer.valueOf(pay_way));
		orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		if ("2".equals(pay_way)) {
			// orderInfoServiceImpl.OrderPayAfter(order_id);
			msg.setStatus(MsgModel.SUCCESS);
		} else {

			String nonceStr = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			String result = PayUtils.wxPay(request, nonceStr, order_desc, order_id, pay_price.setScale(0, BigDecimal.ROUND_UP).toString(), notify_url, open_id);
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
					result_map.put("signType", "MD5");
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
							OrderInfo update_order_info= new OrderInfo();
							update_order_info.setOrderId(out_trade_no);
							update_order_info.setOrderStatus(1);
							update_order_info.setPayTime(new Date());
							 orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
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
}
