package com.pc.shopmanage.service.impl;

import com.pc.shopmanage.service.TcsbPCShopManageServiceI;
import com.tcsb.shopmanage.entity.TcsbShopDeskManageEntity;
import com.tcsb.shopmanage.entity.TcsbShopManageEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zwf
 */

@Service("tcsbPCShopManageService")
@Transactional(rollbackFor = Exception.class)
public class TcsbPCShopManageService extends CommonServiceImpl implements TcsbPCShopManageServiceI {

    @Override
    public AjaxJsonApi setShopProscenium(String shopId, String cpuCode) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbShopManageEntity> list = this.findHql("from TcsbShopManageEntity where shopId=? and prosceniumStatus='1'", shopId);
        if (list.isEmpty()) {
            TcsbShopManageEntity shopManage = this.findUniqueByProperty(TcsbShopManageEntity.class, "cpuCode", cpuCode);
            shopManage.setProsceniumStatus("1");
            this.saveOrUpdate(shopManage);
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("店铺已有前台");
        }
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi manageShopDesk(String shopId, String cpuCode, String deskIds) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        String[] deskIdArray = deskIds.split(",");

        List<TcsbShopDeskManageEntity> list = this.findHql("from TcsbShopDeskManageEntity where shopId=? and cpuCode=?",shopId,cpuCode);
        Set<String> deskSet = new HashSet<>();
        for (TcsbShopDeskManageEntity s : list) {
            deskSet.add(s.getDeskId());
        }
        Set<String> reuqestSet = new HashSet<>();
        for (String s : deskIdArray) {
            reuqestSet.add(s);
        }
        for(TcsbShopDeskManageEntity m :list){
            if(reuqestSet.contains(m.getDeskId())){
                continue;
            }else {
                this.delete(m);
            }
        }
        for (String s : deskIdArray) {
            if(deskSet.contains(s)){
                continue;
            }else {
                TcsbShopDeskManageEntity tcsbShopDeskManageEntity = new TcsbShopDeskManageEntity();
                tcsbShopDeskManageEntity.setCpuCode(cpuCode);
                tcsbShopDeskManageEntity.setCreateTime(new Date());
                tcsbShopDeskManageEntity.setDeskId(s);
                tcsbShopDeskManageEntity.setShopId(shopId);
                this.saveOrUpdate(tcsbShopDeskManageEntity);
            }
        }
        return ajaxJsonApi;
    }
}
