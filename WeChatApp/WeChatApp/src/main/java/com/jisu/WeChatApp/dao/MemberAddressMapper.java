package com.jisu.WeChatApp.dao;

import com.jisu.WeChatApp.pojo.MemberAddress;
import com.jisu.WeChatApp.pojo.MemberAddressExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberAddressMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	long countByExample(MemberAddressExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int deleteByExample(MemberAddressExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String memberAddressId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int insert(MemberAddress record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int insertSelective(MemberAddress record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	List<MemberAddress> selectByExample(MemberAddressExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	MemberAddress selectByPrimaryKey(String memberAddressId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") MemberAddress record, @Param("example") MemberAddressExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") MemberAddress record, @Param("example") MemberAddressExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(MemberAddress record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table member_address
	 * @mbg.generated
	 */
	int updateByPrimaryKey(MemberAddress record);
}