package com.tcsb.shopevaluate.entity;

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
 * @Description: 店铺评价
 * @author onlineGenerator
 * @date 2017-04-24 13:50:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_shop_evaluate", schema = "")
@SuppressWarnings("serial")
public class TcsbShopEvaluateEntity implements java.io.Serializable {
	/**ID*/
	private java.lang.String id;
	/**所属店铺*/
	private java.lang.String shopId;
	/**内容*/
	@Excel(name="内容")
	private java.lang.String content;
	/**用户ID*/
	@Excel(name="用户ID")
	private java.lang.String userId;
	/**菜品口味*/
	@Excel(name="菜品评价")
	private java.lang.Integer dishesEvaluation;
	/**就餐环境*/
	@Excel(name="就餐环境")
	private java.lang.Integer diningEnvironment;
	/**上菜速度**/
	private Integer servingSpeed;
	
	/**是否好评1好评0坏评**/
	private Integer evaluationLevel;
	
	/**评价时间*/
	private java.util.Date createTime;
	/**图片*/
	@Excel(name="图片")
	private java.lang.String img;
	
	
	@Excel(name="订单号")
	private java.lang.String orderId;
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  订单ID
	 */
	@Column(name ="ORDER_ID",nullable=true,length=32)
	public java.lang.String getOrderId(){
		return this.orderId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  订单ID
	 */
	public void setOrderId(java.lang.String orderId){
		this.orderId = orderId;
	}
	
	
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  ID
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name ="ID",nullable=false,length=36)
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
	 *@return: java.lang.Integer  所属店铺
	 */
	@Column(name ="SHOP_ID",nullable=true,length=19)
	public java.lang.String getShopId(){
		return this.shopId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  所属店铺
	 */
	public void setShopId(java.lang.String shopId){
		this.shopId = shopId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  内容
	 */
	@Column(name ="CONTENT",nullable=true,length=128)
	public java.lang.String getContent(){
		return this.content;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  内容
	 */
	public void setContent(java.lang.String content){
		this.content = content;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  用户ID
	 */
	@Column(name ="USER_ID",nullable=true,length=19)
	public java.lang.String getUserId(){
		return this.userId;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  用户ID
	 */
	public void setUserId(java.lang.String userId){
		this.userId = userId;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  菜品评价
	 */
	@Column(name ="DISHES_EVALUATION",nullable=true,length=10)
	public java.lang.Integer getDishesEvaluation(){
		return this.dishesEvaluation;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  菜品评价
	 */
	public void setDishesEvaluation(java.lang.Integer dishesEvaluation){
		this.dishesEvaluation = dishesEvaluation;
	}
	/**
	 *方法: 取得java.lang.Integer
	 *@return: java.lang.Integer  就餐环境
	 */
	@Column(name ="DINING_ENVIRONMENT",nullable=true,length=10)
	public java.lang.Integer getDiningEnvironment(){
		return this.diningEnvironment;
	}

	/**
	 *方法: 设置java.lang.Integer
	 *@param: java.lang.Integer  就餐环境
	 */
	public void setDiningEnvironment(java.lang.Integer diningEnvironment){
		this.diningEnvironment = diningEnvironment;
	}
	/**
	 *方法: 取得java.util.Date
	 *@return: java.util.Date  评价时间
	 */
	@Column(name ="CREATE_TIME",nullable=true)
	public java.util.Date getCreateTime(){
		return this.createTime;
	}

	/**
	 *方法: 设置java.util.Date
	 *@param: java.util.Date  评价时间
	 */
	public void setCreateTime(java.util.Date createTime){
		this.createTime = createTime;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  图片
	 */
	@Column(name ="IMG",nullable=true,length=1250)
	public java.lang.String getImg(){
		return this.img;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  图片
	 */
	public void setImg(java.lang.String img){
		this.img = img;
	}

	@Column(name ="SERVING_SPEED",nullable=true,length=10)
	public Integer getServingSpeed() {
		return servingSpeed;
	}

	public void setServingSpeed(Integer servingSpeed) {
		this.servingSpeed = servingSpeed;
	}

	@Column(name ="EVALUATION_LEVEL",nullable=true,length=10)
	public Integer getEvaluationLevel() {
		return evaluationLevel;
	}

	public void setEvaluationLevel(Integer evaluationLevel) {
		this.evaluationLevel = evaluationLevel;
	}
}
