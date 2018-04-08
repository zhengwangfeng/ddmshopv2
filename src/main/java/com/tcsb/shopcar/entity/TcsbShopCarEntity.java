package com.tcsb.shopcar.entity;

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
 * @Description: 购物车
 * @author onlineGenerator
 * @date 2017-04-26 21:44:06
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_shop_car", schema = "")
@SuppressWarnings("serial")
public class TcsbShopCarEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**createTime*/
	@Excel(name="createTime",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**桌位ID*/
	@Excel(name="桌位ID")
	private java.lang.String deskId;
	
	/**
	 * 是否允许该car进行添加操作1开放0锁定
	 */
	private Integer isLock;
	
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
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  createTime
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  createTime
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
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

	

	@Column(name ="IS_LOCK",nullable=true,length=1)
	public Integer getIsLock() {
		return isLock;
	}

	public void setIsLock(Integer isLock) {
		this.isLock = isLock;
	}

	
}
