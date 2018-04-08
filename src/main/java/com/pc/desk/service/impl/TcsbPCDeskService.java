package com.pc.desk.service.impl;

import com.pc.desk.service.TcsbPCDeskServiceI;
import com.pc.util.PCDateUtil;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("tcsbPCDeskService")
@Transactional
public class TcsbPCDeskService extends CommonServiceImpl implements TcsbPCDeskServiceI {
    /**
     * 预订单转台
     *
     * @param reservationId
     * @param deskId
     * @return
     * @throws Exception
     */
    @Override
    public AjaxJsonApi reservationShiftDesk(String reservationId, String deskId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbDeskReservationEntity reservationEntity = this.get(TcsbDeskReservationEntity.class, reservationId);
        if (reservationEntity != null && reservationEntity.getStatus().equals("1")) {

            TcsbDeskEntity shirftDeskEntity = this.get(TcsbDeskEntity.class, deskId);
            //待转桌位
            if (shirftDeskEntity != null) {
                List<TcsbDeskReservationEntity> list = this.findHql("from TcsbDeskReservationEntity where shopId = ? and deskId=? and status=? and orderTime >? and orderTime <?",
                        reservationEntity.getShopId(), deskId, "1", PCDateUtil.getDateBegin(reservationEntity.getOrderTime()), PCDateUtil.getDateEnd(reservationEntity.getOrderTime()));
                if (list.size() > 0) {
                    ajaxJsonApi.setSuccess(false);
                    ajaxJsonApi.setMsg(shirftDeskEntity.getDeskName() + "未处于空闲状态");
                    return ajaxJsonApi;
                }
                reservationEntity.setDeskId(deskId);
                this.saveOrUpdate(reservationEntity);
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("预点订单为空");
        }
        return ajaxJsonApi;
    }

    /**
     * 堂点订单转台
     *
     * @param orderParentId
     * @param deskId
     * @return
     * @throws Exception
     */
    @Override
    public AjaxJsonApi locationOrderShiftDesk(String orderParentId, String deskId) throws Exception {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbOrderParentEntity orderParentEntity = this.get(TcsbOrderParentEntity.class, orderParentId);
        if (orderParentEntity != null) {
            ajaxJsonApi = moveDeskToAnother(orderParentEntity.getDeskId(), deskId);
            if (ajaxJsonApi.isSuccess()) {
                orderParentEntity.setDeskId(deskId);
                this.saveOrUpdate(orderParentEntity);
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg("堂点订单为空");
        }
        return ajaxJsonApi;
    }

    public AjaxJsonApi moveDeskToAnother(String deskId, String shiftDeskId) {
        AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
        TcsbDeskEntity shirftDeskEntity = this.get(TcsbDeskEntity.class, shiftDeskId);
        TcsbDeskEntity deskEntity = this.get(TcsbDeskEntity.class, deskId);
        //待转桌位
        if (shirftDeskEntity != null && shirftDeskEntity.getStatus().equals("0")) {
            //原桌位
            if (deskEntity != null && !deskEntity.getStatus().equals("0")) {
                shirftDeskEntity.setStatus(deskEntity.getStatus());
                this.saveOrUpdate(shirftDeskEntity);
                deskEntity.setStatus("0");
                this.saveOrUpdate(deskEntity);
            } else {
                ajaxJsonApi.setSuccess(false);
                ajaxJsonApi.setMsg(shirftDeskEntity.getDeskName() + "处于空闲状态");
                return ajaxJsonApi;
            }
        } else {
            ajaxJsonApi.setSuccess(false);
            ajaxJsonApi.setMsg(shirftDeskEntity.getDeskName() + "未处于空闲状态");
            return ajaxJsonApi;
        }
        return ajaxJsonApi;
    }
}
