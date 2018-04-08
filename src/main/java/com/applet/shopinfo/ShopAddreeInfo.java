/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月1日 下午7:27:54
 */
package com.applet.shopinfo;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月1日 下午7:27:54
 */
public class ShopAddreeInfo {
	
	private String shopId;
	
	private String address;
	
	private String headImg;
	
	private String distance;
	
	private String shopName;
	
	private String promotion;
	
	private String buyPeopleNum;
	
	private String avgSale;

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHeadImg() {
		return headImg;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
	}

	@Override
	public String toString() {
		return "ShopAddreeInfo [shopId=" + shopId + ", address=" + address + ", headImg=" + headImg + ", distance="
				+ distance + ", shopName=" + shopName + ", promotion=" + promotion + "]";
	}

	public String getBuyPeopleNum() {
		return buyPeopleNum;
	}

	public void setBuyPeopleNum(String buyPeopleNum) {
		this.buyPeopleNum = buyPeopleNum;
	}

	public String getAvgSale() {
		return avgSale;
	}

	public void setAvgSale(String avgSale) {
		this.avgSale = avgSale;
	}
	
	
	
}
