package com.jisu.WeChatApp.service;

public interface WalletInfoService {
	/**
	 * 添加新的钱包
	 * 
	 * @param member_no
	 * @return
	 */
	public int insertNewWallet(String member_no);
}
