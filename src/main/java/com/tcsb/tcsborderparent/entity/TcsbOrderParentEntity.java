package com.tcsb.tcsborderparent.entity;

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
 * @Description: 订单父级关联
 * @author onlineGenerator
 * @date 2017-10-30 13:55:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_order_parent", schema = "")
@SuppressWarnings("serial")
public class TcsbOrderParentEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**店铺ID*/
	@Excel(name="店铺ID")
	private java.lang.String shopId;
	/**桌位ID*/
	@Excel(name="桌位ID")
	private java.lang.String deskId;
	/**订单状态1正在使用0已清台*/
	@Excel(name="订单状态1正在使用0已清台")
	private java.lang.String orderStatus;
	
	/**订单是否执行支付操作1正在操作0未操作**/
	private String orderLock;
	
	
	/**用餐人数*/
    @Excel(name="用餐人数")
	private java.lang.Integer people;
    
    private Double frontMoney;

    private String DepositId;
    
    private String flag;
    
    private String useFrontMoney;
    
    private Double payMoney;
    
    private Double notPayMoney;

    private Integer deskNum;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	@Column(name ="DEPOSIT_ID",nullable=true,length=32)
	public String getDepositId() {
		return DepositId;
	}

	public void setDepositId(String depositId) {
		DepositId = depositId;
	}

	

	@Transient
	public Double getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(Double payMoney) {
		this.payMoney = payMoney;
	}
	@Transient
	public Double getNotPayMoney() {
		return notPayMoney;
	}

	public void setNotPayMoney(Double notPayMoney) {
		this.notPayMoney = notPayMoney;
	}
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

	@Column(name ="desk_num",nullable=true,length=32)
	public Integer getDeskNum() {
		return deskNum;
	}

	public void setDeskNum(Integer deskNum) {
		this.deskNum = deskNum;
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
	 *@return: java.lang.String  店铺ID
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  店铺ID
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  桌位ID
	 */
	@Column(name ="DESK_ID",nullable=true,length=32)
	public java.lang.String getDeskId(){
		return this.deskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  桌位ID
	 */
	public void setDeskId(java.lang.String deskId){
		this.deskId = deskId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单状态1正在使用0已清台
	 */
	@Column(name ="ORDER_STATUS",nullable=true,length=1)
	public java.lang.String getOrderStatus(){
		return this.orderStatus;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单状态1正在使用0已清台
	 */
	public void setOrderStatus(java.lang.String orderStatus){
		this.orderStatus = orderStatus;
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

	@Column(name ="order_lock",nullable=true)
	public String getOrderLock() {
		return orderLock;
	}

	public void setOrderLock(String orderLock) {
		this.orderLock = orderLock;
	}
	@Column(name ="front_money")
	public Double getFrontMoney() {
		return frontMoney;
	}
	public void setFrontMoney(Double frontMoney) {
		this.frontMoney = frontMoney;
	}
	
	
	/**押金是否使用1使用**/
	@Column(name ="use_front_money")
	public String getUseFrontMoney() {
		return useFrontMoney;
	}

	public void setUseFrontMoney(String useFrontMoney) {
		this.useFrontMoney = useFrontMoney;
	}
	
}
