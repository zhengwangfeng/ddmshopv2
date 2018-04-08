package com.tcsb.platformcoupon.entity;

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
 * @Description: 用户平台优惠券
 * @author onlineGenerator
 * @date 2018-01-27 10:29:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_platform_coupon", schema = "")
@SuppressWarnings("serial")
public class TcsbPlatformCouponEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属用户*/
	@Excel(name="所属用户")
	private java.lang.String userId;
	/**手机*/
	@Excel(name="手机")
	private java.lang.String mobile;
	/**使用状态*/
	@Excel(name="使用状态")
	private java.lang.String useStatus;
	/**满减模版*/
	@Excel(name="满减模版")
	private java.lang.String fullcutTemplateId;
	/**使用时间*/
	@Excel(name="使用时间",format = "yyyy-MM-dd")
	private java.util.Date useTime;
	/**有效期*/
	@Excel(name="有效期",format = "yyyy-MM-dd")
	private java.util.Date expiryDate;
	/**是否新用户优惠券*/
	@Excel(name="是否新用户优惠券")
	private java.lang.String isNewuserCouon;
	/**折扣*/
	@Excel(name="折扣")
	private java.lang.Integer useRebate;
	/**更新人名字*/
	@Excel(name="更新人名字")
	private java.lang.String updateName;
	/**更新时间*/
	@Excel(name="更新时间",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**更新人*/
	@Excel(name="更新人")
	private java.lang.String updateBy;
	/**创建人名字*/
	@Excel(name="创建人名字")
	private java.lang.String createName;
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createBy;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	
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
	 *@return: java.lang.String  所属用户
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属用户
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机
	 */
	@Column(name ="MOBILE",nullable=true,length=32)
	public java.lang.String getMobile(){
		return this.mobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机
	 */
	public void setMobile(java.lang.String mobile){
		this.mobile = mobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  使用状态
	 */
	@Column(name ="USE_STATUS",nullable=true,length=1)
	public java.lang.String getUseStatus(){
		return this.useStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  使用状态
	 */
	public void setUseStatus(java.lang.String useStatus){
		this.useStatus = useStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  满减模版
	 */
	@Column(name ="FULLCUT_TEMPLATE_ID",nullable=true,length=32)
	public java.lang.String getFullcutTemplateId(){
		return this.fullcutTemplateId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  满减模版
	 */
	public void setFullcutTemplateId(java.lang.String fullcutTemplateId){
		this.fullcutTemplateId = fullcutTemplateId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  使用时间
	 */
	@Column(name ="USE_TIME",nullable=true)
	public java.util.Date getUseTime(){
		return this.useTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  使用时间
	 */
	public void setUseTime(java.util.Date useTime){
		this.useTime = useTime;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  有效期
	 */
	@Column(name ="EXPIRY_DATE",nullable=true)
	public java.util.Date getExpiryDate(){
		return this.expiryDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  有效期
	 */
	public void setExpiryDate(java.util.Date expiryDate){
		this.expiryDate = expiryDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否新用户优惠券
	 */
	@Column(name ="IS_NEWUSER_COUON",nullable=true,length=1)
	public java.lang.String getIsNewuserCouon(){
		return this.isNewuserCouon;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否新用户优惠券
	 */
	public void setIsNewuserCouon(java.lang.String isNewuserCouon){
		this.isNewuserCouon = isNewuserCouon;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  折扣
	 */
	@Column(name ="USE_REBATE",nullable=true,length=10)
	public java.lang.Integer getUseRebate(){
		return this.useRebate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  折扣
	 */
	public void setUseRebate(java.lang.Integer useRebate){
		this.useRebate = useRebate;
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
