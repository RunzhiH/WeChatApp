package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.jisu.WeChatApp.pojo.MemberInfo;

@Mapper
public interface MemberInfoMapperSelf {
	/**
	 * 添加新的会员
	 * 
	 * @param memberInfo
	 * @return
	 */
	public int insertNewMemberInfo(MemberInfo memberInfo);

	/**
	 * 获取会员列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getMemberListByCondition(Map<String, String> condition);

	/**
	 * 获取会员默认地址
	 * 
	 * @param member_no_map
	 * @return
	 */
	public Map<String, String> getMemberDefaultAddress(Map<String, String> member_no_map);
	
	public List<Map<String, String>> getServerMemberHasServer(Map<String, String> member_no_map);
	
	public Map<String, String> getServerMemebrInfo(Map<String, String> member_no_map);

	public List<Map<String, String>> getMemebrListByShopId(Map<String, String> map);

	public Map<String,String> getMemberCountByShopId(Map<String, String> map);
}
