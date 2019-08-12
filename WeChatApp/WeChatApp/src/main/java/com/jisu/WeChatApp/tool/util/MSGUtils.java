package com.jisu.WeChatApp.tool.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jisu.WeChatApp.WeChatAppApplication;
import com.jisu.WeChatApp.messge.MessageTemplate;

public class MSGUtils {
	private final static Logger logger = LoggerFactory.getLogger(MSGUtils.class);
	@Autowired
	private static RestTemplate restTemplate;

	/*
	 * 微信小程序推送单个用户
	 */
	public static String pushOneUser(String formid, Map<String, String> sendMsgContext) {
		// 获取access_token
		String url = WeChatURLUtil.getPushMsgUrl();
		String openid = sendMsgContext.get("openid");
		String temp_no = sendMsgContext.get("temp_no");
		RestTemplate rest = new RestTemplate();
		// 拼接推送的模版
		MessageTemplate wxMssVo = (MessageTemplate) SpringContextUtil.getBean(temp_no);
		wxMssVo.setTouser(openid);// 用户openid
		wxMssVo.setTemplate_id(sendMsgContext.get("wx_template_id"));// 模版id
		wxMssVo.setForm_id(formid);// formid
		wxMssVo.setPage(sendMsgContext.get("message_url"));
		wxMssVo.setDataInfo(sendMsgContext);
		ResponseEntity<String> responseEntity = rest.postForEntity(url, wxMssVo, String.class);
		logger.error("小程序推送结果={}", responseEntity.getBody());
		return responseEntity.getBody();
	}
	
//	public static void main(String[] args) {
//		ApplicationContext app = SpringApplication.run(WeChatAppApplication.class, args);
//		SpringContextUtil.setApplicationContext(app);
//		Map<String, String> sendMsgContext= new HashMap<String, String>();
//		sendMsgContext.put("openid", "oucVK5CFskM1--ssOjqIqd4mgVNU");
//		sendMsgContext.put("temp_no", "AT0009");
//		sendMsgContext.put("message_url", "");
//		sendMsgContext.put("wx_template_id", "5XuR-B5XzC_aNxGkOHui1FsPls--OFy9Ja_LPXZBcvE");
//		sendMsgContext.put("create_time", "2019-06-14 21:11:24");
//		sendMsgContext.put("order_code", "2512387784385283");
//		sendMsgContext.put("pay_price", "0.01");
//		sendMsgContext.put("appointment_time_start", "2018-12-30 12:00:00");
//		sendMsgContext.put("server_name", "剪发设计");
//		sendMsgContext.put("server_address", "浙江省宁波市江北区风华路818宁波大学");
//		String result=pushOneUser("19ae387ca0954b8c8f7bcdc2057bdc11",sendMsgContext);
//		System.out.println(result);
//	}
}
