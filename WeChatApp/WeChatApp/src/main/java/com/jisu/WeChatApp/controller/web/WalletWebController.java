package com.jisu.WeChatApp.controller.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alipay.api.AlipayApiException;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.service.impl.WalletInfoServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/web/wallet")
public class WalletWebController {

	@Autowired
	private WalletInfoServiceImpl walletInfoServiceImpl;

	@RequestMapping("/drawalManage")
	public ModelAndView toWithDrawalList() {
		return new ModelAndView("/wallet/drawalManage");
	}

	@RequestMapping("getWithDrawalList")
	@ResponseBody
	public JSONArray getWithDrawalList(HttpServletRequest request) {
		JSONArray withdrawal_list = null;
		Map<String, String> condition= new HashMap<String, String>();
		condition.put("withdrawal_status", request.getParameter("withdrawal_status"));
		try {
			withdrawal_list = walletInfoServiceImpl.getWithDrawalList(condition);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return withdrawal_list;
	}
	
	@RequestMapping("getWithDrawalInfo/{drawal_id}")
	@ResponseBody
	public ModelAndView getWithDrawalInfo(@PathVariable("drawal_id") String id) {
		ModelAndView mv = new ModelAndView("/wallet/drawalManage");
		try {
			if (null == id) {
				mv.addObject("msg", "请求参数有误，请您稍后再试");
				return mv;
			}
			Map<String, String> drawal_info = walletInfoServiceImpl.getWithDrawalInfo(id);
			mv.addObject("flag", "drawalInfo");
			mv.addObject("drawal_info", drawal_info);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "请求异常，请您稍后再试");
		}
		return mv;
	}

	@RequestMapping(value = "agreeWithDrawal", method = RequestMethod.POST)
	@ResponseBody
	public String agreeWithDrawal(@RequestParam("drawal_id") String id, HttpServletRequest request) {
		if (StringUtils.isNotBlank(id)) {
			try {
				return walletInfoServiceImpl.agreeWithDrawal(id, request);
			} catch (AlipayApiException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return "请选择正确的数据";
	}

	@RequestMapping(value = "notAgreeWithDrawal", method = RequestMethod.POST)
	@ResponseBody
	public String notAgreeWithDrawal(@RequestParam("drawal_id") String id, @RequestParam("desc") String desc) {

		if (StringUtils.isNotBlank(id)) {
			return walletInfoServiceImpl.notAgreeWithDrawal(id, desc);
		}
		return "请选择正确的数据";
	}
}
