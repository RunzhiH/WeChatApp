package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

import com.jisu.WeChatApp.pojo.ExperPraiseHistroy;
import com.jisu.WeChatApp.pojo.ExperienceInfo;

public interface ExperService {
	/**
	 * 根据条件获取用户体验列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getExperList(Map<String, String> condition);

	/**
	 * 获取用户点赞历史
	 * 
	 * @param exper_id
	 * @param member_no
	 * @return
	 */
	public List<ExperPraiseHistroy> getExperPraiseHistroy(String exper_id, String member_no);

	/**
	 * 点赞体验
	 * 
	 * @param exper_id
	 * @param member_no
	 * @return
	 */
	public int doExperLike(String exper_id, String is_like);

	/**
	 * 插入点赞历史
	 * 
	 * @param exper_id
	 * @param member_no
	 * @return
	 */
	public int insertExperLikeHistroy(String exper_id, String member_no);

	/**
	 * 删除点赞历史
	 * 
	 * @param exper_id
	 * @param member_no
	 * @return
	 */
	public int deleteExperLikeHistroy(String exper_id, String member_no);
	
	public List<Map<String, String>> getAllExperList();
	
	public String delExper(String exper_id);
	
	public int addExper(ExperienceInfo experienceInfo);
	
	public int updateExper(ExperienceInfo experienceInfo);
	
	public ExperienceInfo getExper(String exper_id);
}
