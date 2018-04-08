package com.tcsb.memberuser.entity;

import java.math.BigDecimal;
import java.util.Date;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;
import javax.xml.soap.Text;
import java.sql.Blob;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 会员信息
 * @author onlineGenerator
 * @date 2018-01-03 11:06:30
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_member_user", schema = "")
@SuppressWarnings("serial")
public class TcsbMemberUserEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**卡号*/
	@Excel(name="卡号")
	private java.lang.String cardNo;
	/**会员级别ID*/
	@Excel(name="会员级别ID")
	private java.lang.String membershipLevelId;
	/**账户余额*/
	private java.lang.Double balance;
	/**用户的唯一标识*/
	private java.lang.String openid;
	/**用户昵称*/
	@Excel(name="用户昵称")
	private java.lang.String nickname;
	/**用户名*/
	@Excel(name="用户名")
	private java.lang.String name;
	/**用户性别*/
	@Excel(name="用户性别")
	private java.lang.String sex;
	/**出生日期*/
	@Excel(name="出生日期",format = "yyyy-MM-dd")
	private java.util.Date birthOfDate;
	/**省份*/
	private java.lang.String province;
	/**城市*/
	private java.lang.String city;
	/**国家*/
	private java.lang.String country;
	/**用户头像*/
	private java.lang.String headimgurl;
	/**用户特权信息*/
	private java.lang.String privilege;
	/**创建时间*/
	private java.util.Date createTime;
	/**商家ID*/
	@Excel(name="商家ID")
	private java.lang.String shopId;
	/**联系方式*/
	@Excel(name="联系方式")
	private java.lang.String mobile;
	/**备注*/
	@Excel(name="备注")
	private java.lang.String note;
	/**更新人名字*/
	private java.lang.String updateName;
	/**更新时间*/
	private java.util.Date updateTime;
	/**更新人*/
	private java.lang.String updateBy;
	/**创建人名字*/
	private java.lang.String createName;
	/**创建人*/
	private java.lang.String createBy;

	/**短信验证码*/
	private java.lang.String smscode;

	/**发送时间*/
	private java.util.Date sendtime;

	/*会员状态(1启用，2停用)*/
	private String status;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=32)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  id
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  卡号
	 */
	@Column(name ="CARD_NO",nullable=true,length=32)
	public java.lang.String getCardNo(){
		return this.cardNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  卡号
	 */
	public void setCardNo(java.lang.String cardNo){
		this.cardNo = cardNo;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  会员级别ID
	 */
	@Column(name ="MEMBERSHIP_LEVEL_ID",nullable=true,length=32)
	public java.lang.String getMembershipLevelId(){
		return this.membershipLevelId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会员级别ID
	 */
	public void setMembershipLevelId(java.lang.String membershipLevelId){
		this.membershipLevelId = membershipLevelId;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账户余额
	 */
	@Column(name ="BALANCE",nullable=true,length=22)
	public java.lang.Double getBalance(){
		return this.balance;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账户余额
	 */
	public void setBalance(java.lang.Double balance){
		this.balance = balance;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户的唯一标识
	 */
	@Column(name ="OPENID",nullable=true,length=32)
	public java.lang.String getOpenid(){
		return this.openid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户的唯一标识
	 */
	public void setOpenid(java.lang.String openid){
		this.openid = openid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户昵称
	 */
	@Column(name ="NICKNAME",nullable=true,length=64)
	public java.lang.String getNickname(){
		return this.nickname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户昵称
	 */
	public void setNickname(java.lang.String nickname){
		this.nickname = nickname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户名
	 */
	@Column(name ="NAME",nullable=true,length=64)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户名
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户性别
	 */
	@Column(name ="SEX",nullable=true,length=1)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户性别
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  出生日期
	 */
	@Column(name ="BIRTH_OF_DATE",nullable=true)
	public java.util.Date getBirthOfDate(){
		return this.birthOfDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  出生日期
	 */
	public void setBirthOfDate(java.util.Date birthOfDate){
		this.birthOfDate = birthOfDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  省份
	 */
	@Column(name ="PROVINCE",nullable=true,length=64)
	public java.lang.String getProvince(){
		return this.province;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  省份
	 */
	public void setProvince(java.lang.String province){
		this.province = province;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  城市
	 */
	@Column(name ="CITY",nullable=true,length=64)
	public java.lang.String getCity(){
		return this.city;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  城市
	 */
	public void setCity(java.lang.String city){
		this.city = city;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  国家
	 */
	@Column(name ="COUNTRY",nullable=true,length=32)
	public java.lang.String getCountry(){
		return this.country;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  国家
	 */
	public void setCountry(java.lang.String country){
		this.country = country;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户头像
	 */
	@Column(name ="HEADIMGURL",nullable=true,length=500)
	public java.lang.String getHeadimgurl(){
		return this.headimgurl;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户头像
	 */
	public void setHeadimgurl(java.lang.String headimgurl){
		this.headimgurl = headimgurl;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户特权信息
	 */
	@Column(name ="PRIVILEGE",nullable=true,length=125)
	public java.lang.String getPrivilege(){
		return this.privilege;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户特权信息
	 */
	public void setPrivilege(java.lang.String privilege){
		this.privilege = privilege;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  商家ID
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  商家ID
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系方式
	 */
	@Column(name ="MOBILE",nullable=true,length=11)
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系方式
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  备注
	 */
	@Column(name ="NOTE",nullable=true,length=125)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  备注
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人名字
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=32)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人名字
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  更新时间
	 */
	@Column(name ="UPDATE_TIME",nullable=true)
	public java.util.Date getUpdateTime(){
		return this.updateTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateTime(java.util.Date updateTime){
		this.updateTime = updateTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  更新人
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=32)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  更新人
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人名字
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人名字
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_BY",nullable=true,length=32)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}


	@Column(name ="SMSCODE",nullable=true,length=10)
	public java.lang.String getSmscode(){
		return this.smscode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  smscode
	 */
	public void setSmscode(java.lang.String smscode){
		this.smscode = smscode;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发送时间
	 */
	@Column(name ="SENDTIME",nullable=true)
	public java.util.Date getSendtime(){
		return this.sendtime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发送时间
	 */
	public void setSendtime(java.util.Date sendtime){
		this.sendtime = sendtime;
	}

	@Column(name ="status",nullable=true,length = 1)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
