package com.tcsb.tcsbfoodtastefunction.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

/**   
 * @Title: Entity
 * @Description: 口味偏好
 * @author onlineGenerator
 * @date 2017-06-20 14:33:53
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_food_taste_function", schema = "")
@org.hibernate.annotations.Proxy(lazy = false)
@SuppressWarnings("serial")
public class TcsbFoodTasteFunctionEntity implements java.io.Serializable {
	
	private TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentfunctionid")
	public TcsbFoodTasteFunctionEntity getTcsbFoodTasteFunctionEntity() {
		return tcsbFoodTasteFunctionEntity;
	}

	public void setTcsbFoodTasteFunctionEntity(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity) {
		this.tcsbFoodTasteFunctionEntity = tcsbFoodTasteFunctionEntity;
	}
	
	
	
	private List<TcsbFoodTasteFunctionEntity> TcsbFoodTasteFunctionEntitys = new ArrayList<TcsbFoodTasteFunctionEntity>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "tcsbFoodTasteFunctionEntity")
	public List<TcsbFoodTasteFunctionEntity> getTcsbFoodTasteFunctionEntitys() {
		return TcsbFoodTasteFunctionEntitys;
	}

	public void setTcsbFoodTasteFunctionEntitys(List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntitys) {
		TcsbFoodTasteFunctionEntitys = tcsbFoodTasteFunctionEntitys;
	}
	
	/**id*/
	private java.lang.String id;
	/**菜单等级*/
	@Excel(name="菜单等级")
	private Short functionlevel;
	/**口味名称*/
	@Excel(name="口味名称")
	private java.lang.String statename;
	/**口味排序*/
	@Excel(name="口味排序")
	private java.lang.String stateorder;
	/**parentid*/
	/*@Excel(name="parentid")
	private java.lang.String parentfunctionid;*/
	/**创建人id*/
	@Excel(name="创建人id")
	private java.lang.String createBy;
	/**创建人*/
	@Excel(name="创建人")
	private java.lang.String createName;
	/**修改人id*/
	@Excel(name="修改人id")
	private java.lang.String updateBy;
	/**修改时间*/
	@Excel(name="修改时间",format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/**创建时间*/
	@Excel(name="创建时间",format = "yyyy-MM-dd")
	private java.util.Date createDate;
	/**修改人*/
	@Excel(name="修改人")
	private java.lang.String updateName;
	/**shopid*/
	@Excel(name="shopid")
	private java.lang.String shopid;
	
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
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  菜单等级
	 */
	@Column(name ="FUNCTIONLEVEL",nullable=true,length=10)
	public Short getFunctionlevel(){
		return this.functionlevel;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  菜单等级
	 */
	public void setFunctionlevel(Short functionlevel){
		this.functionlevel = functionlevel;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  口味名称
	 */
	@Column(name ="STATENAME",nullable=false,length=50)
	public java.lang.String getStatename(){
		return this.statename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  口味名称
	 */
	public void setStatename(java.lang.String statename){
		this.statename = statename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  口味排序
	 */
	@Column(name ="STATEORDER",nullable=true,length=255)
	public java.lang.String getStateorder(){
		return this.stateorder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  口味排序
	 */
	public void setStateorder(java.lang.String stateorder){
		this.stateorder = stateorder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  parentid
	 */
/*	@Column(name ="PARENTFUNCTIONID",nullable=true,length=32)
	public java.lang.String getParentfunctionid(){
		return this.parentfunctionid;
	}*/

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  parentid
	 */
/*	public void setParentfunctionid(java.lang.String parentfunctionid){
		this.parentfunctionid = parentfunctionid;
	}*/
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人id
	 */
	@Column(name ="CREATE_BY",nullable=true,length=32)
	public java.lang.String getCreateBy(){
		return this.createBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人id
	 */
	public void setCreateBy(java.lang.String createBy){
		this.createBy = createBy;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  创建人
	 */
	@Column(name ="CREATE_NAME",nullable=true,length=32)
	public java.lang.String getCreateName(){
		return this.createName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  创建人
	 */
	public void setCreateName(java.lang.String createName){
		this.createName = createName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人id
	 */
	@Column(name ="UPDATE_BY",nullable=true,length=32)
	public java.lang.String getUpdateBy(){
		return this.updateBy;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人id
	 */
	public void setUpdateBy(java.lang.String updateBy){
		this.updateBy = updateBy;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  修改时间
	 */
	@Column(name ="UPDATE_DATE",nullable=true)
	public java.util.Date getUpdateDate(){
		return this.updateDate;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  修改时间
	 */
	public void setUpdateDate(java.util.Date updateDate){
		this.updateDate = updateDate;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  修改人
	 */
	@Column(name ="UPDATE_NAME",nullable=true,length=32)
	public java.lang.String getUpdateName(){
		return this.updateName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  修改人
	 */
	public void setUpdateName(java.lang.String updateName){
		this.updateName = updateName;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  shopid
	 */
	@Column(name ="SHOPID",nullable=true,length=32)
	public java.lang.String getShopid(){
		return this.shopid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  shopid
	 */
	public void setShopid(java.lang.String shopid){
		this.shopid = shopid;
	}


}
