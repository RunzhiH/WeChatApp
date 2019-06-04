package com.jisu.WeChatApp.tool.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;

public class OSSUtils {
	private final static Logger logger = LoggerFactory.getLogger(OSSUtils.class);
	private static String END_POINT = PropertyUtil.getProperty("end_point");
	private static String ACCESS_KEY_ID = PropertyUtil.getProperty("access_key_id");
	private static String ACCESS_KEY_SECRET = PropertyUtil.getProperty("access_key_secret");
	private static String BUCKET_NAME = PropertyUtil.getProperty("bucket_name");
	private static String FOLDER = PropertyUtil.getProperty("folder");
	// 文件访问域名
	public static String FILE_HOST = "http://"+BUCKET_NAME+"."+END_POINT+"/"+FOLDER;
	// 阿里云API的内或外网域名
	// 阿里云API的密钥Access Key ID
	// 阿里云API的密钥Access Key Secret
	// 阿里云API的bucket名称
	// 阿里云API的文件夹名称
	// 初始化属性
//	static {
//		END_POINT = OSSClientConstants.ENDPOINT;
//		ACCESS_KEY_ID = OSSClientConstants.ACCESS_KEY_ID;
//		ACCESS_KEY_SECRET = OSSClientConstants.ACCESS_KEY_SECRET;
//		BUCKET_NAME = OSSClientConstants.BACKET_NAME;
//		FOLDER = OSSClientConstants.FOLDER;
//	}

