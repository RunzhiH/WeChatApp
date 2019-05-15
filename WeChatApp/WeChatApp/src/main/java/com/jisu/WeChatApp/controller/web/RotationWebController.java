package com.jisu.WeChatApp.controller.web;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.dao.RotationMapMapper;
import com.jisu.WeChatApp.pojo.RotationMap;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.service.impl.RotationServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

@Controller
@RequestMapping("/web/rotation")
public class RotationWebController {

	@Autowired
	private RotationMapMapper rotationMapMapper;

	@Autowired
	private RotationServiceImpl rotationServiceImpl;
	
	@RequestMapping("getRotationList")
	@ResponseBody
	public List<RotationMap> getList() {
		List<RotationMap> rotationList = null;
		try {
			 rotationList = rotationMapMapper.findAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotationList;
	}
	
	@RequestMapping("/rotationList")
	@ResponseBody
	public ModelAndView getRotationList() {
		ModelAndView mav = new ModelAndView("rotation/rotationList");
		try {
			List<RotationMap> rotationList = rotationMapMapper.findAll();
			mav.addObject("rotationList", rotationList);
			mav.addObject("msg", "ok");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}

	@RequestMapping(value = "/setRotation", method = RequestMethod.POST)
	@ResponseBody
	public String setRotation(@RequestParam("type") int type, RotationMap rotationMap) {
		try {
			if (null != rotationMap) {
				if (0 == type) {
					// 编辑权限
					rotationServiceImpl.updateRotation(rotationMap);
				} else if (1 == type) {
					// 增加子节点权限
					rotationMap.setCreateTime(new Date());
					rotationServiceImpl.addRotation(rotationMap);
				}
				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "轮播图设置错误,请你稍后再试";
	}

	/**
	 * 获取权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getRotation", method = RequestMethod.GET)
	@ResponseBody
	public RotationMap getPerm(@RequestParam("rotation_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				RotationMap perm = rotationMapMapper.selectByPrimaryKey(id);
				return perm;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 删除权限
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	@ResponseBody
	public String del(@RequestParam("rotation_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				return rotationServiceImpl.delRotation(id);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "删除权限出错，请您稍后再试";
	}

}
