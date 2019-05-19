package com.jisu.WeChatApp.timeTask;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
public class AutoCloseOrderProhi {
	// 自动解封
	@Scheduled(fixedRate = 5000)
	private void CloseOrderProhiTasks() {
		
	}
}
