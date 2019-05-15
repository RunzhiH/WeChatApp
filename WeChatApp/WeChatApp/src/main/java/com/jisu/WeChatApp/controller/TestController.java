package com.jisu.WeChatApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@RequestMapping("/test")
public class TestController {
	@RequestMapping("/getUser")
	public Map<String, Object> getUser() {
		System.out.println("΢��С�������ڵ��á�����");
		Map<String, Object> map = new HashMap<String, Object>();
		List<String> list = new ArrayList<String>();
		list.add("zhangsan");
		list.add("lisi");
		list.add("wanger");
		list.add("mazi");
		map.put("list", list);
		System.out.println("΢��С���������ɡ�����");
		return map;
	}
}
