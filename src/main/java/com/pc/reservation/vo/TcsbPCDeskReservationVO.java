package com.pc.reservation.vo;

import java.util.Date;

public class TcsbPCDeskReservationVO {
    /** id */
    private java.lang.String id;
//    /** 编号 */
//    private java.lang.String no;
    /** 顾客姓名 */
//    private java.lang.String nickname;
    /** 性别 */
    private java.lang.String sex;
    /** 联系电话 */
    private java.lang.String phone;
    /** 预订人数 */
    private java.lang.Integer num;
    /** 预订时断(早,中,晚) */
//    private java.lang.String period;
    /** 预订时间 */
    private java.util.Date orderTime;
    /** 预订保留时间 */
//    private java.lang.String retainTime;
    /** 预约备注 */
//    private java.lang.String note;
    /** 是否押金0未交押金 */
    private java.lang.String isDeposit;
    /** 是否打印 */
//    private java.lang.String isPrint;
    /** 所属桌位 */
    private java.lang.String deskId;
    /** 所属桌位名称 */
    private java.lang.String deskName;

    /** 预订状态0预定成功1结束订单2申请退款 3退款成功*/
//    private java.lang.String status;
    /** 预订来源 */
//    private java.lang.String source;

    private String shopId;

    private String totalprice;

//    private String onlinePrice;

//    private Double platformDiscountPrice;
//
//    private Double specialCouponPrice;
//
//    private Double universalCouponPrice;

    private String userId;

    private String depositDeltaId;

//    private String payStatus;

//    private String prepayId;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

//    public String getNo() {
//        return no;
//    }
//
//    public void setNo(String no) {
//        this.no = no;
//    }

//    public String getNickname() {
//        return nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

//    public String getPeriod() {
//        return period;
//    }
//
//    public void setPeriod(String period) {
//        this.period = period;
//    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

//    public String getRetainTime() {
//        return retainTime;
//    }
//
//    public void setRetainTime(String retainTime) {
//        this.retainTime = retainTime;
//    }
//
//    public String getNote() {
//        return note;
//    }
//
//    public void setNote(String note) {
//        this.note = note;
//    }

    public String getIsDeposit() {
        return isDeposit;
    }

    public void setIsDeposit(String isDeposit) {
        this.isDeposit = isDeposit;
    }

//    public String getIsPrint() {
//        return isPrint;
//    }
//
//    public void setIsPrint(String isPrint) {
//        this.isPrint = isPrint;
//    }

    public String getDeskId() {
        return deskId;
    }

    public void setDeskId(String deskId) {
        this.deskId = deskId;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }
//
//    public String getSource() {
//        return source;
//    }
//
//    public void setSource(String source) {
//        this.source = source;
//    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(String totalprice) {
        this.totalprice = totalprice;
    }

//    public String getOnlinePrice() {
//        return onlinePrice;
//    }
//
//    public void setOnlinePrice(String onlinePrice) {
//        this.onlinePrice = onlinePrice;
//    }
//
//    public Double getPlatformDiscountPrice() {
//        return platformDiscountPrice;
//    }
//
//    public void setPlatformDiscountPrice(Double platformDiscountPrice) {
//        this.platformDiscountPrice = platformDiscountPrice;
//    }
//
//    public Double getSpecialCouponPrice() {
//        return specialCouponPrice;
//    }

//    public void setSpecialCouponPrice(Double specialCouponPrice) {
//        this.specialCouponPrice = specialCouponPrice;
//    }
//
//    public Double getUniversalCouponPrice() {
//        return universalCouponPrice;
//    }
//
//    public void setUniversalCouponPrice(Double universalCouponPrice) {
//        this.universalCouponPrice = universalCouponPrice;
//    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDepositDeltaId() {
        return depositDeltaId;
    }

    public void setDepositDeltaId(String depositDeltaId) {
        this.depositDeltaId = depositDeltaId;
    }

//    public String getPayStatus() {
//        return payStatus;
//    }
//
//    public void setPayStatus(String payStatus) {
//        this.payStatus = payStatus;
//    }
//
//    public String getPrepayId() {
//        return prepayId;
//    }
//
//    public void setPrepayId(String prepayId) {
//        this.prepayId = prepayId;
//    }

    public String getDeskName() {
        return deskName;
    }

    public void setDeskName(String deskName) {
        this.deskName = deskName;
    }
}
