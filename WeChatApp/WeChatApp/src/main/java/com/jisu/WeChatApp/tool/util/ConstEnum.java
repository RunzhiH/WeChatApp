package com.jisu.WeChatApp.tool.util;

public enum ConstEnum {

	/**
	 * 
	 * 分页时，每页数据大小
	 * 
	 */

	PAGESIZE(1),

	/**
	 * 
	 * 当前向前翻页页数，确定导航栏终止页
	 * 
	 */

	PAGEFORWARD(2),

	/**
	 * 
	 * 当前向后翻页页数，确定导航栏起始页
	 * 
	 */

	PAGEBACKWARD(2),

	/**
	 * 
	 * 导航栏显示总页数
	 * 
	 */

	PAGES(5),

	/**
	 * 
	 * 导航栏默认起始页 1
	 * 
	 */

	PAGESTART(1);

	private int value;

	ConstEnum(int value) {

		this.value = value;

	}

	public int getValue() {

		return value;

	}

}
