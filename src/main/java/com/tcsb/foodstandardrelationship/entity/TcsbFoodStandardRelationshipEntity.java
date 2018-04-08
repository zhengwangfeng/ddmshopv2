package com.tcsb.foodstandardrelationship.entity;

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
 * @Description: 食物规格关联表
 * @author onlineGenerator
 * @date 2017-11-27 13:50:38
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_food_standard_relationship", schema = "")
@SuppressWarnings("serial")
public class TcsbFoodStandardRelationshipEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**食物规格ID*/
	@Excel(name="食物规格ID")
	private java.lang.String foodStandardId;
	/**食物ID*/
	@Excel(name="食物ID")
	private java.lang.String foodId;
	
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
	 *@return: java.lang.String  食物规格ID
	 */
	@Column(name ="FOOD_STANDARD_ID",nullable=true,length=32)
	public java.lang.String getFoodStandardId(){
		return this.foodStandardId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  食物规格ID
	 */
	public void setFoodStandardId(java.lang.String foodStandardId){
		this.foodStandardId = foodStandardId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  食物ID
	 */
	@Column(name ="FOOD_ID",nullable=true,length=32)
	public java.lang.String getFoodId(){
		return this.foodId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  食物ID
	 */
	public void setFoodId(java.lang.String foodId){
		this.foodId = foodId;
	}
}
