package com.pc.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 下单方式
 */
public enum OrderMethodEnum {
    XAOMA("扫码", "0"), XIANSHANG("线上", "1"), KEHUDUAN("客户端", "2");

    public static Map<String, String> map = new HashMap<String, String>();

    static {
        for (OrderMethodEnum e : OrderMethodEnum.values()) {
            map.put(e.getValue(),e.getName());
        }
    }

    public static String getMessage(String value){
        return map.get(value);
    }

    private String name;
    private String value;

    private OrderMethodEnum(String name, String value) {
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
