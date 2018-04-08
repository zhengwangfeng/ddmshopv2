/**
 * jeecgos
 * @author Mar_x
 * create on 2017 2017年5月23日 上午11:43:21
 */
package com.tcsb.shopcollect.vo;

import com.tcsb.shopcollect.entity.TcsbShopCollectEntity;

/**
 * jeecgos
 *
 *
 * @author Mar_x
 * create on 2017年5月23日 上午11:43:21
 */
public class TcsbShopCollectEntityVo extends TcsbShopCollectEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1736981829425191486L;
	
	
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
