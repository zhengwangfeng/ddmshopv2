package com.tcsb.settleaccount.entity;

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
 * @Description: 账单结算
 * @author onlineGenerator
 * @date 2017-05-15 19:58:02
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_settle_account", schema = "")
@SuppressWarnings("serial")
public class TcsbSettleAccountEntity implements java.io.Serializable {
	/**主键*/
	private java.lang.String id;
	/**店铺ID*/
	@Excel(name="店铺ID")
	private java.lang.String shopId;
	
	/**结算日期*/
	@Excel(name="结算日期",format = "yyyy-MM-dd")
	private java.util.Date settleData;
	/**结算日期*/
	@Excel(name="待结算账单日期",format = "yyyy-MM-dd")
	private java.util.Date waitSettleData;
	
	/**
	 * 待结算账单日期
	 */
	private String waitSettleDatas;

	/**结算总额*/
	@Excel(name="结算总额")
	private java.lang.String total;
	/**是否已结算*/
	@Excel(name="是否已结算")
	private java.lang.String isSettle;
	/**结算方式*/
	@Excel(name="结算方式")
	private java.lang.String settleWay;
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  主键
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
	 *@param: java.lang.String  主键
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  结算日期
	 */
	@Column(name ="SETTLE_DATA",nullable=true)
	public java.util.Date getSettleData(){
		return this.settleData;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  结算日期
	 */
	public void setSettleData(java.util.Date settleData){
		this.settleData = settleData;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算总额
	 */
	@Column(name ="TOTAL",nullable=true,length=6)
	public java.lang.String getTotal(){
		return this.total;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算总额
	 */
	public void setTotal(java.lang.String total){
		this.total = total;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否已结算
	 */
	@Column(name ="IS_SETTLE",nullable=true,length=1)
	public java.lang.String getIsSettle(){
		return this.isSettle;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否已结算
	 */
	public void setIsSettle(java.lang.String isSettle){
		this.isSettle = isSettle;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  结算方式
	 */
	@Column(name ="SETTLE_WAY",nullable=true,length=128)
	public java.lang.String getSettleWay(){
		return this.settleWay;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  结算方式
	 */
	public void setSettleWay(java.lang.String settleWay){
		this.settleWay = settleWay;
	}
	
	
	
	@Column(name ="wait_settle_data",nullable=true)
	public java.util.Date getWaitSettleData() {
		return waitSettleData;
	}

	public void setWaitSettleData(java.util.Date waitSettleData) {
		this.waitSettleData = waitSettleData;
	}

	@Transient
	public String getWaitSettleDatas() {
		return waitSettleDatas;
	}

	public void setWaitSettleDatas(String waitSettleDatas) {
		this.waitSettleDatas = waitSettleDatas;
	}
	
}
