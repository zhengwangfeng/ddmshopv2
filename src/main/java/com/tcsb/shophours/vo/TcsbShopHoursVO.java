package com.tcsb.shophours.vo;

import com.tcsb.shophours.entity.TcsbShopHoursEntity;

import java.util.Date;

public class TcsbShopHoursVO extends TcsbShopHoursEntity {
    private String shopName;
    private String shopHoursPeriodName;

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopHoursPeriodName() {
        return shopHoursPeriodName;
    }

    public void setShopHoursPeriodName(String shopHoursPeriodName) {
        this.shopHoursPeriodName = shopHoursPeriodName;
    }
}
