/**
 * ddmShopV2
 *
 * @author Mar_x
 * create on 2017 2017年11月4日 上午10:18:01
 */
package com.applet.appletpay;

import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.util.JSONPObject;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.*;
import org.jeecgframework.p3.core.common.utils.RandomUtils;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.applet.shopcar.TcsbShopCarItemVo;
import com.applet.shoporder.service.AppletShopOrderServiceI;
import com.applet.shoporder.vo.AppletRequestParamVo;
import com.applet.shoporder.vo.AppletReservationRequestParamVo;
import com.applet.shoporder.vo.CouponDto;
import com.applet.shoporder.vo.OrderInfoVo;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.deltaquota.entity.TcsbDeltaQuotaEntity;
import com.tcsb.deltaquota.service.TcsbDeltaQuotaServiceI;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.depositdelta.service.TcsbDepositDeltaServiceI;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.desk.service.TcsbDeskServiceI;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.foodstandard.service.TcsbFoodStandardServiceI;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.mealfee.entity.TcsbMealFeeEntity;
import com.tcsb.memberlevelconditions.entity.TcsbMemberLevelConditionsEntity;
import com.tcsb.memberlevelconditions.service.TcsbMemberLevelConditionsServiceI;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.memberlevelequity.service.impl.TcsbMemberLevelEquityServiceImpl;
import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import com.tcsb.membershiplevel.service.TcsbMembershipLevelServiceI;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import com.tcsb.memberuser.service.TcsbMemberUserServiceI;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopcaritem.service.TcsbShopCarItemServiceI;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopsharecontent.entity.TcsbShopShareContentEntity;
import com.tcsb.shopsharegoldcoin.entity.TcsbShopShareGoldCoinEntity;
import com.tcsb.shopsharegoldcoinrecord.entity.TcsbShopShareGoldCoinRecordEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbdeskreservation.service.TcsbDeskReservationServiceI;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsbfoodunit.service.TcsbFoodUnitServiceI;
import com.tcsb.tcsborderbigdatarecord.entity.TcsbOrderBigdataRecordEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsbpaynotice.entity.TcsbPayNoticeEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.usercar.entity.TcsbUserCarEntity;
import com.tcsb.usercar.service.TcsbUserCarServiceI;
import com.tcsb.userdelta.entity.TcsbUserDeltaEntity;
import com.tcsb.userdelta.service.TcsbUserDeltaServiceI;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;
import com.tcsb.userreservationshopcar.entity.TcsbUserReservationShopCarEntity;
import com.tcsb.usershopcar.entity.TcsbUserShopCarEntity;
import com.weixin.core.util.ConfigUtil;
import com.weixin.core.util.JsonResult;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.WxPayUtil;
import com.weixin.core.util.XMLUtil;

/**
 * ddmShopV2
 *
 * @author Mar_x create on 2017年11月4日 上午10:18:01
 */
@Controller
@RequestMapping("/appletPay")
public class AppletPayController {

    @Autowired
    private TcsbOrderServiceI tcsbOrderService;
    @Autowired
    private TcsbDeltaQuotaServiceI tcsbDeltaQuotaService;
    @Autowired
    private TcsbUserDeltaServiceI tcsbUserDeltaService;
    @Autowired
    private TcsbMemberUserServiceI tcsbMemberUserService;
    @Autowired
    private TcsbMembershipLevelServiceI tcsbMembershipLevelService;
    @Autowired
    private TcsbMemberLevelConditionsServiceI tcsbMemberLevelConditionsService;
    @Autowired
    private TcsbShopServiceI tcsbShopService;
    @Autowired
    private TcsbDepositDeltaServiceI tcsbDepositDeltaService;
    @Autowired
    private TcsbShopCarServiceI tcsbShopCarService;
    @Autowired
    private TcsbShopCarItemServiceI tcsbShopCarItemService;
    @Autowired
    private TcsbFoodStandardServiceI tcsbFoodStandardService;
    @Autowired
    private SystemService systemService;
    @Autowired
    private TcsbUserCarServiceI tcsbUserCarService;
    @Autowired
    private TcsbDeskServiceI tcsbDeskService;
    @Autowired
    private TcsbFoodServiceI tcsbFoodService;
    @Autowired
    private TcsbFoodUnitServiceI tcsbFoodUnitService;
    @Autowired
    private TcsbDeskReservationServiceI tcsbDeskReservationService;
    
    @Autowired
    private AppletShopOrderServiceI appletService;

