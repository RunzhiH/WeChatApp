package com.jisu.WeChatApp.service;

import com.jisu.WeChatApp.pojo.MemberInfo;

public interface UserInfoService {
	/**
	 * 根据openid获取会员信息
	 * 
	 * @param open_id
	 * @return
	 */
	public MemberInfo getMemberInfoByOpenId(String open_id);

	/**
	 * 修改并保存会员信息
	 * 
	 * @param memberInfo
	 * @return
	 */
	public MemberInfo editMember(MemberInfo memberInfo);
}
