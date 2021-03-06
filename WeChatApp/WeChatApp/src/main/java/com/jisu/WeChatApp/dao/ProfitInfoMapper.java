package com.jisu.WeChatApp.dao;

import com.jisu.WeChatApp.pojo.ProfitInfo;
import com.jisu.WeChatApp.pojo.ProfitInfoExample;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface ProfitInfoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    long countByExample(ProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int deleteByExample(ProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(String profitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int insert(ProfitInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int insertSelective(ProfitInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    List<ProfitInfo> selectByExample(ProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    ProfitInfo selectByPrimaryKey(String profitId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") ProfitInfo record, @Param("example") ProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") ProfitInfo record, @Param("example") ProfitInfoExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(ProfitInfo record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table profit_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(ProfitInfo record);
}