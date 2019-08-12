package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.PageDataResult;
import com.jisu.WeChatApp.pojo.UserSearchDTO;

public interface MemberInfoService {

	public List<Map<String, String>> getMemberListByCondition(Map<String, String> condition);

	public MemberInfo getMemberInfoByMemberNo(String member_no);

	/**
	 * 获取会员收益列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getMemberIncomeByMemberNo(Map<String, String> condition);

	/**
	 * 获取会员总收益
	 * 
	 * @param member_no_map
	 * @return
	 */
	public Map<String, String> getMemberTotalIncome(String member_no);

	/**
	 * 获取慧眼默认地址
	 * 
	 * @param member_no
	 * @return
	 */
	public Map<String, String> getMemebrDefaultAddress(String member_no);
	
	public MemberInfo getMemberInfoByUsername(String username);
	
	public Map<String, Object> getServerMemberInfoAndHasServer(String member_no);
	
	public Map<String, Object> getMemebrListByShopId(String shop_id);
	
	public void autoCloseMemberProhi();
	
	public void memberProhibitOrder(String member_no);

	String updateIsShare(String is_share, String member_no);

	PageDataResult getAllOperatorMemberList(UserSearchDTO userSearch, Integer page, Integer limit);
	
}
