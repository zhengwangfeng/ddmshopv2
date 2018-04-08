package com.tcsb.usershopcar.entity;

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
 * @Description: 虚拟购物车
 * @author onlineGenerator
 * @date 2017-12-11 17:43:45
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_user_shop_car", schema = "")
@SuppressWarnings("serial")
public class TcsbUserShopCarEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**所属购物车*/
	@Excel(name="所属购物车")
	private java.lang.String shopcarId;
	/**用户唯一标识*/
	@Excel(name="用户唯一标识")
	private java.lang.String openid;
	private String note;
	private String taste;
	private Integer peopleNum;
	private String isRoom;
	private String isJustNowServing;
	private String orderParentId;
	private String shopId;
	@Column(name ="shop_id",nullable=false,length=32)
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOrderParentId() {
		return orderParentId;
	}

	public void setOrderParentId(String orderParentId) {
		this.orderParentId = orderParentId;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public String getTaste() {
		return taste;
	}

	public void setTaste(String taste) {
		this.taste = taste;
	}

	public Integer getPeopleNum() {
		return peopleNum;
	}

	public void setPeopleNum(Integer peopleNum) {
		this.peopleNum = peopleNum;
	}

	public String getIsRoom() {
		return isRoom;
	}

	public void setIsRoom(String isRoom) {
		this.isRoom = isRoom;
	}

	public String getIsJustNowServing() {
		return isJustNowServing;
	}

	public void setIsJustNowServing(String isJustNowServing) {
		this.isJustNowServing = isJustNowServing;
	}

	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
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
	@Column(name ="OPENID",nullable=true,length=32)
	public java.lang.String getOpenid(){
		return this.openid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  用户唯一标识
	 */
	public void setOpenid(java.lang.String openid){
		this.openid = openid;
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
}
