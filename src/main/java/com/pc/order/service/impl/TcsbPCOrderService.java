package com.pc.order.service.impl;

import com.pc.order.service.TcsbPCOrderServiceI;
import com.pc.order.vo.TcsbPCOrderItemVO;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.MyBeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("tcsbPCOrderService")
@Transactional
public class TcsbPCOrderService extends CommonServiceImpl implements TcsbPCOrderServiceI {
    @Override
    public AjaxJsonApi getFoodByorderParentId(String orderParentId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        List<TcsbOrderEntity> orderEntities = this.findByProperty(TcsbOrderEntity.class, "orderParentId", orderParentId);
        List<TcsbPCOrderItemVO> vos = new ArrayList<>();
        for (TcsbOrderEntity entity : orderEntities) {
            List<TcsbOrderItemEntity> itemEntities = this.findByProperty(TcsbOrderItemEntity.class, "orderId", entity.getId());
            for (TcsbOrderItemEntity itemEntity : itemEntities) {
                TcsbPCOrderItemVO vo = new TcsbPCOrderItemVO();
                MyBeanUtils.copyBeanNotNull2Bean(itemEntity, vo);
                vos.add(vo);
            }
        }
        ajaxJsonApi.setObj(vos);
        return ajaxJsonApi;
    }

    @Override
    public AjaxJsonApi clearDesk(String orderParentId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbOrderParentEntity orderParentEntity = this.get(TcsbOrderParentEntity.class, orderParentId);
        if(orderParentEntity!=null){
            if(orderParentEntity.getOrderStatus().equals("1")){
                TcsbDeskEntity deskEntity = this.get(TcsbDeskEntity.class,orderParentEntity.getDeskId());
                if(!deskEntity.getStatus().equals("2")){
                    ajaxJsonApi.setSuccess(false);
                    ajaxJsonApi.setMsg("该桌位未付完款");
                    return ajaxJsonApi;
                }
                //更改订单状态
                orderParentEntity.setOrderStatus("0");
                orderParentEntity.setUpdateDate(new Date());
                this.saveOrUpdate(orderParentEntity);
                //桌位改空闲

                deskEntity.setStatus("0");
                this.saveOrUpdate(deskEntity);
            }else {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg("订单未处于使用中");
            }
        }else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("总订单不存在");
        }
        return ajaxJsonApi;
    }
}
