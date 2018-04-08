package com.tcsb.tcsbfoodfunction.entity;

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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.SequenceGenerator;

import org.jeecgframework.core.common.entity.IdEntity;
import org.jeecgframework.poi.excel.annotation.Excel;

import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;

/**   
 * @Title: Entity
 * @Description: tcsb_food_function
 * @author onlineGenerator
 * @date 2017-06-21 17:25:44
 * @version V1.0   
 *
 */
@Entity
@Table(name = "tcsb_food_function", schema = "")
@SuppressWarnings("serial")
public class TcsbFoodFunctionEntity implements java.io.Serializable {
	/**id*/
	private java.lang.String id;
	private TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity;
	private TcsbFoodEntity tcsbFoodEntity;
	private java.lang.String operation;
	private java.lang.String datarule;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public TcsbFoodTasteFunctionEntity getTcsbFoodTasteFunctionEntity() {
		return tcsbFoodTasteFunctionEntity;
	}

	public void setTcsbFoodTasteFunctionEntity(TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity) {
		this.tcsbFoodTasteFunctionEntity = tcsbFoodTasteFunctionEntity;
	}
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "foodid")
	public TcsbFoodEntity getTcsbFoodEntity() {
		return tcsbFoodEntity;
	}

	public void setTcsbFoodEntity(TcsbFoodEntity tcsbFoodEntity) {
		this.tcsbFoodEntity = tcsbFoodEntity;
	}
	
	
	

	
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
	 *@return: java.lang.String  operation
	 */
	@Column(name ="OPERATION",nullable=true,length=2000)
	public java.lang.String getOperation(){
		return this.operation;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  operation
	 */
	public void setOperation(java.lang.String operation){
		this.operation = operation;
	}


	/**
	 *方法: 取得java.lang.String
	 *@return: java.lang.String  datarule
	 */
	@Column(name ="DATARULE",nullable=true,length=1000)
	public java.lang.String getDatarule(){
		return this.datarule;
	}

	/**
	 *方法: 设置java.lang.String
	 *@param: java.lang.String  datarule
	 */
	public void setDatarule(java.lang.String datarule){
		this.datarule = datarule;
	}

	
}
