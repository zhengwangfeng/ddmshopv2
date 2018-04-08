package com.applet.shoporder.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

public class CouponDto {
	
	private String couponId;
	
	private String fullcutTemplateId;
	
	/**满额*/
	private java.lang.Integer total;
	/**立减*/
	private java.lang.Integer discount;
	
	private String useRange;
	
	private String foodTypeId;
		
	private String foodId;
	
	
//	coupon_type优惠券类型0商户投放1分享领取2平台优惠券
	private String couponType;
//	share_type分享类型1固定分享金2比例分享金
	private String shareType;
//	share_descript分享金描述
	private String shareDescript;
	public String getCouponId() {
		return couponId;
	}
	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}
	public String getFullcutTemplateId() {
		return fullcutTemplateId;
	}
	public void setFullcutTemplateId(String fullcutTemplateId) {
		this.fullcutTemplateId = fullcutTemplateId;
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
	public String getUseRange() {
		return useRange;
	}
	public void setUseRange(String useRange) {
		this.useRange = useRange;
	}
	public String getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getShareType() {
		return shareType;
	}
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	public String getShareDescript() {
		return shareDescript;
	}
	public void setShareDescript(String shareDescript) {
		this.shareDescript = shareDescript;
	}
	

	
}
