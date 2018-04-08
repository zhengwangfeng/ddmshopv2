/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月23日 下午1:37:34
 */
package com.tcsb.shopevaluate.vo;

import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月23日 下午1:37:34
 */
public class TcsbShopEvaluateEntityVo extends TcsbShopEvaluateEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6597353682319763957L;
	
	private String userName;

	private String shopName;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
	
}
