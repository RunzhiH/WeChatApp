package com.jisu.WeChatApp.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.ShopLablePraiseHistroyMapper;
import com.jisu.WeChatApp.daoSelf.LableInfoMapperSelf;
import com.jisu.WeChatApp.pojo.ShopLablePraiseHistroy;
import com.jisu.WeChatApp.pojo.ShopLablePraiseHistroyExample;
import com.jisu.WeChatApp.service.LableInfoService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

@Service("LableInfoServiceImpl")
public class LableInfoServiceImpl implements LableInfoService {

	@Autowired
	private ShopLablePraiseHistroyMapper shopLablePraiseHistroyMapper;
	@Autowired
	private LableInfoMapperSelf lableInfoMapperSelf;

	@Override
	public List<ShopLablePraiseHistroy> getShoplabLablePraiseHistroys(String shop_lable_id, String member_no) {
		// TODO Auto-generated method stub
		ShopLablePraiseHistroyExample example = new ShopLablePraiseHistroyExample();
		example.createCriteria().andShopLableIdEqualTo(shop_lable_id);
		example.createCriteria().andMemberNoEqualTo(member_no);
		return shopLablePraiseHistroyMapper.selectByExample(example);
	}

	@Override
	public int insertShopLablePraiseHistroy(String shop_lable_id, String member_no) {
		// TODO Auto-generated method stub
		ShopLablePraiseHistroy histroy = new ShopLablePraiseHistroy();
		histroy.setShopLablePraiseHistroyId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		histroy.setShopLableId(shop_lable_id);
		histroy.setMemberNo(member_no);
		histroy.setCreateTime(new Date());
		return shopLablePraiseHistroyMapper.insertSelective(histroy);
	}

	@Override
	public int deleteShopLablePraiseHistroy(String shop_lable_id, String member_no) {
		// TODO Auto-generated method stub
		ShopLablePraiseHistroyExample example = new ShopLablePraiseHistroyExample();
		example.createCriteria().andShopLableIdEqualTo(shop_lable_id);
		example.createCriteria().andMemberNoEqualTo(member_no);
		return shopLablePraiseHistroyMapper.deleteByExample(example);
	}

	@Override
	public int updateShopLablePraiseNum(String shop_lable_id) {
		// TODO Auto-generated method stub
		Map<String, String> shop_lable_id_map = new HashMap<String, String>();
		shop_lable_id_map.put("shop_lable_id", shop_lable_id);
		return lableInfoMapperSelf.updateShopLablePraiseNum(shop_lable_id_map);
	}

	@Override
	public int deletelableInfoByLableId(String lable_id_str) {
		// TODO Auto-generated method stub
		String[] lable_id_arr = lable_id_str.split(",");
		List<String> lable_id_list = Arrays.asList(lable_id_arr);
		Map<String, List<String>> list_map = new HashMap<String, List<String>>();
		list_map.put("lable_id_list", lable_id_list);
		return lableInfoMapperSelf.deleteLableInfoByLableId(list_map);
	}

	@Override
	public List<Map<String, String>> getLableList(Map<String, String> condition) {
		// TODO Auto-generated method stub
		return lableInfoMapperSelf.getLableList(condition);
	}

}
