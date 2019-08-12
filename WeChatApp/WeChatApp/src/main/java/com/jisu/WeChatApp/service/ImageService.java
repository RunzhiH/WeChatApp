package com.jisu.WeChatApp.service;

import java.awt.image.BufferedImage;

public interface ImageService {

	BufferedImage createGoodsPoster(String qr_Code_url) throws Exception;

}
