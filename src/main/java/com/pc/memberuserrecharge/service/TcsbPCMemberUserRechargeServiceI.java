package com.pc.memberuserrecharge.service;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

public interface TcsbPCMemberUserRechargeServiceI extends CommonService {
    AjaxJsonApi memberUserRecharge(String id, Double amount, Double presentAmount, String payUtil) throws Exception;

    AjaxJsonApi memberUserCost(String memberUserId, String orderId, Double realPayMoney, Double offlineDiscount, String offlinePayMethod, String dicountType) throws Exception;
}
