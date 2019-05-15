package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopClassMapperSelf {
	/**
	 * 删除商家类目
	 * 
	 * @param map
	 * @return
	 */
	public int deleteShopClassByShopClassId(Map<String, List<String>> map);

	/**
	 * 获取商家分类列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getShopClassList(Map<String, String> condition);
}
