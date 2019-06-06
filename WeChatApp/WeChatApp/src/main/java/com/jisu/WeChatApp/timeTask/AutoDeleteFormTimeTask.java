package com.jisu.WeChatApp.timeTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jisu.WeChatApp.service.impl.SendMessageServiceImpl;

@Component
@EnableScheduling
public class AutoDeleteFormTimeTask {
	@Autowired
	private SendMessageServiceImpl sendMessageServiceImpl;
	/**
	 * 自动删除form
	 */
	@Scheduled(fixedRate = 5000)
	public void AutoDeleteForm() {
		// 删除超过7天的formid
		sendMessageServiceImpl.deleteFormIdByOvertime();
	}
}
