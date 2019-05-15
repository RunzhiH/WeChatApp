package com.jisu.WeChatApp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.aliyun.oss.OSSClient;
import com.jisu.WeChatApp.tool.util.MsgModel;
import com.jisu.WeChatApp.tool.util.OSSUtils;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

@RequestMapping("/api/upload")
@RestController
public class UploadController {
	/**
	 * * 方法描述:文件上传 可上传多个文件 * *
	 * 
	 * @param request *
	 * @param response *
	 * @return
	 */
	@RequestMapping(value = "/uploadImages", method = { RequestMethod.POST })
	public MsgModel upload(HttpServletRequest request, HttpServletResponse response) {
		MsgModel msg = new MsgModel();
		List<String> result_url_arr = new ArrayList<String>();
		;
		OSSClient ossClient = OSSUtils.getOSSClient();
		try {
			// 创建一个通用的多部分解析器
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
					request.getSession().getServletContext());
			// 判断 request 是否有文件上传,即多部分请求
			if (multipartResolver.isMultipart(request)) {
				// 转换成多部分request
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				// 取得request中的所有文件名
				Iterator<String> iter = multiRequest.getFileNames();
				while (iter.hasNext()) {
					// 取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if (file != null) {
						// 取得当前上传文件的文件名称
						String fileName = file.getOriginalFilename();
						// 如果名称不为空,说明该文件存在，否则说明该文件不存在
						String prefix = fileName.substring(fileName.lastIndexOf("."));
						Random random = new Random();
						Integer randomFileName = random.nextInt(1000);
						String newFileName = System.currentTimeMillis() + randomFileName + prefix;// 生成保存在服务器的图片名称，延用原后缀名

						File dest = new File(PropertyUtil.getProperty("tempImage") + newFileName);
						if (!dest.getParentFile().exists()) {
							dest.getParentFile().mkdirs();
						}
						try {
							file.transferTo(dest);
						} catch (IOException e) {
							e.printStackTrace();
						}
						OSSUtils.uploadObject2OSS(ossClient, dest, PropertyUtil.getProperty("bucket_name"),
								PropertyUtil.getProperty("folder"));

						String result_url = OSSUtils.FILE_HOST+newFileName;
						result_url_arr.add(result_url);
						dest.delete();
					}
				}
			}
			msg.setContext(result_url_arr);
			msg.setStatus(MsgModel.SUCCESS);
		} catch (Exception e) {
			msg.setMessage("system error");
			msg.setStatus(MsgModel.ERROR);
			e.printStackTrace();
		}
		return msg;
	}

	/**
	 * 获取oss文本内容
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("getOssContext")
	public MsgModel getOssContext(HttpServletRequest request, HttpServletResponse response) {
		String oss_path = request.getParameter("oss_path");
		// 获取oss实例
		OSSClient ossClient = OSSUtils.getOSSClient();
		String new_file_name = PropertyUtil.getProperty("tempImage") + "context" + oss_path.substring(oss_path.lastIndexOf("."));
		File file = OSSUtils.downLoad(ossClient, oss_path, new_file_name);
		String context_str = OSSUtils.readfile(file);
		MsgModel msg = new MsgModel();
		msg.setContext(context_str);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}
}
