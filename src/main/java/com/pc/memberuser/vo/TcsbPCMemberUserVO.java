package com.pc.memberuser.vo;

import java.util.Date;

public class TcsbPCMemberUserVO {
    /**
     * id
     */
    private java.lang.String id;
    /**
     * 卡号
     */
    private java.lang.String cardNo;
    /**
     * 会员级别ID
     */
    private java.lang.String membershipLevelId;

    private String membershipLevelName;
    /**
     * 账户余额
     */
    private java.lang.Double balance;
    /**
     * 用户名
     */
    private java.lang.String name;
    /**
     * 用户性别
     */
    private java.lang.String sex;
    /**
     * 出生日期
     */
    private java.util.Date birthOfDate;
    /**
     * 创建时间
     */
    private java.util.Date createTime;

    /**
     * 联系方式
     */
    private java.lang.String mobile;

    /**
     * 折扣
     */
    private String discount;
//    /**
//     * 短信验证码
//     */
//    private java.lang.String smscode;
//    /**
//     * 发送时间
//     */
//    private java.util.Date sendtime;
    /**
     * 会员状态
     */
    private String status;

    private String shopId;

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getMembershipLevelId() {
        return membershipLevelId;
    }

    public void setMembershipLevelId(String membershipLevelId) {
        this.membershipLevelId = membershipLevelId;
    }

    public String getMembershipLevelName() {
        return membershipLevelName;
    }

    public void setMembershipLevelName(String membershipLevelName) {
        this.membershipLevelName = membershipLevelName;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthOfDate() {
        return birthOfDate;
    }

    public void setBirthOfDate(Date birthOfDate) {
        this.birthOfDate = birthOfDate;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

//    public String getSmscode() {
//        return smscode;
//    }
//
//    public void setSmscode(String smscode) {
//        this.smscode = smscode;
//    }
//
//    public Date getSendtime() {
//        return sendtime;
//    }
//
//    public void setSendtime(Date sendtime) {
//        this.sendtime = sendtime;
//    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

