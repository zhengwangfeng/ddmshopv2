package com.tcsb.membertitle.vo;

import com.tcsb.membertitle.entity.TcsbMemberTitleEntity;

public class TcsbMemberTitleVO extends TcsbMemberTitleEntity {
    private String shopName;
    private String memberTitleName;

    public String getMemberTitleName() {
        return memberTitleName;
    }

    public void setMemberTitleName(String memberTitleName) {
        this.memberTitleName = memberTitleName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
