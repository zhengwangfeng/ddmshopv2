package com.tcsb.order.entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.lang.String;
import java.lang.Double;
import java.lang.Integer;
import java.math.BigDecimal;

import javax.xml.soap.Text;

import java.sql.Blob;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.SequenceGenerator;

import org.jeecgframework.poi.excel.annotation.Excel;

import com.sun.star.lib.uno.environments.java.java_environment;
import com.weixin.weixinmenu.entity.WeixinMenuEntity;

/**   
 * @Title: Entity
 * @Description: 订单管理
 * @author onlineGenerator
 * @date 2017-03-08 10:41:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_order", schema = "")
@SuppressWarnings("serial")
public class TcsbOrderEntity implements java.io.Serializable {
	/**订单编号用ID*/
	private java.lang.String id;
	/**父级订单Id**/
	private String orderParentId;
	/**订单编号*/
    @Excel(name="订单编号")
	private java.lang.String orderNo;
	/**下单方式*/
    @Excel(name="下单方式")
	private java.lang.String method;
	
	/**所属店铺ID*/
    @Excel(name="所属店铺ID")
	private java.lang.String shopId;
    @Excel(name="口味偏好")
    private String taste;
    
    /**下单时间*/
    @Excel(name="下单时间",format = "yyyy-MM-dd hh:mm:ss")
	private java.util.Date createTime;
	/**订单状态*/
    @Excel(name="订单状态")
	private java.lang.String status;
	/**实际消费*/
    @Excel(name="实际消费")
	private java.lang.Double totalPrice;
    
    
    /**优惠价*/
    /* @Excel(name="优惠价")
 	private java.lang.Double disPrice;*/
 	/**优惠方式*//*
     @Excel(name="优惠方式")
 	private java.lang.String dMethod;*/
 	/**支付状态*/
     @Excel(name="支付状态")
 	private java.lang.String payStatus;
 	/**支付方式 0线上1线下*/
     @Excel(name="支付方式")
 	private java.lang.String payMethod;
     /**付款用户ID*/
 	private java.lang.String userId;
 	/**支付时间**/
 	private java.util.Date payTime;
 	/**用餐时间*/
     @Excel(name="用餐时间",format = "yyyy-MM-dd hh:mm:ss")
 	private java.util.Date eatTime;
 	
 	/**特殊说明*/
     @Excel(name="特殊说明")
 	private java.lang.String note;
 	/**是否包间*/
     @Excel(name="是否包间")
 	private java.lang.String isRoom;
     @Excel(name="平台优惠Id")
     private String platformDiscount;
     @Excel(name="平台优惠价")
     private Double platformDiscountPrice;
     @Excel(name="专用券优惠价")
     private Double specialCouponPrice;
     @Excel(name="通用券优惠价")
     private Double universalCouponPrice;

     /**会员折扣金额*/
     private Double vipDiscountPrice;

	 /**手动折扣金额*/
	 private Double handDiscountPrice;
     
     @Excel(name="是否已接单")
     private String orderIstake;
     @Excel(name="线上价格")
     private Double onlinePrice;
     @Excel(name="线下价格")
     private Double offlinePrice;
     private String deskName;
	 private Integer people;
	 
	 
	 private String shareGoldCoin;

	@Transient
	public Integer getPeople() {
		return people;
	}

	public void setPeople(Integer people) {
		this.people = people;
	}

	private String offlinePayMethod;
     //是否立即上菜
     private String isJustNowServing;

     /**线下抹零*/
     private String offlineDiscount;
    
     @Excel(name="所属桌位ID")
     private String deskId;
     //用户优惠券
     private String universalCoupon;
     
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
 	
 	//=================================
 	/**预定订单扩展**/
 	
 	
 	//=================================
    public String getTaste() {
		return taste;
	}
	public void setTaste(String taste) {
		this.taste = taste;
	}

	
    @Column(name ="DESK_ID",nullable=true,length=32)
	public String getDeskId() {
		return deskId;
	}

	public void setDeskId(String deskId) {
		this.deskId = deskId;
	}

	@Column(name ="handDiscountPrice",nullable=true,length=22)
	public Double getHandDiscountPrice() {
		return handDiscountPrice;
	}

	public void setHandDiscountPrice(Double handDiscountPrice) {
		this.handDiscountPrice = handDiscountPrice;
	}

	@Transient
    public String getDeskName() {
		return deskName;
	}
	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}

   
   /* private TcsbOrderEntity tcsbOrderEntity;
	
    private List<TcsbOrderEntity> tcsbOrderEntities;
    
    @ManyToOne(fetch = FetchType.EAGER)
   	@JoinColumn(name = "parent_id")
	public TcsbOrderEntity getTcsbOrderEntity() {
		return tcsbOrderEntity;
	}
	public void setTcsbOrderEntity(TcsbOrderEntity tcsbOrderEntity) {
		this.tcsbOrderEntity = tcsbOrderEntity;
	}*/
	/*  
    @Excel(name="专用券")
    private String specialCoupon;
    @Excel(name="通用券")
    private String universalCoupon;*/
    @Column(name ="online_price",nullable=true,length=20)
   public Double getOnlinePrice() {
		return onlinePrice;
	}
	public void setOnlinePrice(Double onlinePrice) {
		this.onlinePrice = onlinePrice;
	}
	@Column(name ="offline_price",nullable=true,length=20)
	public Double getOfflinePrice() {
		return offlinePrice;
	}
	/*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tcsbOrderEntity")
	public List<TcsbOrderEntity> getTcsbOrderEntities() {
		return tcsbOrderEntities;
	}
	public void setTcsbOrderEntities(List<TcsbOrderEntity> tcsbOrderEntities) {
		this.tcsbOrderEntities = tcsbOrderEntities;
	}*/
	public void setOfflinePrice(Double offlinePrice) {
		this.offlinePrice = offlinePrice;
	}
	@Column(name ="platform_discount",nullable=true,length=1)
    public String getPlatformDiscount() {
		return platformDiscount;
	}
	public void setPlatformDiscount(String platformDiscount) {
		this.platformDiscount = platformDiscount;
	}
	/* 
	@Column(name ="special_coupon",nullable=true,length=1)
	public String getSpecialCoupon() {
		return specialCoupon;
	}
	public void setSpecialCoupon(String specialCoupon) {
		this.specialCoupon = specialCoupon;
	}
	@Column(name ="universal_coupon",nullable=true,length=1)
	public String getUniversalCoupon() {
		return universalCoupon;
	}
	public void setUniversalCoupon(String universalCoupon) {
		this.universalCoupon = universalCoupon;
	}*/
	@Column(name ="order_istake",nullable=true,length=1)
    public String getOrderIstake() {
		return orderIstake;
	}
	public void setOrderIstake(String orderIstake) {
		this.orderIstake = orderIstake;
	}
	@Column(name ="platform_discount_price",nullable=true,length=22)
	public Double getPlatformDiscountPrice() {
		return platformDiscountPrice;
	}
	public void setPlatformDiscountPrice(Double platformDiscountPrice) {
		this.platformDiscountPrice = platformDiscountPrice;
	}
	@Column(name ="special_coupon_price",nullable=true,length=22)
	public Double getSpecialCouponPrice() {
		return specialCouponPrice;
	}
	public void setSpecialCouponPrice(Double specialCouponPrice) {
		this.specialCouponPrice = specialCouponPrice;
	}
	@Column(name ="universal_coupon_price",nullable=true,length=22)
	public Double getUniversalCouponPrice() {
		return universalCouponPrice;
	}
	public void setUniversalCouponPrice(Double universalCouponPrice) {
		this.universalCouponPrice = universalCouponPrice;
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
	public java.lang.String getMethod(){
		return this.method;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  下单方式
	 */
	public void setMethod(java.lang.String method){
		this.method = method;
	}
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户ID
	 */
	
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单状态
	 */
	
	@Column(name ="STATUS",nullable=true,length=10)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单状态
	 */
	public void setStatus(java.lang.String status){
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
	 *@return: java.lang.Double  优惠价
	 *//*
	
	@Column(name ="DIS_PRICE",nullable=true,length=22)
	public java.lang.Double getDisPrice(){
		return this.disPrice;
	}

	*//**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠价
	 *//*
	public void setDisPrice(java.lang.Double disPrice){
		this.disPrice = disPrice;
	}
	*/
	
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
	@Column(name ="OfflinePayMethod",nullable=true)
	public String getOfflinePayMethod() {
		return offlinePayMethod;
	}
	public void setOfflinePayMethod(String offlinePayMethod) {
		this.offlinePayMethod = offlinePayMethod;
	}
	public String getIsJustNowServing() {
		return isJustNowServing;
	}
	public void setIsJustNowServing(String isJustNowServing) {
		this.isJustNowServing = isJustNowServing;
	}
	
	@Column(name ="offline_discount",nullable=true)
	public String getOfflineDiscount() {
		return offlineDiscount;
	}
	
	
	public void setOfflineDiscount(String offlineDiscount) {
		this.offlineDiscount = offlineDiscount;
	}
	
	@Column(name ="order_parent_id")
	public String getOrderParentId() {
		return orderParentId;
	}
	public void setOrderParentId(String orderParentId) {
		this.orderParentId = orderParentId;
	}
	
	@Column(name ="universal_coupon")
	public String getUniversalCoupon() {
		return universalCoupon;
	}

	public void setUniversalCoupon(String universalCoupon) {
		this.universalCoupon = universalCoupon;
	}
	
	
	public Double getVipDiscountPrice() {
		return vipDiscountPrice;
	}
	public void setVipDiscountPrice(Double vipDiscountPrice) {
		this.vipDiscountPrice = vipDiscountPrice;
		
	}

	@Column(name ="PAY_TIME",nullable=true)
	public java.util.Date getPayTime() {
		return payTime;
	}

	public void setPayTime(java.util.Date payTime) {
		this.payTime = payTime;
	}

	@Column(name ="SHARE_GOLD_COIN")
	public String getShareGoldCoin() {
		return shareGoldCoin;
	}

	public void setShareGoldCoin(String shareGoldCoin) {
		this.shareGoldCoin = shareGoldCoin;
	}

	
}