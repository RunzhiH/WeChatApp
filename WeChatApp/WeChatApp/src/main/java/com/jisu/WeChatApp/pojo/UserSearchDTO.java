package com.jisu.WeChatApp.pojo;

/**
 * @项目名称：wyait-manage
 * @包名：com.wyait.manage.entity @类描述：
 * @创建人：wyait @创建时间：2018-01-02 17:10 @version：V1.0
 */
public class UserSearchDTO {

	private Integer page;

	private Integer limit;

	private String nickname;

	private String phone;

	private String memberNo;

	private String insertTimeStart;

	private String insertTimeEnd;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInsertTimeStart() {
		return insertTimeStart;
	}

	public void setInsertTimeStart(String insertTimeStart) {
		this.insertTimeStart = insertTimeStart;
	}

	public String getInsertTimeEnd() {
		return insertTimeEnd;
	}

	public void setInsertTimeEnd(String insertTimeEnd) {
		this.insertTimeEnd = insertTimeEnd;
	}

	@Override
	public String toString() {
		return "UserSearchDTO [page=" + page + ", limit=" + limit + ", nickname=" + nickname + ", phone=" + phone + ", insertTimeStart=" + insertTimeStart + ", insertTimeEnd=" + insertTimeEnd + "]";
	}

}
