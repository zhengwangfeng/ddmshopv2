package com.pc.desk.service;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

public interface TcsbPCDeskServiceI extends CommonService {
    AjaxJsonApi reservationShiftDesk(String reservationId,String deskId) throws Exception;
    AjaxJsonApi locationOrderShiftDesk(String orderParentId,String deskId) throws Exception;

}
