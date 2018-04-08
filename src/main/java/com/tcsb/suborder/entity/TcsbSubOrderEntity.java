package com.tcsb.suborder.entity;

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
 * @Description: 用户子订单
 * @author onlineGenerator
 * @date 2017-05-18 17:01:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_sub_order", schema = "")
@SuppressWarnings("serial")
public class TcsbSubOrderEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**订单编号*/
	@Excel(name="订单编号")
	private java.lang.String orderNo;
	/**下单方式*/
	@Excel(name="下单方式")
	private java.lang.Integer method;
	/**所属店铺ID*/
	@Excel(name="所属店铺ID")
	private java.lang.String shopId;
	/**deskId*/
	@Excel(name="deskId")
	private java.lang.String deskId;
	/**下单时间*/
	@Excel(name="下单时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**订单状态*/
	@Excel(name="订单状态")
	private java.lang.Integer status;
	/**实际消费*/
	@Excel(name="实际消费")
	private java.lang.Double totalPrice;
	/**线上价格*/
	@Excel(name="线上价格")
	private java.lang.Double onlinePrice;
	/**线下价格*/
	@Excel(name="线下价格")
	private java.lang.Double offlinePrice;
	/**支付状态*/
	@Excel(name="支付状态")
	private java.lang.String payStatus;
	/**支付方式*/
	@Excel(name="支付方式")
	private java.lang.String payMethod;
	/**用餐时间*/
	@Excel(name="用餐时间",format = "yyyy-MM-dd")
	private java.util.Date eatTime;
	/**用餐人数*/
	@Excel(name="用餐人数")
	private java.lang.Integer people;
	/**特殊说明*/
	@Excel(name="特殊说明")
	private java.lang.String note;
	/**是否包间*/
	@Excel(name="是否包间")
	private java.lang.String isRoom;
	/**口味偏好*/
	@Excel(name="口味偏好")
	private java.lang.String taste;
	/**平台优惠价*/
	@Excel(name="平台优惠价")
	private java.lang.Double platformDiscountPrice;
	/**专用券优惠价*/
	@Excel(name="专用券优惠价")
	private java.lang.Double specialCouponPrice;
	/**通用券优惠价*/
	@Excel(name="通用券优惠价")
	private java.lang.Double universalCouponPrice;
	/**是否已接单*/
	@Excel(name="是否已接单")
	private java.lang.String orderIstake;
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
	/**所属用户*/
	@Excel(name="所属用户")
	private java.lang.String userId;
	
	private String isJustNowServing;
	
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
	 *@return: java.lang.String  订单编号
	 */
	@Column(name ="ORDER_NO",nullable=true,length=32)
	public java.lang.String getOrderNo(){
		return this.orderNo;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单编号
	 */
	public void setOrderNo(java.lang.String orderNo){
		this.orderNo = orderNo;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  下单方式
	 */
	@Column(name ="METHOD",nullable=true,length=10)
	public java.lang.Integer getMethod(){
		return this.method;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  下单方式
	 */
	public void setMethod(java.lang.Integer method){
		this.method = method;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属店铺ID
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属店铺ID
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  deskId
	 */
	@Column(name ="DESK_ID",nullable=true,length=32)
	public java.lang.String getDeskId(){
		return this.deskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  deskId
	 */
	public void setDeskId(java.lang.String deskId){
		this.deskId = deskId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  下单时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  下单时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  订单状态
	 */
	@Column(name ="STATUS",nullable=true,length=10)
	public java.lang.Integer getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  订单状态
	 */
	public void setStatus(java.lang.Integer status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  实际消费
	 */
	@Column(name ="TOTAL_PRICE",nullable=true,length=22)
	public java.lang.Double getTotalPrice(){
		return this.totalPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  实际消费
	 */
	public void setTotalPrice(java.lang.Double totalPrice){
		this.totalPrice = totalPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  线上价格
	 */
	@Column(name ="ONLINE_PRICE",nullable=true,length=22)
	public java.lang.Double getOnlinePrice(){
		return this.onlinePrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  线上价格
	 */
	public void setOnlinePrice(java.lang.Double onlinePrice){
		this.onlinePrice = onlinePrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  线下价格
	 */
	@Column(name ="OFFLINE_PRICE",nullable=true,length=22)
	public java.lang.Double getOfflinePrice(){
		return this.offlinePrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  线下价格
	 */
	public void setOfflinePrice(java.lang.Double offlinePrice){
		this.offlinePrice = offlinePrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付状态
	 */
	@Column(name ="PAY_STATUS",nullable=true,length=1)
	public java.lang.String getPayStatus(){
		return this.payStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付状态
	 */
	public void setPayStatus(java.lang.String payStatus){
		this.payStatus = payStatus;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付方式
	 */
	@Column(name ="PAY_METHOD",nullable=true,length=1)
	public java.lang.String getPayMethod(){
		return this.payMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付方式
	 */
	public void setPayMethod(java.lang.String payMethod){
		this.payMethod = payMethod;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  用餐时间
	 */
	@Column(name ="EAT_TIME",nullable=true)
	public java.util.Date getEatTime(){
		return this.eatTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  用餐时间
	 */
	public void setEatTime(java.util.Date eatTime){
		this.eatTime = eatTime;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用餐人数
	 */
	@Column(name ="PEOPLE",nullable=true,length=10)
	public java.lang.Integer getPeople(){
		return this.people;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用餐人数
	 */
	public void setPeople(java.lang.Integer people){
		this.people = people;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  特殊说明
	 */
	@Column(name ="NOTE",nullable=true,length=128)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  特殊说明
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否包间
	 */
	@Column(name ="IS_ROOM",nullable=true,length=1)
	public java.lang.String getIsRoom(){
		return this.isRoom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否包间
	 */
	public void setIsRoom(java.lang.String isRoom){
		this.isRoom = isRoom;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  口味偏好
	 */
	@Column(name ="TASTE",nullable=true,length=1)
	public java.lang.String getTaste(){
		return this.taste;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  口味偏好
	 */
	public void setTaste(java.lang.String taste){
		this.taste = taste;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  平台优惠价
	 */
	@Column(name ="PLATFORM_DISCOUNT_PRICE",nullable=true,length=22)
	public java.lang.Double getPlatformDiscountPrice(){
		return this.platformDiscountPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  平台优惠价
	 */
	public void setPlatformDiscountPrice(java.lang.Double platformDiscountPrice){
		this.platformDiscountPrice = platformDiscountPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  专用券优惠价
	 */
	@Column(name ="SPECIAL_COUPON_PRICE",nullable=true,length=22)
	public java.lang.Double getSpecialCouponPrice(){
		return this.specialCouponPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  专用券优惠价
	 */
	public void setSpecialCouponPrice(java.lang.Double specialCouponPrice){
		this.specialCouponPrice = specialCouponPrice;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  通用券优惠价
	 */
	@Column(name ="UNIVERSAL_COUPON_PRICE",nullable=true,length=22)
	public java.lang.Double getUniversalCouponPrice(){
		return this.universalCouponPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  通用券优惠价
	 */
	public void setUniversalCouponPrice(java.lang.Double universalCouponPrice){
		this.universalCouponPrice = universalCouponPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已接单
	 */
	@Column(name ="ORDER_ISTAKE",nullable=true,length=1)
	public java.lang.String getOrderIstake(){
		return this.orderIstake;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已接单
	 */
	public void setOrderIstake(java.lang.String orderIstake){
		this.orderIstake = orderIstake;
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

	public String getIsJustNowServing() {
		return isJustNowServing;
	}

	public void setIsJustNowServing(String isJustNowServing) {
		this.isJustNowServing = isJustNowServing;
	}
}
