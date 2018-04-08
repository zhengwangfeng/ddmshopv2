package com.pc.order.vo;

import java.util.Date;

public class OrderMessageVO {
    /**订单编号用ID*/
    private java.lang.String id;

    /**订单编号*/
    private java.lang.String orderNo;

    /**下单方式*/
    private java.lang.String method;

    private String methodName;

    /**订单状态*/
    private java.lang.String status;

    private String orderStatus;

    private String orderStatusName;

    /**实际消费*/
    private java.lang.Double totalPrice;

    /**实际消费*/
    private java.lang.Double realTotalPrice;

    /**支付方式 0线上1线下*/
    private java.lang.String payMethod;

    /**会员折扣金额*/
    private Double vipDiscountPrice;

    /**手动折扣金额*/
    private Double handDiscountPrice;

    /**线下抹零*/
    private String offlineDiscount;

    private String deskId;

    private String deskName;

    /**创建人*/
    private java.lang.String createBy;
    /**创建时间*/
    private java.util.Date createDate;

    private Integer people;

    private String shopId;

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public Double getVipDiscountPrice() {
        return vipDiscountPrice;
    }

    public void setVipDiscountPrice(Double vipDiscountPrice) {
        this.vipDiscountPrice = vipDiscountPrice;
    }

    public Double getHandDiscountPrice() {
        return handDiscountPrice;
    }

    public void setHandDiscountPrice(Double handDiscountPrice) {
        this.handDiscountPrice = handDiscountPrice;
    }

    public String getOfflineDiscount() {
        return offlineDiscount;
    }

    public void setOfflineDiscount(String offlineDiscount) {
        this.offlineDiscount = offlineDiscount;
    }

    public String getDeskId() {
        return deskId;
    }

    public void setDeskId(String deskId) {
        this.deskId = deskId;
    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getPeople() {
        return people;
    }

    public void setPeople(Integer people) {
        this.people = people;
    }

    public Double getRealTotalPrice() {
        return realTotalPrice;
    }

    public void setRealTotalPrice(Double realTotalPrice) {
        this.realTotalPrice = realTotalPrice;
    }
}
