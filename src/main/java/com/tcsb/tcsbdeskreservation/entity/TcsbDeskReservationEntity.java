package com.tcsb.tcsbdeskreservation.entity;

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
 * @Description: tcsb_desk_reservation
 * @author onlineGenerator
 * @date 2017-11-08 18:02:13
 * @version V1.0
 * 
 */
@Entity
@Table(name = "tcsb_desk_reservation", schema = "")
@SuppressWarnings("serial")
public class TcsbDeskReservationEntity implements java.io.Serializable {
	/** id */
	private java.lang.String id;
	/** 编号 */
	@Excel(name = "编号")
	private java.lang.String no;
	/** 顾客姓名 */
	@Excel(name = "顾客姓名")
	private java.lang.String nickname;
	/** 性别 */
	@Excel(name = "性别")
	private java.lang.String sex;
	/** 联系电话 */
	@Excel(name = "联系电话")
	private java.lang.String phone;
	/** 预订人数 */
	@Excel(name = "预订人数")
	private java.lang.Integer num;
	/** 预订时断(早,中,晚) */
	@Excel(name = "预订时断(早,中,晚)")
	private java.lang.String period;
	/** 预订时间 */
	@Excel(name = "预订时间")
	private java.util.Date orderTime;
	/** 预订保留时间 */
	@Excel(name = "预订保留时间")
	private java.lang.String retainTime;
	/** 预约备注 */
	@Excel(name = "预约备注")
	private java.lang.String note;
	/** 是否押金0未交押金 */
	@Excel(name = "是否押金")
	private java.lang.String isDeposit;
	/** 是否打印 */
	@Excel(name = "是否打印")
	private java.lang.String isPrint;
	/** 所属桌位 */
	@Excel(name = "所属桌位")
	private java.lang.String deskId;
	/** 预订状态0取消预订 1预订成功*/
	@Excel(name = "预订状态")
	private java.lang.String status;
	/** 预订来源 */
	@Excel(name = "预订来源")
	private java.lang.String source;

	private String shopId;

	private String totalprice;

	private String onlinePrice;

	private Double platformDiscountPrice;

	private Double specialCouponPrice;

	private Double universalCouponPrice;

	private String userId;
	
	private String depositDeltaId;
	
	
	private String payStatus;

	private String prepayId;

	@Column(name = "prepay_id", nullable = false, length = 64)
	public String getPrepayId() {
		return prepayId;
	}

	public void setPrepayId(String prepayId) {
		this.prepayId = prepayId;
	}

