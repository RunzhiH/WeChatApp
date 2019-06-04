package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

public interface HomeDateService {

	public List<Map<String, String>> getMemberChangeDateDay();

	public List<Map<String, String>> getMemberChangeDateMonth();

	public List<Map<String, String>> getMemberChangeDateYear();

	public List<Map<String, String>> getOrderChangeDateDay();

	public List<Map<String, String>> getOrderChangeDateMonth();

	public List<Map<String, String>> getOrderChangeDateYear();

	public List<Map<String, String>> getIncomeChangeDateDay();

	public List<Map<String, String>> getIncomeChangeDateMonth();

	public List<Map<String, String>> getIncomeChangeDateYear();

}
