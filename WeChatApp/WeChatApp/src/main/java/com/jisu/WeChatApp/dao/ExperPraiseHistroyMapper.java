package com.jisu.WeChatApp.dao;

import com.jisu.WeChatApp.pojo.ExperPraiseHistroy;
import com.jisu.WeChatApp.pojo.ExperPraiseHistroyExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ExperPraiseHistroyMapper {

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	long countByExample(ExperPraiseHistroyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int deleteByExample(ExperPraiseHistroyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int deleteByPrimaryKey(String experPraiseHistroyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int insert(ExperPraiseHistroy record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int insertSelective(ExperPraiseHistroy record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	List<ExperPraiseHistroy> selectByExample(ExperPraiseHistroyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	ExperPraiseHistroy selectByPrimaryKey(String experPraiseHistroyId);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int updateByExampleSelective(@Param("record") ExperPraiseHistroy record, @Param("example") ExperPraiseHistroyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int updateByExample(@Param("record") ExperPraiseHistroy record, @Param("example") ExperPraiseHistroyExample example);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int updateByPrimaryKeySelective(ExperPraiseHistroy record);

	/**
	 * This method was generated by MyBatis Generator. This method corresponds to
	 * the database table exper_praise_histroy
	 * 
	 * @mbg.generated
	 */
	int updateByPrimaryKey(ExperPraiseHistroy record);
}