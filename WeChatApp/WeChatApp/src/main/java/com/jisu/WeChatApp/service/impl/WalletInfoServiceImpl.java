package com.jisu.WeChatApp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.jisu.WeChatApp.dao.OrderInfoMapper;
import com.jisu.WeChatApp.dao.ProfitInfoMapper;
import com.jisu.WeChatApp.dao.WalletInfoMapper;
import com.jisu.WeChatApp.dao.WithdrawalRecordMapper;
import com.jisu.WeChatApp.daoSelf.ProfitInfoMapperSelf;
import com.jisu.WeChatApp.daoSelf.WalletChangeRecordMapperSelf;
import com.jisu.WeChatApp.daoSelf.WalletInfoMapperSelf;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.WalletInfo;
import com.jisu.WeChatApp.pojo.WithdrawalRecord;
import com.jisu.WeChatApp.service.WalletInfoService;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.PayUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

import net.sf.json.JSONArray;

@Service("WalletInfoServiceImpl")
public class WalletInfoServiceImpl implements WalletInfoService {
	@Autowired
	private WalletInfoMapper walletInfoMapper;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private ProfitInfoMapper profitInfoMapper;
	@Autowired
	private ProfitInfoMapperSelf profitInfoMapperSelf;
	@Autowired
	private WalletInfoMapperSelf walletInfoMapperSelf;
	@Autowired
	private WalletChangeRecordMapperSelf walletChangeRecordMapperSelf;
	@Autowired
	private WithdrawalRecordMapper withdrawalRecordMapper;

	@Override
	public int insertNewWallet(String member_no) {
		// TODO Auto-generated method stub
		WalletInfo walletInfo = new WalletInfo();
		walletInfo.setWalletId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		walletInfo.setMemberNo(member_no);
		return walletInfoMapper.insertSelective(walletInfo);
	}

	@Override
	public int insertProfitInfo(String order_id) {
		// TODO Auto-generated method stub
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(order_id);
		if (1 == orderInfo.getOrderStatus()) {
			String shop_id = orderInfo.getShopId();
			if (StringUtils.isNotBlank(shop_id)) {
				// 结算服务店家收益
				profitInfoMapperSelf.insertProfitForServerShop(order_id);
				// 结算服务店家收益结束

				// 结算推荐店家收益
				profitInfoMapperSelf.insertProfitForShareShop(order_id);
				// 结算推荐店家收益结束
			} else {
				// 结算推荐店家收益
				profitInfoMapperSelf.insertProftForOnlyShareShop(order_id);
				// 结算推荐店家收益结束
			}

			// 结算服务人员收益
			profitInfoMapperSelf.insertProfitForServerMember(order_id);
			// 结算服务人员收益结束

			// 结算业务员收益
			profitInfoMapperSelf.insertProfitForBusinessMemebr(order_id);
			// 结算业务员收益结束
		}
		return 0;
	}

	@Override
	public Map<String, String> getWalletInfo(String member_no) {
		// TODO Auto-generated method stub
		return walletInfoMapperSelf.getWalletInfo(member_no);
	}

	@Override
	public int applyDrawal(String member_no, BigDecimal rmb, int type) {
		// TODO Auto-generated method stub
		// 插入提现记录
		String drawal_record_id = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		WithdrawalRecord drawal_record = new WithdrawalRecord();
		drawal_record.setWithdrawalRecordId(drawal_record_id);
		drawal_record.setCreateTime(new Date());
		drawal_record.setMemberNo(member_no);
		drawal_record.setWithdrawalPrice(rmb);
		drawal_record.setWithdrawalStatus(0);
		drawal_record.setWithdrawalType(type);
		// 插入提现记录结束
		int num = withdrawalRecordMapper.insertSelective(drawal_record);
		if (num > 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("drawal_record_id", drawal_record_id);
			// 插入余额变动记录
			walletChangeRecordMapperSelf.insertWalletChangeRecordForDrawal(map);
			// 插入余额变动记录 结束
		}
		return num;
	}

	@Override
	public int reduceRmb(String member_no, String rmb) {
		// TODO Auto-generated method stub
		Map<String, String> map = new HashMap<String, String>();
		map.put("member_no", member_no);
		map.put("rmb", rmb);
		int num = walletInfoMapperSelf.reduceRmbForDrawal(map);
		return num;
	}

