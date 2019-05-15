package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

public interface ShopClassService {
	/**
	 * 删除商家类目
	 * 
	 * @param Shop_class_id_str
	 * @return
	 */
	public int deleteShopClassInfoByShopClassId(String Shop_class_id_str);

	/**
	 * 获取商家类目列表
	 * 
	 * @return
	 */
	public List<Map<String, String>> getShopClassList(Map<String, String> condition);
}
