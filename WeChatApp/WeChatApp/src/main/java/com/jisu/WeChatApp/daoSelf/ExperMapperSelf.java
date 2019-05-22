package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExperMapperSelf {
	/**
	 * 根据条件获取用户体验列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getExperList(Map<String, String> condition);

	/**
	 * 修改点赞数
	 * 
	 * @param do_like_map
	 * @return
	 */
	public int updateExperPraiseNum(Map<String, String> do_like_map);
	
	public List<Map<String, String>> getAllExperList();

	public int deleteExperLikeHistroy(Map<String, String> map);
}
