package com.tcsb.order.VO;

import java.util.Date;
import java.util.List;

import com.tcsb.printer.entity.TcsbPrinterEntity;

public class TcsbOrderPrintVO {
	private String shopName;
	private String deskName;
	private String orderNo;
	private String phone;
	private String offlineDiscount;
	public String getOfflineDiscount() {
		return offlineDiscount;
	}

	public void setOfflineDiscount(String offlineDiscount) {
		this.offlineDiscount = offlineDiscount;
	}

	private String note;
	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	private Date createTime;
	private Double totalMoney;
	private Double platformDiscountPrice;
	private Double specialCouponPrice;
	private Double universalCouponPrice;
	private Double finalMoney;
	private String wechatUrl;
	private String isJustNowServing;
	private List<TcsbPrinterEntity> tcsbPrinterEntities;
	public List<TcsbPrinterEntity> getTcsbPrinterEntities() {
		return tcsbPrinterEntities;
	}

	public void setTcsbPrinterEntities(List<TcsbPrinterEntity> tcsbPrinterEntities) {
		this.tcsbPrinterEntities = tcsbPrinterEntities;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Double getPlatformDiscountPrice() {
		return platformDiscountPrice;
	}

	public void setPlatformDiscountPrice(Double platformDiscountPrice) {
		this.platformDiscountPrice = platformDiscountPrice;
	}

	public Double getSpecialCouponPrice() {
		return specialCouponPrice;
	}

	public void setSpecialCouponPrice(Double specialCouponPrice) {
		this.specialCouponPrice = specialCouponPrice;
	}

	public Double getUniversalCouponPrice() {
		return universalCouponPrice;
	}

	public void setUniversalCouponPrice(Double universalCouponPrice) {
		this.universalCouponPrice = universalCouponPrice;
	}

	private List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs;

	public Double getTotalMoney() {
		return totalMoney;
	}

	public void setTotalMoney(Double totalMoney) {
		this.totalMoney = totalMoney;
	}


	public Double getFinalMoney() {
		return finalMoney;
	}

	public void setFinalMoney(Double finalMoney) {
		this.finalMoney = finalMoney;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDeskName() {
		return deskName;
	}

	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getWechatUrl() {
		return wechatUrl;
	}

	public void setWechatUrl(String wechatUrl) {
		this.wechatUrl = wechatUrl;
	}

	public List<TcsbOrderItemPrintVO> getTcsbOrderItemPrintVOs() {
		return tcsbOrderItemPrintVOs;
	}

	public void setTcsbOrderItemPrintVOs(
			List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs) {
		this.tcsbOrderItemPrintVOs = tcsbOrderItemPrintVOs;
	}

	public String getIsJustNowServing() {
		return isJustNowServing;
	}

	public void setIsJustNowServing(String isJustNowServing) {
		this.isJustNowServing = isJustNowServing;
	}
}
