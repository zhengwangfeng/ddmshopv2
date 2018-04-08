package com.pc.order.service;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

public interface TcsbPCOrderServiceI extends CommonService {
    AjaxJsonApi getFoodByorderParentId(String orderParentId) throws Exception;
    AjaxJsonApi clearDesk(String orderParentId)throws Exception;
}
