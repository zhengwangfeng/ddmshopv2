package com.tcsb.shopcaritem.entity;

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
 * @Description: 购物明细
 * @author onlineGenerator
 * @date 2017-04-26 21:43:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_shop_car_item", schema = "")
@SuppressWarnings("serial")
public class TcsbShopCarItemEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**食物ID*/
	@Excel(name="食物ID")
	private java.lang.String foodId;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Integer count;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double price;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**更新时间*/
	@Excel(name="更新时间",format = "yyyy-MM-dd")
	private java.util.Date updateTime;
	/**所属购物车*/
	@Excel(name="所属购物车")
	private java.lang.String shopCar;
	
	/**规格组合串**/
	private String foodTasteFun;
	
	private String name;
	
	
	/**是否时价**/
	private boolean currentPrice;
	
	private String StandardId;

	
	//private java.lang.Integer num;
	
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
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="COUNT",nullable=true,length=10)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  单价
	 */
	@Column(name ="PRICE",nullable=true,length=22)
	public java.lang.Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  单价
	 */
	public void setPrice(java.lang.Double price){
		this.price = price;
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
	 *@return: java.lang.String  所属购物车
	 */
	@Column(name ="SHOP_CAR",nullable=true,length=32)
	public java.lang.String getShopCar(){
		return this.shopCar;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属购物车
	 */
	public void setShopCar(java.lang.String shopCar){
		this.shopCar = shopCar;
	}

	public String getFoodTasteFun() {
		return foodTasteFun;
	}

	public void setFoodTasteFun(String foodTasteFun) {
		this.foodTasteFun = foodTasteFun;
	}
	@Transient
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(boolean currentPrice) {
		this.currentPrice = currentPrice;
	}

	@Column(name="standard_id")
	public String getStandardId() {
		return StandardId;
	}

	public void setStandardId(String standardId) {
		StandardId = standardId;
	}

	
/*	public java.lang.Integer getNum() {
		return num;
	}

	public void setNum(java.lang.Integer num) {
		this.num = num;
	}*/
	
	
}