    /**
     * couponsConfirm(调起微信支付) 扫码下单
     *
     * @param request
     * @param totalFee //支付金额
     * @param body     //支付描述
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @RequestMapping(params = "couponsConfirm")
    @ResponseBody
    public AjaxJson couponsConfirm(HttpServletRequest request,@RequestParam("openid") String openid,
               @RequestParam("orderParentId") String orderParentId)throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();
        TcsbOrderParentEntity tcsbOrderParentEntity = systemService.get(TcsbOrderParentEntity.class, orderParentId);
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
        		 try {
        			 TcsbOrderEntity tcsbOrderEntity = systemService.get(TcsbOrderEntity.class, orderId);
        			 
        			//获取用户分享金
        			 TcsbShopShareGoldCoinEntity shareGoldCoin = appletService.getUserShareGoldCoin(openid, tcsbOrderParentEntity.getShopId());
         	    	if(StringUtil.isNotEmpty(shareGoldCoin)){
         	    		if(Double.valueOf(oInfo.getNeedPayMoney())>Double.valueOf(shareGoldCoin.getTotal())){
         	        		double needPay = BigDecimalUtil.sub(Double.valueOf(oInfo.getNeedPayMoney()), Double.valueOf(shareGoldCoin.getTotal()));
         	            	oInfo.setNeedPayMoney(needPay + "");
         	            	tcsbOrderEntity.setShareGoldCoin(shareGoldCoin.getTotal());
         	            	//oInfo.setShareGoldCoin(BigDecimalUtil.numericRetentionDecimal(shareGoldCoin, 2));
         	        	}else{
         	        		String shareGoldCoin2 = oInfo.getNeedPayMoney();
         	        		tcsbOrderEntity.setShareGoldCoin(shareGoldCoin2);
         	        		//oInfo.setShareGoldCoin(shareGoldCoin2);
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
        		   	 	TcsbMealFeeEntity tcsbMealFeeEntity = systemService.findUniqueByProperty(TcsbMealFeeEntity.class, "shopId", tcsbOrderParentEntity.getShopId());
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
        		        String money = "";
        		        if(payMoney>0){
        		        	money = BigDecimalUtil.numericRetentionDecimal(payMoney, 2);
        		        }else{
        		        	money = "0.01";
        		        }
        		        
        		        //oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needMoney, 2));
        		        
        		        
        		        String body = "点单么平台";
        		        
        		        //String money = payMoney + "";// 获取订单金额
        		        // 保留两位小数位
        		        //money = BigDecimalUtil.numericRetentionDecimal(money, 2);
        		        // 金额转化为分为单位
        		        float sessionmoney = Float.parseFloat(money);
        		        String finalmoney = String.format("%.2f", sessionmoney);
        		        finalmoney = finalmoney.replace(".", "");
        		        // 商户订单号
        		        String out_trade_no = OrderNumberGenerateUtil.getOrderNumber() + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。
        		        tcsbOrderEntity.setOrderNo(out_trade_no);
        		        tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
        		        int intMoney = Integer.parseInt(finalmoney);
        		        // 总金额以分为单位，不带小数点
        		        String totalFee = String.valueOf(intMoney);
        		        String ipAddress = request.getRemoteAddr();
        		        String prepayId;
        		        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
        		        try {
        		             prepayId = WxPayUtil.getAppletPrepayId(totalFee,ipAddress, out_trade_no, body, openid);
        		             // 2 给H5页面传递参数 调起微信支付
        		             signParams = WxPayUtil.createAppletPackageValue(prepayId);
        		        } catch (Exception e) {
        		                 e.printStackTrace();
        		        }
        		        ajaxJson.setMsg("操作成功");
        		        ajaxJson.setObj(signParams);
        		        ajaxJson.setSuccess(true);
				} catch (Exception e) {
					tcsbOrderParentEntity.setOrderLock("0");
	                tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
					ajaxJson.setMsg("系统繁忙");
		            ajaxJson.setObj(null);
		            ajaxJson.setSuccess(false);
				} 
        	 }else{
        		 	ajaxJson.setMsg("订单正在支付中");
		            ajaxJson.setObj(null);
		            ajaxJson.setSuccess(false);
        	 }
        }else{
        	ajaxJson.setMsg("存在未接单时价商品");
            ajaxJson.setObj(null);
            ajaxJson.setSuccess(false);
        }
        
        
                   
        return ajaxJson;
    }
    
    
    
    /**
     * 微信支付异步回调
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay
     * .action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "payNotify")
    @ResponseBody
    public JsonResult payNotifyUrl(HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        //total_fee
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {
            String orderNumber = (String) map.get("out_trade_no");
           
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null&& orderMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                        if (orderMap.get("trade_state") != null&& orderMap.get("trade_state").equalsIgnoreCase("SUCCESS")) {
                            System.out.println("openid ======="+ orderMap.get("openid"));
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
                            String onlinePrice = BigDecimalUtil.divide((String) map.get("total_fee"), "100", 2);
                            TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo",orderNumber);
                            if(StringUtil.isNotEmpty(tcsbOrderEntity)){
                            	if (tcsbOrderEntity.getPayStatus().equals("1")) {
                                    return JsonResult.ok();
                                } else {
                                    tcsbOrderEntity.setPayStatus("1");
                                    tcsbOrderEntity.setPayMethod("0");
                                    tcsbOrderEntity.setUserId(map.get("openid"));
                                    tcsbOrderEntity.setUpdateDate(new Date());
                                    tcsbOrderEntity.setPayTime(new Date());
                                    //double onlinePrice = tcsbOrderEntity.getTotalPrice();
                                    //判断用户是否有使用分享金
                                    if(StringUtil.isNotEmpty(tcsbOrderEntity.getShareGoldCoin())){
                                    	if(Double.valueOf(tcsbOrderEntity.getShareGoldCoin())>0){
                                    		TcsbShopShareGoldCoinEntity shareGoldCoin = appletService.getUserShareGoldCoin(map.get("openid"), tcsbOrderEntity.getShopId());
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
                                        if(tcpon.getCouponType().equals("1")){
                                        	System.out.println("payNotify=====tcpon.getCouponType().equals(1)=======");
                                        	if(StringUtil.isNotEmpty(tcpon.getShareUnionid())){
                                        		if(!tcpon.getShareUnionid().equals("admin")){
                                        			System.out.println("payNotify=====admin=======");
                                        			String hql = "from TcsbShopShareGoldCoinEntity where shopId='"+tcsbOrderEntity.getShopId()+"' and unionid='"+tcpon.getShareUnionid()+"'";
                                        			TcsbShopShareGoldCoinEntity shareGold = tcsbOrderService.singleResult(hql);
                                        			TcsbShopShareContentEntity tcsbShopShareContentEntity = tcsbOrderService.findUniqueByProperty(TcsbShopShareContentEntity.class, "shopId", tcsbOrderEntity.getShopId());
                                        			if(StringUtil.isNotEmpty(tcsbShopShareContentEntity)){
                                        				if(StringUtil.isNotEmpty(tcsbShopShareContentEntity.getShareProportion())){
                                        					try {
                                        						double shareProportion = Double.valueOf(BigDecimalUtil.divide(tcsbShopShareContentEntity.getShareProportion(), "100", 2));
                                        						if(StringUtil.isNotEmpty(shareGold)){
                                                    				double coin = BigDecimalUtil.mul(Double.valueOf(onlinePrice), shareProportion);
                                                    				double totalcoin = BigDecimalUtil.add(Double.valueOf(shareGold.getTotal()), coin);
                                                    				shareGold.setUpdateDate(new Date());
                                                    				shareGold.setTotal(BigDecimalUtil.numericRetentionDecimal(totalcoin, 2));
                                                    				tcsbOrderService.saveOrUpdate(shareGold);
                                                    				
                                                    				TcsbShopShareGoldCoinRecordEntity record = new TcsbShopShareGoldCoinRecordEntity();
                                                    				record.setCreateDate(new Date());
                                                    				record.setDescribe("+"+coin);
                                                    				record.setShopId(tcsbOrderEntity.getShopId());
                                                    				record.setUnionid(tcpon.getShareUnionid());
                                                    				tcsbOrderService.save(record);
                                                    			}else{
                                                    				double coin = BigDecimalUtil.mul(Double.valueOf(onlinePrice), shareProportion);
                                                    				TcsbShopShareGoldCoinEntity sharecoin = new TcsbShopShareGoldCoinEntity();
                                                    				sharecoin.setCreateDate(new Date());
                                                    				sharecoin.setShopId(tcsbOrderEntity.getShopId());
                                                    				sharecoin.setTotal(BigDecimalUtil.numericRetentionDecimal(coin,2));
                                                    				sharecoin.setUnionid(tcpon.getShareUnionid());
                                                    				tcsbOrderService.save(sharecoin);
                                                    				TcsbShopShareGoldCoinRecordEntity record = new TcsbShopShareGoldCoinRecordEntity();
                                                    				record.setCreateDate(new Date());
                                                    				String describe = "+" + coin + "";
                                                    				record.setDescribe(describe);
                                                    				record.setShopId(tcsbOrderEntity.getShopId());
                                                    				record.setUnionid(tcpon.getShareUnionid());
                                                    				tcsbOrderService.save(record);
                                                    			}
															} catch (Exception e) {
																// TODO: handle exception
															}
                                        				}
                                        			}
                                        			
                                        		}
                                        	}
                                        }
                                    }

                                    if (tcsbOrderEntity.getPlatformDiscountPrice() > 0) {
                                        //onlinePrice = BigDecimalUtil.sub(onlinePrice, tcsbOrderEntity.getPlatformDiscountPrice());
                                    	TcsbCouponEntity tpcpon = tcsbOrderService.get(TcsbCouponEntity.class, tcsbOrderEntity.getPlatformDiscount());
                                        tpcpon.setUseStatus("1");
                                        tcsbOrderService.saveOrUpdate(tpcpon);
                                    }

                                    
                                    tcsbOrderEntity.setOnlinePrice(Double.valueOf(onlinePrice));
                                    tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                                    // 解除锁定
                                    TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class,
                                                    tcsbOrderEntity.getOrderParentId());
                                    tcsbOrderParentEntity.setOrderLock("0");
                                    tcsbOrderParentEntity.setUseFrontMoney("1");
                                    tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);

                                    try {
                                        TcsbDeskEntity tcsbDesk = tcsbOrderService.get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
                                        tcsbDesk.setStatus("2");
                                        tcsbOrderService.saveOrUpdate(tcsbDesk);
                                    } catch (Exception e) {
                                        // TODO: handle exception
                                    }
                                    return JsonResult.ok();
                                }
                            }
                            
                        }
                    }
                } else {
                    // 解除锁定
                    TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.findUniqueByProperty(TcsbOrderEntity.class,"orderNo", orderNumber);
                    tcsbOrderEntity.setUniversalCouponPrice(0.0);
                    tcsbOrderEntity.setPlatformDiscountPrice(0.0);
                    tcsbOrderEntity.setSpecialCouponPrice(0.0);
                    tcsbOrderEntity.setVipDiscountPrice(0.0);
                    tcsbOrderEntity.setPayMethod("0");
                    tcsbOrderEntity.setShareGoldCoin("0");
                    tcsbOrderEntity.setUniversalCoupon(null);
                    tcsbOrderEntity.setPlatformDiscount(null);
                    tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                    TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class,tcsbOrderEntity.getOrderParentId());
                    tcsbOrderParentEntity.setOrderLock("0");
                    tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
                    return JsonResult.fail("");
                }
            }
        }
        // 解除锁定
        return JsonResult.ok();
    }
    
    
    
    

    /**
     * couponsConfirm(调起微信支付)扫描餐前付款
     *
     * @param request
     * @param totalFee //支付金额
     * @param body     //支付描述
     * @return
     * @throws ParseException
     * @throws Exception      扫描餐前付款
     */
    @RequestMapping(params = "couponsShopCarConfirm")
    @ResponseBody
    public AjaxJson couponsShopCarConfirm(HttpServletRequest request,
                                          AppletRequestParamVo param) throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();
        TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService
                .findUniqueByProperty(TcsbShopCarEntity.class, "deskId",
                        param.getDeskId());
        // 锁定购物车
        tcsbShopCarEntity.setIsLock(1);
        try {
            tcsbShopCarService.saveOrUpdate(tcsbShopCarEntity);
            // 获取购物车列表
            List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = tcsbShopCarItemService
                    .findHql("from TcsbShopCarItemEntity where shopCar =? ",
                            tcsbShopCarEntity.getId());
            Double price = 0.0;
            for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                Integer num = tcsbShopCarItemEntity.getCount();
                Double money = 0.0;
                if (StringUtil
                        .isNotEmpty(tcsbShopCarItemEntity.getStandardId())) {
                    // 根据规格ID获取实际的价格
                    TcsbFoodStandardEntity tcsbFoodStandardEntity = tcsbFoodStandardService
                            .get(TcsbFoodStandardEntity.class,
                                    tcsbShopCarItemEntity.getStandardId());
                    money = BigDecimalUtil.mul(
                            tcsbFoodStandardEntity.getPrice(), num);
                } else {
                    TcsbFoodEntity tcsbFoodEntity = systemService.get(
                            TcsbFoodEntity.class,
                            tcsbShopCarItemEntity.getFoodId());
                    money = BigDecimalUtil.mul(tcsbFoodEntity.getPrice(), num);
                }
                price = BigDecimalUtil.add(price, money);
            }
            Double payMoney = 0.0;
            payMoney = price;
            // 打折情况
            // 获取优惠活动
            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService
                    .findByProperty(TcsbDiscountActivityEntity.class, "shopId",
                            param.getShopId());
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                            .get(TcsbShopFullcutTemplateEntity.class,
                                    tcsbDiscountActivityEntity
                                            .getFullcutTemplateId());
                    tcsbShopFullcutTemplateEntities
                            .add(tcsbFullcutTemplateEntity);
                }
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total",
                            "discount");
                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                        if (payMoney >= tcsbShopFullcutTemplateEntities.get(i)
                                .getTotal()) {
                            payMoney = BigDecimalUtil
                                    .sub(payMoney,
                                            Double.parseDouble(tcsbShopFullcutTemplateEntities
                                                    .get(i).getDiscount() + ""));
                            if (payMoney < 0) {
                                payMoney = 0.01;
                            }
                            break;
                        }
                    }
                }
            }
            // 获取优惠券信息
            List<TcsbCouponEntity> tcsbCouponEntities = systemService
                    .findHql(
                            "from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?",
                            param.getShopId(), param.getOpenId());
            List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
            if (!tcsbCouponEntities.isEmpty()) {
                for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                    TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                            .get(TcsbFullcutTemplateEntity.class,
                                    tcsbCouponEntity.getFullcutTemplateId());
                    Integer userPeriod = tcsbFullcutTemplateEntity
                            .getUsePeriod();// 使用期限
                    String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); // 日期单位
                    // 专用券中有效的满减活动
                    Calendar calDes = null;
                    try {
                        calDes = DateUtils.parseCalendar(DateUtils.date2Str(
                                tcsbCouponEntity.getExpiryDate(),
                                DateUtils.datetimeFormat),
                                "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calSrc = null;
                    try {
                        calSrc = DateUtils.parseCalendar(DateUtils.date2Str(
                                new Date(), DateUtils.datetimeFormat),
                                "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                        tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity
                                .getId());
                        tcsbFullcutTemplateEntities
                                .add(tcsbFullcutTemplateEntity);
                    }
                    ;
                }
            }
            // 立减
            if (!tcsbFullcutTemplateEntities.isEmpty()) {
                ListSorter.sort(tcsbFullcutTemplateEntities, "total",
                        "discount");
                outer:
                for (int i = tcsbFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                    TcsbCouponEntity tcsbCouponEntity = systemService.get(
                            TcsbCouponEntity.class, tcsbFullcutTemplateEntities
                                    .get(i).getCouponId());
                    if (tcsbCouponEntity.getUseRange().equals("0")) {
                        if (payMoney >= tcsbFullcutTemplateEntities.get(i)
                                .getTotal()) {
                            payMoney = BigDecimalUtil.sub(payMoney, Double
                                    .parseDouble(tcsbFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            if (payMoney < 0) {
                                payMoney = 0.01;
                            }
                            break outer;
                        }
                    } else {
                        // 查找订单项中是否有该道菜
                        TcsbFoodEntity tcsbFoodEntity = systemService.get(
                                TcsbFoodEntity.class,
                                tcsbCouponEntity.getFoodId());
                        for (int j = 0; j < tcsbShopCarItemEntities.size(); j++) {
                            if (tcsbFoodEntity.getId().equals(
                                    tcsbShopCarItemEntities.get(j).getFoodId())) {
                                payMoney = BigDecimalUtil
                                        .sub(payMoney,
                                                Double.parseDouble(tcsbFullcutTemplateEntities
                                                        .get(i).getDiscount()
                                                        + ""));
                                if (payMoney < 0) {
                                    payMoney = 0.01;
                                }
                                break outer;
                            }
                        }
                    }
                }

            }
            // 创建虚拟购物车
            List<TcsbUserShopCarEntity> tcsbUserShopCarEntities = systemService
                    .findHql(
                            "from TcsbUserShopCarEntity where shopcarId =? and openid = ?",
                            tcsbShopCarEntity.getId(), param.getOpenId());
            if (tcsbUserShopCarEntities.isEmpty()) {
                TcsbUserShopCarEntity tcsbUserShopCarEntity = new TcsbUserShopCarEntity();
                tcsbUserShopCarEntity.setCreateTime(new Date());
                tcsbUserShopCarEntity.setOpenid(param.getOpenId());
                tcsbUserShopCarEntity.setShopcarId(tcsbShopCarEntity.getId());
                tcsbUserShopCarEntity.setIsJustNowServing(param
                        .getIsJustNowServing());
                tcsbUserShopCarEntity.setIsRoom(param.getIsRoom());
                tcsbUserShopCarEntity.setNote(param.getNote());
                tcsbUserShopCarEntity.setPeopleNum(param.getPeopleNum());
                tcsbUserShopCarEntity.setTaste(param.getTaste());
                tcsbUserShopCarEntity.setShopId(param.getShopId());
                tcsbUserShopCarEntity
                        .setOrderParentId(param.getOrderParentId());
                systemService.save(tcsbUserShopCarEntity);
            }
            String body = "点单么平台";
            String money = payMoney + "";// 获取订单金额
            // 保留两位小数位
            money = BigDecimalUtil.numericRetentionDecimal(money, 2);
            // 金额转化为分为单位
            float sessionmoney = Float.parseFloat(money);
            String finalmoney = String.format("%.2f", sessionmoney);
            finalmoney = finalmoney.replace(".", "");
            // 商户订单号
            String out_trade_no = OrderNumberGenerateUtil.getOrderNumber()
                    + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。

            // tcsbOrderEntity.setOrderNo(out_trade_no);
            // tcsbOrderService.saveOrUpdate(tcsbOrderEntity);

            int intMoney = Integer.parseInt(finalmoney);
            // 总金额以分为单位，不带小数点
            String totalFee = String.valueOf(intMoney);
            String ipAddress = request.getRemoteAddr();
            String prepayId;
            SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
            prepayId = WxPayUtil.getAppletShopCarPrepayId(totalFee, ipAddress,
                    out_trade_no, body, param.getOpenId());
            // 2 给H5页面传递参数 调起微信支付
            signParams = WxPayUtil.createAppletShopCarPackageValue(prepayId);
            ajaxJson.setObj(signParams);
            ajaxJson.setSuccess(true);
        } catch (Exception e1) {
            tcsbShopCarEntity.setIsLock(0);
            try {
                tcsbShopCarService.saveOrUpdate(tcsbShopCarEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e1.printStackTrace();
        }

        return ajaxJson;
    }

    /**
     * couponsConfirm(调起微信支付)预约餐前付款（全额的情况）
     *
     * @param request
     * @param totalFee //支付金额
     * @param body     //支付描述
     * @return
     * @throws ParseException
     * @throws Exception      扫描餐前付款
     */
    @RequestMapping(params = "couponsReservationTotalMoneyShopCarConfirm")
    @ResponseBody
    public AjaxJson couponsReservationTotalMoneyShopCarConfirm(
            HttpServletRequest request, AppletReservationRequestParamVo param)
            throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();
        TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.get(TcsbShopCarEntity.class, param.getShopCarId());
        // 锁定购物车
        tcsbShopCarEntity.setIsLock(1);
        try {
            tcsbShopCarService.saveOrUpdate(tcsbShopCarEntity);
            // 获取购物车列表
            List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = tcsbShopCarItemService
                    .findHql("from TcsbShopCarItemEntity where shopCar =? ",
                            tcsbShopCarEntity.getId());
            Double price = 0.0;
            Double nodisMoney = 0.0;
            for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                Integer num = tcsbShopCarItemEntity.getCount();
                Double money = 0.0;
                TcsbFoodEntity tcsbFoodEntity = systemService.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
                if (StringUtil
                        .isNotEmpty(tcsbShopCarItemEntity.getStandardId())) {
                    // 根据规格ID获取实际的价格
                    TcsbFoodStandardEntity tcsbFoodStandardEntity = tcsbFoodStandardService
                            .get(TcsbFoodStandardEntity.class,
                                    tcsbShopCarItemEntity.getStandardId());
                    money = BigDecimalUtil.mul(
                            tcsbFoodStandardEntity.getPrice(), num);
                } else {

                    money = BigDecimalUtil.mul(tcsbFoodEntity.getPrice(), num);
                }
                price = BigDecimalUtil.add(price, money);

                if (tcsbFoodEntity.getIsDis().equals("N")) {
                    nodisMoney = BigDecimalUtil.add(nodisMoney, money);
                }

            }
            Double payMoney = 0.0;
            payMoney = price;
            double disMoney = BigDecimalUtil.sub(price, nodisMoney);
            // 打折情况
            // 获取优惠活动
            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService
                    .findByProperty(TcsbDiscountActivityEntity.class, "shopId",
                            param.getShopId());
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                            .get(TcsbShopFullcutTemplateEntity.class,
                                    tcsbDiscountActivityEntity
                                            .getFullcutTemplateId());
                    tcsbShopFullcutTemplateEntities
                            .add(tcsbFullcutTemplateEntity);
                }
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total",
                            "discount");
                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                        if (disMoney >= tcsbShopFullcutTemplateEntities.get(i)
                                .getTotal()) {
                            payMoney = BigDecimalUtil
                                    .sub(payMoney,
                                            Double.parseDouble(tcsbShopFullcutTemplateEntities
                                                    .get(i).getDiscount() + ""));

                            disMoney = BigDecimalUtil
                                    .sub(disMoney,
                                            Double.parseDouble(tcsbShopFullcutTemplateEntities
                                                    .get(i).getDiscount() + ""));

                            if (payMoney < 0) {
                                payMoney = 0.01;
                            }
                            break;
                        }
                    }
                }
            }
            // 获取优惠券信息
            List<TcsbCouponEntity> tcsbCouponEntities = systemService
                    .findHql(
                            "from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?",
                            param.getShopId(), param.getOpenId());
            List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
            if (!tcsbCouponEntities.isEmpty()) {
                for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                    TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                            .get(TcsbFullcutTemplateEntity.class,
                                    tcsbCouponEntity.getFullcutTemplateId());
                    Integer userPeriod = tcsbFullcutTemplateEntity
                            .getUsePeriod();// 使用期限
                    String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); // 日期单位
                    // 专用券中有效的满减活动
                    Calendar calDes = null;
                    try {
                        calDes = DateUtils.parseCalendar(DateUtils.date2Str(
                                tcsbCouponEntity.getExpiryDate(),
                                DateUtils.datetimeFormat),
                                "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calSrc = null;
                    try {
                        calSrc = DateUtils.parseCalendar(DateUtils.date2Str(
                                new Date(), DateUtils.datetimeFormat),
                                "yyyy-MM-dd HH:mm:ss");
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                        tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity
                                .getId());
                        tcsbFullcutTemplateEntities
                                .add(tcsbFullcutTemplateEntity);
                    }
                    ;
                }
            }
            // 立减
            if (!tcsbFullcutTemplateEntities.isEmpty()) {
                ListSorter.sort(tcsbFullcutTemplateEntities, "total",
                        "discount");
                outer:
                for (int i = tcsbFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                    TcsbCouponEntity tcsbCouponEntity = systemService.get(
                            TcsbCouponEntity.class, tcsbFullcutTemplateEntities
                                    .get(i).getCouponId());
                    if (tcsbCouponEntity.getUseRange().equals("0")) {
                        if (disMoney >= tcsbFullcutTemplateEntities.get(i)
                                .getTotal()) {
                            payMoney = BigDecimalUtil.sub(payMoney, Double
                                    .parseDouble(tcsbFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            if (payMoney < 0) {
                                payMoney = 0.01;
                            }
                            break outer;
                        }
                    } else {
                        // 查找订单项中是否有该道菜
                        TcsbFoodEntity tcsbFoodEntity = systemService.get(
                                TcsbFoodEntity.class,
                                tcsbCouponEntity.getFoodId());
                        for (int j = 0; j < tcsbShopCarItemEntities.size(); j++) {
                            if (tcsbFoodEntity.getId().equals(
                                    tcsbShopCarItemEntities.get(j).getFoodId())) {
                                payMoney = BigDecimalUtil
                                        .sub(payMoney,
                                                Double.parseDouble(tcsbFullcutTemplateEntities
                                                        .get(i).getDiscount()
                                                        + ""));
                                if (payMoney < 0) {
                                    payMoney = 0.01;
                                }
                                break outer;
                            }
                        }
                    }
                }

            }
            // 创建预约虚拟购物车
            List<TcsbUserReservationShopCarEntity> tcsbUserReservationShopCarEntities = systemService
                    .findHql(
                            "from TcsbUserReservationShopCarEntity where shopcarId =? and userId = ?",
                            tcsbShopCarEntity.getId(), param.getOpenId());
            if (tcsbUserReservationShopCarEntities.isEmpty()) {
                TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity = new TcsbUserReservationShopCarEntity();
                tcsbUserReservationShopCarEntity.setCreateTime(new Date());
                tcsbUserReservationShopCarEntity.setEatTime(DateUtils
                        .parseDate(param.getEatTime(), "yyyy-MM-dd HH:mm"));
                tcsbUserReservationShopCarEntity.setNickname(param
                        .getNickname());
                tcsbUserReservationShopCarEntity.setNote(param.getNote());
                tcsbUserReservationShopCarEntity.setPeopleNum(param
                        .getPeopleNum());
                tcsbUserReservationShopCarEntity.setPhone(param.getPhone());
                tcsbUserReservationShopCarEntity.setSex(param.getSex());
                tcsbUserReservationShopCarEntity.setShopcarId(tcsbShopCarEntity
                        .getId());
                tcsbUserReservationShopCarEntity.setShopId(param.getShopId());
                tcsbUserReservationShopCarEntity.setReservationId(param.getReservationId());
                tcsbUserReservationShopCarEntity.setUserId(param.getOpenId());
                systemService.save(tcsbUserReservationShopCarEntity);
            }
            String body = "点单么平台";
            String money = payMoney + "";// 获取订单金额
            // 保留两位小数位
            money = BigDecimalUtil.numericRetentionDecimal(money, 2);
            // 金额转化为分为单位
            float sessionmoney = Float.parseFloat(money);
            String finalmoney = String.format("%.2f", sessionmoney);
            finalmoney = finalmoney.replace(".", "");
            // 商户订单号
            String out_trade_no = OrderNumberGenerateUtil.getOrderNumber()
                    + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。

            // tcsbOrderEntity.setOrderNo(out_trade_no);
            // tcsbOrderService.saveOrUpdate(tcsbOrderEntity);

            int intMoney = Integer.parseInt(finalmoney);
            // 总金额以分为单位，不带小数点
            String totalFee = String.valueOf(intMoney);
            String ipAddress = request.getRemoteAddr();
            String prepayId;
            SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
            prepayId = WxPayUtil.getAppletReservationTotalMoneyShopCarPrepayId(totalFee, ipAddress,
                    out_trade_no, body, param.getOpenId());

            TcsbDeskReservationEntity tcsbDeskReservationEntity = systemService.get(TcsbDeskReservationEntity.class, param.getReservationId());
            tcsbDeskReservationEntity.setPrepayId(prepayId);
            systemService.saveOrUpdate(tcsbDeskReservationEntity);
            // 2 给H5页面传递参数 调起微信支付
            signParams = WxPayUtil.createAppletReservationTotalMoneyShopCarPackageValue(prepayId);
            ajaxJson.setObj(signParams);
            ajaxJson.setSuccess(true);
        } catch (Exception e1) {
            tcsbShopCarEntity.setIsLock(0);
            try {
                tcsbShopCarService.saveOrUpdate(tcsbShopCarEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e1.printStackTrace();
        }

        return ajaxJson;
    }

    /**
     * 微信支付异步回调(预约下单餐前全额付款回调函数)
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay
     * .action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "reservationTotalMoneyshopCarPayNotify")
    @ResponseBody
    public JsonResult reservationTotalMoneyshopCarPayNotify(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {

            String orderNumber = (String) map.get("out_trade_no");
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null
                            && orderMap.get("result_code").equalsIgnoreCase(
                            "SUCCESS")) {
                        if (orderMap.get("trade_state") != null
                                && orderMap.get("trade_state")
                                .equalsIgnoreCase("SUCCESS")) {

                            System.out.println("openid ======="
                                    + map.get("openid"));
                            String orderNo = map.get("out_trade_no");
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改

                            // 拿到预约虚拟购物车的信息
                            TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity = systemService
                                    .findUniqueByProperty(
                                            TcsbUserReservationShopCarEntity.class,
                                            "userId", map.get("openid"));
                            if (StringUtil
                                    .isNotEmpty(tcsbUserReservationShopCarEntity)) {
                                TcsbShopCarEntity tcsbShopCarEntity = systemService
                                        .get(TcsbShopCarEntity.class,
                                                tcsbUserReservationShopCarEntity
                                                        .getShopcarId());
                                // 查看该用用户有没有预约的订单
                                /*List<TcsbDeskReservationEntity> tcsbDeskReservationEntities = systemService
                                        .findHql(
												"from TcsbDeskReservationEntity where shopId = ?  and userId = ? and payStatus='0'",
												tcsbUserReservationShopCarEntity.getShopId(),
												tcsbUserReservationShopCarEntity
														.getUserId());*/
                                TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, tcsbUserReservationShopCarEntity.getReservationId());

                                List<TcsbDeskReservationItemEntity> tcsbDeskReservationItemEntities = systemService
                                        .findByProperty(
                                                TcsbDeskReservationItemEntity.class,
                                                "reservationId", tcsbUserReservationShopCarEntity.getReservationId());
                                // 空预约订单的情况(都是空订单)（0全额付款）
                                if (tcsbDeskReservationItemEntities.isEmpty()) {
                                    //生成预约订单
                                    deskReservationNotItem(orderNo,
                                            tcsbUserReservationShopCarEntity,
                                            tcsbShopCarEntity,
                                            tcsbDeskReservationEntity, "0");
                                    //删除购物车信息
                                    systemService.delete(tcsbUserReservationShopCarEntity);
                                    tcsbShopCarService.delete(tcsbShopCarEntity);
                                }
                                return JsonResult.ok();
                            /*	// 有订单的情况
								else {
									TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity = tcsbDeskReservationItemEntities.get(0);
									//追加购物项

								}*/
                                //删除预约虚拟购物车的信息

                            }

                        }
                    }
                }
            }
            return JsonResult.ok();
        } else {
            return JsonResult.fail("预约失败");
        }
    }

    /**
     * couponsConfirm(调起微信支付)预约餐前付款（押金的情况）
     *
     * @param request
     * @param totalFee //支付金额
     * @param body     //支付描述
     * @return
     * @throws ParseException
     * @throws Exception      扫描餐前付款
     */
    @RequestMapping(params = "couponsReservationDepositMoneyShopCarConfirm")
    @ResponseBody
    public AjaxJson couponsReservationDepositMoneyShopCarConfirm(
            HttpServletRequest request, AppletReservationRequestParamVo param)
            throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();
        TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.get(TcsbShopCarEntity.class, param.getShopCarId());
        try {
            TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, param.getShopId());
            //获取所需的押金
            Double payMoney = tcsbShopEntity.getReserveDeposit();
            // 创建预约虚拟购物车
            List<TcsbUserReservationShopCarEntity> tcsbUserReservationShopCarEntities = systemService
                    .findHql(
                            "from TcsbUserReservationShopCarEntity where shopcarId =? and userId = ?",
                            tcsbShopCarEntity.getId(), param.getOpenId());
            if (tcsbUserReservationShopCarEntities.isEmpty()) {
                TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity = new TcsbUserReservationShopCarEntity();
                tcsbUserReservationShopCarEntity.setCreateTime(new Date());
                tcsbUserReservationShopCarEntity.setEatTime(DateUtils
                        .parseDate(param.getEatTime(), "yyyy-MM-dd HH:mm"));
                tcsbUserReservationShopCarEntity.setNickname(param
                        .getNickname());
                tcsbUserReservationShopCarEntity.setNote(param.getNote());
                tcsbUserReservationShopCarEntity.setPeopleNum(param
                        .getPeopleNum());
                tcsbUserReservationShopCarEntity.setPhone(param.getPhone());
                tcsbUserReservationShopCarEntity.setSex(param.getSex());
                tcsbUserReservationShopCarEntity.setShopcarId(tcsbShopCarEntity
                        .getId());
                tcsbUserReservationShopCarEntity.setShopId(param.getShopId());
                tcsbUserReservationShopCarEntity.setUserId(param.getOpenId());
                tcsbUserReservationShopCarEntity.setReservationId(param.getReservationId());
                systemService.save(tcsbUserReservationShopCarEntity);
            }
            String body = "点单么平台";
            String money = payMoney + "";// 获取订单金额
            // 保留两位小数位
            money = BigDecimalUtil.numericRetentionDecimal(money, 2);
            // 金额转化为分为单位
            float sessionmoney = Float.parseFloat(money);
            String finalmoney = String.format("%.2f", sessionmoney);
            finalmoney = finalmoney.replace(".", "");
            // 商户订单号
            String out_trade_no = OrderNumberGenerateUtil.getOrderNumber()
                    + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。

            // tcsbOrderEntity.setOrderNo(out_trade_no);
            // tcsbOrderService.saveOrUpdate(tcsbOrderEntity);

            int intMoney = Integer.parseInt(finalmoney);
            // 总金额以分为单位，不带小数点
            String totalFee = String.valueOf(intMoney);
            String ipAddress = request.getRemoteAddr();
            String prepayId;
            SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
            prepayId = WxPayUtil.getAppletReservationDepositMoneyShopCarPrepayId(totalFee, ipAddress,
                    out_trade_no, body, param.getOpenId());

            TcsbDeskReservationEntity tcsbDeskReservationEntity = systemService.get(TcsbDeskReservationEntity.class, param.getReservationId());
            tcsbDeskReservationEntity.setPrepayId(prepayId);
            systemService.saveOrUpdate(tcsbDeskReservationEntity);

            // 2 给H5页面传递参数 调起微信支付
            signParams = WxPayUtil.createAppletReservationDepositMoneyShopCarPackageValue(prepayId);
            ajaxJson.setObj(signParams);
            ajaxJson.setSuccess(true);
        } catch (Exception e1) {
            tcsbShopCarEntity.setIsLock(0);
            try {
                tcsbShopCarService.saveOrUpdate(tcsbShopCarEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            e1.printStackTrace();
        }

        return ajaxJson;
    }

    /**
     * 微信支付异步回调(预约下单餐前押金付款回调函数)
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay
     * .action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "reservationDepositMoneyshopCarPayNotify")
    @ResponseBody
    public JsonResult reservationDepositMoneyshopCarPayNotify(
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {

            String orderNumber = (String) map.get("out_trade_no");
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null
                            && orderMap.get("result_code").equalsIgnoreCase(
                            "SUCCESS")) {
                        if (orderMap.get("trade_state") != null
                                && orderMap.get("trade_state")
                                .equalsIgnoreCase("SUCCESS")) {

                            System.out.println("openid ======="
                                    + map.get("openid"));
                            String orderNo = map.get("out_trade_no");
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
                            // 拿到预约虚拟购物车的信息
                            TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity = systemService
                                    .findUniqueByProperty(
                                            TcsbUserReservationShopCarEntity.class,
                                            "userId", map.get("openid"));
                            if (StringUtil
                                    .isNotEmpty(tcsbUserReservationShopCarEntity)) {
                                TcsbShopCarEntity tcsbShopCarEntity = systemService
                                        .get(TcsbShopCarEntity.class,
                                                tcsbUserReservationShopCarEntity
                                                        .getShopcarId());
                                // 查看该用用户有没有预约的订单
								/*List<TcsbDeskReservationEntity> tcsbDeskReservationEntities = systemService
										.findHql(
												"from TcsbDeskReservationEntity where shopId = ?  and userId = ? and payStatus='0'",
												tcsbUserReservationShopCarEntity.getShopId(),
												tcsbUserReservationShopCarEntity
														.getUserId());*/
                                TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, tcsbUserReservationShopCarEntity.getReservationId());

                                List<TcsbDeskReservationItemEntity> tcsbDeskReservationItemEntities = systemService
                                        .findByProperty(
                                                TcsbDeskReservationItemEntity.class,
                                                "reservationId", tcsbUserReservationShopCarEntity.getReservationId());
                                // 空预约订单的情况(都是空订单)
                                if (tcsbDeskReservationItemEntities.isEmpty()) {
                                    //生成预约订单
                                    deskReservationNotItem(orderNo,
                                            tcsbUserReservationShopCarEntity,
                                            tcsbShopCarEntity,
                                            tcsbDeskReservationEntity, "3");
                                    //删除购物车信息
                                    systemService.delete(tcsbUserReservationShopCarEntity);
                                    tcsbShopCarService.delete(tcsbShopCarEntity);
                                }
                                return JsonResult.ok();
							/*	// 有订单的情况
								else {
									TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity = tcsbDeskReservationItemEntities.get(0);
									//追加购物项

								}*/
                                //删除预约虚拟购物车的信息

                            }

                        }
                    }
                }
            }
            return JsonResult.ok();
        } else {
            return JsonResult.fail("预约失败");
        }
    }


    private void deskReservationNotItem(String orderNo,
                                        TcsbUserReservationShopCarEntity tcsbUserReservationShopCarEntity,
                                        TcsbShopCarEntity tcsbShopCarEntity,
                                        TcsbDeskReservationEntity tcsbDeskReservationEntity, String flag) {
        tcsbDeskReservationEntity.setNickname(tcsbUserReservationShopCarEntity
                .getNickname());
        tcsbDeskReservationEntity.setNote(tcsbUserReservationShopCarEntity
                .getNote());
        tcsbDeskReservationEntity.setNum(tcsbUserReservationShopCarEntity
                .getPeopleNum());

        tcsbDeskReservationEntity.setPhone(tcsbUserReservationShopCarEntity
                .getPhone());
        tcsbDeskReservationEntity.setSex(tcsbUserReservationShopCarEntity
                .getSex());
        //预约成功
        tcsbDeskReservationEntity.setStatus("1");
        tcsbDeskReservationEntity.setOrderTime(tcsbUserReservationShopCarEntity
                .getEatTime());

        //发送商家短信消息
        TcsbShopEntity shopEntity = tcsbDeskReservationService.get(TcsbShopEntity.class, tcsbDeskReservationEntity.getShopId());
        String content = "您有新的预订订单请及时接单！" + ",预订人:"
                + tcsbDeskReservationEntity.getNickname() + ",预订电话:" + tcsbDeskReservationEntity.getPhone()
                + ",预订时间:"+DateUtils.date2Str(tcsbDeskReservationEntity.getOrderTime(),DateUtils.time_sdf);
        //发送短信
        try {
            SaleSmsClient.sendMessageForContent(shopEntity.getPhone(), content);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Double totalPrice = 0.0;
        //全额付款的情况 (自动使用优惠)
        if ("0".equals(flag)) {
            tcsbDeskReservationEntity.setNo(orderNo);
            tcsbDeskReservationEntity.setPayStatus("1");
            //全额付款的情况 直接计算工出优惠的信息
            // 获取购物车的信息（添加购物)
            List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = systemService
                    .findHql("from TcsbShopCarItemEntity where shopCar = ?",
                            tcsbShopCarEntity.getId());

            for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                TcsbFoodEntity tcsbFood = tcsbDeskReservationService.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
                TcsbFoodUnitEntity unit = tcsbFoodUnitService.get(TcsbFoodUnitEntity.class, tcsbFood.getUnitId());
                TcsbDeskReservationItemEntity reservationItem = new TcsbDeskReservationItemEntity();
                //reservationItem.setFoodtastefun(tcsbShopCarItemVo.getFoodTasteFun());
                reservationItem.setReservationId(tcsbDeskReservationEntity.getId());
                reservationItem.setFoodId(tcsbShopCarItemEntity.getFoodId());
                Double tempMoney = 0.0;
                if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getStandardId())) {
                    TcsbFoodStandardEntity tfs = tcsbFoodUnitService.get(TcsbFoodStandardEntity.class, tcsbShopCarItemEntity.getStandardId());
                    if (StringUtil.isNotEmpty(tfs)) {
                        reservationItem.setPrice(tfs.getPrice());
                        reservationItem.setFoodtastefun(tcsbShopCarItemEntity.getFoodTasteFun());
                        reservationItem.setStandardId(tfs.getId());
                        reservationItem.setStandardName(tfs.getName());
                        tempMoney = BigDecimalUtil.mul(tfs.getPrice(), tcsbShopCarItemEntity.getCount());
                        totalPrice = BigDecimalUtil.add(totalPrice, tempMoney);
                    } else {
                        reservationItem.setFoodtastefun(tcsbShopCarItemEntity.getFoodTasteFun());
                        reservationItem.setPrice(tcsbFood.getPrice());
                        tempMoney = BigDecimalUtil.mul(tcsbFood.getPrice(), tcsbShopCarItemEntity.getCount());
                        totalPrice = BigDecimalUtil.add(totalPrice, tempMoney);
                    }


                } else if (StringUtil.isNotEmpty(tcsbFood)) {
                    reservationItem.setFoodtastefun(tcsbShopCarItemEntity.getFoodTasteFun());
                    reservationItem.setPrice(tcsbFood.getPrice());
                    tempMoney = BigDecimalUtil.mul(tcsbFood.getPrice(), tcsbShopCarItemEntity.getCount());
                    totalPrice = BigDecimalUtil.add(totalPrice, tempMoney);
                }
                //reservationItem.setPrice(tcsbFood.getPrice());
                reservationItem.setFoodName(tcsbFood.getName());
                reservationItem.setUnitName(unit.getName());
                if (tcsbShopCarItemEntity.isCurrentPrice()) {
                    //时价菜品
                    if (tcsbShopCarItemEntity.getCount() == 1) {
                        reservationItem.setCount(1);
                    } else {
                        for (int i = 0; i < tcsbShopCarItemEntity.getCount(); i++) {
                            reservationItem.setCount(1);
                        }
                    }

                    tcsbDeskReservationService.save(reservationItem);
                } else {
                    //非时价菜品
                    if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getFoodTasteFun())) {
                        //菜品有规格
                        reservationItem.setCount(1);
                        tcsbDeskReservationService.save(reservationItem);
                    } else {
                        //菜品无规格
                        //此处做非时价商品
                        //直接添加
                        String hql = "from TcsbDeskReservationItemEntity where foodId='" + tcsbShopCarItemEntity.getFoodId() + "' and reservationId='" + tcsbDeskReservationEntity.getId() + "'";
                        TcsbDeskReservationItemEntity addtcsbShopCarItem = tcsbDeskReservationService.singleResult(hql);
                        if (StringUtil.isNotEmpty(addtcsbShopCarItem)) {
                            //addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getNum());
                            addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount() + tcsbShopCarItemEntity.getCount());
                            tcsbDeskReservationService.saveOrUpdate(addtcsbShopCarItem);
                        } else {
                            reservationItem.setCount(tcsbShopCarItemEntity.getCount());
                            tcsbDeskReservationService.save(reservationItem);
                        }
                    }
                }
            }
            Double finalMoney = totalPrice;
            //使用优惠信息
            //优惠券
            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService
                    .findByProperty(TcsbDiscountActivityEntity.class, "shopId",
                            tcsbDeskReservationEntity.getShopId());
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                            .get(TcsbShopFullcutTemplateEntity.class,
                                    tcsbDiscountActivityEntity
                                            .getFullcutTemplateId());
                    tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                }
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total",
                            "discount");
                    boolean hasCut = false;
                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                        if (totalPrice >= tcsbShopFullcutTemplateEntities.get(i)
                                .getTotal()) {
                            totalPrice = BigDecimalUtil.sub(totalPrice, Double
                                    .parseDouble(tcsbShopFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            finalMoney = BigDecimalUtil.sub(finalMoney, Double
                                    .parseDouble(tcsbShopFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            // finalMoney = finalMoney -
                            // Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+"");
                            tcsbDeskReservationEntity.setUniversalCouponPrice(Double
                                    .parseDouble(tcsbShopFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            hasCut = true;
                            break;
                        }
                    }
                    if (!hasCut) {
                        tcsbDeskReservationEntity.setUniversalCouponPrice(0.0);
                    }
                } else {
                    tcsbDeskReservationEntity.setUniversalCouponPrice(0.0);
                }
            } else {
                tcsbDeskReservationEntity.setUniversalCouponPrice(0.0);
            }
            // 获取专用券（userId）
            List<TcsbCouponEntity> tcsbCouponEntities = systemService
                    .findHql(
                            "from TcsbCouponEntity where useStatus=0 and shopId = ? and userId = ? and useStatus='0' ",
                            tcsbDeskReservationEntity.getShopId(), tcsbDeskReservationEntity.getUserId());
            List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
            if (!tcsbCouponEntities.isEmpty()) {
                // 获取专用券中满减服务中有效的活动
                for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                    // 获取活动生效的时间
                    TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                            .get(TcsbFullcutTemplateEntity.class,
                                    tcsbCouponEntity.getFullcutTemplateId());
                    Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
                    String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
                    int days = 0;
                    // todo
                    if (dateUnit.equals("year")) {
                        days = userPeriod * DateUtils.getCurrentYearDays();
                    } else if (dateUnit.equals("month")) {
                        days = userPeriod * DateUtils.getCurrentMonthDay();
                    } else {
                        days = userPeriod;
                    }
                    // 专用券中有效的满减活动
                    Calendar calDes;
                    try {
                        calDes = DateUtils.parseCalendar(DateUtils.date2Str(
                                tcsbCouponEntity.getExpiryDate(),
                                DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                        Calendar calSrc = DateUtils.parseCalendar(DateUtils
                                        .date2Str(new Date(), DateUtils.datetimeFormat),
                                "yyyy-MM-dd HH:mm:ss");
                        if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                            tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity
                                    .getId());
                            tcsbFullcutTemplateEntities
                                    .add(tcsbFullcutTemplateEntity);
                        }
                        ;
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            // 立减
            if (!tcsbFullcutTemplateEntities.isEmpty()) {
                ListSorter.sort(tcsbFullcutTemplateEntities, "total", "discount");
                boolean hasCut = false;
                outer:
                for (int i = tcsbFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                    if (finalMoney >= tcsbFullcutTemplateEntities.get(i).getTotal()) {
                        TcsbCouponEntity tcsbCouponEntity = systemService.get(
                                TcsbCouponEntity.class, tcsbFullcutTemplateEntities
                                        .get(i).getCouponId());

                        //把优惠券设置为使用状态
                        tcsbCouponEntity.setUseStatus("1");
                        systemService.saveOrUpdate(tcsbCouponEntity);
                        if ("0".equals(tcsbCouponEntity.getUseRange())) {
                            totalPrice = BigDecimalUtil.sub(
                                    totalPrice,
                                    Double.parseDouble(tcsbFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            // finalMoney = finalMoney -
                            // Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+"");
                            tcsbDeskReservationEntity.setSpecialCouponPrice(Double
                                    .parseDouble(tcsbFullcutTemplateEntities.get(i)
                                            .getDiscount() + ""));
                            hasCut = true;
                            break outer;
                        }
                        if ("1".equals(tcsbCouponEntity.getUseRange())) {
                            TcsbFoodEntity tcsbFoodEntity = systemService.get(
                                    TcsbFoodEntity.class,
                                    tcsbCouponEntity.getFoodId());
                            List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService
                                    .findByProperty(TcsbOrderItemEntity.class,
                                            "orderId", tcsbDeskReservationEntity.getId());
                            // 查找订单项中是否有该道菜
                            for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
                                if (tcsbOrderItemEntity.getFoodId().equals(
                                        tcsbFoodEntity.getId())) {
                                    totalPrice = BigDecimalUtil
                                            .sub(totalPrice,
                                                    Double.parseDouble(tcsbFullcutTemplateEntities
                                                            .get(i).getDiscount()
                                                            + ""));
                                    // finalMoney = finalMoney -
                                    // Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+"");
                                    tcsbDeskReservationEntity.setSpecialCouponPrice(Double
                                            .parseDouble(tcsbFullcutTemplateEntities
                                                    .get(i).getDiscount() + ""));
                                    hasCut = true;
                                    break outer;
                                }
                            }
                        }

                    }
                }
                if (!hasCut) {
                    tcsbDeskReservationEntity.setSpecialCouponPrice(0.0);
                }
            } else {
                tcsbDeskReservationEntity.setSpecialCouponPrice(0.0);
            }
            tcsbDeskReservationEntity.setTotalprice(BigDecimalUtil.numericRetentionDecimal(totalPrice, 2));
            tcsbDeskReservationEntity.setOnlinePrice(BigDecimalUtil.numericRetentionDecimal(finalMoney, 2));
            try {
                tcsbDeskReservationService.saveOrUpdate(tcsbDeskReservationEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //押金的情况
        else {
            //更新押金信息表
            TcsbDepositDeltaEntity tcsbDepositDeltaEntity = tcsbDepositDeltaService.get(TcsbDepositDeltaEntity.class, tcsbDeskReservationEntity.getDepositDeltaId());
            tcsbDepositDeltaEntity.setOrderNo(orderNo);
            tcsbDeskReservationEntity.setPayStatus("0");
            tcsbDepositDeltaEntity.setStatus(1);
            try {
                tcsbDepositDeltaService.saveOrUpdate(tcsbDepositDeltaEntity);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //全额付款的情况 直接计算工出优惠的信息
            // 获取购物车的信息（添加购物)
            List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = systemService
                    .findHql("from TcsbShopCarItemEntity where shopCar = ?",
                            tcsbShopCarEntity.getId());
            for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
                TcsbFoodEntity tcsbFood = tcsbDeskReservationService.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
                TcsbFoodUnitEntity unit = tcsbFoodUnitService.get(TcsbFoodUnitEntity.class, tcsbFood.getUnitId());
                TcsbDeskReservationItemEntity reservationItem = new TcsbDeskReservationItemEntity();
                //reservationItem.setFoodtastefun(tcsbShopCarItemVo.getFoodTasteFun());
                reservationItem.setReservationId(tcsbDeskReservationEntity.getId());
                reservationItem.setFoodId(tcsbShopCarItemEntity.getFoodId());
                if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getStandardId())) {
                    TcsbFoodStandardEntity tfs = tcsbFoodUnitService.get(TcsbFoodStandardEntity.class, tcsbShopCarItemEntity.getStandardId());
                    if (StringUtil.isNotEmpty(tfs)) {
                        reservationItem.setPrice(tfs.getPrice());
                        reservationItem.setFoodtastefun(tcsbShopCarItemEntity.getFoodTasteFun() + "/" + tfs.getName());
                    } else {

                        reservationItem.setFoodtastefun(tcsbShopCarItemEntity.getFoodTasteFun());
                        reservationItem.setPrice(tcsbFood.getPrice());
                    }

                } else if (StringUtil.isNotEmpty(tcsbFood)) {
                    reservationItem.setFoodtastefun(tcsbShopCarItemEntity.getFoodTasteFun());
                    reservationItem.setPrice(tcsbFood.getPrice());
                }
                //reservationItem.setPrice(tcsbFood.getPrice());
                reservationItem.setFoodName(tcsbFood.getName());
                reservationItem.setUnitName(unit.getName());
                if (tcsbShopCarItemEntity.isCurrentPrice()) {
                    //时价菜品
                    if (tcsbShopCarItemEntity.getCount() == 1) {
                        reservationItem.setCount(1);
                    } else {
                        for (int i = 0; i < tcsbShopCarItemEntity.getCount(); i++) {
                            reservationItem.setCount(1);
                        }
                    }
                    tcsbDeskReservationService.save(reservationItem);
                } else {
                    //非时价菜品
                    if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getFoodTasteFun())) {
                        //菜品有规格
                        reservationItem.setCount(1);
                        tcsbDeskReservationService.save(reservationItem);
                    } else {
                        //菜品无规格
                        //此处做非时价商品
                        //直接添加
                        String hql = "from TcsbDeskReservationItemEntity where foodId='" + tcsbShopCarItemEntity.getFoodId() + "' and reservationId='" + tcsbDeskReservationEntity.getId() + "'";
                        TcsbDeskReservationItemEntity addtcsbShopCarItem = tcsbDeskReservationService.singleResult(hql);
                        if (StringUtil.isNotEmpty(addtcsbShopCarItem)) {
                            //addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getNum());
                            addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount() + tcsbShopCarItemEntity.getCount());
                            tcsbDeskReservationService.saveOrUpdate(addtcsbShopCarItem);
                        } else {
                            reservationItem.setCount(tcsbShopCarItemEntity.getCount());
                            tcsbDeskReservationService.save(reservationItem);
                        }
                    }
                }
            }

        }
    }

    /**
     * 微信支付异步回调(扫描下单餐前付款回调函数)
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay
     * .action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "shopCarPayNotify")
    @ResponseBody
    public JsonResult shopCarPayNotify(HttpServletRequest request,
                                       HttpServletResponse response) throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {
            String orderNumber = (String) map.get("out_trade_no");
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null
                            && orderMap.get("result_code").equalsIgnoreCase(
                            "SUCCESS")) {
                        if (orderMap.get("trade_state") != null
                                && orderMap.get("trade_state")
                                .equalsIgnoreCase("SUCCESS")) {
                            System.out.println("openid ======="
                                    + map.get("openid"));
                            String orderNo = map.get("out_trade_no");
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
                            // 拿到虚拟购物车的信息
                            TcsbUserShopCarEntity tcsbUserShopCarEntity = systemService
                                    .findUniqueByProperty(
                                            TcsbUserShopCarEntity.class,
                                            "openid", map.get("openid"));
                            if (StringUtil.isNotEmpty(tcsbUserShopCarEntity)) {

                                TcsbShopCarEntity tcsbShopCarEntity = systemService
                                        .get(TcsbShopCarEntity.class,
                                                tcsbUserShopCarEntity
                                                        .getShopcarId());
                                if (StringUtil.isNotEmpty(tcsbShopCarEntity)) {
                                    AppletRequestParamVo param = new AppletRequestParamVo();
                                    param.setDeskId(tcsbShopCarEntity
                                            .getDeskId());
                                    param.setIsJustNowServing(tcsbUserShopCarEntity
                                            .getIsJustNowServing());
                                    param.setIsRoom(tcsbUserShopCarEntity
                                            .getIsRoom());
                                    param.setNote(tcsbUserShopCarEntity
                                            .getNote());
                                    param.setPeopleNum(tcsbUserShopCarEntity
                                            .getPeopleNum());
                                    param.setShopId(tcsbUserShopCarEntity
                                            .getShopId());
                                    param.setTaste(tcsbUserShopCarEntity
                                            .getTaste());
                                    param.setOrderNo(orderNo);
                                    String deskId = tcsbShopCarEntity
                                            .getDeskId();
                                    TcsbOrderParentEntity tcsbOrderParentEntity = queryHasOrderParent(deskId);

                                    String orderParentId = "";
                                    boolean toAdd = false;
                                    if (StringUtil
                                            .isNotEmpty(tcsbOrderParentEntity)) {
                                        if (tcsbOrderParentEntity
                                                .getOrderLock().equals("0")) {
                                            // 查看是否是空订单
                                            List<TcsbOrderEntity> tcsbOrderEntities = systemService
                                                    .findHql(
                                                            "from TcsbOrderEntity where orderParentId = ?",
                                                            tcsbOrderParentEntity
                                                                    .getId());
                                            if (tcsbOrderEntities.isEmpty()) {
                                                orderParentId = tcsbUserShopCarEntity
                                                        .getOrderParentId();
                                                // 创建子订单信息
                                                TcsbOrderEntity newOrder = this
                                                        .createOrder(
                                                                param,
                                                                tcsbUserShopCarEntity
                                                                        .getOrderParentId());
                                                // 添加接单项
                                                TcsbSubOrderEntity subOrder = this
                                                        .createSubOrderReturnSubId(
                                                                param,
                                                                newOrder.getId());
                                                // 创建子订单项-创建接单子项
                                                toAdd = this
                                                        .addOrderItemAndSubOrderItem(
                                                                subOrder.getId(),
                                                                newOrder,
                                                                "0",
                                                                map.get("openid"));
                                                // 拿到虚拟购物车的信息
                                                systemService
                                                        .delete(tcsbUserShopCarEntity);
                                            } else {
                                                orderParentId = tcsbOrderParentEntity
                                                        .getId();
                                                // 存在正在使用的桌位订单
                                                // 添加子订单(查未支付的订单)
                                                TcsbOrderEntity order = this
                                                        .getOrder(deskId);
                                                if (StringUtil
                                                        .isNotEmpty(order)) {
                                                    // 存在未支付的子订单
                                                    // 创建接单信息
                                                    TcsbSubOrderEntity subOrder = this
                                                            .createSubOrderReturnSubId(
                                                                    param,
                                                                    order.getId());
                                                    // 创建子订单项-创建接单子项
                                                    toAdd = this
                                                            .addOrderItemAndSubOrderItem(
                                                                    subOrder.getId(),
                                                                    order,
                                                                    "1",
                                                                    map.get("openid"));
                                                } else {
                                                    // 不存在未支付的子订单
                                                    // 创建新的子订单信息
                                                    TcsbOrderEntity newOrder = this
                                                            .createOrder(
                                                                    param,
                                                                    tcsbOrderParentEntity
                                                                            .getId());
                                                    // 添加接单项
                                                    TcsbSubOrderEntity subOrder = this
                                                            .createSubOrderReturnSubId(
                                                                    param,
                                                                    newOrder.getId());
                                                    // 创建子订单项-创建接单子项
                                                    toAdd = this
                                                            .addOrderItemAndSubOrderItem(
                                                                    subOrder.getId(),
                                                                    newOrder,
                                                                    "0",
                                                                    map.get("openid"));
                                                }
                                                // 清空虚拟购物车
                                                // 拿到虚拟购物车的信息
                                                systemService
                                                        .delete(tcsbUserShopCarEntity);
                                            }
                                            // ////0空闲1预定2未下单3用餐中
                                            if (toAdd) {
                                                TcsbDeskEntity desk = tcsbDeskService
                                                        .get(TcsbDeskEntity.class,
                                                                param.getDeskId());
                                                desk.setStatus("3");
                                                tcsbDeskService
                                                        .saveOrUpdate(desk);
                                            }
                                            // 解锁购物车
											/*
											 * tcsbShopCarEntity.setIsLock(0);
											 * tcsbShopCarService
											 * .saveOrUpdate(tcsbShopCarEntity);
											 */
                                            //
                                        }
                                        // 锁定该订单
                                        tcsbOrderParentEntity.setFlag("1");
                                        tcsbOrderService
                                                .saveOrUpdate(tcsbOrderParentEntity);
                                        return JsonResult.ok();
                                    }

                                }
                            } else {
                                return JsonResult.ok();
                            }
							/*
							 * else{ //不存在使用中的订单 TcsbOrderParentEntity
							 * newOrderParent = this.createOrderParent(param);
							 * orderParentId = newOrderParent.getId(); //创建子订单信息
							 * TcsbOrderEntity newOrder =
							 * this.createOrder(param, newOrderParent.getId());
							 * //添加接单项 TcsbSubOrderEntity subOrder =
							 * this.createSubOrderReturnSubId(param,
							 * newOrder.getId()); //创建子订单项-创建接单子项 toAdd =
							 * this.addOrderItemAndSubOrderItem
							 * (subOrder.getId(), newOrder); }
							 */

                        }
                    }
                }
            }
        }
        // 解除锁定
        return JsonResult.ok();
    }

    private TcsbOrderParentEntity queryHasOrderParent(String deskId) {
        String hql = "from TcsbOrderParentEntity where deskId='" + deskId
                + "' and orderStatus='1'";
        TcsbOrderParentEntity tcsbOrderParentEntity = systemService
                .singleResult(hql);
        return tcsbOrderParentEntity;
    }

    private TcsbOrderEntity getOrder(String deskId) {
        String tcsbOrder = "from TcsbOrderEntity where deskId='" + deskId
                + "' and payStatus=0";
        TcsbOrderEntity tcsbOrderEntity = systemService.singleResult(tcsbOrder);
        return tcsbOrderEntity;
    }

    private TcsbOrderParentEntity createOrderParent(AppletRequestParamVo param) {
        TcsbOrderParentEntity newtop = new TcsbOrderParentEntity();
        newtop.setDeskId(param.getDeskId());
        newtop.setShopId(param.getShopId());
        newtop.setOrderStatus("1");
        newtop.setOrderLock("0");
        newtop.setCreateDate(new Date());
        newtop.setFrontMoney(0.0);
        newtop.setPeople(param.getPeopleNum());
        systemService.save(newtop);
        return newtop;
    }

    private TcsbOrderEntity createOrder(AppletRequestParamVo param,
                                        String orderParentId) {
        TcsbOrderEntity tcsbOrderEntity = new TcsbOrderEntity();
        tcsbOrderEntity.setShopId(param.getShopId());
        tcsbOrderEntity.setDeskId(param.getDeskId());
        tcsbOrderEntity.setNote(param.getNote());
        tcsbOrderEntity.setTaste(param.getTaste());
        tcsbOrderEntity.setIsRoom(param.getIsRoom());
        tcsbOrderEntity.setPayTime(new Date());
        // 绑定商户订单号
        tcsbOrderEntity.setOrderNo(param.getOrderNo());
        tcsbOrderEntity.setIsJustNowServing(param.getIsJustNowServing());
        // 设置默认值
        tcsbOrderEntity.setUniversalCouponPrice(0.0);
        tcsbOrderEntity.setPlatformDiscountPrice(0.0);
        tcsbOrderEntity.setSpecialCouponPrice(0.0);
        tcsbOrderEntity.setVipDiscountPrice(0.0);
        tcsbOrderEntity.setMethod("0");
        tcsbOrderEntity.setStatus("0");
        tcsbOrderEntity.setPayStatus("1");
        tcsbOrderEntity.setPayMethod("0");
        tcsbOrderEntity.setOrderIstake("N");
        tcsbOrderEntity.setOnlinePrice(0.0);// 线上价格设置为零
        tcsbOrderEntity.setOfflinePrice(0.0);// 线下收款默认为实际消费金额
        tcsbOrderEntity.setCreateTime(new Date());
        tcsbOrderEntity.setCreateDate(new Date());
        tcsbOrderEntity.setOrderParentId(orderParentId);//
        systemService.save(tcsbOrderEntity);
        return tcsbOrderEntity;
    }

    private boolean addOrderItemAndSubOrderItem(String suborderId,
                                                TcsbOrderEntity order, String flag, String openid) {
        // 获取购物车信息
        TcsbShopCarEntity shopCar = systemService.findUniqueByProperty(
                TcsbShopCarEntity.class, "deskId", order.getDeskId());
        //
        List<TcsbShopCarItemEntity> shopCarItemList = systemService
                .findByProperty(TcsbShopCarItemEntity.class, "shopCar",
                        shopCar.getId());
        // 统计价格和优惠信息
        Double totalPrice = 0.0;
        Double offLinePrice = 0.0;
        if ("1".equals(flag)) {
            totalPrice = order.getTotalPrice();
        }
        // 遍历购物项信息
        if (shopCarItemList.size() > 0) {
            for (TcsbShopCarItemEntity shopCar2 : shopCarItemList) {
                TcsbFoodEntity tcsbFoodEntity = systemService.get(
                        TcsbFoodEntity.class, shopCar2.getFoodId());
                double price = BigDecimalUtil.mul(tcsbFoodEntity.getPrice(),
                        shopCar2.getCount());
                totalPrice = BigDecimalUtil.add(totalPrice, price);
                // 创建接单项
                String orderItemRelation = OrderNumberGenerateUtil
                        .getOrderNumber();
                TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
                tcsbUserOrderItemEntity.setCount(Double.valueOf(shopCar2
                        .getCount() + ""));
                tcsbUserOrderItemEntity.setNum(shopCar2.getCount());
                tcsbUserOrderItemEntity.setFoodId(shopCar2.getFoodId());
                tcsbUserOrderItemEntity.setFoodTasteFun(shopCar2
                        .getFoodTasteFun());
                if (StringUtil.isNotEmpty(shopCar2.getStandardId())) {
                    TcsbFoodStandardEntity tfs = systemService.get(
                            TcsbFoodStandardEntity.class,
                            shopCar2.getStandardId());
                    tcsbUserOrderItemEntity.setStandardName(tfs.getName());
                    tcsbUserOrderItemEntity.setPrice(tfs.getPrice());
                } else {
                    tcsbUserOrderItemEntity.setFoodTasteFun(shopCar2
                            .getFoodTasteFun());
                    tcsbUserOrderItemEntity.setPrice(tcsbFoodEntity.getPrice());
                }
                tcsbUserOrderItemEntity.setOrderId(suborderId);
                tcsbUserOrderItemEntity.setFoodName(tcsbFoodEntity.getName());// 设置食品名称
                // tcsbUserOrderItemEntity.setPrice(tcsbFoodEntity.getPrice());//设置食品单价
                tcsbUserOrderItemEntity.setOrderItemRelation(orderItemRelation);
                tcsbUserOrderItemEntity.setStandardId(shopCar2.getStandardId());
                TcsbFoodUnitEntity tcsbFoodUnitEntity = systemService.get(
                        TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
                // 创建订单子项
                if (shopCar2.isCurrentPrice()
                        || StringUtil.isNotEmpty(shopCar2.getFoodTasteFun())) {
                    // 时价或规格不为空
                    TcsbOrderItemEntity tcsbOrderItem = new TcsbOrderItemEntity();
                    tcsbOrderItem.setCount(Double.valueOf(shopCar2.getCount()
                            + ""));
                    tcsbOrderItem.setNum(shopCar2.getCount());
                    tcsbOrderItem.setFoodId(shopCar2.getFoodId());
                    tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
                    if (StringUtil.isNotEmpty(shopCar2.getStandardId())) {
                        TcsbFoodStandardEntity tfs = systemService.get(
                                TcsbFoodStandardEntity.class,
                                shopCar2.getStandardId());
                        tcsbOrderItem.setFoodTasteFun(shopCar2
                                .getFoodTasteFun() + "/" + tfs.getName());
                        tcsbOrderItem.setPrice(tfs.getPrice());
                    } else {
                        tcsbOrderItem.setFoodTasteFun(shopCar2
                                .getFoodTasteFun());
                        tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());
                    }
                    tcsbOrderItem.setOrderId(order.getId());// 设置订单ID
                    tcsbOrderItem.setFoodName(tcsbFoodEntity.getName());// 设置食品名称
                    // tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());//设置食品单价
                    tcsbOrderItem.setStandardId(shopCar2.getStandardId());
                    tcsbOrderItem.setUnitName(tcsbFoodUnitEntity.getName());
                    if (shopCar2.isCurrentPrice()) {
                        tcsbOrderItem.setIsFloat("1");
                        tcsbOrderItem.setIsUpdatePrice(0);
                        tcsbUserOrderItemEntity.setIsFloat("1");
                        tcsbUserOrderItemEntity.setIsUpdatePrice(0);
                    } else {
                        tcsbOrderItem.setIsFloat("0");
                        tcsbOrderItem.setIsUpdatePrice(1);
                        tcsbUserOrderItemEntity.setIsFloat("0");
                        tcsbUserOrderItemEntity.setIsUpdatePrice(1);
                    }
                    tcsbOrderItem.setOrderItemRelation(orderItemRelation);
                    systemService.save(tcsbOrderItem);
                } else {
                    // 非时价且规格为空

                    if (StringUtil.isNotEmpty(shopCar2.getStandardId())) {
                        TcsbOrderItemEntity tcsbOrderItem = new TcsbOrderItemEntity();
                        tcsbOrderItem.setCount(Double.valueOf(shopCar2
                                .getCount()));
                        tcsbOrderItem.setNum(shopCar2.getCount());
                        tcsbOrderItem.setFoodId(shopCar2.getFoodId());
                        if (StringUtil.isNotEmpty(shopCar2.getStandardId())) {
                            TcsbFoodStandardEntity tfs = systemService.get(
                                    TcsbFoodStandardEntity.class,
                                    shopCar2.getStandardId());
                            tcsbOrderItem.setFoodTasteFun(shopCar2
                                    .getFoodTasteFun() + "/" + tfs.getName());
                            tcsbOrderItem.setPrice(tfs.getPrice());
                        } else {
                            tcsbOrderItem.setFoodTasteFun(shopCar2
                                    .getFoodTasteFun());
                            tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());
                        }
                        tcsbOrderItem.setFoodTasteFun(shopCar2
                                .getFoodTasteFun());
                        tcsbOrderItem.setOrderId(order.getId());
                        tcsbOrderItem.setFoodName(tcsbFoodEntity.getName());

                        tcsbOrderItem.setUnitName(tcsbFoodUnitEntity.getName());
                        tcsbOrderItem.setIsFloat("0");
                        tcsbOrderItem.setIsUpdatePrice(Integer.valueOf(1));
                        tcsbOrderItem.setOrderItemRelation(orderItemRelation);
                        systemService.save(tcsbOrderItem);
                    } else {
                        String tcsbOrderItemupdatehql = "from TcsbOrderItemEntity where orderId='"
                                + order.getId()
                                + "' and foodId='"
                                + shopCar2.getFoodId() + "'";
                        List<TcsbOrderItemEntity> tcsbOrderItemupdate = systemService
                                .findByQueryString(tcsbOrderItemupdatehql);
                        if (tcsbOrderItemupdate.size() > 0) {
                            double count = BigDecimalUtil.add(
                                    tcsbOrderItemupdate.get(0).getCount(),
                                    shopCar2.getCount());
                            double num = BigDecimalUtil.add(tcsbOrderItemupdate
                                    .get(0).getNum(), shopCar2.getCount());
                            int ii = (new Double(num)).intValue();
                            tcsbOrderItemupdate.get(0).setCount(count);
                            tcsbOrderItemupdate.get(0).setNum(ii);
                            systemService.saveOrUpdate(tcsbOrderItemupdate
                                    .get(0));
                        } else {
                            TcsbOrderItemEntity tcsbOrderItem = new TcsbOrderItemEntity();
                            tcsbOrderItem.setCount(Double.valueOf(shopCar2
                                    .getCount() + ""));
                            tcsbOrderItem.setNum(shopCar2.getCount());
                            tcsbOrderItem.setFoodId(shopCar2.getFoodId());
                            tcsbOrderItem.setFoodTasteFun(shopCar2
                                    .getFoodTasteFun());
                            tcsbOrderItem.setOrderId(order.getId());
                            tcsbOrderItem.setFoodName(tcsbFoodEntity.getName());// 设置食品名称
                            tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());// 设置食品单价
                            tcsbOrderItem.setUnitName(tcsbFoodUnitEntity
                                    .getName());
                            tcsbOrderItem.setIsFloat("0");
                            tcsbOrderItem.setIsUpdatePrice(1);
                            tcsbOrderItem
                                    .setOrderItemRelation(orderItemRelation);
                            systemService.save(tcsbOrderItem);
                        }
                    }
                    tcsbUserOrderItemEntity.setIsFloat("0");
                    tcsbUserOrderItemEntity.setIsUpdatePrice(1);
                }
                systemService.save(tcsbUserOrderItemEntity);
            }
            // 添加多人购物信息
            clearUserCar(order, shopCar);
        }
        // 获取优惠活动
        List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService
                .findByProperty(TcsbDiscountActivityEntity.class, "shopId",
                        order.getShopId());
        if (!tcsbDiscountActivityEntities.isEmpty()) {
            List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
            for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                        .get(TcsbShopFullcutTemplateEntity.class,
                                tcsbDiscountActivityEntity
                                        .getFullcutTemplateId());
                tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
            }
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                ListSorter.sort(tcsbShopFullcutTemplateEntities, "total",
                        "discount");
                boolean hasCut = false;
                for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                    if (totalPrice >= tcsbShopFullcutTemplateEntities.get(i)
                            .getTotal()) {
                        totalPrice = BigDecimalUtil.sub(totalPrice, Double
                                .parseDouble(tcsbShopFullcutTemplateEntities
                                        .get(i).getDiscount() + ""));
                        // finalMoney = finalMoney -
                        // Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+"");
                        order.setUniversalCouponPrice(Double
                                .parseDouble(tcsbShopFullcutTemplateEntities
                                        .get(i).getDiscount() + ""));
                        hasCut = true;
                        break;
                    }
                }
                if (!hasCut) {
                    order.setUniversalCouponPrice(0.0);
                }
            } else {
                order.setUniversalCouponPrice(0.0);
            }
        } else {
            order.setUniversalCouponPrice(0.0);
        }
        // 获取专用券（userId）
        List<TcsbCouponEntity> tcsbCouponEntities = systemService
                .findHql(
                        "from TcsbCouponEntity where useStatus=0 and shopId = ? and userId = ? and useStatus='0' ",
                        order.getShopId(), openid);
        List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
        if (!tcsbCouponEntities.isEmpty()) {
            // 获取专用券中满减服务中有效的活动
            for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                // 获取活动生效的时间
                TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService
                        .get(TcsbFullcutTemplateEntity.class,
                                tcsbCouponEntity.getFullcutTemplateId());
                Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
                String dateUnit = tcsbFullcutTemplateEntity.getDateUnit();
                int days = 0;
                // todo
                if (dateUnit.equals("year")) {
                    days = userPeriod * DateUtils.getCurrentYearDays();
                } else if (dateUnit.equals("month")) {
                    days = userPeriod * DateUtils.getCurrentMonthDay();
                } else {
                    days = userPeriod;
                }
                // 专用券中有效的满减活动
                Calendar calDes;
                try {
                    calDes = DateUtils.parseCalendar(DateUtils.date2Str(
                            tcsbCouponEntity.getExpiryDate(),
                            DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");
                    Calendar calSrc = DateUtils.parseCalendar(DateUtils
                                    .date2Str(new Date(), DateUtils.datetimeFormat),
                            "yyyy-MM-dd HH:mm:ss");
                    if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                        tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity
                                .getId());
                        tcsbFullcutTemplateEntities
                                .add(tcsbFullcutTemplateEntity);
                    }
                    ;
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        // 立减
        if (!tcsbFullcutTemplateEntities.isEmpty()) {
            ListSorter.sort(tcsbFullcutTemplateEntities, "total", "discount");
            boolean hasCut = false;
            outer:
            for (int i = tcsbFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                if (totalPrice >= tcsbFullcutTemplateEntities.get(i).getTotal()) {
                    TcsbCouponEntity tcsbCouponEntity = systemService.get(
                            TcsbCouponEntity.class, tcsbFullcutTemplateEntities
                                    .get(i).getCouponId());
                    tcsbCouponEntity.setUseStatus("1");
                    systemService.saveOrUpdate(tcsbCouponEntity);
                    if ("0".equals(tcsbCouponEntity.getUseRange())) {
                        totalPrice = BigDecimalUtil.sub(
                                totalPrice,
                                Double.parseDouble(tcsbFullcutTemplateEntities
                                        .get(i).getDiscount() + ""));
                        // finalMoney = finalMoney -
                        // Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+"");
                        order.setSpecialCouponPrice(Double
                                .parseDouble(tcsbFullcutTemplateEntities.get(i)
                                        .getDiscount() + ""));
                        hasCut = true;
                        break outer;
                    }
                    if ("1".equals(tcsbCouponEntity.getUseRange())) {
                        TcsbFoodEntity tcsbFoodEntity = systemService.get(
                                TcsbFoodEntity.class,
                                tcsbCouponEntity.getFoodId());
                        List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService
                                .findByProperty(TcsbOrderItemEntity.class,
                                        "orderId", order.getId());
                        // 查找订单项中是否有该道菜
                        for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
                            if (tcsbOrderItemEntity.getFoodId().equals(
                                    tcsbFoodEntity.getId())) {
                                totalPrice = BigDecimalUtil
                                        .sub(totalPrice,
                                                Double.parseDouble(tcsbFullcutTemplateEntities
                                                        .get(i).getDiscount()
                                                        + ""));
                                // finalMoney = finalMoney -
                                // Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+"");
                                order.setSpecialCouponPrice(Double
                                        .parseDouble(tcsbFullcutTemplateEntities
                                                .get(i).getDiscount() + ""));
                                hasCut = true;
                                break outer;
                            }
                        }
                    }

                }
            }
            if (!hasCut) {
                order.setSpecialCouponPrice(0.0);
            }
        } else {
            order.setSpecialCouponPrice(0.0);
        }
        order.setTotalPrice(totalPrice);
        order.setOnlinePrice(totalPrice);
        tcsbOrderService.saveOrUpdate(order);
        return true;

    }

    private void clearUserCar(TcsbOrderEntity order, TcsbShopCarEntity shopCar) {
        List<TcsbUserCarEntity> tcsbUserCarEntity = systemService
                .findByProperty(TcsbUserCarEntity.class, "carId",
                        shopCar.getId());
        if (StringUtil.isNotEmpty(tcsbUserCarEntity)) {
            for (TcsbUserCarEntity tcsbUserCarEntity2 : tcsbUserCarEntity) {
                String userorderhql = "from TcsbUserOrderEntity where userId='"
                        + tcsbUserCarEntity2.getUserId()
                        + "' and orderParentId='" + order.getOrderParentId()
                        + "'";
                TcsbUserOrderEntity tcsbUserOrderEntitycheck = systemService
                        .singleResult(userorderhql);
                if (tcsbUserOrderEntitycheck == null) {
                    TcsbUserOrderEntity tcsbUserOrderEntity = new TcsbUserOrderEntity();
                    tcsbUserOrderEntity.setOrderParentId(order
                            .getOrderParentId());
                    tcsbUserOrderEntity.setUserId(tcsbUserCarEntity2
                            .getUserId());
                    tcsbUserOrderEntity.setCreateDate(new Date());
                    tcsbUserOrderEntity.setPayStatus("0");
                    systemService.save(tcsbUserOrderEntity);
                }
            }
        }
        // 清空购物车
        systemService.executeSql("delete from tcsb_shop_car where desk_id = ?",
                order.getDeskId());
        systemService.executeSql(
                "delete from tcsb_shop_car_item where shop_car = ?",
                shopCar.getId());
        systemService.executeSql("delete from tcsb_user_car where car_id = ?",
                shopCar.getId());
    }

    private TcsbSubOrderEntity createSubOrderReturnSubId(
            AppletRequestParamVo param, String orderId) {
        TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
        tcsbSubOrderEntity.setShopId(param.getShopId());
        tcsbSubOrderEntity.setDeskId(param.getDeskId());
        tcsbSubOrderEntity.setNote(param.getNote());
        tcsbSubOrderEntity.setTaste(param.getTaste());
        tcsbSubOrderEntity.setIsRoom(param.getIsRoom());
        tcsbSubOrderEntity.setIsJustNowServing(param.getIsJustNowServing());
        // 设置默认值
        tcsbSubOrderEntity.setUniversalCouponPrice(0.0);
        tcsbSubOrderEntity.setPlatformDiscountPrice(0.0);
        tcsbSubOrderEntity.setSpecialCouponPrice(0.0);
        tcsbSubOrderEntity.setPayStatus("0");
        tcsbSubOrderEntity.setPayMethod("1");
        tcsbSubOrderEntity.setOrderIstake("N");
        tcsbSubOrderEntity.setOnlinePrice(0.0);// 线上价格设置为零
        tcsbSubOrderEntity.setOfflinePrice(0.0);// 线下收款默认为实际消费金额
        tcsbSubOrderEntity.setCreateTime(new Date());
        tcsbSubOrderEntity.setOrderNo(orderId);// 设置订单号
        systemService.save(tcsbSubOrderEntity);
        return tcsbSubOrderEntity;
    }

    /**
     * 预约下单 couponsConfirm(调起微信支付)
     *
     * @param request
     * @param totalFee //支付金额
     * @param body     //支付描述
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @RequestMapping(params = "reservationcouponsConfirm")
    @ResponseBody
    public AjaxJson reservationCouponsConfirm(HttpServletRequest request,
                                              @RequestParam("openid") String openid,
                                              @RequestParam("reservationId") String reservationId)
            throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();
        double finalMoney = 0.0;
        TcsbDeskReservationEntity tcsbDeskReservation = tcsbOrderService.get(
                TcsbDeskReservationEntity.class, reservationId);
        // 获取店铺的预约方式/*0全额(1免押金)2押金*/
        TcsbShopEntity tcsbShopEntity = tcsbShopService.get(
                TcsbShopEntity.class, tcsbDeskReservation.getShopId());
        // 全额付款包括免押金
        if ("0".equals(tcsbShopEntity.getReservationMethod())
                || "1".equals(tcsbShopEntity.getReservationMethod())) {
            List<TcsbDeskReservationItemEntity> deskReservationItem = tcsbOrderService
                    .findByProperty(TcsbDeskReservationItemEntity.class,
                            "reservationId", reservationId);

            // double payMoney = 0.0;
            // 检测是否使用过优惠券===start===
            boolean universalCoupon = false;

            // 检测是否使用过优惠券===end===
            for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : deskReservationItem) {
                double addmoney = BigDecimalUtil.mul(
                        tcsbDeskReservationItemEntity.getPrice(),
                        tcsbDeskReservationItemEntity.getCount());
                finalMoney = finalMoney + addmoney;
            }

            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbOrderService
                    .findByProperty(TcsbDiscountActivityEntity.class, "shopId",
                            tcsbDeskReservation.getShopId());
            if (!tcsbDiscountActivityEntities.isEmpty()) {
                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbOrderService
                            .get(TcsbShopFullcutTemplateEntity.class,
                                    tcsbDiscountActivityEntity
                                            .getFullcutTemplateId());
                    tcsbShopFullcutTemplateEntities
                            .add(tcsbFullcutTemplateEntity);
                }
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total",
                            "discount");
                    boolean hasCut = false;
                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                        if (finalMoney >= tcsbShopFullcutTemplateEntities
                                .get(i).getTotal()) {
                            finalMoney = BigDecimalUtil
                                    .sub(finalMoney,
                                            Double.parseDouble(tcsbShopFullcutTemplateEntities
                                                    .get(i).getDiscount() + ""));
                            // reservationInfoVo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(needpay,
                            // 2));
                            tcsbDeskReservation.setSpecialCouponPrice(Double
                                    .valueOf(tcsbShopFullcutTemplateEntities
                                            .get(i).getDiscount() + ""));
                            // reservationInfoVo.setShopUserDiscountMsg(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+"");
                            hasCut = true;
                            break;
                        }
                    }
                }
            }
        }
        // 付押金
        else if ("2".equals(tcsbShopEntity.getReservationMethod())) {
            finalMoney = tcsbShopEntity.getReserveDeposit();
        }
        String body = "点单么平台";
        String money = finalMoney + "";// 获取订单金额
        // 保留两位小数位
        money = BigDecimalUtil.numericRetentionDecimal(money, 2);
        // 金额转化为分为单位
        float sessionmoney = Float.parseFloat(money);
        String finalmoney = String.format("%.2f", sessionmoney);
        finalmoney = finalmoney.replace(".", "");
        // 商户订单号
        String out_trade_no = OrderNumberGenerateUtil.getOrderNumber()
                + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。
        if ("0".equals(tcsbShopEntity.getReservationMethod())
                || "1".equals(tcsbShopEntity.getReservationMethod())) {
            tcsbDeskReservation.setNo(out_trade_no);

        } else if ("2".equals(tcsbShopEntity.getReservationMethod())) {
            // 设计预约押金冲值记录表
            TcsbDepositDeltaEntity tcsbDepositDeltaEntity = new TcsbDepositDeltaEntity();
            tcsbDepositDeltaEntity.setOpenid(tcsbDeskReservation.getUserId());
            tcsbDepositDeltaEntity.setShopId(tcsbDeskReservation.getShopId());
            tcsbDepositDeltaEntity.setReserveDeposit(tcsbShopEntity
                    .getReserveDeposit());
            tcsbDepositDeltaEntity.setOrderNo(out_trade_no);
            tcsbDepositDeltaEntity.setStatus(0);
            try {
                tcsbDepositDeltaService.save(tcsbDepositDeltaEntity);
                // 设置为有押金方式
                tcsbDeskReservation.setIsDeposit("1");
                tcsbDeskReservation.setDepositDeltaId(tcsbDepositDeltaEntity
                        .getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tcsbOrderService.saveOrUpdate(tcsbDeskReservation);
        int intMoney = Integer.parseInt(finalmoney);
        // 总金额以分为单位，不带小数点
        String totalFee = String.valueOf(intMoney);
        String ipAddress = request.getRemoteAddr();
        String prepayId;
        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
        try {
            // 全额付款回调
            if ("0".equals(tcsbShopEntity.getReservationMethod())
                    || "1".equals(tcsbShopEntity.getReservationMethod())) {
                prepayId = WxPayUtil.getAppletReservationPrepayId(totalFee,
                        ipAddress, out_trade_no, body, openid);
                // 2 给H5页面传递参数 调起微信支付
                signParams = WxPayUtil
                        .createAppletReservationPackageValue(prepayId);
            }
            // 押金回调
            else {
                prepayId = WxPayUtil.getAppletReservationDeltaPrepayId(
                        totalFee, ipAddress, out_trade_no, body, openid);
                // 2 给H5页面传递参数 调起微信支付
                signParams = WxPayUtil
                        .createAppletReservationDeltaPackageValue(prepayId);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ajaxJson.setObj(signParams);
        ajaxJson.setSuccess(true);

        return ajaxJson;
    }

    /**
     * 会员冲值 couponsConfirm(调起微信支付)
     *
     * @param request
     * @param totalFee //支付金额
     * @param body     //支付描述
     * @return
     * @throws ParseException
     * @throws Exception
     */
    @RequestMapping(params = "deltaCouponsConfirm")
    @ResponseBody
    public AjaxJson deltaCouponsConfirm(HttpServletRequest request,
                                        @RequestParam("openid") String openid,
                                        @RequestParam("quotaId") String quotaId) throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();
        TcsbDeltaQuotaEntity tcsbDeltaQuotaEntity = tcsbDeltaQuotaService.get(
                TcsbDeltaQuotaEntity.class, quotaId);
        String body = "点单么平台";
        String money = "";// 冲值金额
        money = BigDecimalUtil.numericRetentionDecimal(
                tcsbDeltaQuotaEntity.getQuota(), 2);
        // 金额转化为分为单位
        float sessionmoney = Float.parseFloat(money);
        String finalmoney = String.format("%.2f", sessionmoney);
        finalmoney = finalmoney.replace(".", "");
        // 商户订单号
        String out_trade_no = OrderNumberGenerateUtil.getOrderNumber()
                + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。
        // 生成用户冲值记录
        TcsbUserDeltaEntity tcsbUserDeltaEntity = new TcsbUserDeltaEntity();
        tcsbUserDeltaEntity.setAmount(Double.parseDouble(money + ""));
        tcsbUserDeltaEntity.setShopId(tcsbDeltaQuotaEntity.getShopId());
        tcsbUserDeltaEntity.setOppenid(openid);
        if ("Y".equals(tcsbDeltaQuotaEntity.getIsTowards())) {
            // 保留两位小数位
            String gift = BigDecimalUtil.numericRetentionDecimal(
                    tcsbDeltaQuotaEntity.getTowardsLimit(), 2);
            tcsbUserDeltaEntity.setTowardsLimit(Double.parseDouble(gift));
        } else {
            tcsbUserDeltaEntity.setTowardsLimit(0.0);
        }
        tcsbUserDeltaEntity.setStatus(0);
        tcsbUserDeltaEntity.setOrderNo(out_trade_no);
        tcsbUserDeltaEntity.setCreateDate(new Date());
        try {
            tcsbUserDeltaService.save(tcsbUserDeltaEntity);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        int intMoney = Integer.parseInt(finalmoney);
        // 总金额以分为单位，不带小数点
        String totalFee = String.valueOf(intMoney);
        String ipAddress = request.getRemoteAddr();
        String prepayId;
        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
        try {
            prepayId = WxPayUtil.getAppletDeltaPrepayId(totalFee, ipAddress,
                    out_trade_no, body, openid);
            // 2 给H5页面传递参数 调起微信支付
            signParams = WxPayUtil.createAppletDeltaPackageValue(prepayId);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        ajaxJson.setObj(signParams);
        ajaxJson.setSuccess(true);

        return ajaxJson;
    }

    /**
     * 会员冲值回调 微信支付异步回调
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/
     * wxpay/pay.action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "deltaPayNotify")
    @ResponseBody
    public JsonResult deltaPayNotify(HttpServletRequest request,
                                     HttpServletResponse response, String userId) throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {
            String orderNumber = (String) map.get("out_trade_no");
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null
                            && orderMap.get("result_code").equalsIgnoreCase(
                            "SUCCESS")) {
                        if (orderMap.get("trade_state") != null
                                && orderMap.get("trade_state")
                                .equalsIgnoreCase("SUCCESS")) {
                            String openId = map.get("openid");
                            System.out.println("openid ======="
                                    + orderMap.get("openid"));
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
                            // 更新冲值表为已冲值状态
                            TcsbUserDeltaEntity tcsbUserDeltaEntity = tcsbUserDeltaService
                                    .findUniqueByProperty(
                                            TcsbUserDeltaEntity.class,
                                            "orderNo", orderNumber);
                            tcsbUserDeltaEntity.setStatus(1);
                            tcsbUserDeltaService
                                    .saveOrUpdate(tcsbUserDeltaEntity);
                            // 查询是否有设置会员级别表
                            List<TcsbMembershipLevelEntity> tcsbMembershipLevelEntities = tcsbMembershipLevelService
                                    .findHql(
                                            "from TcsbMembershipLevelEntity where shopId = ?",
                                            tcsbUserDeltaEntity.getShopId());
                            if (tcsbMembershipLevelEntities.isEmpty()) {
                                // 无设置会员级别的情况不做操作
                            } else {
                                List<String> conditions = new ArrayList<>();
                                for (TcsbMembershipLevelEntity tcsbMembershipLevelEntity : tcsbMembershipLevelEntities) {
                                    conditions.add(tcsbMembershipLevelEntity
                                            .getMemberLevelConditionsId());
                                }
                                // 获取会员级别条件
                                CriteriaQuery cq = new CriteriaQuery(
                                        TcsbMemberLevelConditionsEntity.class);

                                cq.add(Restrictions.in("id", conditions));
                                cq.addOrder("charge", SortDirection.desc);
                                List<TcsbMemberLevelConditionsEntity> tcsbMemberLevelConditionsEntities = tcsbMembershipLevelService
                                        .getListByCriteriaQuery(cq, false);
                                // 获取符合的级别条件ID
                                String currentCodittionId = "";
                                for (TcsbMemberLevelConditionsEntity tcsbMemberLevelConditionsEntity : tcsbMemberLevelConditionsEntities) {
                                    Double charge = tcsbMemberLevelConditionsEntity
                                            .getCharge();
                                    if (tcsbUserDeltaEntity.getAmount() >= tcsbMemberLevelConditionsEntity
                                            .getCharge()) {
                                        currentCodittionId = tcsbMemberLevelConditionsEntity
                                                .getId();
                                        break;
                                    }
                                }
                                // 查找是否有该会员
                                List<TcsbMemberUserEntity> tcsbMemberUserEntitys = tcsbMemberUserService
                                        .findHql(
                                                "from TcsbMemberUserEntity where shopId = ? and openid = ?",
                                                tcsbUserDeltaEntity.getShopId(),
                                                tcsbUserDeltaEntity
                                                        .getOppenid());
                                if (tcsbMemberUserEntitys.isEmpty()) {
                                    // 新增加的会员(根据符合的级别条件获取会员级别ID并保存会员信息)
                                    List<TcsbMembershipLevelEntity> tcsbMembershipLevelEntitys = tcsbMembershipLevelService
                                            .findHql(
                                                    "from TcsbMembershipLevelEntity where shopId = ? and memberLevelConditionsId = ?",
                                                    tcsbUserDeltaEntity
                                                            .getShopId(),
                                                    currentCodittionId);
                                    TcsbMemberUserEntity tcsbMemberUserEntity = new TcsbMemberUserEntity();
                                    tcsbMemberUserEntity.setOpenid(openId);
                                    tcsbMemberUserEntity
                                            .setMembershipLevelId(tcsbMembershipLevelEntitys
                                                    .get(0).getId());
                                    tcsbMemberUserEntity
                                            .setBalance(tcsbUserDeltaEntity
                                                    .getAmount());
                                    tcsbMemberUserEntity
                                            .setShopId(tcsbUserDeltaEntity
                                                    .getShopId());
                                    // todo 保存会员的基础信息在加
                                    tcsbMemberUserService
                                            .save(tcsbMemberUserEntity);
                                    return JsonResult.ok();
                                }
                                // 更新会员
                                else {
                                    // 获取当前会员的级别条件
                                    String sql = "select c.id from tcsb_member_level_conditions c left JOIN tcsb_membership_level l on l.member_level_conditions_id = c.id left join tcsb_member_user u on u.membership_level_id = l.id where u.shop_id = ? and u.openid = ? ";
                                    List<Map<String, Object>> tcsbMemberLevelConditionsEntitys = tcsbMemberLevelConditionsService
                                            .findForJdbc(sql,
                                                    tcsbUserDeltaEntity
                                                            .getShopId(),
                                                    openId);
                                    TcsbMemberLevelConditionsEntity tcsbMemberLevelConditionsEntity = tcsbMemberLevelConditionsService
                                            .get(TcsbMemberLevelConditionsEntity.class,
                                                    (String) tcsbMemberLevelConditionsEntitys
                                                            .get(0).get("id"));
                                    // 会员是否具备升级的条件
                                    if (!currentCodittionId
                                            .equals(tcsbMemberLevelConditionsEntity
                                                    .getId())) {
                                        TcsbMemberLevelConditionsEntity tcsbMemberLevelConditionsEntity2 = tcsbMemberLevelConditionsService
                                                .get(TcsbMemberLevelConditionsEntity.class,
                                                        currentCodittionId);
                                        if (tcsbMemberLevelConditionsEntity
                                                .getCharge() < tcsbMemberLevelConditionsEntity2
                                                .getCharge()) {
                                            // 开始升级()
                                            List<TcsbMembershipLevelEntity> tcsbMembershipLevelEntitys = tcsbMembershipLevelService
                                                    .findHql(
                                                            "from TcsbMembershipLevelEntity where memberLevelConditionsId = ? and shopId = ?",
                                                            currentCodittionId,
                                                            tcsbUserDeltaEntity
                                                                    .getShopId());
                                            TcsbMemberUserEntity tcsbMemberUserEntity = tcsbMemberUserEntitys
                                                    .get(0);
                                            tcsbMemberUserEntity
                                                    .setBalance(tcsbMemberUserEntity
                                                            .getBalance()
                                                            + tcsbUserDeltaEntity
                                                            .getAmount());
                                            tcsbMemberUserEntity
                                                    .setMembershipLevelId(tcsbMembershipLevelEntitys
                                                            .get(0).getId());
                                            return JsonResult.ok();
                                        }
                                    }

                                }
                            }

                        }
                    }
                }
            }
        }
        return JsonResult.ok();
    }

    

    /**
     * 微信支付异步回调
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay
     * .action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "reservationPayNotify")
    @ResponseBody
    public JsonResult reservationPayNotify(HttpServletRequest request,
                                           HttpServletResponse response, String userId) throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {
            String orderNumber = (String) map.get("out_trade_no");
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null
                            && orderMap.get("result_code").equalsIgnoreCase(
                            "SUCCESS")) {
                        if (orderMap.get("trade_state") != null
                                && orderMap.get("trade_state")
                                .equalsIgnoreCase("SUCCESS")) {
                            System.out.println("openid ======="
                                    + map.get("openid"));
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
                            TcsbDeskReservationEntity tcsbDeskReservationEntity = tcsbOrderService
                                    .findUniqueByProperty(
                                            TcsbDeskReservationEntity.class,
                                            "no", orderNumber);
                            if (tcsbDeskReservationEntity.getPayStatus()
                                    .equals("1")) {
                                return JsonResult.ok();
                            } else {
                                tcsbDeskReservationEntity.setPayStatus("1");
                                tcsbDeskReservationEntity
                                        .setOnlinePrice(BigDecimalUtil.numericRetentionDecimal(
                                                BigDecimalUtil
                                                        .sub(Double
                                                                        .valueOf(tcsbDeskReservationEntity
                                                                                .getTotalprice()),
                                                                tcsbDeskReservationEntity
                                                                        .getSpecialCouponPrice()),
                                                2));
                                tcsbOrderService
                                        .saveOrUpdate(tcsbDeskReservationEntity);
                                return JsonResult.ok();
                            }
                        }
                    }
                } else {
                    // 解除锁定
                    TcsbDeskReservationEntity tcsbOrderEntity = tcsbOrderService
                            .findUniqueByProperty(
                                    TcsbDeskReservationEntity.class, "no",
                                    orderNumber);
                    tcsbOrderEntity.setUniversalCouponPrice(0.0);
                    tcsbOrderEntity.setPlatformDiscountPrice(0.0);
                    tcsbOrderEntity.setSpecialCouponPrice(0.0);
                    tcsbOrderEntity.setOnlinePrice("0");
                    tcsbOrderEntity.setIsDeposit("0");
                    tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                    return JsonResult.fail("");
                }
            }
        }
        // 解除锁定
        return JsonResult.ok();
    }

    /**
     * 微信支付异步回调(押金支付回调)
     * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com
     * /wxpay/pay.action“（微信坑不支持带参的，否则接收不到微信的流信息）
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "reservationDeltaNotify")
    @ResponseBody
    public JsonResult reservationDeltaNotify(HttpServletRequest request,
                                             HttpServletResponse response, String userId) throws Exception {
        BufferedReader reader = null;
        reader = request.getReader();
        String line = "";
        String xmlString = null;
        StringBuffer inputString = new StringBuffer();

        while ((line = reader.readLine()) != null) {
            inputString.append(line);
        }
        xmlString = inputString.toString();
        request.getReader().close();
        System.out.println("----接收到的数据如下：---" + xmlString);
        Map<String, String> map = new HashMap<String, String>();
        map = XMLUtil.doXMLParse(xmlString);
        if (checkSign(xmlString)) {
            String orderNumber = (String) map.get("out_trade_no");
            if (orderNumber != null) {
                // 调用微信查询订单接口，
                Map<String, String> orderMap = WxPayUtil
                        .queryWeiXinOrder(orderNumber);
                if (orderMap.get("return_code") != null
                        && orderMap.get("return_code").equalsIgnoreCase(
                        "SUCCESS")) {
                    if (orderMap.get("result_code") != null
                            && orderMap.get("result_code").equalsIgnoreCase(
                            "SUCCESS")) {
                        if (orderMap.get("trade_state") != null
                                && orderMap.get("trade_state")
                                .equalsIgnoreCase("SUCCESS")) {
                            System.out.println("openid ======="
                                    + map.get("openid"));
                            // 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
                            // TcsbDeskReservationEntity tcsbOrderEntity =
                            // tcsbOrderService.findUniqueByProperty(TcsbDeskReservationEntity.class,
                            // "no", orderNumber);
                            TcsbDepositDeltaEntity tcsbDepositDeltaEntity = tcsbDepositDeltaService
                                    .findUniqueByProperty(
                                            TcsbDepositDeltaEntity.class,
                                            "orderNo", orderNumber);
                            if (tcsbDepositDeltaEntity.getStatus().equals("1")) {
                                return JsonResult.ok();
                            } else {
                                tcsbDepositDeltaEntity.setStatus(1);
                                tcsbDepositDeltaEntity.setPayMethod("0");
                                tcsbDepositDeltaService
                                        .saveOrUpdate(tcsbDepositDeltaEntity);
                                return JsonResult.ok();
                            }
                        }
                    }
                } else {
                    // 解除锁定
                    TcsbDeskReservationEntity tcsbOrderEntity = tcsbOrderService
                            .findUniqueByProperty(
                                    TcsbDeskReservationEntity.class, "no",
                                    orderNumber);
                    tcsbOrderEntity.setUniversalCouponPrice(0.0);
                    tcsbOrderEntity.setPlatformDiscountPrice(0.0);
                    tcsbOrderEntity.setSpecialCouponPrice(0.0);
                    tcsbOrderEntity.setOnlinePrice("0");
                    tcsbOrderEntity.setIsDeposit("0");
                    tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                    return JsonResult.fail("");
                }
            }
        }
        // 解除锁定
        return JsonResult.ok();
    }

    private boolean checkSign(String xmlString) {

        Map<Object, Object> map = null;

        try {

            map = XMLUtil.doXMLParse(xmlString);

        } catch (Exception e) {
            e.printStackTrace();
        }

        String signFromAPIResponse = map.get("sign").toString();

        if (signFromAPIResponse == "" || signFromAPIResponse == null) {

            System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");

            return false;

        }
        System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);

        // 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

        map.put("sign", "");

        // 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

        String signForAPIResponse = getSign(map);

        if (!signForAPIResponse.equals(signFromAPIResponse)) {

            // 签名验不过，表示这个API返回的数据有可能已经被篡改了

            System.out
                    .println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为"
                            + signForAPIResponse);

            return false;

        }

        System.out.println("恭喜，API返回的数据签名验证通过!!!");

        return true;

    }

    public String getSign(Map<Object, Object> map) {
        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
        for (Map.Entry<Object, Object> stringStringEntry : map.entrySet()) {
            signParams.put(stringStringEntry.getKey(),
                    stringStringEntry.getValue());
        }
        signParams.remove("sign");
        String sign = PayCommonUtil.createSign(signParams);
        return sign;
    }

    /**
     * 退款
     *
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping(params = "refund")
    @ResponseBody
    public AjaxJsonApi refund(HttpServletRequest request,
                              HttpServletResponse response, String orderId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        System.out.println("++++++++++++++++++++++++++++++");

        System.out.println("==============================");

		/*
		 * PageData pd = new PageData(); pd = this.getPageData();
		 */

		/*--------1.初始化数据参数----------*/
        String appId = ConfigUtil.APPLET_APPID;
        String secret = ConfigUtil.APPLET_APP_SECRECT;
        String shh = ConfigUtil.MCH_ID;
        String key = ConfigUtil.API_KEY;
        // String filePath = ConfigUtil.certificatePath;
        // //退款需要提供证书数据，所以需要根据证书路径读取证书
        // String filePath ="‪/opt/apiclientcert.p12";
        String filePath = "‪D:\\apiclientcert.p12";
        // 需要退款的商户订单号，对应提交订单中的out_trade_no
        // String orderId = "201709131011113626_1505272299478";
        // TcsbDeskReservationEntity tr =
        // tcsbOrderService.get(TcsbDeskReservationEntity.class, orderId);
        // float sessionmoney = Float.parseFloat(tr.getOnlinePrice());
        float money = 827.9f;
        String finalmoney = String.format("%.2f", money);
        finalmoney = finalmoney.replace(".", "");
        int intMoney = Integer.parseInt(finalmoney);
        // 总金额以分为单位，不带小数点
        String totalFee = String.valueOf(intMoney);
        String total_fee = totalFee; // 也可以根据业务场景从数据库中获取总金额和退款金额
        String refund_fee = totalFee;
        String orderNo = "20171227011134352453378";
        // Map<String,String> result = (Map<String, String>)
        // wxRefund(request,response,appId,secret,shh,key,tr.getNo(),total_fee,refund_fee,filePath);
        Map<String, String> result = (Map<String, String>) wxRefund(request,
                response, appId, secret, shh, key, orderNo, total_fee,
                refund_fee, filePath);
        System.out.println(result);
        System.out.println("+++++++++++++++   +++++++++++++++");
        if (result.get("returncode").equals("ok")) {
            ajaxJson.setMsg("退款申请成功");
        } else {
            ajaxJson.setMsg("退款申请失败");
        }

        System.out.println("++++++++++++++++++++++++++++++");
		/*
		 * 根据result的返回状态，处理你的业务逻辑 .....
		 */
        return ajaxJson;
    }

    private Object wxRefund(HttpServletRequest request,
                            HttpServletResponse response, String appId, String secret,
                            String shh, String key, String orderId, String total_fee,
                            String refund_fee, String path) {
        Map<String, String> result = new HashMap<String, String>();
		/*
		 * PageData pd = new PageData(); pd = this.getPageData();
		 */
        String refundid = UUIDGenerator.generate();
        String nonce_str = MD5.getMessageDigest(String.valueOf(
                new Random().nextInt(10000)).getBytes());

		/*-----  1.生成预支付订单需要的的package数据-----*/
        SortedMap<Object, Object> packageParams = new TreeMap<Object, Object>();
        packageParams.put("appid", appId);
        packageParams.put("mch_id", shh);
        packageParams.put("nonce_str", nonce_str);
        packageParams.put("op_user_id", shh);
        packageParams.put("out_trade_no", orderId);
        packageParams.put("out_refund_no", refundid);
        packageParams.put("total_fee", total_fee);
        packageParams.put("refund_fee", refund_fee);
		/*----2.根据package生成签名sign---- */

		/*
		 * SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		 * parameters.put("appid", ConfigUtil.APPID); parameters.put("mch_id",
		 * ConfigUtil.MCH_ID); parameters.put("nonce_str",
		 * PayCommonUtil.CreateNoncestr()); parameters.put("out_trade_no",
		 * orderNumber);
		 */
        String sign = PayCommonUtil.createSign(packageParams);

        // RequestHandler reqHandler = new RequestHandler(request, response);
        // reqHandler.init(appId, secret, key);
        // String sign = reqHandler.createSign(packageParams);

		/*----3.拼装需要提交到微信的数据xml---- */
        String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<mch_id>"
                + shh + "</mch_id>" + "<nonce_str>" + nonce_str
                + "</nonce_str>" + "<op_user_id>" + shh + "</op_user_id>"
                + "<out_trade_no>" + orderId + "</out_trade_no>"
                + "<out_refund_no>" + refundid + "</out_refund_no>"
                + "<refund_fee>" + refund_fee + "</refund_fee>" + "<total_fee>"
                + total_fee + "</total_fee>" + "<sign>" + sign + "</sign>"
                + "</xml>";
        try {
			/*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
            System.out.println(path.substring(1));
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(path.substring(1));
            try {
                keyStore.load(instream, shh.toCharArray());
            } finally {
                instream.close();
            }

            // 实例化密钥库
            KeyManagerFactory kmf = KeyManagerFactory
                    .getInstance(KeyManagerFactory.getDefaultAlgorithm());
            // 初始化密钥工厂
            kmf.init(keyStore, shh.toCharArray());

            // 创建SSLContext
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
            // 获取SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();

            // Trust own CA and all self-signed certs
            // SSLContext sslcontext =
            // SSLContext.custom().loadKeyMaterial(keyStore,
            // shh.toCharArray()).build();
            // Allow TLSv1 protocol only
			/*
			 * SSLConnectionSocketFactory sslsf = new
			 * SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
			 * null,
			 * SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
			 */
            // CloseableHttpClient httpClient =
            // HttpClients.custom().setSSLSocketFactory(sslsf).build();
            String requestUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
            URL url = new URL(requestUrl);
            HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            // 设置当前实例使用的SSLSocketFactory
            conn.setSSLSocketFactory(ssf);
            conn.setDoOutput(true);
            conn.setDoInput(true);

            if (null != xml) {
                OutputStream outputStream = conn.getOutputStream();
                // 注意编码格式
                outputStream.write(xml.getBytes("UTF-8"));
                outputStream.close();
            }

            // 从输入流读取返回内容
            InputStream inputStream = conn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(
                    inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(
                    inputStreamReader);
            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            // 释放资源
            bufferedReader.close();
            inputStreamReader.close();
            inputStream.close();
            inputStream = null;
            conn.disconnect();
            // return buffer.toString();

            // conn.connect();

			/*----5.发送数据到微信的退款接口---- */

            // HttpPost httpost =
            // HttpClientConnectionManager.getPostMethod(url);
            // httpost.setEntity(new StringEntity(xml, "UTF-8"));
            // HttpResponse weixinResponse = httpClient.execute(httpost);
            // String jsonStr = EntityUtils.toString(weixinResponse.getEntity(),
            // "UTF-8");
            // logger.info(jsonStr);

            // Map map = GetWxOrderno.doXMLParse(jsonStr);

            // String result2 =CommonUtil.httpsRequest(url, "POST", xml);
            System.out.println(buffer.toString());
            Map<String, String> map = XMLUtil.doXMLParse(buffer.toString());// 解析微信返回的信息，以Map形式存储便于取值
            if ("success".equalsIgnoreCase((String) map.get("return_code"))) {

                result.put("returncode", "ok");
                result.put("returninfo", "退款成功");
            } else {

                result.put("returncode", "error");
                result.put("returninfo", "退款失败");
            }
        } catch (Exception e) {
            System.out.println(e);
            result.put("returncode", "error");
            result.put("returninfo", "退款失败");
        }
        return result;

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
  //@RequestMapping(params = "couponsConfirmDeprecated")
    //@ResponseBody
    @Deprecated
    public AjaxJson couponsConfirmDeprecated(HttpServletRequest request,
                                   @RequestParam("openid") String openid,
                                   @RequestParam("orderParentId") String orderParentId)
            throws ParseException {
        AjaxJson ajaxJson = new AjaxJson();

        List<TcsbOrderItemEntity> nopayOrderItemArray = new ArrayList<>();// 订单未支付菜单项
        TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(
                TcsbOrderParentEntity.class, orderParentId);
        double frontMoney = 0.0;
        if (StringUtil.isNotEmpty(tcsbOrderParentEntity.getFrontMoney())) {
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
        List<TcsbOrderEntity> TcsbOrder = tcsbOrderService.findByProperty(
                TcsbOrderEntity.class, "orderParentId", orderParentId);
        double finalMoney = 0.0;
        double payMoney = 0.0;
        double nodisMoney = 0.0;
        boolean payState = true;
        boolean isMealFee = false;
        out:
        for (TcsbOrderEntity tcsbOrderEntity2 : TcsbOrder) {
            List<TcsbOrderItemEntity> tcsbOrderItemEntity = tcsbOrderService
                    .findByProperty(TcsbOrderItemEntity.class, "orderId",
                            tcsbOrderEntity2.getId());
            if (tcsbOrderEntity2.getPayStatus().equals("1")) {
                for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                    double addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount(), tcsbOrderItemEntity2.getPrice());
                    finalMoney = BigDecimalUtil.add(finalMoney, addmoney);
                }
                isMealFee = true;
            } else {
                for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                    TcsbFoodEntity tf = tcsbOrderService.get(TcsbFoodEntity.class, tcsbOrderItemEntity2.getFoodId());
                    double addmoney = 0.0D;
                    if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getStandardId())) {
                        TcsbFoodStandardEntity tfs = tcsbOrderService.get(TcsbFoodStandardEntity.class, tcsbOrderItemEntity2.getStandardId());
                        if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())) {
                            addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount() - tcsbOrderItemEntity2.getRetreatNum(), tfs.getPrice());
                        } else {
                            addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount(), tfs.getPrice());
                        }

                    } else {
                        if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())) {
                            addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount() - tcsbOrderItemEntity2.getRetreatNum(), tf.getPrice());
                        } else {
                            addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount(), tf.getPrice());
                        }

                    }
                    finalMoney = BigDecimalUtil.add(finalMoney, addmoney);
                    payMoney = BigDecimalUtil.add(payMoney, addmoney);
                    if (tf.getIsDis().equals("N")) {
                        nodisMoney = BigDecimalUtil.add(nodisMoney, addmoney);
                    }
                    nopayOrderItemArray.add(tcsbOrderItemEntity2);
                    if (tcsbOrderItemEntity2.getIsUpdatePrice() == 0) {
                        payState = false;
                        break out;
                    }

                }
                tcsbOrderEntity2.setTotalPrice(payMoney);
            }
        }
        if (payState) {

            if (tcsbOrderParentEntity.getOrderLock().equals("0")) {
                try {
                    String hql = "from TcsbOrderEntity where orderParentId='"
                            + orderParentId + "' and payStatus='0'";
                    TcsbOrderEntity tcsbOrderEntity = tcsbOrderService
                            .singleResult(hql);
                    if (StringUtil.isNotEmpty(tcsbOrderEntity)) {
                        // 锁定订单
                        tcsbOrderParentEntity.setOrderLock("1");
                        tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
                        // ==========================================
                        payMoney = tcsbOrderEntity.getTotalPrice();
                        // 检测是否使用过优惠券===start===
                        boolean universalCoupon = false;
                        boolean specialCoupon = false;
                        boolean platformDiscount = false;
                        // 检测是否使用过店铺活动===start===
                        // 检测是否使用过优惠券===start===
                        // 检测是否使用过平台优惠券===start===
                        for (TcsbOrderEntity tcsbOrderEntity2 : TcsbOrder) {
                            if (tcsbOrderEntity2.getPayStatus().equals("1")) {
                                if (tcsbOrderEntity2.getSpecialCouponPrice() > 0) {
                                    specialCoupon = true;
                                }
                            }

                            if (tcsbOrderEntity2.getPayStatus().equals("1")) {
                                if (tcsbOrderEntity2.getUniversalCouponPrice() > 0) {
                                    universalCoupon = true;

                                }
                            }

                            if (tcsbOrderEntity2.getPlatformDiscountPrice() > 0) {
                                platformDiscount = true;//平台优惠
                            }

                            if (specialCoupon && universalCoupon && platformDiscount) {
                                break;
                            }

                        }
                        // 检测是否使用过店铺活动===end===
                        // 检测是否使用过优惠券===end===
                        // 检测是否使用过平台优惠券===end===

                        //查询是否有餐位费
                        TcsbMealFeeEntity tcsbMealFeeEntity = tcsbOrderService.findUniqueByProperty(TcsbMealFeeEntity.class, "shopId", tcsbOrderParentEntity.getShopId());
                        if (StringUtil.isNotEmpty(tcsbMealFeeEntity)) {
                            //是否交过餐位费
                            double mealFee = BigDecimalUtil.mul(tcsbOrderParentEntity.getPeople(), tcsbMealFeeEntity.getMealFee());
                            finalMoney = BigDecimalUtil.add(finalMoney, mealFee);
                            if (!isMealFee) {
                                //未交
                                payMoney = BigDecimalUtil.add(payMoney, mealFee);
                            }
                        }
                        //查询是否有餐位费====end=====


                        double discountMoney = BigDecimalUtil.sub(finalMoney,
                                nodisMoney);
                        double universalCouponMoney = discountMoney;// 用户总额减去(不参与)(活动)后的金额，用来判断是否满足优惠券满减
                        if (!specialCoupon) {
                            // 获取优惠活动
                            List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbOrderService
                                    .findByProperty(TcsbDiscountActivityEntity.class, "shopId",
                                            tcsbOrderEntity.getShopId());
                            if (!tcsbDiscountActivityEntities.isEmpty()) {
                                List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                                for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                                    TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbOrderService.get(
                                            TcsbShopFullcutTemplateEntity.class,
                                            tcsbDiscountActivityEntity.getFullcutTemplateId());
                                    tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                                }
                                if (!tcsbDiscountActivityEntities.isEmpty()) {
                                    ListSorter.sort(tcsbShopFullcutTemplateEntities, "total", "discount");
                                    for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                                        if (discountMoney >= tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
                                            payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(
                                                    tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                                            universalCouponMoney = BigDecimalUtil.sub(universalCouponMoney,
                                                    Double.parseDouble(
                                                            tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                                            if (payMoney < 0) {
                                                payMoney = 0.01;
                                            }
                                            tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(
                                                    tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                                            // tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                                            break;
                                        }
                                    }
                                }
                            }
                        }

                        if (!universalCoupon) {
                            List<TcsbCouponEntity> tcsbCouponEntities = tcsbOrderService
                                    .findHql(
                                            "from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?",
                                            tcsbOrderEntity.getShopId(), openid);
                            List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
                            if (!tcsbCouponEntities.isEmpty()) {
                                for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                                    TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbOrderService
                                            .get(TcsbFullcutTemplateEntity.class,
                                                    tcsbCouponEntity
                                                            .getFullcutTemplateId());
                                    Integer userPeriod = tcsbFullcutTemplateEntity
                                            .getUsePeriod();// 使用期限
                                    String dateUnit = tcsbFullcutTemplateEntity
                                            .getDateUnit(); // 日期单位
                                    // 专用券中有效的满减活动
                                    Calendar calDes = null;
                                    try {
                                        calDes = DateUtils
                                                .parseCalendar(
                                                        DateUtils
                                                                .date2Str(
                                                                        tcsbCouponEntity
                                                                                .getExpiryDate(),
                                                                        DateUtils.datetimeFormat),
                                                        "yyyy-MM-dd HH:mm:ss");
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    Calendar calSrc = null;
                                    try {
                                        calSrc = DateUtils
                                                .parseCalendar(
                                                        DateUtils
                                                                .date2Str(
                                                                        new Date(),
                                                                        DateUtils.datetimeFormat),
                                                        "yyyy-MM-dd HH:mm:ss");
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }
                                    if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                                        tcsbFullcutTemplateEntity
                                                .setCouponId(tcsbCouponEntity
                                                        .getId());
                                        tcsbFullcutTemplateEntities
                                                .add(tcsbFullcutTemplateEntity);
                                    }
                                    ;
                                }
                            }
                            // 立减
                            if (!tcsbFullcutTemplateEntities.isEmpty()) {
                                ListSorter.sort(tcsbFullcutTemplateEntities,
                                        "total", "discount");
                                outer:
                                for (int i = tcsbFullcutTemplateEntities
                                        .size() - 1; i >= 0; i--) {
                                    TcsbCouponEntity tcsbCouponEntity = tcsbOrderService
                                            .get(TcsbCouponEntity.class,
                                                    tcsbFullcutTemplateEntities
                                                            .get(i)
                                                            .getCouponId());
                                    if (tcsbCouponEntity.getUseRange().equals(
                                            "0")) {
                                        if (universalCouponMoney >= tcsbFullcutTemplateEntities
                                                .get(i).getTotal()) {
                                            payMoney = BigDecimalUtil
                                                    .sub(payMoney,
                                                            Double.parseDouble(tcsbFullcutTemplateEntities
                                                                    .get(i)
                                                                    .getDiscount()
                                                                    + ""));
                                            if (payMoney < 0) {
                                                payMoney = 0.01;
                                            }
                                            tcsbOrderEntity
                                                    .setUniversalCouponPrice(Double
                                                            .valueOf(tcsbFullcutTemplateEntities
                                                                    .get(i)
                                                                    .getDiscount()
                                                                    + ""));
                                            tcsbOrderEntity
                                                    .setUniversalCoupon(tcsbFullcutTemplateEntities
                                                            .get(i)
                                                            .getCouponId());
                                            break outer;
                                        }
                                    } else {
                                        // 查找订单项中是否有该道菜
                                        TcsbFoodEntity tcsbFoodEntity = tcsbOrderService
                                                .get(TcsbFoodEntity.class,
                                                        tcsbCouponEntity
                                                                .getFoodId());
                                        for (int j = 0; j < nopayOrderItemArray
                                                .size(); j++) {
                                            if (tcsbFoodEntity.getId().equals(
                                                    nopayOrderItemArray.get(j)
                                                            .getFoodId())) {
                                                payMoney = BigDecimalUtil
                                                        .sub(payMoney,
                                                                Double.parseDouble(tcsbFullcutTemplateEntities
                                                                        .get(i)
                                                                        .getDiscount()
                                                                        + ""));
                                                if (payMoney < 0) {
                                                    payMoney = 0.01;
                                                }
                                                tcsbOrderEntity
                                                        .setUniversalCouponPrice(Double
                                                                .valueOf(tcsbFullcutTemplateEntities
                                                                        .get(i)
                                                                        .getDiscount()
                                                                        + ""));
                                                tcsbOrderEntity
                                                        .setUniversalCoupon(tcsbFullcutTemplateEntities
                                                                .get(i)
                                                                .getCouponId());
                                                break outer;
                                            }
                                        }
                                    }
                                }

                            }
                        }


                        if (!platformDiscount) {
                            CriteriaQuery cq = new CriteriaQuery(TcsbPlatformCouponEntity.class);
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
                            cq.eq("userId", openid);
                            cq.add();
                            List<TcsbPlatformCouponEntity> tcsbPlatformCouponList = tcsbOrderService.getListByCriteriaQuery(cq, false);
                            List<TcsbPlatformFullcutTemplateEntity> tcsbPlatformFullcutTemplateList = new ArrayList<>();
                            for (TcsbPlatformCouponEntity tcsbPlatformCouponEntity : tcsbPlatformCouponList) {
                                TcsbPlatformFullcutTemplateEntity tcsbPlatformFullcutTemplate = tcsbOrderService.get(TcsbPlatformFullcutTemplateEntity.class, tcsbPlatformCouponEntity.getFullcutTemplateId());
                                tcsbPlatformFullcutTemplateList.add(tcsbPlatformFullcutTemplate);
                            }


                            if (tcsbPlatformCouponList.size() > 0) {
                                boolean hasCut = false;
                                ListSorter.sort(tcsbPlatformFullcutTemplateList, "total", "discount");
                                for (int i = tcsbPlatformFullcutTemplateList.size() - 1; i >= 0; i--) {
                                    if (universalCouponMoney >= tcsbPlatformFullcutTemplateList.get(i).getTotal()) {
                                        payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbPlatformFullcutTemplateList.get(i).getDiscount() + ""));
                                        if (payMoney < 0) {
                                            payMoney = 0.01;
                                        }
                                        tcsbOrderEntity.setPlatformDiscountPrice(Double.valueOf(tcsbPlatformFullcutTemplateList.get(i).getDiscount() + ""));
                                        tcsbOrderEntity.setPlatformDiscount(tcsbPlatformFullcutTemplateList.get(i).getId());
                                        hasCut = true;
                                        break;
                                    }
                                }

                                if (!hasCut) {
                                    tcsbOrderEntity.setPlatformDiscountPrice(0.0);
                                    tcsbOrderEntity.setPlatformDiscount(null);
                                }
                            }
                        }


                        // VIP折扣
                        // 获取用户会员信息
                        String viphql = "from TcsbMemberUserEntity where openid='" + openid + "' and shopId='" + tcsbOrderEntity.getShopId() + "'";
                        TcsbMemberUserEntity tm = tcsbOrderService.singleResult(viphql);
                        if (StringUtil.isNotEmpty(tm)) {
                            // 获取会员详情
                            // TcsbMembershipLevelEntity tmship =
                            // tcsbOrderService.get(TcsbMembershipLevelEntity.class,
                            // tm.getMembershipLevelId());
                            TcsbMemberLevelEquityEntity tmle = tcsbOrderService.findUniqueByProperty(TcsbMemberLevelEquityEntity.class,
                                    "membershipLevelId", tm.getMembershipLevelId());
                            if (tmle.getIsDiscount().equals("1")) {
                                String discount = BigDecimalUtil.divide(tmle.getDiscount() + "", 10 + "", 2);
                                if (payMoney > 0) {
                                    double needDisMoney = BigDecimalUtil.sub(payMoney, nodisMoney);
                                    double vippayMoney = BigDecimalUtil.mul(needDisMoney, Double.valueOf(discount));
                                    double vipdisMoney = BigDecimalUtil.sub(payMoney, vippayMoney);
                                    if (payMoney < 0) {
                                        payMoney = 0.01;
                                    } else {
                                        payMoney = vippayMoney;
                                    }
                                    tcsbOrderEntity.setVipDiscountPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(
                                            vipdisMoney, 2)));
                                }
                            }
                        }

                        String body = "点单么平台";
                        payMoney = BigDecimalUtil.sub(payMoney, frontMoney);
                        String money = payMoney + "";// 获取订单金额
                        // 保留两位小数位
                        money = BigDecimalUtil
                                .numericRetentionDecimal(money, 2);
                        // 金额转化为分为单位
                        float sessionmoney = Float.parseFloat(money);
                        String finalmoney = String.format("%.2f", sessionmoney);
                        finalmoney = finalmoney.replace(".", "");
                        // 商户订单号
                        String out_trade_no = OrderNumberGenerateUtil
                                .getOrderNumber() + RandomUtils.generateNum(5);// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。

                        tcsbOrderEntity.setOrderNo(out_trade_no);
                        tcsbOrderService.saveOrUpdate(tcsbOrderEntity);

                        int intMoney = Integer.parseInt(finalmoney);
                        // 总金额以分为单位，不带小数点
                        String totalFee = String.valueOf(intMoney);
                        String ipAddress = request.getRemoteAddr();
                        String prepayId;
                        SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
                        try {
                            prepayId = WxPayUtil.getAppletPrepayId(totalFee,
                                    ipAddress, out_trade_no, body, openid);
                            // 2 给H5页面传递参数 调起微信支付
                            signParams = WxPayUtil
                                    .createAppletPackageValue(prepayId);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }

                        ajaxJson.setObj(signParams);
                        ajaxJson.setSuccess(true);
                    } else {
                        ajaxJson.setMsg("没有未支付的订单");
                        ajaxJson.setObj(null);
                        ajaxJson.setSuccess(false);
                    }
                } catch (Exception e) {
                    // 解除锁定订单
                    tcsbOrderParentEntity.setOrderLock("0");
                    tcsbOrderService.saveOrUpdate(tcsbOrderParentEntity);
                }

            } else {
                ajaxJson.setMsg("订单正在支付中");
                ajaxJson.setObj(null);
                ajaxJson.setSuccess(false);
            }
        } else {
            ajaxJson.setMsg("存在未接单时价商品");
            ajaxJson.setObj(null);
            ajaxJson.setSuccess(false);
        }
        return ajaxJson;
    }
    
    
    

}
