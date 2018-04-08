/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月23日 下午1:57:48
 */
package com.tcsb.coupon.vo;

import java.util.Date;

import com.tcsb.coupon.entity.TcsbCouponEntity;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月23日 下午1:57:48
 */
public class TcsbCouponEntityVo extends TcsbCouponEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2371475154330082281L;
	
	private String shopName;
	
	private String userName;
	
	private String fullcutTemplateIdName;
	
	private String expiryDate;

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFullcutTemplateIdName() {
		return fullcutTemplateIdName;
	}

	public void setFullcutTemplateIdName(String fullcutTemplateIdName) {
		this.fullcutTemplateIdName = fullcutTemplateIdName;
	}

	
}
