package com.jisu.WeChatApp.dao;

import com.jisu.WeChatApp.pojo.ReplyCommentInfo;
import com.jisu.WeChatApp.pojo.ReplyCommentInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ReplyCommentInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	long countByExample(ReplyCommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int deleteByExample(ReplyCommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String replyCommentId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int insert(ReplyCommentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int insertSelective(ReplyCommentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	List<ReplyCommentInfo> selectByExample(ReplyCommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	ReplyCommentInfo selectByPrimaryKey(String replyCommentId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") ReplyCommentInfo record, @Param("example") ReplyCommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") ReplyCommentInfo record, @Param("example") ReplyCommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(ReplyCommentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table reply_comment_info
	 * @mbg.generated
	 */
	int updateByPrimaryKey(ReplyCommentInfo record);
}