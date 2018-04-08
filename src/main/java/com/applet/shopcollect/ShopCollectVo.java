/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月3日 下午3:48:44
 */
package com.applet.shopcollect;

import com.tcsb.shop.entity.TcsbShopEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月3日 下午3:48:44
 */
public class ShopCollectVo {
	
	private TcsbShopEntity tcsbShop;
	
	private String promotion;
	
	private String buyPeopleNum;
	
	private String avgSale;
	
	private String distance;

	public TcsbShopEntity getTcsbShop() {
		return tcsbShop;
	}

	public void setTcsbShop(TcsbShopEntity tcsbShop) {
		this.tcsbShop = tcsbShop;
	}

	public String getPromotion() {
		return promotion;
	}

	public void setPromotion(String promotion) {
		this.promotion = promotion;
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

	public String getDistance() {
		return distance;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	

}
