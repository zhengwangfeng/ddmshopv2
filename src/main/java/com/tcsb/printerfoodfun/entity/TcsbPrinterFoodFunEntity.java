package com.tcsb.printerfoodfun.entity;

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
 * @Description: 打印食物菜单
 * @author onlineGenerator
 * @date 2017-07-14 14:19:10
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_printer_food_fun", schema = "")
@SuppressWarnings("serial")
public class TcsbPrinterFoodFunEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	/**foodTypeId*/
	private java.lang.String foodId;
	/**printerId*/
	private java.lang.String printerId;
	
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
	 *@return: java.lang.String  foodTypeId
	 */
	@Column(name ="FOOD_ID",nullable=true,length=32)
	public java.lang.String getFoodId(){
		return this.foodId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  foodTypeId
	 */
	public void setFoodId(java.lang.String foodId){
		this.foodId = foodId;
	}
	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  printerId
	 */
	@Column(name ="PRINTER_ID",nullable=true,length=32)
	public java.lang.String getPrinterId(){
		return this.printerId;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  printerId
	 */
	public void setPrinterId(java.lang.String printerId){
		this.printerId = printerId;
	}
}
