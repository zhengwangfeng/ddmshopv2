package com.pc.pay.controller;

import com.alibaba.fastjson.JSONObject;
import com.pc.pay.service.TcsbPCPayServiceI;
import com.pc.pay.vo.TcsbPCOrderItemVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping("/pc/PCPayController")
public class TcsbPCPayController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private TcsbPCPayServiceI tcsbPCPayService;

    private static final Logger logger = Logger.getLogger(TcsbPCPayController.class);

    /**
     * PC端菜品退款
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "PCFoodRefund")
    @ResponseBody
    public AjaxJsonApi foodRefund(HttpServletRequest request, HttpServletResponse response, String reason, String jsonArray) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        //订单存在
        List<TcsbPCOrderItemVO> itemVOList = JSONObject.parseArray(jsonArray, TcsbPCOrderItemVO.class);
        if (itemVOList != null) {
            try {
                List<AjaxJsonApi> resultList = tcsbPCPayService.refundFoodItem(itemVOList, reason);
                ajaxJson.setObj(resultList);
            } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("系统异常！");
                e.printStackTrace();
            }
        } else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("商品数据错误");
        }
        return ajaxJson;
    }

    /**
     * PC端子订单退款
     *
     * @param request
     * @param response
     * @param orderId
     * @return
     */
    @RequestMapping(params = "PCOrderRefund")
    @ResponseBody
    public AjaxJsonApi orderRefund(HttpServletRequest request, HttpServletResponse response, String orderId, String reason) {
        AjaxJsonApi ajaxJson = null;
        if(orderId!=null){
            //已支付订单
            try {
                ajaxJson = tcsbPCPayService.refundOrder(orderId, reason);
            } catch (Exception e) {
                ajaxJson.setSuccess(false);
                ajaxJson.setMsg("系统异常！");
                e.printStackTrace();
            }
        }else {
            ajaxJson.setSuccess(false);
            ajaxJson.setMsg("请求子订单参数为空");
        }

        return ajaxJson;
    }

    /**
     * 退押金
     *
     * @param request
     * @param response
     * @param orderParentId
     * @return
     */
    @RequestMapping(params = "refundDepositMoney")
    @ResponseBody
    public AjaxJsonApi refundDepositMoney(HttpServletRequest request, HttpServletResponse response, String orderParentId) {
        AjaxJsonApi ajaxJson = new AjaxJsonApi();
        //TcsbOrderParentEntity tcsbOrderParentEntity = systemService.get(TcsbOrderParentEntity.class, orderParentId);
        //总订单存在
//        if (tcsbOrderParentEntity != null) {
//            ajaxJson = tcsbPCPayService.refundDepositMoney(request, response, tcsbOrderParentEntity);
//        } else {
//            ajaxJson.setSuccess(false);
//            ajaxJson.setMsg("订单不存在！");
//        }
        return ajaxJson;
    }

    /**
     * PC端总订单退款
     *
     * @param request
     * @param response
     * @param orderParentId
     * @return
     */
    @RequestMapping(params = "PCOrderParentRefund")
    @ResponseBody
    public AjaxJsonApi orderParentRefund(HttpServletRequest request, HttpServletResponse response, String orderParentId, String reason) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if(orderParentId!=null){
            //已支付订单
            try {
                ajaxJsonApi = tcsbPCPayService.refundOrderParent(orderParentId, reason);
            } catch (Exception e) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("系统异常！");
                e.printStackTrace();
            }
        }else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("请求总参数为空");
        }
        return ajaxJsonApi;
    }

    /**
     * PC端预约单退款
     *
     * @param request
     * @param response
     * @param deskReservationId
     * @return
     */
    @RequestMapping(params = "PCDeskReservationCancel")
    @ResponseBody
    public AjaxJsonApi deskReservationReRefund(HttpServletRequest request, HttpServletResponse response, String deskReservationId, String reason) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if(StringUtils.isNotEmpty(deskReservationId)){
            try {
                ajaxJsonApi = tcsbPCPayService.refundDeskReservation(deskReservationId, reason);
            } catch (Exception e) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("系统异常！");
                e.printStackTrace();
            }
        }else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("预约订单参数错误");
        }
        return ajaxJsonApi;
    }
}
