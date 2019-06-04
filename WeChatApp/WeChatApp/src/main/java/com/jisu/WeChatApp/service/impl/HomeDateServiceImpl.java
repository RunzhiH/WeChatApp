package com.jisu.WeChatApp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.daoSelf.HomeDateMapperSelf;
import com.jisu.WeChatApp.service.HomeDateService;
import com.jisu.WeChatApp.tool.util.BaseUtils;

@Service("HomeDateServiceImpl")
public class HomeDateServiceImpl implements HomeDateService {
	@Autowired
	private HomeDateMapperSelf homeDateMapperSelf;

	@Override
	public List<Map<String, String>> getMemberChangeDateDay() {
		// TODO Auto-generated method stub
		String end = "15";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getMemberChangeDateDay(map);
	}

	@Override
	public List<Map<String, String>> getMemberChangeDateMonth() {
		String end = "12";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getMemberChangeDateMonth(map);
	}

	@Override
	public List<Map<String, String>> getMemberChangeDateYear() {
		// TODO Auto-generated method stub
		String end = "5";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getMemberChangeDateYear(map);
	}

	@Override
	public List<Map<String, String>> getOrderChangeDateDay() {
		// TODO Auto-generated method stub
		String end = "15";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getOrderChangeDateDay(map);
	}

	@Override
	public List<Map<String, String>> getOrderChangeDateMonth() {
		// TODO Auto-generated method stub
		String end = "12";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getOrderChangeDateMonth(map);
	}

	@Override
	public List<Map<String, String>> getOrderChangeDateYear() {
		// TODO Auto-generated method stub
		String end = "5";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getOrderChangeDateYear(map);
	}
	@Override
	public List<Map<String, String>> getIncomeChangeDateDay() {
		// TODO Auto-generated method stub
		String end = "15";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getIncomeChangeDateDay(map);
	}
	@Override
	public List<Map<String, String>> getIncomeChangeDateMonth() {
		// TODO Auto-generated method stub
		String end = "12";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getIncomeChangeDateMonth(map);
	}
	@Override
	public List<Map<String, String>> getIncomeChangeDateYear() {
		// TODO Auto-generated method stub
		String end = "5";
		String begin = "0";
		List<String> date_list = BaseUtils.SectionToList(begin, end);
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("date_list", date_list);
		return homeDateMapperSelf.getIncomeChangeDateYear(map);
	}

}