	/**
	 * 获取阿里云OSS客户端对象
	 * 
	 * @return ossClient
	 */
	public static OSSClient getOSSClient() {
		return new OSSClient(END_POINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
	}

	/**
	 * 创建存储空间
	 * 
	 * @param ossClient  OSS连接
	 * @param bucketName 存储空间
	 * @return
	 */
	public static String createBucketName(OSSClient ossClient, String bucketName) {
		// 存储空间
		final String bucketNames = bucketName;
		if (!ossClient.doesBucketExist(bucketName)) {
			// 创建存储空间
			Bucket bucket = ossClient.createBucket(bucketName);
			logger.info("创建存储空间成功");
			return bucket.getName();
		}
		return bucketNames;
	}

	/**
	 * 删除存储空间buckName
	 * 
	 * @param ossClient  oss对象
	 * @param bucketName 存储空间
	 */
	public static void deleteBucket(OSSClient ossClient, String bucketName) {
		ossClient.deleteBucket(bucketName);
		logger.info("删除" + bucketName + "Bucket成功");
	}

	/**
	 * 创建模拟文件夹
	 * 
	 * @param ossClient  oss连接
	 * @param bucketName 存储空间
	 * @param folder     模拟文件夹名如"qj_nanjing/"
	 * @return 文件夹名
	 */
	public static String createFolder(OSSClient ossClient, String bucketName, String folder) {
		// 文件夹名
		final String keySuffixWithSlash = folder;
		// 判断文件夹是否存在，不存在则创建
		if (!ossClient.doesObjectExist(bucketName, keySuffixWithSlash)) {
			// 创建文件夹
			ossClient.putObject(bucketName, keySuffixWithSlash, new ByteArrayInputStream(new byte[0]));
			logger.info("创建文件夹成功");
			// 得到文件夹名
			OSSObject object = ossClient.getObject(bucketName, keySuffixWithSlash);
			String fileDir = object.getKey();
			return fileDir;
		}
		return keySuffixWithSlash;
	}

	/**
	 * 根据key删除OSS服务器上的文件
	 * 
	 * @param ossClient  oss连接
	 * @param bucketName 存储空间
	 * @param folder     模拟文件夹名 如"qj_nanjing/"
	 * @param key        Bucket下的文件的路径名+文件名 如："upload/cake.jpg"
	 */
	public static void deleteFile(OSSClient ossClient, String bucketName, String folder, String key) {
		ossClient.deleteObject(bucketName, folder + key);
		logger.info("删除" + bucketName + "下的文件" + folder + key + "成功");
	}

	/**
	 * 上传图片至OSS
	 * 
	 * @param ossClient  oss连接
	 * @param file       上传文件（文件全路径如：D:\\image\\cake.jpg）
	 * @param bucketName 存储空间
	 * @param folder     模拟文件夹名 如"qj_nanjing/"
	 * @return String 返回的唯一MD5数字签名
	 */
	public static String uploadObject2OSS(OSSClient ossClient, File file, String bucketName, String folder) {
		String resultStr = null;
		try {
			// 以输入流的形式上传文件
			InputStream is = new FileInputStream(file);
			// 文件名
			String fileName = file.getName();
			// 文件大小
			Long fileSize = file.length();
			// 创建上传Object的Metadata
			ObjectMetadata metadata = new ObjectMetadata();
			// 上传的文件的长度
			metadata.setContentLength(is.available());
			// 指定该Object被下载时的网页的缓存行为
			metadata.setCacheControl("no-cache");
			// 指定该Object下设置Header
			metadata.setHeader("Pragma", "no-cache");
			// 指定该Object被下载时的内容编码格式
			metadata.setContentEncoding("utf-8");
			// 文件的MIME，定义文件的类型及网页编码，决定浏览器将以什么形式、什么编码读取文件。如果用户没有指定则根据Key或文件名的扩展名生成，
			metadata.setObjectAcl(CannedAccessControlList.PublicRead); // 指定文件操作权限为公共读
			// 如果没有扩展名则填默认值application/octet-stream
			metadata.setContentType(getContentType(fileName));
			// 指定该Object被下载时的名称（指示MINME用户代理如何显示附加的文件，打开或下载，及文件名称）
			metadata.setContentDisposition("filename/filesize=" + fileName + "/" + fileSize + "Byte.");
			// 上传文件 (上传文件流的形式)
			PutObjectResult putResult = ossClient.putObject(bucketName, folder + fileName, is, metadata);
			// 解析结果
			resultStr = putResult.getETag();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("上传阿里云OSS服务器异常." + e.getMessage(), e);
		}
		return resultStr;
	}

	/**
	 * 通过文件名判断并获取OSS服务文件上传时文件的contentType
	 * 
	 * @param fileName 文件名
	 * @return 文件的contentType
	 */
	public static String getContentType(String fileName) {
		// 文件的后缀名
		String fileExtension = fileName.substring(fileName.lastIndexOf("."));
		if (".bmp".equalsIgnoreCase(fileExtension)) {
			return "image/bmp";
		}
		if (".gif".equalsIgnoreCase(fileExtension)) {
			return "image/gif";
		}
		if (".jpeg".equalsIgnoreCase(fileExtension) || ".jpg".equalsIgnoreCase(fileExtension) || ".png".equalsIgnoreCase(fileExtension)) {
			return "image/jpeg";
		}
		if (".html".equalsIgnoreCase(fileExtension)) {
			return "text/html";
		}
		if (".txt".equalsIgnoreCase(fileExtension)) {
			return "text/plain";
		}
		if (".vsd".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.visio";
		}
		if (".ppt".equalsIgnoreCase(fileExtension) || "pptx".equalsIgnoreCase(fileExtension)) {
			return "application/vnd.ms-powerpoint";
		}
		if (".doc".equalsIgnoreCase(fileExtension) || "docx".equalsIgnoreCase(fileExtension)) {
			return "application/msword";
		}
		if (".xml".equalsIgnoreCase(fileExtension)) {
			return "text/xml";
		}
		// 默认返回类型
		return "image/jpeg";
	}

	public static String getUrl(OSSClient ossClient, String key) {
		// 设置URL过期时间为10年 3600l* 1000*24*365*10
		Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
		// 生成URL
		URL url = ossClient.generatePresignedUrl(BUCKET_NAME, key, expiration);
		if (url != null) {
			return url.toString();
		}
		return null;
	}

	/**
	 * 读取文件内容
	 * 
	 * @param file
	 * @return
	 */
	public static String readfile(File file) {
		InputStream input = null;
		try {
			input = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		byte[] bytes = new byte[1024];
		try {
			for (int n; (n = input.read(bytes)) != -1;) {
				buffer.append(new String(bytes, 0, n, "GBK"));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		// System.out.println(buffer);
		return buffer.toString();
	}

	/**
	 * 从oss上获取文件
	 * 
	 * @param ossClient
	 * @param oss_path
	 * @param new_File_path
	 * @return
	 */
	public static File downLoad(OSSClient ossClient, String oss_path, String new_File_path) {
		if(oss_path.indexOf(BUCKET_NAME)>0) {
			oss_path=oss_path.replace(FILE_HOST, "");
		}
		File new_file = new File(new_File_path);
		ossClient.getObject(new GetObjectRequest(BUCKET_NAME, oss_path), new_file);
		return new_file;
	}
// 测试
//	public static void main(String[] args) {
//		// 初始化OSSClient
//		OSSClient ossClient = OSSUtils.getOSSClient();
//		// 上传文件
//		String files = "C:\\tempImage\\20190417141848.jpg";
//		String[] file = files.split(",");
//		for (String filename : file) {
//			// System.out.println("filename:"+filename);
//			File filess = new File(filename);
//			String md5key = OSSUtils.uploadObject2OSS(ossClient, filess, BUCKET_NAME, FOLDER);
//			logger.info("上传后的文件MD5数字唯一签名:" + md5key);
//			// 上传后的文件MD5数字唯一签名:40F4131427068E08451D37F02021473A
//		}
//	}
}