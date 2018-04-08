package com.applet.message.vo;

public class Rows {
    private String value;
    private String color;

    public Rows(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public static Rows getDefaultRows(){
        return new Rows("","#000000");
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
