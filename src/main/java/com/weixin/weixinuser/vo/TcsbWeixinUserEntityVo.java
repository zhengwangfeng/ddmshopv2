/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月22日 上午11:33:10
 */
package com.weixin.weixinuser.vo;

import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月22日 上午11:33:10
 */
public class TcsbWeixinUserEntityVo extends TcsbWeixinUserEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1276123358548691476L;
	
	//消费次数
	private int saleCount;
	//消费店铺数
	private int saleShopCount;
	//消费金额
	private String saleMoney;
	//均消费金额
	private String avgSaleMoney;
	public int getSaleCount() {
		return saleCount;
	}
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	public int getSaleShopCount() {
		return saleShopCount;
	}
	public void setSaleShopCount(int saleShopCount) {
		this.saleShopCount = saleShopCount;
	}
	public String getSaleMoney() {
		return saleMoney;
	}
	public void setSaleMoney(String saleMoney) {
		this.saleMoney = saleMoney;
	}
	public String getAvgSaleMoney() {
		return avgSaleMoney;
	}
	public void setAvgSaleMoney(String avgSaleMoney) {
		this.avgSaleMoney = avgSaleMoney;
	}
	
	

}
