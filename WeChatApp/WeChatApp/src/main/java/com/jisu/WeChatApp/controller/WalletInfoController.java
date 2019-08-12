package com.jisu.WeChatApp.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.service.impl.WalletInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/wallet")
@RestController
public class WalletInfoController {

	@Autowired
	private WalletInfoServiceImpl walletInfoServiceImpl;

	@RequestMapping("getWalletInfo")
	public MsgModel getWalletInfo(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		Map<String, String> wallet_info = walletInfoServiceImpl.getWalletInfo(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(wallet_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping(value = "applyWithdrawal", method = RequestMethod.POST)
	public MsgModel applyWithdrawal(HttpServletRequest request) {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String rmb = request.getParameter("rmb");
		String withdrawal_type = request.getParameter("withdrawal_type");
		if (StringUtils.isBlank(rmb)) {
			msg.setMessage("提现金额不能为空");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		BigDecimal drawal_rmb = new BigDecimal(rmb);
		if (new BigDecimal(0).compareTo(drawal_rmb) > 0) {
			msg.setMessage("提现金额不能为小于0");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		int type = 0;
		if (StringUtils.isNotBlank(withdrawal_type)) {
			type = Integer.valueOf(withdrawal_type);
		} else {
			msg.setMessage("未知到账方式");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		// 查询余额是否足够提现
		int wallet_num = walletInfoServiceImpl.reduceRmb(member_no, rmb);
		// 查询余额是否足够提现结束
		if (wallet_num == 0) {
			msg.setMessage("余额不足");
			msg.setStatus(MsgModel.ERROR);
			return msg;
		}
		// 插入提现记录
		int num = walletInfoServiceImpl.applyDrawal(member_no, drawal_rmb, type);
		// 插入提现记录结束

		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	@RequestMapping("getWithDrawalList")
	public MsgModel getWithDrawalList(HttpServletRequest request) {
		String member_no = request.getParameter("member_no");
		List<Map<String, String>> withDrawal = walletInfoServiceImpl.getWithDrawalListByMember(member_no);
		MsgModel msg = new MsgModel();
		msg.setContext(withDrawal);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
