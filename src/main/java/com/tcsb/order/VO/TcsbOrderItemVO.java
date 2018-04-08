package com.tcsb.order.VO;

public class TcsbOrderItemVO {
	private String name;
	private double count;
	private String foodId;
	private String foodTypeId;
	private double price;
	private String foodName;
	private String foodTasteFun;
	private boolean istaste;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public String getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getFoodTasteFun() {
		return foodTasteFun;
	}
	public void setFoodTasteFun(String foodTasteFun) {
		this.foodTasteFun = foodTasteFun;
	}
	public boolean isIstaste() {
		return istaste;
	}
	public void setIstaste(boolean istaste) {
		this.istaste = istaste;
	}
	
}
