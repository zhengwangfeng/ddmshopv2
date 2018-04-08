
package com.tcsb.order.page;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecgframework.poi.excel.annotation.ExcelCollection;

/**   
 * @Title: Entity
 * @Description: 订单管理
 * @author onlineGenerator
 * @date 2017-03-08 10:41:50
 * @version V1.0   
 *
 */
public class TcsbOrderPage implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**订单编号*/
    @Excel(name="订单编号")
	private java.lang.String orderNo;
	/**下单方式*/
    @Excel(name="下单方式")
	private java.lang.Integer method;
	/**用户ID*/
    @Excel(name="用户ID")
	private java.lang.Integer userId;
	/**所属店铺ID*/
    @Excel(name="所属店铺ID")
	private java.lang.String shopId;
	/**下单时间*/
    @Excel(name="下单时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**订单状态*/
    @Excel(name="订单状态")
	private java.lang.Integer status;
	/**实际消费*/
    @Excel(name="实际消费")
	private java.lang.Double totalPrice;
	/**优惠价*/
    @Excel(name="优惠价")
	private java.lang.Double disPrice;
	/**优惠方式*/
    @Excel(name="优惠方式")
	private java.lang.String dMethod;
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
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户ID
	 */
	public java.lang.Integer getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户ID
	 */
	public void setUserId(java.lang.Integer userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  所属店铺ID
	 */
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
	 */
	public java.lang.Double getDisPrice(){
		return this.disPrice;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  优惠价
	 */
	public void setDisPrice(java.lang.Double disPrice){
		this.disPrice = disPrice;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠方式
	 */
	public java.lang.String getDMethod(){
		return this.dMethod;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠方式
	 */
	public void setDMethod(java.lang.String dMethod){
		this.dMethod = dMethod;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付状态
	 */
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

	/**保存-订单项管理*/
    @ExcelCollection(name="订单项管理")
	private List<TcsbOrderItemEntity> tcsbOrderItemList = new ArrayList<TcsbOrderItemEntity>();
		public List<TcsbOrderItemEntity> getTcsbOrderItemList() {
		return tcsbOrderItemList;
		}
		public void setTcsbOrderItemList(List<TcsbOrderItemEntity> tcsbOrderItemList) {
		this.tcsbOrderItemList = tcsbOrderItemList;
		}
}
