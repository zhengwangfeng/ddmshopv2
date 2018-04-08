package com.tcsb.platformdiscount.entity;

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
 * @Description: 平台优惠
 * @author onlineGenerator
 * @date 2017-04-13 22:06:14
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_platform_discount", schema = "")
@SuppressWarnings("serial")
public class TcsbPlatformDiscountEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**标题*/
	@Excel(name="标题")
	private java.lang.String title;
	/**抵用面额*/
	@Excel(name="抵用面额")
	private java.lang.Integer cash;
	/**使用条件*/
	@Excel(name="使用条件")
	private java.lang.Integer useCondition;
	/**使用范围*/
	@Excel(name="使用范围")
	private java.lang.String useRange;
	/**发放时间起*/
	@Excel(name="发放时间起",format = "yyyy-MM-dd")
	private java.util.Date releaseTimeBegin;
	/**发放时间止*/
	@Excel(name="发放时间止",format = "yyyy-MM-dd")
	private java.util.Date releaseTimeEnd;
	/**使用有效期起*/
	@Excel(name="使用有效期起",format = "yyyy-MM-dd")
	private java.util.Date expiryDateBegin;
	/**使用有效期止*/
	@Excel(name="使用有效期止",format = "yyyy-MM-dd")
	private java.util.Date expiryDateEnd;
	/**发放数量*/
	@Excel(name="发放数量")
	private java.lang.Integer number;
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
	 *@return: java.lang.String  标题
	 */
	@Column(name ="TITLE",nullable=true,length=32)
	public java.lang.String getTitle(){
		return this.title;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  标题
	 */
	public void setTitle(java.lang.String title){
		this.title = title;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  抵用面额
	 */
	@Column(name ="CASH",nullable=true,length=10)
	public java.lang.Integer getCash(){
		return this.cash;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  抵用面额
	 */
	public void setCash(java.lang.Integer cash){
		this.cash = cash;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  使用条件
	 */
	@Column(name ="USE_CONDITION",nullable=true,length=10)
	public java.lang.Integer getUseCondition(){
		return this.useCondition;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  使用条件
	 */
	public void setUseCondition(java.lang.Integer useCondition){
		this.useCondition = useCondition;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  使用范围
	 */
	@Column(name ="USE_RANGE",nullable=true,length=32)
	public java.lang.String getUseRange(){
		return this.useRange;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  使用范围
	 */
	public void setUseRange(java.lang.String useRange){
		this.useRange = useRange;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发放时间起
	 */
	@Column(name ="RELEASE_TIME_BEGIN",nullable=true)
	public java.util.Date getReleaseTimeBegin(){
		return this.releaseTimeBegin;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发放时间起
	 */
	public void setReleaseTimeBegin(java.util.Date releaseTimeBegin){
		this.releaseTimeBegin = releaseTimeBegin;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  发放时间止
	 */
	@Column(name ="RELEASE_TIME_END",nullable=true)
	public java.util.Date getReleaseTimeEnd(){
		return this.releaseTimeEnd;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  发放时间止
	 */
	public void setReleaseTimeEnd(java.util.Date releaseTimeEnd){
		this.releaseTimeEnd = releaseTimeEnd;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  使用有效期起
	 */
	@Column(name ="EXPIRY_DATE_BEGIN",nullable=true)
	public java.util.Date getExpiryDateBegin(){
		return this.expiryDateBegin;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  使用有效期起
	 */
	public void setExpiryDateBegin(java.util.Date expiryDateBegin){
		this.expiryDateBegin = expiryDateBegin;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  使用有效期止
	 */
	@Column(name ="EXPIRY_DATE_END",nullable=true)
	public java.util.Date getExpiryDateEnd(){
		return this.expiryDateEnd;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  使用有效期止
	 */
	public void setExpiryDateEnd(java.util.Date expiryDateEnd){
		this.expiryDateEnd = expiryDateEnd;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  发放数量
	 */
	@Column(name ="NUMBER",nullable=true,length=10)
	public java.lang.Integer getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  发放数量
	 */
	public void setNumber(java.lang.Integer number){
		this.number = number;
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
