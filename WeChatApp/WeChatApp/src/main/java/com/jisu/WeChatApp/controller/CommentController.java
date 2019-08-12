package com.jisu.WeChatApp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jisu.WeChatApp.dao.CommentInfoMapper;
import com.jisu.WeChatApp.dao.ReplyCommentInfoMapper;
import com.jisu.WeChatApp.pojo.CommentInfo;
import com.jisu.WeChatApp.pojo.ReplyCommentInfo;
import com.jisu.WeChatApp.service.impl.CommentServiceImpl;
import com.jisu.WeChatApp.tool.util.DynamicCodeUtil;
import com.jisu.WeChatApp.tool.util.MsgModel;

@RequestMapping("/api/comment")
@RestController
public class CommentController {
	@Autowired
	private CommentInfoMapper commentInfoMapper;
	@Autowired
	private ReplyCommentInfoMapper replyCommentInfoMapper;
	@Autowired
	private CommentServiceImpl commentServiceImpl;

	/**
	 * 保存评论
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveComment", method = RequestMethod.POST)
	public MsgModel saveComment(HttpServletRequest request) {
		String comment_type = request.getParameter("comment_type");
		String relation_id = request.getParameter("relation_id");
		String comment_text = request.getParameter("comment_text");
		String member_no = request.getParameter("member_no");

		CommentInfo commentInfo = new CommentInfo();
		commentInfo.setCommentId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		commentInfo.setCommentText(comment_text);
		commentInfo.setCommentType(Integer.valueOf(comment_type));
		commentInfo.setCreateTime(new Date());
		commentInfo.setMemberNo(member_no);
		commentInfo.setRelationId(relation_id);
		int num = commentInfoMapper.insertSelective(commentInfo);
		MsgModel msg = new MsgModel();
		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	/**
	 * 保存评论回复
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "saveReplyComment", method = RequestMethod.POST)
	public MsgModel saveReplyComment(HttpServletRequest request) {
		String comment_id = request.getParameter("comment_id");
		String comment_text = request.getParameter("comment_text");
		String member_no = request.getParameter("member_no");
		String to_member = request.getParameter("to_member");

		ReplyCommentInfo replyCommentInfo = new ReplyCommentInfo();
		replyCommentInfo.setCommentId(comment_id);
		replyCommentInfo.setCreateTime(new Date());
		replyCommentInfo.setCommentText(comment_text);
		replyCommentInfo.setMemberNo(member_no);
		replyCommentInfo.setToMember(to_member);
		replyCommentInfo.setReplyCommentId(DynamicCodeUtil.generateCode(DynamicCodeUtil.TYPE_ALL_MIXED, 32, null));
		int num = replyCommentInfoMapper.insertSelective(replyCommentInfo);
		MsgModel msg = new MsgModel();
		if (num > 0) {
			msg.setStatus(MsgModel.SUCCESS);
		} else {
			msg.setStatus(MsgModel.ERROR);
		}
		return msg;
	}

	@RequestMapping("getCommentInfoList")
	public MsgModel getCommentInfoList(HttpServletRequest request) {
		String comment_type = request.getParameter("comment_type");
		String relation_id = request.getParameter("relation_id");
		List<Map<String, Object>> comment_list = commentServiceImpl.getCommentInfoList(relation_id, comment_type);
		MsgModel msg = new MsgModel();
		msg.setContext(comment_list);
		msg.setStatus(MsgModel.SUCCESS);
		return msg;
	}

}
