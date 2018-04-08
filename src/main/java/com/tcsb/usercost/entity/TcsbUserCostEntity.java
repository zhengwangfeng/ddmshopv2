package com.tcsb.usercost.entity;

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
 * @Description: 用户消费记录
 * @author onlineGenerator
 * @date 2017-11-03 17:31:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_user_cost", schema = "")
@SuppressWarnings("serial")
public class TcsbUserCostEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**用户消费*/
	@Excel(name="用户消费")
	private java.lang.Double cost;
	/**所属店铺*/
	private java.lang.String shopId;
	/**用户的唯一标识*/
	private java.lang.String oppenid;
	/**消费时间*/
	private java.util.Date createDate;
	/**桌位*/
	@Excel(name="桌位")
	private java.lang.String deskId;

	/**会员id*/
	private String memberUserId;

	/**账户余额*/
	private Double balance;

	/**订单号*/
	private String orderId;

	@Column(name ="order_id",nullable=true,length=32)
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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


	@Column(name ="member_user_id",nullable=false,length=32)
	public String getMemberUserId() {
		return memberUserId;
	}

	public void setMemberUserId(String memberUserId) {
		this.memberUserId = memberUserId;
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
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  用户消费
	 */
	@Column(name ="COST",nullable=true,length=22)
	public java.lang.Double getCost(){
		return this.cost;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  用户消费
	 */
	public void setCost(java.lang.Double cost){
		this.cost = cost;
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
	 *@return: java.util.Date  消费时间
	 */
	@Column(name ="CREATE_DATE",nullable=true)
	public java.util.Date getCreateDate(){
		return this.createDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  消费时间
	 */
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  桌位
	 */
	@Column(name ="DESK_ID",nullable=true,length=32)
	public java.lang.String getDeskId(){
		return this.deskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  桌位
	 */
	public void setDeskId(java.lang.String deskId){
		this.deskId = deskId;
	}
}
