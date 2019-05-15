package com.jisu.WeChatApp.tool.util;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.jisu.WeChatApp.messge.MessageTemplate;

public class MSGUtils {
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
		
		// 拼接推送的模版
		MessageTemplate wxMssVo = (MessageTemplate) SpringContextUtil.getBean(temp_no);
		wxMssVo.setOpenid(openid);// 用户openid
		wxMssVo.setTemplate_id(sendMsgContext.get("template_id"));// 模版id
		wxMssVo.setForm_id(formid);// formid
		wxMssVo.setPage(sendMsgContext.get("message_url"));
		wxMssVo.setDataInfo(sendMsgContext);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, wxMssVo, String.class);
		// log.error("小程序推送结果={}", responseEntity.getBody());
		return responseEntity.getBody();
	}
}
