package com.tcsb.callservice.vo;

import com.tcsb.callservice.entity.TcsbCallServiceEntity;

public class TcsbCallServiceEntityVo extends TcsbCallServiceEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4580172892739096346L;
	
	private String serviceName;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}


}
