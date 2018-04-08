package com.tcsb.callservice.entity;

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
 * @Description: 呼叫服务
 * @author onlineGenerator
 * @date 2017-05-10 15:23:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_call_service", schema = "")
@SuppressWarnings("serial")
public class TcsbCallServiceEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属店铺*/
	@Excel(name="所属店铺")
	private java.lang.String shopId;
	/**所属桌位*/
	@Excel(name="所属桌位")
	private java.lang.String deskId;
	/**选择的服务*/
	@Excel(name="选择的服务")
	private java.lang.String shopServiceId;
	/**数量*/
	@Excel(name="数量")
	private java.lang.Integer count;
	/**是否已读0未读1已读*/
	@Excel(name="是否已读")
	private java.lang.String isread;
	/**是否播放0未播放1已播放（采用socket字段废弃）*/
	@Excel(name="是否播放")
	@Deprecated
	private java.lang.String isplay;
	/**用户ID*/
	@Excel(name="用户ID")
	private java.lang.String userId;
	/**呼叫时间*/
	@Excel(name="呼叫时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	
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
	 *@return: java.lang.String  选择的服务
	 */
	@Column(name ="SHOP_SERVICE_ID",nullable=true,length=32)
	public java.lang.String getShopServiceId(){
		return this.shopServiceId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  选择的服务
	 */
	public void setShopServiceId(java.lang.String shopServiceId){
		this.shopServiceId = shopServiceId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  数量
	 */
	@Column(name ="COUNT",nullable=true,length=10)
	public java.lang.Integer getCount(){
		return this.count;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  数量
	 */
	public void setCount(java.lang.Integer count){
		this.count = count;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否播放
	 */
	@Column(name ="ISPLAY",nullable=true,length=1)
	public java.lang.String getIsplay(){
		return this.isplay;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否播放
	 */
	public void setIsplay(java.lang.String isplay){
		this.isplay = isplay;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  呼叫时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  呼叫时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
}
