package com.tcsb.memberlevelequity.vo;

import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;

public class TcsbMemberLevelEquityVO extends TcsbMemberLevelEquityEntity {
    private String shopName;
    private String membershipLevelName;

    public String getMembershipLevelName() {
        return membershipLevelName;
    }

    public void setMembershipLevelName(String membershipLevelName) {
        this.membershipLevelName = membershipLevelName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
