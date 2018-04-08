/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月10日 上午9:52:24
 */
package com.applet.shoporder.vo;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月10日 上午9:52:24
 */
public class AppletRequestParamVo {
	private String isJustNowServing;
	private String deskId;
	private String shopId;
	private String note;
	private String taste;
	private Integer peopleNum;
	private String isRoom;
	private String openId;
	private String orderParentId;
	private String orderNo;
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getOrderParentId() {
		return orderParentId;
	}
	public void setOrderParentId(String orderParentId) {
		this.orderParentId = orderParentId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getIsJustNowServing() {
		return isJustNowServing;
	}
	public void setIsJustNowServing(String isJustNowServing) {
		this.isJustNowServing = isJustNowServing;
	}
	public String getDeskId() {
		return deskId;
	}
	public void setDeskId(String deskId) {
		this.deskId = deskId;
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
	public void setNote(String note) {
		this.note = note;
	}
	public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}
	public Integer getPeopleNum() {
		return peopleNum;
	}
	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}
	public String getIsRoom() {
		return isRoom;
	}
	public void setIsRoom(String isRoom) {
		this.isRoom = isRoom;
	}
	@Override
	public String toString() {
		return "AppletRequestParamVo [isJustNowServing=" + isJustNowServing + ", deskId=" + deskId + ", shopId="
				+ shopId + ", note=" + note + ", taste=" + taste + ", peopleNum=" + peopleNum + ", isRoom=" + isRoom
				+ "]";
	}
	
	

}
