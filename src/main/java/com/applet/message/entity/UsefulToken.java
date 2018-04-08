package com.applet.message.entity;

import org.hibernate.annotations.GenericGenerator;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.persistence.*;

@Entity
@Table(name = "token")
public class UsefulToken {
    /**ID*/
    private java.lang.String id;
    /**凭证*/
    @Excel(name="凭证")
    private java.lang.String token;
    /**凭证有效时间*/
    @Excel(name="凭证有效时间")
    private java.lang.Integer activeTime;
    /**创建时间*/
    @Excel(name="创建时间",format = "yyyy-MM-dd hh:mm:ss")
    private java.util.Date myCreateTime;

    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  ID
     */
    @Id
    @GeneratedValue(generator = "paymentableGenerator")
    @GenericGenerator(name = "paymentableGenerator", strategy = "uuid")
    @Column(name ="ID",nullable=false,length=32)
    public java.lang.String getId(){
        return this.id;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  ID
     */
    public void setId(java.lang.String id){
        this.id = id;
    }
    /**
     *方法: 取得java.lang.String
     *@return: java.lang.String  凭证
     */
    @Column(name ="token",nullable=true,length=255)
    public java.lang.String getToken(){
        return this.token;
    }

    /**
     *方法: 设置java.lang.String
     *@param: java.lang.String  凭证
     */
    public void setToken(java.lang.String token){
        this.token = token;
    }
    /**
     *方法: 取得java.lang.Integer
     *@return: java.lang.Integer  凭证有效时间
     */
    @Column(name ="active_time",nullable=true,length=10)
    public java.lang.Integer getActiveTime(){
        return this.activeTime;
    }

    /**
     *方法: 设置java.lang.Integer
     *@param: java.lang.Integer  凭证有效时间
     */
    public void setActiveTime(java.lang.Integer activeTime){
        this.activeTime = activeTime;
    }
    /**
     *方法: 取得java.util.Date
     *@return: java.util.Date  创建时间
     */
    @Column(name ="MY_CREATE_TIME",nullable=true)
    public java.util.Date getMyCreateTime(){
        return this.myCreateTime;
    }

    /**
     *方法: 设置java.util.Date
     *@param: java.util.Date  创建时间
     */
    public void setMyCreateTime(java.util.Date myCreateTime){
        this.myCreateTime = myCreateTime;
    }
}
