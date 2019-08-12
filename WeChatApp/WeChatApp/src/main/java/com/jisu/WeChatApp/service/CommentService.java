package com.jisu.WeChatApp.service;

import java.util.List;
import java.util.Map;

public interface CommentService {
	public List<Map<String, Object>> getCommentInfoList(String relation, String comment_type);
}
