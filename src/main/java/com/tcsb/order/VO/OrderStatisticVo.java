package com.tcsb.order.VO;

public class OrderStatisticVo {
	private String id;
	//创建时间
	private String createdate;
	//总营业额
	private String totalprice;
	
	private String realyprice;
	
	private String vipDiscountPrice;
	
	private String offlinepaybyvipcard;
	
	//实际收款
	private String disprice;
	//线上支付——线上实际收款
	private String onlinePayment;
	/**
	 * 线下支付
	 */
	private String offlinePayment;
	/**
	 * 平台需结算
	 */
	private String platformSettlement;
	/**
	 * 平台优惠
	 */
	private String platformdiscountprice;
	/**
	 * 商户优惠
	 */
	private String specialcouponprice;
	/**
	 * 通用卷
	 */
	private String universalcouponprice;
	
	/**
	 * 线下则扣价
	 */
	private String offlineDiscount;
	//线上营收
	private String onlineIncome;
	
	//线下营收
	private String offlineIncome;
	
	
	private String  offlinepaybycard ;
	private String  offlinepaybywechat;
	private String  offlinepaybyaply;
	private String  offlinepaybycash;
	private String  offlinepaybycredit;
	private String  offlinepaybyteam;
	
	
	private String offlinediscountprice;
	
	
	public String getOfflinepaybycard() {
		return offlinepaybycard;
	}
	public void setOfflinepaybycard(String offlinepaybycard) {
		this.offlinepaybycard = offlinepaybycard;
	}
	public String getOfflinepaybywechat() {
		return offlinepaybywechat;
	}
	public void setOfflinepaybywechat(String offlinepaybywechat) {
		this.offlinepaybywechat = offlinepaybywechat;
	}
	public String getOfflinepaybyaply() {
		return offlinepaybyaply;
	}
	public void setOfflinepaybyaply(String offlinepaybyaply) {
		this.offlinepaybyaply = offlinepaybyaply;
	}
	public String getOfflinepaybycash() {
		return offlinepaybycash;
	}
	public void setOfflinepaybycash(String offlinepaybycash) {
		this.offlinepaybycash = offlinepaybycash;
	}
	public String getOfflinepaybycredit() {
		return offlinepaybycredit;
	}
	public void setOfflinepaybycredit(String offlinepaybycredit) {
		this.offlinepaybycredit = offlinepaybycredit;
	}
	public String getPlatformdiscountprice() {
		return platformdiscountprice;
	}
	public void setPlatformdiscountprice(String platformdiscountprice) {
		this.platformdiscountprice = platformdiscountprice;
	}
	public String getSpecialcouponprice() {
		return specialcouponprice;
	}
	public void setSpecialcouponprice(String specialcouponprice) {
		this.specialcouponprice = specialcouponprice;
	}
	public String getUniversalcouponprice() {
		return universalcouponprice;
	}
	public void setUniversalcouponprice(String universalcouponprice) {
		this.universalcouponprice = universalcouponprice;
	}
	public String getCreatedate() {
		return createdate;
	}
	public void setCreatedate(String createdate) {
		this.createdate = createdate;
	}
	public String getTotalprice() {
		return totalprice;
	}
	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
	public String getDisprice() {
		return disprice;
	}
	public void setDisprice(String disprice) {
		this.disprice = disprice;
	}
	public String getOnlinePayment() {
		return onlinePayment;
	}
	public void setOnlinePayment(String onlinePayment) {
		this.onlinePayment = onlinePayment;
	}
	public String getOfflinePayment() {
		return offlinePayment;
	}
	public void setOfflinePayment(String offlinePayment) {
		this.offlinePayment = offlinePayment;
	}
	public String getPlatformSettlement() {
		return platformSettlement;
	}
	public void setPlatformSettlement(String platformSettlement) {
		this.platformSettlement = platformSettlement;
	}
	@Override
	public String toString() {
		return "OrderStatisticVo [createdate=" + createdate + ", totalprice=" + totalprice + ", disprice=" + disprice
				+ ", onlinePayment=" + onlinePayment + ", offlinePayment=" + offlinePayment + ", platformSettlement="
				+ platformSettlement + "]";
	}
	public String getOfflineDiscount() {
		return offlineDiscount;
	}
	public void setOfflineDiscount(String offlineDiscount) {
		this.offlineDiscount = offlineDiscount;
	}
	public String getOnlineIncome() {
		return onlineIncome;
	}
	public void setOnlineIncome(String onlineIncome) {
		this.onlineIncome = onlineIncome;
	}
	public String getOfflineIncome() {
		return offlineIncome;
	}
	public void setOfflineIncome(String offlineIncome) {
		this.offlineIncome = offlineIncome;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfflinepaybyteam() {
		return offlinepaybyteam;
	}
	public void setOfflinepaybyteam(String offlinepaybyteam) {
		this.offlinepaybyteam = offlinepaybyteam;
	}
	public String getOfflinediscountprice() {
		return offlinediscountprice;
	}
	public void setOfflinediscountprice(String offlinediscountprice) {
		this.offlinediscountprice = offlinediscountprice;
	}
	public String getRealyprice() {
		return realyprice;
	}
	public void setRealyprice(String realyprice) {
		this.realyprice = realyprice;
	}
	public String getVipDiscountPrice() {
		return vipDiscountPrice;
	}
	public void setVipDiscountPrice(String vipDiscountPrice) {
		this.vipDiscountPrice = vipDiscountPrice;
	}
	public String getOfflinepaybyvipcard() {
		return offlinepaybyvipcard;
	}
	public void setOfflinepaybyvipcard(String offlinepaybyvipcard) {
		this.offlinepaybyvipcard = offlinepaybyvipcard;
	}



	
	
}
