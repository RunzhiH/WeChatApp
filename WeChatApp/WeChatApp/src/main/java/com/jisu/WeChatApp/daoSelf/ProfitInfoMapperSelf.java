package com.jisu.WeChatApp.daoSelf;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProfitInfoMapperSelf {
	
	public int insertProfitForServerShop(String order_id);
	
	public int insertProfitForShareShop(String order_id);
	
	public int insertProfitForServerMember(String order_id);
	
	public int insertProftForOnlyShareShop(String order_id);
	
	public int insertProfitForBusinessMemebr(String order_id);
	
	public int updateStatusByOrderId(String order_id);
}
