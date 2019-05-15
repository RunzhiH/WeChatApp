package com.jisu.WeChatApp.timeTask;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component注解用于对那些比较中立的类进行注释；
//相对与在持久层、业务层和控制层分别采用 @Repository、@Service 和 @Controller 对分层中的类进行注释
@Component
@EnableScheduling // 1.开启定时任务
//@EnableAsync // 2.开启多线程
public class AutoCloseOrderTimeTask {

	/**
	 * 自动关闭订单定时任务
	 * 
	 * @throws InterruptedException
	 * @Cron 表达式参数分别表示： 秒（0~59） 例如0/5表示每5秒 分（0~59） 时（0~23） 日（0~31）的某天，需计算 月（0~11）
	 *       周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
	 */
	// 3.添加定时任务
	//@Scheduled(cron = "0/5 * * * * ?")
	// 或直接指定时间间隔，例如：5秒
	@Scheduled(fixedRate=5000)
	private void CloseOrderTasks() {
		
	
	}
}