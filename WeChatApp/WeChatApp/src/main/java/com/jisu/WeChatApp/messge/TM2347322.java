package com.jisu.WeChatApp.messge;

import java.util.HashMap;
import java.util.Map;

public class TM2347322 extends MessageTemplate {

	public void setDataInfo(Map<String, String> messageData) {
		// TODO Auto-generated method stub
		Map<String, TemplateData> m = new HashMap<>(5);
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue("新下单待抢单");
		m.put("keyword1", keyword1);
		super.setData(m);
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue("这里填下单金额的值");
		m.put("keyword2", keyword2);
		super.setData(m);

		TemplateData keyword3 = new TemplateData();
		keyword3.setValue("这里填配送地址");
		m.put("keyword3", keyword3);
		super.setData(m);

		TemplateData keyword4 = new TemplateData();
		keyword4.setValue("这里填取件地址");
		m.put("keyword4", keyword4);
		super.setData(m);

		TemplateData keyword5 = new TemplateData();
		keyword5.setValue("这里填备注");
		m.put("keyword5", keyword5);
		super.setData(m);
	}
}
