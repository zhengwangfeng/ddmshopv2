package com.tcsb.tcsbdeskreservationitem.entity;

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
 * @Description: tcsb_desk_reservation_item
 * @author onlineGenerator
 * @date 2017-11-08 18:02:33
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_desk_reservation_item", schema = "")
@SuppressWarnings("serial")
public class TcsbDeskReservationItemEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**食物ID*/
	@Excel(name="食物ID")
	private java.lang.String foodId;
	
	private String foodName;
	
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
	/**所属预定单*/
	private java.lang.String reservationId;
	/**foodtastefun*/
	@Excel(name="foodtastefun")
	private java.lang.String foodtastefun;
	
	
	private String isFloat;
	
	private String unitName;
	/**
	 * 商品的数量，指代份数
	 */
	private Integer num;
	
	/**
	 * 是否更新了时价类产品的价格1已更新0未更新
	 */
	private Integer isUpdatePrice;
	
	private String standardId;
	
	private String standardName;
	
	private String IsSetMeal;
	
	@Column(name ="is_float")
	public String getIsFloat() {
		return isFloat;
	}

	public void setIsFloat(String isFloat) {
		this.isFloat = isFloat;
	}
	@Column(name ="unit_name")
	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getIsUpdatePrice() {
		return isUpdatePrice;
	}

	public void setIsUpdatePrice(Integer isUpdatePrice) {
		this.isUpdatePrice = isUpdatePrice;
	}

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
	@Column(name ="RESERVATION_ID",nullable=true,length=32)
	public java.lang.String getReservationId(){
		return this.reservationId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属购物车
	 */
	public void setReservationId(java.lang.String reservationId){
		this.reservationId = reservationId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  foodtastefun
	 */
	@Column(name ="FOODTASTEFUN",nullable=true,length=65535)
	public java.lang.String getFoodtastefun(){
		return this.foodtastefun;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  foodtastefun
	 */
	public void setFoodtastefun(java.lang.String foodtastefun){
		this.foodtastefun = foodtastefun;
	}

	@Column(name ="food_name")
	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

	@Column(name ="Standard_Id")
	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}

	@Column(name ="Standard_Name")
	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	@Column(name ="is_set_meal")
	public String getIsSetMeal() {
		return IsSetMeal;
	}

	public void setIsSetMeal(String isSetMeal) {
		IsSetMeal = isSetMeal;
	}
}
