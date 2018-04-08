package com.pc.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单状态
 */
public enum OrderStatusEnum {
    XIAO_FEI_ZHONG("消费中", "1"), XIANSHANG("已完成", "0");

    public static Map<String, String> map = new HashMap<String, String>();

    static {
        for (OrderStatusEnum e : OrderStatusEnum.values()) {
            map.put(e.getValue(),e.getName());
        }
    }

    public static String getMessage(String value){
        return map.get(value);
    }

    private String name;
    private String value;

    private OrderStatusEnum(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
