/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017 2017年10月27日 下午5:05:01
 */
package com.applet.shoporder.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.*;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applet.shopcar.vo.TcsbShopCarTempVO;
import com.applet.shoporder.service.AppletShopOrderServiceI;
import com.applet.shoporder.vo.AppletRequestParamVo;
import com.applet.shoporder.vo.AppletReservationRequestParamVo;
import com.applet.shoporder.vo.CouponDto;
import com.applet.shoporder.vo.OrderInfoVo;
import com.applet.shoporder.vo.OrderReturnVo;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.depositdelta.service.TcsbDepositDeltaServiceI;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.mealfee.entity.TcsbMealFeeEntity;
import com.tcsb.mealfee.service.TcsbMealFeeServiceI;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import com.tcsb.shopcar.VO.TcsbShopCarVO;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopsharegoldcoin.entity.TcsbShopShareGoldCoinEntity;
import com.tcsb.shopsharegoldcoinrecord.entity.TcsbShopShareGoldCoinRecordEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbdeskreservation.service.TcsbDeskReservationServiceI;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;
import com.tcsb.tcsbdeskreservationitem.service.TcsbDeskReservationItemServiceI;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsborderparent.service.TcsbOrderParentServiceI;
import com.tcsb.tcsbshoppaymode.entity.TcsbShopPaymodeEntity;
import com.tcsb.userreservationshopcar.entity.TcsbUserReservationShopCarEntity;
import com.tcsb.userreservationshopcar.service.TcsbUserReservationShopCarServiceI;
import com.tcsb.usershopcar.entity.TcsbUserShopCarEntity;
import com.tcsb.usershopcar.service.TcsbUserShopCarServiceI;
import com.tcsb.weixinAppletAPI.weixinAppletAPIController;

/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017年10月27日 下午5:05:01
 */
@Controller
@RequestMapping("/appletShopOrder")
public class ShopOrderController extends BaseController {

    /**
     * Logger for tcsbShopCarService class
     */
    private static final Logger logger = Logger.getLogger(weixinAppletAPIController.class);

    @Autowired
    private TcsbOrderServiceI tcsbOrderService;

    @Autowired
    private AppletShopOrderServiceI appletService;
    @Autowired
    private TcsbMealFeeServiceI tcsbMealFeeService;
    @Autowired
    private TcsbOrderParentServiceI tcsbOrderParentService;
    @Autowired
    private TcsbUserShopCarServiceI tcsbUserShopCarService;
    @Autowired
    private TcsbDeskReservationServiceI tcsbDeskReservationService;
    @Autowired
    private TcsbUserReservationShopCarServiceI tcsbUserReservationShopCarService;
    @Autowired
    private TcsbDeskReservationItemServiceI tcsbDeskReservationItemService;
    @Autowired
    private TcsbShopCarServiceI tcsbShopCarService;
    @Autowired
    private TcsbDepositDeltaServiceI tcsbDepositDeltaService;

    
    @RequestMapping("/getOrderByOrderParentId")
    @ResponseBody
    public  AjaxJsonApi getOrderByOrderParentId(String orderParentId, String shopId, String userId) {
    	AjaxJsonApi ajaxJson = new AjaxJsonApi();
    	TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderParentId);
    	OrderInfoVo oInfo = appletService.getShopDeskOrder(orderParentId);
    	oInfo.setOrderParentId(orderParentId);
    	//获取桌位名称
    	if(StringUtil.isNotEmpty(tcsbOrderParentEntity)){
    		if(StringUtil.isNotEmpty(tcsbOrderParentEntity.getDeskId())){
    			TcsbDeskEntity desk = tcsbDeskReservationService.get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
    			oInfo.setOrderDeskName(desk.getDeskName());
    		}
    	}
    	
