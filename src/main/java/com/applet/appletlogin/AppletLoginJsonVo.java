/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月25日 下午4:58:12
 */
package com.applet.appletlogin;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月25日 下午4:58:12
 */
public class AppletLoginJsonVo {
	
	private String session_key;
	private String expires_in;
	private String openid;
	private String errcode;
	private String errmsg;
	private String hints;
	public String getSession_key() {
		return session_key;
	}
	public void setSession_key(String session_key) {
		this.session_key = session_key;
	}
	public String getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getErrcode() {
		return errcode;
	}
	public void setErrcode(String errcode) {
		this.errcode = errcode;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getHints() {
		return hints;
	}
	public void setHints(String hints) {
		this.hints = hints;
	}
	
	
	
}
