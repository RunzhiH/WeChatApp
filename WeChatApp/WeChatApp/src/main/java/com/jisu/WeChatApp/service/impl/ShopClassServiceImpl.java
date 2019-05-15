package com.jisu.WeChatApp.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.daoSelf.ShopClassMapperSelf;
import com.jisu.WeChatApp.service.ShopClassService;
@Service("ShopClassServiceImpl")
public class ShopClassServiceImpl implements ShopClassService {
	@Autowired
	private ShopClassMapperSelf shopClassMapperSelf;

	@Override
	public int deleteShopClassInfoByShopClassId(String shop_class_id_str) {
		// TODO Auto-generated method stub
		String[] shop_class_id_arr = shop_class_id_str.split(",");
		List<String> shop_class_id_list = Arrays.asList(shop_class_id_arr);
		Map<String, List<String>> list_map = new HashMap<String, List<String>>();
		list_map.put("shop_class_id_list", shop_class_id_list);
		return shopClassMapperSelf.deleteShopClassByShopClassId(list_map);
	}

	@Override
	public List<Map<String, String>> getShopClassList(Map<String, String> condition) {
		// TODO Auto-generated method stub
		return shopClassMapperSelf.getShopClassList(condition);
	}
	
	

}
