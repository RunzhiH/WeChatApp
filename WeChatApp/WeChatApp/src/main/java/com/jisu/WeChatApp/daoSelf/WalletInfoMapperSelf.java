package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WalletInfoMapperSelf {
	/**
	 * 根据变动记录id修改钱包信息
	 * 
	 * @param map
	 * @return
	 */
	public int updateWalletInfoByRecordId(Map<String, String> map);

	/**
	 * 获取会员收益列表
	 * 
	 * @param condition
	 * @return
	 */
	public List<Map<String, String>> getMemberIncomeByMemberNo(Map<String, String> condition);

	/**
	 * 获取会员总收益
	 * 
	 * @param member_no_map
	 * @return
	 */
	public Map<String, String> getMemberTotalIncome(Map<String, String> member_no_map);

	public Map<String, String> getWalletInfo(String member_no);

	public int reduceRmbForDrawal(Map<String, String> map);

	public List<Map<String, String>> getWithDrawalList(Map<String, String> condition);

	public Map<String, String> getWithDrawalRecordWithMemberInfo(String record_id);
}
