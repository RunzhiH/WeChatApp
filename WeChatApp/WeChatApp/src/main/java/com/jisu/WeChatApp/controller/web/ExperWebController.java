package com.jisu.WeChatApp.controller.web;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jisu.WeChatApp.pojo.ExperienceInfo;
import com.jisu.WeChatApp.service.impl.ExperServiceImpl;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/web/exper")
public class ExperWebController {
	@Autowired
	private ExperServiceImpl experServiceImpl;

	@RequestMapping("experList")
	public ModelAndView toPage() {
		return new ModelAndView("exper/experList");
	}

	@RequestMapping("getAllExperList")
	@ResponseBody
	public JSONArray getAllExperList() {
		return JSONArray.fromObject(experServiceImpl.getAllExperList());
	}

	@RequestMapping(value = "setExper", method = RequestMethod.POST)
	@ResponseBody
	public String setExper(@RequestParam("type") int type, ExperienceInfo experienceInfo) {
		try {
			if (null != experienceInfo) {
				if (0 == type) {
					experServiceImpl.updateExper(experienceInfo);
				} else {
					experServiceImpl.addExper(experienceInfo);
				}
				return "ok";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "设置服务类目出错，请您稍后再试";
	}

	@RequestMapping(value = "delExper", method = RequestMethod.POST)
	@ResponseBody
	public String delExper(@RequestParam("epxer_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				return experServiceImpl.delExper(id);
			}
		} catch (Exception e) {
			e.printStackTrace();

		}
		return "删除服务类目出错，请您稍后再试";
	}

	@RequestMapping("getExper")
	@ResponseBody
	public ExperienceInfo getExper(@RequestParam("epxer_id") String id) {
		try {
			if (StringUtils.isNotBlank(id)) {
				ExperienceInfo serverClass = experServiceImpl.getExper(id);
				return serverClass;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
