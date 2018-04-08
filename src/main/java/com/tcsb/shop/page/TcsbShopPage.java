package com.tcsb.shop.page;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;

/**   
 * @Title: Entity
 * @Description: 店铺管理
 * @author onlineGenerator
 * @date 2017-02-28 11:06:59
 * @version V1.0   
 *
 *
 */
public class TcsbShopPage implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String name;
	/**头像*/
	@Excel(name="头像")
	private java.lang.String headimg;
	/**简介*/
	@Excel(name="简介")
	private java.lang.String introdution;
	/**地址*/
	@Excel(name="地址")
	private java.lang.String address;
	/**经度*/
	@Excel(name="经度")
	private String longitude;
	/**纬度*/
	@Excel(name="纬度")
	private String latitude;
	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**联系方式*/
	@Excel(name="联系方式")
	private java.lang.String phone;
	/**是否入驻*/
	@Excel(name="是否入驻")
	private java.lang.String idDel;
	/**星级*/
	@Excel(name="星级")
	private java.lang.Double level;
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
	@Excel(name="用户ID")
	private String userId;
	
	private List<TcsbImagesManagerEntity> imagesManagerList;
	public List<TcsbImagesManagerEntity> getImagesManagerList() {
		return imagesManagerList;
	}

	public void setImagesManagerList(List<TcsbImagesManagerEntity> imagesManagerList) {
		this.imagesManagerList = imagesManagerList;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

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
	 *@return: java.lang.String  名称
	 */
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  头像
	 */
	public java.lang.String getHeadimg(){
		return this.headimg;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  头像
	 */
	public void setHeadimg(java.lang.String headimg){
		this.headimg = headimg;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  简介
	 */
	public java.lang.String getIntrodution(){
		return this.introdution;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  简介
	 */
	public void setIntrodution(java.lang.String introdution){
		this.introdution = introdution;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  地址
	 */
	public java.lang.String getAddress(){
		return this.address;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  地址
	 */
	public void setAddress(java.lang.String address){
		this.address = address;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  联系方式
	 */
	public java.lang.String getPhone(){
		return this.phone;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  联系方式
	 */
	public void setPhone(java.lang.String phone){
		this.phone = phone;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否入驻
	 */
	public java.lang.String getIdDel(){
		return this.idDel;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否入驻
	 */
	public void setIdDel(java.lang.String idDel){
		this.idDel = idDel;
	}
	/**
	 *方法: 取得java.lang.Double
	 *@return: java.lang.Double  星级
	 */
	public java.lang.Double getLevel(){
		return this.level;
	}

	/**
	 *方法: 设置java.lang.Double
	 *@param: java.lang.Double  星级
	 */
	public void setLevel(java.lang.Double level){
		this.level = level;
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
}
