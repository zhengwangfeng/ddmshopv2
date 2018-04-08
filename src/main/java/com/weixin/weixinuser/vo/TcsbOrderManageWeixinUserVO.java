package com.weixin.weixinuser.vo;

import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;

public class TcsbOrderManageWeixinUserVO extends TcsbWeixinUserEntity {
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
