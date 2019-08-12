package com.jisu.WeChatApp.service.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.service.ImageService;
import com.jisu.WeChatApp.tool.util.PropertyUtil;

@Service("ImageServiceImpl")
public class ImageServiceImpl implements ImageService {
	/**
	 * 生成海报
	 *
	 * @param dto
	 * @param currentUser
	 * @return
	 */
	@Override
	public BufferedImage createGoodsPoster(String qr_Code_url) throws Exception {
		/**
		 * 海报所需数据 1. 商品图片 2. 规格 3. 描述 4. 售价 5. 参考价 6. 会员名 7. 会员头像 8. 二维码url 9. 横幅图
		 * 注：文字的y值是文字的下边框距离画布的上边框的距离
		 */
		// 海报底图
		BufferedImage poster = ImageIO.read(new URL(PropertyUtil.getProperty("post_back_image")));

		// 创建一个和海报地图一样大的纯色的图片作为画布开始
		BufferedImage bgBufferImage = new BufferedImage(poster.getWidth(), poster.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
		bgBufferImage.getGraphics().drawImage(poster, 0, 0, poster.getWidth(), poster.getHeight(), Color.WHITE, null);
		Graphics2D graphics = bgBufferImage.createGraphics();
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
		rh.put(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
		rh.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		rh.put(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_DISABLE);
		rh.put(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		rh.put(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		rh.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		graphics.setRenderingHints(rh);
		graphics.setBackground(Color.WHITE);
		// 创建一个和海报地图一样大的纯色的图片作为画布结束
		// 小程序码
		// 获取小程序码
		BufferedImage codeImage = ImageIO.read(new URL(qr_Code_url));
		// 半径
		graphics.drawImage(codeImage, 125, 110, 580, 580, null);
		// 将海报地图画在画布上
		graphics.drawImage(poster, 0, 0, poster.getWidth(), poster.getHeight(), null);
		// 绘制市场价结束
		graphics.dispose();
		return bgBufferImage;
	}

//	public static void main(String[] args) throws IOException {
//		BufferedImage bi;
//		try {
//			bi = createGoodsPoster("http://dashouyi.oss-cn-hangzhou.aliyuncs.com/image/1560326654528qr_code.png");
//
//			File outputfile = new File("C:\\tempImage\\saved.png");
//
//			ImageIO.write(bi, "png", outputfile);
//			System.out.println("ok");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
