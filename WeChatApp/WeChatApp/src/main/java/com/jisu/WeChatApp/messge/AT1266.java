package com.jisu.WeChatApp.messge;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
@Service("AT1266")
public class AT1266 extends MessageTemplate {

	@Override
	public void setDataInfo(Map<String, String> messageData) {
		// TODO Auto-generated method stub
		Map<String, TemplateData> m = new HashMap<>();
		TemplateData keyword1 = new TemplateData();
		keyword1.setValue(messageData.get("order_code"));
		m.put("keyword1", keyword1);
		TemplateData keyword2 = new TemplateData();
		keyword2.setValue(messageData.get("order_nicakname"));
		m.put("keyword2", keyword2);
		TemplateData keyword3 = new TemplateData();
		keyword3.setValue(messageData.get("appointment_time"));
		m.put("keyword3", keyword3);
		TemplateData keyword4 = new TemplateData();
		keyword4.setValue(messageData.get("server_name"));
		m.put("keyword4", keyword4);
		TemplateData keyword5 = new TemplateData();
		keyword5.setValue(messageData.get("server_address"));
		m.put("keyword5", keyword5);
		TemplateData keyword6 = new TemplateData();
		keyword6.setValue(messageData.get("order_phone"));
		m.put("keyword6", keyword6);
		
		super.setData(m);
	}

}
