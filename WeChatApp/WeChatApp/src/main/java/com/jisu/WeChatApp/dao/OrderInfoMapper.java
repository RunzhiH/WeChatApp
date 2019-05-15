package com.jisu.WeChatApp.dao;

import com.jisu.WeChatApp.pojo.OrderInfo;
import com.jisu.WeChatApp.pojo.OrderInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface OrderInfoMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	long countByExample(OrderInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int deleteByExample(OrderInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String orderId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int insert(OrderInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int insertSelective(OrderInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	List<OrderInfo> selectByExample(OrderInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	OrderInfo selectByPrimaryKey(String orderId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(OrderInfo record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to the database table order_info
	 * @mbg.generated
	 */
	int updateByPrimaryKey(OrderInfo record);
}