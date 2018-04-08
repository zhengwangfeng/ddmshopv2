package com.pc.shopmanage.service;

import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

import java.util.List;

/**
 * @author zwf
 */
public interface TcsbPCShopManageServiceI extends CommonService {
    /**
     * 设置cpu序列号对应的pc端为前台
     *
     * @param shopId
     * @param cpuCode
     * @return
     */
    public AjaxJsonApi setShopProscenium(String shopId, String cpuCode);

    /**
     * 设置cpu序列号对应的pc端管理相应的桌位
     *
     * @param shopId
     * @param cpuCode
     * @param deskIds
     * @return
     */
    AjaxJsonApi manageShopDesk(String shopId, String cpuCode, String deskIds);


}
