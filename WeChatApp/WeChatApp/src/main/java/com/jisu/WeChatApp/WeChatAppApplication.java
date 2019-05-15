package com.jisu.WeChatApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.jisu.WeChatApp.tool.util.SpringContextUtil;

@SpringBootApplication
//@MapperScan("com.jisu.WeChatApp.dao")
@EnableTransactionManagement(proxyTargetClass = true)
public class WeChatAppApplication {

	public static void main(String[] args) {
		ApplicationContext app = SpringApplication.run(WeChatAppApplication.class, args);
		SpringContextUtil.setApplicationContext(app);
	}

}
