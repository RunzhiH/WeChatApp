package com.jisu.WeChatApp.pojo;

public class UserRole {

	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user_role.member_no
	 * @mbg.generated
	 */
	private String memberNo;
	/**
	 * This field was generated by MyBatis Generator. This field corresponds to the database column user_role.role_id
	 * @mbg.generated
	 */
	private String roleId;

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user_role.member_no
	 * @return  the value of user_role.member_no
	 * @mbg.generated
	 */
	public String getMemberNo() {
		return memberNo;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user_role.member_no
	 * @param memberNo  the value for user_role.member_no
	 * @mbg.generated
	 */
	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo == null ? null : memberNo.trim();
	}

	/**
	 * This method was generated by MyBatis Generator. This method returns the value of the database column user_role.role_id
	 * @return  the value of user_role.role_id
	 * @mbg.generated
	 */
	public String getRoleId() {
		return roleId;
	}

	/**
	 * This method was generated by MyBatis Generator. This method sets the value of the database column user_role.role_id
	 * @param roleId  the value for user_role.role_id
	 * @mbg.generated
	 */
	public void setRoleId(String roleId) {
		this.roleId = roleId == null ? null : roleId.trim();
	}
}