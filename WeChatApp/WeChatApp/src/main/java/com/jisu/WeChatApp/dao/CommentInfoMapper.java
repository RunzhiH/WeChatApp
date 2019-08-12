package com.jisu.WeChatApp.dao;

import com.jisu.WeChatApp.pojo.CommentInfo;
import com.jisu.WeChatApp.pojo.CommentInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	long countByExample(CommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int deleteByExample(CommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String commentId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int insert(CommentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int insertSelective(CommentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	List<CommentInfo> selectByExample(CommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	CommentInfo selectByPrimaryKey(String commentId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") CommentInfo record, @Param("example") CommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") CommentInfo record, @Param("example") CommentInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(CommentInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table comment_info
	 * @mbg.generated
	 */
	int updateByPrimaryKey(CommentInfo record);
}