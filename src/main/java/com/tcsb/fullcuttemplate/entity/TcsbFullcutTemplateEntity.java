package com.tcsb.fullcuttemplate.entity;

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
 * @Description: 满减模版
 * @author onlineGenerator
 * @date 2017-04-13 17:49:50
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_fullcut_template", schema = "")
@SuppressWarnings("serial")
public class TcsbFullcutTemplateEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**所属店铺*/
	private java.lang.String shopId;
	/**使用期限*/
	@Excel(name="使用期限")
	private Integer usePeriod;
	
	/**日期单位*/
	@Excel(name="日期单位")
	private String dateUnit;
	
	/**满额*/
	@Excel(name="满额")
	private java.lang.Integer total;
	/**立减*/
	@Excel(name="立减")
	private java.lang.Integer discount;
	
	//标记该条优惠券是否显示到模板列表中
	private String isShow;
		
	private String couponId;
		
		
	private String useRange;
		
	private String foodTypeId;
		
	private String foodId;
	
//	coupon_type优惠券类型0商户投放1分享领取
	private String couponType;
//	share_type分享类型1固定分享金2比例分享金
	private String shareType;
//	share_descript分享金描述
	private String shareDescript;
	
	private Date createTime;
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

	
	@Column(name ="create_time")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	@Column(name ="date_unit",nullable=true,length=6)
	public String getDateUnit() {
		return dateUnit;
	}

	public void setDateUnit(String dateUnit) {
		this.dateUnit = dateUnit;
	}
	@Column(name ="use_period",nullable=true)
	public Integer getUsePeriod() {
		return usePeriod;
	}

	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}
	
	@Transient
	public String getFoodTypeId() {
		return foodTypeId;
	}

	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	@Transient
	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	
	
	@Transient
	public String getUseRange() {
		return useRange;
	}

	public void setUseRange(String useRange) {
		this.useRange = useRange;
	}

	@Transient
	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  满额
	 */
	@Column(name ="TOTAL",nullable=true,length=10)
	public java.lang.Integer getTotal(){
		return this.total;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  满额
	 */
	public void setTotal(java.lang.Integer total){
		this.total = total;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  立减
	 */
	@Column(name ="DISCOUNT",nullable=true,length=10)
	public java.lang.Integer getDiscount(){
		return this.discount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  立减
	 */
	public void setDiscount(java.lang.Integer discount){
		this.discount = discount;
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

	@Column(name ="isshow",nullable=true)
	public String getIsShow() {
		return isShow;
	}

	public void setIsShow(String isShow) {
		this.isShow = isShow;
	}

	@Column(name ="coupon_type")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	@Column(name ="share_type")
	public String getShareType() {
		return shareType;
	}
	
	public void setShareType(String shareType) {
		this.shareType = shareType;
	}
	@Column(name ="share_descript")
	public String getShareDescript() {
		return shareDescript;
	}

	public void setShareDescript(String shareDescript) {
		this.shareDescript = shareDescript;
	}
}
