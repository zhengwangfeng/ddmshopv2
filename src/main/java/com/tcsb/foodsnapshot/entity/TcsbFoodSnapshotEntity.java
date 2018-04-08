package com.tcsb.foodsnapshot.entity;

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
 * @Description: 食物快照用于同步
 * @author onlineGenerator
 * @date 2017-11-08 10:55:23
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_food_snapshot", schema = "")
@SuppressWarnings("serial")
public class TcsbFoodSnapshotEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String name;
	/**价格*/
	@Excel(name="价格")
	private java.lang.Double price;
	/**所属店铺*/
	@Excel(name="所属店铺")
	private java.lang.String shopId;
	/**菜品ID*/
	@Excel(name="菜品ID")
	private java.lang.String foodTypeId;
	/**图片*/
	@Excel(name="图片")
	private java.lang.String img;
	/**折扣价*/
	@Excel(name="折扣价")
	private java.lang.Double discountPrice;
	/**是否支持外卖*/
	@Excel(name="是否支持外卖")
	private java.lang.String istakeout;
	/**是否是特色*/
	@Excel(name="是否是特色")
	private java.lang.String ischara;
	/**菜状态*/
	@Excel(name="菜状态")
	private java.lang.String status;
	/**更新人名字*/
	private java.lang.String updateName;
	/**更新时间*/
	private java.util.Date updateDate;
	/**更新人*/
	private java.lang.String updateBy;
	/**创建人名字*/
	@Excel(name="创建人名字")
	private java.lang.String createName;
	/**创建人*/
	private java.lang.String createBy;
	/**创建时间*/
	private java.util.Date createDate;
	/**expiryDate*/
	@Excel(name="expiryDate",format = "yyyy-MM-dd")
	private java.util.Date expiryDate;
	/**isDis*/
	@Excel(name="isDis")
	private java.lang.String isDis;
	/**unitid*/
	@Excel(name="unitid")
	private java.lang.String unitid;
	/**isDelete*/
	private java.lang.Integer isDelete;
	/**orders*/
	@Excel(name="orders")
	private java.lang.Integer orders;
	
	
	private java.lang.Integer snapStatus;
	
	
	@Column(name ="snap_status",nullable=false,length=1)
	public java.lang.Integer getSnapStatus() {
		return snapStatus;
	}

	public void setSnapStatus(java.lang.Integer snapStatus) {
		this.snapStatus = snapStatus;
	}

	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
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
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=64)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  价格
	 */
	@Column(name ="PRICE",nullable=true,length=22)
	public java.lang.Double getPrice(){
		return this.price;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  价格
	 */
	public void setPrice(java.lang.Double price){
		this.price = price;
	}
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
	 *@return: java.lang.String  图片
	 */
	@Column(name ="IMG",nullable=true,length=250)
	public java.lang.String getImg(){
		return this.img;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setImg(java.lang.String img){
		this.img = img;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  折扣价
	 */
	@Column(name ="DISCOUNT_PRICE",nullable=true,length=22)
	public java.lang.Double getDiscountPrice(){
		return this.discountPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  折扣价
	 */
	public void setDiscountPrice(java.lang.Double discountPrice){
		this.discountPrice = discountPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否支持外卖
	 */
	@Column(name ="ISTAKEOUT",nullable=true,length=1)
	public java.lang.String getIstakeout(){
		return this.istakeout;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否支持外卖
	 */
	public void setIstakeout(java.lang.String istakeout){
		this.istakeout = istakeout;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否是特色
	 */
	@Column(name ="ISCHARA",nullable=true,length=1)
	public java.lang.String getIschara(){
		return this.ischara;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否是特色
	 */
	public void setIschara(java.lang.String ischara){
		this.ischara = ischara;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  菜状态
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  菜状态
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  expiryDate
	 */
	@Column(name ="EXPIRY_DATE",nullable=true)
	public java.util.Date getExpiryDate(){
		return this.expiryDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  expiryDate
	 */
	public void setExpiryDate(java.util.Date expiryDate){
		this.expiryDate = expiryDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  isDis
	 */
	@Column(name ="IS_DIS",nullable=true,length=1)
	public java.lang.String getIsDis(){
		return this.isDis;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  isDis
	 */
	public void setIsDis(java.lang.String isDis){
		this.isDis = isDis;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  unitid
	 */
	@Column(name ="UNITID",nullable=true,length=32)
	public java.lang.String getUnitid(){
		return this.unitid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  unitid
	 */
	public void setUnitid(java.lang.String unitid){
		this.unitid = unitid;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  isDelete
	 */
	@Column(name ="IS_DELETE",nullable=true,length=10)
	public java.lang.Integer getIsDelete(){
		return this.isDelete;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  isDelete
	 */
	public void setIsDelete(java.lang.Integer isDelete){
		this.isDelete = isDelete;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  orders
	 */
	@Column(name ="ORDERS",nullable=true,length=10)
	public java.lang.Integer getOrders(){
		return this.orders;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  orders
	 */
	public void setOrders(java.lang.Integer orders){
		this.orders = orders;
	}
}
