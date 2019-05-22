package com.jisu.WeChatApp.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

	@Override
	public List<Map<String, String>> getFreeServerMemberList(String shop_server_id) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("shop_server_id", shop_server_id);
		return serverMapperSelf.getFreeServerMemberList(map);
	}

	@Override
	public List<Map<String, Object>> getAppointmentServerTimeList(String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_no", member_no);
		List<Map<String, String>> server_time_list = serverMapperSelf.getAppointmentServerTimeList(map);

		SimpleDateFormat day_sdf = new SimpleDateFormat("YYYY-MM-dd");
		SimpleDateFormat hour_sdf = new SimpleDateFormat("HH");
		List<Map<String, Object>> result_list= new ArrayList<Map<String,Object>>();
		for (int i = 0; i < 3; i++) {
			Map<String, Object> result_map = new HashMap<String, Object>();
			Date now = new Date();
			now.setDate(now.getDate() + i);
			if (i == 0) {
				now.setHours(now.getHours() + 2);
			}
			String day_str = day_sdf.format(now);
			String hour_str = hour_sdf.format(now);
			int begin_hour = 9;
			if (i == 0 && Integer.valueOf(hour_str).compareTo(new Integer(begin_hour)) > 0) {
				begin_hour = Integer.valueOf(hour_str);
			}
			List<String> setup_time=new ArrayList<String>();
			for (int j = begin_hour; j < 21; j++) {
				setup_time.add(String.valueOf(j));
			}
			for (Map<String,String> server_time : server_time_list) {
				if(day_str.equals(server_time.get("the_day"))) {
					String begin_time=server_time.get("appointment_time_start");
					String end_time=server_time.get("appointment_time_end");
					for (int k =  Integer.valueOf(begin_time); k < Integer.valueOf(end_time); k++) {
						setup_time.remove(k);
					}
				}
			}
			List<Map<String, String>> setup_time_list=new ArrayList<Map<String,String>>();
			for (String hour : setup_time) {
				Map<String, String> setup_time_map=new HashMap<String, String>();
				setup_time_map.put("time_key", day_str+" "+hour);
				if(hour.length()<2) {
					hour="0"+hour+":00";
				}else {
					hour+=":00";
				}
				setup_time_map.put("hour_str", hour);
				setup_time_list.add(setup_time_map);
			}
			result_map.put("day_key", day_str);
			result_map.put("time_list", setup_time_list);
			result_list.add(result_map);
		}

		return result_list;
	}

}
