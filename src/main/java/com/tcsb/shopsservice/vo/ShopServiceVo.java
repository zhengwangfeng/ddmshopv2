package com.tcsb.shopsservice.vo;

import com.tcsb.service.entity.TcsbServiceEntity;

public class ShopServiceVo extends TcsbServiceEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5356298995564073093L;
	
	
	private int state;


	public int getState() {
		return state;
	}


	public void setState(int state) {
		this.state = state;
	}

}
