package com.tcsb.salestatistics.entity;

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
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.SequenceGenerator;

import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 商品销量统计
 * @author onlineGenerator
 * @date 2017-05-05 15:04:20
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_sale_statistics", schema = "")
@SuppressWarnings("serial")
public class TcsbSaleStatisticsEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**食物ID*/
	@Excel(name="食物ID")
	private java.lang.String foodId;
	/**销量*/
	@Excel(name="销量")
	private java.lang.Integer count;
	/**foodName*/
	@Excel(name="食物名称")
	private java.lang.String foodName;
	
	/**price*//*
	@Excel(name="单价")
	private java.lang.Double price;*/
	/**所属店铺*/
	@Excel(name="所属店铺")
	private java.lang.String shopId;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**用户openid*/
	@Excel(name="openid")
	private java.lang.String userId;
	
	private Double totalPrice;
	@Transient
	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

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
	 *@return: java.lang.String  食物ID
	 */
	@Column(name ="FOOD_ID",nullable=true,length=32)
	public java.lang.String getFoodId(){
		return this.foodId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  食物ID
	 */
	public void setFoodId(java.lang.String foodId){
		this.foodId = foodId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  销量
	 */
	@Column(name ="COUNT",nullable=true,length=10)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  销量
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  食物名称
	 */
	@Column(name ="FOOD_NAME",nullable=true,length=32)
	public java.lang.String getFoodName(){
		return this.foodName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  食物名称
	 */
	public void setFoodName(java.lang.String foodName){
		this.foodName = foodName;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
/*	@Column(name ="PRICE",nullable=true,length=22)
	public java.lang.Double getPrice(){
		return this.price;
	}*/

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
/*	public void setPrice(java.lang.Double price){
		this.price = price;
	}*/
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属店铺
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属店铺
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
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
	 *@return: java.lang.String  食物ID
	 */
	@Column(name ="user_id",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  食物ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
}
