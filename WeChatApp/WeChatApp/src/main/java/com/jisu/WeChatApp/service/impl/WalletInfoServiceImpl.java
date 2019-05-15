package com.jisu.WeChatApp.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.dao.WalletInfoMapper;
import com.jisu.WeChatApp.pojo.WalletInfo;
import com.jisu.WeChatApp.service.WalletInfoService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;

@Service("WalletInfoServiceImpl")
public class WalletInfoServiceImpl implements WalletInfoService {
	@Autowired
	private WalletInfoMapper walletInfoMapper;

	@Override
	public int insertNewWallet(String member_no) {
		// TODO Auto-generated method stub
		WalletInfo walletInfo = new WalletInfo();
		walletInfo.setWalletId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		walletInfo.setMemberNo(member_no);
		return walletInfoMapper.insertSelective(walletInfo);
	}

}
