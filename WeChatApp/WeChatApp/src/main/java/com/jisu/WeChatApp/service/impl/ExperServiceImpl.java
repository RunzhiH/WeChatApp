package com.jisu.WeChatApp.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.ExperPraiseHistroyMapper;
import com.jisu.WeChatApp.dao.ExperienceInfoMapper;
import com.jisu.WeChatApp.daoSelf.ExperMapperSelf;
import com.jisu.WeChatApp.pojo.ExperPraiseHistroy;
import com.jisu.WeChatApp.pojo.ExperPraiseHistroyExample;
import com.jisu.WeChatApp.pojo.ExperienceInfo;
import com.jisu.WeChatApp.service.ExperService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

@Service("ExperServiceImpl")
public class ExperServiceImpl implements ExperService {
	@Autowired
	private ExperMapperSelf experMapperSelf;
	@Autowired
	private ExperPraiseHistroyMapper experPraiseHistroyMapper;
	@Autowired
	private ExperienceInfoMapper experienceInfoMapper;

	@Override
	public List<Map<String, String>> getExperList(Map<String, String> condition) {
		// TODO Auto-generated method stub
		return experMapperSelf.getExperList(condition);
	}

	@Override
	public List<ExperPraiseHistroy> getExperPraiseHistroy(String exper_id, String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("exper_id", exper_id);
		condition.put("member", member_no);
		return experPraiseHistroyMapper.getExperPraiseHistroyList(condition);
	}

	@Override
	public int doExperLike(String exper_id, String is_like) {
		// TODO Auto-generated method stub
		Map<String, String> do_like_map = new HashMap<String, String>();
		do_like_map.put("exper_id", exper_id);
		do_like_map.put("is_like", is_like);
		return experMapperSelf.updateExperPraiseNum(do_like_map);
	}

	@Override
	public int insertExperLikeHistroy(String exper_id, String member_no) {
		// TODO Auto-generated method stub
		ExperPraiseHistroy histroy = new ExperPraiseHistroy();
		histroy.setCreateTime(new Date());
		histroy.setExperienceId(exper_id);
		histroy.setExperPraiseHistroyId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		histroy.setMemberNo(member_no);
		return experPraiseHistroyMapper.insertSelective(histroy);
	}

	@Override
	public int deleteExperLikeHistroy(String exper_id, String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_no", member_no);
		map.put("exper_id", exper_id);
		return experMapperSelf.deleteExperLikeHistroy(map);
	}

	@Override
	public List<Map<String, String>> getAllExperList() {
		// TODO Auto-generated method stub
		return experMapperSelf.getAllExperList();
	}

	@Override
	public String delExper(String exper_id) {
		// TODO Auto-generated method stub
		if (experienceInfoMapper.deleteByPrimaryKey(exper_id) > 0) {
			return "ok";
		} else {
			return "删除失败，请您稍后再试";
		}
	}

	@Override
	public int addExper(ExperienceInfo experienceInfo) {
		// TODO Auto-generated method stub
		experienceInfo.setCreateTime(new Date());
		experienceInfo.setExperienceId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		return experienceInfoMapper.insertSelective(experienceInfo);
	}

	@Override
	public int updateExper(ExperienceInfo experienceInfo) {
		// TODO Auto-generated method stub
		return experienceInfoMapper.updateByPrimaryKeySelective(experienceInfo);
	}

	@Override
	public ExperienceInfo getExper(String exper_id) {
		// TODO Auto-generated method stub
		return experienceInfoMapper.selectByPrimaryKey(exper_id);
	}

	@Override
	public Map<String, Object> getExperInfo(String exper_id) {
		// TODO Auto-generated method stub
		Map<String, String> exper_info= experMapperSelf.getExperInfo(exper_id);
		String server_before_photo=exper_info.get("server_before_photo");
		String server_after_photo= exper_info.get("server_after_photo");
		List<String> photo_list=new  ArrayList<String>();
		if(StringUtils.isNotBlank(server_before_photo)) {
			photo_list.add(server_before_photo);
		}
		String[] server_after_photo_arr= null;
		if(StringUtils.isNotBlank(server_after_photo)) {
			server_after_photo_arr=server_after_photo.split(",");
			for (int i = 0; i < server_after_photo_arr.length; i++) {
				photo_list.add(server_after_photo_arr[i]);
			}
		}
		//photo_list.add(experience_photo);
		Map<String, Object> result_map=new HashMap<String, Object>();
		result_map.putAll(exper_info);
		result_map.put("photo_list", photo_list);
		return result_map;
	}

}
