package com.jisu.WeChatApp.controller.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.service.impl.HomeDateServiceImpl;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/web/home")
@RestController
public class HomeWebController {
	@Autowired
	private HomeDateServiceImpl homeDateServiceImpl;

	@RequestMapping("getMemberChenageDate")
	public MsgModel getMemberChenageDate(HttpServletRequest request) {
		String type = request.getParameter("type");
		List<Map<String, String>> result = null;
		if ("1".equals(type)) {
			result = homeDateServiceImpl.getMemberChangeDateDay();
		} else if ("2".equals(type)) {
			result = homeDateServiceImpl.getMemberChangeDateMonth();
		} else if ("3".equals(type)) {
			result = homeDateServiceImpl.getMemberChangeDateYear();
		}
		MsgModel msg = new MsgModel();
		msg.setContext(result);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
	
	@RequestMapping("getOrderChangeDate")
	public MsgModel getOrderChangeDate(HttpServletRequest request) {
		String type = request.getParameter("type");
		List<Map<String, String>> result = null;
		if ("1".equals(type)) {
			result = homeDateServiceImpl.getOrderChangeDateDay();
		} else if ("2".equals(type)) {
			result = homeDateServiceImpl.getOrderChangeDateMonth();
		} else if ("3".equals(type)) {
			result = homeDateServiceImpl.getOrderChangeDateYear();
		}
		
		MsgModel msg = new MsgModel();
		msg.setContext(result);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
	
	@RequestMapping("getIncomeChangeDate")
	public MsgModel getIncomeChangeDate(HttpServletRequest request) {
		String type = request.getParameter("type");
		List<Map<String, String>> result = null;
		if ("1".equals(type)) {
			result = homeDateServiceImpl.getIncomeChangeDateDay();
		} else if ("2".equals(type)) {
			result = homeDateServiceImpl.getIncomeChangeDateMonth();
		} else if ("3".equals(type)) {
			result = homeDateServiceImpl.getIncomeChangeDateYear();
		}
		
		MsgModel msg = new MsgModel();
		msg.setContext(result);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
