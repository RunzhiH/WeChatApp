package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

import com.jisu.WeChatApp.entity.ServerMemberVO;
import com.jisu.WeChatApp.pojo.ServerClass;
import com.jisu.WeChatApp.pojo.ShopServer;

public interface ServerService {
	public List<ServerClass> getServerClassList();

	public void updateServerClass(ServerClass permission);

	public void addServerClass(ServerClass permission);

	public ServerClass getServerClass(String id);

	public String delServerClass(String id);

	public List<ServerClass> getLevel1ServerClassList();

	public List<ServerClass> getNextLevelServerClassList(String server_class_id);

	public String delServer(String id);

	public List<Map<String, String>> getAllServerList();

	public void updateServer(ShopServer shopServer);

	public void addServer(ShopServer shopServer);

	public ShopServer getServer(String id);

	public List<Map<String, String>> getServerListByClassId(String server_class_id);

	public List<Map<String, String>> getFreeServerMemberList(String shop_server_id, String address_x, String address_y);

	public List<Map<String, Object>> getAppointmentServerTimeList(String member_no);

	public List<Map<String, String>> getAllServerMemberList();

	public ServerMemberVO getServerMemberByMemberNo(String member_no);

	public void updateServerMember(ServerMemberVO serverMemebr);

	public void addServerMember(ServerMemberVO serverMemebr);
	
	public String delServerMember(String member_no);
}
