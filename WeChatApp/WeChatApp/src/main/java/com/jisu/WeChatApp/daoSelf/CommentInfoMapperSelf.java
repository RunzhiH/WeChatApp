package com.jisu.WeChatApp.daoSelf;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentInfoMapperSelf {
	public List<Map<String, String>> getCommentList(Map<String, String> condition);
	
	public List<Map<String, String>> getReplyCommentList(Map<String, String> condition);
}
