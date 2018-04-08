package com.weixin.weixinautoresponse.entity;

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
 * @Description: 微信关键字回复
 * @author onlineGenerator
 * @date 2017-03-28 15:51:37
 * @version V1.0   
 *
 */
@Entity
@Table(name = "weixin_autoresponse", schema = "")
@SuppressWarnings("serial")
public class WeixinAutoresponseEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**关键字*/
	@Excel(name="关键字")
	private java.lang.String keyword;
	/**消息类型*/
	@Excel(name="消息类型")
	private java.lang.String msgtype;
	/**消息模版ID*/
	@Excel(name="消息模版ID")
	private java.lang.String templateid;
	/**模版名称*/
	private java.lang.String templatename;
	/**公众号ID*/
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
	 *@return: java.lang.String  关键字
	 */
	@Column(name ="KEYWORD",nullable=true,length=255)
	public java.lang.String getKeyword(){
		return this.keyword;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  关键字
	 */
	public void setKeyword(java.lang.String keyword){
		this.keyword = keyword;
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
	 *@return: java.lang.String  消息模版ID
	 */
	@Column(name ="TEMPLATEID",nullable=true,length=32)
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
	 *@return: java.lang.String  模版名称
	 */
	@Column(name ="TEMPLATENAME",nullable=true,length=255)
	public java.lang.String getTemplatename(){
		return this.templatename;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  模版名称
	 */
	public void setTemplatename(java.lang.String templatename){
		this.templatename = templatename;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  公众号ID
	 */
	@Column(name ="ACCOUNTID",nullable=true,length=32)
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
