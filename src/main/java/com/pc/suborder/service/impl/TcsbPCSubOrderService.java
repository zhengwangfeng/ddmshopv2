package com.pc.suborder.service.impl;

import com.pc.suborder.service.TcsbPCSubOrderServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service("tcsbPCSubOrderService")
@Transactional(rollbackFor = Exception.class)
public class TcsbPCSubOrderService extends CommonServiceImpl implements TcsbPCSubOrderServiceI {

    @Override
    public AjaxJsonApi createSubOrderAndSubOrderItems(String content) {

        JSONObject jsonObj = JSONObject.fromObject(content);
        String orderId = (String) jsonObj.get("orderId");
        String str2 = (String)jsonObj.get("orderItems");

        str2 = str2.substring(1,str2.length()-1);
        JSONArray jsonArrayItems = JSONArray.fromObject(str2);

        TcsbOrderEntity orderEntity = this.get(TcsbOrderEntity.class, orderId);
        TcsbOrderParentEntity orderParentEntity = this.get(TcsbOrderParentEntity.class, orderEntity.getOrderParentId());
        TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
        tcsbSubOrderEntity.setShopId(orderEntity.getShopId());
        tcsbSubOrderEntity.setDeskId(orderParentEntity.getDeskId());
        tcsbSubOrderEntity.setNote(orderEntity.getNote());
        tcsbSubOrderEntity.setTaste(orderEntity.getTaste());
        tcsbSubOrderEntity.setIsRoom(orderEntity.getIsRoom());
        tcsbSubOrderEntity.setIsJustNowServing(orderEntity.getIsJustNowServing());
        //设置默认值
        tcsbSubOrderEntity.setUniversalCouponPrice(0.0);
        tcsbSubOrderEntity.setPlatformDiscountPrice(0.0);
        tcsbSubOrderEntity.setSpecialCouponPrice(0.0);
        tcsbSubOrderEntity.setPayStatus("0");
        tcsbSubOrderEntity.setPayMethod("1");
        tcsbSubOrderEntity.setOrderIstake("N");
        //线上价格设置为零
        tcsbSubOrderEntity.setOnlinePrice(0.0);
        //线下收款默认为实际消费金额
        tcsbSubOrderEntity.setOfflinePrice(0.0);
        tcsbSubOrderEntity.setCreateTime(new Date());
        //设置订单号
        tcsbSubOrderEntity.setOrderNo(orderId);
        this.save(tcsbSubOrderEntity);

        //参数1为要转换的JSONArray数据，参数2为要转换的目标数据，即List盛装的数据
        //List<TcsbFoodEntity> foodList = JSONArray.toList(jsonArrayItems, new TcsbFoodEntity(), new JsonConfig());
        List<TcsbUserOrderItemEntity> orderItemEntities = JSONArray.toList(jsonArrayItems, new TcsbUserOrderItemEntity(), new JsonConfig());

        for (TcsbUserOrderItemEntity orderItemEntity : orderItemEntities) {
            TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
            TcsbFoodEntity entity = this.get(TcsbFoodEntity.class,orderItemEntity.getFoodId());
            tcsbUserOrderItemEntity.setCount(orderItemEntity.getCount());
            tcsbUserOrderItemEntity.setNum(orderItemEntity.getNum());
            tcsbUserOrderItemEntity.setFoodId(entity.getId());
            tcsbUserOrderItemEntity.setFoodTasteFun(orderItemEntity.getFoodTasteFun());
            tcsbUserOrderItemEntity.setIsSetMeal(entity.getIsSetMeal());

            if (StringUtil.isNotEmpty(orderItemEntity.getStandardId())) {
                TcsbFoodStandardEntity tfs = this.get(TcsbFoodStandardEntity.class, orderItemEntity.getStandardId());
                tcsbUserOrderItemEntity.setStandardName(tfs.getName());
                tcsbUserOrderItemEntity.setPrice(tfs.getPrice());
            } else {
                tcsbUserOrderItemEntity.setPrice(entity.getPrice());
            }
            tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
            //设置食品名称
            tcsbUserOrderItemEntity.setFoodName(entity.getName());
            String orderItemRelation = OrderNumberGenerateUtil.getOrderNumber();
            tcsbUserOrderItemEntity.setOrderItemRelation(orderItemRelation);
            tcsbUserOrderItemEntity.setStandardId(orderItemEntity.getStandardId());

            //TcsbFoodEntity foodEntity = this.get(TcsbFoodEntity.class,entity.getFoodId());

            if (entity.getIsCurrentPrice().equals("1")) {
                tcsbUserOrderItemEntity.setIsFloat("1");
                tcsbUserOrderItemEntity.setIsUpdatePrice(0);
            } else {
                tcsbUserOrderItemEntity.setIsFloat("0");
                tcsbUserOrderItemEntity.setIsUpdatePrice(1);
            }
            this.saveOrUpdate(tcsbUserOrderItemEntity);
        }


        return null;
    }
}
