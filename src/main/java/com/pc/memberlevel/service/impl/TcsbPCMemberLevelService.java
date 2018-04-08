package com.pc.memberlevel.service.impl;

import com.pc.memberlevel.service.TcsbPCMemberLevelServiceI;
import com.pc.memberlevel.vo.TcsbPCMembershipLevelVO;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("tcsbPCMemberLevelService")
@Transactional
public class TcsbPCMemberLevelService extends CommonServiceImpl implements TcsbPCMemberLevelServiceI {

    @Override
    public AjaxJsonApi getShopAllMemberLevel(String shopId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbMembershipLevelEntity> levelList = this.findByProperty(TcsbMembershipLevelEntity.class,"shopId",shopId);
        List<TcsbPCMembershipLevelVO> outList = new ArrayList<>();
        for(TcsbMembershipLevelEntity levelEntity : levelList){
            TcsbPCMembershipLevelVO vo = new TcsbPCMembershipLevelVO();
            MyBeanUtils.copyBeanNotNull2Bean(levelEntity,vo);
            TcsbMemberLevelEquityEntity equityEntity = this.get(TcsbMemberLevelEquityEntity.class,levelEntity.getMemberLevelEquityId());
            vo.setDiscount(equityEntity.getDiscount().toString());
            outList.add(vo);
        }
        ajaxJsonApi.setSuccess(true);
        ajaxJsonApi.setObj(outList);
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi addMemberLevel(String levelName, String discount, String shopId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbMembershipLevelEntity> levelList = this.findHql("from TcsbMembershipLevelEntity where name=? and shopId=?", levelName, shopId);
        String equityName = levelName;
        List<TcsbMemberLevelEquityEntity> equityList = this.findHql("from TcsbMemberLevelEquityEntity where name=? and shopId=?", equityName, shopId);
        if (levelList.isEmpty() && equityList.isEmpty()) {

            TcsbMemberLevelEquityEntity tcsbMemberLevelEquityEntity = new TcsbMemberLevelEquityEntity();
            tcsbMemberLevelEquityEntity = setDiscount(tcsbMemberLevelEquityEntity, discount);
            tcsbMemberLevelEquityEntity.setName(levelName);
            tcsbMemberLevelEquityEntity.setShopId(shopId);
            tcsbMemberLevelEquityEntity.setCreateDate(new Date());
            this.save(tcsbMemberLevelEquityEntity);

            TcsbMembershipLevelEntity levelEntity = new TcsbMembershipLevelEntity();
            levelEntity.setName(levelName);
            levelEntity.setCreateDate(new Date());
            levelEntity.setMemberLevelEquityId(tcsbMemberLevelEquityEntity.getId());
            levelEntity.setShopId(shopId);
            this.save(levelEntity);

            ajaxJsonApi.setSuccess(true);
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员等级已存在");
        }
        return ajaxJsonApi;
    }

    public TcsbMemberLevelEquityEntity setDiscount(TcsbMemberLevelEquityEntity tcsbMemberLevelEquityEntity, String discount) {
        Double doubleValDiscount = Double.parseDouble(discount);
        if (doubleValDiscount == 10) {
            tcsbMemberLevelEquityEntity.setIsDiscount("0");
        } else {
            tcsbMemberLevelEquityEntity.setIsDiscount("1");
            tcsbMemberLevelEquityEntity.setDiscount(Double.parseDouble(discount));
        }
        return tcsbMemberLevelEquityEntity;
    }

    @Override
    public AjaxJsonApi updateMemberLevel(String memberLevelId, String levelName, String discount) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbMembershipLevelEntity levelEntity = get(TcsbMembershipLevelEntity.class, memberLevelId);
        if (levelEntity != null) {
            levelEntity.setName(levelName);
            this.saveOrUpdate(levelEntity);
            TcsbMemberLevelEquityEntity equityEntity = get(TcsbMemberLevelEquityEntity.class, levelEntity.getMemberLevelEquityId());
            if (equityEntity != null) {
                equityEntity.setName(levelName);
                equityEntity = setDiscount(equityEntity, discount);
                this.saveOrUpdate(equityEntity);
            }
            ajaxJsonApi.setSuccess(true);
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员等级不存在");
        }
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi deleteMemberLevel(String memberLevelId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbMembershipLevelEntity levelEntity = get(TcsbMembershipLevelEntity.class, memberLevelId);
        if (levelEntity != null) {
            List<TcsbMemberUserEntity> list = this.findByProperty(TcsbMemberUserEntity.class,"membershipLevelId",memberLevelId);
            if(!list.isEmpty()){
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("该会员类型有会员正在使用，请替换使用中的会员类型");
            }else {
                TcsbMemberLevelEquityEntity equityEntity = get(TcsbMemberLevelEquityEntity.class, levelEntity.getMemberLevelEquityId());
                if (equityEntity != null) {
                    this.delete(equityEntity);
                }
                this.delete(levelEntity);
                ajaxJsonApi.setSuccess(true);
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("会员等级不存在");
        }
        return ajaxJsonApi;
    }
}