	@Override
	public JSONArray getWithDrawalList(Map<String, String> condition) {
		// TODO Auto-generated method stub
		List<Map<String, String>> drawal_list = walletInfoMapperSelf.getWithDrawalList(condition);
		JSONArray withdrawal_list = JSONArray.fromObject(drawal_list);
		return withdrawal_list;
	}

	@Override
	public String agreeWithDrawal(String record_id, HttpServletRequest request) throws AlipayApiException {
		// TODO Auto-generated method stub
		// 获取该提现记录
		Map<String, String> record = walletInfoMapperSelf.getWithDrawalRecordWithMemberInfo(record_id);
		// 获取该提现记录结束
		if (record != null) {
			String withdrawal_type = record.get("withdrawal_type");
			String amount = String.valueOf(record.get("withdrawal_price"));
			boolean pay_success = false;
			if ("0".equals(withdrawal_type)) {
				pay_success = true;
			} else if ("2".equals(withdrawal_type)) {

				// 获取提现账号
				String app_id = PropertyUtil.getProperty("alipay_app_id");
				String private_key = PropertyUtil.getProperty("private_key");
				String alipay_public_key = PropertyUtil.getProperty("alipay_public_key");
				String out_biz_no = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
				String payee_account = record.get("alipay_account");

				AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", app_id, private_key, "json", "GBK", alipay_public_key, "RSA2");
				AlipayFundTransToaccountTransferRequest alipay_request = new AlipayFundTransToaccountTransferRequest();

				alipay_request.setBizContent("{" + "\"out_biz_no\":\"" + out_biz_no + "\"," + "\"payee_type\":\"ALIPAY_LOGONID\"," + "\"payee_account\":\"" + payee_account + "\"," + "\"amount\":\"" + amount + "\"," + "\"payer_show_name\":\"大手艺服务平台\"," + "\"remark\":\"提现到账\"" + "  }");
				AlipayFundTransToaccountTransferResponse response = alipayClient.execute(alipay_request);
				if (response.isSuccess()) {
					System.out.println("调用成功");
					pay_success = true;
				} else {
					System.out.println("调用失败");
					return response.getMsg();
				}

			} else if ("1".equals(withdrawal_type)) {
				String openId = record.get("openid");
				String money = String.valueOf(new BigDecimal(amount).multiply(new BigDecimal(100)));
				String ip = PayUtils.getIpAddr(request);
				String desc = "提现到账";
				String msg = PayUtils.weixinWithdraw(openId, ip, money, desc);
				if ("转账成功".equals(msg)) {
					pay_success = true;
				} else {
					return msg;
				}

			} else {

				return "未知提现方式";
			}
			// 修改状态
			if (pay_success) {
				WithdrawalRecord withdrawalRecord = new WithdrawalRecord();
				withdrawalRecord.setWithdrawalRecordId(record_id);
				withdrawalRecord.setWithdrawalStatus(1);
				withdrawalRecord.setFinishTime(new Date());
				withdrawalRecordMapper.updateByPrimaryKeySelective(withdrawalRecord);
				return "ok";
			}
			// 修改状态结束
		} else {
			return "不存在该提现记录";
		}
		return null;

	}

	@Override
	public Map<String, String> getWithDrawalInfo(String id) {
		// TODO Auto-generated method stub
		return walletInfoMapperSelf.getWithDrawalRecordWithMemberInfo(id);
	}

	@Override
	public String notAgreeWithDrawal(String id, String desc) {
		WithdrawalRecord withdrawalRecord = new WithdrawalRecord();
		withdrawalRecord.setWithdrawalRecordId(id);
		withdrawalRecord.setWithdrawalStatus(2);
		withdrawalRecord.setRefuseTime(new Date());
		withdrawalRecord.setRefuseNote(desc);
		int num = withdrawalRecordMapper.updateByPrimaryKeySelective(withdrawalRecord);
		if (num > 0) {
			return "ok";
		}
		return "系统错误";
	}

	@Override
	public List<Map<String, String>> getWithDrawalListByMember(String member_no) {
		// TODO Auto-generated method stub
		Map<String, String> map= new HashMap<String, String>();
		map.put("member_no", member_no);
		return walletInfoMapperSelf.getWithDrawalList(map);
	}

}
