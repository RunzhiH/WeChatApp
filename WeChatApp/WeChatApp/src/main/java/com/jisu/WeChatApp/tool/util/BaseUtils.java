package com.jisu.WeChatApp.tool.util;

import java.util.ArrayList;
import java.util.List;

public class BaseUtils {

	public static List<String> SectionToList(String begin, String end) {
		List<String> return_list = new ArrayList<String>();
		for (int i = Integer.valueOf(begin); i < Integer.valueOf(end); i++) {
			return_list.add(String.valueOf(i));
		}
		return return_list;
	}
}
