package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

public interface AreaInfoService {
	/**
	 * 获取省列表
	 * 
	 * @return
	 */
	public List<Map<String, String>> getSheng();

	/**
	 * 获取下级区域
	 * 
	 * @param area_code
	 * @return
	 */
	public List<Map<String, String>> getNextAreaInfo(String area_code);
}
