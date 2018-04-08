/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月26日 下午5:39:57
 */
package com.applet.shopcar;

import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * ddmShopV2
 *     
 *
 * @author Mar_x
 * create on 2017年10月26日 下午5:39:57
 */
public class TcsbShopCarItemVo {
	
	/**id*/
	private java.lang.String id;
	/**食物ID*/
	private java.lang.String foodId;
	/**数量*/
	private java.lang.Integer count;
	/**单价*/
	private java.lang.Double price;

	/**所属购物车*/
	private java.lang.String shopCar;
	
	/**规格组合串**/
	private String foodTasteFun;
	
	private String foodname;
	/**是否时价**/
	private boolean currentPrice;
	
	/**是否有可选规格**/
	private boolean foodTaste;
	/****/
	private Integer num;
	
	private String standardId;
	private boolean foodstandard;

	public java.lang.String getId() {
		return id;
	}

	public void setId(java.lang.String id) {
		this.id = id;
	}

	public java.lang.String getFoodId() {
		return foodId;
	}

	public void setFoodId(java.lang.String foodId) {
		this.foodId = foodId;
	}

	public java.lang.Integer getCount() {
		return count;
	}

	public void setCount(java.lang.Integer count) {
		this.count = count;
	}

	public java.lang.Double getPrice() {
		return price;
	}

	public void setPrice(java.lang.Double price) {
		this.price = price;
	}

	public java.lang.String getShopCar() {
		return shopCar;
	}

	public void setShopCar(java.lang.String shopCar) {
		this.shopCar = shopCar;
	}

	public String getFoodTasteFun() {
		return foodTasteFun;
	}

	public void setFoodTasteFun(String foodTasteFun) {
		this.foodTasteFun = foodTasteFun;
	}

	public String getFoodname() {
		return foodname;
	}

	public void setFoodname(String foodname) {
		this.foodname = foodname;
	}



	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}



	public boolean isFoodTaste() {
		return foodTaste;
	}

	public void setFoodTaste(boolean foodTaste) {
		this.foodTaste = foodTaste;
	}

	public boolean isCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(boolean currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Override
	public String toString() {
		return "TcsbShopCarItemVo [id=" + id + ", foodId=" + foodId + ", count=" + count + ", price=" + price
				+ ", shopCar=" + shopCar + ", foodTasteFun=" + foodTasteFun + ", foodname=" + foodname
				+ ", currentPrice=" + currentPrice + ", foodTaste=" + foodTaste + ", num=" + num + "]";
	}

	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	public boolean isFoodstandard() {
		return foodstandard;
	}

	public void setFoodstandard(boolean foodstandard) {
		this.foodstandard = foodstandard;
	}











	
	
	
}
