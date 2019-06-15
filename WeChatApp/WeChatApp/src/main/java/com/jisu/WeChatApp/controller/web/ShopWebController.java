package com.jisu.WeChatApp.controller.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.pojo.Permission;
import com.jisu.WeChatApp.pojo.Role;
import com.jisu.WeChatApp.pojo.RolePermission;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.service.impl.ShopInfoServiceImpl;

@Controller
@RequestMapping("/web/shop")
public class ShopWebController {
	@Autowired
	private ShopInfoServiceImpl shopInfoServiceImpl;
	@Autowired
	private ShopInfoMapper shopInfoMapper;

	@RequestMapping("/shopManage")
	public ModelAndView toListPage() {
		ModelAndView mav = new ModelAndView("shop/shopManage");
		
		return mav;
	}

	@RequestMapping("getShopList")
	@ResponseBody
	public List<ShopInfo> getShopList() {
		List<ShopInfo> shopList = null;
		try {
			shopList = shopInfoServiceImpl.getAllShopList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return shopList;
	}
	
	/**
	 * 根据id查询角色
	 * 
	 * @return PermTreeDTO
	 */
	@RequestMapping(value = "/updateShop/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView updateShop(@PathVariable("id") String id) {
		ModelAndView mv = new ModelAndView("shop/shopManage");
		try {
			if (null == id) {
				mv.addObject("msg", "请求参数有误，请您稍后再试");
				return mv;
			}
			mv.addObject("flag", "updateShop");
			ShopInfo shopInfo = shopInfoMapper.selectByPrimaryKey(id);
			// 角色详情
			mv.addObject("shopInfo", shopInfo);
		} catch (Exception e) {
			e.printStackTrace();
			mv.addObject("msg", "请求异常，请您稍后再试");
		}
		return mv;
	}
	
	@RequestMapping(value = "/setShop", method = RequestMethod.POST)
	@ResponseBody
	public String setShop(ShopInfo shopInfo) {
		try {
			if (null == shopInfo) {
				return "请您填写完整的店铺信息";
			}
			return shopInfoServiceImpl.updateShopInfo(shopInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "操作错误，请您稍后再试";
	}
}
