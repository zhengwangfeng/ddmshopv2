/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月8日 下午1:31:07
 */
package com.applet.shopinfo;

import java.util.List;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月8日 下午1:31:07
 */
public class ShopHoursVo {
	
	private String name;
	
	private String businessHours;
	
	private List<?> hours;
	


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public List getHours() {
		return hours;
	}

	public void setHours(List hours) {
		this.hours = hours;
	}



}
