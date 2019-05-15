package com.jisu.WeChatApp.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.daoSelf.MemberInfoMapperSelf;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.MemberInfoExample;
import com.jisu.WeChatApp.service.UserInfoService;

@Service("UserInfoServiceImpl")
public class UserInfoServiceImpl implements UserInfoService {
	@Autowired
	private MemberInfoMapper memberInfoMapper;
	@Autowired
	private MemberInfoMapperSelf memberInfoMapperSelf;

	@Override
	public MemberInfo getMemberInfoByOpenId(String open_id) {
		// TODO Auto-generated method stub
		MemberInfoExample memberE = new MemberInfoExample();
		memberE.createCriteria().andOpenidEqualTo(open_id);
		List<MemberInfo> member_list = memberInfoMapper.selectByExample(memberE);
		if (member_list.size() > 0) {
			return member_list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public MemberInfo editMember(MemberInfo memberInfo) {
		// TODO Auto-generated method stub
		String member_no = memberInfo.getMemberNo();
		MemberInfoExample memberE = new MemberInfoExample();
		if (StringUtils.isNotBlank(member_no)) {
			memberInfoMapper.updateByPrimaryKeySelective(memberInfo);
			memberE.createCriteria().andMemberNoEqualTo(member_no);
		} else {
			memberInfoMapperSelf.insertNewMemberInfo(memberInfo);
			memberE.createCriteria().andOpenidEqualTo(memberInfo.getOpenid());
		}
		
		List<MemberInfo> member_list = memberInfoMapper.selectByExample(memberE);
		if (member_list.size() > 0) {
			return member_list.get(0);
		} else {
			return null;
		}
	}

}
