package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeDateMapperSelf {

	public List<Map<String, String>> getMemberChangeDateDay(Map<String, List<String>> map);

	public List<Map<String, String>> getMemberChangeDateMonth(Map<String, List<String>> map);

	public List<Map<String, String>> getMemberChangeDateYear(Map<String, List<String>> map);

	public List<Map<String, String>> getOrderChangeDateDay(Map<String, List<String>> map);

	public List<Map<String, String>> getOrderChangeDateMonth(Map<String, List<String>> map);

	public List<Map<String, String>> getOrderChangeDateYear(Map<String, List<String>> map);
	
	public List<Map<String, String>> getIncomeChangeDateDay(Map<String, List<String>> map);

	public List<Map<String, String>> getIncomeChangeDateMonth(Map<String, List<String>> map);

	public List<Map<String, String>> getIncomeChangeDateYear(Map<String, List<String>> map);

}
