package com.jisu.WeChatApp.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.RotationMapMapper;
import com.jisu.WeChatApp.pojo.RotationMap;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/rotation")
@RestController
public class RotationController {
	@Autowired
	private RotationMapMapper rotationMapMapper;

	@RequestMapping("getRotationList")
	public MsgModel getRotationList(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		List<RotationMap> rotation_list = rotationMapMapper.findAll();
		msg.setContext(rotation_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
