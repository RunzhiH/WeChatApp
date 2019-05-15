package com.jisu.WeChatApp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.daoSelf.AreaInfoMapperSelf;
import com.jisu.WeChatApp.service.AreaInfoService;

@Service("AreaInfoServiceImpl")
public class AreaInfoServiceImpl implements AreaInfoService {
	@Autowired
	private AreaInfoMapperSelf areaInfoMapperSelf;

	@Override
	public List<Map<String, String>> getSheng() {
		// TODO Auto-generated method stub
		return areaInfoMapperSelf.getSheng();
	}

	@Override
	public List<Map<String, String>> getNextAreaInfo(String area_code) {
		// TODO Auto-generated method stub
		Map<String, String> area_code_map = new HashMap<String, String>();
		area_code_map.put("area_code", area_code);
		return areaInfoMapperSelf.getNextAreaInfo(area_code);
	}

}
