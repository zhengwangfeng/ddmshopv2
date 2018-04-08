package com.pc.memberuser.vo;

import java.util.Date;

public class TcsbPCMemberUserBasicMsgVO {
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
     * 联系方式
     */
    private java.lang.String mobile;

//    private String status;

    private String shopId;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

//    public String getStatus() {
//        return status;
//    }
//
//    public void setStatus(String status) {
//        this.status = status;
//    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }
}
