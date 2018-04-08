package com.tcsb.common;

public enum NoticeEnum {
    SHOP_SERVICE("0001","tcsbShopService","1"),
    MEMBERSHIP_USER("0002","tcsbMemberUserController","1");
    private String serviceCode;
    private String serviceName;
    private String authority;

    NoticeEnum(String serviceCode, String serviceName, String authority) {
        this.serviceCode = serviceCode;
        this.serviceName = serviceName;
        this.authority = authority;
    }

    public String getServiceCode() {
        return serviceCode;
    }

    public void setServiceCode(String serviceCode) {
        this.serviceCode = serviceCode;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
