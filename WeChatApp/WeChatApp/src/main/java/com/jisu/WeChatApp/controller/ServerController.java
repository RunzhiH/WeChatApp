package com.jisu.WeChatApp.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.ServerMemberInfoMapper;
import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.ServerClass;
import com.jisu.WeChatApp.pojo.ServerMemberInfo;
import com.jisu.WeChatApp.pojo.ServerMemberInfoExample;
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
	@Autowired
	private ServerMemberInfoMapper serverMemberInfoMapper;
	@Autowired
	private MemberInfoMapper memberInfoMapper;

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
		String exper_id = request.getParameter("exper_id");
		String address_x = request.getParameter("address_x");
		String address_y = request.getParameter("address_y");
		List<Map<String, String>> server_member_list = serverServiceImpl.getFreeServerMemberList(exper_id, address_x, address_y);
		MsgModel msg = new MsgModel();
		msg.setContext(server_member_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getSetupTimeList")
	public MsgModel getSetupTimeList(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		String shop_id = request.getParameter("shop_id");
		List<Map<String, Object>> time_list = serverServiceImpl.getAppointmentServerTimeList(member_no, shop_id);
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

	@RequestMapping(value = "saveRestTime", method = RequestMethod.POST)
	public MsgModel saveRestTime(HttpServletRequest request) {
		String rest_time_list_str = request.getParameter("rest_time_list");
		String member_no = request.getParameter("member_no");
		MsgModel msg = new MsgModel();
		int num = serverServiceImpl.saveMemberRestTime(member_no, rest_time_list_str);
		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	@RequestMapping(value = "getRestTimeList")
	public MsgModel getRestTimeList(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		List<Map<String, String>> rest_time_list = serverServiceImpl.getRestTimeList(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(rest_time_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getServerMemberListRank")
	public MsgModel getServerMemberListRank(HttpServletRequest request) {
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("begin", begin);
		condition.put("end", end);
		if (StringUtils.isNotBlank(end) && StringUtils.isBlank(begin)) {
			condition.put("begin", "0");
			condition.put("end", end);
		}
		List<Map<String, String>> server_member_list = serverServiceImpl.getServerMemberListRank(condition);
		MsgModel msg = new MsgModel();
		msg.setContext(server_member_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping(value = "saveServerMember", method = RequestMethod.POST)
	public MsgModel saveServerMember(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		String nickname = request.getParameter("nickname");
		String phone = request.getParameter("phone");
		String short_desc = request.getParameter("short_desc");
		String server_member_desc = request.getParameter("server_member_desc");
		String server_class_id_str = request.getParameter("server_class_id_str");
		String lat = request.getParameter("lat");
		String lon = request.getParameter("lon");
		String photo_desc = request.getParameter("photo_desc");

		ServerMemberInfo serverMemberInfo = new ServerMemberInfo();
		serverMemberInfo.setLat(new BigDecimal(lat));
		serverMemberInfo.setLon(new BigDecimal(lon));
		serverMemberInfo.setMemberNo(member_no);
		serverMemberInfo.setOrderTakesType(2);
		serverMemberInfo.setPhotoDesc(photo_desc);
		serverMemberInfo.setServerClassIdStr(server_class_id_str);
		serverMemberInfo.setServerMemberDesc(server_member_desc);
		serverMemberInfo.setShortDesc(short_desc);
		MsgModel msg = new MsgModel();
		ServerMemberInfoExample example = new ServerMemberInfoExample();
		example.createCriteria().andMemberNoEqualTo(member_no);
		List<ServerMemberInfo> oldServerMemberInfo = serverMemberInfoMapper.selectByExample(example);
		int num = 0;
		if (oldServerMemberInfo.size() > 0) {
			String server_member_id = oldServerMemberInfo.get(0).getServerMemberId();
			serverMemberInfo.setServerMemberId(server_member_id);
			num = serverMemberInfoMapper.updateByPrimaryKeySelective(serverMemberInfo);
		} else {
			num = serverMemberInfoMapper.insertSelective(serverMemberInfo);
		}
		if (num > 0) {
			msg.setContext(serverMemberInfo);
			msg.setStatus(MsgModel.SUCCESS);
			
			MemberInfo member_info= new MemberInfo();
			member_info.setMemberNo(member_no);
			member_info.setPhone(phone);
			member_info.setNickname(nickname);
			member_info.setMemberType(2);
			memberInfoMapper.updateByPrimaryKeySelective(member_info);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}
}
