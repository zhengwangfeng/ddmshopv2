/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月30日 下午6:32:08
 */
package com.applet.shoporder.vo;

import java.util.List;

import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月30日 下午6:32:08
 */
public class OrderInfoVo {
	
	
	private String orderParentId;
	
	private List<TcsbOrderEntity> tcsbOrderEntity;
	
	private List<TcsbOrderItemEntity> tcsbOrderItemArray;
	
	private List<TcsbOrderItemEntity> payOrderItemArray;
	
	private List<TcsbOrderItemEntity> nopayOrderItemArray;
	
	private String finalMoney;
	private String noPayMoney;
	private String payMoney;
	private String needPayMoney;
	private String noDiscountMoney;
	
	private String fronMoney;
	
	
	private String orderDeskName;
	
	
	
	
	private boolean hasShopFullcut;
	
	/**
	 * 是否有优惠券
	 */
	private boolean hasFullCut;
	
	@Deprecated
	private boolean hasPlatformCut;
	
	private String universalCouponPrice;
	
	private String shopUserDiscountMsg;
	
	private String platformCouponPrice;
	
	private String vipDisCount;
	
	private boolean noPayOrder;
	
	private boolean haveMealFee;
	
	
	private String shareGoldCoin;
	
	/**
	 * 优惠券金额
	 */
	private String couponPrice;
	
	
	public boolean isHasShopFullcut() {
		return hasShopFullcut;
	}
	public void setHasShopFullcut(boolean hasShopFullcut) {
		this.hasShopFullcut = hasShopFullcut;
	}
	public boolean isHasFullCut() {
		return hasFullCut;
	}
	public void setHasFullCut(boolean hasFullCut) {
		this.hasFullCut = hasFullCut;
	}
	
	public boolean isHaveMealFee() {
		return haveMealFee;
	}
	public void setHaveMealFee(boolean haveMealFee) {
		this.haveMealFee = haveMealFee;
	}
	private String mealFee;
	
	public String getMealFee() {
		return mealFee;
	}
	public void setMealFee(String mealFee) {
		this.mealFee = mealFee;
	}
	public List<TcsbOrderItemEntity> getTcsbOrderItemArray() {
		return tcsbOrderItemArray;
	}
	public void setTcsbOrderItemArray(List<TcsbOrderItemEntity> tcsbOrderItemArray) {
		this.tcsbOrderItemArray = tcsbOrderItemArray;
	}
	public String getFinalMoney() {
		return finalMoney;
	}
	public void setFinalMoney(String finalMoney) {
		this.finalMoney = finalMoney;
	}
	public String getNoPayMoney() {
		return noPayMoney;
	}
	public void setNoPayMoney(String noPayMoney) {
		this.noPayMoney = noPayMoney;
	}
	public String getPayMoney() {
		return payMoney;
	}
	public void setPayMoney(String payMoney) {
		this.payMoney = payMoney;
	}
	public String getUniversalCouponPrice() {
		return universalCouponPrice;
	}
	public void setUniversalCouponPrice(String universalCouponPrice) {
		this.universalCouponPrice = universalCouponPrice;
	}
	public String getNeedPayMoney() {
		return needPayMoney;
	}
	public void setNeedPayMoney(String needPayMoney) {
		this.needPayMoney = needPayMoney;
	}
	public String getShopUserDiscountMsg() {
		return shopUserDiscountMsg;
	}
	public void setShopUserDiscountMsg(String shopUserDiscountMsg) {
		this.shopUserDiscountMsg = shopUserDiscountMsg;
	}
	public String getVipDisCount() {
		return vipDisCount;
	}
	public void setVipDisCount(String vipDisCount) {
		this.vipDisCount = vipDisCount;
	}
	public List<TcsbOrderItemEntity> getPayOrderItemArray() {
		return payOrderItemArray;
	}
	public void setPayOrderItemArray(List<TcsbOrderItemEntity> payOrderItemArray) {
		this.payOrderItemArray = payOrderItemArray;
	}
	public List<TcsbOrderItemEntity> getNopayOrderItemArray() {
		return nopayOrderItemArray;
	}
	public void setNopayOrderItemArray(List<TcsbOrderItemEntity> nopayOrderItemArray) {
		this.nopayOrderItemArray = nopayOrderItemArray;
	}
	public String getOrderParentId() {
		return orderParentId;
	}
	public void setOrderParentId(String orderParentId) {
		this.orderParentId = orderParentId;
	}
	public boolean isNoPayOrder() {
		return noPayOrder;
	}
	public void setNoPayOrder(boolean noPayOrder) {
		this.noPayOrder = noPayOrder;
	}
	public String getFronMoney() {
		return fronMoney;
	}
	public void setFronMoney(String fronMoney) {
		this.fronMoney = fronMoney;
	}
	public String getOrderDeskName() {
		return orderDeskName;
	}
	public void setOrderDeskName(String orderDeskName) {
		this.orderDeskName = orderDeskName;
	}
	public boolean isHasPlatformCut() {
		return hasPlatformCut;
	}
	public void setHasPlatformCut(boolean hasPlatformCut) {
		this.hasPlatformCut = hasPlatformCut;
	}
	public String getPlatformCouponPrice() {
		return platformCouponPrice;
	}
	public void setPlatformCouponPrice(String platformCouponPrice) {
		this.platformCouponPrice = platformCouponPrice;
	}
	public String getShareGoldCoin() {
		return shareGoldCoin;
	}
	public void setShareGoldCoin(String shareGoldCoin) {
		this.shareGoldCoin = shareGoldCoin;
	}
	public String getCouponPrice() {
		return couponPrice;
	}
	public void setCouponPrice(String couponPrice) {
		this.couponPrice = couponPrice;
	}
	public String getNoDiscountMoney() {
		return noDiscountMoney;
	}
	public void setNoDiscountMoney(String noDiscountMoney) {
		this.noDiscountMoney = noDiscountMoney;
	}
	public List<TcsbOrderEntity> getTcsbOrderEntity() {
		return tcsbOrderEntity;
	}
	public void setTcsbOrderEntity(List<TcsbOrderEntity> tcsbOrderEntity) {
		this.tcsbOrderEntity = tcsbOrderEntity;
	}
	
	
	
	

}
