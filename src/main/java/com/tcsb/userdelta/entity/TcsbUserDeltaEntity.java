package com.tcsb.userdelta.entity;

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

import com.sun.star.lib.uno.environments.java.java_environment;

/**   
 * @Title: Entity
 * @Description: 用户充值记录
 * @author onlineGenerator
 * @date 2017-11-03 17:28:58
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_user_delta", schema = "")
@SuppressWarnings("serial")
public class TcsbUserDeltaEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**冲值金额*/
	@Excel(name="冲值金额")
	private java.lang.Double amount;
	/**会员id*/
	private String memberUserId;
	/**真实充值金额*/
	private Double realAmount;

	/**所属店铺*/
	private java.lang.String shopId;
	/**用户的唯一标识*/
	private java.lang.String oppenid;
	/**冲值时间*/
	private java.util.Date createDate;
	/*赠送额度*/
	private Double towardsLimit;
	/*冲值状态*/
	private Integer status;

	/**支付方式 1线下 0线上*/
	private java.lang.String payMethod;

	/**支付工具 1现金 2银联卡 3微信 4支付宝*/
	private java.lang.String payUtil;

	/**赠送金额*/
	private Double presentAmount;

	/**账户余额*/
	private Double balance;

	@Column(name ="real_amount",nullable=true,length=22)
	public Double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  账户余额
	 */
	@Column(name ="BALANCE",nullable=true,length=22)
	public java.lang.Double getBalance(){
		return this.balance;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  账户余额
	 */
	public void setBalance(java.lang.Double balance){
		this.balance = balance;
	}

	@Column(name ="present_amount",nullable=true,length=22)
	public Double getPresentAmount() {
		return presentAmount;
	}

	public void setPresentAmount(Double presentAmount) {
		this.presentAmount = presentAmount;
	}

	@Column(name ="pay_util",nullable=true,length=1)
	public String getPayUtil() {
		return payUtil;
	}

	public void setPayUtil(String payUtil) {
		this.payUtil = payUtil;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name ="pay_method",nullable=true,length=1)
	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	@Column(name ="member_user_id",nullable=false,length=32)
	public String getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(String memberUserId) {
		this.memberUserId = memberUserId;
	}

	@Column(name ="towards_limit",nullable=false,length=32)
	public Double getTowardsLimit() {
		return towardsLimit;
	}

	public void setTowardsLimit(Double towardsLimit) {
		this.towardsLimit = towardsLimit;
	}
	
	@Column(name ="order_no",nullable=false,length=32)
	public java.lang.String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(java.lang.String orderNo) {
		this.orderNo = orderNo;
	}

	private java.lang.String orderNo;
	
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  冲值金额
	 */
	@Column(name ="AMOUNT",nullable=true,length=22)
	public java.lang.Double getAmount(){
		return this.amount;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  冲值金额
	 */
	public void setAmount(java.lang.Double amount){
		this.amount = amount;
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
	 *@return: java.lang.String  用户的唯一标识
	 */
	@Column(name ="OPPENID",nullable=true,length=32)
	public java.lang.String getOppenid(){
		return this.oppenid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户的唯一标识
	 */
	public void setOppenid(java.lang.String oppenid){
		this.oppenid = oppenid;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  冲值时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  冲值时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
}
