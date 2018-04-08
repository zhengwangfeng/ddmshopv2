package com.pc.suborder.service;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

public interface TcsbPCSubOrderServiceI extends CommonService {
    AjaxJsonApi createSubOrderAndSubOrderItems(String content);
}
