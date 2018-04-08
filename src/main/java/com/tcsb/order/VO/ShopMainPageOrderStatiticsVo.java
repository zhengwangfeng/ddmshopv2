package com.tcsb.order.VO;
/**
 * 
 * jeecgos
 * 商家首页营业收入统计
 *
 * @author Mar_x
 * create on 2017年5月19日 下午2:38:18
 */
public class ShopMainPageOrderStatiticsVo {
	
	
	private Double todayIncome;
	private Double avgDataIncome;
	private Double totalIncme;//总营业额
	private Double onlineIncome;
	private Double offlineIncome;
	private Double monthTotalIncme;
	private Double monthAvgIncme;
	private int todayJoinVip;

	
	
	public Double getTodayIncome() {
		return todayIncome;
	}
	public void setTodayIncome(Double todayIncome) {
		this.todayIncome = todayIncome;
	}
	public Double getAvgDataIncome() {
		return avgDataIncome;
	}
	public void setAvgDataIncome(Double avgDataIncome) {
		this.avgDataIncome = avgDataIncome;
	}
	public Double getTotalIncme() {
		return totalIncme;
	}
	public void setTotalIncme(Double totalIncme) {
		this.totalIncme = totalIncme;
	}
	public Double getOnlineIncome() {
		return onlineIncome;
	}
	public void setOnlineIncome(Double onlineIncome) {
		this.onlineIncome = onlineIncome;
	}
	public Double getOfflineIncome() {
		return offlineIncome;
	}
	public void setOfflineIncome(Double offlineIncome) {
		this.offlineIncome = offlineIncome;
	}
	public Double getMonthTotalIncme() {
		return monthTotalIncme;
	}
	public void setMonthTotalIncme(Double monthTotalIncme) {
		this.monthTotalIncme = monthTotalIncme;
	}
	public Double getMonthAvgIncme() {
		return monthAvgIncme;
	}
	public void setMonthAvgIncme(Double monthAvgIncme) {
		this.monthAvgIncme = monthAvgIncme;
	}
	public int getTodayJoinVip() {
		return todayJoinVip;
	}
	public void setTodayJoinVip(int todayJoinVip) {
		this.todayJoinVip = todayJoinVip;
	}
	
	

}
