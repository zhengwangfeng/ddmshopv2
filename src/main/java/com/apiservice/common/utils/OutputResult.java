package com.apiservice.common.utils;

import java.io.Serializable;

public class OutputResult implements Serializable{

    /**
     * 返回客户端统一格式，包括状态码，提示信息，以及业务数据
     */
    private static final long serialVersionUID = 1L;
    //状态码
    private String status;
    //必要的提示信息
    private String message;
    //业务数据
    private Object data;

    public OutputResult(String status,String message,Object data){
        this.status = status;
        this.message = message;
        this.data = data;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
   /* public String toString(){
        if(null == this.data){
            this.setData(new Object());
        }
        return JSON.toJSONString(this);
    }*/
}