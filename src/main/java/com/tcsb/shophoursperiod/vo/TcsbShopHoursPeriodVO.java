package com.tcsb.shophoursperiod.vo;

import com.tcsb.shophoursperiod.entity.TcsbShopHoursPeriodEntity;

public class TcsbShopHoursPeriodVO extends TcsbShopHoursPeriodEntity {
    private String shopName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }
}
