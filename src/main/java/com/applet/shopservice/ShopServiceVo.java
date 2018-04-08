/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月9日 下午4:24:57
 */
package com.applet.shopservice;

import java.util.List;

import com.tcsb.service.entity.TcsbServiceEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月9日 下午4:24:57
 */
public class ShopServiceVo {
	
	private String parentName;
	
	private String parentid;
	
	private List<TcsbServiceEntity> serviceList;
	
	/**排序*/
	private java.lang.String stateorder;

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public List<TcsbServiceEntity> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<TcsbServiceEntity> serviceList) {
		this.serviceList = serviceList;
	}

	public String getParentid() {
		return parentid;
	}

	public void setParentid(String parentid) {
		this.parentid = parentid;
	}

	public java.lang.String getStateorder() {
		return stateorder;
	}

	public void setStateorder(java.lang.String stateorder) {
		this.stateorder = stateorder;
	}


	
	

}
