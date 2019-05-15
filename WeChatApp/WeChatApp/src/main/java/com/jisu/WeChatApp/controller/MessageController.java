package com.jisu.WeChatApp.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.FormInfoMapper;
import com.jisu.WeChatApp.pojo.FormInfo;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/message")
@RestController
public class MessageController {
	@Autowired
	private FormInfoMapper formInfoMapper;

	/**
	 * 保存formid
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("saveMessageFormId")
	public MsgModel saveMessageFormId(HttpServletRequest request, HttpServletResponse response) {
		String openid = request.getParameter("openid");
		String form_id = request.getParameter("form_id");
		String form_info_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);

		FormInfo formInfo = new FormInfo();
		formInfo.setCreateTime(new Date());
		formInfo.setFormId(form_id);
		formInfo.setOpenId(openid);
		formInfo.setFormInfoId(form_info_id);
		int num = formInfoMapper.insertSelective(formInfo);
		MsgModel msg = new MsgModel();
		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}
}
