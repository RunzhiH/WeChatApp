package com.jisu.WeChatApp.tool.util;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class QrCodeUtil {
	private static Logger logger = LoggerFactory.getLogger(QrCodeUtil.class);

	/**
	 * 获取小程序码
	 * 
	 * @param access_token
	 * @param scene        最大32个可见字符，只支持数字，大小写英文以及部分特殊字符：!#$&'()*+,/:;=?@-._~，其它字符请自行编码为合法字符（因不支持%，中文无法使用
	 *                     urlencode 处理，请使用其他编码方式
	 * @return 返回路径
	 */
	public static InputStream getQrCode(String access_token, String path, int width, boolean auto_color,
			JSONObject line_color) {
		RestTemplate rest = new RestTemplate();
		InputStream inputStream = null;
		if (StringUtils.isNotBlank(access_token)) {
			JSONObject param = new JSONObject();
			param.put("path", path);
			param.put("width", width);
			param.put("auto_color", auto_color);
			if (!auto_color) {
				param.put("line_color", line_color);
			}
			logger.info("调用生成微信URL接口传参:" + param);
			MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
			HttpEntity requestEntity = new HttpEntity(JSON.toJSONString(param), headers);
			ResponseEntity<byte[]> entity = rest.exchange(WeChatURLUtil.getWxaCodeunLimit(), HttpMethod.POST,
					requestEntity, byte[].class, new Object[0]);
			logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
			byte[] result = entity.getBody();
			logger.info(Base64.encodeBase64String(result));
			inputStream = new ByteArrayInputStream(result);

		}
		return inputStream;
	}
}
