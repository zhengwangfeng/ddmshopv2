package com.applet.shopcar.vo;

import java.util.List;

import com.applet.shopcar.TcsbShopCarItemVo;

public class TcsbShopCarTempVO {
	private String shopId;
	private String openId;
	private String note;
	private Integer peopleNum;
	private String phone;
	private String sex;
	private String nickname;
	private String eatTime;
	private String shopCarItemVo;
	public String getEatTime() {
		return eatTime;
	}
	public String getNickname() {
		return nickname;
	}
	public String getNote() {
		return note;
	}
	public String getOpenId() {
		return openId;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public String getPhone() {
		return phone;
	}
	public String getSex() {
		return sex;
	}
	public String getShopId() {
		return shopId;
	}
	public void setEatTime(String eatTime) {
		this.eatTime = eatTime;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getShopCarItemVo() {
		return shopCarItemVo;
	}
	public void setShopCarItemVo(String shopCarItemVo) {
		this.shopCarItemVo = shopCarItemVo;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
}
