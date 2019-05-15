package com.jisu.WeChatApp.daoSelf;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletChangeRecordMapperSelf {
	/**
	 * 插入服务商家的余额变动记录
	 * 
	 * @return
	 */
	public int insertWalletChangeRecordForServerShop(Map<String, String> order_id_map);

	/**
	 * 插入分享商家的余额变动记录
	 * 
	 * @return
	 */
	public int insertWalletChangeRecordForShareShop(Map<String, String> order_id_map);

	/**
	 * 插入服务人员的余额变动记录
	 * 
	 * @return
	 */
	public int insertWalletChangeRecordForServerMember(Map<String, String> order_id_map);

	/**
	 * 插入首推店家的余额百年东记录
	 * 
	 * @param order_id_map
	 * @return
	 */
	public int insertWalletChangeRecordForFirstShareShop(Map<String, String> order_id_map);
}
