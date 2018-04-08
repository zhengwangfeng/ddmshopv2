package com.pc.memberuser.service;

import com.pc.memberuser.vo.TcsbPCMemberUserBasicMsgVO;
import com.pc.memberuser.vo.TcsbPCMemberUserVO;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.CommonService;

public interface TcsbPCMemberUserServiceI extends CommonService {
//    AjaxJsonApi getShopAllMemberUser(String shopId, DataGrid dataGrid) throws Exception;

    AjaxJsonApi addMemberUser(TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) throws Exception;

    AjaxJsonApi updateMemberUser(TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) throws Exception;

    AjaxJsonApi updateMemberUserStatus(String memberUserId) throws Exception;

    AjaxJsonApi getShopOneMemberUser(String queryString,String shopId) throws Exception;

    public TcsbPCMemberUserVO convertMemberUserEntityTOVO(TcsbMemberUserEntity entity) throws Exception;
}