	@Column(name = "pay_status", nullable = false, length = 1)
	public String getPayStatus() {
		return payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	/** 更新人名字 */
	@Excel(name = "更新人名字")
	private java.lang.String updateName;

	@Column(name = "deposit_delta_id", nullable = true, length = 32)
	public String getDepositDeltaId() {
		return depositDeltaId;
	}

	public void setDepositDeltaId(String depositDeltaId) {
		this.depositDeltaId = depositDeltaId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getOnlinePrice() {
		return onlinePrice;
	}

	public void setOnlinePrice(String onlinePrice) {
		this.onlinePrice = onlinePrice;
	}

	public Double getPlatformDiscountPrice() {
		return platformDiscountPrice;
	}

	public void setPlatformDiscountPrice(Double platformDiscountPrice) {
		this.platformDiscountPrice = platformDiscountPrice;
	}

	public Double getSpecialCouponPrice() {
		return specialCouponPrice;
	}

	public void setSpecialCouponPrice(Double specialCouponPrice) {
		this.specialCouponPrice = specialCouponPrice;
	}

	public Double getUniversalCouponPrice() {
		return universalCouponPrice;
	}

	public void setUniversalCouponPrice(Double universalCouponPrice) {
		this.universalCouponPrice = universalCouponPrice;
	}

	/** 更新时间 */
	@Excel(name = "更新时间", format = "yyyy-MM-dd")
	private java.util.Date updateDate;
	/** 更新人 */
	@Excel(name = "更新人")
	private java.lang.String updateBy;
	/** 创建人名字 */
	@Excel(name = "创建人名字")
	private java.lang.String createName;
	/** 创建人 */
	@Excel(name = "创建人")
	private java.lang.String createBy;
	/** 创建时间 */
	@Excel(name = "创建时间", format = "yyyy-MM-dd")
	private java.util.Date createDate;

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String id
	 */
	@Id
	@GeneratedValue(generator = "paymentableGenerator")
	@GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
	@Column(name = "ID", nullable = false, length = 32)
	public java.lang.String getId() {
		return this.id;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String id
	 */
	public void setId(java.lang.String id) {
		this.id = id;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 编号
	 */
	@Column(name = "NO", nullable = true, length = 32)
	public java.lang.String getNo() {
		return this.no;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 编号
	 */
	public void setNo(java.lang.String no) {
		this.no = no;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 顾客姓名
	 */
	@Column(name = "NICKNAME", nullable = true, length = 25)
	public java.lang.String getNickname() {
		return this.nickname;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 顾客姓名
	 */
	public void setNickname(java.lang.String nickname) {
		this.nickname = nickname;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 性别
	 */
	@Column(name = "SEX", nullable = true, length = 1)
	public java.lang.String getSex() {
		return this.sex;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 性别
	 */
	public void setSex(java.lang.String sex) {
		this.sex = sex;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 联系电话
	 */
	@Column(name = "PHONE", nullable = true, length = 18)
	public java.lang.String getPhone() {
		return this.phone;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 联系电话
	 */
	public void setPhone(java.lang.String phone) {
		this.phone = phone;
	}

	/**
	 * 方法: 取得java.lang.Integer
	 * 
	 * @return: java.lang.Integer 预订人数
	 */
	@Column(name = "NUM", nullable = true, length = 10)
	public java.lang.Integer getNum() {
		return this.num;
	}

	/**
	 * 方法: 设置java.lang.Integer
	 * 
	 * @param: java.lang.Integer 预订人数
	 */
	public void setNum(java.lang.Integer num) {
		this.num = num;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 预订时断(早,中,晚)
	 */
	@Column(name = "PERIOD", nullable = true, length = 1)
	public java.lang.String getPeriod() {
		return this.period;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 预订时断(早,中,晚)
	 */
	public void setPeriod(java.lang.String period) {
		this.period = period;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 预订时间
	 */
	@Column(name = "ORDER_TIME", nullable = true, length = 32)
	public Date getOrderTime() {
		return this.orderTime;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 预订时间
	 */
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 预订保留时间
	 */
	@Column(name = "RETAIN_TIME", nullable = true, length = 16)
	public java.lang.String getRetainTime() {
		return this.retainTime;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 预订保留时间
	 */
	public void setRetainTime(java.lang.String retainTime) {
		this.retainTime = retainTime;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 预约备注
	 */
	@Column(name = "NOTE", nullable = true, length = 125)
	public java.lang.String getNote() {
		return this.note;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 预约备注
	 */
	public void setNote(java.lang.String note) {
		this.note = note;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 是否押金
	 */
	@Column(name = "IS_DEPOSIT", nullable = true, length = 1)
	public java.lang.String getIsDeposit() {
		return this.isDeposit;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 是否押金
	 */
	public void setIsDeposit(java.lang.String isDeposit) {
		this.isDeposit = isDeposit;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 是否打印
	 */
	@Column(name = "IS_PRINT", nullable = true, length = 1)
	public java.lang.String getIsPrint() {
		return this.isPrint;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 是否打印
	 */
	public void setIsPrint(java.lang.String isPrint) {
		this.isPrint = isPrint;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 所属桌位
	 */
	@Column(name = "DESK_ID", nullable = true, length = 32)
	public java.lang.String getDeskId() {
		return this.deskId;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 所属桌位
	 */
	public void setDeskId(java.lang.String deskId) {
		this.deskId = deskId;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 预订状态
	 */
	@Column(name = "STATUS", nullable = true, length = 1)
	public java.lang.String getStatus() {
		return this.status;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 预订状态
	 */
	public void setStatus(java.lang.String status) {
		this.status = status;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 预订来源
	 */
	@Column(name = "SOURCE", nullable = true, length = 1)
	public java.lang.String getSource() {
		return this.source;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 预订来源
	 */
	public void setSource(java.lang.String source) {
		this.source = source;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 更新人名字
	 */
	@Column(name = "UPDATE_NAME", nullable = true, length = 32)
	public java.lang.String getUpdateName() {
		return this.updateName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 更新人名字
	 */
	public void setUpdateName(java.lang.String updateName) {
		this.updateName = updateName;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 更新时间
	 */
	@Column(name = "UPDATE_DATE", nullable = true)
	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 更新时间
	 */
	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 更新人
	 */
	@Column(name = "UPDATE_BY", nullable = true, length = 32)
	public java.lang.String getUpdateBy() {
		return this.updateBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 更新人
	 */
	public void setUpdateBy(java.lang.String updateBy) {
		this.updateBy = updateBy;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人名字
	 */
	@Column(name = "CREATE_NAME", nullable = true, length = 32)
	public java.lang.String getCreateName() {
		return this.createName;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人名字
	 */
	public void setCreateName(java.lang.String createName) {
		this.createName = createName;
	}

	/**
	 * 方法: 取得java.lang.String
	 * 
	 * @return: java.lang.String 创建人
	 */
	@Column(name = "CREATE_BY", nullable = true, length = 32)
	public java.lang.String getCreateBy() {
		return this.createBy;
	}

	/**
	 * 方法: 设置java.lang.String
	 * 
	 * @param: java.lang.String 创建人
	 */
	public void setCreateBy(java.lang.String createBy) {
		this.createBy = createBy;
	}

	/**
	 * 方法: 取得java.util.Date
	 * 
	 * @return: java.util.Date 创建时间
	 */
	@Column(name = "CREATE_DATE", nullable = true)
	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	/**
	 * 方法: 设置java.util.Date
	 * 
	 * @param: java.util.Date 创建时间
	 */
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTotalprice() {
		return totalprice;
	}

	public void setTotalprice(String totalprice) {
		this.totalprice = totalprice;
	}
}
