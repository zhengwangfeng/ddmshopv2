package com.pc.memberlevel.service;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;

public interface TcsbPCMemberLevelServiceI {
    AjaxJsonApi getShopAllMemberLevel(String shopId) throws Exception;

    AjaxJsonApi addMemberLevel(String levelName, String discount, String shopId) throws Exception;

    AjaxJsonApi updateMemberLevel(String memberLevelId, String levelName, String discount) throws Exception;

    AjaxJsonApi deleteMemberLevel(String memberLevelId) throws Exception;
}