    	//获取用户分享金
    	TcsbShopShareGoldCoinEntity shareGoldCoin = appletService.getUserShareGoldCoin(userId, shopId);
    	if(StringUtil.isNotEmpty(shareGoldCoin)){
    		if(Double.valueOf(oInfo.getNeedPayMoney())>Double.valueOf(shareGoldCoin.getTotal())){
        		double needPay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(shareGoldCoin.getTotal()));
            	oInfo.setNeedPayMoney(needPay + "");
            	oInfo.setShareGoldCoin(BigDecimalUtil.numericRetentionDecimal(Double.valueOf(shareGoldCoin.getTotal()), 2));
        	}else{
        		String shareGoldCoin2 = oInfo.getNeedPayMoney();
        		oInfo.setShareGoldCoin(shareGoldCoin2);
        		oInfo.setNeedPayMoney("0");
        	}
    	}else{
    		oInfo.setShareGoldCoin("0");
    	}
    	
    	//获取订单押金
    	double frontMoney = 0.0D;
    	if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getFrontMoney())) {
            if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getUseFrontMoney())) {
                if (tcsbOrderParentEntity.getUseFrontMoney().equals("1")) {
                    frontMoney = 0.0D;
                } else {
                    frontMoney = tcsbOrderParentEntity.getFrontMoney();
                }
            } else {
                frontMoney = tcsbOrderParentEntity.getFrontMoney();
            }
            oInfo.setFronMoney(BigDecimalUtil.numericRetentionDecimal(tcsbOrderParentEntity.getFrontMoney(), 2));
        }else{
        	oInfo.setFronMoney("0");
        }
    	//检测该订单是否是要交餐位费
    	 TcsbMealFeeEntity tcsbMealFeeEntity = tcsbMealFeeService.findUniqueByProperty(TcsbMealFeeEntity.class, "shopId", shopId);
         if (tcsbMealFeeEntity == null) {
             oInfo.setHaveMealFee(false);
             oInfo.setMealFee("0");
         } else {
         	//是否交过餐位费
        	 oInfo.setHaveMealFee(true);
        	 double mealFee = BigDecimalUtil.mul(tcsbOrderParentEntity.getPeople(), tcsbMealFeeEntity.getMealFee());
        	 oInfo.setMealFee(BigDecimalUtil.numericRetentionDecimal(mealFee, 2));
        	 if(oInfo.getPayOrderItemArray().size() < 1){
        		 double needPayMoney = BigDecimalUtil.add(Double.valueOf(oInfo.getNeedPayMoney()), mealFee);
        		 oInfo.setNeedPayMoney(needPayMoney + "");
        	 }
         }
         
         //
         double CheckdiscountPrice = BigDecimalUtil
        		 .sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(oInfo.getNoDiscountMoney())); 
         if(CheckdiscountPrice > 0){
        	//店铺活动
        		 //double shopDiscountPrice = appletService.isUseShopDiscount(oInfo.getTcsbOrderEntity());
                 //if( shopDiscountPrice <= 0){
                	 List<TcsbShopFullcutTemplateEntity> tcsbFullcutTemplateEntity = appletService.getShopDiscountActivity(shopId);
                	 if(tcsbFullcutTemplateEntity.size()>0){
                		 boolean hasCut = false;
                         for (int i = tcsbFullcutTemplateEntity.size() - 1; i >= 0; i--) {
                             //总额扣除不参与打折的菜品金额，判断是否满足条件
                             if (CheckdiscountPrice >= tcsbFullcutTemplateEntity.get(i).getTotal()) {
                                 double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(tcsbFullcutTemplateEntity.get(i).getDiscount()));
                                 if (needpay > 0) {
                                     oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
                                 } else {
                                     oInfo.setNeedPayMoney("0");
                                 }
                                 CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, Double.parseDouble(tcsbFullcutTemplateEntity.get(i).getDiscount() + ""));
                                 oInfo.setShopUserDiscountMsg(tcsbFullcutTemplateEntity.get(i).getDiscount() + "");
                                 oInfo.setHasShopFullcut(true);
                                 hasCut = true;
                                 break;
                             }
                         }
                         if (!hasCut) {
                             oInfo.setShopUserDiscountMsg("0");
                             oInfo.setHasShopFullcut(false);
                         }
                	 }else{
                		 oInfo.setShopUserDiscountMsg("0");
                         oInfo.setHasShopFullcut(false);
                	 }
                 /*}else{
                	 oInfo.setShopUserDiscountMsg(BigDecimalUtil.numericRetentionDecimal(shopDiscountPrice, 2));
                     oInfo.setHasShopFullcut(true);
                 }*/
         }
        	
             
             //优惠券
         if(CheckdiscountPrice>0){
             //double couponPrice = appletService.isUseCoupon(oInfo.getTcsbOrderEntity());
             //if( couponPrice <= 0){
            	 List<CouponDto> couponDtoList = appletService.getUserCoupon(userId,shopId);
            	 if(couponDtoList.size()>0){
            		 boolean hasCut = false;
                     outer:
                     for (int i = couponDtoList.size() - 1; i >= 0; i--) {
                    	 if (CheckdiscountPrice >= couponDtoList.get(i).getTotal()) {
                    		 if(StringUtil.isNotEmpty(couponDtoList.get(i).getUseRange())){
                    			 if(couponDtoList.get(i).getUseRange().equals("0")){
                    				 double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(couponDtoList.get(i).getDiscount() + ""));
                                     if (needpay > 0) {
                                         oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
                                     } else {
                                         oInfo.setNeedPayMoney("0");
                                     }
                                     oInfo.setUniversalCouponPrice(couponDtoList.get(i).getDiscount() + "");
                                     CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponDtoList.get(i).getDiscount());
                                     hasCut = true;
                                     break outer;
                        		 }else{
                        			//查找订单项中是否有该道菜
                                     for (int j = 0; j < oInfo.getNopayOrderItemArray().size(); j++) {
                                         if (couponDtoList.get(i).getFoodId().equals(oInfo.getNopayOrderItemArray().get(j).getFoodId())) {
                                             double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(couponDtoList.get(i).getDiscount() + ""));
                                             if (needpay > 0) {
                                                 oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
                                             } else {
                                                 oInfo.setNeedPayMoney("0");
                                             }
                                             oInfo.setUniversalCouponPrice(couponDtoList.get(i).getDiscount() + "");
                                             CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponDtoList.get(i).getDiscount());
                                             hasCut = true;
                                             break outer;
                                         }
                                     } 
                        		 }
                    		 }else{
                    			 double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(couponDtoList.get(i).getDiscount() + ""));
                                 if (needpay > 0) {
                                     oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
                                 } else {
                                     oInfo.setNeedPayMoney("0");
                                 }
                                 oInfo.setUniversalCouponPrice(couponDtoList.get(i).getDiscount() + "");
                                 CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponDtoList.get(i).getDiscount());
                                 hasCut = true;
                                 break outer;
                    		 } 
                    	 }
                     }
            		 if (!hasCut) {
                         oInfo.setUniversalCouponPrice("0");
                         oInfo.setHasFullCut(false);
                     }
            	 }else{
            		 oInfo.setUniversalCouponPrice("0");
                     oInfo.setHasFullCut(false);
            	 }
             /*}else{
            	 CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponPrice);
            	 oInfo.setUniversalCouponPrice(BigDecimalUtil.numericRetentionDecimal(couponPrice, 2));
                 oInfo.setHasFullCut(true);
             }*/
        	//}
         }
         
         //检测会员折扣
         //VIP折扣
         //获取用户会员信息
         String hql = "from TcsbMemberUserEntity where openid='" + userId + "' and shopId='" + shopId + "'";
         TcsbMemberUserEntity tm = tcsbOrderService.singleResult(hql);
         if (StringUtil.isNotEmpty(tm)) {
             //获取会员详情
             //TcsbMembershipLevelEntity tmship = tcsbOrderService.get(TcsbMembershipLevelEntity.class, tm.getMembershipLevelId());
             TcsbMemberLevelEquityEntity tmle = tcsbOrderService.findUniqueByProperty(TcsbMemberLevelEquityEntity.class, "membershipLevelId", tm.getMembershipLevelId());
             if (tmle.getIsDiscount().equals("1")) {
                 String discount = BigDecimalUtil.divide(tmle.getDiscount() + "", 10 + "", 2);
                 if (CheckdiscountPrice > 0) {
                     //
                     double needDisMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(oInfo.getNoDiscountMoney()));
                     if(needDisMoney > 0){
                    	 double vipneedMoney = BigDecimalUtil.mul(needDisMoney, Double.valueOf(discount));
                         double vipdisMoney = BigDecimalUtil.sub(needDisMoney, vipneedMoney);
                         double needPayMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), vipdisMoney);
                         oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needPayMoney, 2));
                         oInfo.setVipDisCount(BigDecimalUtil.numericRetentionDecimal(vipdisMoney, 2));
                     }else{
                    	 oInfo.setVipDisCount("0"); 
                     }
                     
                 } else {
                     oInfo.setVipDisCount("0");
                 }
             } else {
                 oInfo.setVipDisCount("0");
             }
         } else {
             oInfo.setVipDisCount("0");
         }
         
         
         if(Double.valueOf(oInfo.getNoPayMoney())>0){
        	 oInfo.setNoPayOrder(true);
         }else{
        	 oInfo.setNoPayOrder(false);
         }
         
         double needMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), frontMoney);
         if(needMoney>0){
        	 oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needMoney, 2));
         }else{
        	 oInfo.setNeedPayMoney("0");
         }
         
         ajaxJson.setMsg("请求成功");
         ajaxJson.setSuccess(true);
         ajaxJson.setObj(oInfo);
         return ajaxJson;
    }
    


    /**
     * (餐后付款)生成订单并返回订单Id
     *
     * @return
     */
    @RequestMapping("/saveOrderReturnOrderParentId")
    @ResponseBody
    public AjaxJsonApi saveOrderReturnOrderParentId(AppletRequestParamVo param) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String orderParentId = tcsbOrderService.saveOrderReturnOrderParentId(param);
        if(StringUtil.isNotEmpty(orderParentId)){
        	ajaxJson.setSuccess(true);
            ajaxJson.setObj(orderParentId);
        }else{
        	ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }

    /**
     * (扫码餐前付款直接支付弹出需要支付的金额并返回父订单号)
     *
     * @return
     */
    @RequestMapping("/getMoneyByShopCar")
    @ResponseBody
    public AjaxJsonApi getMoneyByShopCar(AppletRequestParamVo param) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Map<String, Object> map = tcsbOrderService.getMoneyByShopCar(param);
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(map);
        return ajaxJson;
    }

    /**
     * (预约餐前付款直接支付弹出需要支付的金额并返回父订单号(全额的情况))
     *
     * @return
     */
    @RequestMapping("/getReservationTotalMoneyByShopCar")
    @ResponseBody
    public AjaxJsonApi getReservationTotalMoneyByShopCar(TcsbShopCarTempVO tcsbShopCarVO) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Map<String, Object> map = tcsbOrderService.getReservationTotalMoneyByShopCar(tcsbShopCarVO);
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(map);
        return ajaxJson;
    }

    /**
     * (预约餐前付款直接支付弹出需要支付的金额并返回父订单号(押金的情况))
     *
     * @return
     */
    @RequestMapping("/getReservationDepositMoneyByShopCar")
    @ResponseBody
    public AjaxJsonApi getReservationDepositMoneyByShopCar(TcsbShopCarTempVO tcsbShopCarVO) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Map<String, Object> map = tcsbOrderService.getReservationDepositMoneyByShopCar(tcsbShopCarVO);
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(map);
        return ajaxJson;
    }


    /**
     * 获取店铺支付方式
     *
     * @param deskId
     * @return
     */
    @RequestMapping("/getPayMode")
    @ResponseBody
    public AjaxJsonApi getPayMode(String shopId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        ajaxJson.setObj(appletService.getShopPaymode(shopId));
        ajaxJson.setMsg("请求成功");
        ajaxJson.setSuccess(true);
        return ajaxJson;
    }

    /**
     * 获取订单信息
     *
     * @param orderId
     * @return
     */
    @RequestMapping("/getOrderByOrderId")
    @ResponseBody
    public AjaxJsonApi getOrder(String orderId, String shopId, String userId,String unionid) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        OrderInfoVo oInfo = new OrderInfoVo();
        oInfo.setOrderParentId(orderId);
        double frontMoney = 0.0;
        double frontMoneyVo = 0.0;
        TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderId);
        
        if(StringUtil.isNotEmpty(tcsbOrderParentEntity)){
    		if(StringUtil.isNotEmpty(tcsbOrderParentEntity.getDeskId())){
    			TcsbDeskEntity desk = tcsbDeskReservationService.get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
    			oInfo.setOrderDeskName(desk.getDeskName());
    		}
    	}
        
        
        if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getFrontMoney())) {
            frontMoneyVo = tcsbOrderParentEntity.getFrontMoney();
            if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getUseFrontMoney())) {
                if (tcsbOrderParentEntity.getUseFrontMoney().equals("1")) {
                    frontMoney = 0.0;
                } else {
                    frontMoney = tcsbOrderParentEntity.getFrontMoney();
                }
            } else {
                frontMoney = tcsbOrderParentEntity.getFrontMoney();
            }
        }
        oInfo.setFronMoney(frontMoneyVo + "");

        //=====全局参数定义区=====
        boolean platformDiscount = false;//平台优惠
        boolean specialCoupon = false;//商家活动优惠
        boolean universalCoupon = false;//会员优惠券
        double platformDiscountPrice = 0.0;//平台优惠金额
        double specialCouponPrice = 0.0;//商家活动优惠金额
        double universalCouponPrice = 0.0;//会员优惠券金额
        Double finalMoney = 0.0;//总金额

        Double noPayMoney = 0.0;//未付款
