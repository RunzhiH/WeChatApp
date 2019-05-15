package com.jisu.WeChatApp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.RotationMapMapper;
import com.jisu.WeChatApp.pojo.Permission;
import com.jisu.WeChatApp.pojo.RotationMap;
import com.jisu.WeChatApp.service.RotationService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

@Service("RotationServiceImpl")
public class RotationServiceImpl implements RotationService {

	@Autowired
	private RotationMapMapper RotationMapMapper;

	public int updateRotation(RotationMap rotationMap) {
		return RotationMapMapper.updateByPrimaryKeySelective(rotationMap);
	}

	public int addRotation(RotationMap rotationMap) {
		rotationMap.setRotationId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		return RotationMapMapper.insertSelective(rotationMap);
	}

	@Override
	public String delRotation(String id) {
		// 查看该权限是否有子节点，如果有，先删除子节点
		if (this.RotationMapMapper.deleteByPrimaryKey(id) > 0) {
			return "ok";
		} else {
			return "删除失败，请您稍后再试";
		}
	}

}
