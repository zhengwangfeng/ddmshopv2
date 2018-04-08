package com.weixin.weixinmenu.entity;

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
 * @Description: 微信菜单
 * @author onlineGenerator
 * @date 2017-03-28 17:47:49
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_menu", schema = "")
@org.hibernate.annotations.Proxy(lazy = false)
@SuppressWarnings("serial")
public class WeixinMenuEntity implements java.io.Serializable {
	//父菜单
	private WeixinMenuEntity weixinMenuEntity;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "FATHERID")
	public WeixinMenuEntity getWeixinMenuEntity() {
		return weixinMenuEntity;
	}

	public void setWeixinMenuEntity(WeixinMenuEntity weixinMenuEntity) {
		this.weixinMenuEntity = weixinMenuEntity;
	}
	private List<WeixinMenuEntity> WeixinMenuEntitys = new ArrayList<WeixinMenuEntity>();
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "weixinMenuEntity")
	public List<WeixinMenuEntity> getWeixinMenuEntitys() {
		return WeixinMenuEntitys;
	}

	public void setWeixinMenuEntitys(List<WeixinMenuEntity> weixinMenuEntitys) {
		WeixinMenuEntitys = weixinMenuEntitys;
	}
	private Short menuLevel;//菜单等级
	@Column(name = "menu_level")
	public Short getMenuLevel() {
		return menuLevel;
	}
	public void setMenuLevel(Short menuLevel) {
		this.menuLevel = menuLevel;
	}
	/**ID*/
	private java.lang.String id;

	/** 菜单标识*/
	@Excel(name=" 菜单标识")
	private java.lang.String menukey;
	/**消息类型*/
	@Excel(name="消息类型")
	private java.lang.String msgtype;
	/**名称*/
	@Excel(name="名称")
	private java.lang.String name;
	/**顺序*/
	@Excel(name="顺序")
	private java.lang.String orders;
	/**消息模版ID */
	@Excel(name="消息模版ID ")
	private java.lang.String templateid;
	/**动作设置*/
	@Excel(name="动作设置")
	private java.lang.String type;
	/**链接地址*/
	@Excel(name="链接地址")
	private java.lang.String url;
	/**公众号ID   */
	private java.lang.String accountid;
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
	 *方法: 取得java.lang.String
	 *@return: java.lang.String   菜单标识
	 */
	@Column(name ="MENUKEY",nullable=true,length=255)
	public java.lang.String getMenukey(){
		return this.menukey;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String   菜单标识
	 */
	public void setMenukey(java.lang.String menukey){
		this.menukey = menukey;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息类型
	 */
	@Column(name ="MSGTYPE",nullable=true,length=255)
	public java.lang.String getMsgtype(){
		return this.msgtype;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息类型
	 */
	public void setMsgtype(java.lang.String msgtype){
		this.msgtype = msgtype;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  名称
	 */
	@Column(name ="NAME",nullable=true,length=255)
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
	 *@return: java.lang.String  顺序
	 */
	@Column(name ="ORDERS",nullable=true,length=11)
	public java.lang.String getOrders(){
		return this.orders;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  顺序
	 */
	public void setOrders(java.lang.String orders){
		this.orders = orders;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  消息模版ID 
	 */
	@Column(name ="TEMPLATEID",nullable=true,length=255)
	public java.lang.String getTemplateid(){
		return this.templateid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  消息模版ID 
	 */
	public void setTemplateid(java.lang.String templateid){
		this.templateid = templateid;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  动作设置
	 */
	@Column(name ="TYPE",nullable=true,length=255)
	public java.lang.String getType(){
		return this.type;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  动作设置
	 */
	public void setType(java.lang.String type){
		this.type = type;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  链接地址
	 */
	@Column(name ="URL",nullable=true,length=255)
	public java.lang.String getUrl(){
		return this.url;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  链接地址
	 */
	public void setUrl(java.lang.String url){
		this.url = url;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众号ID   
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=255)
	public java.lang.String getAccountid(){
		return this.accountid;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  公众号ID   
	 */
	public void setAccountid(java.lang.String accountid){
		this.accountid = accountid;
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
