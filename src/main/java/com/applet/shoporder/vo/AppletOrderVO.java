/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月27日 下午5:14:40
 */
package com.applet.shoporder.vo;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月27日 下午5:14:40
 */
public class AppletOrderVO {
	
	private String deskId;
	
	private String justNowEat;
	
	private String peopleNum;
	
	private String note;

	public String getDeskId() {
		return deskId;
	}

	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	public String getJustNowEat() {
		return justNowEat;
	}

	public void setJustNowEat(String justNowEat) {
		this.justNowEat = justNowEat;
	}

	public String getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(String peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	@Override
	public String toString() {
		return "AppletOrderVO [deskId=" + deskId + ", justNowEat=" + justNowEat + ", peopleNum=" + peopleNum + ", note="
				+ note + "]";
	}

}
