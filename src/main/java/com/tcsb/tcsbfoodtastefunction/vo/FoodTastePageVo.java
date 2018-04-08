/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年6月22日 下午4:41:33
 */
package com.tcsb.tcsbfoodtastefunction.vo;

import java.util.List;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年6月22日 下午4:41:33
 */
public class FoodTastePageVo {
	
	private String firstFun;
	private String selectTaste;

	private List<FoodTasteVo> foodTasteVo;

	public String getFirstFun() {
		return firstFun;
	}

	public void setFirstFun(String firstFun) {
		this.firstFun = firstFun;
	}

	public List<FoodTasteVo> getFoodTasteVo() {
		return foodTasteVo;
	}

	public void setFoodTasteVo(List<FoodTasteVo> foodTasteVo) {
		this.foodTasteVo = foodTasteVo;
	}

	public String getSelectTaste() {
		return selectTaste;
	}

	public void setSelectTaste(String selectTaste) {
		this.selectTaste = selectTaste;
	}


	

}
