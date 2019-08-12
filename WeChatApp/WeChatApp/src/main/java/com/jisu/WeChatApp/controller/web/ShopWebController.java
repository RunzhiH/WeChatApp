package com.jisu.WeChatApp.controller.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.daoSelf.OrderInfoMapperSelf;
import com.jisu.WeChatApp.pojo.ShopClass;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.service.impl.ShopInfoServiceImpl;

@Controller
@RequestMapping("/web/shop")
public class ShopWebController {
	@Autowired
	private ShopInfoServiceImpl shopInfoServiceImpl;
	@Autowired
	private ShopInfoMapper shopInfoMapper;
	@Autowired
	private OrderInfoMapperSelf orderInfoMapperSelf;

	@RequestMapping("/shopManage")
	public ModelAndView toListPage() {
		ModelAndView mav = new ModelAndView("shop/shopManage");

		return mav;
	}

	@RequestMapping("getShopList")
	@ResponseBody
	public List<ShopInfo> getShopList(HttpServletRequest request) {
		List<ShopInfo> shopList = null;
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("shop_status", request.getParameter("shop_status"));
		try {
			shopList = shopInfoServiceImpl.getAllShopList(condition);
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

	@RequestMapping(value = "delShop", method = RequestMethod.POST)
	@ResponseBody
	public String delExper(@RequestParam("shop_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				return shopInfoServiceImpl.delShop(id);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "删除服务类目出错，请您稍后再试";
	}

	@RequestMapping(value = "agreeShop", method = RequestMethod.POST)
	@ResponseBody
	public String agreeShop(@RequestParam("shop_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				Map<String, String> order_info = orderInfoMapperSelf.getOrderInfoByPayShop(id);
				if (MapUtils.isNotEmpty(order_info)) {
					String order_staus = String.valueOf(order_info.get("order_status"));
					if ("0".equals(order_staus) || "7".equals(order_staus)) {
						return "未支付诚意金";
					} else {
						return shopInfoServiceImpl.agreeShop(id);
					}

				} else {
					return "未支付诚意金";
				}

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

	@RequestMapping("shopClassList")
	public ModelAndView shopClassList() {
		return new ModelAndView("shop/shopClassList");
	}

	@RequestMapping("getShopClassList")
	@ResponseBody
	public List<ShopClass> getShopClassList() {
		List<ShopClass> shopClassList = null;
		try {
			shopClassList = shopInfoServiceImpl.getAllShopClassList();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return shopClassList;
	}

	@RequestMapping(value = "setShopClass", method = RequestMethod.POST)
	@ResponseBody
	public String setShopClass(@RequestParam("type") int type, ShopClass shopClass) {
		try {
			if (shopClass != null) {
				if (0 == type) {
					shopInfoServiceImpl.updateShopClass(shopClass);
				} else {
					shopInfoServiceImpl.addShopClass(shopClass);
				}
				return "ok";
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "出错了，请您稍后再试";
	}

	@RequestMapping(value = "delShopClass", method = RequestMethod.POST)
	@ResponseBody
	public String delShopClass(@RequestParam("shop_class_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				return shopInfoServiceImpl.delShopClass(id);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("getShopClass")
	@ResponseBody
	public ShopClass getShopClass(@RequestParam("shop_class_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				ShopClass shopClass = shopInfoServiceImpl.getShopClass(id);
				return shopClass;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
