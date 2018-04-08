package com.applet.message.controller;

import com.applet.message.service.AppletMessageServiceI;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/appletMessageController")
public class AppletMessageController {
    @Autowired
    private AppletMessageServiceI appletMessageService;

//    /**
//     * 发送订单成功消息
//     *
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(params = "sendMessage")
//    @ResponseBody
//    public AjaxJsonApi getToken(HttpServletRequest request, HttpServletResponse response, String reservationId,String openId,String prePayId,String totalPrice) {
//
//        appletMessageService.sendReservationMessage(reservationId,openId,prePayId.replaceAll("prepay_id=",""),totalPrice);
//        return null;
//    }

    /**
     * 发送订单成功消息
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(params = "sendPCReservationSuccessMessage")
    @ResponseBody
    public AjaxJsonApi getToken(HttpServletRequest request, HttpServletResponse response, String reservationId) {
    	System.out.println(reservationId);
        appletMessageService.sendReservationMessage(reservationId);
        return null;
    }

}
