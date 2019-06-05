package com.jisu.WeChatApp.entity;

public class ServerMemberVO {

	private String memberNo;
	private String phone;
	private String nickname;
	private String photo;
	private String serverClassIdStr;
	private String orderTakesType;
	private String serverMemberDesc;
	private String lat;
	private String lon;
	private String photoDesc;

	public String getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(String memberNo) {
		this.memberNo = memberNo;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getServerClassIdStr() {
		return serverClassIdStr;
	}

	public void setServerClassIdStr(String serverClassIdStr) {
		this.serverClassIdStr = serverClassIdStr;
	}

	public String getOrderTakesType() {
		return orderTakesType;
	}

	public void setOrderTakesType(String orderTakesType) {
		this.orderTakesType = orderTakesType;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getServerMemberDesc() {
		return serverMemberDesc;
	}

	public void setServerMemberDesc(String serverMemberDesc) {
		this.serverMemberDesc = serverMemberDesc;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getPhotoDesc() {
		return photoDesc;
	}

	public void setPhotoDesc(String photoDesc) {
		this.photoDesc = photoDesc;
	}
	
}
