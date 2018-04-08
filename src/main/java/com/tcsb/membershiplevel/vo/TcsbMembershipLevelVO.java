package com.tcsb.membershiplevel.vo;


import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;

public class TcsbMembershipLevelVO extends TcsbMembershipLevelEntity {
    private String shopName;
    private String memberLevelConditionsName;
    private String memberLevelEquityName;
    private String memberTitleName;


    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getMemberLevelConditionsName() {
        return memberLevelConditionsName;
    }

    public void setMemberLevelConditionsName(String memberLevelConditionsName) {
        this.memberLevelConditionsName = memberLevelConditionsName;
    }

    public String getMemberTitleName() {
        return memberTitleName;
    }

    public void setMemberTitleName(String memberTitleName) {
        this.memberTitleName = memberTitleName;
    }

    public String getMemberLevelEquityName() {
        return memberLevelEquityName;
    }

    public void setMemberLevelEquityName(String memberLevelEquityName) {
        this.memberLevelEquityName = memberLevelEquityName;
    }
}
