package com.jisu.WeChatApp.controller;

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

import com.jisu.WeChatApp.dao.ShopClassMapper;
import com.jisu.WeChatApp.pojo.ShopClass;
import com.jisu.WeChatApp.service.impl.ShopClassServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/shopClass")
@RestController
public class ShopClassController {
	@Autowired
	private ShopClassMapper shopClassMapper;
	@Autowired
	private ShopClassServiceImpl shopClassServiceImpl;

	/**
	 * 保存或修改商家类目
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveShopClass", method = RequestMethod.POST)
	public MsgModel saveShopClass(HttpServletRequest request, HttpServletResponse response) {
		String shop_class_id = request.getParameter("shop_class_id");
		String shop_class_name = request.getParameter("shop_class_name");
		String shop_class_desc = request.getParameter("shop_class_desc");
		String shop_class_photo = request.getParameter("shop_class_photo");

		ShopClass shopClass = new ShopClass();
		shopClass.setShopClassName(shop_class_name);
		shopClass.setShopClassDesc(shop_class_desc);
		shopClass.setShopClassPhoto(shop_class_photo);
		int save_num = 0;
		if (StringUtils.isNotBlank(shop_class_id)) {
			shopClass.setShopClassId(shop_class_id);
			save_num = shopClassMapper.updateByPrimaryKeySelective(shopClass);
		} else {
			shopClass.setShopClassId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
			save_num = shopClassMapper.insertSelective(shopClass);
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
	 * 删除商家类目
	 * 
	 * @return
	 */
	@RequestMapping(value = "deleteShopClassByShopClassId", method = RequestMethod.POST)
	public MsgModel deleteShopClassByShopClassId(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String shop_class_id_str = request.getParameter("shop_class_id_str");
		int delete_num = shopClassServiceImpl.deleteShopClassInfoByShopClassId(shop_class_id_str);
		if (delete_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 获取商家类目列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getShopClassList")
	public MsgModel getShopClassList(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		Map<String, String> condition = new HashMap<String, String>();
		List<Map<String, String>> shop_class_list = shopClassServiceImpl.getShopClassList(condition);
		msg.setContext(shop_class_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
