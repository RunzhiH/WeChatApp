package com.jisu.WeChatApp.controller.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.pojo.ExperienceInfo;
import com.jisu.WeChatApp.pojo.ServerClass;
import com.jisu.WeChatApp.pojo.ShopServer;
import com.jisu.WeChatApp.service.impl.ServerServiceImpl;

import net.sf.json.JSONArray;

@RequestMapping("/web/server")
@Controller()
public class ServerWebController {
	private static final Logger logger = LoggerFactory.getLogger(ServerWebController.class);

	@Autowired
	private ServerServiceImpl serverServiceImpl;

	/**
	 * 服务类目列表
	 * 
	 * @return ok/fail
	 */
	@RequestMapping(value = "/serverClassList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView serverClassList() {
		logger.debug("服务类目列表！");
		ModelAndView mav = new ModelAndView("server/serverClassList");
		try {
			List<ServerClass> serverClassList = serverServiceImpl.getServerClassList();
			logger.debug("服务类目列表查询=serverClassList:" + serverClassList);
			mav.addObject("serverClassList", serverClassList);
			mav.addObject("msg", "ok");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("服务类目查询异常！", e);
		}
		return mav;
	}

	/**
	 * 添加服务类目
	 * 
	 * @param type              [0：编辑；1：新增子节点服务类目]
	 * @param serverClassission
	 * @return ModelAndView ok/fail
	 */
	@RequestMapping(value = "/setServerClass", method = RequestMethod.POST)
	@ResponseBody
	public String setPerm(@RequestParam("type") int type, ServerClass permission) {
		logger.debug("设置服务类目--区分type-" + type + "【0：编辑；1：新增子节点服务类目】，服务类目--permission-" + permission);
		try {
			if (null != permission) {
				if (0 == type) {
					// 编辑服务类目
					serverServiceImpl.updateServerClass(permission);
				} else if (1 == type) {
					// 增加子节点服务类目
					serverServiceImpl.addServerClass(permission);
				}
				logger.debug("设置服务类目成功！-permission-" + permission);
				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("设置服务类目异常！", e);
		}
		return "设置服务类目出错，请您稍后再试";
	}

	/**
	 * 获取服务类目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getServerClass", method = RequestMethod.GET)
	@ResponseBody
	public ServerClass getServerClass(@RequestParam("server_class_id") String id) {
		logger.debug("获取服务类目--id-" + id);
		try {
			if (StringUtils.isNotBlank(id)) {
				ServerClass serverClass = serverServiceImpl.getServerClass(id);
				logger.debug("获取服务类目成功！-permission-" + serverClass);
				return serverClass;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("获取服务类目异常！", e);
		}
		return null;
	}

	/**
	 * 删除服务类目
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delServerClass", method = RequestMethod.POST)
	@ResponseBody
	public String del(@RequestParam("server_class_id") String id) {
		logger.debug("删除服务类目--id-" + id);
		try {
			if (StringUtils.isNotBlank(id)) {
				return serverServiceImpl.delServerClass(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除服务类目异常！", e);
		}
		return "删除服务类目出错，请您稍后再试";
	}

	@RequestMapping("serverList")
	public ModelAndView toPage() {
		return new ModelAndView("server/serverList");
	}

	@RequestMapping("getAllServerList")
	@ResponseBody
	public JSONArray getAllServerList() {
		return JSONArray.fromObject(serverServiceImpl.getAllServerList());
	}

	@RequestMapping(value = "setServer", method = RequestMethod.POST)
	@ResponseBody
	public String setServer(@RequestParam("type") int type, ShopServer shopServer) {
		try {
			if (null != shopServer) {
				if (0 == type) {
					serverServiceImpl.updateServer(shopServer);
				} else {
					serverServiceImpl.addServer(shopServer);
				}
				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "设置服务类目出错，请您稍后再试";
	}

	@RequestMapping(value = "delServer", method = RequestMethod.POST)
	@ResponseBody
	public String delServer(@RequestParam("shop_server_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				return serverServiceImpl.delServer(id);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "删除服务类目出错，请您稍后再试";
	}

	@RequestMapping("getServer")
	@ResponseBody
	public ShopServer getServer(@RequestParam("shop_server_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				ShopServer serverClass = serverServiceImpl.getServer(id);
				return serverClass;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping("getServerMember")
	@ResponseBody
	public JSONArray getServerMember(@RequestParam("order_id") String id) {
		
		List<Map<String, String>> server_list= serverServiceImpl.getServerMember(id);
		if(server_list.size()>0) {
			return JSONArray.fromObject(server_list);
		}else {
			return null;
		}
		
	}
}
