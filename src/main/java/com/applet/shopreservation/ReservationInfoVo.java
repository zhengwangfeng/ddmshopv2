/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月30日 下午6:32:08
 */
package com.applet.shopreservation;

import java.util.List;

import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月30日 下午6:32:08
 */
public class ReservationInfoVo {
	
	
	private TcsbDeskReservationEntity reservation;
	
	private List<TcsbDeskReservationItemEntity> tcsbOrderItemArray;
	
	private String reservationDeskName;
	
	private String finalMoney;
	public String getFinalMoney() {
		return finalMoney;
	}

	private String needPayMoney;
	
	private String universalCouponPrice;
	
	private String alreadyPaid;
	
	public String getAlreadyPaid() {
		return alreadyPaid;
	}

	public void setAlreadyPaid(String alreadyPaid) {
		this.alreadyPaid = alreadyPaid;
	}

	private String shopUserDiscountMsg;
	
	private String vipDisCount;
	
	//是否要立即
	private Boolean isPay;
	
	private String depositeMoney;




	public String getDepositeMoney() {
		return depositeMoney;
	}

	public void setDepositeMoney(String depositeMoney) {
		this.depositeMoney = depositeMoney;
	}

	public Boolean getIsPay() {
		return isPay;
	}

	public void setIsPay(Boolean isPay) {
		this.isPay = isPay;
	}

	public void setFinalMoney(String finalMoney) {
		this.finalMoney = finalMoney;
	}

	public String getNeedPayMoney() {
		return needPayMoney;
	}

	public void setNeedPayMoney(String needPayMoney) {
		this.needPayMoney = needPayMoney;
	}

	public String getUniversalCouponPrice() {
		return universalCouponPrice;
	}

	public void setUniversalCouponPrice(String universalCouponPrice) {
		this.universalCouponPrice = universalCouponPrice;
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

	public List<TcsbDeskReservationItemEntity> getTcsbOrderItemArray() {
		return tcsbOrderItemArray;
	}

	public void setTcsbOrderItemArray(List<TcsbDeskReservationItemEntity> tcsbOrderItemArray) {
		this.tcsbOrderItemArray = tcsbOrderItemArray;
	}

	public TcsbDeskReservationEntity getReservation() {
		return reservation;
	}

	public void setReservation(TcsbDeskReservationEntity reservation) {
		this.reservation = reservation;
	}

	public String getReservationDeskName() {
		return reservationDeskName;
	}

	public void setReservationDeskName(String reservationDeskName) {
		this.reservationDeskName = reservationDeskName;
	}
	

	

}
