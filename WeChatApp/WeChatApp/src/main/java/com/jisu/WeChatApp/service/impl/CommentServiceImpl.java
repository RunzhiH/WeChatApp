package com.jisu.WeChatApp.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jisu.WeChatApp.daoSelf.CommentInfoMapperSelf;
import com.jisu.WeChatApp.service.CommentService;

@Service("CommetServiceImpl")
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentInfoMapperSelf commentInfoMapperSelf;

	@Override
	public List<Map<String, Object>> getCommentInfoList(String relation_id, String comment_type) {
		// TODO Auto-generated method stub
		Map<String, String> condition = new HashMap<String, String>();
		condition.put("relation_id", relation_id);
		condition.put("comment_type", comment_type);
		List<Map<String, String>> comment_list = commentInfoMapperSelf.getCommentList(condition);
		List<Map<String, Object>> result_list = new ArrayList<Map<String, Object>>();
		List<Map<String, String>> reply_comment_list = commentInfoMapperSelf.getReplyCommentList(condition);
		for (Map<String, String> comment : comment_list) {
			String comment_id = comment.get("comment_id");
			Map<String, Object> result_map = new HashMap<String, Object>();
			result_map.putAll(comment);
			List<Map<String, String>> result_reply_comment = new ArrayList<Map<String, String>>();
			for (Map<String, String> reply_comment : reply_comment_list) {
				if (comment_id.equals(reply_comment.get("comment_id"))) {
					result_reply_comment.add(reply_comment);
				}
			}
			result_map.put("reply_comment_list", result_reply_comment);
			result_list.add(result_map);
		}

		return result_list;
	}

}
