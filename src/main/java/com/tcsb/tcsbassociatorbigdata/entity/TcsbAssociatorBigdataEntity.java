package com.tcsb.tcsbassociatorbigdata.entity;

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
 * @Description: 会员信息统计
 * @author onlineGenerator
 * @date 2017-07-18 11:37:59
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_associator_bigdata", schema = "")
@SuppressWarnings("serial")
public class TcsbAssociatorBigdataEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**会员id*/
	@Excel(name="会员id")
	private java.lang.String userId;
	/**会员昵称*/
	@Excel(name="会员昵称")
	private java.lang.String userNickname;
	/**总消费次数*/
	@Excel(name="总消费次数")
	private java.lang.String saleCount;
	/**消费总额*/
	@Excel(name="消费总额")
	private java.lang.Double saleTotal;
	/**平均消费额*/
	@Excel(name="平均消费额")
	private java.lang.Double saleAvgTotal;
	/**最近消费时间*/
	@Excel(name="最近消费时间",format = "yyyy-MM-dd")
	private java.util.Date lastSaleTime;
	/**手机号*/
	@Excel(name="手机号")
	private java.lang.String userMobile;
	/**所在城市*/
	@Excel(name="所在城市")
	private java.lang.String userCity;
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
	/**店铺id*/
	@Excel(name="店铺id")
	private java.lang.String shopId;
	
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
	 *@return: java.lang.String  会员id
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会员id
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  会员昵称
	 */
	@Column(name ="USER_NICKNAME",nullable=true,length=128)
	public java.lang.String getUserNickname(){
		return this.userNickname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  会员昵称
	 */
	public void setUserNickname(java.lang.String userNickname){
		this.userNickname = userNickname;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  总消费次数
	 */
	@Column(name ="SALE_COUNT",nullable=true,length=10)
	public java.lang.String getSaleCount(){
		return this.saleCount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  总消费次数
	 */
	public void setSaleCount(java.lang.String saleCount){
		this.saleCount = saleCount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消费总额
	 */
	@Column(name ="SALE_TOTAL",nullable=true,length=12)
	public java.lang.Double getSaleTotal(){
		return this.saleTotal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消费总额
	 */
	public void setSaleTotal(java.lang.Double saleTotal){
		this.saleTotal = saleTotal;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  平均消费额
	 */
	@Column(name ="SALE_AVG_TOTAL",nullable=true,length=12)
	public java.lang.Double getSaleAvgTotal(){
		return this.saleAvgTotal;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  平均消费额
	 */
	public void setSaleAvgTotal(java.lang.Double saleAvgTotal){
		this.saleAvgTotal = saleAvgTotal;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  最近消费时间
	 */
	@Column(name ="LAST_SALE_TIME",nullable=true)
	public java.util.Date getLastSaleTime(){
		return this.lastSaleTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  最近消费时间
	 */
	public void setLastSaleTime(java.util.Date lastSaleTime){
		this.lastSaleTime = lastSaleTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  手机号
	 */
	@Column(name ="USER_MOBILE",nullable=true,length=11)
	public java.lang.String getUserMobile(){
		return this.userMobile;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  手机号
	 */
	public void setUserMobile(java.lang.String userMobile){
		this.userMobile = userMobile;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所在城市
	 */
	@Column(name ="USER_CITY",nullable=true,length=10)
	public java.lang.String getUserCity(){
		return this.userCity;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所在城市
	 */
	public void setUserCity(java.lang.String userCity){
		this.userCity = userCity;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  店铺id
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺id
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
}
