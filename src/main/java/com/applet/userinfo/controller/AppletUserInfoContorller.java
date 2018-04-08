/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017 2017年10月25日 下午3:09:00
 */
package com.applet.userinfo.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidParameterSpecException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.applet.userinfo.UserInfoVo;
import com.applet.userinfo.service.AppletUserInfoServiceI;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.annotations.Delete;
import org.apache.log4j.Logger;
import org.apache.xerces.impl.dv.util.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.SellerSmsClient;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeewx.api.core.util.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.applet.appletutil.LatLngUtil;
import com.applet.shopcollect.ShopCollectVo;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbuserfooter.entity.TcsbUserFooterEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.weixinAppletAPI.weixinAppletAPIController;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import com.weixin.weixinuser.service.WeixinUserServiceI;

/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017年10月25日 下午3:09:00
 */
@Controller
@RequestMapping("/appletUserInfo")
public class AppletUserInfoContorller extends BaseController {

    /**
     * Logger for tcsbShopCarService class
     */
    private static final Logger logger = Logger.getLogger(weixinAppletAPIController.class);

    @Autowired
    private WeixinUserServiceI weixinUserService;

    @Autowired
    private AppletUserInfoServiceI appletUserInfoService;

    /**
     * 
     * @param sessionkey
     * @param encryptedData
     * @param iv
     * @param shopId
     * @return
     * @throws Exception
     */
    @Deprecated
    @RequestMapping("/register")
    @ResponseBody
    public AjaxJsonApi register(String sessionkey,String encryptedData,String iv,String shopId) throws Exception {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String result = appletUserInfoService.getUserInfo(encryptedData,sessionkey,iv);
        UserInfoVo userInfo = JSON.parseObject(result, UserInfoVo.class);
        System.out.println(result);
        System.out.println(userInfo);
        TcsbWeixinUserEntity weixinuser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userInfo.getOpenid());
        if(weixinuser == null){
        	//小程序用户不存在
        	if(StringUtil.isNotEmpty(userInfo.getUnionId())){
        		// 根据unionid查询用户
        		TcsbWeixinUserEntity unioniduser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", userInfo.getUnionId());
        		if(unioniduser == null){
        			//小程序用户不存在，公众号未授权
        			appletUserInfoService.saveBaseUserInfo(userInfo);
        		}else{
        			//小程序用户不存在。但是用户在公众号已授权
        			unioniduser.setOpenid(weixinuser.getOpenid());
        			weixinUserService.saveOrUpdate(unioniduser);
        		}
        	}else{
        		appletUserInfoService.saveBaseUserInfo(userInfo);
        	}
        }else{
        	//小程序用户存在
        	// 根据unionid查询用户
        	if(StringUtil.isNotEmpty(userInfo.getUnionId())){
        		if(StringUtil.isEmpty(weixinuser.getUnionid())){
        			TcsbWeixinUserEntity unioniduser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "unionid", userInfo.getUnionId());
        			if(unioniduser == null){
        				//老用户，公众号未授权
        				weixinuser.setUnionid(userInfo.getUnionId());
        				weixinUserService.saveOrUpdate(weixinuser);
        			}else{
        				//老用户，公众号已授权
        				//合并注册信息
        				String wechatOpenId = unioniduser.getWechatOpenId();
        				if(StringUtil.isNotEmpty(unioniduser.getMobile())){
        					String mobile = unioniduser.getMobile();
        					weixinuser.setMobile(mobile);
        				}
        				weixinUserService.delete(unioniduser);
        				weixinuser.setWechatOpenId(wechatOpenId);
        				weixinuser.setUnionid(userInfo.getUnionId());
        				weixinUserService.saveOrUpdate(weixinuser);
        			}
        		}
        		//else新用户
        	}
        }
        
        //绑定店铺用户信息
        if(StringUtil.isNotEmpty(shopId)){
        	String hql = "from WeixinUserEntity where openid='"+userInfo.getOpenid()+"' and shopId='"+shopId+"'";
        	WeixinUserEntity user = weixinUserService.singleResult(hql);
        	if(user == null){
        		appletUserInfoService.saveShopUser(userInfo.getOpenid(), shopId);
        	}
        }
        
        //更新优惠券信息
        if(StringUtil.isNotEmpty(userInfo.getUnionId())){
        	appletUserInfoService.updateUserConponByUnionid(userInfo.getUnionId(),userInfo.getOpenid());
        }
        ajaxJson.setMsg("注册成功");
        ajaxJson.setSuccess(false);
        ajaxJson.setObj(result);
        return ajaxJson;
    }
    
    
    
    
    
    
    

    /**
     * 检测用户是需要绑定手机
     *
     * @param userId
     * @return
     */
    @RequestMapping("/checkMobile")
    @ResponseBody
    public AjaxJsonApi checkMobile(String userId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        TcsbWeixinUserEntity tuser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
        if (StringUtil.isNotEmpty(tuser)) {
            if (StringUtil.isNotEmpty(tuser.getMobile())) {
                ajaxJson.setMsg("用户已绑定手机");
                ajaxJson.setSuccess(false);
                ajaxJson.setObj(true);
            } else {
                ajaxJson.setMsg("未绑定手机");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(null);
            }
        } else {
            ajaxJson.setMsg("用户不存在");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(false);
        }

        return ajaxJson;
    }


    /**
     * 绑定手机号
     *
     * @param userId
     * @param mobile
     * @param smsCode
     * @return
     */
    @RequestMapping("/bingMobile")
    @ResponseBody
    public AjaxJsonApi bingMobile(String userId, @RequestParam String mobile, @RequestParam String smsCode) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();

        TcsbWeixinUserEntity tuser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
        if (StringUtil.isNotEmpty(tuser)) {
            if (tuser.getSmscode().equals(smsCode) && tuser.getWaitMobile().equals(mobile)) {
                TcsbWeixinUserEntity checkmobile = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "mobile", mobile);
                if (StringUtil.isEmpty(checkmobile)) {
                    tuser.setMobile(mobile);
                    tuser.setSendtime(null);
                    tuser.setSmscode(null);
                    tuser.setWaitMobile(null);
                    weixinUserService.saveOrUpdate(tuser);
                }

                //TODO
                //appletUserInfoService.updateUserConpon(mobile, userId);


                ajaxJson.setMsg("绑定成功");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(null);
            } else {
                ajaxJson.setMsg("验证码不正确");
                ajaxJson.setSuccess(false);
                ajaxJson.setObj(null);
            }
        } else {
            ajaxJson.setMsg("用户不存在");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }

    /**
     * 获取手机绑定验证码
     *
     * @param userId
     * @param mobile
     * @return
     */
    @RequestMapping("/getSmsCode")
    @ResponseBody
    public AjaxJsonApi getSmsCode(String userId, String mobile) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(mobile)) {
            TcsbWeixinUserEntity tuser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "mobile", mobile);
            if (StringUtil.isNotEmpty(tuser)) {
                if (tuser.getOpenid().equals(userId)) {
                    ajaxJson.setMsg("已绑定该手机号码");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setObj(null);
                } else {
                    ajaxJson.setMsg("该手机号码已被绑定");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setObj(null);
                }
            } else {
                //检测验证码
                SellerSmsClient client = new SellerSmsClient();
                String smsCode;
                TcsbWeixinUserEntity weixinUser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
                if (StringUtil.isNotEmpty(weixinUser)) {
                    if (StringUtil.isEmpty(weixinUser.getSmscode())) {
                        try {
                            smsCode = client.sendMessage(mobile);
                            weixinUser.setSmscode(smsCode);
                            weixinUser.setWaitMobile(mobile);
                            weixinUser.setSendtime(new Date());
                            weixinUserService.saveOrUpdate(weixinUser);
                            ajaxJson.setMsg("发送成功");
                            ajaxJson.setSuccess(true);
                            ajaxJson.setObj(null);
                        } catch (IOException e) {
                            ajaxJson.setMsg("发送失败");
                            ajaxJson.setSuccess(false);
                            ajaxJson.setObj(null);
                        }
                    } else {
                        Calendar calDes = null;
                        try {
                            calDes = DateUtils.parseCalendar(DateUtils.date2Str(weixinUser.getSendtime(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calSrc = null;
                        try {
                            calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (DateUtils.dateDiff('s', calSrc, calDes) < 120) {
                            ajaxJson.setMsg(ReturnMessageEnum.SMSISSEND.getMsg());
                            ajaxJson.setSuccess(false);
                            ajaxJson.setObj(null);
                        } else {
                            //短信过期重新发送
                            try {
                                smsCode = client.sendMessage(mobile);
                                ajaxJson.setMsg(ReturnMessageEnum.SMSISOUTOFDATE.getMsg() + "重新发送");
                                ajaxJson.setSuccess(true);
                                weixinUser.setSmscode(smsCode);
                                weixinUser.setWaitMobile(mobile);
                                weixinUser.setSendtime(new Date());
                                ajaxJson.setObj(null);
                                weixinUserService.saveOrUpdate(weixinUser);
                            } catch (IOException e) {
                                ajaxJson.setMsg("发送失败");
                                ajaxJson.setSuccess(false);
                                ajaxJson.setObj(null);
                            }

                        }
                    }
                } else {
                    ajaxJson.setMsg("用户不存在");
                    ajaxJson.setSuccess(false);
                    ajaxJson.setObj(null);
                }
            }
        } else {
            ajaxJson.setMsg("用户ID，mobile不能为空");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }


        return ajaxJson;
    }


    /**
     * 获取绑定手机信息
     *
     * @param userId
     * @param mobile
     * @param smsCode
     * @return
     */
    @RequestMapping("/getUserMobile")
    @ResponseBody
    public AjaxJsonApi getUserMobile(@RequestParam String userId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        TcsbWeixinUserEntity weixinUser = weixinUserService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
        //tcsbAssociatorBigdatasave.setUserMobile(tcsbWeixinUserEntity.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})","$1****$2"));
        if (StringUtil.isNotEmpty(weixinUser)) {
            if (StringUtil.isNotEmpty(weixinUser.getMobile())) {
                ajaxJson.setMsg("获取成功");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(weixinUser.getMobile().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            } else {
                ajaxJson.setMsg("用户未绑定手机");
                ajaxJson.setSuccess(true);
                ajaxJson.setObj(0);
            }
        } else {
            ajaxJson.setMsg("用户不存在");
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }

    /**
     * 我的足迹
     *
     * @param userId
     * @return
     */
    @RequestMapping("/myFootprint")
    @ResponseBody
    public AjaxJsonApi myFootprint(@RequestParam String userId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();

        List<ShopCollectVo> collectShopList = new ArrayList<>();
        ShopCollectVo shopCollectVo;

        CriteriaQuery cq = new CriteriaQuery(TcsbUserFooterEntity.class);
        cq.add(Restrictions.eq("openid", userId));
        cq.addOrder("createDate", SortDirection.desc);
        cq.setPageSize(10);
        cq.setCurPage(0);
        cq.add();
        List<TcsbUserFooterEntity> weixinUserFooter = weixinUserService.getListByCriteriaQuery(cq, true);
        if (weixinUserFooter.size() > 0) {
            for (TcsbUserFooterEntity userfooter : weixinUserFooter) {
                shopCollectVo = new ShopCollectVo();
                TcsbShopEntity tcsbShop = weixinUserService.get(TcsbShopEntity.class, userfooter.getShopId());
                if (tcsbShop != null) {
                    TcsbShopEntity tcsbShopEntity = new TcsbShopEntity();
                    try {
                        BeanUtils.copyProperties(tcsbShopEntity, tcsbShop);
                        tcsbShopEntity.setHeadimg(getAppletCkPath() + tcsbShopEntity.getHeadimg().replace("images", "_thumbs/Images"));
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                    shopCollectVo.setTcsbShop(tcsbShopEntity);
                    //获取店铺优惠
                    String hql = "from TcsbShopFullcutTemplateEntity where shopId=?";
                    List<TcsbShopFullcutTemplateEntity> TcsbShopFullcutTemplateEntity = weixinUserService.findHql(hql, userfooter.getShopId());
                    if (StringUtil.isNotEmpty(TcsbShopFullcutTemplateEntity)) {
                        String promotion = "";
                        for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity2 : TcsbShopFullcutTemplateEntity) {
                            promotion = promotion + "满" + tcsbShopFullcutTemplateEntity2.getTotal() + "减" + tcsbShopFullcutTemplateEntity2.getDiscount() + ",";
                        }
                        if (StringUtil.isNotEmpty(promotion)) {
                            promotion = promotion.substring(0, promotion.length() - 1);
                            shopCollectVo.setPromotion(promotion);
                        } else {
                            shopCollectVo.setPromotion("暂无优惠信息");
                        }
                    } else {
                        shopCollectVo.setPromotion("暂无优惠信息");
                    }


                    //获取商家订单信息

                    String orderhql = "from TcsbOrderEntity where shopId='" + userfooter.getShopId() + "' and payStatus=1";
                    List<TcsbOrderEntity> tcsbOrderEntity = weixinUserService.findByQueryString(orderhql);
                    if (tcsbOrderEntity.size() > 0) {
                        double allsale = 0.0;
                        for (TcsbOrderEntity tcsbOrderEntity2 : tcsbOrderEntity) {
                            allsale = BigDecimalUtil.add(allsale, tcsbOrderEntity2.getTotalPrice());
                        }
                        shopCollectVo.setBuyPeopleNum(tcsbOrderEntity.size() + "");
                        String avgsale = BigDecimalUtil.divide(allsale + "", tcsbOrderEntity.size() + "", 0);
                        shopCollectVo.setAvgSale(avgsale);
                    } else {
                        shopCollectVo.setBuyPeopleNum("0");
                        shopCollectVo.setAvgSale("0");
                    }

                    shopCollectVo.setDistance("");
                    collectShopList.add(shopCollectVo);
                    ajaxJson.setMsg("获取成功");
                    ajaxJson.setSuccess(true);
                    ajaxJson.setObj(collectShopList);
                }

            }
        } else {
            ajaxJson.setMsg("用户暂无足迹");
            ajaxJson.setSuccess(true);
            ajaxJson.setObj(null);
        }

        return ajaxJson;
    }


    /**
     * 删除我的足迹
     *
     * @param userId
     * @param shopId
     * @return
     */
    @RequestMapping("/deleteFootprint")
    @ResponseBody
    public AjaxJsonApi deleteFooter(String userId, String shopId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String hql = "from TcsbUserFooterEntity where openid='" + userId + "' and shopId='" + shopId + "'";
        TcsbUserFooterEntity tcsbUserFooterEntity = weixinUserService.singleResult(hql);
        if (StringUtil.isNotEmpty(tcsbUserFooterEntity)) {
            weixinUserService.delete(tcsbUserFooterEntity);
            ajaxJson.setMsg("删除成功");
            ajaxJson.setObj(null);
            ajaxJson.setSuccess(true);
        } else {
            ajaxJson.setMsg("删除失败");
            ajaxJson.setObj(null);
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }

    /**
     * 添加用户足迹
     *
     * @param userId
     * @param shopId
     * @return
     */
    @RequestMapping("/addFootprint")
    @ResponseBody
    public AjaxJsonApi addFooter(String userId, String shopId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();

        if (StringUtil.isNotEmpty(userId) && StringUtil.isNotEmpty(shopId)) {
            String hql = "from TcsbUserFooterEntity where openid='" + userId + "' and shopId='" + shopId + "'";
            TcsbUserFooterEntity tcsbUserFooterEntity = weixinUserService.singleResult(hql);
            if (StringUtil.isNotEmpty(tcsbUserFooterEntity)) {
                tcsbUserFooterEntity.setCreateDate(new Date());
                weixinUserService.saveOrUpdate(tcsbUserFooterEntity);
                ajaxJson.setMsg("添加成功");
                ajaxJson.setObj(null);
                ajaxJson.setSuccess(true);
            } else {
                TcsbUserFooterEntity tf = new TcsbUserFooterEntity();
                tf.setOpenid(userId);
                tf.setShopId(shopId);
                tf.setCreateDate(new Date());
                weixinUserService.save(tf);
                ajaxJson.setMsg("添加成功");
                ajaxJson.setObj(null);
                ajaxJson.setSuccess(true);
            }
        } else {
            ajaxJson.setMsg("添加失败");
            ajaxJson.setObj(null);
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }


}
