package com.tcsb.coupon.service;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.shop.entity.TcsbShopEntity;

import com.weixin.core.entity.common.AccessToken;
import com.weixin.p3.oauth2.pojo.oauth2.WeixinOauth2Token;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbCouponServiceI extends CommonService {

    public void delete(TcsbCouponEntity entity) throws Exception;

    public Serializable save(TcsbCouponEntity entity) throws Exception;

    public void saveOrUpdate(TcsbCouponEntity entity) throws Exception;

    public String saveTcsbCouponAndSendSms(TcsbCouponEntity tcsbCoupon, TcsbShopEntity tcsbShopEntity);

    AjaxJsonApi getCouponAuthUrl(String domain, String shopId);

    AjaxJsonApi receiveCouponByWeb(String unionid, String fullcutTemplateId) throws Exception;

    AjaxJsonApi getCouponAuthUrl(String domain);

}
