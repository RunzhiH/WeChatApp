package com.jisu.WeChatApp.messge;

import lombok.Data;

/*
* 设置推送的文字和颜色
* */
@Data
public class TemplateData {
    //keyword1：订单类型，keyword2：下单金额，keyword3：配送地址，keyword4：取件地址，keyword5备注
    private String value;//,,依次排下去
//    private String color;//字段颜色（微信官方已废弃，设置没有效果）

	public void setValue(String value) {
		// TODO Auto-generated method stub
		this.value=value;
	}

	public String getValue() {
		return value;
	}
	
}