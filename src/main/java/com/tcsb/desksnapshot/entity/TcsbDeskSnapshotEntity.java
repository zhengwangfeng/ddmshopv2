package com.tcsb.desksnapshot.entity;

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
 * @Description: 桌位快照
 * @author onlineGenerator
 * @date 2017-11-14 16:11:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_desk_snapshot", schema = "")
@SuppressWarnings("serial")
public class TcsbDeskSnapshotEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**店铺ID*/
	@Excel(name="店铺ID")
	private java.lang.String shopId;
	/**桌位编号*/
	@Excel(name="桌位编号")
	private java.lang.String number;
	/**二维码*/
	@Excel(name="二维码")
	private java.lang.String qrcode;
	/**是否被预约*/
	@Excel(name="是否被预约")
	private java.lang.String isOrder;
	/**桌位类别ID*/
	@Excel(name="桌位类别ID")
	private java.lang.String desktypeId;
	/**桌位名称*/
	@Excel(name="桌位名称")
	private java.lang.String deskName;
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
	/**0空闲1预定2未下单3用餐中*/
	@Excel(name="0空闲1预定2未下单3用餐中")
	private java.lang.String status;
	/**0添加1删除2改*/
	@Excel(name="0添加1删除2改")
	private java.lang.Integer snapStatus;
	/**小程序二维码**/
	private java.lang.String appletQrcode;




	@Column(name ="applet_qrcode",nullable=true,length=125)
	public java.lang.String getAppletQrcode() {
		return appletQrcode;
	}

	public void setAppletQrcode(java.lang.String appletQrcode) {
		this.appletQrcode = appletQrcode;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "assigned")
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  桌位编号
	 */
	@Column(name ="NUMBER",nullable=true,length=32)
	public java.lang.String getNumber(){
		return this.number;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  桌位编号
	 */
	public void setNumber(java.lang.String number){
		this.number = number;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  二维码
	 */
	@Column(name ="QRCODE",nullable=true,length=125)
	public java.lang.String getQrcode(){
		return this.qrcode;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  二维码
	 */
	public void setQrcode(java.lang.String qrcode){
		this.qrcode = qrcode;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  是否被预约
	 */
	@Column(name ="IS_ORDER",nullable=true,length=1)
	public java.lang.String getIsOrder(){
		return this.isOrder;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  是否被预约
	 */
	public void setIsOrder(java.lang.String isOrder){
		this.isOrder = isOrder;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  桌位类别ID
	 */
	@Column(name ="DESKTYPE_ID",nullable=true,length=32)
	public java.lang.String getDesktypeId(){
		return this.desktypeId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  桌位类别ID
	 */
	public void setDesktypeId(java.lang.String desktypeId){
		this.desktypeId = desktypeId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  桌位名称
	 */
	@Column(name ="DESK_NAME",nullable=true,length=32)
	public java.lang.String getDeskName(){
		return this.deskName;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  桌位名称
	 */
	public void setDeskName(java.lang.String deskName){
		this.deskName = deskName;
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
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  0空闲1预定2未下单3用餐中
	 */
	@Column(name ="STATUS",nullable=true,length=1)
	public java.lang.String getStatus(){
		return this.status;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  0空闲1预定2未下单3用餐中
	 */
	public void setStatus(java.lang.String status){
		this.status = status;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  0添加1删除2改
	 */
	@Column(name ="SNAP_STATUS",nullable=true,length=10)
	public java.lang.Integer getSnapStatus(){
		return this.snapStatus;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  0添加1删除2改
	 */
	public void setSnapStatus(java.lang.Integer snapStatus){
		this.snapStatus = snapStatus;
	}
}
