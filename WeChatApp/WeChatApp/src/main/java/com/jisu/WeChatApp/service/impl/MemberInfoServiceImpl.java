package com.jisu.WeChatApp.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.jisu.WeChatApp.dao.MemberInfoMapper;
import com.jisu.WeChatApp.dao.MemberProhiMapper;
import com.jisu.WeChatApp.dao.RoleMapper;
import com.jisu.WeChatApp.dao.ServerMemberInfoMapper;
import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.daoSelf.MemberInfoMapperSelf;
import com.jisu.WeChatApp.daoSelf.ServerMapperSelf;
import com.jisu.WeChatApp.daoSelf.WalletInfoMapperSelf;
import com.jisu.WeChatApp.pojo.MemberInfo;
import com.jisu.WeChatApp.pojo.MemberProhi;
import com.jisu.WeChatApp.pojo.PageDataResult;
import com.jisu.WeChatApp.pojo.ServerMemberInfo;
import com.jisu.WeChatApp.pojo.ServerMemberInfoExample;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.pojo.ShopInfoExample;
import com.jisu.WeChatApp.pojo.UserRole;
import com.jisu.WeChatApp.pojo.UserSearchDTO;
import com.jisu.WeChatApp.service.MemberInfoService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

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
	@Autowired
	private ServerMemberInfoMapper serverMemberInfoMapper;
	@Autowired
	private ServerMapperSelf serverMapperSelf;
	@Autowired
	private MemberProhiMapper memberProhiMapper;
	@Autowired
	private ShopInfoMapper shopInfoMapper;

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
		Map<String, String> member_no_map = new HashMap<String, String>();
		member_no_map.put("member_no", member_no);
		Map<String, Object> result_map = new HashMap<String, Object>();
		result_map.putAll(memberInfoMapperSelf.getServerMemebrInfo(member_no_map));
		result_map.put("server_list", memberInfoMapperSelf.getServerMemberHasServer(member_no_map));
		return result_map;
	}

	@Override
	public Map<String, Object> getMemebrListByShopId(String shop_id) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_id", shop_id);
		Map<String, Object> result_map = new HashMap<String, Object>();
		List<Map<String, String>> member_list = memberInfoMapperSelf.getMemebrListByShopId(map);
		result_map.put("member_list", member_list);
		result_map.putAll(memberInfoMapperSelf.getMemberCountByShopId(map));
		return result_map;
	}

	@Override
	public void autoCloseMemberProhi() {
		// TODO Auto-generated method stub
		List<Map<String, String>> member_prohi_list = serverMapperSelf.getProhiMemberList();
		for (Map<String, String> member_prohi : member_prohi_list) {
			String member_type = member_prohi.get("member_type");
			int num = 0;
			String member_no = member_prohi.get("member_no");
			if ("1".equals(member_type)) {
				// 商家解封
				ShopInfo shopInfo = new ShopInfo();
				shopInfo.setInProhi(0);
				ShopInfoExample example = new ShopInfoExample();
				example.createCriteria().andMemberNoEqualTo(member_no);
				num = shopInfoMapper.updateByExampleSelective(shopInfo, example);
			} else if ("2".equals(member_type)) {
				// 技术人员解封
				ServerMemberInfo serverMemberInfo = new ServerMemberInfo();
				serverMemberInfo.setInProhi(0);
				ServerMemberInfoExample example = new ServerMemberInfoExample();
				example.createCriteria().andMemberNoEqualTo(member_no);
				num = serverMemberInfoMapper.updateByExampleSelective(serverMemberInfo, example);
			}
			if (num > 0) {
				MemberProhi memberProhi = new MemberProhi();
				memberProhi.setMemberProhiId(member_prohi.get("member_prohi_id"));
				memberProhi.setProhiStatus(1);
				memberProhiMapper.updateByPrimaryKeySelective(memberProhi);
			}
		}
	}

	public void memberProhibitOrder(String member_no) {
		// TODO Auto-generated method stub
		ServerMemberInfo serverMemberInfo = new ServerMemberInfo();
		serverMemberInfo.setInProhi(1);
		ServerMemberInfoExample example = new ServerMemberInfoExample();
		example.createCriteria().andMemberNoEqualTo(member_no);
		serverMemberInfoMapper.updateByExampleSelective(serverMemberInfo, example);

		// 插入封禁记录
		MemberProhi memberProhi = new MemberProhi();
		memberProhi.setMemberProhiId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		memberProhi.setMemberType(1);
		memberProhi.setMemberNo(member_no);
		memberProhi.setProhiTime(5);
		memberProhi.setProhiStatus(0);
		memberProhi.setCreateTime(new Date());
		memberProhi.setProhiType(1);
		memberProhiMapper.insertSelective(memberProhi);
		// 插入封禁记录结束
	}

	@Override
	public String updateIsShare(String is_share, String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_no", member_no);
		map.put("is_share", is_share);
		int num = memberInfoMapperSelf.updateMemberIsShare(map);
		if (num > 0) {
			return "ok";
		}
		return "出错了,请稍后重试";
	}
	@Override
	public PageDataResult getAllOperatorMemberList(UserSearchDTO userSearch, Integer page, Integer limit) {
		// TODO Auto-generated method stub
		PageDataResult pdr = new PageDataResult();
		PageHelper.startPage(page, limit);
		List<Map<String, String>> list = memberInfoMapperSelf.getAllOperatorMemberList(userSearch);
		PageInfo<Map<String, String>> pageInfo = new PageInfo<>(list);
		// 设置获取到的总记录数total：
		pdr.setTotals(Long.valueOf(pageInfo.getTotal()).intValue());
		pdr.setList(pageInfo.getList());
		return pdr;
	}
	
}
