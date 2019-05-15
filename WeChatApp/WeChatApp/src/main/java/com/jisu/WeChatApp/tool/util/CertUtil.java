package com.jisu.WeChatApp.tool.util;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.ssl.SSLContexts;

/**
 * 
 * 加载证书的类
 * 
 * @author
 * 
 * @since 2017/08/16
 * 
 */

@SuppressWarnings("deprecation")

public class CertUtil {

	private static WeixinConfigUtils config = new WeixinConfigUtils();

	/**
	 * 
	 * 加载证书
	 * 
	 */

	public static SSLConnectionSocketFactory initCert() throws Exception {

		String path = "";

		FileInputStream instream = null;

		KeyStore keyStore = KeyStore.getInstance("PKCS12");

		instream = new FileInputStream(new File(path));

		keyStore.load(instream, config.mch_id.toCharArray());

		if (null != instream) {

			instream.close();

		}

		SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, config.mch_id.toCharArray()).build();

		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
				SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

		return sslsf;

	}

}
