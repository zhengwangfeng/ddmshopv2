/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月18日 下午1:41:09
 */
package com.applet.shopinfo;

import java.util.List;

import com.tcsb.shop.entity.TcsbShopEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月18日 下午1:41:09
 */
public class ShopInfoVo {
	
	private TcsbShopEntity tcsbShop;
	
	/***优惠活动*/
	private String promotion;
	/***折扣*/
	private String discountTitle;
	
	private String businessHours;
	/**优惠券**/
	private String iscoupon;
	
	
	private List<Object> shopImgs;

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

	public String getDiscountTitle() {
		return discountTitle;
	}

	public void setDiscountTitle(String discountTitle) {
		this.discountTitle = discountTitle;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getIscoupon() {
		return iscoupon;
	}

	public void setIscoupon(String iscoupon) {
		this.iscoupon = iscoupon;
	}

	public List<Object> getShopImgs() {
		return shopImgs;
	}

	public void setShopImgs(List<Object> shopImgs) {
		this.shopImgs = shopImgs;
	}


	
	

}
