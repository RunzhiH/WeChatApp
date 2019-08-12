package com.jisu.WeChatApp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSSClient;
import com.jisu.WeChatApp.dao.ExperienceInfoMapper;
import com.jisu.WeChatApp.dao.OrderInfoMapper;
import com.jisu.WeChatApp.dao.ShopServerMapper;
import com.jisu.WeChatApp.pojo.ExperPraiseHistroy;
import com.jisu.WeChatApp.pojo.ExperienceInfo;
import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.ShopServer;
import com.jisu.WeChatApp.service.impl.ExperServiceImpl;
import com.jisu.WeChatApp.service.impl.OrderInfoServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.OSSUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

@RequestMapping("/api/exper")
@RestController
public class ExperienceController {
	@Autowired
	private ExperienceInfoMapper experienceInfoMapper;
	@Autowired
	private ExperServiceImpl experServiceImpl;
	@Autowired
	private OrderInfoMapper orderInfoMapper;
	@Autowired
	private OrderInfoServiceImpl orderInfoServiceImpl;
	@Autowired
	private ShopServerMapper shopServerMapper;

	/**
	 * 保存客户体验
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "saveExperienceInfo", method = RequestMethod.POST)
	public MsgModel saveExperienceInfo(HttpServletRequest request, HttpServletResponse response) {
		String exper_context = request.getParameter("exper_context");
		String member_no = request.getParameter("member_no");
		String title = request.getParameter("title");
		String exper_id = request.getParameter("exper_id");
		String exper_photo = request.getParameter("exper_photo");
		String exper_desc = request.getParameter("exper_desc");
		String server_name = request.getParameter("server_name");
		// 将富文本内容上传
		String result_url = "";
		if (StringUtils.isNotBlank(exper_context)) {
			FileOutputStream fos = null;
			try {
				OSSClient ossClient = OSSUtils.getOSSClient();
				String prefix = ".HTML";
				Random random = new Random();
				Integer randomFileName = random.nextInt(1000);
				String newFileName = System.currentTimeMillis() + randomFileName + prefix;// 生成保存在服务器的图片名称，延用原后缀名
				File dest = new File(PropertyUtil.getProperty("tempImage") + newFileName);
				fos = new FileOutputStream(dest);
				byte[] context_bytes = exper_context.getBytes();
				fos.write(context_bytes);
				OSSUtils.uploadObject2OSS(ossClient, dest, PropertyUtil.getProperty("bucket_name"), PropertyUtil.getProperty("folder"));
				result_url = OSSUtils.FILE_HOST + newFileName;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			} finally {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// 将富文本内容上传 结束
		ExperienceInfo experienceInfo = new ExperienceInfo();
		experienceInfo.setExperiencePhoto(exper_photo);
		experienceInfo.setExperienceDesc(exper_desc);
		experienceInfo.setMemberNo(member_no);
		experienceInfo.setTextUrl(result_url);
		experienceInfo.setTitle(title);
		experienceInfo.setExperIdea(request.getParameter("exper_idea"));
		experienceInfo.setServerName(server_name);
		experienceInfo.setOrderId(request.getParameter("order_id"));
		experienceInfo.setServerBeforePhoto(request.getParameter("server_before_photo"));
		experienceInfo.setServerAfterPhoto(request.getParameter("server_after_photo"));
		OrderInfo orderInfo = orderInfoMapper.selectByPrimaryKey(request.getParameter("order_id"));
		experienceInfo.setShopServerId(orderInfo.getShopServerId());
		ShopServer shop_server = shopServerMapper.selectByPrimaryKey(orderInfo.getShopServerId());
		experienceInfo.setServerClassId1(shop_server.getServerClassId());
		experienceInfo.setServerClassId2(request.getParameter("server_class_id2"));
		experienceInfo.setServerClassId3(request.getParameter("server_class_id3"));
		experienceInfo.setServerClassId4(request.getParameter("server_class_id4"));
		experienceInfo.setServerClassId5(request.getParameter("server_class_id5"));
		experienceInfo.setExperPrice(new BigDecimal(request.getParameter("exper_price")));
		int save_num = 0;
		if (StringUtils.isNotBlank(exper_id)) {
			experienceInfo.setExperienceId(exper_id);
			save_num = experienceInfoMapper.updateByPrimaryKeySelective(experienceInfo);
		} else {
			experienceInfo.setExperienceId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
			experienceInfo.setCreateTime(new Date());
			save_num = experienceInfoMapper.insertSelective(experienceInfo);
		}
		MsgModel msg = new MsgModel();
		if (save_num > 0) {
			if (StringUtils.isBlank(exper_id)) {
				String order_id = request.getParameter("order_id");

				int num = orderInfoServiceImpl.updateOrderStatus(order_id, 3);
				if (num > 0) {
					// 结算收益
					orderInfoServiceImpl.settlementOrder(order_id);
					// 结束收益结束
				}
			}
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 获取体验列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getExperListByCondition")
	public MsgModel getExperListByCondition(HttpServletRequest request, HttpServletResponse response) {
		String begin = request.getParameter("begin");
		String end = request.getParameter("end");
		String member_no = request.getParameter("member_no");
		String server_class_id = request.getParameter("server_class_id");
		String server_member_no= request.getParameter("server_member_no");
		if (StringUtils.isNotBlank(end) && StringUtils.isBlank(begin)) {
			begin = "0";
		}
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("begin", begin);
		condition.put("end", end);
		condition.put("member_no", member_no);
		condition.put("server_member_no", server_member_no);
		condition.put("server_class_id", server_class_id);
		List<Map<String, String>> exper_list = experServiceImpl.getExperList(condition);
		MsgModel msg = new MsgModel();
		msg.setContext(exper_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	/**
	 * 用户点赞体验
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "doExperLike", method = RequestMethod.POST)
	public MsgModel doExperLike(HttpServletRequest request, HttpServletResponse response) {
		String member_no = request.getParameter("member_no");
		String exper_id = request.getParameter("exper_id");
		String is_like = request.getParameter("is_like");
		int num = 0;
		MsgModel msg = new MsgModel();
		if ("0".equals(is_like)) {
			// 是否已点赞
			List<ExperPraiseHistroy> histroys = experServiceImpl.getExperPraiseHistroy(exper_id, member_no);
			if (histroys.size() > 0) {
				msg.setMessage("已点赞");
				msg.setStatus(MsgModel.ERROR);
				return msg;
			} else {
				// 插入点赞历史
				num = experServiceImpl.insertExperLikeHistroy(exper_id, member_no);
				// 插入点赞历史结束
			}
		} else {
			num = experServiceImpl.deleteExperLikeHistroy(exper_id, member_no);
		}
		if (num > 0) {
			// 点赞体验
			experServiceImpl.doExperLike(exper_id, is_like);
			// 点赞体验结束
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
			msg.setMessage("点赞失败");
		}
		return msg;
	}

	@RequestMapping("getExperInfo")
	public MsgModel getExperInfo(HttpServletRequest request) {
		String exper_id = request.getParameter("exper_id");
		Map<String, Object> exper_info = experServiceImpl.getExperInfo(exper_id);
		MsgModel msg = new MsgModel();
		msg.setContext(exper_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

	@RequestMapping("getExperInfoByOrderId")
	public MsgModel getExperInfoByOrderId(HttpServletRequest request) {
		String order_id = request.getParameter("order_id");
		Map<String, String> exper_info = experServiceImpl.getExperInfoByOrderId(order_id);
		MsgModel msg = new MsgModel();
		msg.setContext(exper_info);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
