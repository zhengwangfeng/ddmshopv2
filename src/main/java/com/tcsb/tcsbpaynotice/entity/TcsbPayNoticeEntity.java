package com.tcsb.tcsbpaynotice.entity;

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
 * @Description: 支付消息通知
 * @author onlineGenerator
 * @date 2017-06-21 14:21:27
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_pay_notice", schema = "")
@SuppressWarnings("serial")
public class TcsbPayNoticeEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属店铺*/
	@Excel(name="所属店铺")
	private java.lang.String shopId;
	/**所属桌位*/
	@Excel(name="所属桌位")
	private java.lang.String deskId;
	/**支付金额*/
	@Excel(name="支付金额")
	private java.lang.String payMoney;
	/**是否已读*/
	@Excel(name="是否已读")
	private java.lang.String isread;
	/**支付时间*/
	@Excel(name="支付时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**
	 * 消息的订单号
	 */
	private String orderNo;
	

	private String deskName;
	
	private String shopName;
	@Transient
	public String getDeskName() {
		return deskName;
	}

	public void setDeskName(String deskName) {
		this.deskName = deskName;
	}
	@Transient
	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
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
	 *@return: java.lang.String  所属桌位
	 */
	@Column(name ="DESK_ID",nullable=true,length=32)
	public java.lang.String getDeskId(){
		return this.deskId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属桌位
	 */
	public void setDeskId(java.lang.String deskId){
		this.deskId = deskId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  支付金额
	 */
	@Column(name ="PAY_MONEY",nullable=true,length=32)
	public java.lang.String getPayMoney(){
		return this.payMoney;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  支付金额
	 */
	public void setPayMoney(java.lang.String payMoney){
		this.payMoney = payMoney;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已读
	 */
	@Column(name ="ISREAD",nullable=true,length=1)
	public java.lang.String getIsread(){
		return this.isread;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已读
	 */
	public void setIsread(java.lang.String isread){
		this.isread = isread;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  支付时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  支付时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
}
