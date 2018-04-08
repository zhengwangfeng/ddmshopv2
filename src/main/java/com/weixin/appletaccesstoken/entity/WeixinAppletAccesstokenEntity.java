package com.weixin.appletaccesstoken.entity;

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
 * @Description: 微信小程序凭证
 * @author onlineGenerator
 * @date 2017-11-13 14:39:03
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_applet_accesstoken", schema = "")
@SuppressWarnings("serial")
public class WeixinAppletAccesstokenEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**凭证*/
	@Excel(name="凭证")
	private java.lang.String accessToken;
	/**凭证有效时间*/
	@Excel(name="凭证有效时间")
	private java.lang.Integer expiresIn;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date createTime;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
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
	 *@param: java.lang.String  ID
	 */
	public void setId(java.lang.String id){
		this.id = id;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  凭证
	 */
	@Column(name ="ACCESS_TOKEN",nullable=true,length=255)
	public java.lang.String getAccessToken(){
		return this.accessToken;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  凭证
	 */
	public void setAccessToken(java.lang.String accessToken){
		this.accessToken = accessToken;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  凭证有效时间
	 */
	@Column(name ="EXPIRES_IN",nullable=true,length=10)
	public java.lang.Integer getExpiresIn(){
		return this.expiresIn;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  凭证有效时间
	 */
	public void setExpiresIn(java.lang.Integer expiresIn){
		this.expiresIn = expiresIn;
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
}
