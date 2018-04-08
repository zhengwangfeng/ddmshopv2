package com.pc.pay.service.impl;

import com.pc.pay.service.TcsbPCPayServiceI;
import com.pc.pay.vo.TcsbPCOrderItemVO;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsbretreatfood.entity.TcsbRetreatFoodEntity;
import com.tcsb.tcsbretreatorder.entity.TcsbRetreatOrderEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.weixin.core.util.ConfigUtil;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.WxPayUtil;
import com.weixin.core.util.XMLUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.*;

@Service("tcsbPCPayService")
@Transactional
public class TcsbPCPayServiceImpl extends CommonServiceImpl implements TcsbPCPayServiceI {
    private static final Logger logger = Logger.getLogger(TcsbPCPayServiceImpl.class);

    @Override
    public Double countOrderTotalMoney(String orderId) throws ParseException {
        List<TcsbOrderItemEntity> nopayOrderItemArray = new ArrayList<>();//订单未支付菜单项

        TcsbOrderEntity TcsbOrder = get(TcsbOrderEntity.class, orderId);
        TcsbOrderParentEntity tcsbOrderParentEntity = get(TcsbOrderParentEntity.class, TcsbOrder.getOrderParentId());
        double finalMoney = 0.0;
        double payMoney = 0.0;
        double nodisMoney = 0.0;
        boolean payState = true;

        List<TcsbOrderItemEntity> tcsbOrderItemEntity = findByProperty(TcsbOrderItemEntity.class, "orderId", orderId);
        if (TcsbOrder.getPayStatus().equals("1")) {
            //List<TcsbOrderItemEntity> tcsbOrderItemEntity = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
            for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                double addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount() - tcsbOrderItemEntity2.getRetreatNum(), tcsbOrderItemEntity2.getPrice());
                finalMoney = BigDecimalUtil.add(finalMoney, addmoney);
            }
        } else {
            for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
                TcsbFoodEntity tf = get(TcsbFoodEntity.class, tcsbOrderItemEntity2.getFoodId());
                double addmoney = 0.0D;
                if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getStandardId())) {
                    TcsbFoodStandardEntity tfs = get(TcsbFoodStandardEntity.class, tcsbOrderItemEntity2.getStandardId());
                    addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount(), tfs.getPrice());
                } else {
                    addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount(), tf.getPrice());
                }
                finalMoney = BigDecimalUtil.add(finalMoney, addmoney);
                payMoney = BigDecimalUtil.add(payMoney, addmoney);
                if (tf.getIsDis().equals("N")) {
                    nodisMoney = BigDecimalUtil.add(nodisMoney, addmoney);
                }
                nopayOrderItemArray.add(tcsbOrderItemEntity2);
                if (tcsbOrderItemEntity2.getIsUpdatePrice() == 0) {
                    payState = false;
                }

            }
        }
        //finalMoney = BigDecimalUtil.add(finalMoney, tcsbOrderEntity2.getTotalPrice());

        if (payState) {

            //==========================================
            payMoney = TcsbOrder.getTotalPrice();
            //检测是否使用过优惠券===start===
            boolean universalCoupon = false;
            boolean specialCoupon = false;

            //检测是否使用过优惠券===end===


            double discountMoney = BigDecimalUtil.sub(finalMoney, nodisMoney);
            double universalCouponMoney = discountMoney;//用户总额减去(不参与)(活动)后的金额，用来判断是否满足优惠券满减
            if (!specialCoupon) {
                //获取优惠活动
                List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = findByProperty(TcsbDiscountActivityEntity.class, "shopId", TcsbOrder.getShopId());
                if (!tcsbDiscountActivityEntities.isEmpty()) {
                    List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
                    for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                        TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
                        tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                    }
                    if (!tcsbDiscountActivityEntities.isEmpty()) {
                        ListSorter.sort(tcsbShopFullcutTemplateEntities, "total", "discount");
                        for (int i = tcsbShopFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                            if (discountMoney >= tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
                                payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                                universalCouponMoney = BigDecimalUtil.sub(universalCouponMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                                if (payMoney < 0) {
                                    payMoney = 0.01;
                                }
                                TcsbOrder.setSpecialCouponPrice(Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount() + ""));
                                //tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
                                break;
                            }
                        }
                    }
                }
            }

            TcsbUserOrderEntity tcsbUserOrderEntity = findUniqueByProperty(TcsbUserOrderEntity.class, "orderParentId", tcsbOrderParentEntity.getId());
            List<TcsbCouponEntity> tcsbCouponEntities = null;
            if (tcsbUserOrderEntity != null) {
                tcsbCouponEntities = findHql("from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?", TcsbOrder.getShopId(), tcsbUserOrderEntity.getUserId());
            }

            List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
            if (!tcsbCouponEntities.isEmpty()) {
                for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
                    TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
                    Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
                    String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
                    //专用券中有效的满减活动
                    Calendar calDes = null;

                    calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");

                    Calendar calSrc = null;

                    calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd HH:mm:ss");

                    if (DateUtils.dateDiff('s', calSrc, calDes) < 0) {
                        tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
                        tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
                    }
                }
            }
            //立减
            if (!tcsbFullcutTemplateEntities.isEmpty()) {
                ListSorter.sort(tcsbFullcutTemplateEntities, "total", "discount");
                outer:
                for (int i = tcsbFullcutTemplateEntities.size() - 1; i >= 0; i--) {
                    TcsbCouponEntity tcsbCouponEntity = get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
                    if (tcsbCouponEntity != null) {
                        if (tcsbCouponEntity.getUseRange().equals("0")) {
                            if (universalCouponMoney >= tcsbFullcutTemplateEntities.get(i).getTotal()) {
                                payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                                if (payMoney < 0) {
                                    payMoney = 0.01;
                                }
                                TcsbOrder.setUniversalCouponPrice(Double.valueOf(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                                TcsbOrder.setUniversalCoupon(tcsbFullcutTemplateEntities.get(i).getCouponId());
                                break outer;
                            }
                        } else {
                            //查找订单项中是否有该道菜
                            TcsbFoodEntity tcsbFoodEntity = get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
                            for (int j = 0; j < nopayOrderItemArray.size(); j++) {
                                if (tcsbFoodEntity.getId().equals(nopayOrderItemArray.get(j).getFoodId())) {
                                    payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                                    if (payMoney < 0) {
                                        payMoney = 0.01;
                                    }
                                    TcsbOrder.setUniversalCouponPrice(Double.valueOf(tcsbFullcutTemplateEntities.get(i).getDiscount() + ""));
                                    TcsbOrder.setUniversalCoupon(tcsbFullcutTemplateEntities.get(i).getCouponId());
                                    break outer;
                                }
                            }
                        }
                    }
                }
            }

            //VIP折扣
            //获取用户会员信息
            String viphql = "from TcsbMemberUserEntity where openid='" + tcsbUserOrderEntity.getUserId() + "' and shopId='" + TcsbOrder.getShopId() + "'";
            TcsbMemberUserEntity tm = singleResult(viphql);
            if (tm != null) {
                //获取会员详情
                //TcsbMembershipLevelEntity tmship = tcsbOrderService.get(TcsbMembershipLevelEntity.class, tm.getMembershipLevelId());
                TcsbMemberLevelEquityEntity tmle = findUniqueByProperty(TcsbMemberLevelEquityEntity.class, "membershipLevelId", tm.getMembershipLevelId());
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
                        TcsbOrder.setVipDiscountPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(vipdisMoney, 2)));
                    }
                }
            }
        }
        return finalMoney;
    }

    /**
     * 退菜品
     *
     * @param itemVOList
     * @param reason
     * @return
     */
    @Override
    public List<AjaxJsonApi> refundFoodItem(List<TcsbPCOrderItemVO> itemVOList, String reason) throws Exception {
        List<AjaxJsonApi> resultList = new ArrayList<>();
        Set<String> orderSet = new HashSet<>();
        for (TcsbPCOrderItemVO tcsbPCOrderItemVO : itemVOList) {
            TcsbOrderItemEntity tcsbOrderItemEntity = get(TcsbOrderItemEntity.class, tcsbPCOrderItemVO.getOrderItemId());
            TcsbOrderEntity tcsbOrderEntity = get(TcsbOrderEntity.class, tcsbOrderItemEntity.getOrderId());
            orderSet.add(tcsbOrderItemEntity.getOrderId());
            if (tcsbOrderEntity.getPayStatus().equals("1")) {
                if (tcsbOrderItemEntity != null) {
                    long count = (long) (tcsbOrderItemEntity.getCount() - tcsbOrderItemEntity.getRetreatNum() - Integer.parseInt(tcsbPCOrderItemVO.getCount()));
                    if (count < 0) {
                        AjaxJsonApi ajaxJson = new AjaxJsonApi();
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg(tcsbOrderItemEntity.getFoodName() + ":退款数量大于订单数量");
                        resultList.add(ajaxJson);
                    }
                    if (count >= 0) {
                        tcsbOrderItemEntity.setRetreatNum(Integer.parseInt(tcsbPCOrderItemVO.getCount()) + tcsbOrderItemEntity.getRetreatNum());
                        saveOrUpdate(tcsbOrderItemEntity);
                        //记录退菜信息
                        TcsbRetreatFoodEntity tcsbRetreatFoodEntity = new TcsbRetreatFoodEntity();
                        tcsbRetreatFoodEntity.setOrderId(tcsbOrderItemEntity.getOrderId());
                        tcsbRetreatFoodEntity.setChargebackReason(reason);
                        if (tcsbOrderEntity != null) {
                            tcsbRetreatFoodEntity.setShopId(tcsbOrderEntity.getShopId());
                        }
                        tcsbRetreatFoodEntity.setFoodId(tcsbOrderItemEntity.getFoodId());
                        tcsbRetreatFoodEntity.setNum(Integer.parseInt(tcsbPCOrderItemVO.getCount()));
                        tcsbRetreatFoodEntity.setCreateDate(new Date());
                        save(tcsbRetreatFoodEntity);
                        AjaxJsonApi ajaxJson = new AjaxJsonApi();
                        ajaxJson.setSuccess(true);
                        ajaxJson.setMsg(tcsbOrderItemEntity.getFoodName() + "退菜成功");
                        resultList.add(ajaxJson);
                    }
                }
            } else if (tcsbOrderEntity.getPayStatus().equals("0")) {
                AjaxJsonApi ajaxJson = new AjaxJsonApi();
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("订单未付款");
                resultList.add(ajaxJson);
            }
        }
        for (String orderId : orderSet) {
            Double totalFee = 0.0;
            Double totalMoney = countOrderTotalMoney(orderId);
            TcsbOrderEntity tcsbOrderEntity = get(TcsbOrderEntity.class, orderId);
            if (tcsbOrderEntity != null) {
                //已支付菜品
                if (tcsbOrderEntity.getPayStatus().equals("1")) {
                    totalFee = tcsbOrderEntity.getTotalPrice() - totalMoney;
                    tcsbOrderEntity.setTotalPrice(totalMoney);
                    saveOrUpdate(tcsbOrderEntity);
                }
                if (totalFee > 0) {
                    Map<String, String> result = reFund(tcsbOrderEntity.getOrderNo(), totalFee);
//                    resultList.add(addMapResultToAjaxJsonApi(result, tcsbOrderEntity.getId()));
                }
                updateOrderStatus(tcsbOrderEntity);
            }

        }

        return resultList;
    }

    public void updateOrderStatus(TcsbOrderEntity tcsbOrderEntity) {
        List<TcsbOrderItemEntity> itemList = findHql("from TcsbOrderItemEntity where orderId='" + tcsbOrderEntity.getId() + "'");
        boolean needUpdate = true;
        for (TcsbOrderItemEntity item : itemList) {
            if (item.getRetreatNum() < item.getCount()) {
                needUpdate = false;
            }
        }
        if (needUpdate) {
            tcsbOrderEntity.setPayStatus("2");
            saveOrUpdate(tcsbOrderEntity);
        }
    }

    /**
     * 退订单
     *
     * @param orderId
     * @param reason
     * @return
     */
    @Override
    public AjaxJsonApi refundOrder(String orderId, String reason) throws Exception {
        TcsbOrderEntity tcsbOrderEntity = get(TcsbOrderEntity.class, orderId);
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        if (tcsbOrderEntity != null) {
            //已支付
            if (tcsbOrderEntity.getPayStatus().equals("1")) {
                //线下
                if (tcsbOrderEntity.getPayMethod().equals("1")) {
                    tcsbOrderEntity.setPayStatus("2");
                    saveOrUpdate(tcsbOrderEntity);
                } else if (tcsbOrderEntity.getPayMethod().equals("0")) {
                    ajaxJson = refundOneOrder(orderId, reason);
                }
            } else if (tcsbOrderEntity.getPayStatus().equals("0")) {
                tcsbOrderEntity.setPayStatus("2");
                saveOrUpdate(tcsbOrderEntity);
            }
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("子订单不存在");
        }
        return ajaxJson;
    }

    /**
     * 退一个订单
     *
     * @param orderId
     * @param reason
     * @return
     */
    public AjaxJsonApi refundOneOrder(String orderId, String reason) throws Exception {
        TcsbOrderEntity tcsbOrderEntity = get(TcsbOrderEntity.class, orderId);
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        if (tcsbOrderEntity != null) {
            Map<String, String> result = reFund(tcsbOrderEntity.getOrderNo());
            if (result.get("returncode").equals("SUCCESS")) {
                TcsbRetreatOrderEntity tcsbRetreatOrderEntity = new TcsbRetreatOrderEntity();
                tcsbRetreatOrderEntity.setChargebackReason(reason);
                tcsbRetreatOrderEntity.setOrderId(tcsbOrderEntity.getId());
                tcsbRetreatOrderEntity.setShopId(tcsbOrderEntity.getShopId());
                tcsbRetreatOrderEntity.setCreateDate(new Date());
                save(tcsbRetreatOrderEntity);
                tcsbOrderEntity.setPayStatus("2");
                List<TcsbOrderItemEntity> orderItemEntities = findHql("from TcsbOrderItemEntity where orderId='" + tcsbOrderEntity.getId() + "'");
                for (TcsbOrderItemEntity entity : orderItemEntities) {
                    entity.setRetreatNum(entity.getCount().intValue());
                    saveOrUpdate(entity);
                }
                ajaxJson.setSuccess(true);
                ajaxJson.setMsg("退款成功");
                ajaxJson.setObj(tcsbOrderEntity.getId());
                saveOrUpdate(tcsbOrderEntity);
            } else if (result.get("returncode").equals("FAIL")) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg(result.get("returninfo"));
                ajaxJson.setObj(tcsbOrderEntity.getId());
            }
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("订单号查无订单");
        }
        return ajaxJson;
    }


    /**
     * 退总订单
     *
     * @param orderParentId
     * @param reason
     * @return
     * @throws Exception
     */
    @Override
    public AjaxJsonApi refundOrderParent(String orderParentId, String reason) throws Exception {
        TcsbOrderParentEntity tcsbOrderParentEntity = get(TcsbOrderParentEntity.class, orderParentId);
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        //订单存在
        if (tcsbOrderParentEntity != null) {
            List<TcsbOrderEntity> orderList = findHql("from TcsbOrderEntity where shopId='" + tcsbOrderParentEntity.getShopId() + "' and orderParentId='" + tcsbOrderParentEntity.getId() + "'");

            if (orderList != null && orderList.size() > 0) {
                boolean refundSuccess = true;
                for (TcsbOrderEntity orderEntity : orderList) {
                    //已支付
                    if (orderEntity.getPayStatus().equals("1")) {
                        //线下
                        if (orderEntity.getPayMethod().equals("1")) {
                            orderEntity.setPayStatus("2");
                            saveOrUpdate(orderEntity);
                            //线上
                        } else if (orderEntity.getPayMethod().equals("0")) {
                            AjaxJsonApi ajaxJson = refundOneOrder(orderEntity.getId(), reason);
                            if (ajaxJson.isSuccess() == false) {
                                refundSuccess = false;
                                ajaxJsonApi.setSuccess(false);
                                ajaxJsonApi.setMsg(ajaxJson.getMsg());
                                break;
                            }

                        }
                    } else if (orderEntity.getPayStatus().equals("0")) {
                        orderEntity.setPayStatus("2");
                        saveOrUpdate(orderEntity);
                    }

                }
                if (refundSuccess) {
                    //清台
                    tcsbOrderParentEntity.setOrderStatus("0");
                    saveOrUpdate(tcsbOrderParentEntity);
                    //桌位改空闲
                    TcsbDeskEntity tcsbDeskEntity = get(TcsbDeskEntity.class, tcsbOrderParentEntity.getDeskId());
                    tcsbDeskEntity.setStatus("0");
                    saveOrUpdate(tcsbDeskEntity);

                    ajaxJsonApi.setSuccess(true);
                    ajaxJsonApi.setMsg("退总单成功");
                }

//            AjaxJsonApi ajaxJson = refundDepositMoney(request, response, tcsbOrderParentEnttity);
//            resultList.add(ajaxJson);
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("总订单号不存在");
        }

        return ajaxJsonApi;
    }

    /**
     * 退预订单
     *
     * @param deskReservationId
     * @param reason
     * @return
     * @throws Exception
     */
    @Override
    public AjaxJsonApi refundDeskReservation(String deskReservationId, String reason) throws Exception {
        TcsbDeskReservationEntity tcsbDeskReservationEntity = get(TcsbDeskReservationEntity.class, deskReservationId);
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        //预约订单存在
        if (tcsbDeskReservationEntity != null) {
            //已支付
            if (tcsbDeskReservationEntity.getPayStatus().equals("1")) {
                //暂时默认微信支付
                if (tcsbDeskReservationEntity.getNo() != null) {
                    Map<String, String> result = reFund(tcsbDeskReservationEntity.getNo());

                    if (result.get("returncode").equals("SUCCESS")) {
                        tcsbDeskReservationEntity.setPayStatus("3");
                        saveOrUpdate(tcsbDeskReservationEntity);
                        ajaxJsonApi.setSuccess(true);
                        ajaxJsonApi.setMsg("退预约单成功");
                    } else if (result.get("returncode").equals("FAIL")) {
                        ajaxJsonApi.setSuccess(false);
                        ajaxJsonApi.setMsg(result.get("returninfo"));
                        return ajaxJsonApi;
                    }
                }


            }
            //有押金
            if (tcsbDeskReservationEntity.getIsDeposit().equals("1")) {
                if (StringUtils.isNotEmpty(tcsbDeskReservationEntity.getDepositDeltaId())) {
                    ajaxJsonApi = refundDepositMoney(tcsbDeskReservationEntity.getDepositDeltaId());
                    if (!ajaxJsonApi.isSuccess()) {
                        return ajaxJsonApi;
                    }
                }
                //没押金
            }
            tcsbDeskReservationEntity.setStatus("0");
            this.saveOrUpdate(tcsbDeskReservationEntity);
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("预约订单不存在");
        }

        return ajaxJsonApi;
    }

//    public AjaxJsonApi addMapResultToAjaxJsonApi(Map<String, String> result, Object obj) {
//        AjaxJsonApi ajaxJson = new AjaxJsonApi();
//        if (result.get("returncode").equals("SUCCESS")) {
//            ajaxJson.setSuccess(true);
//            ajaxJson.setMsg("退菜成功");
//        } else if (result.get("returncode").equals("FAIL")) {
//            ajaxJson.setSuccess(false);
//            ajaxJson.setObj(obj);
//            ajaxJson.setMsg(result.get("returninfo"));
//        }
//        return ajaxJson;
//    }

    /**
     * 退押金
     *
     * @param depositDeltaId
     * @return
     */
    @Override
    public AjaxJsonApi refundDepositMoney(String depositDeltaId) throws Exception {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        TcsbDepositDeltaEntity tcsbDepositDeltaEntity = get(TcsbDepositDeltaEntity.class, depositDeltaId);
        if (tcsbDepositDeltaEntity != null) {
            //支付回调结果状态
            if (tcsbDepositDeltaEntity.getStatus().toString().equals("1")) {
                //线下支付
                if (StringUtils.isNotEmpty(tcsbDepositDeltaEntity.getPayMethod()) && tcsbDepositDeltaEntity.getPayMethod().equals("1")) {
                    ajaxJson.setSuccess(true);
                    ajaxJson.setMsg("请退线下支付押金:" + tcsbDepositDeltaEntity.getReserveDeposit());
                } else {
                    //暂时微信支付
                    Map<String, String> result = reFund(tcsbDepositDeltaEntity.getOrderNo());
                    if (result.get("returncode").equals("SUCCESS")) {

                        ajaxJson.setSuccess(true);
                        ajaxJson.setMsg("退押金成功");
                    } else if (result.get("returncode").equals("FAIL")) {
                        ajaxJson.setSuccess(false);
                        ajaxJson.setMsg(result.get("returninfo"));
                    }
                }
            }
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("查无押金记录");
        }

        return ajaxJson;
    }

//    @Override
//    public AjaxJsonApi refundDepositMoneyOnly(HttpServletRequest request, HttpServletResponse response,TcsbDepositDeltaEntity tcsbDepositDeltaEntity) {
//        AjaxJsonApi ajaxJson = new AjaxJsonApi();
//        try {
//            ajaxJson = refundDepositMoney(request, response, tcsbDepositDeltaEntity);
//        } catch (Exception e) {
//            ajaxJson.setSuccess(false);
//            ajaxJson.setMsg("系统异常");
//            logger.error(e);
//            this.getSession().getTransaction().rollback();
//        }
//        return ajaxJson;
//    }


    @Override
    public Map<String, String> reFund(String orderNo, Double totalFee) throws Exception {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        Map<String, String> orderMap = null;
        orderMap = WxPayUtil.queryWeiXinOrder(orderNo);
        Map<String, String> result = null;
        if (orderMap.get("return_code") != null && orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
            if (orderMap.get("result_code") != null && orderMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                String appId = ConfigUtil.APPLET_APPID;
                String secret = ConfigUtil.APPLET_APP_SECRECT;
                String shh = ConfigUtil.MCH_ID;
                String key = ConfigUtil.API_KEY;
//                String filePath = "//opt/apiclientcert.p12";
                String filePath = "‪D:\\apiclientcert.p12";
                String finalmoney = null;
                finalmoney = String.format("%.2f", totalFee);
                finalmoney = finalmoney.replace(".", "");
                int intMoney = Integer.parseInt(finalmoney);
                // 总金额以分为单位，不带小数点
                result = (Map<String, String>) wxRefund(appId, secret, shh, key, orderNo, orderMap.get("total_fee"), String.valueOf(intMoney), filePath);
            }
        }
        return result;
    }


    public Map<String, String> reFund(String orderNo) throws Exception {
        Map<String, String> orderMap = null;
        orderMap = WxPayUtil.queryWeiXinOrder(orderNo);
        Map<String, String> result = null;
        if (orderMap.get("return_code") != null && orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
            if (orderMap.get("result_code") != null && orderMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
                String appId = ConfigUtil.APPLET_APPID;
                String secret = ConfigUtil.APPLET_APP_SECRECT;
                String shh = ConfigUtil.MCH_ID;
                String key = ConfigUtil.API_KEY;
                //String filePath = "//opt/apiclientcert.p12";
                String filePath = "‪D:\\apiclientcert.p12";
                // 总金额以分为单位，不带小数点
                result = (Map<String, String>) wxRefund(appId, secret, shh, key, orderNo, orderMap.get("total_fee"), orderMap.get("total_fee"), filePath);
            }
        }
        return result;
    }

//    @Override
//    public boolean validOrderFee(String orderNo, Double totalFee) throws Exception {
//        AjaxJsonApi ajaxJson = new AjaxJsonApi();
//        Map<String, String> orderMap = null;
//        orderMap = WxPayUtil.queryWeiXinOrder(orderNo);
//
//        if (orderMap.get("return_code") != null && orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
//            if (orderMap.get("result_code") != null && orderMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
//                if (orderMap.get("trade_state") != null && orderMap.get("trade_state").equalsIgnoreCase("SUCCESS")) {
//                    String finalmoney = null;
//                    finalmoney = String.format("%.2f", totalFee);
//                    finalmoney = finalmoney.replace(".", "");
//                    int intMoney = Integer.parseInt(finalmoney);
//                    if (orderMap.get("total_fee").equals(String.valueOf(intMoney))) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }

    public Object wxRefund(String appId, String secret,
                           String shh, String key, String orderId, String total_fee, String refund_fee, String path) throws Exception {
        Map<String, String> result = new HashMap<String, String>();
        /*PageData pd = new PageData();
        pd = this.getPageData();*/
        String refundid = UUIDGenerator.generate();
        String nonce_str = MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());

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

	/*	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid", ConfigUtil.APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str",  PayCommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", orderNumber);*/
        String sign = PayCommonUtil.createSign(packageParams);

        //RequestHandler reqHandler = new RequestHandler(request, response);
        //reqHandler.init(appId, secret, key);
        //String sign = reqHandler.createSign(packageParams);

		/*----3.拼装需要提交到微信的数据xml---- */
        String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<mch_id>" + shh + "</mch_id>" + "<nonce_str>"
                + nonce_str + "</nonce_str>" + "<op_user_id>" + shh + "</op_user_id>" + "<out_trade_no>" + orderId
                + "</out_trade_no>" + "<out_refund_no>" + refundid + "</out_refund_no>" + "<refund_fee>" + refund_fee
                + "</refund_fee>" + "<total_fee>" + total_fee + "</total_fee>" + "<sign>" + sign + "</sign>" + "</xml>";

            /*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
        System.out.println(path.substring(1));
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(path.substring(1));

        keyStore.load(instream, shh.toCharArray());
        instream.close();


        // 实例化密钥库
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        // 初始化密钥工厂
        kmf.init(keyStore, shh.toCharArray());

        // 创建SSLContext
        SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
        sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
        // 获取SSLSocketFactory对象
        SSLSocketFactory ssf = sslContext.getSocketFactory();

        // Trust own CA and all self-signed certs
        //SSLContext sslcontext = SSLContext.custom().loadKeyMaterial(keyStore, shh.toCharArray()).build();
        // Allow TLSv1 protocol only
            /*SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
                    null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);*/
        //CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
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
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
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
        //return buffer.toString();


        //conn.connect();


			/*----5.发送数据到微信的退款接口---- */

        //HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
        //httpost.setEntity(new StringEntity(xml, "UTF-8"));
        //HttpResponse weixinResponse = httpClient.execute(httpost);
        //String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
        //logger.info(jsonStr);

        //Map map = GetWxOrderno.doXMLParse(jsonStr);

        //String result2 =CommonUtil.httpsRequest(url, "POST", xml);
        System.out.println(buffer.toString());
        Map<String, String> map = XMLUtil.doXMLParse(buffer.toString());//解析微信返回的信息，以Map形式存储便于取值
        if (map.get("return_code").equals("SUCCESS")) {
            if (map.get("result_code").equals("SUCCESS")) {
                result.put("returncode", "SUCCESS");
                result.put("returninfo", "退款成功");
            } else if (map.get("result_code").equals("FAIL")) {
                result.put("returncode", "FAIL");
                result.put("returninfo", map.get("err_code_des"));
            }
        } else {
            result.put("returncode", "FAIL");
            result.put("returninfo", map.get("return_msg"));
        }
        return result;

    }
}
