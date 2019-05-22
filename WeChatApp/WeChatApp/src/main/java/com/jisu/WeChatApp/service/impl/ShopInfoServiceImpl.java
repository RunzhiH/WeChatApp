package com.jisu.WeChatApp.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.MemberProhiMapper;
import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.dao.ShopLableMapper;
import com.jisu.WeChatApp.dao.ShopPraiseHistoryMapper;
import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.daoSelf.ShopInfoMapperSelf;
import com.jisu.WeChatApp.pojo.MemberProhi;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.pojo.ShopLableExample;
import com.jisu.WeChatApp.pojo.ShopPraiseHistory;
import com.jisu.WeChatApp.pojo.ShopPraiseHistoryExample;
import com.jisu.WeChatApp.pojo.ShopServerExample;
import com.jisu.WeChatApp.service.ShopInfoService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

import net.sf.json.JSONArray;

@Service("ShopInfoServiceImpl")
public class ShopInfoServiceImpl implements ShopInfoService {
	@Autowired
	private ShopInfoMapperSelf shopInfoMapperSelf;
	@Autowired
	private ShopLableMapper shopLableMapper;
	@Autowired
	private ShopServerMapper shopServerMapper;
	@Autowired
	private ShopPraiseHistoryMapper shopPraiseHistoryMapper;
	@Autowired
	private ShopInfoMapper shopInfoMapper;
	@Autowired
	private MemberProhiMapper memberProhiMapper;

	@Override
	public List<Map<String, String>> getShopListByCondition(Map<String, String> condition) {
		// TODO Auto-generated method stub

		return shopInfoMapperSelf.getShopInfoListByCondition(condition);
	}

	@Override
	public int deleteShopByShopId(String shop_id_str) {
		// TODO Auto-generated method stub
		String[] shop_id_arr = shop_id_str.split(",");
		List<String> shop_id_list = Arrays.asList(shop_id_arr);
		return shopInfoMapperSelf.deleteShopByShopId(shop_id_list);
	}

	@Override
	public int saveShopLableInfo(String shop_lable_str, String shop_id) {
		// TODO Auto-generated method stub
		// 添加前先删除已关联的标签
		ShopLableExample example = new ShopLableExample();
		example.createCriteria().andShopIdEqualTo(shop_id);
		shopLableMapper.deleteByExample(example);
		// 添加前先删除已关联的标签结束
		String[] lable_id_arr = shop_lable_str.split(",");
		List<String> lable_id_list = Arrays.asList(lable_id_arr);
		Map<String, Object> insert_map = new HashMap<String, Object>();
		insert_map.put("shop_id", shop_id);
		insert_map.put("lable_id_list", lable_id_list);
		return shopInfoMapperSelf.insertShopLable(insert_map);
	}

	@Override
	public int saveShopServerInfo(String shop_server_str, String shop_id) {
		// TODO Auto-generated method stub
		// 添加前先删除已关联的服务
		ShopServerExample example = new ShopServerExample();
		example.createCriteria().andShopIdEqualTo(shop_id);
		shopServerMapper.deleteByExample(example);
		// 添加前先删除已关联的服务结束
		JSONArray shop_server_json = JSONArray.fromObject(shop_server_str);
		List<Map<String, String>> shop_server_list = JSONArray.toList(shop_server_json);
		Map<String, Object> insert_map = new HashMap<String, Object>();
		insert_map.put("shop_id", shop_id);
		insert_map.put("shop_server_list", shop_server_list);
		return shopInfoMapperSelf.insertShopServer(insert_map);
	}

	@Override
	public List<Map<String, String>> getShopLableByShopId(String shop_id) {
		// TODO Auto-generated method stub
		Map<String, String> shop_id_map = new HashMap<String, String>();
		shop_id_map.put("shop_id", shop_id);
		return shopInfoMapperSelf.getShopLableByShopId(shop_id_map);
	}

	@Override
	public List<Map<String, String>> getShopServerByShopId(String shop_id) {
		// TODO Auto-generated method stub
		Map<String, String> shop_id_map = new HashMap<String, String>();
		shop_id_map.put("shop_id", shop_id);
		return shopInfoMapperSelf.getShopServerByshopId(shop_id_map);
	}

