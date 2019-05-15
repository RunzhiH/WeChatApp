package com.jisu.WeChatApp.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.service.impl.MemberInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.UserInfoServiceImpl;
import com.jisu.WeChatApp.service.impl.WalletInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.HttpsUtil;
import com.jisu.WeChatApp.tool.util.MD5Util;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.WeChatURLUtil;
import com.jisu.WeChatApp.tool.util.WechatGetUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	@Autowired
	private UserInfoServiceImpl userInfoServiceImpl;// 用于用户信息的curd

	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private WalletInfoServiceImpl walletInfoServiceImpl;
	// 注入ehcache管理器
	@Autowired
	private EhCacheManager ecm;

	@Autowired
	private MemberInfoServiceImpl memberInfoServiceImpl;

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

	/**
	 * 登录【使用shiro中自带的HashedCredentialsMatcher结合ehcache（记录输错次数）配置进行密码输错次数限制】 </br>
	 * 缺陷是，无法友好的在后台提供解锁用户的功能，当然，可以直接提供一种解锁操作，清除ehcache缓存即可，不记录在用户表中； </br>
	 * 
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public MsgModel login(HttpServletRequest request, HttpServletResponse response) {
		String user = request.getParameter("username");
		String pwd = request.getParameter("pwd");
		String rememberMe = request.getParameter("rememberMe");
		logger.debug("用户登录，请求参数=user:" + user + "，是否记住我：" + rememberMe);
		MsgModel msg = new MsgModel();
		if (null == user) {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("请求参数有误，请您稍后再试");
			logger.debug("用户登录，结果=responseResult:" + msg);
			return msg;
		}
		// 用户是否存在
		MemberInfo existUser = memberInfoServiceImpl.getMemberInfoByUsername(user);
		if (existUser == null) {
			msg.setMessage("该用户不存在，请您联系管理员");
			logger.debug("用户登录，结果=responseResult:" + msg);
			return msg;
		} else {
			// 是否离职
//			if (existUser.getIsJob()) {
//				msg.setMessage("登录用户已离职，请您联系管理员");
//				logger.debug("用户登录，结果=responseResult:" + msg);
//				return msg;
//			}
			// 校验验证码
			/*
			 * if(!existUser.getMcode().equals(user.getSmsCode())){ //不等
			 * responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
			 * responseResult.setMessage("短信验证码输入有误");
			 * logger.debug("用户登录，结果=responseResult:"+responseResult); return
			 * responseResult; } //1分钟 long beginTime =existUser.getSendTime().getTime();
			 * long endTime = new Date().getTime(); if(((endTime-beginTime)-60000>0)){
			 * responseResult.setCode(IStatusMessage.SystemStatus.PARAM_ERROR.getCode());
			 * responseResult.setMessage("短信验证码超时");
			 * logger.debug("用户登录，结果=responseResult:"+responseResult); return
			 * responseResult; }
			 */
		}
		// 用户登录
		try {
			// 1、 封装用户名、密码、是否记住我到token令牌对象 [支持记住我]
			AuthenticationToken token = new UsernamePasswordToken(user, DigestUtils.md5Hex(pwd), rememberMe);
			// 2、 Subject调用login
			Subject subject = SecurityUtils.getSubject();
			// 在调用了login方法后,SecurityManager会收到AuthenticationToken,并将其发送给已配置的Realm执行必须的认证检查
			// 每个Realm都能在必要时对提交的AuthenticationTokens作出反应
			// 所以这一步在调用login(token)方法时,它会走到MyRealm.doGetAuthenticationInfo()方法中,具体验证方式详见此方法
			logger.debug("用户登录，用户验证开始！user=" + user);
			subject.login(token);
			msg.setStatus(MsgModel.SUCCESS);
			logger.info("用户登录，用户验证通过！user=" + user);
		} catch (UnknownAccountException uae) {
			logger.error("用户登录，用户验证未通过：未知用户！user=" + user, uae);
			msg.setMessage("该用户不存在，请您联系管理员");
		} catch (IncorrectCredentialsException ice) {
			// 获取输错次数
			logger.error("用户登录，用户验证未通过：错误的凭证，密码输入错误！user=" + user, ice);
			msg.setMessage("用户名或密码不正确");
		} catch (LockedAccountException lae) {
			logger.error("用户登录，用户验证未通过：账户已锁定！user=" + user, lae);
			msg.setMessage("账户已锁定");
		} catch (ExcessiveAttemptsException eae) {
			logger.error("用户登录，用户验证未通过：错误次数大于5次,账户已锁定！user=.getMobile()" + user, eae);
			msg.setMessage("用户名或密码错误次数大于5次,账户已锁定!</br><span style='color:red;font-weight:bold; '>2分钟后可再次登录，或联系管理员解锁</span>");
			// 这里结合了，另一种密码输错限制的实现，基于redis或mysql的实现；也可以直接使用RetryLimitHashedCredentialsMatcher限制5次
		} /*
			 * catch (DisabledAccountException sae){
			 * logger.error("用户登录，用户验证未通过：帐号已经禁止登录！user=" + user.getMobile(),sae);
			 * responseResult.setCode(IStatusMessage.SystemStatus.ERROR.getCode());
			 * responseResult.setMessage("帐号已经禁止登录"); }
			 */catch (AuthenticationException ae) {
			// 通过处理Shiro的运行时AuthenticationException就可以控制用户登录失败或密码错误时的情景
			logger.error("用户登录，用户验证未通过：认证异常，异常信息如下！user=" + user, ae);
			msg.setMessage("用户名或密码不正确");
		} catch (Exception e) {
			logger.error("用户登录，用户验证未通过：操作异常，异常信息如下！user=" + user, e);
			msg.setMessage("用户登录失败，请您稍后再试");
		}
//		Cache<String, AtomicInteger> passwordRetryCache = ecm.getCache("passwordRetryCache");
//		if (null != passwordRetryCache) {
//			int retryNum = (passwordRetryCache.get(existUser.getMemberNo()) == null ? 0 : passwordRetryCache.get(existUser.getMemberNo())).intValue();
//			logger.debug("输错次数：" + retryNum);
//			if (retryNum > 0 && retryNum < 6) {
//				msg.setMessage("用户名或密码错误" + retryNum + "次,再输错" + (6 - retryNum) + "次账号将锁定");
//			}
//		}
//		logger.debug("用户登录，user=" + user + ",登录结果=responseResult:" + msg);
		return msg;
	}
	@RequestMapping(value="updateMmeberPwd",method=RequestMethod.POST)
	public MsgModel updateMmeberPwd(HttpServletRequest request,HttpServletResponse response) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		String member_no=request.getParameter("member_no");
		String pwd=request.getParameter("pwd");
		String MD5_pwd=DigestUtils.md5Hex(pwd);
		
		MemberInfo memberInfo = new MemberInfo();
		memberInfo.setMemberNo(member_no);
		memberInfo.setPassword(MD5_pwd);
		memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
		return  null;
	}
}
