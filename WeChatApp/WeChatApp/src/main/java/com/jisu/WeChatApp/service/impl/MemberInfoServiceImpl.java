package com.jisu.WeChatApp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.RoleMapper;
import com.jisu.WeChatApp.daoSelf.MemberInfoMapperSelf;
import com.jisu.WeChatApp.daoSelf.WalletInfoMapperSelf;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.PageDataResult;
import com.jisu.WeChatApp.pojo.UserRole;
import com.jisu.WeChatApp.pojo.UserSearchDTO;
import com.jisu.WeChatApp.service.MemberInfoService;

@Service("MemberInfoServiceImpl")
public class MemberInfoServiceImpl implements MemberInfoService {
	@Autowired
	private MemberInfoMapperSelf memberInfoMapperSelf;
	@Autowired
	private MemberInfoMapper userMapper;
	@Autowired
	private WalletInfoMapperSelf walletInfoMapperSelf;
	@Autowired
	private RoleMapper roleMapper;

	@Override
	public List<Map<String, String>> getMemberListByCondition(Map<String, String> condition) {
		// TODO Auto-generated method stub
		return memberInfoMapperSelf.getMemberListByCondition(condition);
	}

	@Override
	public MemberInfo getMemberInfoByMemberNo(String member_no) {
		// TODO Auto-generated method stub
		return userMapper.selectByPrimaryKey(member_no);
	}

	@Override
	public List<Map<String, String>> getMemberIncomeByMemberNo(Map<String, String> condition) {
		// TODO Auto-generated method stub
		return walletInfoMapperSelf.getMemberIncomeByMemberNo(condition);
	}

	@Override
	public Map<String, String> getMemberTotalIncome(String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> member_no_map = new HashMap<String, String>();
		member_no_map.put("member_no", member_no);
		return walletInfoMapperSelf.getMemberTotalIncome(member_no_map);
	}

	@Override
	public Map<String, String> getMemebrDefaultAddress(String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> member_no_map = new HashMap<String, String>();
		member_no_map.put("member_no", member_no);
		return memberInfoMapperSelf.getMemberDefaultAddress(member_no_map);
	}

	@Override
	public MemberInfo getMemberInfoByUsername(String username) {
		// TODO Auto-generated method stub
		return userMapper.getMemberInfoByUserName(username);
	}

	public UserRole getUserAndRoles(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public PageDataResult getUsers(UserSearchDTO userSearch, Integer page, Integer limit) {
		PageDataResult pdr = new PageDataResult();
		PageHelper.startPage(page, limit);
		List<MemberInfo> urList = userMapper.getUser(userSearch);
		// 获取分页查询后的数据
		PageInfo<MemberInfo> pageInfo = new PageInfo<>(urList);
		// 设置获取到的总记录数total：
		pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
		pdr.setList(pageInfo.getList());
		return pdr;
	}

	public String setJobUser(String id, Integer isJob, String memberNo, Integer version) {
		// TODO Auto-generated method stub
		return null;
	}

	public String setUser(MemberInfo user, String roleIds) {
		// TODO Auto-generated method stub
		return null;
	}

	public String setDelUser(Integer id, int i, String memberNo, Integer version) {
		// TODO Auto-generated method stub
		return null;
	}

	public int updatePwd(String memberNo, String md5Hex) {
		// TODO Auto-generated method stub
		return 0;
	}

	public Map<String, Object> getServerMemberInfoAndHasServer(String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> member_no_map= new HashMap<String, String>();
		member_no_map.put("member_no", member_no);
		Map<String, Object>  result_map= new HashMap<String, Object>();
		result_map.putAll(memberInfoMapperSelf.getServerMemebrInfo(member_no_map));
		result_map.put("server_list", memberInfoMapperSelf.getServerMemberHasServer(member_no_map));
		return result_map;
	}
	
	public List<Map<String, String>> getMemebrListByShopId(String shop_id){
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_id", shop_id);
		return memberInfoMapperSelf.getMemebrListByShopId(map);
	}
}
