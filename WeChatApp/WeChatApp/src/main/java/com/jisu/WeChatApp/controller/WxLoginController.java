package com.jisu.WeChatApp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.service.impl.MemberInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.UserInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.WalletInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.HttpsUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.WeChatURLUtil;
import com.jisu.WeChatApp.tool.util.WechatGetUtil;

@RestController
@RequestMapping("/api/login")
public class WxLoginController {
	@Autowired
	private UserInfoServiceImpl userInfoServiceImpl;// 用于用户信息的curd

	@Autowired
	private WalletInfoServiceImpl walletInfoServiceImpl;

	/**
	 * 登录 通过js_code 获取openid及session_key
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("null")
	@ResponseBody
	@RequestMapping("/vote")
	public Map<String, Object> listVote(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String code = request.getParameter("js_code");
		String encryptedData = request.getParameter("encryptedData");
		String iv = request.getParameter("iv");
		System.out.println("》》》收到请求，请求数据为[code：" + code + "]");
		Map<String, Object> map = new HashMap<>();
		// 通过code换取网页授权web_access_token
		if (code != null || !(code.equals(""))) {
			String CODE = code;
			String WebAccessToken = "";
			String openId = "";
			String userId = "";
			// 替换字符串，获得请求URL
			String token = WeChatURLUtil.getWebAccess(CODE);// 通过自定义工具类组合出小程序需要的登录凭证 code
			// System.out.println("》》》组合token为：" + token);
			// 通过https方式请求获得web_access_token并获得小程序的返回
			String resultStr = HttpsUtil.httpsRequestToString(token, "GET", null);
			// 通过JsonObject解析小程序返回数据
			JSONObject jsonObject = JSON.parseObject(resultStr);
			System.out.println("jsonObject>>>" + jsonObject);
			// 如果JasonObject或opeid为空则登录失败
			if (null != jsonObject && jsonObject.getString("openid") != null) {

				// 从jsonObject中获取sessionKey的值
				WebAccessToken = jsonObject.getString("session_key");
				// 获取openid
				openId = jsonObject.getString("openid");
				if (StringUtils.isNotBlank(jsonObject.getString("openid")) && StringUtils.isNotBlank(jsonObject.getString("session_key"))) {
					try {
						// 解密获取用户信息
						JSONObject userInfoJSON = WechatGetUtil.getUserInfo(encryptedData, jsonObject.getString("session_key"), iv);
						if (userInfoJSON != null) {
							// 这步应该set进实体类
							Map<String, String> userInfo = new HashMap<String, String>();
							userInfo.put("openId", String.valueOf(userInfoJSON.get("openId")));
							userInfo.put("nickName", String.valueOf(userInfoJSON.get("nickName")));
							userInfo.put("gender", String.valueOf(userInfoJSON.get("gender")));
							userInfo.put("city", String.valueOf(userInfoJSON.get("city")));
							userInfo.put("province", String.valueOf(userInfoJSON.get("province")));
							userInfo.put("country", String.valueOf(userInfoJSON.get("country")));
							userInfo.put("avatarUrl", String.valueOf(userInfoJSON.get("avatarUrl")));
							// 解密unionId & openId;
							if (userInfoJSON.get("unionId") != null) {
								userInfo.put("unionId", String.valueOf(userInfoJSON.get("unionId")));
							}
							// 然后根据openid去数据库判断有没有该用户信息，若没有则存入数据库，有则返回用户数据
							MemberInfo member = userInfoServiceImpl.getMemberInfoByOpenId(openId);
							if (member == null) {

								MemberInfo member_info = new MemberInfo();
								member_info.setOpenid(openId);
								member = userInfoServiceImpl.editMember(member_info);
								member.setNickname(userInfo.get("nickName"));
								member.setPhoto(userInfo.get("avatarUrl"));
								member = userInfoServiceImpl.editMember(member);
								map.put("context", member);
								// 添加钱包
								walletInfoServiceImpl.insertNewWallet(member.getMemberNo());
								// 添加钱包结束
							} else {
								String share_shop_id = member.getShareShopId();
								member = userInfoServiceImpl.editMember(member);
								userId = member.getMemberNo();
								map.put("context", member);
							}
							// 登陆操作
							map.put("status", 200);
							map.put("msg", "登录成功");
						}

					} catch (JSONException e) {
						e.printStackTrace();
						WebAccessToken = null;// 获取code失败
						System.out.println("获取session_key失败");
						map.put("stauts", 500);
						map.put("msg", "登录失败");
					}
				} else {
					System.out.println("获取openid及session_key失败");
					map.put("stauts", 500);
					map.put("msg", "登录失败");
				}
			}
		}
		return map;

	}

	/**
	 * 小程序登陆后台
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "wxLogin", method = RequestMethod.POST)
	public MsgModel wxLogin(HttpServletRequest request, HttpServletResponse response) throws Exception {
		MsgModel msg = new MsgModel();
		String session_key = request.getParameter("session_key");
		String encryptedData = request.getParameter("encryptedData");
		String iv = request.getParameter("iv");
		if (StringUtils.isBlank(encryptedData)) {
			msg.setMessage("encryptedData为空");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		if (StringUtils.isBlank(session_key)) {
			msg.setMessage("session_key为空");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		if (StringUtils.isBlank(iv)) {
			msg.setMessage("iv为空");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		System.err.println("encryptedData=" + encryptedData + ",session_key=" + session_key + ",iv:" + iv);
		JSONObject userInfoJSON = WechatGetUtil.getUserInfo(encryptedData, session_key, iv);
		if (userInfoJSON != null) {
			// 这步应该set进实体类
			Map<String, String> userInfo = new HashMap<String, String>();
			userInfo.put("openId", String.valueOf(userInfoJSON.get("openId")));
			userInfo.put("nickName", String.valueOf(userInfoJSON.get("nickName")));
			userInfo.put("gender", String.valueOf(userInfoJSON.get("gender")));
			userInfo.put("city", String.valueOf(userInfoJSON.get("city")));
			userInfo.put("province", String.valueOf(userInfoJSON.get("province")));
			userInfo.put("country", String.valueOf(userInfoJSON.get("country")));
			userInfo.put("avatarUrl", String.valueOf(userInfoJSON.get("avatarUrl")));
			// 解密unionId & openId;
			if (userInfoJSON.get("unionId") != null) {
				userInfo.put("unionId", String.valueOf(userInfoJSON.get("unionId")));
			}
			// 然后根据openid去数据库判断有没有该用户信息，若没有则存入数据库，有则返回用户数据
			String openId = userInfo.get("openId");
			MemberInfo member = userInfoServiceImpl.getMemberInfoByOpenId(openId);
			if (member == null) {
				MemberInfo member_info = new MemberInfo();
				member_info.setOpenid(openId);
				member = userInfoServiceImpl.editMember(member_info);
				member.setNickname(userInfo.get("nickName"));
				member.setPhoto(userInfo.get("avatarUrl"));
				userInfoServiceImpl.editMember(member);
				// 添加钱包
				walletInfoServiceImpl.insertNewWallet(member.getMemberNo());
				// 添加钱包结束
			} else {
				member.setNickname(userInfo.get("nickName"));
				member.setPhoto(userInfo.get("avatarUrl"));
				userInfoServiceImpl.editMember(member);
			}
			// 登陆操作
			msg.setContext(member);
			msg.setStatus(MsgModel.SUCCESS);
		}
		return msg;
	}

	/**
	 * 获取用户openid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getOpenId")
	public MsgModel getOpenId(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String code = request.getParameter("js_code");
		if (code != null || !"".equals(code)) {
			String CODE = code;
			// 替换字符串，获得请求URL
			String token = WeChatURLUtil.getWebAccess(CODE);// 通过自定义工具类组合出小程序需要的登录凭证 code
			// System.out.println("》》》组合token为：" + token);
			// 通过https方式请求获得web_access_token并获得小程序的返回
			String resultStr = HttpsUtil.httpsRequestToString(token, "GET", null);
			// 通过JsonObject解析小程序返回数据
			JSONObject jsonObject = JSON.parseObject(resultStr);
			if (null != jsonObject && jsonObject.getString("openid") != null) {
				msg.setContext(jsonObject);
				msg.setStatus(MsgModel.SUCCESS);
			} else {
				// System.out.println("获取openid及session_key失败");
				msg.setMessage(MsgModel.ERROR);
				msg.setMessage("登陆失败");
			}
		} else {
			msg.setMessage(MsgModel.ERROR);
			msg.setMessage("js_code为空");
		}
		return msg;
	}

}
