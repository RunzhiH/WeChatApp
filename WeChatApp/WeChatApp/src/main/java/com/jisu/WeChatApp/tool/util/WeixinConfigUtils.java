package com.jisu.WeChatApp.tool.util;

import java.io.InputStream;
import java.util.Properties;

import com.alibaba.druid.support.logging.Log;
import com.alibaba.druid.support.logging.LogFactory;

public class WeixinConfigUtils {

	private static final Log log = LogFactory.getLog(WeixinConfigUtils.class);

	public static String appid;

	public static String mch_id;

	public static String notify_url;

	public static String order_notify_url;

	public static String doctor_notify_url;

	static {
		try {
			InputStream is = WeixinConfigUtils.class.getResourceAsStream("/weixin.properties");
			Properties properties = new Properties();
			properties.load(is);
			appid = PropertyUtil.getProperty("wx.appid");
			mch_id = PropertyUtil.getProperty("wx.mch_id");
			notify_url = PropertyUtil.getProperty("weixin.notify_url");
			order_notify_url = PropertyUtil.getProperty("weixin.order_notify_url");
			doctor_notify_url = PropertyUtil.getProperty("weixin.doctor_notify_url");
		} catch (Exception ex) {
			log.debug("加载配置文件：" + ex.getMessage());
		}
	}
}