	@Override
	public Map<String, String> getShopInfoRank(String shop_id, String shi_code, String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_id", shop_id);
		map.put("shi_code", shi_code);
		map.put("member_no", member_no);
		return shopInfoMapperSelf.getShopInRankByShopId(map);
	}

	@Override
	public List<Map<String, String>> getShopPraiseHistoriesByMember(String shop_id, String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("shop_id", shop_id);
		condition.put("member_no", member_no);
		return shopInfoMapperSelf.getShopPraiseHistoryByMemberAndShopId(condition);
	}

	@Override
	public int insertShopPraiseHistory(String shop_id, String member_no) {
		// TODO Auto-generated method stub
		ShopPraiseHistory shopPraiseHistory = new ShopPraiseHistory();
		shopPraiseHistory.setPraiseHistoryId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		shopPraiseHistory.setCreateTime(new Date());
		shopPraiseHistory.setMemberOn(member_no);
		shopPraiseHistory.setShopId(shop_id);
		return shopPraiseHistoryMapper.insertSelective(shopPraiseHistory);
	}

	@Override
	public int doMemberLikeShop(String shop_id, String type) {
		// TODO Auto-generated method stub
		Map<String, String> shop_id_map = new HashMap<String, String>();
		shop_id_map.put("shop_id", shop_id);
		shop_id_map.put("is_like", type);
		return shopInfoMapperSelf.updatShopPraiseNum(shop_id_map);
	}

	@Override
	public int deleteShopPraiseHistory(String shop_id, String member_no) {
		// TODO Auto-generated method stub
		ShopPraiseHistoryExample example = new ShopPraiseHistoryExample();
		example.createCriteria().andShopIdEqualTo(shop_id);
		example.createCriteria().andMemberOnEqualTo(member_no);
		return shopPraiseHistoryMapper.deleteByExample(example);
	}

	@Override
	public List<Map<String, String>> getMyLatelyShopList(String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> member_map = new HashMap<String, String>();
		member_map.put("member_no", member_no);
		return shopInfoMapperSelf.getMyLatelyShopList(member_map);
	}

	@Override
	public List<Map<String, String>> getRecommendShopList(String shop_server_id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_server_id", shop_server_id);
		return shopInfoMapperSelf.getRecommendShopList(map);
	}

	@Override
	public List<ShopInfo> getAllShopList() {
		// TODO Auto-generated method stub
		return shopInfoMapper.getAllShopList();
	}

	@Override
	public Map<String, Object> getshopInfoByMemberNo(String member_no) {
		// TODO Auto-generated method stub
		Map<String, Object> result_map = new HashMap<String, Object>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_no", member_no);
		result_map.putAll(shopInfoMapperSelf.getshopInfoByMemberNo(map));
		result_map.put("server_list", shopInfoMapperSelf.getShopHasServerList(map));
		return result_map;
	}

	@Override
	public Map<String, String> getShopInfoByShopIdAndClassId(String shop_id, String server_class_id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_id", shop_id);
		map.put("server_class_id", server_class_id);
		return shopInfoMapperSelf.getShopInfoByShopIdAndClassId(map);
	}

	@Override
	public List<Map<String, String>> getFreeServerShopList(String shop_server_id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_server_id", shop_server_id);
		return shopInfoMapperSelf.getFreeServerShopList(map);
	}

	@Override
	public void shopProhibitOrder(String shop_id) {
		// TODO Auto-generated method stub
		ShopInfo shopInfo = new ShopInfo();
		shopInfo.setShopId(shop_id);
		shopInfo.setInProhi(1);
		shopInfoMapper.updateByPrimaryKeySelective(shopInfo);
		String member_no= shopInfo.getMemberNo();
		//插入封禁记录
		MemberProhi memberProhi= new MemberProhi();
		memberProhi.setMemberProhiId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		memberProhi.setMemberType(1);
		memberProhi.setMemberNo(member_no);
		memberProhi.setProhiTime(5);
		memberProhi.setProhiStatus(0);
		memberProhi.setCreateTime(new Date());
		memberProhi.setProhiType(1);
		memberProhiMapper.insertSelective(memberProhi);
		//插入封禁记录结束
	}

}
