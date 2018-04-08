package com.tcsb.userorderitem.entity;

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
 * @Description: 用户子订单项
 * @author onlineGenerator
 * @date 2017-05-18 16:56:01
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_user_order_item", schema = "")
@SuppressWarnings("serial")
public class TcsbUserOrderItemEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**所属订单*/
	@Excel(name="所属订单")
	private java.lang.String orderId;
	/**食物ID*/
	@Excel(name="食物ID")
	private java.lang.String foodId;
	/**数量*/
	@Excel(name="数量")
	private double count;
	/**单价*/
	@Excel(name="单价")
	private java.lang.Double price;
	/**菜品ID*/
	@Excel(name="菜品ID")
	private java.lang.String foodTypeId;
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
	
	
	private String foodTasteFun;
	
	private String standardName;
	@Transient
	public String getStandardName() {
		return standardName;
	}

	public void setStandardName(String standardName) {
		this.standardName = standardName;
	}

	/**
	 * 商品的数量，指代份数
	 */
	private Integer num;
	
	
	/**
	 * 是否存在实价菜品1存在0不存在
	 */
	private String isFloat;
	
	/**
	 * 是否更新了时价类产品的价格1已更新0未更新
	 */
	private int isUpdatePrice;
	
	
	
	/**
	 * 订单项与子项订单项关联标志
	 */
	private String orderItemRelation;
	
	
	private java.lang.String foodName;


	private String standardId;
	
	/**
	 * 是否套餐1是0不是
	 */
	private String isSetMeal;
	
	@Column(name ="is_set_meal",nullable=false,length=1)
	public String getIsSetMeal() {
		return isSetMeal;
	}

	public void setIsSetMeal(String isSetMeal) {
		this.isSetMeal = isSetMeal;
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
	 *@return: java.lang.String  所属订单
	 */
	@Column(name ="ORDER_ID",nullable=true,length=32)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属订单
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
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
	public double getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setCount(double count){
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜品ID
	 */
	@Column(name ="FOOD_TYPE_ID",nullable=true,length=32)
	public java.lang.String getFoodTypeId(){
		return this.foodTypeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜品ID
	 */
	public void setFoodTypeId(java.lang.String foodTypeId){
		this.foodTypeId = foodTypeId;
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

	public String getFoodTasteFun() {
		return foodTasteFun;
	}

	public void setFoodTasteFun(String foodTasteFun) {
		this.foodTasteFun = foodTasteFun;
	}

	public int getIsUpdatePrice() {
		return isUpdatePrice;
	}

	public void setIsUpdatePrice(int isUpdatePrice) {
		this.isUpdatePrice = isUpdatePrice;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public String getOrderItemRelation() {
		return orderItemRelation;
	}

	public void setOrderItemRelation(String orderItemRelation) {
		this.orderItemRelation = orderItemRelation;
	}
	@Column(name ="IS_FLOAT",nullable=true)
	public String getIsFloat() {
		return isFloat;
	}

	public void setIsFloat(String isFloat) {
		this.isFloat = isFloat;
	}

	@Column(name ="FOOD_NAME",nullable=true,length=128)
	public java.lang.String getFoodName() {
		return foodName;
	}

	public void setFoodName(java.lang.String foodName) {
		this.foodName = foodName;
	}
	@Column(name = "standard_id")
	public String getStandardId() {
		return standardId;
	}

	public void setStandardId(String standardId) {
		this.standardId = standardId;
	}
}
