package com.jisu.WeChatApp.tool.util;

/**
 * 获取拼接后的微信接口调用地址
 * 
 * @author hongrunzhi
 *
 */
public class WeChatURLUtil {
	static String appid = PropertyUtil.getProperty("wx.appid");
	static String secret = PropertyUtil.getProperty("wx.secret");
	// 获取code的请求地址
	public static String Get_Code = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=STAT#wechat_redirect";

	// 替换字符串
	public static String getCode(String REDIRECT_URI, String SCOPE) {
		return String.format(Get_Code, appid, REDIRECT_URI, SCOPE);
	}

	// 获取Web_access_tokenhttps的请求地址
	public static String Web_access_tokenhttps = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

	public static String auth_access_tokenHttps = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";

	// 替换字符串
	public static String getWebAccess(String CODE) {
		return String.format(Web_access_tokenhttps, /* 这里填小程序的appid */appid, /* 这里填该小程序的SECRET */secret, CODE);
	}

	// 拉取用户信息的请求地址
	public static String User_Message = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";

	// 替换字符串
	public static String getUserMessage(String openid) {
		String access_token = WechatGetUtil.getAccessToken();
		return String.format(User_Message, access_token, openid);
	}

	// 替换字符串
	public static String getAccessTokenUrl() {
		return String.format(auth_access_tokenHttps, /* 这里填小程序的appid */appid, /* 这里填该小程序的SECRET */secret);
	}

	private static String wxacodeunlimit_https = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=%s";

	public static String getWxaCodeunLimit() {
		String access_token = WechatGetUtil.getAccessToken();
		return String.format(wxacodeunlimit_https, access_token);
	}

	// 推送url
	private static String wx_push_message_https = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/send?access_token=%s";

	public static String getPushMsgUrl() {
		String access_token = WechatGetUtil.getAccessToken();
		return String.format(wx_push_message_https, access_token);
	}
}
