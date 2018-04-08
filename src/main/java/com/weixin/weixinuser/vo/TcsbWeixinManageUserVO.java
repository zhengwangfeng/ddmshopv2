package com.weixin.weixinuser.vo;

import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;

public class TcsbWeixinManageUserVO extends TcsbWeixinUserEntity{
    private String note;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
