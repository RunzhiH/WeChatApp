package com.jisu.WeChatApp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.service.impl.AreaInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/area")
@RestController
public class AreaInfoController {
	@Autowired
	private AreaInfoServiceImpl areaInfoServiceImpl;

	/**
	 * 获取所有省列表
	 * 
	 * @param request
	 * @param Response
	 * @return
	 */
	@RequestMapping("getSheng")
	public MsgModel getSheng(HttpServletRequest request, HttpServletResponse Response) {
		MsgModel msg = new MsgModel();
		List<Map<String, String>> sheng_list = areaInfoServiceImpl.getSheng();
		msg.setContext(sheng_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 获取下级区域列表
	 * 
	 * @param request
	 * @param Response
	 * @return
	 */
	@RequestMapping(value = "getNextAreaInfo")
	public MsgModel getNextAreaInfo(HttpServletRequest request, HttpServletResponse Response) {
		MsgModel msg = new MsgModel();
		String area_code = request.getParameter("area_code");
		List<Map<String, String>> area_info = areaInfoServiceImpl.getNextAreaInfo(area_code);
		msg.setContext(area_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
