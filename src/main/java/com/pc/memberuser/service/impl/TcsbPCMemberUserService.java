package com.pc.memberuser.service.impl;

import com.pc.memberuser.service.TcsbPCMemberUserServiceI;
import com.pc.memberuser.vo.TcsbPCMemberUserBasicMsgVO;
import com.pc.memberuser.vo.TcsbPCMemberUserVO;
import java.util.Date;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("tcsbPCMemberUserService")
@Transactional
public class TcsbPCMemberUserService extends CommonServiceImpl implements TcsbPCMemberUserServiceI {

//    @Override
//    public AjaxJsonApi getShopAllMemberUser(String shopId, DataGrid dataGrid) throws Exception {
//
//        CriteriaQuery cq = new CriteriaQuery(TcsbMemberUserEntity.class, dataGrid);
//
//        cq.eq("shopId", shopId);
//        cq.addOrder("cardNo", SortDirection.desc);
//        this.getDataGridReturn(cq, true);
//        List<TcsbMemberUserEntity> list = dataGrid.getResults();
//
//        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
//        List<TcsbPCMemberUserVO> outList = new ArrayList<>();
//        for (TcsbMemberUserEntity entity : list) {
//            TcsbPCMemberUserVO vo = new TcsbPCMemberUserVO();
//            MyBeanUtils.copyBeanNotNull2Bean(entity, vo);
//            TcsbMembershipLevelEntity levelEntity = this.get(TcsbMembershipLevelEntity.class, entity.getMembershipLevelId());
//            vo.setMembershipLevelName(levelEntity.getName());
//            TcsbMemberLevelEquityEntity equityEntity = this.get(TcsbMemberLevelEquityEntity.class, levelEntity.getMemberLevelEquityId());
//            //有折扣
//            if (equityEntity.getIsDiscount().equals("1")) {
//                vo.setDiscount(equityEntity.getDiscount().toString());
//            } else if (equityEntity.getIsDiscount().equals("0")) {
//                vo.setDiscount("10");
//            }
//            outList.add(vo);
//        }
//        ajaxJsonApi.setObj(outList);
//        return ajaxJsonApi;
//    }

    @Override
    public AjaxJsonApi addMemberUser(TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        if (ajaxJsonApi.isSuccess()) {
            ajaxJsonApi = validCardNo(ajaxJsonApi, memberUserBasicMsgVO);
        }
        if (ajaxJsonApi.isSuccess()) {
            ajaxJsonApi = validMobile(ajaxJsonApi, memberUserBasicMsgVO);
        }
        if (ajaxJsonApi.isSuccess()) {
            TcsbMemberUserEntity memberUserEntity = new TcsbMemberUserEntity();
            memberUserBasicMsgVO.setId(null);
            MyBeanUtils.copyBeanNotNull2Bean(memberUserBasicMsgVO, memberUserEntity);
            memberUserEntity.setBalance(0.0);
            memberUserEntity.setCreateTime(new Date());
            memberUserEntity.setStatus("1");
            this.save(memberUserEntity);
        }
        return ajaxJsonApi;
    }

//    public AjaxJsonApi validAddMemberUser(TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) {
//        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
//        if (ajaxJsonApi.isSuccess()) {
//            ajaxJsonApi = validCardNo(ajaxJsonApi,memberUserBasicMsgVO);
//        }
//        if(ajaxJsonApi.isSuccess()){
//            ajaxJsonApi = validMobile(ajaxJsonApi,memberUserBasicMsgVO);
//        }
//        return ajaxJsonApi;
//    }

