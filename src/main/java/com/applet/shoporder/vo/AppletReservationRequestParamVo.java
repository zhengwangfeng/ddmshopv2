/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月10日 上午9:52:24
 */
package com.applet.shoporder.vo;

import java.util.Date;

/**
 * ddmShopV2
 *
 *
 */
public class AppletReservationRequestParamVo {
	private String shopId;
	private String openId;
	private String note;
	private Integer peopleNum;
	private String phone;
	private String sex;
	private String nickname;
	private String eatTime;
	private String shopCarId;
	private String reservationId;
	public String getReservationId() {
		return reservationId;
	}
	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
	}
	public String getShopCarId() {
		return shopCarId;
	}
	public void setShopCarId(String shopCarId) {
		this.shopCarId = shopCarId;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getNote() {
		return note;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getEatTime() {
		return eatTime;
	}
	public void setEatTime(String eatTime) {
		this.eatTime = eatTime;
	}

}
