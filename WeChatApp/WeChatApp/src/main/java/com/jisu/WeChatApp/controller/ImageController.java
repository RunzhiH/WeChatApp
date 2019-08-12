package com.jisu.WeChatApp.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.aliyun.oss.OSSClient;
import com.jisu.WeChatApp.dao.ShopInfoMapper;
import com.jisu.WeChatApp.pojo.ShopInfo;
import com.jisu.WeChatApp.service.impl.ImageServiceImpl;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.OSSUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

@RestController
@RequestMapping("/api/image")
public class ImageController {
	@Autowired
	private ShopInfoMapper shopInfoMapper;
	@Autowired
	ImageServiceImpl imageServiceImpl;

	@ResponseBody
	@RequestMapping("getShopPost")
	public MsgModel getShopPost(HttpServletRequest request) {
		MsgModel msg = new MsgModel();
		String shop_id = request.getParameter("shop_id");

		ShopInfo shop_info = shopInfoMapper.selectByPrimaryKey(shop_id);
		String post_image_url = shop_info.getPostImageUrl();
		if (StringUtils.isBlank(post_image_url)) {
			// 生成海报并上传到oss
			try {
				String qr_code_url = shop_info.getQrCodeUrl();
				BufferedImage bi = imageServiceImpl.createGoodsPoster(qr_code_url);
				post_image_url = System.currentTimeMillis() + "saved.png";
				File outputfile = new File(PropertyUtil.getProperty("tempImage") + post_image_url);
				ImageIO.write(bi, "png", outputfile);
				OSSClient ossClient = OSSUtils.getOSSClient();
				OSSUtils.uploadObject2OSS(ossClient, outputfile, PropertyUtil.getProperty("bucket_name"), PropertyUtil.getProperty("folder"));
				String oss_post_image_url = OSSUtils.FILE_HOST + post_image_url;
				
				shop_info.setPostImageUrl(oss_post_image_url);
				shopInfoMapper.updateByPrimaryKeySelective(shop_info);
				msg.setContext(oss_post_image_url);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			// 生成海报并上传到oss结束
		} else {
			msg.setContext(post_image_url);
		}
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
