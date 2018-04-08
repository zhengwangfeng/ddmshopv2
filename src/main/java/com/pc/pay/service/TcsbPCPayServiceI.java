package com.pc.pay.service;

import com.pc.pay.vo.TcsbPCOrderItemVO;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface TcsbPCPayServiceI {
    Double countOrderTotalMoney(String orderId) throws ParseException;

    List<AjaxJsonApi> refundFoodItem(List<TcsbPCOrderItemVO> itemVOList, String reason) throws Exception;

    AjaxJsonApi refundOrder(String orderId, String reason) throws Exception;

    AjaxJsonApi refundOrderParent(String orderParentId, String reason) throws Exception;

    AjaxJsonApi refundDeskReservation(String deskReservationId, String reason) throws Exception;

    AjaxJsonApi refundDepositMoney(String depositDeltaId) throws Exception;

//    AjaxJsonApi refundDepositMoneyOnly(HttpServletRequest request, HttpServletResponse response, TcsbOrderParentEntity tcsbOrderParentEnttity);

    Map<String, String> reFund(String orderNo, Double totalFee) throws Exception;

    //boolean validOrderFee(String orderNo, Double totalFee) throws Exception;

    Object wxRefund(String appId, String secret,
                    String shh, String key, String orderId, String total_fee, String refund_fee, String path) throws Exception;
}
