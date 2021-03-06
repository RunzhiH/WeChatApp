package com.jisu.WeChatApp.pojo;

import java.util.Date;

public class ReplyCommentInfo {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.reply_comment_id
	 * @mbg.generated
	 */
	private String replyCommentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.comment_id
	 * @mbg.generated
	 */
	private String commentId;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.to_member
	 * @mbg.generated
	 */
	private String toMember;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.member_no
	 * @mbg.generated
	 */
	private String memberNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.create_time
	 * @mbg.generated
	 */
	private Date createTime;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.comment_text
	 * @mbg.generated
	 */
	private String commentText;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column reply_comment_info.praise_points
	 * @mbg.generated
	 */
	private Integer praisePoints;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.reply_comment_id
	 * @return  the value of reply_comment_info.reply_comment_id
	 * @mbg.generated
	 */
	public String getReplyCommentId() {
		return replyCommentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.reply_comment_id
	 * @param replyCommentId  the value for reply_comment_info.reply_comment_id
	 * @mbg.generated
	 */
	public void setReplyCommentId(String replyCommentId) {
		this.replyCommentId = replyCommentId == null ? null : replyCommentId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.comment_id
	 * @return  the value of reply_comment_info.comment_id
	 * @mbg.generated
	 */
	public String getCommentId() {
		return commentId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.comment_id
	 * @param commentId  the value for reply_comment_info.comment_id
	 * @mbg.generated
	 */
	public void setCommentId(String commentId) {
		this.commentId = commentId == null ? null : commentId.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.to_member
	 * @return  the value of reply_comment_info.to_member
	 * @mbg.generated
	 */
	public String getToMember() {
		return toMember;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.to_member
	 * @param toMember  the value for reply_comment_info.to_member
	 * @mbg.generated
	 */
	public void setToMember(String toMember) {
		this.toMember = toMember == null ? null : toMember.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.member_no
	 * @return  the value of reply_comment_info.member_no
	 * @mbg.generated
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.member_no
	 * @param memberNo  the value for reply_comment_info.member_no
	 * @mbg.generated
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo == null ? null : memberNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.create_time
	 * @return  the value of reply_comment_info.create_time
	 * @mbg.generated
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.create_time
	 * @param createTime  the value for reply_comment_info.create_time
	 * @mbg.generated
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.comment_text
	 * @return  the value of reply_comment_info.comment_text
	 * @mbg.generated
	 */
	public String getCommentText() {
		return commentText;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.comment_text
	 * @param commentText  the value for reply_comment_info.comment_text
	 * @mbg.generated
	 */
	public void setCommentText(String commentText) {
		this.commentText = commentText == null ? null : commentText.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column reply_comment_info.praise_points
	 * @return  the value of reply_comment_info.praise_points
	 * @mbg.generated
	 */
	public Integer getPraisePoints() {
		return praisePoints;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column reply_comment_info.praise_points
	 * @param praisePoints  the value for reply_comment_info.praise_points
	 * @mbg.generated
	 */
	public void setPraisePoints(Integer praisePoints) {
		this.praisePoints = praisePoints;
	}
}