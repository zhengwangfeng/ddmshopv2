/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月3日 下午2:52:58
 */
package com.applet.usercoupon;

import java.util.Date;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月3日 下午2:52:58
 */
public class CouponVo {
	
	
	private String couponId;
	private String shopId;
	private String ShopName;
	private String expiryDate;
	private String useRangeCode;
	private String useRangeName;
	/**满额**/
	private java.lang.Integer total;
	/**立减*/
	private java.lang.Integer discount;
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}
	public String getShopName() {
		return ShopName;
	}
	public void setShopName(String shopName) {
		ShopName = shopName;
	}
	
	public String getUseRangeCode() {
		return useRangeCode;
	}
	public void setUseRangeCode(String useRangeCode) {
		this.useRangeCode = useRangeCode;
	}
	public String getUseRangeName() {
		return useRangeName;
	}
	public void setUseRangeName(String useRangeName) {
		this.useRangeName = useRangeName;
	}
	public java.lang.Integer getTotal() {
		return total;
	}
	public void setTotal(java.lang.Integer total) {
		this.total = total;
	}
	public java.lang.Integer getDiscount() {
		return discount;
	}
	public void setDiscount(java.lang.Integer discount) {
		this.discount = discount;
	}
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	
	
	
	

}
