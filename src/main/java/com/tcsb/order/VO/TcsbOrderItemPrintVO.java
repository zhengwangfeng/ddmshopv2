package com.tcsb.order.VO;

public class TcsbOrderItemPrintVO {
   private String foodName;
   private double count;
   private Double price;
   private String unitName;
   private String funFoodTaste;
   private String foodId;
public String getFoodId() {
	return foodId;
}
public String getUnitName() {
	return unitName;
}
public void setUnitName(String unitName) {
	this.unitName = unitName;
}
public void setFoodId(String foodId) {
	this.foodId = foodId;
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
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public String getFunFoodTaste() {
	return funFoodTaste;
}
public void setFunFoodTaste(String funFoodTaste) {
	this.funFoodTaste = funFoodTaste;
}
}
