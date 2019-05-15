package com.jisu.WeChatApp.service;

import com.jisu.WeChatApp.pojo.RotationMap;

public interface RotationService {
	public int updateRotation(RotationMap rotationMap);

	public int addRotation(RotationMap rotationMap);
	
	public String delRotation(String id);
	
}
