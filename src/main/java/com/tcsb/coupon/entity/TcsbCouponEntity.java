package com.tcsb.coupon.entity;

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
 * @Description: 优惠卷
 * @author onlineGenerator
 * @date 2017-05-09 22:03:22
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_coupon", schema = "")
@SuppressWarnings("serial")
public class TcsbCouponEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属店铺*/
	@Excel(name="所属店铺")
	private java.lang.String shopId;
	/**所属用户*/
	@Excel(name="所属用户")
	private java.lang.String userId;
	
//	coupon_type优惠券类型0商户投放1分享领取2平台投放
	private String couponType;
	
	private String unionid;
	
	private String shareUnionid;
	
	
	/**满减模版*/
	@Excel(name="满减模版")
	private java.lang.String fullcutTemplateId;
	/**折扣*/
	private java.lang.Integer useRebate;
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
	/**使用状态0未使用1已使用2已过期*/
	@Excel(name="使用状态")
	private String useStatus;
	/**使用状态*/
	@Excel(name="使用时间")
	private Date useTime;
	/**有效期*/
	@Excel(name="有效期")
	private Date expiryDate;
	/**所属菜品*/
	@Excel(name="所属菜品")
	private String foodTypeId;

//	private String unionid;

	private String mobile;

	@Column(name ="mobile",nullable=true,length=12)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

//	@Column(name ="unionid",nullable=true,length=32)
//	public String getUnionid() {
//		return unionid;
//	}
//
//	public void setUnionid(String unionid) {
//		this.unionid = unionid;
//	}

	/**使用范围*/
	@Excel(name="使用范围")
	private String useRange;
	@Column(name ="use_range",nullable=true,length=1)
	public String getUseRange() {
		return useRange;
	}

	public void setUseRange(String useRange) {
		this.useRange = useRange;
	}

	/**所属食物*/
	@Excel(name="所属食物")
	private String foodId;
	@Column(name ="food_type_id",nullable=true,length=32)
	public String getFoodTypeId() {
		return foodTypeId;
	}

	public void setFoodTypeId(String foodTypeId) {
		this.foodTypeId = foodTypeId;
	}
	@Column(name ="food_id",nullable=true,length=32)
	public String getFoodId() {
		return foodId;
	}

	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}

	@Column(name ="expiry_date",nullable=true)
	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	@Column(name ="use_time",nullable=true)
	public Date getUseTime() {
		return useTime;
	}

	public void setUseTime(Date useTime) {
		this.useTime = useTime;
	}

	@Column(name ="use_status",nullable=true,length=1)
	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  满减模版
	 */
	@Column(name ="FULLCUT_TEMPLATE_ID",nullable=true,length=32)
	public java.lang.String getFullcutTemplateId(){
		return this.fullcutTemplateId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  满减模版
	 */
	public void setFullcutTemplateId(java.lang.String fullcutTemplateId){
		this.fullcutTemplateId = fullcutTemplateId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  折扣
	 */
	@Column(name ="USE_REBATE",nullable=true,length=10)
	public java.lang.Integer getUseRebate(){
		return this.useRebate;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  折扣
	 */
	public void setUseRebate(java.lang.Integer useRebate){
		this.useRebate = useRebate;
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

	
	@Column(name ="coupon_type")
	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
	}

	
	@Column(name ="share_unionid")
	public String getShareUnionid() {
		return shareUnionid;
	}

	public void setShareUnionid(String shareUnionid) {
		this.shareUnionid = shareUnionid;
	}
}
