package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

import com.jisu.WeChatApp.pojo.ShopLablePraiseHistroy;

public interface LableInfoService {
	/**
	 * 获取商家标签点赞信息
	 * 
	 * @param shop_lable_id
	 * @param memebr_no
	 * @return
	 */
	public List<ShopLablePraiseHistroy> getShoplabLablePraiseHistroys(String shop_lable_id, String memebr_no);

	/**
	 * 插入商家标签点赞历史
	 * 
	 * @param shop_lable_id
	 * @param member_no
	 * @return
	 */
	public int insertShopLablePraiseHistroy(String shop_lable_id, String member_no);

	/**
	 * 删除商家标签点赞历史
	 * 
	 * @param shop_lable_id
	 * @param member_no
	 * @return
	 */
	public int deleteShopLablePraiseHistroy(String shop_lable_id, String member_no);

	/**
	 * 更新商家标签点赞数量
	 * 
	 * @param shop_lable_id
	 * @return
	 */
	public int updateShopLablePraiseNum(String shop_lable_id);

	/**
	 * 删除标签
	 * 
	 * @param lable_id_str
	 * @return
	 */
	public int deletelableInfoByLableId(String lable_id_str);

	/**
	 * 获取标签列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getLableList(Map<String, String> condition);
}
