package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jisu.WeChatApp.entity.ServerMemberVO;

@Mapper
public interface ServerMapperSelf {
	public List<Map<String, String>> getAllServerList();

	public List<Map<String, String>> getServerMemberByOrderId(String order_id);

	public List<Map<String, String>> getServerListByClassId(Map<String, String> id_map);

	public List<Map<String, String>> getFreeServerMemberList(Map<String, String> map);

	public List<Map<String, String>> getProhiMemberList();

	public List<Map<String, String>> getAppointmentServerTimeList(Map<String, String> map);

	public List<Map<String, String>> getServerMemberList();
	
	public ServerMemberVO getServerMember(String member_no);

	public int deleteServerMember(String member_no);

	public List<Map<String, String>> getFreeServerMemberListForJuli(Map<String, String> map);

	public List<Map<String, String>> getFreeShopList(Map<String, String> map);

	public int insertRestTimeList(Map<String, Object> map);

	public List<Map<String, String>> getFreeServerList(Map<String, String> map);

	public List<Map<String, String>> getServerRestTimeList(Map<String, String> map);

	public List<Map<String, String>> getRestTimeList(String member_no);

	public int deleteRestTimeByMemberNo(String member_no);

	public List<Map<String, String>> getServerMemberListRank(Map<String, String> condition);

}