//        if (oInfo.isHaveMealFee()) {
//            noPayMoney = Double.parseDouble(oInfo.getMealFee());
//        }
        
        boolean isMealFee = false;
        
        Double payMoney = 0.0;//已付款
        double nodisMoney = 0.0;
        boolean noPayOrder = false;
        List<TcsbOrderItemEntity> tcsbOrderItemArray = new ArrayList<>();//订单总菜单项
        List<TcsbOrderItemEntity> payOrderItemArray = new ArrayList<>();//订单已支付菜单项
        List<TcsbOrderItemEntity> nopayOrderItemArray = new ArrayList<>();//订单未支付菜单项
        //=====全局参数定义区=====

        List<TcsbOrderEntity> TcsbOrder = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderParentId", orderId);
        //检测是否使用过店铺活动===start===
        //检测是否使用过优惠券===start===
        //检测是否使用过平台优惠券===start===
        for (TcsbOrderEntity tcsbOrderEntity2 : TcsbOrder) {
            if (tcsbOrderEntity2.getPayStatus().equals("1")) {
                if (tcsbOrderEntity2.getSpecialCouponPrice() > 0) {
                    //System.out.println("使用过店铺活动");
                    specialCoupon = true;
                    specialCouponPrice = tcsbOrderEntity2.getSpecialCouponPrice();
                    //break;
                }
                if (tcsbOrderEntity2.getUniversalCouponPrice() > 0) {
                    //System.out.println("使用过优惠券");
                    universalCoupon = true;
                    universalCouponPrice = tcsbOrderEntity2.getUniversalCouponPrice();
                    //break;
                }
                
                if(tcsbOrderEntity2.getPlatformDiscountPrice() > 0){
                	platformDiscount = true;//平台优惠
                    platformDiscountPrice = tcsbOrderEntity2.getPlatformDiscountPrice();//平台优惠金额
                }
                
            }
            if (specialCoupon && universalCoupon && platformDiscount) {
                break;
            }
        }
        
        boolean useCoupon = true;
        
        if(universalCoupon || platformDiscount){
        	useCoupon = false;
        	oInfo.setHasFullCut(true);
        	double couponPrice = BigDecimalUtil.add(universalCouponPrice, platformDiscountPrice);
        	oInfo.setCouponPrice(BigDecimalUtil.numericRetentionDecimal(couponPrice, 2));
        }
        
        //检测是否使用过店铺活动===end===
        //检测是否使用过优惠券===end===
        //检测是否使用过平台优惠券===end===
        //===========================

        for (TcsbOrderEntity tcsbOrderEntity2 : TcsbOrder) {
            //finalMoney = BigDecimalUtil.add(finalMoney, tcsbOrderEntity2.getTotalPrice());
            List<TcsbOrderItemEntity> tcsbOrderItemEntity = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
            if (tcsbOrderEntity2.getPayStatus().equals("1")) {
                for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                    TcsbFoodEntity tf = tcsbOrderService.get(TcsbFoodEntity.class, tcsbOrderItemEntity2.getFoodId());
                    double addmoney = 0.0D;
                    if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getStandardId())) {
                        TcsbFoodStandardEntity tfs = tcsbOrderService.get(TcsbFoodStandardEntity.class, tcsbOrderItemEntity2.getStandardId());
                        if(StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())){
                        	addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue()-tcsbOrderItemEntity2.getRetreatNum(), tfs.getPrice().doubleValue());
                        }else{
                        	addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue(), tfs.getPrice().doubleValue());
                        }
                    } else {
                    	if(StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())){
                    		addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue()-tcsbOrderItemEntity2.getRetreatNum(), tf.getPrice().doubleValue());
                    	}else{
                    		addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue(), tf.getPrice().doubleValue());
                    	}
                    }
                    finalMoney = BigDecimalUtil.add(finalMoney, addmoney);
                    payMoney = BigDecimalUtil.add(payMoney, addmoney);
                    tcsbOrderItemArray.add(tcsbOrderItemEntity2);
                    payOrderItemArray.add(tcsbOrderItemEntity2);
                    if (tf.getIsDis().equals("N")) {
                        nodisMoney = BigDecimalUtil.add(nodisMoney, addmoney);
                    }
                }
                isMealFee = true;
            } else {
                noPayOrder = true;
                //List<TcsbOrderItemEntity> tcsbOrderItemEntity = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
                for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                    TcsbOrderItemEntity entity = new TcsbOrderItemEntity();
                    try {
                        MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderItemEntity2,entity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(entity.getRetreatNum()!=null){
                        entity.setCount(entity.getCount()-entity.getRetreatNum());
                        if(entity.getCount()==0||entity.getCount()<0){
                            continue;
                        }
                    }

                    TcsbFoodEntity tf = tcsbOrderService.get(TcsbFoodEntity.class, entity.getFoodId());
                    double addmoney = 0.0D;
                    if (StringUtil.isNotEmpty(entity.getStandardId())) {
                        TcsbFoodStandardEntity tfs = tcsbOrderService.get(TcsbFoodStandardEntity.class, entity.getStandardId());
                        addmoney = BigDecimalUtil.mul(entity.getCount().doubleValue(), tfs.getPrice().doubleValue());
                    } else {
                        addmoney = BigDecimalUtil.mul(entity.getCount().doubleValue(), tf.getPrice().doubleValue());
                    }
                    finalMoney = BigDecimalUtil.add(finalMoney, addmoney);
                    noPayMoney = BigDecimalUtil.add(noPayMoney, addmoney);
                    tcsbOrderItemArray.add(entity);
                    nopayOrderItemArray.add(entity);
                    if (tf.getIsDis().equals("N")) {
                        nodisMoney = BigDecimalUtil.add(nodisMoney, addmoney);
                    }
                }
                tcsbOrderEntity2.setTotalPrice(noPayMoney);
            }
        }
        
       
        
        oInfo.setTcsbOrderItemArray(tcsbOrderItemArray);
        //oInfo.setPayOrderItemArray(payOrderItemArray);
        oInfo.setNopayOrderItemArray(nopayOrderItemArray);
        oInfo.setNoPayOrder(noPayOrder);
        //=========================
        oInfo.setFinalMoney(BigDecimalUtil.numericRetentionDecimal(finalMoney, 2));

        oInfo.setNoPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
        oInfo.setPayMoney(BigDecimalUtil.numericRetentionDecimal(payMoney, 2));

      //查询是否有餐位费
        TcsbMealFeeEntity tcsbMealFeeEntity = tcsbMealFeeService.findUniqueByProperty(TcsbMealFeeEntity.class, "shopId", shopId);
        if (tcsbMealFeeEntity == null) {
            oInfo.setHaveMealFee(false);
            oInfo.setMealFee("0");
        } else {
        	//是否交过餐位费
        	oInfo.setHaveMealFee(true);
        	double mealFee = BigDecimalUtil.mul(tcsbOrderParentEntity.getPeople(), tcsbMealFeeEntity.getMealFee());
            oInfo.setMealFee(BigDecimalUtil.numericRetentionDecimal(mealFee, 2));
            finalMoney = BigDecimalUtil.add(finalMoney, mealFee);
            oInfo.setFinalMoney(BigDecimalUtil.numericRetentionDecimal(finalMoney, 2));
            if(!isMealFee){
            	//未交
            	noPayMoney = BigDecimalUtil.add(noPayMoney, mealFee);
            }else{
            	payMoney = BigDecimalUtil.add(payMoney, mealFee);
                oInfo.setPayMoney(BigDecimalUtil.numericRetentionDecimal(payMoney, 2));
            }
        }
        
        //
        double discountMoney = BigDecimalUtil.sub(finalMoney, nodisMoney);
        double universalCouponMoney = discountMoney;//用户总额减去(不参与)(活动)后的金额，用来判断是否满足优惠券满减
        if (!specialCoupon) {
            //获取优惠活动
            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbOrderService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", shopId);
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbOrderService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
                    tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                }
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total", "discount");
                    boolean hasCut = false;
                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                        //总额扣除不参与打折的菜品金额，判断是否满足条件
                        if (discountMoney >= tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
                            double needpay = BigDecimalUtil.sub(Double.valueOf(noPayMoney), Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                            if (needpay > 0) {
                                //discountMoney = BigDecimalUtil.sub(discountMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
                                oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
                            } else {
                                //discountMoney = 0.0;
                                oInfo.setNeedPayMoney("0");
                            }
                            universalCouponMoney = BigDecimalUtil.sub(universalCouponMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                            oInfo.setShopUserDiscountMsg(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + "");
                            hasCut = true;
                            break;
                        }
                    }
                    if (!hasCut) {
                        oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
                        oInfo.setShopUserDiscountMsg("0");
                        oInfo.setHasShopFullcut(false);
                    }
                } else {
                    oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
                    oInfo.setShopUserDiscountMsg("0");
                    oInfo.setHasShopFullcut(false);
                }
            } else {
                oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
                oInfo.setShopUserDiscountMsg("0");
                oInfo.setHasShopFullcut(false);
            }
        } else {
            oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
            oInfo.setShopUserDiscountMsg(BigDecimalUtil.numericRetentionDecimal(specialCouponPrice, 2));
            oInfo.setHasShopFullcut(false);
        }


        //TODO
        //用户优惠券
        if (useCoupon) {
            
       	 CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class);
          Calendar calSrc = null;
			try {
				calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          try {
				cq.ge("expiryDate", DateUtils.parseDate(DateUtils.date_sdf.format(calSrc.getTime()), "yyyy-MM-dd"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
          cq.eq("useStatus", "0");
          cq.eq("unionid", unionid);
          cq.add();
          List<TcsbCouponEntity> couponList = tcsbOrderService.getListByCriteriaQuery(cq, false);
          if(couponList.size()>0){
        	  List<TcsbPlatformFullcutTemplateEntity> tcsbPlatformFullcutTemplateList = new ArrayList<>();
  	        	for (TcsbCouponEntity coupon : couponList) {
  	        		TcsbPlatformFullcutTemplateEntity tcsbPlatformFullcutTemplate = tcsbOrderService.get(TcsbPlatformFullcutTemplateEntity.class, coupon.getFullcutTemplateId());
  	        		tcsbPlatformFullcutTemplateList.add(tcsbPlatformFullcutTemplate);
  	        	}
  	        	ListSorter.sort(tcsbPlatformFullcutTemplateList, "total", "discount");
  	        	for(int i = tcsbPlatformFullcutTemplateList.size() - 1; i >= 0; i--){
       			 if (universalCouponMoney >= tcsbPlatformFullcutTemplateList.get(i).getTotal()) {
                        double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(tcsbPlatformFullcutTemplateList.get(i).getDiscount() + ""));
                        if (needpay > 0) {
                            oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
                        } else {
                            oInfo.setNeedPayMoney("0");
                        }
                        oInfo.setCouponPrice(tcsbPlatformFullcutTemplateList.get(i).getDiscount() + "");
                        break;
                    }
       		 }
  	        	
          }else{
        	  oInfo.setHasFullCut(useCoupon);
        	  oInfo.setCouponPrice("0");
          }
        }        
        
        //VIP折扣
        //获取用户会员信息
        String hql = "from TcsbMemberUserEntity where openid='" + userId + "' and shopId='" + shopId + "'";
        TcsbMemberUserEntity tm = tcsbOrderService.singleResult(hql);
        if (StringUtil.isNotEmpty(tm)) {
            //获取会员详情
            //TcsbMembershipLevelEntity tmship = tcsbOrderService.get(TcsbMembershipLevelEntity.class, tm.getMembershipLevelId());
            TcsbMemberLevelEquityEntity tmle = tcsbOrderService.findUniqueByProperty(TcsbMemberLevelEquityEntity.class, "membershipLevelId", tm.getMembershipLevelId());
            if (tmle.getIsDiscount().equals("1")) {
                String discount = BigDecimalUtil.divide(tmle.getDiscount() + "", 10 + "", 2);
                if (discountMoney > 0) {
                    //

                    double needDisMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), nodisMoney);
                    double vipneedMoney = BigDecimalUtil.mul(needDisMoney, Double.valueOf(discount));
                    double vipdisMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), vipneedMoney);
                    oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(vipneedMoney, 2));
                    oInfo.setVipDisCount(BigDecimalUtil.numericRetentionDecimal(vipdisMoney, 2));
                } else {
                    oInfo.setVipDisCount("0");
                }
            } else {
                oInfo.setVipDisCount("0");
            }
        } else {
            oInfo.setVipDisCount("0");
        }
        
        oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), frontMoney), 2));

        String shareGoldCoin ="0";
        //获取用户分享金
        String sharehql = "from TcsbShopShareGoldCoinEntity where shopId='"+shopId+"' and unionid='"+unionid+"'";
        TcsbShopShareGoldCoinEntity tcsbShopShareGoldCoinEntity = tcsbOrderService.singleResult(sharehql);
        if(StringUtil.isNotEmpty(tcsbShopShareGoldCoinEntity)){
        	if(Double.valueOf(oInfo.getNeedPayMoney())>Double.valueOf(tcsbShopShareGoldCoinEntity.getTotal())){
        		shareGoldCoin = tcsbShopShareGoldCoinEntity.getTotal();
        		double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(tcsbShopShareGoldCoinEntity.getTotal()));
        		oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
        	}else{
        		shareGoldCoin = oInfo.getNeedPayMoney();
        		oInfo.setNeedPayMoney("0");
        	}
        }
        oInfo.setShareGoldCoin(shareGoldCoin);
        
        ajaxJson.setMsg("请求成功");
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(oInfo);
        return ajaxJson;
    }
    
    
    
    


    /**
     * 获取当前桌位是否存在未清台的订单
     *
     * @param deskId
     * @return
     */
    @RequestMapping("/getThisOrderId")
    @ResponseBody
    public AjaxJsonApi getThisOrderId(String deskId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        String hql = "from TcsbOrderParentEntity where deskId='" + deskId + "' and orderStatus='1'";
        TcsbOrderParentEntity top = tcsbOrderService.singleResult(hql);
        Map<String, Object> map = new HashMap<>();
        if (StringUtil.isNotEmpty(top)) {
            TcsbShopPaymodeEntity tp = tcsbOrderService.findUniqueByProperty(TcsbShopPaymodeEntity.class, "shopId", top.getShopId());
            //0无限制1线上付款2线下付款
            if (StringUtil.isNotEmpty(tp)) {
                if (tp.getPayMode().equals("0")) {
                    map.put("payMode", 0);

                }
                if (tp.getPayMode().equals("1")) {
                    map.put("payMode", 1);
                }
                if (tp.getPayMode().equals("2")) {
                    map.put("payMode", 2);
                }
            } else {
                map.put("payMode", 0);
            }
            ajaxJson.setSuccess(true);
            map.put("orderId", top.getId());
            ajaxJson.setObj(map);
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setObj(null);
        }


        ajaxJson.setMsg("请求成功");

        return ajaxJson;
    }


    /**
     * 检测隐藏下单字段
     *
     * @param deskId
     * @return
     */
    @RequestMapping("/checkHidden")
    @ResponseBody
    public AjaxJsonApi checkHidden(String deskId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Map<String, Object> map = new HashMap<>();
        String hql = "from TcsbOrderParentEntity where deskId='" + deskId + "' and orderStatus='1'";
        TcsbOrderParentEntity top = tcsbOrderService.singleResult(hql);
        if (StringUtil.isNotEmpty(top)) {
            map.put("people", top.getPeople());
            map.put("peopleClick", true);
        } else {
            map.put("people", 1);
            map.put("peopleClick", false);
        }

        String tcsbOrderupdatehql = "from TcsbOrderEntity where deskId='" + deskId + "' and payStatus=0";
        TcsbOrderEntity tcsbOrderupdate = tcsbOrderService.singleResult(tcsbOrderupdatehql);
        if (StringUtil.isNotEmpty(tcsbOrderupdate)) {
            map.put("other", true);
        } else {
            map.put("other", false);
        }

        ajaxJson.setSuccess(true);
        ajaxJson.setObj(map);
        ajaxJson.setMsg("请求成功");

        return ajaxJson;
    }


    /**
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletPay(String orderParentId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, orderParentId);
        tcsbOrderParentEntity.setOrderLock("0");
        tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
        String hql = "from TcsbOrderEntity where orderParentId='" + orderParentId + "' and payStatus='0'";
        TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.singleResult(hql);
        tcsbOrderEntity.setUniversalCouponPrice(0.0);
        tcsbOrderEntity.setPlatformDiscountPrice(0.0);
        tcsbOrderEntity.setSpecialCouponPrice(0.0);
        tcsbOrderEntity.setShareGoldCoin("0");
        tcsbOrderEntity.setUniversalCoupon(null);
        tcsbOrderEntity.setPlatformDiscount(null);
        tcsbOrderEntity.setPayMethod("1");
        tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }

    /**
     * 取消获取购物车支付金额生成的预订单号(预约模块中的全额付款取消)
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletReservationTotalGetMoneyShopCarPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletReservationTotalGetMoneyShopCarPay(String reservationId, String shopcarId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        //删除预约信息
        TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
        try {
            if ("0".equals(tcsbDeskReservationEntity.getPayStatus())) {
                tcsbDeskReservationService.delete(tcsbDeskReservationEntity);
            }
        } catch (Exception e) {
        }
        //删除购物车
        TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.get(TcsbShopCarEntity.class, shopcarId);
        try {
            if (StringUtil.isNotEmpty(tcsbShopCarEntity)) {
                tcsbShopCarService.delete(tcsbShopCarEntity);
                List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = tcsbShopCarService.findHql("from TcsbShopCarItemEntity where shopCar = ?", tcsbShopCarEntity.getId());
                if (!tcsbShopCarItemEntities.isEmpty()) {
                    for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                        tcsbShopCarService.delete(tcsbShopCarItemEntity);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }

    /**
     * 预约微信全额支付取消
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletReservationTotalShopCarPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletReservationTotalShopCarPay(String reservationId, String openId, String shopcarId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
		/*	//删除预定信息
			TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
			if (!"1".equals(tcsbDeskReservationEntity.getPayStatus())) {
				tcsbDeskReservationService.delete(tcsbDeskReservationEntity);
			}*/
            //删除虚拟购物车
            List<TcsbUserReservationShopCarEntity> tcsbUserReservationShopCarEntities = tcsbUserReservationShopCarService.findHql("from TcsbUserReservationShopCarEntity where shopcarId =? and userId = ?", shopcarId, openId);
            for (TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity : tcsbUserReservationShopCarEntities) {
                tcsbUserReservationShopCarService.delete(tcsbUserReservationShopCarEntity);
            }
            //删除预约信息
            TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
            tcsbDeskReservationService.delete(tcsbDeskReservationEntity);
            //删除购物车
            TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.get(TcsbShopCarEntity.class, shopcarId);
            try {
                if (StringUtil.isNotEmpty(tcsbShopCarEntity)) {
                    tcsbShopCarService.delete(tcsbShopCarEntity);
                    List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = tcsbShopCarService.findHql("from TcsbShopCarItemEntity where shopCar = ?", tcsbShopCarEntity.getId());
                    if (!tcsbShopCarItemEntities.isEmpty()) {
                        for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                            tcsbShopCarService.delete(tcsbShopCarItemEntity);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		
		/*TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderParentId);
		try {
			tcsbOrderParentService.delete(tcsbOrderParentEntity);
			//删除
			//创建虚拟购物车
			List<TcsbUserShopCarEntity> tcsbUserShopCarEntities = tcsbUserShopCarService.findHql("from TcsbUserShopCarEntity where shopcarId =? and openid = ?", shopcarId,openId);
			for (TcsbUserShopCarEntity tcsbUserShopCarEntity : tcsbUserShopCarEntities) {
				tcsbUserShopCarService.delete(tcsbUserShopCarEntity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        //
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }


    /**
     * 取消获取购物车支付金额生成的预订单号(预约模块中的押金付款取消)
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletReservationDepositGetMoneyShopCarPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletReservationDepositGetMoneyShopCarPay(String reservationId, String shopcarId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        //删除预约信息
        TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
        try {
            TcsbDepositDeltaEntity tcsbDepositDeltaEntity = tcsbDepositDeltaService.get(TcsbDepositDeltaEntity.class, tcsbDeskReservationEntity.getDepositDeltaId());
            //是否冲值成功
            if (tcsbDepositDeltaEntity.getStatus() == 0) {
                tcsbDeskReservationService.delete(tcsbDeskReservationEntity);
                tcsbDepositDeltaService.delete(tcsbDepositDeltaEntity);
            }

        } catch (Exception e) {
        }
        //删除购物车
        TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.get(TcsbShopCarEntity.class, shopcarId);
        try {
            if (StringUtil.isNotEmpty(tcsbShopCarEntity)) {
                tcsbShopCarService.delete(tcsbShopCarEntity);
                List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = tcsbShopCarService.findHql("from TcsbShopCarItemEntity where shopCar = ?", tcsbShopCarEntity.getId());
                if (!tcsbShopCarItemEntities.isEmpty()) {
                    for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                        tcsbShopCarService.delete(tcsbShopCarItemEntity);
                    }
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }

    /**
     * 预约微信押金支付取消
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletReservationDepositShopCarPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletReservationDepositShopCarPay(String reservationId, String openId, String shopcarId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        try {
		/*	//删除预定信息
			TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
			if (!"1".equals(tcsbDeskReservationEntity.getPayStatus())) {
				tcsbDeskReservationService.delete(tcsbDeskReservationEntity);
			}*/
            //删除虚拟购物车
            List<TcsbUserReservationShopCarEntity> tcsbUserReservationShopCarEntities = tcsbUserReservationShopCarService.findHql("from TcsbUserReservationShopCarEntity where shopcarId =? and userId = ?", shopcarId, openId);
            for (TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity : tcsbUserReservationShopCarEntities) {
                tcsbUserReservationShopCarService.delete(tcsbUserReservationShopCarEntity);
            }
            //删除预约信息
            TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
            try {
                TcsbDepositDeltaEntity tcsbDepositDeltaEntity = tcsbDepositDeltaService.get(TcsbDepositDeltaEntity.class, tcsbDeskReservationEntity.getDepositDeltaId());
                //是否冲值成功
                if (tcsbDepositDeltaEntity.getStatus() == 0) {
                    tcsbDeskReservationService.delete(tcsbDeskReservationEntity);
                    tcsbDepositDeltaService.delete(tcsbDepositDeltaEntity);
                }

            } catch (Exception e) {
            }
            //删除购物车
            TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.get(TcsbShopCarEntity.class, shopcarId);
            try {
                if (StringUtil.isNotEmpty(tcsbShopCarEntity)) {
                    tcsbShopCarService.delete(tcsbShopCarEntity);
                    List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = tcsbShopCarService.findHql("from TcsbShopCarItemEntity where shopCar = ?", tcsbShopCarEntity.getId());
                    if (!tcsbShopCarItemEntities.isEmpty()) {
                        for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                            tcsbShopCarService.delete(tcsbShopCarItemEntity);
                        }
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		
		
		/*TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderParentId);
		try {
			tcsbOrderParentService.delete(tcsbOrderParentEntity);
			//删除
			//创建虚拟购物车
			List<TcsbUserShopCarEntity> tcsbUserShopCarEntities = tcsbUserShopCarService.findHql("from TcsbUserShopCarEntity where shopcarId =? and openid = ?", shopcarId,openId);
			for (TcsbUserShopCarEntity tcsbUserShopCarEntity : tcsbUserShopCarEntities) {
				tcsbUserShopCarService.delete(tcsbUserShopCarEntity);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
        //
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }


    /**
     * 取消获取购物车支付金额生成的预订单号
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletGetMoneyShopCarPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletGetMoneyShopCarPay(String orderParentId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        //删除
        TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderParentId);
        //如果没有子单的情况在删除
        List<TcsbOrderEntity> tcsbOrderEntities = tcsbOrderService.findHql("from TcsbOrderEntity where orderParentId = ? ", tcsbOrderParentEntity.getId());
        try {
            if (!tcsbOrderEntities.isEmpty()) {
                if ("0".equals(tcsbOrderParentEntity.getFlag())) {
                    tcsbOrderParentService.delete(tcsbOrderParentEntity);
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }

    /**
     * 微信支付取消
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/cancleAppletShopCarPay")
    @ResponseBody
    public AjaxJsonApi cancleAppletShopCarPay(String orderParentId, String openId, String shopcarId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
		/*TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, orderParentId);
		tcsbOrderParentEntity.setOrderLock("0");
		tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
		String hql = "from TcsbOrderEntity where orderParentId='"+orderParentId+"' and payStatus='0'";
		TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.singleResult(hql);
		tcsbOrderEntity.setUniversalCouponPrice(0.0);
		tcsbOrderEntity.setPlatformDiscountPrice(0.0);
		tcsbOrderEntity.setSpecialCouponPrice(0.0);
		tcsbOrderService.saveOrUpdate(tcsbOrderEntity);*/
        //删除虚拟订单
        TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderParentId);
        try {
            tcsbOrderParentService.delete(tcsbOrderParentEntity);
            //删除
            //创建虚拟购物车
            List<TcsbUserShopCarEntity> tcsbUserShopCarEntities = tcsbUserShopCarService.findHql("from TcsbUserShopCarEntity where shopcarId =? and openid = ?", shopcarId, openId);
            for (TcsbUserShopCarEntity tcsbUserShopCarEntity : tcsbUserShopCarEntities) {
                tcsbUserShopCarService.delete(tcsbUserShopCarEntity);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //
        ajaxJson.setSuccess(true);
        ajaxJson.setObj(null);
        ajaxJson.setMsg("请求成功");
        return ajaxJson;
    }


    /**
     * 零元支付
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/zeroPay")
    @ResponseBody
    public AjaxJsonApi zeroPay(String orderParentId,String openid) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderParentService.get(TcsbOrderParentEntity.class, orderParentId);
        OrderInfoVo oInfo = appletService.getShopDeskOrder(orderParentId);
        boolean payState = true;
        String orderId = "";
        for (TcsbOrderEntity order : oInfo.getTcsbOrderEntity()) {
			if(order.getPayStatus().equals("0")){
				orderId = order.getId();
				break;
			}
		}
        
        for (TcsbOrderItemEntity orderItem : oInfo.getNopayOrderItemArray()) {
        	if (orderItem.getIsUpdatePrice() == 0) {
                payState = false;
                break;
            }
		}
        if(payState){
        	 if (tcsbOrderParentEntity.getOrderLock().equals("0")) {
        		 tcsbOrderParentEntity.setOrderLock("1");
                 tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
        		 
        			 TcsbOrderEntity tcsbOrderEntity = tcsbOrderParentService.get(TcsbOrderEntity.class, orderId);
        			 
        			//获取用户分享金
        			 TcsbShopShareGoldCoinEntity shareGoldCoin = appletService.getUserShareGoldCoin(openid, tcsbOrderParentEntity.getShopId());
         	    	if(StringUtil.isNotEmpty(shareGoldCoin)){
         	    		if(Double.valueOf(oInfo.getNeedPayMoney())>Double.valueOf(shareGoldCoin.getTotal())){
         	        		double needPay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(shareGoldCoin.getTotal()));
         	            	oInfo.setNeedPayMoney(needPay + "");
         	            	tcsbOrderEntity.setShareGoldCoin(shareGoldCoin.getTotal());
         	        	}else{
         	        		String shareGoldCoin2 = oInfo.getNeedPayMoney();
         	        		tcsbOrderEntity.setShareGoldCoin(shareGoldCoin2);
         	        		oInfo.setNeedPayMoney("0");
         	        	}
         	    	}
        			 
        			 
        		        double frontMoney = 0.0D;
        		    	if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getFrontMoney())) {
        		            if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getUseFrontMoney())) {
        		                if (tcsbOrderParentEntity.getUseFrontMoney().equals("1")) {
        		                    frontMoney = 0.0D;
        		                } else {
        		                    frontMoney = tcsbOrderParentEntity.getFrontMoney();
        		                }
        		            } else {
        		                frontMoney = tcsbOrderParentEntity.getFrontMoney();
        		            }
        		        }
        		    	
        		    	//检测该订单是否是要交餐位费
        		   	 	TcsbMealFeeEntity tcsbMealFeeEntity = tcsbOrderParentService.findUniqueByProperty(TcsbMealFeeEntity.class, "shopId", tcsbOrderParentEntity.getShopId());
        		        if (StringUtil.isNotEmpty(tcsbMealFeeEntity)) {
        		        	//是否交过餐位费
        		       	 	double mealFee = BigDecimalUtil.mul(tcsbOrderParentEntity.getPeople(), tcsbMealFeeEntity.getMealFee());
        		       	 	oInfo.setMealFee(BigDecimalUtil.numericRetentionDecimal(mealFee, 2));
        		       	 	if(oInfo.getPayOrderItemArray().size() < 1){
        		       	 		double needPayMoney = BigDecimalUtil.add(Double.valueOf(oInfo.getNeedPayMoney()), mealFee);
        		       	 		oInfo.setNeedPayMoney(needPayMoney + "");
        		       	 	}
        		        }
        		        
        		        double CheckdiscountPrice = BigDecimalUtil
        		       		 .sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(oInfo.getNoDiscountMoney())); 
        		        if(CheckdiscountPrice > 0){
        		       	//店铺活动
        		       	 //double shopDiscountPrice = appletService.isUseShopDiscount(oInfo.getTcsbOrderEntity());
        		            //if( shopDiscountPrice <= 0){
        		           	 List<TcsbShopFullcutTemplateEntity> tcsbFullcutTemplateEntity = appletService.getShopDiscountActivity(tcsbOrderParentEntity.getShopId());
        		           	 if(tcsbFullcutTemplateEntity.size()>0){
        		                    for (int i = tcsbFullcutTemplateEntity.size() - 1; i >= 0; i--) {
        		                        //总额扣除不参与打折的菜品金额，判断是否满足条件
        		                        if (CheckdiscountPrice >= tcsbFullcutTemplateEntity.get(i).getTotal()) {
        		                            double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(tcsbFullcutTemplateEntity.get(i).getDiscount()));
        		                            if (needpay > 0) {
        		                                oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
        		                            } else {
        		                                oInfo.setNeedPayMoney("0");
        		                            }
        		                            tcsbOrderEntity.setSpecialCouponPrice(Double.valueOf(tcsbFullcutTemplateEntity.get(i).getDiscount()));
        		                            CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, Double.parseDouble(tcsbFullcutTemplateEntity.get(i).getDiscount() + ""));
        		                            break;
        		                        }
        		                    }
        		           	 }
        		           }
        		            
        		            if(CheckdiscountPrice>0){
        		            	//优惠券
            		            //double couponPrice = appletService.isUseCoupon(oInfo.getTcsbOrderEntity());
            		            //if( couponPrice <= 0){
            		           	 List<CouponDto> couponDtoList = appletService.getUserCoupon(openid,tcsbOrderParentEntity.getShopId());
            		           	 if(couponDtoList.size()>0){
            		           		 boolean hasCut = false;
            		                    outer:
            		                    for (int i = couponDtoList.size() - 1; i >= 0; i--) {
            		                   	 if (CheckdiscountPrice >= couponDtoList.get(i).getTotal()) {
            		                   		 if(StringUtil.isNotEmpty(couponDtoList.get(i).getUseRange())){
            		                   			 if(couponDtoList.get(i).getUseRange().equals("0")){
            		                   				 double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(couponDtoList.get(i).getDiscount() + ""));
            		                                    if (needpay > 0) {
            		                                        oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
            		                                    } else {
            		                                        oInfo.setNeedPayMoney("0");
            		                                    }
            		                                    if(couponDtoList.get(i).getCouponType().equals("2")){
            		                                    	tcsbOrderEntity.setPlatformDiscount(couponDtoList.get(i).getCouponId());
            		                                    	tcsbOrderEntity.setPlatformDiscountPrice(Double.valueOf(couponDtoList.get(i).getDiscount()));
            		                                    }else{
            		                                    	tcsbOrderEntity.setUniversalCoupon(couponDtoList.get(i).getCouponId());
            		                                    	tcsbOrderEntity.setUniversalCouponPrice(Double.valueOf(couponDtoList.get(i).getDiscount()));
            		                                    }
            		                                    CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponDtoList.get(i).getDiscount());
            		                                    break outer;
            		                       		 }else{
            		                       			//查找订单项中是否有该道菜
            		                                    for (int j = 0; j < oInfo.getNopayOrderItemArray().size(); j++) {
            		                                        if (couponDtoList.get(i).getFoodId().equals(oInfo.getNopayOrderItemArray().get(j).getFoodId())) {
            		                                            double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(couponDtoList.get(i).getDiscount() + ""));
            		                                            if (needpay > 0) {
            		                                                oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
            		                                            } else {
            		                                                oInfo.setNeedPayMoney("0");
            		                                            }
            		                                            if(couponDtoList.get(i).getCouponType().equals("2")){
            		                                            	tcsbOrderEntity.setPlatformDiscount(couponDtoList.get(i).getCouponId());
            		                                            	tcsbOrderEntity.setPlatformDiscountPrice(Double.valueOf(couponDtoList.get(i).getDiscount()));
            		                                            }else{
            		                                            	tcsbOrderEntity.setUniversalCoupon(couponDtoList.get(i).getCouponId());
            		                                            	tcsbOrderEntity.setUniversalCouponPrice(Double.valueOf(couponDtoList.get(i).getDiscount()));
            		                                            }
            		                                            CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponDtoList.get(i).getDiscount());
            		                                            break outer;
            		                                        }
            		                                    } 
            		                       		 }
            		                   		 }else{
            		                   			 double needpay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.parseDouble(couponDtoList.get(i).getDiscount() + ""));
            		                                if (needpay > 0) {
            		                                    oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay, 2));
            		                                } else {
            		                                    oInfo.setNeedPayMoney("0");
            		                                }
            		                                if(couponDtoList.get(i).getCouponType().equals("2")){
            		                                	tcsbOrderEntity.setPlatformDiscount(couponDtoList.get(i).getCouponId());
            		                                	tcsbOrderEntity.setPlatformDiscountPrice(Double.valueOf(couponDtoList.get(i).getDiscount()));
            		                                }else{
            		                                	tcsbOrderEntity.setUniversalCoupon(couponDtoList.get(i).getCouponId());
            		                                	tcsbOrderEntity.setUniversalCouponPrice(Double.valueOf(couponDtoList.get(i).getDiscount()));
            		                                }
            		                                CheckdiscountPrice = BigDecimalUtil.sub(CheckdiscountPrice, couponDtoList.get(i).getDiscount());
            		                                break outer;
            		                   		 } 
            		                   	 }
            		                    }
            		           	 }
        		            }
        		            
        		        //}
        		        
        		        
        		      //检测会员折扣
        		        //VIP折扣
        		        //获取用户会员信息
        		        if(Double.valueOf(oInfo.getNeedPayMoney())>0){
        		        	String hql = "from TcsbMemberUserEntity where openid='" + openid + "' and shopId='" + tcsbOrderParentEntity.getShopId() + "'";
        		            TcsbMemberUserEntity tm = tcsbOrderService.singleResult(hql);
        		            if (StringUtil.isNotEmpty(tm)) {
        		                //获取会员详情
        		                //TcsbMembershipLevelEntity tmship = tcsbOrderService.get(TcsbMembershipLevelEntity.class, tm.getMembershipLevelId());
        		                TcsbMemberLevelEquityEntity tmle = tcsbOrderService.findUniqueByProperty(TcsbMemberLevelEquityEntity.class, "membershipLevelId", tm.getMembershipLevelId());
        		                if (tmle.getIsDiscount().equals("1")) {
        		                    String discount = BigDecimalUtil.divide(tmle.getDiscount() + "", 10 + "", 2);
        		                    if (CheckdiscountPrice > 0) {
        		                        //
        		                        double needDisMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(oInfo.getNoDiscountMoney()));
        		                        if(needDisMoney > 0){
        		                       	 double vipneedMoney = BigDecimalUtil.mul(needDisMoney, Double.valueOf(discount));
        		                            double vipdisMoney = BigDecimalUtil.sub(needDisMoney, vipneedMoney);
        		                            double needPayMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), vipdisMoney);
        		                            oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needPayMoney, 2));
        		                            tcsbOrderEntity.setVipDiscountPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(vipdisMoney, 2)));
        		                        }
        		                    }
        		                }
        		            }
        		        }
        		        double payMoney = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), frontMoney);
        		        System.out.println("=========================================");
        		        System.out.println(payMoney);
        		        System.out.println("=========================================");
        		        if(payMoney<0.01){
        		        	tcsbOrderEntity.setPayStatus("1");
        		        	tcsbOrderEntity.setOnlinePrice(0.0);
        		        	tcsbOrderEntity.setMethod("0");
        		        	
        		        	//判断用户是否有使用分享金
                            if(StringUtil.isNotEmpty(tcsbOrderEntity.getShareGoldCoin())){
                            	if(Double.valueOf(tcsbOrderEntity.getShareGoldCoin())>0){
                            		//TcsbShopShareGoldCoinEntity shareGoldCoin = appletService.getUserShareGoldCoin(map.get("openid"), tcsbOrderEntity.getShopId());
                        			if(StringUtil.isNotEmpty(shareGoldCoin)){
                        				double settleShareGoldCoin = BigDecimalUtil.sub(Double.valueOf(shareGoldCoin.getTotal()), Double.valueOf(tcsbOrderEntity.getShareGoldCoin()));
                        				shareGoldCoin.setTotal(BigDecimalUtil.numericRetentionDecimal(settleShareGoldCoin, 2));
                        				tcsbOrderService.saveOrUpdate(shareGoldCoin);
                        				TcsbShopShareGoldCoinRecordEntity record = new TcsbShopShareGoldCoinRecordEntity();
                        				record.setCreateDate(new Date());
                        				record.setDescribe("-"+tcsbOrderEntity.getShareGoldCoin());
                        				record.setShopId(tcsbOrderEntity.getShopId());
                        				record.setUnionid(shareGoldCoin.getUnionid());
                        				tcsbOrderService.save(record);
                        			}
                            	}
                            }
        		        	
        		        	
        		        	 if (tcsbOrderEntity.getUniversalCouponPrice() > 0) {
                                 //onlinePrice = BigDecimalUtil.sub(onlinePrice, tcsbOrderEntity.getUniversalCouponPrice());
                                 TcsbCouponEntity tcpon = tcsbOrderService.get(TcsbCouponEntity.class, tcsbOrderEntity.getUniversalCoupon());
                                 tcpon.setUseStatus("1");
                                 tcsbOrderService.saveOrUpdate(tcpon);
                             }

                             if (tcsbOrderEntity.getPlatformDiscountPrice() > 0) {
                                 //onlinePrice = BigDecimalUtil.sub(onlinePrice, tcsbOrderEntity.getPlatformDiscountPrice());
                             	TcsbCouponEntity tpcpon = tcsbOrderService.get(TcsbCouponEntity.class, tcsbOrderEntity.getPlatformDiscount());
                                 tpcpon.setUseStatus("1");
                                 tcsbOrderService.saveOrUpdate(tpcpon);
                             }
                             tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                             tcsbOrderParentEntity.setOrderLock("0");
                             tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
                             ajaxJson.setMsg("支付成功");
                             ajaxJson.setSuccess(true);
                             ajaxJson.setObj(null);
        		        }
        		
        }
       }else{
    	   ajaxJson.setMsg("支付失败");
           ajaxJson.setSuccess(false);
           ajaxJson.setObj(null);
       }

        return ajaxJson;
    }

    /**
     * 检测是否有时价商品未更新
     *
     * @param orderParentId
     * @return
     */
    @RequestMapping("/checkPayState")
    @ResponseBody
    public AjaxJsonApi checkPayState(String orderParentId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        ajaxJson.setSuccess(true);
        //TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, orderParentId);
        List<TcsbOrderEntity> TcsbOrder = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderParentId", orderParentId);
        out:
        for (TcsbOrderEntity tcsbOrderEntity2 : TcsbOrder) {
            if (tcsbOrderEntity2.getPayStatus().equals("0")) {
                List<TcsbOrderItemEntity> tcsbOrderItemEntity = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
                for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                    if (tcsbOrderItemEntity2.getIsUpdatePrice() == 0) {
                        ajaxJson.setMsg("存在未接单时价商品");
                        ajaxJson.setSuccess(false);
                        break out;
                    }
                }
            } else {
                continue;
            }
        }

        return ajaxJson;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * 生成订单并返回订单Id
     *
     * @return
     */
    @Deprecated
    @RequestMapping("/saveOrderReturnOrderId")
    @ResponseBody
    public AjaxJsonApi appletSaveOrderReturnOrderId(AppletRequestParamVo param) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        OrderReturnVo oreturn = new OrderReturnVo();
        try {
            TcsbOrderParentEntity orderParent = appletService.getOrderParent(param.getDeskId());
            boolean add = false;
            String orderparentId = "";
            if (StringUtil.isNotEmpty(orderParent)) {
                if (orderParent.getOrderLock().equals("0")) {
                    oreturn.setOrderId(orderParent.getId());
                    //存在正在使用的桌位订单
                    //添加子订单
                    TcsbOrderEntity order = appletService.getOrder(param.getDeskId());
                    if (StringUtil.isNotEmpty(order)) {
                        //存在未支付的子订单
                        //创建接单信息
                        TcsbSubOrderEntity subOrder = appletService.createSubOrderReturnSubId(param, order.getId());
                        //创建子订单项-创建接单子项
                        add = appletService.addOrderItemAndSubOrder(subOrder.getId(), order);
                    } else {
                        //不存在未支付的子订单
                        //创建子订单信息
                        TcsbOrderEntity newOrder = appletService.createOrder(param, orderParent.getId());
                        //添加接单项
                        TcsbSubOrderEntity subOrder = appletService.createSubOrderReturnSubId(param, newOrder.getId());
                        //创建子订单项-创建接单子项
                        add = appletService.addOrderItemAndSubOrder(subOrder.getId(), newOrder);
                    }
                }
            } else {
                //不存在使用中的订单
                TcsbOrderParentEntity newOrderParent = appletService.createOrderParent(param);
                oreturn.setOrderId(newOrderParent.getId());
                //创建子订单信息
                TcsbOrderEntity newOrder = appletService.createOrder(param, newOrderParent.getId());
                //添加接单项
                TcsbSubOrderEntity subOrder = appletService.createSubOrderReturnSubId(param, newOrder.getId());
                //创建子订单项-创建接单子项
                add = appletService.addOrderItemAndSubOrder(subOrder.getId(), newOrder);
            }
            ////0空闲1预定2未下单3用餐中
            if (add) {
                TcsbDeskEntity desk = tcsbOrderService.get(TcsbDeskEntity.class, param.getDeskId());
                desk.setStatus("3");
                tcsbOrderService.saveOrUpdate(desk);

                oreturn.setPaymode(appletService.getShopPaymode(param.getShopId()));
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("下单成功");
                ajaxJson.setObj(oreturn);
            } else {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("下单失败");
                ajaxJson.setObj(null);
            }
        } catch (Exception e) {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("下单失败:系统繁忙");
            ajaxJson.setObj(null);
        }
        return ajaxJson;
    }
    
    
    
    
    

}
