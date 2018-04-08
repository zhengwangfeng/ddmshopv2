package com.tcsb.platformfullcuttemplate.entity;

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
 * @Description: 平台优惠券模板
 * @author onlineGenerator
 * @date 2018-01-27 09:30:36
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_platform_fullcut_template", schema = "")
@SuppressWarnings("serial")
public class TcsbPlatformFullcutTemplateEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**满额*/
	@Excel(name="满额")
	private java.lang.Integer total;
	/**立减*/
	@Excel(name="立减")
	private java.lang.Integer discount;
	/**权重*/
	@Excel(name="权重")
	private java.lang.String weight;
	/**优惠券是否投入使用*/
	@Excel(name="优惠券是否投入使用")
	private java.lang.String isUse;
	/**是否新用户随机领取券*/
	@Excel(name="是否新用户随机领取券")
	private java.lang.String isNewuserRandom;
	
	
	/**使用期限*/
	@Excel(name="使用期限")
	private Integer usePeriod;
	
	/**日期单位*/
	@Excel(name="日期单位")
	private String dateUnit;
	
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
	
	@Column(name ="use_period",nullable=true)
	public Integer getUsePeriod() {
		return usePeriod;
	}

	public void setUsePeriod(Integer usePeriod) {
		this.usePeriod = usePeriod;
	}
	
	
	@Column(name ="date_unit",nullable=true,length=6)
	public String getDateUnit() {
		return dateUnit;
	}
	
	public void setDateUnit(String dateUnit) {
		this.dateUnit = dateUnit;
	}
	
	
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  满额
	 */
	@Column(name ="TOTAL",nullable=true,length=10)
	public java.lang.Integer getTotal(){
		return this.total;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  满额
	 */
	public void setTotal(java.lang.Integer total){
		this.total = total;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  立减
	 */
	@Column(name ="DISCOUNT",nullable=true,length=10)
	public java.lang.Integer getDiscount(){
		return this.discount;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  立减
	 */
	public void setDiscount(java.lang.Integer discount){
		this.discount = discount;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  权重
	 */
	@Column(name ="WEIGHT",nullable=true,length=11)
	public java.lang.String getWeight(){
		return this.weight;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  权重
	 */
	public void setWeight(java.lang.String weight){
		this.weight = weight;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  优惠券是否投入使用
	 */
	@Column(name ="IS_USE",nullable=true,length=1)
	public java.lang.String getIsUse(){
		return this.isUse;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  优惠券是否投入使用
	 */
	public void setIsUse(java.lang.String isUse){
		this.isUse = isUse;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否新用户随机领取券
	 */
	@Column(name ="IS_NEWUSER_RANDOM",nullable=true,length=1)
	public java.lang.String getIsNewuserRandom(){
		return this.isNewuserRandom;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否新用户随机领取券
	 */
	public void setIsNewuserRandom(java.lang.String isNewuserRandom){
		this.isNewuserRandom = isNewuserRandom;
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
}
