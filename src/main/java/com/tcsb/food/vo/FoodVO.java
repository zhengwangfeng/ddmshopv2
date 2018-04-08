package com.tcsb.food.vo;

public class FoodVO {
	private String id;
	private String foodTypeId;
	private String name;
	private Double price;
	private String img;
	private Integer num;
	private boolean goodshow;
	private Integer orders;
	
	private boolean foodstandard;
	
	/**
	 * mar_x
	 * 添加商品单位，商品是否有规格,商品是否时价
	 * @return
	 */
	private String unitName;
	private boolean foodTaste;
	private boolean isCurrentPrice;
	public Integer getOrders() {
		return orders;
	}
	public void setOrders(Integer orders) {
		this.orders = orders;
	}
	
	
	public String getId() {
		return id;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public boolean isGoodshow() {
		return goodshow;
	}
	public void setGoodshow(boolean goodshow) {
		this.goodshow = goodshow;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFoodTypeId() {
		return foodTypeId;
	}
	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public boolean isFoodTaste() {
		return foodTaste;
	}
	public void setFoodTaste(boolean foodTaste) {
		this.foodTaste = foodTaste;
	}
	public boolean isCurrentPrice() {
		return isCurrentPrice;
	}
	public void setCurrentPrice(boolean isCurrentPrice) {
		this.isCurrentPrice = isCurrentPrice;
	}
	public boolean isFoodstandard() {
		return foodstandard;
	}
	public void setFoodstandard(boolean foodstandard) {
		this.foodstandard = foodstandard;
	}

	
}
