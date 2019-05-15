package com.jisu.WeChatApp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.ServerClassMapper;
import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.daoSelf.ServerMapperSelf;
import com.jisu.WeChatApp.pojo.ServerClass;
import com.jisu.WeChatApp.pojo.ServerClassExample;
import com.jisu.WeChatApp.pojo.ShopServer;
import com.jisu.WeChatApp.service.ServerService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

import net.sf.json.JSONArray;

@Service("ServerServiceImpl")
public class ServerServiceImpl implements ServerService {

	@Autowired
	private ServerClassMapper serverClassMapper;

	@Autowired
	private ShopServerMapper shopServerMapper;

	@Autowired
	private ServerMapperSelf serverMapperSelf;

	@Override
	public List<ServerClass> getServerClassList() {
		// TODO Auto-generated method stub
		return serverClassMapper.findAll();
	}

	@Override
	public void updateServerClass(ServerClass permission) {
		// TODO Auto-generated method stub
		serverClassMapper.updateByPrimaryKeySelective(permission);
	}

	@Override
	public void addServerClass(ServerClass permission) {
		// TODO Auto-generated method stub
		permission.setServerClassId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		serverClassMapper.insertSelective(permission);
	}

	@Override
	public ServerClass getServerClass(String id) {
		// TODO Auto-generated method stub
		return serverClassMapper.selectByPrimaryKey(id);
	}

	@Override
	public String delServerClass(String server_class_id) {
		// TODO Auto-generated method stub
		List<ServerClass> childPerm = serverClassMapper.findChildPerm(server_class_id);
		if (null != childPerm && childPerm.size() > 0) {
			return "删除失败，请您先删除该权限的子节点";
		}
		if (serverClassMapper.deleteByPrimaryKey(server_class_id) > 0) {
			return "ok";
		} else {
			return "删除失败，请您稍后再试";
		}
	}

	@Override
	public List<ServerClass> getLevel1ServerClassList() {
		// TODO Auto-generated method stub
		ServerClassExample example = new ServerClassExample();
		example.createCriteria().andServerClassLevelEqualTo(1);
		return serverClassMapper.selectByExample(example);
	}

	@Override
	public List<ServerClass> getNextLevelServerClassList(String server_class_id) {
		// TODO Auto-generated method stub
		ServerClassExample example = new ServerClassExample();
		example.createCriteria().andServerClassPidEqualTo(server_class_id);
		return serverClassMapper.selectByExample(example);
	}

	@Override
	public String delServer(String id) {
		// TODO Auto-generated method stub
		if (shopServerMapper.deleteByPrimaryKey(id) > 0) {
			return "ok";
		} else {
			return "删除失败，请您稍后再试";
		}
	}

	@Override
	public List<Map<String, String>> getAllServerList() {
		// TODO Auto-generated method stub
		return serverMapperSelf.getAllServerList();
	}

	@Override
	public void updateServer(ShopServer shopServer) {
		// TODO Auto-generated method stub
		shopServerMapper.updateByPrimaryKeySelective(shopServer);
	}

	@Override
	public void addServer(ShopServer shopServer) {
		// TODO Auto-generated method stub
		shopServer.setShopServerId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		shopServerMapper.insertSelective(shopServer);
	}

	@Override
	public ShopServer getServer(String id) {
		// TODO Auto-generated method stub
		return shopServerMapper.selectByPrimaryKey(id);
	}

	public List<Map<String, String>> getServerMember(String order_id) {
		// TODO Auto-generated method stub
		return serverMapperSelf.getServerMemberByOrderId(order_id);
	}

	@Override
	public List<Map<String, String>> getServerListByClassId(String server_class_id) {
		// TODO Auto-generated method stub
		Map<String, String> id_map = new HashMap<String, String>();
		id_map.put("server_class_id", server_class_id);
		return serverMapperSelf.getServerListByClassId(id_map);
	}

}
