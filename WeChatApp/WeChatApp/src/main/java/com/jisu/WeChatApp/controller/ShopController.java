package com.jisu.WeChatApp.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.pojo.ShopPraiseHistory;
import com.jisu.WeChatApp.pojo.ShopServer;
import com.jisu.WeChatApp.service.impl.ShopInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.UserInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MD5Util;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/shop")
@RestController
@SpringBootApplication
public class ShopController {
	@Autowired
	private ShopInfoMapper shopInfoMapper;
	@Autowired
	private ShopInfoServiceImpl shopInfoServiceImpl;
	@Autowired
	private ShopServerMapper shopServerMapper;
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private UserInfoServiceImpl userInfoServiceImpl;

	/**
	 * 根据条件获取店铺列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getShopListByCondition", method = RequestMethod.GET)
	public MsgModel getShopListByCondition(HttpServletRequest request, HttpServletResponse response) {
		String shop_name = request.getParameter("shop_name");
		String user_x = request.getParameter("user_x");
		String user_y = request.getParameter("user_y");
		String shop_qu = request.getParameter("shop_qu");
		String shop_class_id = request.getParameter("shop_class_id");
		String shop_status = request.getParameter("shop_status");
		String juli = request.getParameter("juli");
		String shi_code = request.getParameter("shi_code");
		String praise_points_type = request.getParameter("praise_points_type");
		String juli_sort = request.getParameter("juli_sort");
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		if (StringUtils.isNotBlank(end) && StringUtils.isBlank(begin)) {
			begin = "0";
		}
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("shop_name", shop_name);
		if (StringUtils.isNotBlank(juli)) {
			condition.put("juli", juli);
		}
		condition.put("user_x", user_x);
		condition.put("user_y", user_y);
		condition.put("shi_code", shi_code);
		condition.put("shop_qu", shop_qu);
		condition.put("shop_class_id", shop_class_id);
		condition.put("shop_status", shop_status);
		condition.put("begin", begin);
		condition.put("end", end);
		condition.put("praise_points_type", praise_points_type);
		condition.put("juli_sort", juli_sort);
		List<Map<String, String>> shop_list = shopInfoServiceImpl.getShopListByCondition(condition);
		MsgModel msg = new MsgModel();
		msg.setContext(shop_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 添加新的商家
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveShopInfo", method = RequestMethod.POST)
	public MsgModel saveShopInfo(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String shop_name = request.getParameter("shop_name");
		String shop_describe = request.getParameter("shop_describe");
		String shop_sheng = request.getParameter("shop_sheng");
		String shop_shi = request.getParameter("shop_shi");
		String shop_qu = request.getParameter("shop_qu");
		String shop_x = request.getParameter("shop_x");
		String shop_y = request.getParameter("shop_y");
		String shop_photo_url = request.getParameter("shop_photo_url");
		String shop_address = request.getParameter("shop_address");
		String shop_logo_url = request.getParameter("shop_logo_url");
		String shop_phone = request.getParameter("shop_phone");
		String shop_class_id = request.getParameter("shop_class_id");
		String shop_id = request.getParameter("shop_id");
		String member_no = request.getParameter("member_no");
		String authorization_book_url = request.getParameter("authorization_book_url");
		String qq = request.getParameter("qq");
		String wechat = request.getParameter("wechat");
		String operator_member_id = request.getParameter("operator_memebr_id");
		String desc_photo_url = request.getParameter("desc_photo_url");
		ShopInfo shopInfo = new ShopInfo();
		if (StringUtils.isNotBlank(authorization_book_url)) {
			shopInfo.setAuthorizationBookUrl(authorization_book_url);
		} else {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("授权书不能为空");
			return msg;
		}
		if (StringUtils.isNotBlank(shop_address)) {
			shopInfo.setShopAddress(shop_address);
		}
		if (StringUtils.isNotBlank(shop_describe)) {
			shopInfo.setShopDescribe(shop_describe);
		}
		if (StringUtils.isNotBlank(shop_logo_url)) {
			shopInfo.setShopLogoUrl(shop_logo_url);
		}
		if (StringUtils.isNotBlank(shop_name)) {
			shopInfo.setShopName(shop_name);
		}
		if (StringUtils.isNotBlank(shop_phone)) {
			shopInfo.setShopPhone(shop_phone);
		}
		if (StringUtils.isNotBlank(shop_photo_url)) {
			shopInfo.setShopPhotoUrl(shop_photo_url);

		}
		if (StringUtils.isNotBlank(shop_sheng)) {
			shopInfo.setShopSheng(shop_sheng);
		}
		if (StringUtils.isNotBlank(shop_shi)) {
			shopInfo.setShopShi(shop_shi);
		}
		if (StringUtils.isNotBlank(shop_qu)) {
			shopInfo.setShopQu(shop_qu);
		}
		if (StringUtils.isNotBlank(shop_x)) {
			shopInfo.setShopX(new BigDecimal(shop_x));
		}
		if (StringUtils.isNotBlank(shop_y)) {
			shopInfo.setShopY(new BigDecimal(shop_y));
		}
		if (StringUtils.isNotBlank(shop_class_id)) {
			shopInfo.setShopClassId(shop_class_id);
		}

		if (StringUtils.isNotBlank(qq)) {
			shopInfo.setQq(qq);
		}
		if (StringUtils.isNotBlank(wechat)) {
			shopInfo.setWechat(wechat);
		}
		if (StringUtils.isNotBlank(desc_photo_url)) {
			shopInfo.setDescPhotoUrl(desc_photo_url);
		}
		int save_num = 0;
		if (StringUtils.isNotBlank(shop_id)) {
			shopInfo.setShopId(shop_id);
			save_num = shopInfoMapper.updateByPrimaryKeySelective(shopInfo);
		} else {
			shopInfo.setCreateTime(new Date());
			shop_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			shopInfo.setShopId(shop_id);
			shopInfo.setPraisePoints(0);
			shopInfo.setShopStatus(0);
			shopInfo.setOperatorMemberId(operator_member_id);
//			try {
//				// 加密密码
//				shopInfo.setPassword(MD5Util.getEncryptedPwd(password));
//			} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			if (StringUtils.isNotBlank(member_no)) {
				shopInfo.setMemberNo(member_no);
			}
			save_num = shopInfoMapper.insertSelective(shopInfo);
			// 修改会员身份为商家
			MemberInfo memberInfo = new MemberInfo();
			memberInfo.setMemberNo(member_no);
			memberInfo.setMemberType(1);
			memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
			// 修改会员是否为商家结束
		}

		if (save_num > 0) {
//			// 保存商家服务信息
//			String shop_server_str = request.getParameter("shop_server_str");
//			if (StringUtils.isNotBlank(shop_server_str)) {
//				shopInfoServiceImpl.saveShopServerInfo(shop_server_str, shop_id);
//			}
			// 保存商家服务信息结束

			// 保存标签信息
			String shop_lable_str = request.getParameter("shop_lable_str");
			if (StringUtils.isNotBlank(shop_lable_str)) {
				shopInfoServiceImpl.saveShopLableInfo(shop_lable_str, shop_id);
			}
			// 保存标签信息结束

			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 删除商家
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteShopByShopId", method = RequestMethod.POST)
	public MsgModel deleteShopByShopId(HttpServletRequest request, HttpServletResponse Response) {
		String shop_id_str = request.getParameter("shop_id_str");
		int del_num = shopInfoServiceImpl.deleteShopByShopId(shop_id_str);
		MsgModel msg = new MsgModel();
		if (del_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 获取带区域排名的商家详情
	 * 
	 * @param request
	 * @param Response
	 * @return
	 */
	@RequestMapping(value = "getShopInfoByShopId")
	public MsgModel getShopInfoByShopId(HttpServletRequest request, HttpServletResponse Response) {
		String shop_id = request.getParameter("shop_id");
		String shi_code = request.getParameter("shi_code");
		String member_no = request.getParameter("member_no");
		Map<String, String> shop_info = shopInfoServiceImpl.getShopInfoRank(shop_id, shi_code, member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(shop_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 获取商家服务信息
	 * 
	 * @param request
	 * @param Response
	 * @return
	 */
	@RequestMapping(value = "getShopServerByShopId")
	public MsgModel getShopServerByShopId(HttpServletRequest request, HttpServletResponse Response) {
		String shop_id = request.getParameter("shop_id");
		List<Map<String, String>> server_list = shopInfoServiceImpl.getShopServerByShopId(shop_id);
		MsgModel msg = new MsgModel();
		msg.setContext(server_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 获取商家标签
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "getShopLableByshopId")
	public MsgModel getShopLableByshopId(HttpServletRequest request, HttpServletResponse response) {
		String shop_id = request.getParameter("shop_id");
		List<Map<String, String>> lable_list = shopInfoServiceImpl.getShopLableByShopId(shop_id);
		MsgModel msg = new MsgModel();
		msg.setContext(lable_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 商家点赞
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "doShopLike")
	public MsgModel doShopLike(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String shop_id = request.getParameter("shop_id");
		String is_like = request.getParameter("is_like");
		int num = 0;
		if ("0".equals(is_like)) {
			// 是否已点赞
			List<Map<String, String>> histories = shopInfoServiceImpl.getShopPraiseHistoriesByMember(shop_id, member_no);
			if (histories.size() > 0) {
				msg.setStatus(MsgModel.ERROR);
				msg.setMessage("您已点赞!");
				return msg;
			}
			// 是否已点赞结束
			// 插入点赞历史
			num = shopInfoServiceImpl.insertShopPraiseHistory(shop_id, member_no);
			// 插入点赞历史结束
			// 未绑定商家时 绑定该商家
			MemberInfo member = memberInfoMapper.selectByPrimaryKey(member_no);
			String share_shop_id = member.getShareShopId();
			// 是否有推荐商家
			if (StringUtils.isNotBlank(share_shop_id) && StringUtils.isNotBlank(shop_id)) {
				member.setShareShopId(shop_id);
				userInfoServiceImpl.editMember(member);
			}

		} else {
			// 删除点赞历史
			num = shopInfoServiceImpl.deleteShopPraiseHistory(shop_id, member_no);
			// 删除点赞历史结束
		}
		if (num > 0) {
			shopInfoServiceImpl.doMemberLikeShop(shop_id, is_like);
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("点赞失败");

		}
		return msg;
	}

	/**
	 * 保存商家服务
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/saveShopServer", method = RequestMethod.POST)
	public MsgModel saveShopServer(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String server_name = request.getParameter("servcer_name");
		String sercer_desc = request.getParameter("server_desc");
		String shop_id = request.getParameter("shop_id");
		String server_url = request.getParameter("server_url");
		String server_price = request.getParameter("server_price");
		String shop_server_id = request.getParameter("shop_server_id");
		String server_sale_price = request.getParameter("server_sale_price");
		String server_class_id = request.getParameter("server_class_id");
		ShopServer shopServer = new ShopServer();
		shopServer.setServerName(server_name);
		shopServer.setServerDesc(sercer_desc);
		shopServer.setServerPrice(new BigDecimal(server_price));
		shopServer.setServerUrl(server_url);
		shopServer.setShopId(shop_id);
		shopServer.setServerSalePrice(new BigDecimal(server_sale_price));
		shopServer.setServerClassId(server_class_id);
		int save_num = 0;
		if (StringUtils.isNotBlank(shop_server_id)) {
			shopServer.setShopServerId(shop_server_id);
			shopServerMapper.updateByPrimaryKeySelective(shopServer);
		} else {
			shopServer.setShopServerId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
			shopServerMapper.insertSelective(shopServer);
		}
		if (save_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 商家登陆后台验证
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "loginShop", method = RequestMethod.POST)
	public MsgModel loginShop(HttpServletRequest request, HttpServletResponse response) {
		String shop_id = request.getParameter("shop_id");
		String password = request.getParameter("password");
		MsgModel msg = new MsgModel();
		ShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(shop_id);
		String pwdInDb = shopInfo.getPassword();
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
		String shop_id = request.getParameter("shop_id");
		String password = request.getParameter("password");
		MsgModel msg = new MsgModel();
		try {
			String pwdInBd = MD5Util.getEncryptedPwd(password);
			ShopInfo shopInfo = new ShopInfo();
			shopInfo.setShopId(shop_id);
			shopInfo.setPassword(pwdInBd);
			int update_num = shopInfoMapper.updateByPrimaryKeySelective(shopInfo);
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
	 * 获取最近去过的店
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getMyLatelyShopList")
	public MsgModel getMyLatelyShopList(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		List<Map<String, String>> shop_list = shopInfoServiceImpl.getMyLatelyShopList(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(shop_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 绑定商家
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "bindingShop")
	public MsgModel bindingShop(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		String shop_id = request.getParameter("shop_id");
		MemberInfo memberInfo = memberInfoMapper.selectByPrimaryKey(member_no);
		String share_shop_id = memberInfo.getShareShopId();
		MsgModel msg = new MsgModel();
		if (StringUtils.isNotBlank(share_shop_id)) {
			memberInfo.setShareShopId(shop_id);
			int num = memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
			if (num > 0) {
				msg.setStatus(MsgModel.SUCCESS);
			} else {
				msg.setStatus(MsgModel.ERROR);
			}
		} else {
			msg.setMessage("已绑定商家");

		}
		return msg;
	}

	@RequestMapping("getShopInfoByMemberNo")
	public MsgModel getShopInfoByMemberNo(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		Map<String, Object> shop_info = shopInfoServiceImpl.getshopInfoByMemberNo(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(shop_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getShopInfoByShopIdAndClassId")
	public MsgModel getShopInfoByShopIdAndClassId(HttpServletRequest request) {
		String shop_id = request.getParameter("shop_id");
		String server_class_id = request.getParameter("server_class_id");
		Map<String, String> shop_info = shopInfoServiceImpl.getShopInfoByShopIdAndClassId(shop_id, server_class_id);
		MsgModel msg = new MsgModel();
		msg.setContext(shop_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
