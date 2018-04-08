package com.pc.order.vo;

import java.util.Date;

public class TcsbPCOrderItemVO {
    /**ID*/
    private java.lang.String id;
    /**所属订单*/
    private java.lang.String orderId;
    /**食物ID*/
    private java.lang.String foodId;

    private java.lang.String foodName;
    /**数量
     * 用来计算商品的价格
     * */
    private java.lang.Double count;
    /**单价*/
    private java.lang.Double price;
    /**菜品ID*/
    private java.lang.String foodTypeId;
//    /**更新人名字*/
//    private java.lang.String updateName;
//    /**更新时间*/
//    private java.util.Date updateDate;
//    /**更新人*/
//    private java.lang.String updateBy;
//    /**创建人名字*/
//    private java.lang.String createName;
//    /**创建人*/
//    private java.lang.String createBy;
//    /**创建时间*/
//    private java.util.Date createDate;

    private String isFloat;

    private String unitName;
    /**
     * 商品的数量，指代份数
     */
    private Integer num;
    /***
     * 口味
     */
    private String foodTasteFun;
    /**
     * 是否更新了时价类产品的价格1已更新0未更新
     */
    private Integer isUpdatePrice;

    private Integer retreatNum;

    /**
     * 订单项与子项订单项关联标志
     */
    private String orderItemRelation;

    private String standardId;

    private String standardName;

    private String specialReq;

    private String isSetMeal;

    public String getIsSetMeal() {
        return isSetMeal;
    }

    public void setIsSetMeal(String isSetMeal) {
        this.isSetMeal = isSetMeal;
    }

    public String getSpecialReq() {
        return specialReq;
    }

    public void setSpecialReq(String specialReq) {
        this.specialReq = specialReq;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        this.foodId = foodId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getFoodTypeId() {
        return foodTypeId;
    }

    public void setFoodTypeId(String foodTypeId) {
        this.foodTypeId = foodTypeId;
    }

//    public String getUpdateName() {
//        return updateName;
//    }
//
//    public void setUpdateName(String updateName) {
//        this.updateName = updateName;
//    }
//
//    public Date getUpdateDate() {
//        return updateDate;
//    }
//
//    public void setUpdateDate(Date updateDate) {
//        this.updateDate = updateDate;
//    }
//
//    public String getUpdateBy() {
//        return updateBy;
//    }
//
//    public void setUpdateBy(String updateBy) {
//        this.updateBy = updateBy;
//    }
//
//    public String getCreateName() {
//        return createName;
//    }
//
//    public void setCreateName(String createName) {
//        this.createName = createName;
//    }
//
//    public String getCreateBy() {
//        return createBy;
//    }
//
//    public void setCreateBy(String createBy) {
//        this.createBy = createBy;
//    }
//
//    public Date getCreateDate() {
//        return createDate;
//    }
//
//    public void setCreateDate(Date createDate) {
//        this.createDate = createDate;
//    }

    public String getIsFloat() {
        return isFloat;
    }

    public void setIsFloat(String isFloat) {
        this.isFloat = isFloat;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getFoodTasteFun() {
        return foodTasteFun;
    }

    public void setFoodTasteFun(String foodTasteFun) {
        this.foodTasteFun = foodTasteFun;
    }

    public Integer getIsUpdatePrice() {
        return isUpdatePrice;
    }

    public void setIsUpdatePrice(Integer isUpdatePrice) {
        this.isUpdatePrice = isUpdatePrice;
    }

    public Integer getRetreatNum() {
        return retreatNum;
    }

    public void setRetreatNum(Integer retreatNum) {
        this.retreatNum = retreatNum;
    }

    public String getOrderItemRelation() {
        return orderItemRelation;
    }

    public void setOrderItemRelation(String orderItemRelation) {
        this.orderItemRelation = orderItemRelation;
    }

    public String getStandardId() {
        return standardId;
    }

    public void setStandardId(String standardId) {
        this.standardId = standardId;
    }

    public String getStandardName() {
        return standardName;
    }

    public void setStandardName(String standardName) {
        this.standardName = standardName;
    }
}
