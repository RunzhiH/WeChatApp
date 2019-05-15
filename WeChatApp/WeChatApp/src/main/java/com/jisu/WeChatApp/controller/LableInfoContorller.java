package com.jisu.WeChatApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.LableInfoMapper;
import com.jisu.WeChatApp.pojo.LableInfo;
import com.jisu.WeChatApp.pojo.ShopLablePraiseHistroy;
import com.jisu.WeChatApp.service.impl.LableInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/lable")
@RestController
public class LableInfoContorller {
	@Autowired
	private LableInfoMapper lableInfoMapper;
	@Autowired
	private LableInfoServiceImpl lableInfoServiceImpl;

	/**
	 * 保存修改商家标签
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveLableInfo", method = RequestMethod.POST)
	public MsgModel saveLableInfo(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String lable_name = request.getParameter("lable_name");
		String lable_desc = request.getParameter("lable_desc");
		String lable_id = request.getParameter("lable_id");

		LableInfo lableInfo = new LableInfo();
		lableInfo.setLableName(lable_name);
		lableInfo.setLableDesc(lable_desc);
		int save_num = 0;
		if (StringUtils.isNotBlank(lable_id)) {
			lableInfo.setLableId(lable_id);
			save_num = lableInfoMapper.updateByPrimaryKeySelective(lableInfo);
		} else {
			lableInfo.setLableId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
			save_num = lableInfoMapper.insertSelective(lableInfo);
		}
		if (save_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 获取标签列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getLableList")
	public MsgModel getLableList(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String labble_name = request.getParameter("lable_name");
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("lable_name", labble_name);
		List<Map<String, String>> lable_list = lableInfoServiceImpl.getLableList(condition);
		msg.setStatus(MsgModel.SUCCESS);
		msg.setContext(lable_list);
		return msg;
	}

	/**
	 * 删除标签
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteLableInfoByLableId")
	public MsgModel deleteLableInfoByLableId(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String lable_id_str = request.getParameter("lable_id_str");
		int delete_num = lableInfoServiceImpl.deletelableInfoByLableId(lable_id_str);
		if (delete_num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 商家标签点赞
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "doShopLableLike")
	public MsgModel doShopLableLike(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		String member_no = request.getParameter("member_no");
		String shop_lable_id = request.getParameter("shop_lable_id");
		String lable_is_like = request.getParameter("lable_is_like");
		int num = 0;
		if ("0".equals(lable_is_like)) {
			// 获取点赞历史
			List<ShopLablePraiseHistroy> histroys = lableInfoServiceImpl.getShoplabLablePraiseHistroys(shop_lable_id,
					member_no);
			// 获取点赞历史结束
			if (histroys.size() > 0) {
				msg.setStatus(MsgModel.WORRING);
				msg.setMessage("您已点赞");
				return msg;
			}
			num = lableInfoServiceImpl.insertShopLablePraiseHistroy(shop_lable_id, member_no);
		} else {
			num = lableInfoServiceImpl.deleteShopLablePraiseHistroy(shop_lable_id, member_no);
		}
		if (num > 0) {
			lableInfoServiceImpl.updateShopLablePraiseNum(shop_lable_id);
		} else {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("点赞失败");
		}
		return msg;
	}
}
