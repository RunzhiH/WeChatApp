package com.jisu.WeChatApp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.jisu.WeChatApp.dao.MemberAddressMapper;
import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.ServerMemberInfoMapper;
import com.jisu.WeChatApp.pojo.MemberAddress;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.ServerMemberInfo;
import com.jisu.WeChatApp.pojo.ServerMemberInfoExample;
import com.jisu.WeChatApp.service.impl.MemberInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.UserInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MD5Util;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/member")
@RestController
public class MemberController {
	@Autowired
	private MemberInfoServiceImpl memberInfoServiceImpl;
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private UserInfoServiceImpl userInfoServiceImpl;
	@Autowired
	private ServerMemberInfoMapper serverMemberInfoMapper;
	@Autowired
	private MemberAddressMapper memberAddressMapper;

	/**
	 * 获取会员列表
	 * 
	 * @param request
	 * @param Response
	 * @return
	 */
	@RequestMapping("/getMemberListByCondition")
	public MsgModel getMemberListByCondition(HttpServletRequest request, HttpServletResponse Response) {
		String nick_name = request.getParameter("nicke_name");
		String member_type = request.getParameter("member_type");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end) && StringUtils.isBlank(begin)) {
			begin = "0";
		}
		MsgModel msg = new MsgModel();
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("nick_name", nick_name);
		condition.put("member_type", member_type);
		condition.put("begin", begin);
		condition.put("end", end);
		List<Map<String, String>> member_list = memberInfoServiceImpl.getMemberListByCondition(condition);
		msg.setContext(member_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 修改会员类别
	 * 
	 * @param request
	 * @param Response
	 * @return
	 */
	@RequestMapping(value = "updateMemberType", method = RequestMethod.POST)
	public MsgModel updateMemberType(HttpServletRequest request, HttpServletResponse Response) {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String member_type = request.getParameter("member_type");
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMemberNo(member_no);
		memberInfo.setMemberType(Integer.valueOf(member_type));
		int update_num = memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
		if (update_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 根据openid获取用户信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMemberInfoByOpenId")
	public MsgModel getMemberInfoByOpenId(HttpServletRequest request, HttpServletResponse response) {
		String open_id = request.getParameter("open_id");
		MemberInfo memberInfo = userInfoServiceImpl.getMemberInfoByOpenId(open_id);
		MsgModel msg = new MsgModel();
		msg.setContext(memberInfo);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("updateMemberInfo")
	public MsgModel updateMemberInfo(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		String nickname = request.getParameter("nickname");
		String age = request.getParameter("age");
		String sex = request.getParameter("sex");
		String phone = request.getParameter("phone");
		String photo = request.getParameter("photo");
		String alipay_account = request.getParameter("alipay_account");

		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMemberNo(member_no);
		memberInfo.setNickname(nickname);
		if (StringUtils.isNotBlank(age)) {
			memberInfo.setAge(Integer.valueOf(age));
		}
		if (StringUtils.isNotBlank(sex)) {
			memberInfo.setSex(Integer.valueOf(sex));
		}
		memberInfo.setPhone(phone);
		memberInfo.setPhoto(photo);
		memberInfo.setAlipayAccount(alipay_account);
		MemberInfo member= userInfoServiceImpl.editMember(memberInfo);
		MsgModel msg = new MsgModel();
		msg.setContext(member);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getMemberInfoByMemberNo")
	public MsgModel getMemberInfoByMemberNo(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		MemberInfo memberInfo = memberInfoServiceImpl.getMemberInfoByMemberNo(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(memberInfo);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 后台登陆验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "memberLogin", method = RequestMethod.POST)
	public MsgModel memberLogin(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		String password = request.getParameter("password");

		MsgModel msg = new MsgModel();
		MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(member_no);
		String pwdInDb = memberInfo.getPassword();
		if (null != pwdInDb) { // 该用户存在
			try {
				if (MD5Util.validPassword(password, pwdInDb)) {
					msg.setStatus(MsgModel.SUCCESS);
				} else {
					msg.setStatus(MsgModel.ERROR);
				}
			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 修改验证密码
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updatePwd", method = RequestMethod.POST)
	public MsgModel updatePwd(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		String password = request.getParameter("password");
		MsgModel msg = new MsgModel();
		try {
			String pwdInBd = MD5Util.getEncryptedPwd(password);
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setMemberNo(member_no);
			memberInfo.setPassword(pwdInBd);
			int update_num = memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
			if (update_num > 0) {
				msg.setStatus(MsgModel.SUCCESS);
			} else {
				msg.setStatus(MsgModel.ERROR);
			}
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 获取会员收益列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMemberIncomeList")
	public MsgModel getMemberIncomeList(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end) && StringUtils.isBlank(begin)) {
			begin = "0";
		}
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("member_no", member_no);
		condition.put("begin", begin);
		condition.put("end", end);
		List<Map<String, String>> member_income_list = memberInfoServiceImpl.getMemberIncomeByMemberNo(condition);
		MsgModel msg = new MsgModel();
		msg.setContext(member_income_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 获取会员总收益
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMemberTotalIncome")
	public MsgModel getMemberTotalIncome(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		Map<String, String> member_total_income = memberInfoServiceImpl.getMemberTotalIncome(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(member_total_income);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 保存服务人员信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "saveServerMember", method = RequestMethod.POST)
	public MsgModel saveServerMember(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String name = request.getParameter("name");
		String photo = request.getParameter("photo");
		String phone = request.getParameter("phone");
		String sex = request.getParameter("sex");
		String order_takes_type = request.getParameter("order_takes_type");
		// String start_time = request.getParameter("start_time");
		// String end_time = request.getParameter("end_time");
		String desc = request.getParameter("desc");
		String server_class_id_str = request.getParameter("server_class_id_str");

		ServerMemberInfo serverMemberInfo = new ServerMemberInfo();
		serverMemberInfo.setServerMemberDesc(desc);
		// serverMemberInfo.setEndTime(sdf.parse(end_time));
		serverMemberInfo.setMemberNo(member_no);

		serverMemberInfo.setOrderTakesType(Integer.valueOf(order_takes_type));
		serverMemberInfo.setServerClassIdStr(server_class_id_str);
		// serverMemberInfo.setStratTime(sdf.parse(start_time));

		ServerMemberInfoExample example = new ServerMemberInfoExample();
		example.createCriteria().andMemberNoEqualTo(member_no);
		List<ServerMemberInfo> serverMemberInfoList = serverMemberInfoMapper.selectByExample(example);
		int num = 0;
		if (serverMemberInfoList.size() > 0) {
			serverMemberInfo.setServerMemberId(serverMemberInfoList.get(0).getServerMemberId());
			num = serverMemberInfoMapper.updateByPrimaryKeySelective(serverMemberInfo);
		} else {
			serverMemberInfo.setServerMemberId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
			num = serverMemberInfoMapper.insertSelective(serverMemberInfo);
		}

		if (num > 0) {
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setMemberNo(member_no);
			memberInfo.setPhoto(photo);
			memberInfo.setMemberType(2);
			memberInfo.setNickname(name);
			memberInfo.setPhone(phone);
			userInfoServiceImpl.editMember(memberInfo);
			msg.setStatus(MsgModel.SUCCESS);

		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 保存默认地址
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveDefaultAddress", method = RequestMethod.POST)
	public MsgModel saveDefaultAddress(HttpServletResponse response, HttpServletRequest request) {
		String member_no = request.getParameter("memebr_no");
		String address = request.getParameter("address");
		String sheng_code = request.getParameter("sheng_code");
		String shi_code = request.getParameter("shi_code");
		String qu_code = request.getParameter("qu_code");
		String member_address_id = request.getParameter("member_address_id");
		String phone = request.getParameter("phone");
		String nickname = request.getParameter("nickname");

		MemberAddress memberAddress = new MemberAddress();
		memberAddress.setAddress(address);
		memberAddress.setMemberNo(member_no);
		memberAddress.setShengCode(sheng_code);
		memberAddress.setShiCode(shi_code);
		memberAddress.setQuCode(qu_code);
		memberAddress.setNickname(nickname);
		memberAddress.setPhone(phone);
		int save_num = 0;
		if (StringUtils.isNotBlank(member_address_id)) {
			memberAddress.setMemberAddressId(member_address_id);
			save_num = memberAddressMapper.updateByPrimaryKeySelective(memberAddress);
		} else {
			memberAddress.setMemberAddressId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
			save_num = memberAddressMapper.insertSelective(memberAddress);
		}
		MsgModel msg = new MsgModel();
		if (save_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 获取会员默认地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMemberDefaultAddress")
	public MsgModel getMemberDefaultAddress(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		Map<String, String> address_map = memberInfoServiceImpl.getMemebrDefaultAddress(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(address_map);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getServerMemberInfoAndHasServer")
	public MsgModel getServerMemberInfoAndHasServer(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		Map<String, Object> server_member = memberInfoServiceImpl.getServerMemberInfoAndHasServer(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(server_member);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getMemebrListByShopId")
	public MsgModel getMemebrListByShopId(HttpServletRequest request, HttpServletResponse response) {
		String shop_id = request.getParameter("shop_id");
		Map<String, Object> member_list = memberInfoServiceImpl.getMemebrListByShopId(shop_id);
		MsgModel msg = new MsgModel();
		msg.setContext(member_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
