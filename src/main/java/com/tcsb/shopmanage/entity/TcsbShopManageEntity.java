package com.tcsb.shopmanage.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author zwf
 */
@Entity
@Table(name = "tcsb_shop_manage", schema = "")
public class TcsbShopManageEntity {
    private String id;

    private String shopId;

    private String cpuCode;

    //前台1是前台0不是
    private String prosceniumStatus;

    private java.util.Date createTime;

    // TODO: 2018/3/2 shop里面设置多个前台，开放结账权限
//    /**
//     * 前台权限1是前台
//     */
//    private String prosceniumAuth;
//    /**
//     * 结账权限1开放0只前台结账
//     */
//    private String accountAuth;

    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name = "ID", nullable = false, length = 32)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "shop_id", nullable = true, length = 32)
    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    @Column(name = "cpu_code", nullable = true, length = 64)
    public String getCpuCode() {
        return cpuCode;
    }

    public void setCpuCode(String cpuCode) {
        this.cpuCode = cpuCode;
    }

    @Column(name = "CREATE_TIME", nullable = true)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Column(name = "proscenium_status", nullable = true, length = 1)
    public String getProsceniumStatus() {
        return prosceniumStatus;
    }

    public void setProsceniumStatus(String prosceniumStatus) {
        this.prosceniumStatus = prosceniumStatus;
    }

    //    @Column(name = "proscenium_auth", nullable = true, length = 1)
//    public String getProsceniumAuth() {
//        return prosceniumAuth;
//    }
//
//    public void setProsceniumAuth(String prosceniumAuth) {
//        this.prosceniumAuth = prosceniumAuth;
//    }

//    @Column(name = "account_auth", nullable = true, length = 1)
//    public String getAccountAuth() {
//        return accountAuth;
//    }
//
//    public void setAccountAuth(String accountAuth) {
//        this.accountAuth = accountAuth;
//    }
}
