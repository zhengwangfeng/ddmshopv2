package com.tcsb.printer.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.tcsb.order.VO.TcsbOrderItemPrintVO;

/**   
 * @Title: Entity
 * @Description: 打印机设置
 * @author onlineGenerator
 * @date 2017-07-12 11:54:31
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_printer", schema = "")
@SuppressWarnings("serial")
public class TcsbPrinterEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**打印机索引*/
	private java.lang.Integer printIndex;
	/**打印机名称*/
	private java.lang.String name;
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
	private String shopId;
	private String autoPrint;
	@Column(name ="auto_print",nullable=false,length=11)
	public String getAutoPrint() {
		return autoPrint;
	}

	public void setAutoPrint(String autoPrint) {
		this.autoPrint = autoPrint;
	}

	/**打印份数**/
	private Integer printNum;
	@Column(name ="print_num",nullable=false,length=11)
	public Integer getPrintNum() {
		return printNum;
	}

	public void setPrintNum(Integer printNum) {
		this.printNum = printNum;
	}

	/**分单打印*/
	private String printChild;
	@Column(name ="print_child",nullable=false,length=1)
	public String getPrintChild() {
		return printChild;
	}

	public void setPrintChild(String printChild) {
		this.printChild = printChild;
	}

	@Column(name ="shop_id",nullable=true,length=32)
	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	private List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs;
	@Transient
	public List<TcsbOrderItemPrintVO> getTcsbOrderItemPrintVOs() {
		return tcsbOrderItemPrintVOs;
	}

	public void setTcsbOrderItemPrintVOs(
			List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs) {
		this.tcsbOrderItemPrintVOs = tcsbOrderItemPrintVOs;
	}

	private List<TcsbPrinterEntity> printList;            
	@Transient
	public List<TcsbPrinterEntity> getPrintList() {
		return printList;
	}

	public void setPrintList(List<TcsbPrinterEntity> printList) {
		this.printList = printList;
	}

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
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  打印机索引
	 */
	@Column(name ="PRINT_INDEX",nullable=true,length=10)
	public java.lang.Integer getPrintIndex(){
		return this.printIndex;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  打印机索引
	 */
	public void setPrintIndex(java.lang.Integer printIndex){
		this.printIndex = printIndex;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  打印机名称
	 */
	@Column(name ="NAME",nullable=true,length=32)
	public java.lang.String getName(){
		return this.name;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  打印机名称
	 */
	public void setName(java.lang.String name){
		this.name = name;
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
