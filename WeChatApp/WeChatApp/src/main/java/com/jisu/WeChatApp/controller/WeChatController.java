package com.jisu.WeChatApp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSSClient;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.OSSUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;
import com.jisu.WeChatApp.tool.util.QrCodeUtil;
import com.jisu.WeChatApp.tool.util.WechatGetUtil;

@RequestMapping("/api/wx")
@RestController
public class WeChatController {
	private static Logger logger = LoggerFactory.getLogger(QrCodeUtil.class);

	/**
	 * 获取解密数据
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getEncryptedData")
	public MsgModel getEncryptedData(HttpServletRequest request, HttpServletResponse response) {
		String session_key = request.getParameter("session_key");
		String encryptedData = request.getParameter("encryptedData");
		String iv = request.getParameter("iv");
		JSONObject dataJson = WechatGetUtil.getUserInfo(encryptedData, session_key, iv);
		MsgModel msg = new MsgModel();
		msg.setStatus(MsgModel.SUCCESS);
		msg.setContext(dataJson);
		return msg;
	}

	/**
	 * 获取小程序二维码
	 * 
	 * @return
	 */
	@RequestMapping("/getWxaCode")
	public MsgModel getWeChatCode(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		// String shop_id = request.getParameter("shop_id");
		// String path = "pages/index/index?shop_id=" + shop_id; // 指向页面
		String path = request.getParameter("path");
		String scene = request.getParameter("scene");
		int width = 430;
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String access_token = WechatGetUtil.getAccessToken();
		if (StringUtils.isNotBlank(access_token)) {
			try {
				inputStream = QrCodeUtil.getQrCode(access_token, scene, path, width, false, null);
				String newFileName= System.currentTimeMillis() + "qr_code.png";
				String temp_path = PropertyUtil.getProperty("tempImage") + newFileName;
				File file = new File(temp_path);
				if (!file.exists()) {
					file.createNewFile();
				}
				outputStream = new FileOutputStream(file);
				int len = 0;
				byte[] buf = new byte[1024];
				while ((len = inputStream.read(buf, 0, 1024)) != -1) {
					outputStream.write(buf, 0, len);
				}
				outputStream.flush();
				OSSClient ossClient = OSSUtils.getOSSClient();
				OSSUtils.uploadObject2OSS(ossClient, file, PropertyUtil.getProperty("bucket_name"),
						PropertyUtil.getProperty("folder"));

				String result_url = OSSUtils.FILE_HOST+newFileName;
				msg.setContext(result_url);
				msg.setStatus(MsgModel.SUCCESS);
				file.delete();
			} catch (Exception e) {
				logger.error("调用小程序生成微信永久小程序码URL接口异常", e);
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			}
		}
		return msg;
	}
}
