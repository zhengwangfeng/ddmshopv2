package com.tcsb.userreservationshopcar.entity;

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
 * @Description: 预约虚拟购物车
 * @author onlineGenerator
 * @date 2017-12-14 16:16:47
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_user_reservation_shop_car", schema = "")
@SuppressWarnings("serial")
public class TcsbUserReservationShopCarEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属购物车*/
	@Excel(name="所属购物车")
	private java.lang.String shopcarId;
	/**用户唯一标识*/
	@Excel(name="用户唯一标识")
	private java.lang.String userId;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private java.util.Date createTime;
	/**note*/
	@Excel(name="note")
	private java.lang.String note;
	/**peopleNum*/
	@Excel(name="peopleNum")
	private java.lang.Integer peopleNum;
	/**shopId*/
	@Excel(name="shopId")
	private java.lang.String shopId;
	/**phone*/
	@Excel(name="phone")
	private java.lang.String phone;
	/**sex*/
	@Excel(name="sex")
	private java.lang.String sex;
	/**nickname*/
	@Excel(name="nickname")
	private java.lang.String nickname;
	/**eatTime*/
	@Excel(name="eatTime",format = "yyyy-MM-dd")
	private java.util.Date eatTime;
	
	private String reservationId;
	public String getReservationId() {
		return reservationId;
	}

	public void setReservationId(String reservationId) {
		this.reservationId = reservationId;
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
	 *@return: java.lang.String  所属购物车
	 */
	@Column(name ="SHOPCAR_ID",nullable=true,length=32)
	public java.lang.String getShopcarId(){
		return this.shopcarId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  所属购物车
	 */
	public void setShopcarId(java.lang.String shopcarId){
		this.shopcarId = shopcarId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  用户唯一标识
	 */
	@Column(name ="USER_ID",nullable=true,length=32)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户唯一标识
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  创建时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  创建时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  note
	 */
	@Column(name ="NOTE",nullable=true,length=125)
	public java.lang.String getNote(){
		return this.note;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  note
	 */
	public void setNote(java.lang.String note){
		this.note = note;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  peopleNum
	 */
	@Column(name ="PEOPLE_NUM",nullable=true,length=10)
	public java.lang.Integer getPeopleNum(){
		return this.peopleNum;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  peopleNum
	 */
	public void setPeopleNum(java.lang.Integer peopleNum){
		this.peopleNum = peopleNum;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shopId
	 */
	@Column(name ="SHOP_ID",nullable=true,length=32)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shopId
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  phone
	 */
	@Column(name ="PHONE",nullable=true,length=32)
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  phone
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  sex
	 */
	@Column(name ="SEX",nullable=true,length=10)
	public java.lang.String getSex(){
		return this.sex;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  sex
	 */
	public void setSex(java.lang.String sex){
		this.sex = sex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  nickname
	 */
	@Column(name ="NICKNAME",nullable=true,length=32)
	public java.lang.String getNickname(){
		return this.nickname;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  nickname
	 */
	public void setNickname(java.lang.String nickname){
		this.nickname = nickname;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  eatTime
	 */
	@Column(name ="EAT_TIME",nullable=true)
	public java.util.Date getEatTime(){
		return this.eatTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  eatTime
	 */
	public void setEatTime(java.util.Date eatTime){
		this.eatTime = eatTime;
	}
}
