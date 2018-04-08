package com.tcsb.coupon.service.impl;

import com.tcsb.coupon.service.TcsbCouponServiceI;

import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.p3.oauth2.pojo.oauth2.WeixinOauth2Token;
import com.weixin.p3.oauth2.util.OAuth2Util;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import com.weixin.weixinuser.entity.WeixinUserEntity;

import org.jeecgframework.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.io.IOException;
import java.io.Serializable;

import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tcsbCouponService")
@Transactional
public class TcsbCouponServiceImpl extends CommonServiceImpl implements TcsbCouponServiceI {


    public void delete(TcsbCouponEntity entity) throws Exception {
        super.delete(entity);
        //执行删除操作增强业务
        this.doDelBus(entity);
    }

    public Serializable save(TcsbCouponEntity entity) throws Exception {
        Serializable t = super.save(entity);
        //执行新增操作增强业务
        this.doAddBus(entity);
        return t;
    }

    public void saveOrUpdate(TcsbCouponEntity entity) throws Exception {
        super.saveOrUpdate(entity);
        //执行更新操作增强业务
        this.doUpdateBus(entity);
    }

    /**
     * 新增操作增强业务
     *
     * @param t
     * @return
     */
    private void doAddBus(TcsbCouponEntity t) throws Exception {
    }

    /**
     * 更新操作增强业务
     *
     * @param t
     * @return
     */
    private void doUpdateBus(TcsbCouponEntity t) throws Exception {
    }

    /**
     * 删除操作增强业务
     *
     * @param t
     * @return
     */
    private void doDelBus(TcsbCouponEntity t) throws Exception {
    }

