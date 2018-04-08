/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月19日 下午5:19:25
 */
package com.tcsb.associatorstatistics.vo;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月19日 下午5:19:25
 */
public class UserFoodStatisticsVo {
	
	private String foodname;
	
	private int saleCount;
	
	private int buyCount;
	
	private String avgSaleCount;

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}

	public int getSaleCount() {
		return saleCount;
	}

	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}

	public int getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}

	public String getAvgSaleCount() {
		return avgSaleCount;
	}

	public void setAvgSaleCount(String avgSaleCount) {
		this.avgSaleCount = avgSaleCount;
	} 

	
}
