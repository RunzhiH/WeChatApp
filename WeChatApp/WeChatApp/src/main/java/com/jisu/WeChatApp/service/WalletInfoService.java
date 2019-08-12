package com.jisu.WeChatApp.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alipay.api.AlipayApiException;

import net.sf.json.JSONArray;

public interface WalletInfoService {
	/**
	 * 添加新的钱包
	 * 
	 * @param member_no
	 * @return
	 */
	public int insertNewWallet(String member_no);

	/**
	 * 插入预计收益
	 * 
	 * @param order_id
	 * @return
	 */
	public int insertProfitInfo(String order_id);

	Map<String, String> getWalletInfo(String member_no);

	int applyDrawal(String member_no, BigDecimal rmb, int withdrawal_type);

	JSONArray getWithDrawalList(Map<String, String> condition);

	int reduceRmb(String member_no, String rmb);

	String agreeWithDrawal(String record_id,HttpServletRequest request) throws AlipayApiException;

	Map<String, String> getWithDrawalInfo(String id);

	String notAgreeWithDrawal(String id,String desc);

	List<Map<String, String>> getWithDrawalListByMember(String member_no);
}