    private Map<String, Object> populationMap(TcsbCouponEntity t) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("id", t.getId());
        map.put("shop_id", t.getShopId());
        map.put("user_id", t.getUserId());
        map.put("fullcut_template_id", t.getFullcutTemplateId());
        map.put("use_rebate", t.getUseRebate());
        map.put("update_name", t.getUpdateName());
        map.put("update_date", t.getUpdateDate());
        map.put("update_by", t.getUpdateBy());
        map.put("create_name", t.getCreateName());
        map.put("create_by", t.getCreateBy());
        map.put("create_date", t.getCreateDate());
        return map;
    }

    /**
     * 替换sql中的变量
     *
     * @param sql
     * @param t
     * @return
     */
    public String replaceVal(String sql, TcsbCouponEntity t) {
        sql = sql.replace("#{id}", String.valueOf(t.getId()));
        sql = sql.replace("#{shop_id}", String.valueOf(t.getShopId()));
        sql = sql.replace("#{user_id}", String.valueOf(t.getUserId()));
        sql = sql.replace("#{fullcut_template_id}", String.valueOf(t.getFullcutTemplateId()));
        sql = sql.replace("#{use_rebate}", String.valueOf(t.getUseRebate()));
        sql = sql.replace("#{update_name}", String.valueOf(t.getUpdateName()));
        sql = sql.replace("#{update_date}", String.valueOf(t.getUpdateDate()));
        sql = sql.replace("#{update_by}", String.valueOf(t.getUpdateBy()));
        sql = sql.replace("#{create_name}", String.valueOf(t.getCreateName()));
        sql = sql.replace("#{create_by}", String.valueOf(t.getCreateBy()));
        sql = sql.replace("#{create_date}", String.valueOf(t.getCreateDate()));
        sql = sql.replace("#{UUID}", UUID.randomUUID().toString());
        return sql;
    }

    /**
     * 执行JAVA增强
     */
    private void executeJavaExtend(String cgJavaType, String cgJavaValue, Map<String, Object> data) throws Exception {
        if (StringUtil.isNotEmpty(cgJavaValue)) {
            Object obj = null;
            try {
                if ("class".equals(cgJavaType)) {
                    //因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
                    obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
                } else if ("spring".equals(cgJavaType)) {
                    obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
                }
                if (obj instanceof CgformEnhanceJavaInter) {
                    CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
                    javaInter.execute(data);
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new Exception("执行JAVA增强出现异常！");
            }
        }
    }

    @Override
    public String saveTcsbCouponAndSendSms(TcsbCouponEntity tcsbCoupon, TcsbShopEntity tcsbShopEntity) {
        String message = "优惠卷添加成功";
        String userhql = "from WeixinUserEntity where openid='" + tcsbCoupon.getUserId() + "' and shopId='" + tcsbShopEntity.getId() + "'";
        WeixinUserEntity weixinUserEntity = this.singleResult(userhql);
        TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCoupon.getFullcutTemplateId());
        int userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
        String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
        if (dateUnit.equals("month")) {
            dateUnit = "个月";
        }
        if (dateUnit.equals("year")) {
            dateUnit = "年";
        }
        if (dateUnit.equals("day")) {
            dateUnit = "日";
        }
        String content = "恭喜您获得" + tcsbShopEntity.getName() + "的" + tcsbFullcutTemplateEntity.getDiscount() + "元优惠券一张，有效期" + userPeriod + "" + dateUnit + "，欢迎到店使用。";
        //扣除短信条数
        TcsbShopSmsServiceEntity tcsbShopSmsServiceEntity = this.findUniqueByProperty(TcsbShopSmsServiceEntity.class, "shopId", tcsbShopEntity.getId());
        if (tcsbShopSmsServiceEntity != null) {
            int count = tcsbShopSmsServiceEntity.getCount();
            if (count <= 0) {
                message = "短信条数不足";
            } else {
                try {
                    this.save(tcsbCoupon);
                    if (StringUtil.isNotEmpty(weixinUserEntity)) {
                        //SaleSmsClient.sendMessageForContent(weixinUserEntity.getMobile(),content);
                    } else {
                        String userhql2 = "from WeixinUserEntity where id='" + tcsbCoupon.getUserId() + "' and shopId='" + tcsbShopEntity.getId() + "'";
                        WeixinUserEntity weixinUserEntity2 = this.singleResult(userhql2);
                        if (StringUtil.isNotEmpty(weixinUserEntity2)) {
                            //SaleSmsClient.sendMessageForContent(weixinUserEntity2.getMobile(),content);
                        } else {
                            message = "用户暂未绑定手机号";
                        }
                    }

                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                tcsbShopSmsServiceEntity.setCount(tcsbShopSmsServiceEntity.getCount() - 1);
                this.updateEntitie(tcsbShopSmsServiceEntity);
            }
        }
        return message;
    }

    /**
     * 获取优惠卷授权微信登录url
     *
     * @param domain 域名不含http://
     * @param shopId
     * @return
     */
    @Override
    public AjaxJsonApi getCouponAuthUrl(String domain, String shopId) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        TcsbShopEntity tcsbShopEntity = get(TcsbShopEntity.class, shopId);
        if (tcsbShopEntity != null) {
            String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");
            String url = OAuth2Util.obtainWeixinOAuth2Url(domain, APPID, "snsapi_userinfo");
            url.replace("STATE", shopId);
            ajaxJsonApi.setSuccess(true);
            ajaxJsonApi.setObj(url);
            ajaxJsonApi.setMsg("获取成功");
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("店铺ID错误");
        }

        return ajaxJsonApi;
    }

    /**
     * 获取优惠卷授权微信登录url
     *
     * @param domain 域名不含http://
     * @return
     */
    @Override
    public AjaxJsonApi getCouponAuthUrl(String domain) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");
        String url = OAuth2Util.obtainWeixinOAuth2Url(domain, APPID, "snsapi_userinfo");
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(url);
        ajaxJsonApi.setMsg("获取成功");

        return ajaxJsonApi;
    }