    public AjaxJsonApi validCardNo(AjaxJsonApi ajaxJsonApi, TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) {
        if (ajaxJsonApi.isSuccess()) {
            List<TcsbMemberUserEntity> validCardNoList = this.findHql("from TcsbMemberUserEntity where cardNo=? and shopId=?", memberUserBasicMsgVO.getCardNo(), memberUserBasicMsgVO.getShopId());
            if (!validCardNoList.isEmpty()) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("卡号已存在");
            }
        }
        return ajaxJsonApi;
    }

    public AjaxJsonApi validMobile(AjaxJsonApi ajaxJsonApi, TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) {
        if (ajaxJsonApi.isSuccess()) {
            List<TcsbMemberUserEntity> validMobileList = this.findHql("from TcsbMemberUserEntity where mobile=? and shopId=?", memberUserBasicMsgVO.getMobile(), memberUserBasicMsgVO.getShopId());
            if (ajaxJsonApi.isSuccess() && !validMobileList.isEmpty()) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("电话号码已存在");
            }
        }
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi updateMemberUser(TcsbPCMemberUserBasicMsgVO memberUserBasicMsgVO) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbMemberUserEntity tcsbMemberUserEntity = this.get(TcsbMemberUserEntity.class, memberUserBasicMsgVO.getId());
        if (tcsbMemberUserEntity != null) {
            if (ajaxJsonApi.isSuccess() && !memberUserBasicMsgVO.getCardNo().equals(tcsbMemberUserEntity.getCardNo())) {
                ajaxJsonApi = validCardNo(ajaxJsonApi, memberUserBasicMsgVO);
            }
            if (ajaxJsonApi.isSuccess() && !memberUserBasicMsgVO.getMobile().equals(tcsbMemberUserEntity.getMobile())) {
                ajaxJsonApi = validMobile(ajaxJsonApi, memberUserBasicMsgVO);
            }
            if (ajaxJsonApi.isSuccess()) {
                MyBeanUtils.copyBeanNotNull2Bean(memberUserBasicMsgVO, tcsbMemberUserEntity);
                this.saveOrUpdate(tcsbMemberUserEntity);
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员不存在");
        }
        return ajaxJsonApi;
    }


    @Override
    public AjaxJsonApi updateMemberUserStatus(String memberUserId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbMemberUserEntity tcsbMemberUserEntity = this.get(TcsbMemberUserEntity.class, memberUserId);
        if(tcsbMemberUserEntity.getStatus().equals("0")){
            tcsbMemberUserEntity.setStatus("1");
        }else if (tcsbMemberUserEntity.getStatus().equals("1")){
            tcsbMemberUserEntity.setStatus("0");
        }
        this.saveOrUpdate(tcsbMemberUserEntity);
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi getShopOneMemberUser(String queryString, String shopId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbMemberUserEntity> list = this.findHql("from TcsbMemberUserEntity where shopId='" + shopId + "' and cardNo like '%" + queryString + "%' or mobile like '%" + queryString + "%' or name like '%" + queryString + "%'");

        if (!list.isEmpty()) {
            if (list.size() > 1) {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("查询结果大于1，请替换查询条件");
            } else {
                ajaxJsonApi.setObj(convertMemberUserEntityTOVO(list.get(0)));
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("查询结果为0");
        }

        return ajaxJsonApi;
    }

    public TcsbPCMemberUserVO convertMemberUserEntityTOVO(TcsbMemberUserEntity entity) throws Exception {
        TcsbPCMemberUserVO vo = new TcsbPCMemberUserVO();
        MyBeanUtils.copyBeanNotNull2Bean(entity, vo);

        TcsbMembershipLevelEntity levelEntity = this.get(TcsbMembershipLevelEntity.class, entity.getMembershipLevelId());
        if (levelEntity != null) {
            vo.setMembershipLevelName(levelEntity.getName());
        }

        TcsbMemberLevelEquityEntity equityEntity = this.get(TcsbMemberLevelEquityEntity.class, levelEntity.getMemberLevelEquityId());
        if (equityEntity != null) {
            //有折扣
            if (equityEntity.getIsDiscount().equals("1")) {
                vo.setDiscount(equityEntity.getDiscount().toString());
            } else if (equityEntity.getIsDiscount().equals("0")) {
                vo.setDiscount("10");
            }
        }
        return vo;
    }

}
