package com.jisu.WeChatApp.tool.handle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jisu.WeChatApp.tool.util.MsgModel;

@ControllerAdvice

public class ExceptionHandle {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandle.class);

	@ExceptionHandler(Exception.class)

	@ResponseBody

	public MsgModel handle(Exception e) {
		LOGGER.error(e.getMessage());
		MsgModel msg = new MsgModel();
		msg.setMessage("系统异常：" + e.getMessage());
		msg.setStatus(MsgModel.ERROR);
		return msg;
	}

}
