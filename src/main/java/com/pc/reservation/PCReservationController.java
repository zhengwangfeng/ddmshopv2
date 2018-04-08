package com.pc.reservation;

import java.util.Date;
import java.util.List;

import com.pc.pay.service.TcsbPCPayServiceI;
import com.pc.reservation.vo.TcsbPCDeskReservationVO;
import com.pc.util.PCDateUtil;
import org.apache.commons.lang.StringUtils;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbdeskreservation.service.TcsbDeskReservationServiceI;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/pc/PCReservation")
public class PCReservationController {

    @Autowired
    private TcsbDeskReservationServiceI tcsbDeskReservationService;

    @Autowired
    private TcsbPCPayServiceI tcsbPCPayService;

    /**
     * 预点开台
     *
     * @param reservationId
     * @return
     */
    @RequestMapping(params = "begin")
    @ResponseBody
    public String reservationBegin(String reservationId) {

        TcsbDeskReservationEntity deskReservation = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationId);
        if (StringUtil.isNotEmpty(deskReservation)) {
            if (deskReservation.getOrderTime().getTime() < PCDateUtil.getDateBegin(new Date()).getTime()
                    || deskReservation.getOrderTime().getTime() > PCDateUtil.getDateEnd(new Date()).getTime()) {
                return "只能转台当天的预订单";
            }
            double finalMoney = 0.00;
            List<TcsbDeskReservationItemEntity> deskReservationItem = tcsbDeskReservationService.findByProperty(TcsbDeskReservationItemEntity.class, "reservationId", deskReservation.getId());
            for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : deskReservationItem) {
                double addend = BigDecimalUtil.mul(tcsbDeskReservationItemEntity.getCount(), tcsbDeskReservationItemEntity.getPrice());
                finalMoney = BigDecimalUtil.add(finalMoney, addend);
            }
            //生成父订单
            String orderparentId = createOrderParent(deskReservation);
            //生成订单
            String orderId = createOrder(orderparentId, deskReservation, finalMoney);
            //生成订单项
            for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : deskReservationItem) {
                TcsbOrderItemEntity orderitem = new TcsbOrderItemEntity();
                orderitem.setOrderId(orderId);
                orderitem.setFoodId(tcsbDeskReservationItemEntity.getFoodId());
                orderitem.setFoodName(tcsbDeskReservationItemEntity.getFoodName());
                orderitem.setCount(Double.valueOf(tcsbDeskReservationItemEntity.getCount() + ""));
                orderitem.setPrice(tcsbDeskReservationItemEntity.getPrice());
                orderitem.setCreateDate(new Date());
                orderitem.setIsSetMeal(tcsbDeskReservationItemEntity.getIsSetMeal());
                orderitem.setUnitName(tcsbDeskReservationItemEntity.getUnitName());
                if (StringUtil.isNotEmpty(tcsbDeskReservationItemEntity.getIsFloat())) {
                    if (tcsbDeskReservationItemEntity.getIsFloat().equals("1")) {
                        orderitem.setIsFloat("1");
                        orderitem.setIsUpdatePrice(0);
                    } else {
                        orderitem.setIsFloat("0");
                        orderitem.setIsUpdatePrice(1);
                    }
                } else {
                    orderitem.setIsFloat("0");
                    orderitem.setIsUpdatePrice(1);
                }


                orderitem.setNum(tcsbDeskReservationItemEntity.getCount());

                orderitem.setFoodTasteFun(tcsbDeskReservationItemEntity.getFoodtastefun());
                orderitem.setStandardName(tcsbDeskReservationItemEntity.getStandardName());
                orderitem.setStandardId(tcsbDeskReservationItemEntity.getStandardId());
                tcsbDeskReservationService.save(orderitem);
            }
            //更新桌位状态
            TcsbDeskEntity desk = tcsbDeskReservationService.get(TcsbDeskEntity.class, deskReservation.getDeskId());
            desk.setIsOrder("0");
            desk.setStatus("3");
            tcsbDeskReservationService.saveOrUpdate(desk);
            //更新预订单状态
            deskReservation.setStatus("0");
            try {
                tcsbDeskReservationService.saveOrUpdate(deskReservation);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //保存用户信息
            TcsbUserOrderEntity userorder = new TcsbUserOrderEntity();
            userorder.setCreateDate(new Date());
            userorder.setOrderId(orderId);
            userorder.setOrderParentId(orderparentId);
            userorder.setUserId(deskReservation.getUserId());
            tcsbDeskReservationService.save(userorder);
            return "转台成功";
        } else {
            return "预订单不存在";
        }
    }

    public String createOrderParent(TcsbDeskReservationEntity deskReservation) {

        String frontMoney = "";
        if (deskReservation.getIsDeposit().equals("0")) {
            //全额
            frontMoney = deskReservation.getOnlinePrice();
        } else {
            //押金
            TcsbDepositDeltaEntity deposidelta = tcsbDeskReservationService.get(TcsbDepositDeltaEntity.class, deskReservation.getDepositDeltaId());
            frontMoney = BigDecimalUtil.numericRetentionDecimal(deposidelta.getReserveDeposit(), 2);
        }
        TcsbOrderParentEntity orderparent = new TcsbOrderParentEntity();
        orderparent.setCreateDate(new Date());
        orderparent.setDepositId(deskReservation.getDepositDeltaId());
        orderparent.setDeskId(deskReservation.getDeskId());
        orderparent.setFlag(null);
        orderparent.setFrontMoney(Double.valueOf(frontMoney == null ? "0" : frontMoney));
        orderparent.setOrderLock("0");
        orderparent.setShopId(deskReservation.getShopId());
        orderparent.setOrderStatus("1");
        orderparent.setPeople(deskReservation.getNum());
        orderparent.setUseFrontMoney("0");
        tcsbDeskReservationService.save(orderparent);
        return orderparent.getId();
    }


    /**
     * @param
     * @return
     */
    public String createOrder(String orderParentId, TcsbDeskReservationEntity deskReservation, double finalMoney) {

        TcsbOrderEntity order = new TcsbOrderEntity();
        order.setOrderParentId(orderParentId);
        order.setOrderNo("001");
        order.setShopId(deskReservation.getShopId());
        order.setPayStatus("0");
        order.setTotalPrice(finalMoney);
        order.setDeskId(deskReservation.getDeskId());
        order.setCreateDate(new Date());
        //order.setStatus("0");
        order.setStatus("2");
        order.setCreateTime(new Date());
        order.setOnlinePrice(0.0);
        order.setPayMethod("1");
        order.setPlatformDiscountPrice(0.0);
        order.setUniversalCouponPrice(0.0);
        order.setSpecialCouponPrice(0.0);
        order.setOrderIstake("N");
        order.setOfflinePrice(0.0);
        order.setIsJustNowServing("立即上菜");
        order.setVipDiscountPrice(0.0);
        tcsbDeskReservationService.save(order);
        return order.getId();

    }

    /**
     * PC端预订桌位
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCReserveDesk")
    @ResponseBody
    public AjaxJsonApi reserveDesk(HttpServletRequest request, HttpServletResponse response, TcsbDeskReservationEntity deskReservationEntity) {

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();

        try {
            if (deskReservationEntity.getPhone().matches("^1[34578][0-9]{9}$")) {
                List<TcsbDeskReservationEntity> list = tcsbDeskReservationService.findHql("from TcsbDeskReservationEntity where shopId = ? and deskId=? and status =? and orderTime >? and orderTime <?",
                        deskReservationEntity.getShopId(), deskReservationEntity.getDeskId(), "1", PCDateUtil.getDateBegin(deskReservationEntity.getOrderTime()), PCDateUtil.getDateEnd(deskReservationEntity.getOrderTime()));
                if (list.isEmpty()) {
                    String deposit = request.getParameter("deposit");
                    Double doubleDeposit = Double.valueOf(deposit);
                    if (StringUtils.isNotEmpty(deposit) && doubleDeposit > 0) {
                        TcsbDepositDeltaEntity depositDeltaEntity = new TcsbDepositDeltaEntity();
                        depositDeltaEntity.setReserveDeposit(doubleDeposit);
                        //线下支付
                        depositDeltaEntity.setPayMethod("1");
                        depositDeltaEntity.setStatus(1);
                        depositDeltaEntity.setShopId(deskReservationEntity.getShopId());
                        depositDeltaEntity.setCreateDate(new Date());
                        tcsbDeskReservationService.save(depositDeltaEntity);
                        deskReservationEntity.setIsDeposit("1");
                        deskReservationEntity.setDepositDeltaId(depositDeltaEntity.getId());
                    } else {
                        deskReservationEntity.setIsDeposit("0");
                    }
                    deskReservationEntity.setCreateDate(new Date());
                    deskReservationEntity.setStatus("1");
                    deskReservationEntity.setPayStatus("0");
                    tcsbDeskReservationService.save(deskReservationEntity);
                } else {
                    ajaxJsonApi.setSuccess(false);
                    ajaxJsonApi.setMsg("该桌位当天已被预订");
                }
            } else {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("电话号码格式错误");
            }
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }

    /**
     * PC端根据日期查询预订桌位
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCGetReservationByDate")
    @ResponseBody
    public AjaxJsonApi getReservationByDate(HttpServletRequest request, HttpServletResponse response, TcsbDeskReservationEntity reservationEntity) {

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        CriteriaQuery cq = new CriteriaQuery(TcsbDeskReservationEntity.class);
        if (reservationEntity.getShopId() == null) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("店铺id为空");
            return ajaxJsonApi;
        }

        cq.eq("shopId", reservationEntity.getShopId());
        try {
            String queryDate = request.getParameter("queryDate");
            if (StringUtils.isNotEmpty(queryDate)) {
                Date d = DateUtils.parseDate(queryDate, "yyyy-MM-dd");
                cq.ge("orderTime", PCDateUtil.getDateBegin(d));
                cq.le("orderTime", PCDateUtil.getDateEnd(d));
            }
            cq.eq("status", "1");
            cq.addOrder("orderTime", SortDirection.desc);
            cq.add();
            List<TcsbPCDeskReservationVO> list = this.tcsbDeskReservationService.getListByCriteriaQuery(cq, false);

            ajaxJsonApi.setObj(list);
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }

    /**
     * PC端取消预订单
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCReservationCancel")
    @ResponseBody
    public AjaxJsonApi reservationClearDesk(HttpServletRequest request, HttpServletResponse response, TcsbDeskReservationEntity reservationEntity) {

        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        try {
            TcsbDeskReservationEntity deskReservationEntity = tcsbDeskReservationService.get(TcsbDeskReservationEntity.class, reservationEntity.getId());
            if (deskReservationEntity != null) {
                ajaxJsonApi = tcsbPCPayService.refundDeskReservation(reservationEntity.getId(), "");
            }
        } catch (Exception e) {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("系统异常");
            e.printStackTrace();
        }
        return ajaxJsonApi;
    }
}

