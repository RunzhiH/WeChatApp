package com.jisu.WeChatApp.pojo;

public class BestServerClass {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column best_server_class.best_server_class_id
     *
     * @mbg.generated
     */
    private String bestServerClassId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column best_server_class.server_class_id
     *
     * @mbg.generated
     */
    private String serverClassId;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column best_server_class.member_no
     *
     * @mbg.generated
     */
    private String memberNo;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column best_server_class.best_server_class_id
     *
     * @return the value of best_server_class.best_server_class_id
     *
     * @mbg.generated
     */
    public String getBestServerClassId() {
        return bestServerClassId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column best_server_class.best_server_class_id
     *
     * @param bestServerClassId the value for best_server_class.best_server_class_id
     *
     * @mbg.generated
     */
    public void setBestServerClassId(String bestServerClassId) {
        this.bestServerClassId = bestServerClassId == null ? null : bestServerClassId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column best_server_class.server_class_id
     *
     * @return the value of best_server_class.server_class_id
     *
     * @mbg.generated
     */
    public String getServerClassId() {
        return serverClassId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column best_server_class.server_class_id
     *
     * @param serverClassId the value for best_server_class.server_class_id
     *
     * @mbg.generated
     */
    public void setServerClassId(String serverClassId) {
        this.serverClassId = serverClassId == null ? null : serverClassId.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column best_server_class.member_no
     *
     * @return the value of best_server_class.member_no
     *
     * @mbg.generated
     */
    public String getMemberNo() {
        return memberNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column best_server_class.member_no
     *
     * @param memberNo the value for best_server_class.member_no
     *
     * @mbg.generated
     */
    public void setMemberNo(String memberNo) {
        this.memberNo = memberNo == null ? null : memberNo.trim();
    }
}