package com.jisu.WeChatApp.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.pojo.ServerClass;
import com.jisu.WeChatApp.pojo.ShopServer;
import com.jisu.WeChatApp.service.impl.ServerServiceImpl;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/server")
@RestController
public class ServerController {
	@Autowired
	private ServerServiceImpl serverServiceImpl;
	@Autowired
	private ShopServerMapper shopServerMapper;

	@RequestMapping("getLeveL1ServerClass")
	public MsgModel getLeveL1ServerClass() {
		MsgModel msg = new MsgModel();
		List<ServerClass> serverClassList = serverServiceImpl.getLevel1ServerClassList();
		msg.setContext(serverClassList);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getNextLevelServerClassList")
	public MsgModel getNextLevelServerClassList(HttpServletRequest request, HttpServletResponse response) {
		String server_class_id = request.getParameter("server_class_id");
		MsgModel msg = new MsgModel();
		List<ServerClass> serverClassList = serverServiceImpl.getNextLevelServerClassList(server_class_id);
		msg.setContext(serverClassList);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getServerListByClassId")
	public MsgModel getServerListByClassId(HttpServletRequest request) {
		String id = request.getParameter("server_class_id");
		List<Map<String, String>> server_list = serverServiceImpl.getServerListByClassId(id);
		MsgModel msg = new MsgModel();
		msg.setContext(server_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 获取空闲的技术人员
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("getFreeServerMemberList")
	public MsgModel getFreeServerMemberList(HttpServletRequest request) {
		String shop_server_id = request.getParameter("shop_server_id");
		String address_x = request.getParameter("address_x");
		String address_y = request.getParameter("address_y");
		List<Map<String, String>> server_member_list = serverServiceImpl.getFreeServerMemberList(shop_server_id, address_x, address_y);
		MsgModel msg = new MsgModel();
		msg.setContext(server_member_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getSetupTimeList")
	public MsgModel getSetupTimeList(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		List<Map<String, Object>> time_list = serverServiceImpl.getAppointmentServerTimeList(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(time_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getShopServerInfoById")
	public MsgModel getShopServerInfoById(HttpServletRequest request) {
		String shop_server_id = request.getParameter("shop_server_id");
		ShopServer shopServer = shopServerMapper.selectByPrimaryKey(shop_server_id);
		MsgModel msg = new MsgModel();
		msg.setContext(shopServer);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

}
