/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月25日 下午5:28:08
 */
package com.applet.userinfo;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月25日 下午5:28:08
 */
public class UserInfoVo {

	private String nickName;
	private String avatarUrl;
	private String gender; //性别 0：未知、1：男、2：女
	private String province;
	private String city;
	private String country;
	private String openid;
	private String shopId;
	private String unionId;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatarUrl() {
		return avatarUrl;
	}
	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getShopId() {
		return shopId;
	}
	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getUnionId() {
		return unionId;
	}
	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}
	@Override
	public String toString() {
		return "UserInfoVo [nickName=" + nickName + ", avatarUrl=" + avatarUrl + ", gender=" + gender + ", province="
				+ province + ", city=" + city + ", country=" + country + ", openid=" + openid + ", shopId=" + shopId
				+ ", unionId=" + unionId + "]";
	}

	
	
	
}
