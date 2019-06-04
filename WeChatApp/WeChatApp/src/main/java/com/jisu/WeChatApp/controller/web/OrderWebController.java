package com.jisu.WeChatApp.controller.web;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.dao.OrderInfoMapper;
import com.jisu.WeChatApp.dao.RefundOrderInfoMapper;
import com.jisu.WeChatApp.entity.OrderSearchDTO;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.PageDataResult;
import com.jisu.WeChatApp.pojo.RefundOrderInfo;
import com.jisu.WeChatApp.service.impl.OrderInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.PayUtils;

@RequestMapping("/web/order")
@Controller
public class OrderWebController {

	@Autowired
	private OrderInfoServiceImpl orderInfoServiceImpl;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private RefundOrderInfoMapper refundOrderInfoMapper;

	@RequestMapping("orderManage")
	public ModelAndView orderManage() {
		return new ModelAndView("order/orderManage");
	}

	@RequestMapping(value = "getOrderList", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = "orderList")
	public PageDataResult getOrderList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, OrderSearchDTO orderSearchDTO) {
		PageDataResult pdr = new PageDataResult();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			// 获取用户和角色列表
			pdr = orderInfoServiceImpl.getOrderList(page, limit, orderSearchDTO);
			pdr.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdr;
	}

	@RequestMapping(value = "getCheckOrderList", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value = "orderCheck")
	public PageDataResult getCheckOrderList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, OrderSearchDTO orderSearchDTO) {
		PageDataResult pdr = new PageDataResult();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			// 获取用户和角色列表
			orderSearchDTO.setOrderStatus("0");
			pdr = orderInfoServiceImpl.getOrderList(page, limit, orderSearchDTO);
			pdr.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdr;
	}

	@RequestMapping("orderDatil/{order_id}")
	@ResponseBody
	public ModelAndView getOrderDatil(@PathVariable("order_id") String id) {
		ModelAndView mv = new ModelAndView("order/orderManage");
		try {
			if (null == id) {
				mv.addObject("msg", "请求参数有误，请您稍后再试");
				return mv;
			}
			OrderInfo orderInfo = orderInfoServiceImpl.getOrder(id);
			mv.addObject("flag", "orderDatil");
			mv.addObject("orderInfo", orderInfo);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "请求异常，请您稍后再试");
		}
		return mv;
	}

	@RequestMapping("getOrder")
	@ResponseBody
	public OrderInfo getOrder(@RequestParam("order_id") String id) {
		OrderInfo order_info = null;
		try {
			if (null != id) {
				order_info = orderInfoServiceImpl.getOrder(id);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return order_info;
	}

	@RequestMapping(value = "updateServerMember", method = RequestMethod.POST)
	@ResponseBody
	public String updateServerMember(OrderInfo orderInfo) {
		if (null == orderInfo.getServerMemberNo()) {
			return "请选择技术人员";
		}
		try {
			if (null != orderInfo) {
				orderInfo.setOrderStatus(1);
				orderInfoServiceImpl.updateServerMember(orderInfo);
				return "ok";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "修改订单出错，请您稍后再试";
	}

	/**
	 * 关闭订单
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "closeOrderById", method = RequestMethod.POST)
	@ResponseBody
	public String closeOrderById(HttpServletRequest request, HttpServletResponse response) {
		String order_id = request.getParameter("order_id");
		OrderInfo orderInfo = new OrderInfo();
		orderInfo.setOrderId(order_id);
		orderInfo.setOrderStatus(5);
		orderInfo.setCloseTime(new Date());
		int num = orderInfoMapper.updateByPrimaryKeySelective(orderInfo);
		if (num > 0) {
			return "ok";
		} else {
			return "出错了,请稍后重试";
		}
	}

	@RequestMapping("refundOrderManage")
	public ModelAndView refundOrderPage() {

		return new ModelAndView("order/refundOrderManage");
	}

	@RequestMapping("getRefundOrderList")
	@ResponseBody
	public PageDataResult getRefundOrderList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, OrderSearchDTO orderSearchDTO) {

		PageDataResult pdr = new PageDataResult();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			// 获取用户和角色列表
			pdr = orderInfoServiceImpl.getRefundOrderList(page, limit, orderSearchDTO);
			pdr.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdr;
	}

	@RequestMapping("getCheckRefundOrderList")
	@ResponseBody
	public PageDataResult getCheckRefundOrderList(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, OrderSearchDTO orderSearchDTO) {

		PageDataResult pdr = new PageDataResult();
		try {
			if (null == page) {
				page = 1;
			}
			if (null == limit) {
				limit = 10;
			}
			orderSearchDTO.setRefundOrderStatus("0");
			pdr = orderInfoServiceImpl.getRefundOrderList(page, limit, orderSearchDTO);
			pdr.setCode(200);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pdr;
	}

	/**
	 * 退款
	 * 
	 * @param request
	 * @param url
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public String doRefund(HttpServletRequest request, HttpServletResponse response) {
		String refund_order_id = request.getParameter("refund_order_id");
		RefundOrderInfo refundOrderInfo = refundOrderInfoMapper.selectByPrimaryKey(refund_order_id);
		String orderId = refundOrderInfo.getOrderId();
		BigDecimal refund_price = refundOrderInfo.getRefundPrice();
		BigDecimal order_price = refundOrderInfo.getOrderPrice();
		String refund_fee = refund_price.multiply(new BigDecimal(100)).toString();
		String total_fee = order_price.multiply(new BigDecimal(100)).toString();
		String refund_desc=refundOrderInfo.getRefundOrderDesc();
		Map<String, String> result = new HashMap<String, String>();
		try {
			result = PayUtils.wxRefund(refund_order_id, orderId, total_fee, refund_fee, refund_desc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 判断是否退款成功
		if ("ok".equals(result.get("returncode"))) {
			
			return "ok";
		}
		return "微信退款失败";
	}

}
