package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LableInfoMapperSelf {

	/**
	 * 更新商家标签点赞数量
	 * 
	 * @param shop_lable_id
	 * @return
	 */
	public int updateShopLablePraiseNum(Map<String, String> shop_lable_id_map);

	/**
	 * 删除标签信息
	 * 
	 * @param list_map
	 * @return
	 */
	public int deleteLableInfoByLableId(Map<String, List<String>> list_map);

	/**
	 * 获取标签列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getLableList(Map<String, String> condition);
}
