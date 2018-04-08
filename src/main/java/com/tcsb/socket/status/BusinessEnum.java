package com.tcsb.socket.status;

public enum BusinessEnum {
	BUSINESSORDER("order","订单通知消息");
	private BusinessEnum(String code,String msg){
		this.code = code;
		this.msg = msg;
	}
	private String code;
	private String msg;
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
}
