package com.applet.message.service;

import com.applet.message.vo.AppletOrderSuccessVO;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;

public interface AppletMessageServiceI {
    AjaxJsonApi sendMessage(String openId, String templateId, String page, String formId, String data, String color, String emphasisKeyword);
    AjaxJsonApi sendOrderSuccessMessage(AppletOrderSuccessVO appletOrderSuccessVO);
    AjaxJsonApi sendReservationMessage(String reservationId);
    AjaxJsonApi sendOrderMessage(String orderId,String openId,String prePayId,String totalPrice);
}
