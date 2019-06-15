package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.pojo.ShopPraiseHistory;

public interface ShopInfoService {
	/**
	 * 根据条件获取商家列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getShopListByCondition(Map<String, String> condition);

	/**
	 * 删除商家 多个商家id用英文逗号分隔
	 * 
	 * @param shop_id_str
	 * @return
	 */
	public int deleteShopByShopId(String shop_id_str);

	/**
	 * 保存商家标签信息
	 * 
	 * @param shop_lable_str
	 * @return
	 */
	public int saveShopLableInfo(String shop_lable_str, String shop_id);

	/**
	 * 保存商家服务信息
	 * 
	 * @param shop_server_str
	 * @return
	 */
	public int saveShopServerInfo(String shop_server_str, String shop_id);

	/**
	 * 根据shop_id获取商家标签
	 * 
	 * @param shop_id
	 * @return
	 */
	public List<Map<String, String>> getShopLableByShopId(String shop_id);

	/**
	 * 根据shop_id获取商家服务
	 * 
	 * @param shop_id
	 * @return
	 */
	public List<Map<String, String>> getShopServerByShopId(String shop_id);

	/**
	 * 获取商家区域排名
	 * 
	 * @param shop_id
	 * @param shi_code
	 * @return
	 */
	public Map<String, String> getShopInfoRank(String shop_id, String shi_code,String member_no);

	/**
	 * 根据会员号获取商家点赞历史纪录
	 * 
	 * @param member_no
	 * @return
	 */
	public List<Map<String, String>> getShopPraiseHistoriesByMember(String shop_id, String member_no);

	/**
	 * 插入点赞历史
	 * 
	 * @param shop_id
	 * @param member_no
	 * @return
	 */
	public int insertShopPraiseHistory(String shop_id, String member_no);

	/**
	 * 会员点赞商家
	 * 
	 * @param shop_id
	 * @return
	 */
	public int doMemberLikeShop(String shop_id, String type);

	/**
	 * 删除商家点赞历史
	 * 
	 * @param shop_id
	 * @param member_no
	 * @return
	 */
	public int deleteShopPraiseHistory(String shop_id, String member_no);

	/**
	 * 获取最近去过的商家
	 * 
	 * @param member_no
	 * @return
	 */
	public List<Map<String, String>> getMyLatelyShopList(String member_no);

	
	public List<Map<String, String>> getRecommendShopList(String shop_server_id);
	
	public List<ShopInfo> getAllShopList();
	
	public Map<String, Object> getshopInfoByMemberNo(String member_no);
	
	public Map<String, String> getShopInfoByShopIdAndClassId(String shop_id, String server_class_id);
	
	public List<Map<String, String>> getFreeServerShopList(String server_class_id);
	
	public void shopProhibitOrder(String shop_id);
	
	public String updateShopInfo(ShopInfo shopInfo);
}
