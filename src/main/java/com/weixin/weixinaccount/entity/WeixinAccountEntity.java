package com.weixin.weixinaccount.entity;

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
 * @Description: 微信公众号
 * @author onlineGenerator
 * @date 2017-03-27 16:38:32
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_account", schema = "")
@SuppressWarnings("serial")
public class WeixinAccountEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**公众帐号名称*/
	@Excel(name="公众帐号名称")
	private java.lang.String accountname;
	/**公众帐号TOKEN*/
	@Excel(name="公众帐号TOKEN")
	private java.lang.String accounttoken;
	/**公众微信号*/
	@Excel(name="公众微信号")
	private java.lang.String accountnumber;
	/**公众号类型*/
	@Excel(name="公众号类型")
	private java.lang.String accounttype;
	/**电子邮箱*/
	@Excel(name="电子邮箱")
	private java.lang.String accountemail;
	/**公众帐号描述*/
	@Excel(name="公众帐号描述")
	private java.lang.String accountdesc;
	/**公众号at*/
	@Excel(name="公众号at")
	private java.lang.String accountaccesstoken;
	/**公众帐号APPID*/
	@Excel(name="公众帐号APPID")
	private java.lang.String accountappid;
	/**公众帐号As*/
	@Excel(name="公众帐号As")
	private java.lang.String accountappsecret;
	/**USERNAME*/
	private java.lang.String username;
	/**公众号原始ID*/
	@Excel(name="公众号原始ID")
	private java.lang.String weixinAccountid;
	/**apiticket*/
	private java.lang.String apiticket;
	/**apiticketttime*/
	private java.util.Date apiticketttime;
	/**jsapiticket*/
	private java.lang.String jsapiticket;
	/**jsapitickett*/
	private java.util.Date jsapitickettime;
	/**
	 * token生成时间
	 */
	private Date addtoekntime;
	@Column(name ="ADDTOEKNTIME",nullable=true)
	
	public Date getAddtoekntime() {
		return addtoekntime;
	}

	public void setAddtoekntime(Date addtoekntime) {
		this.addtoekntime = addtoekntime;
	}
	/**更新人名字*/
	private java.lang.String updateName;
	/**更新时间*/
	private java.util.Date updateDate;
	/**更新人*/
	private java.lang.String updateBy;
	/**创建人名字*/
	private java.lang.String createName;
	/**创建人*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.util.Date createDate;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
	public java.lang.String getId(){
		return this.id;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  主键
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众帐号名称
	 */
	@Column(name ="ACCOUNTNAME",nullable=true,length=200)
	public java.lang.String getAccountname(){
		return this.accountname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众帐号名称
	 */
	public void setAccountname(java.lang.String accountname){
		this.accountname = accountname;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众帐号TOKEN
	 */
	@Column(name ="ACCOUNTTOKEN",nullable=true,length=200)
	public java.lang.String getAccounttoken(){
		return this.accounttoken;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众帐号TOKEN
	 */
	public void setAccounttoken(java.lang.String accounttoken){
		this.accounttoken = accounttoken;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众微信号
	 */
	@Column(name ="ACCOUNTNUMBER",nullable=true,length=200)
	public java.lang.String getAccountnumber(){
		return this.accountnumber;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众微信号
	 */
	public void setAccountnumber(java.lang.String accountnumber){
		this.accountnumber = accountnumber;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众号类型
	 */
	@Column(name ="ACCOUNTTYPE",nullable=true,length=50)
	public java.lang.String getAccounttype(){
		return this.accounttype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众号类型
	 */
	public void setAccounttype(java.lang.String accounttype){
		this.accounttype = accounttype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  电子邮箱
	 */
	@Column(name ="ACCOUNTEMAIL",nullable=true,length=200)
	public java.lang.String getAccountemail(){
		return this.accountemail;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  电子邮箱
	 */
	public void setAccountemail(java.lang.String accountemail){
		this.accountemail = accountemail;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众帐号描述
	 */
	@Column(name ="ACCOUNTDESC",nullable=true,length=500)
	public java.lang.String getAccountdesc(){
		return this.accountdesc;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众帐号描述
	 */
	public void setAccountdesc(java.lang.String accountdesc){
		this.accountdesc = accountdesc;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众号at
	 */
	@Column(name ="ACCOUNTACCESSTOKEN",nullable=true,length=1000)
	public java.lang.String getAccountaccesstoken(){
		return this.accountaccesstoken;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众号at
	 */
	public void setAccountaccesstoken(java.lang.String accountaccesstoken){
		this.accountaccesstoken = accountaccesstoken;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众帐号APPID
	 */
	@Column(name ="ACCOUNTAPPID",nullable=true,length=200)
	public java.lang.String getAccountappid(){
		return this.accountappid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众帐号APPID
	 */
	public void setAccountappid(java.lang.String accountappid){
		this.accountappid = accountappid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众帐号As
	 */
	@Column(name ="ACCOUNTAPPSECRET",nullable=true,length=500)
	public java.lang.String getAccountappsecret(){
		return this.accountappsecret;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众帐号As
	 */
	public void setAccountappsecret(java.lang.String accountappsecret){
		this.accountappsecret = accountappsecret;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  USERNAME
	 */
	@Column(name ="USERNAME",nullable=true,length=50)
	public java.lang.String getUsername(){
		return this.username;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  USERNAME
	 */
	public void setUsername(java.lang.String username){
		this.username = username;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众号原始ID
	 */
	@Column(name ="WEIXIN_ACCOUNTID",nullable=true,length=100)
	public java.lang.String getWeixinAccountid(){
		return this.weixinAccountid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众号原始ID
	 */
	public void setWeixinAccountid(java.lang.String weixinAccountid){
		this.weixinAccountid = weixinAccountid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  apiticket
	 */
	@Column(name ="APITICKET",nullable=true,length=200)
	public java.lang.String getApiticket(){
		return this.apiticket;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  apiticket
	 */
	public void setApiticket(java.lang.String apiticket){
		this.apiticket = apiticket;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  apiticketttime
	 */
	@Column(name ="APITICKETTTIME",nullable=true)
	public java.util.Date getApiticketttime(){
		return this.apiticketttime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  apiticketttime
	 */
	public void setApiticketttime(java.util.Date apiticketttime){
		this.apiticketttime = apiticketttime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  jsapiticket
	 */
	@Column(name ="JSAPITICKET",nullable=true,length=200)
	public java.lang.String getJsapiticket(){
		return this.jsapiticket;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  jsapiticket
	 */
	public void setJsapiticket(java.lang.String jsapiticket){
		this.jsapiticket = jsapiticket;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  jsapitickett
	 */
	@Column(name ="JSAPITICKETTIME",nullable=true)
	public java.util.Date getJsapitickettime(){
		return this.jsapitickettime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  jsapitickett
	 */
	public void setJsapitickettime(java.util.Date jsapitickettime){
		this.jsapitickettime = jsapitickettime;
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
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  更新时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
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
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
}
