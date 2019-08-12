package com.jisu.WeChatApp.tool.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyStore;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.core.util.UuidUtil;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.jisu.WeChatApp.pojo.Transfers;

public class PayUtils {
	private final static Logger logger = LoggerFactory.getLogger(PayUtils.class);
	/**
	 * * 签名字符串 *
	 * 
	 * @param text需要签名的字符串
	 * @param key               密钥
	 * @param input_charset编码格式
	 * @return 签名结果
	 */
	public static String sign(String text, String key, String input_charset) {
		text = text + "&key=" + key;
		return DigestUtils.md5Hex(getContentBytes(text, input_charset));
	}

	/**
	 * * 签名字符串 *
	 * 
	 * @param text需要签名的字符串 *
	 * @param sign          签名结果 *
	 * @param key密钥 *
	 * @param input_charset 编码格式 *
	 * @return 签名结果
	 */
	public static boolean verify(String text, String sign, String key, String input_charset) {
		text = text + key;
		String mysign = DigestUtils.md5Hex(getContentBytes(text, input_charset));
		if (mysign.equals(sign)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * *
	 * 
	 * @param content *
	 * @param charset *
	 * @return *
	 * @throws SignatureException
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] getContentBytes(String content, String charset) {
		if (charset == null || "".equals(charset)) {
			return content.getBytes();
		}
		try {
			return content.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:" + charset);
		}
	}

	private static boolean isValidChar(char ch) {
		if ((ch >= '0' && ch <= '9') || (ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z'))
			return true;
		if ((ch >= 0x4e00 && ch <= 0x7fff) || (ch >= 0x8000 && ch <= 0x952f))
			return true;// 简体中文汉字编码
		return false;
	}

	/**
	 * * 除去数组中的空值和签名参数 *
	 * 
	 * @param sArray 签名参数组 *
	 * @return 去掉空值与签名参数后的新签名参数组
	 */
	public static Map<String, String> paraFilter(Map<String, String> sArray) {
		Map<String, String> result = new HashMap<String, String>();
		if (sArray == null || sArray.size() <= 0) {
			return result;
		}
		for (String key : sArray.keySet()) {
			String value = sArray.get(key);
			if (value == null || value.equals("") || key.equalsIgnoreCase("sign") || key.equalsIgnoreCase("sign_type")) {
				continue;
			}
			result.put(key, value);
		}
		return result;
	}

	/**
	 * * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串 *
	 * 
	 * @param params 需要排序并参与字符拼接的参数组
	 * @return 拼接后字符串
	 */
	public static String createLinkString(Map<String, String> params) {
		List<String> keys = new ArrayList<String>(params.keySet());
		Collections.sort(keys);
		String prestr = "";
		for (int i = 0; i < keys.size(); i++) {
			String key = keys.get(i);
			String value = params.get(key);
			if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
				prestr = prestr + key + "=" + value;
			} else {
				prestr = prestr + key + "=" + value + "&";
			}
		}
		return prestr;
	}

	/**
	 * * *
	 * 
	 * @param requestUrl请求地址 *
	 * @param requestMethod请求方法 *
	 * @param outputStr参数
	 */
	public static String httpRequest(String requestUrl, String requestMethod, String outputStr) { // 创建SSLContext
		StringBuffer buffer = null;
		try {
			URL url = new URL(requestUrl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(requestMethod);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.connect(); // 往服务器端写内容
			if (null != outputStr) {
				OutputStream os = conn.getOutputStream();
				os.write(outputStr.getBytes("utf-8"));
				os.close();
			} // 读取服务器端返回的内容
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			buffer = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				buffer.append(line);
			}

			br.close();
		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。 *
	 * 
	 * @param strxml *
	 * @return
	 * @throws JDOMException *
	 * @throws IOException
	 */
	public static Map doXMLParse(String strxml) throws Exception {
		if (null == strxml || "".equals(strxml)) {
			return null;
		}
		/* ============= !!!!注意，修复了微信官方反馈的漏洞，更新于2018-10-16 =========== */ try {
			Map<String, String> data = new HashMap<String, String>(); // TODO 在这里更换
			DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
			documentBuilderFactory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-general-entities", false);
			documentBuilderFactory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
			documentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
			documentBuilderFactory.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
			documentBuilderFactory.setXIncludeAware(false);
			documentBuilderFactory.setExpandEntityReferences(false);
			InputStream stream = new ByteArrayInputStream(strxml.getBytes("UTF-8"));
			org.w3c.dom.Document doc = documentBuilderFactory.newDocumentBuilder().parse(stream);
			doc.getDocumentElement().normalize();
			NodeList nodeList = doc.getDocumentElement().getChildNodes();
			for (int idx = 0; idx < nodeList.getLength(); ++idx) {
				Node node = nodeList.item(idx);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					org.w3c.dom.Element element = (org.w3c.dom.Element) node;
					data.put(element.getNodeName(), element.getTextContent());
				}
			}
			try {
				stream.close();
			} catch (Exception ex) { // do nothing
			}
			return data;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * * 获取子结点的xml *
	 * 
	 * @param children *
	 * @return String
	 */
	public static String getChildrenText(List children) {
		StringBuffer sb = new StringBuffer();
		if (!children.isEmpty()) {
			Iterator it = children.iterator();
			while (it.hasNext()) {
				Element e = (Element) it.next();
				String name = e.getName();
				String value = e.getTextNormalize();
				List list = e.getChildren();
				sb.append("<" + name + ">");
				if (!list.isEmpty()) {
					sb.append(getChildrenText(list));
				}
				sb.append(value);
				sb.append("</" + name + ">");
			}
		}
		return sb.toString();
	}

	public static InputStream String2Inputstream(String str) {
		return new ByteArrayInputStream(str.getBytes());
	}

	/** * IpUtils工具类方法 * 获取真实的ip地址 * @param request * @return */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) { // 多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 微信支付
	 * 
	 * @param request
	 * @param nonce_str    订单编号
	 * @param body         商品名称
	 * @param out_trade_no 商户订单号
	 * @param total_fee    支付金额
	 * @param notify_url   回调地址
	 * @param open_id      用户open_id
	 * @return
	 */
	public static String wxPay(HttpServletRequest request, String nonce_str, String body, String out_trade_no, String total_fee, String notify_url, String open_id) {
		String appid = PropertyUtil.getProperty("wx.appid");
		String mch_key = PropertyUtil.getProperty("wx.mch_key");
		String mch_id = PropertyUtil.getProperty("wx.mch_id");
		String TRADETYPE = "JSAPI";
		String pay_url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
		try { // 生成的随机字符串
				// 商品名称
				// 获取客户端的ip地址
			String spbill_create_ip = PayUtils.getIpAddr(request);
			// 组装参数，用户生成统一下单接口的签名
			Map<String, String> packageParams = new HashMap<String, String>();
			packageParams.put("appid", appid);
			packageParams.put("mch_id", mch_id);
			packageParams.put("nonce_str", nonce_str);
			packageParams.put("body", body);
			packageParams.put("out_trade_no", out_trade_no);// 商户订单号
			packageParams.put("total_fee", total_fee);// 支付金额，这边需要转成字符串类型，否则后面的签名会失败
			packageParams.put("spbill_create_ip", spbill_create_ip);
			packageParams.put("notify_url", notify_url);// 支付成功后的回调地址
			packageParams.put("trade_type", TRADETYPE);// 支付方式
			packageParams.put("openid", open_id);
			String prestr = PayUtils.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
			// MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
			String mysign = PayUtils.sign(prestr, mch_key, "utf-8").toUpperCase();
			// 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
			String xml = "<xml>" 
					+ "<appid>" + appid + "</appid>" 
					+ "<body><![CDATA[" + body + "]]></body>" 
					+ "<mch_id>" + mch_id + "</mch_id>" 
					+ "<nonce_str>" + nonce_str + "</nonce_str>" 
					+ "<notify_url>" + notify_url + "</notify_url>"
					+ "<openid>" + open_id + "</openid>" 
					+ "<out_trade_no>" + out_trade_no + "</out_trade_no>" 
					+ "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>" 
					+ "<total_fee>" + total_fee + "</total_fee>" 
					+ "<trade_type>" + TRADETYPE + "</trade_type>" 
					+ "<sign>" + mysign + "</sign>" 
					+ "</xml>";
			logger.debug("调试模式_统一下单接口 请求XML数据：" + xml);
			// 调用统一下单接口，并接受返回的结果
			String result = PayUtils.httpRequest(pay_url, "POST", xml);
			logger.debug("调试模式_统一下单接口 返回XML数据：" + result);

			return result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	
	
	// 微信的参数
	private WeixinConfigUtils config = new WeixinConfigUtils();

	/**
	 * 
	 * 微信提现（企业付款）
	 * 
	 */
	public static String weixinWithdraw(String openId, String ip, String money, String desc) {
		if (StringUtils.isNotBlank(money) && StringUtils.isNotBlank(ip) && StringUtils.isNotBlank(openId)) {
			// 参数组
			String url = "";
			String appid = PropertyUtil.getProperty("wx.appid");
			String mch_id = PropertyUtil.getProperty("wx.mch_id");
			String nonce_str = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			// 是否校验用户姓名 NO_CHECK：不校验真实姓名 FORCE_CHECK：强校验真实姓名
			String checkName = "NO_CHECK";
			// 等待确认转账金额,ip,openid的来源
			Integer amount = Integer.valueOf(money);
			String spbill_create_ip = ip;
			String partner_trade_no = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
			// 描述
			// String desc = "健康由我医师助手提现" + amount / 100 + "元";
			// 参数：开始生成第一次签名
			
			// 构造签名的map
			SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();
			parameters.put("appid", appid);
			parameters.put("mch_id", mch_id);
			parameters.put("partner_trade_no", partner_trade_no);
			parameters.put("nonce_str", nonce_str);
			parameters.put("openId", openId);
			parameters.put("checkName", checkName);
			parameters.put("amount", amount);
			parameters.put("spbill_create_ip", spbill_create_ip);
			parameters.put("desc", desc);
			String sign = WXSignUtils.createSign("UTF-8", parameters);
			Transfers transfers = new Transfers();
			transfers.setAmount(amount);
			transfers.setCheck_name(checkName);
			transfers.setDesc(desc);
			transfers.setMch_appid(appid);
			transfers.setMchid(mch_id);
			transfers.setNonce_str(nonce_str);
			transfers.setOpenid(openId);
			transfers.setPartner_trade_no(partner_trade_no);
			transfers.setSign(sign);
			transfers.setSpbill_create_ip(spbill_create_ip);
			String xmlInfo = HttpXmlUtils.transferXml(transfers);
			try {
				CloseableHttpResponse response = HttpsUtil.Post(url, xmlInfo, true);
				String transfersXml = EntityUtils.toString(response.getEntity(), "utf-8");
				Map<String, String> transferMap = HttpXmlUtils.parseRefundXml(transfersXml);
				if (transferMap.size() > 0) {
					if (transferMap.get("result_code").equals("SUCCESS") && transferMap.get("return_code").equals("SUCCESS")) {
						// 成功需要进行的逻辑操作，
						return "转账成功";
					}else {
						return transferMap.get("return_msg");
					}
				}
				System.out.println("成功");
			} catch (Exception e) {
				// log.error(e.getMessage());
				// throw new Exception(this, "企业付款异常" + e.getMessage());
				return "企业付款异常:" + e.getMessage();
			}
		} else {
			System.out.println("失败");
		}
		return null;
	}

	public static Map<String, String> wxRefund(String refundid,String orderId, String total_fee, String refund_fee, String refund_desc) {
		String appid = PropertyUtil.getProperty("wx.appid");
		String mch_key = PropertyUtil.getProperty("wx.mch_key");
		String mch_id = PropertyUtil.getProperty("wx.mch_id");
		String ssl_path=PropertyUtil.getProperty("weixin.ssl.path");
		Map<String, String> result = new HashMap<String, String>();
		if(StringUtils.isBlank(refundid)) {
			refundid = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		}
		String nonce_str = DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null);
		/*-----  1.生成预支付订单需要的的package数据-----*/
		SortedMap<String, String> packageParams = new TreeMap<String, String>();
		packageParams.put("appid", appid);
		packageParams.put("mch_id", mch_id);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", mch_id);
		packageParams.put("out_trade_no", orderId);
		packageParams.put("out_refund_no", refundid);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		packageParams.put("refund_desc", refund_desc);
		/*----2.根据package生成签名sign---- */
		String prestr = PayUtils.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		// MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
		String sign = PayUtils.sign(prestr, mch_key, "utf-8").toUpperCase();
		/*----3.拼装需要提交到微信的数据xml---- */
		String xml = "<xml>" 
					+ "<appid>" + appid + "</appid>" 
					+ "<mch_id>" + mch_id + "</mch_id>" 
					+ "<nonce_str>" + nonce_str + "</nonce_str>" 
					+ "<op_user_id>" + mch_id + "</op_user_id>" 
					+ "<out_trade_no>" + orderId + "</out_trade_no>" 
					+ "<out_refund_no>" + refundid + "</out_refund_no>" 
					+ "<refund_fee>" + refund_fee + "</refund_fee>" 
					+ "<total_fee>" + total_fee + "</total_fee>"
					+ "<refund_desc>" + refund_desc + "</refund_desc>"
					+ "<sign>" + sign + "</sign>"
					+ "</xml>";
		try {
			/*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(new File(ssl_path));
			try {
				keyStore.load(instream, mch_id.toCharArray());
			} finally {
				instream.close();
			}
			// Trust own CA and all self-signed certs
			SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, mch_id.toCharArray()).build();
			// Allow TLSv1 protocol only
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			/*----5.发送数据到微信的退款接口---- */
			String refund_url = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			String jsonStr = PayUtils.httpRequest(refund_url, "POST", xml);
			// logger.info(jsonStr);

			Map map = doXMLParse(jsonStr);
			if ("success".equalsIgnoreCase((String) map.get("return_code"))) {
				// logger.info("退款成功");
				result.put("returncode", "ok");
				result.put("returninfo", "退款成功");
			} else {
				// logger.info("退款失败");
				result.put("returncode", "error");
				result.put("returninfo", "退款失败");
			}
		} catch (Exception e) {
			// logger.info("退款失败");
			result.put("returncode", "error");
			result.put("returninfo", "退款失败");
		}
		return result;

	}

}
