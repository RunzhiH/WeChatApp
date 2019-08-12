package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ShopInfoMapperSelf {
	/**
	 * 根据条件获取商家列表
	 * 
	 * @param condetion
	 * @return
	 */
	public List<Map<String, String>> getShopInfoListByCondition(Map<String, String> condition);

	/**
	 * 删除多个商家
	 * 
	 * @param shop_id_list
	 * @return
	 */
	public int deleteShopByShopId(List<String> shop_id_list);

	/**
	 * 保存商家标签
	 * 
	 * @param insert_map
	 * @return
	 */
	public int insertShopLable(Map<String, Object> insert_map);

	/**
	 * 保存商家服务
	 * 
	 * @param shop_server
	 * @return
	 */
	public int insertShopServer(Map<String, Object> shop_server_map);

	/**
	 * 根据shop_id 获取商家标签
	 * 
	 * @param shop_id_map
	 * @return
	 */
	public List<Map<String, String>> getShopLableByShopId(Map<String, String> shop_id_map);

	/**
	 * 根据shop_id获取商家服务
	 * 
	 * @param shop_id_map
	 * @return
	 */
	public List<Map<String, String>> getShopServerByshopId(Map<String, String> shop_id_map);

	/**
	 * 获取带区域排名的商家信息
	 * 
	 * @param map
	 * @return
	 */
	public Map<String, String> getShopInRankByShopId(Map<String, String> map);

	/**
	 * 更新商家点赞数
	 * 
	 * @param shop_id
	 * @return
	 */
	public int updatShopPraiseNum(Map<String, String> shop_id);

	/**
	 * 获取最近去过的店
	 * 
	 * @param member_map
	 * @return
	 */
	public List<Map<String, String>> getMyLatelyShopList(Map<String, String> member_map);

	/**
	 * 获取推荐商家列表
	 * 
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getRecommendShopList(Map<String, String> map);
	
	public List<Map<String, String>> getShopPraiseHistoryByMemberAndShopId(Map<String, String> condition);

	public Map<String, String> getshopInfoByMemberNo(Map<String, String> map);

	public Map<String, String> getShopInfoByShopIdAndClassId(Map<String, String> map);
	
	public List<Map<String, String>> getShopHasServerList(Map<String, String> map);

	public List<Map<String, String>> getFreeServerShopList(Map<String, String> map);

	public List<Map<String, String>> getShopListByOperatorMemberNo(String member_no);

}
