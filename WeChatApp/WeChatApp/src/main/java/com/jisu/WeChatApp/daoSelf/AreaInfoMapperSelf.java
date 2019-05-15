package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AreaInfoMapperSelf {
	/**
	 * 获取省列表
	 * 
	 * @return
	 */
	public List<Map<String, String>> getSheng();

	/**
	 * 获取下级地区
	 * 
	 * @param area_code
	 * @return
	 */
	public List<Map<String, String>> getNextAreaInfo(String area_code);
}
