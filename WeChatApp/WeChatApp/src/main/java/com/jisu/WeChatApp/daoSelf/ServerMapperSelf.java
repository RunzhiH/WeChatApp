package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ServerMapperSelf {
	public List<Map<String, String>> getAllServerList();

	public List<Map<String, String>> getServerMemberByOrderId(String order_id);
	
	public List<Map<String, String>> getServerListByClassId(Map<String, String> id_map);
	
	public List<Map<String, String>> getFreeServerMemberList(Map<String, String> map);
}
