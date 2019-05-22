package com.jisu.WeChatApp.tool.util;

import java.util.Map;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

/**
 * Created on 17/6/7. 短信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过
 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可) 工程依赖了2个jar包(存放在工程的libs目录下)
 * 1:aliyun-java-sdk-core.jar 2:aliyun-java-sdk-dysmsapi.jar
 *
 * 备注:Demo工程编码采用UTF-8 国际短信发送请勿参照此DEMO
 */
public class SmsUtil {
	// 产品名称:云通信短信API产品,开发者无需替换
	static final String product = "Dysmsapi";
	// 产品域名,开发者无需替换
	static final String domain = "dysmsapi.aliyuncs.com";

	// TODO 此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
	static final String accessKeyId = "LTAIG9evTM5JWLSW";
	static final String accessKeySecret = "bYADQgb1UFtLV1UURWU3U3IUDW1G3u";

	/**
	 * Created on 17/6/7.信API产品的DEMO程序,工程中包含了一个SmsDemo类，直接通过 短
	 * 执行main函数即可体验短信产品API功能(只需要将AK替换成开通了云通信-短信产品功能的AK即可) 工程依赖了2个jar包(存放在工程的libs目录下)
	 * 1:aliyun-java-sdk-core.jar 2:aliyun-java-sdk-dysmsapi.jar
	 * <p>
	 * 备注:Demo工程编码采用UTF-8 国际短信发送请勿参照此DEMO
	 */

	public static CommonResponse sendSmsCode(String mobile, String code) throws ClientException {

		// 可自助调整超时时间
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");

		// 初始化acsClient,暂不支持region化
		DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);

		CommonRequest request = new CommonRequest();
		// request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("PhoneNumbers", mobile);
		request.putQueryParameter("SignName", "大手艺");
		request.putQueryParameter("TemplateCode", "SMS_165690765");
		request.putQueryParameter("TemplateParam", "{\"code\":'" + code + "'}");
		CommonResponse response = null;
		try {
			response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return response;
	}

	public static CommonResponse sendSmsRemind(Map<String, String> sendMsgContext) throws ClientException {

		// 初始化acsClient,暂不支持region化
		DefaultProfile profile = DefaultProfile.getProfile("default", accessKeyId, accessKeySecret);
		IAcsClient client = new DefaultAcsClient(profile);
		String mobile=sendMsgContext.get("phone");
		String order_code=sendMsgContext.get("order_code");
		CommonRequest request = new CommonRequest();
		// request.setProtocol(ProtocolType.HTTPS);
		request.setMethod(MethodType.POST);
		request.setDomain("dysmsapi.aliyuncs.com");
		request.setVersion("2017-05-25");
		request.setAction("SendSms");
		request.putQueryParameter("PhoneNumbers", mobile);
		request.putQueryParameter("SignName", "大手艺");
		request.putQueryParameter("TemplateCode", "SMS_165690765");
		request.putQueryParameter("TemplateParam", "{\"order_code\":'" + order_code + "'}");
		CommonResponse response = null;
		try {
			response = client.getCommonResponse(request);
			System.out.println(response.getData());
		} catch (ServerException e) {
			e.printStackTrace();
		} catch (ClientException e) {
			e.printStackTrace();
		}
		return response;
	}

//	public static void main(String[] args) throws ClientException, InterruptedException {
//
//		// 发短信
//		CommonResponse response = sendSmsCode("15958243735", "698767");
//		System.out.println("短信接口返回的数据----------------");
//		System.out.println("DATA=" + response.getData());
////		System.out.println("Message=" + response.getMessage());
////		System.out.println("RequestId=" + response.getRequestId());
////		System.out.println("BizId=" + response.getBizId());
//
//	}
}
