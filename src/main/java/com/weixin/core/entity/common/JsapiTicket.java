package com.weixin.core.entity.common;

public class JsapiTicket {
	// 获取到的票据
	private String ticket;
	// 票据有效时间，单位：秒
	private int expiresIn;
	public String getTicket() {
		return ticket;
	}
	public void setTicket(String ticket) {
		this.ticket = ticket;
	}
	public int getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(int expiresIn) {
		this.expiresIn = expiresIn;
	}
}
