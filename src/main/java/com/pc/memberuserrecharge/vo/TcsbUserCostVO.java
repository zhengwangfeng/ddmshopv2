package com.pc.memberuserrecharge.vo;

import java.util.Date;

public class TcsbUserCostVO {
    /**id*/
    private java.lang.String id;

    /**用户消费*/
    private java.lang.Double cost;

    /**消费时间*/
    private java.util.Date createDate;

    /**账户余额*/
    private Double balance;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