//    /**
//     * 网页领取优惠卷接口
//     *
//     * @return
//     */
//    @Override
//    public AjaxJsonApi receiveCouponByWeb(String unionid, String fullcutTemplateId) throws Exception {
//        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
//        TcsbWeixinUserEntity tcsbWeixinUserEntity = findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", unionid);
//        if (tcsbWeixinUserEntity != null) {
//
//            List<TcsbCouponEntity> tcsbCouponEntities = findHql("from TcsbCouponEntity where fullcutTemplateId=? and unionid=?", fullcutTemplateId, unionid);
//            if (tcsbCouponEntities != null && tcsbCouponEntities.size() > 0) {
//                ajaxJsonApi.setSuccess(false);
//                ajaxJsonApi.setMsg("该优惠卷已领取");
//            } else {
//                TcsbCouponEntity tcsbCouponEntity = new TcsbCouponEntity();
//                tcsbCouponEntity.setFullcutTemplateId(fullcutTemplateId);
//                tcsbCouponEntity.setUnionid(unionid);
//
//                if (tcsbWeixinUserEntity.getOpenid() != null) {
//                    tcsbCouponEntity.setUserId(tcsbWeixinUserEntity.getOpenid());
//                }
//
//                TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = get(TcsbFullcutTemplateEntity.class, fullcutTemplateId);
//                Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
//                String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
//
//                int days = 0;
//                //todo
//                if (dateUnit.equals("year")) {
//                    days = userPeriod * DateUtils.getCurrentYearDays();
//                } else if (dateUnit.equals("month")) {
//                    days = userPeriod * DateUtils.getCurrentMonthDay();
//                } else {
//                    days = userPeriod;
//                }
//                String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
//                tcsbCouponEntity.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
//                save(tcsbCouponEntity);
//                ajaxJsonApi.setSuccess(true);
//                ajaxJsonApi.setMsg("领取成功");
//            }
//        }
//        return ajaxJsonApi;
//    }


    /**
     * 网页领取优惠卷接口
     *
     * @return
     */
    @Override
    public AjaxJsonApi receiveCouponByWeb(String mobile, String fullcutTemplateId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

            List<TcsbCouponEntity> tcsbCouponEntities = findHql("from TcsbCouponEntity where fullcutTemplateId=? and mobile=?", fullcutTemplateId, mobile);
            if (tcsbCouponEntities != null && tcsbCouponEntities.size() > 0) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("该优惠卷已领取");
            } else {
                TcsbCouponEntity tcsbCouponEntity = new TcsbCouponEntity();
                tcsbCouponEntity.setFullcutTemplateId(fullcutTemplateId);
                tcsbCouponEntity.setMobile(mobile);

//                if (tcsbWeixinUserEntity.getOpenid() != null) {
//                    tcsbCouponEntity.setUserId(tcsbWeixinUserEntity.getOpenid());
//                }

                TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = get(TcsbFullcutTemplateEntity.class, fullcutTemplateId);
                Integer userPeriod = 8;
                String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();

                int days = 0;
                //todo
                if (dateUnit.equals("year")) {
                    days = userPeriod * DateUtils.getCurrentYearDays();
                } else if (dateUnit.equals("month")) {
                    days = userPeriod * DateUtils.getCurrentMonthDay();
                } else {
                    days = userPeriod;
                }
                String expiryDate = DateUtils.getAfterDayDate(String.valueOf(days));
                tcsbCouponEntity.setExpiryDate(DateUtils.parseDate(expiryDate, "yyyy-MM-dd HH:mm:ss"));
                save(tcsbCouponEntity);

                TcsbShopEntity tcsbShopEntity = get(TcsbShopEntity.class,tcsbFullcutTemplateEntity.getShopId());

                tcsbCouponEntity.setShopId(tcsbShopEntity.getId());
                tcsbCouponEntity.setUseStatus("0");
                tcsbCouponEntity.setUseRange("0");
                tcsbCouponEntity.setCreateDate(new Date());

                //拼接发送的短信内容
                String content = "恭喜您获得" + tcsbShopEntity.getName() + "的" + tcsbFullcutTemplateEntity.getDiscount() + "元优惠券一张，有效期" + userPeriod + "天" + "，欢迎到店使用。";
                if (StringUtils.isNotEmpty(mobile)) {
                    SaleSmsClient.sendMessageForContent(mobile, content);//发送短信
                }
                ajaxJsonApi.setSuccess(true);
                ajaxJsonApi.setObj(tcsbCouponEntity.getId());
                ajaxJsonApi.setMsg("领取成功");
            }

        return ajaxJsonApi;
    }
}