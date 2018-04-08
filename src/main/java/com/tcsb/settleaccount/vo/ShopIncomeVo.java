/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年6月8日 下午2:45:01
 */
package com.tcsb.settleaccount.vo;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年6月8日 下午2:45:01
 */
public class ShopIncomeVo {

		/** ID */
		private java.lang.String id;
		/** 名称 */
		private java.lang.String name;
	    //创建时间
		private String createdate;
		//总营业额
		private String totalprice;
		//实际收款
		private String disprice;
		//线上支付
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

		public java.lang.String getId() {
			return id;
		}

		public void setId(java.lang.String id) {
			this.id = id;
		}

		public java.lang.String getName() {
			return name;
		}

		public void setName(java.lang.String name) {
			this.name = name;
		}
		
		
	
}
