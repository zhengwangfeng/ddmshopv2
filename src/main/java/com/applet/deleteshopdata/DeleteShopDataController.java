package com.applet.deleteshopdata;

import java.util.List;

import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.usercar.entity.TcsbUserCarEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;

@Controller
@RequestMapping("/shopdatadelete")
public class DeleteShopDataController {
	
	@Autowired
	private SystemService o;
	
	@RequestMapping()
	@ResponseBody
	public void delete(String shopId){
		
		//根据店铺id获取店铺订单
		List<TcsbOrderEntity> torder = o.findByProperty(TcsbOrderEntity.class, "shopId", shopId);
		for (TcsbOrderEntity tcsbOrderEntity : torder) {
			//删除订单项
			List<TcsbOrderItemEntity> toitem = o.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
			for (TcsbOrderItemEntity tcsbOrderItemEntity : toitem) {
				o.delete(tcsbOrderItemEntity);
			}
			o.delete(tcsbOrderEntity);
		}
		
		List<TcsbOrderParentEntity> toparent = o.findByProperty(TcsbOrderParentEntity.class, "shopId", shopId);
		for (TcsbOrderParentEntity tcsbOrderParentEntity : toparent) {
			List<TcsbUserOrderEntity> tuorder = o.findByProperty(TcsbUserOrderEntity.class, "orderParentId", tcsbOrderParentEntity.getId());
			for (TcsbUserOrderEntity tcsbUserOrderEntity : tuorder) {
				o.delete(tcsbUserOrderEntity);
			}
			o.delete(tcsbOrderParentEntity);
		}
		
		//===========================
		List<TcsbDeskEntity> tdesk = o.findByProperty(TcsbDeskEntity.class, "shopId", shopId);
		for (TcsbDeskEntity tcsbDeskEntity : tdesk) {
			List<TcsbShopCarEntity> tcar = o.findByProperty(TcsbShopCarEntity.class, "deskId", tcsbDeskEntity.getId());
			for (TcsbShopCarEntity tcsbShopCarEntity : tcar) {
				List<TcsbShopCarItemEntity> catitem = o.findByProperty(TcsbShopCarItemEntity.class, "shopCar", tcsbShopCarEntity.getId());
				for (TcsbShopCarItemEntity tcsbShopCarItemEntity : catitem) {
					o.delete(tcsbShopCarItemEntity);
				}
				
				List<TcsbUserCarEntity> tusercar = o.findByProperty(TcsbUserCarEntity.class, "carId", tcsbShopCarEntity.getId());
				for (TcsbUserCarEntity tcsbUserCarEntity : tusercar) {
					o.delete(tcsbUserCarEntity);	
				}
				o.delete(tcsbShopCarEntity);
			}
			tcsbDeskEntity.setIsOrder("0");
			tcsbDeskEntity.setStatus("0");
			o.saveOrUpdate(tcsbDeskEntity);
		}
		
		//==========================
		
		List<TcsbSubOrderEntity> tsuborder = o.findByProperty(TcsbSubOrderEntity.class, "shopId", shopId);
		for (TcsbSubOrderEntity tcsbSubOrderEntity : tsuborder) {
			List<TcsbUserOrderItemEntity> tuseritem = o.findByProperty(TcsbUserOrderItemEntity.class, "orderId", tcsbSubOrderEntity.getId());
			for (TcsbUserOrderItemEntity tcsbUserOrderItemEntity : tuseritem) {
				o.delete(tcsbUserOrderItemEntity);
			}
			o.delete(tcsbSubOrderEntity);
		}
		
		//=========================
		List<TcsbDeskReservationEntity> treservation = o.findByProperty(TcsbDeskReservationEntity.class, "shopId", shopId);
		for (TcsbDeskReservationEntity tcsbDeskReservationEntity : treservation) {
			List<TcsbDeskReservationItemEntity> treservationitem = o.findByProperty(TcsbDeskReservationItemEntity.class, "reservationId", tcsbDeskReservationEntity.getId());
			for (TcsbDeskReservationItemEntity tcsbDeskReservationItemEntity : treservationitem) {
				o.delete(tcsbDeskReservationItemEntity);
			}
			o.delete(tcsbDeskReservationEntity);
		}
		//过滤掉无效的订单及订单项
		
		List<TcsbOrderEntity> order = o.getList(TcsbOrderEntity.class);
		
		for (TcsbOrderEntity tcsbOrderEntity : order) {
			TcsbOrderParentEntity op = o.get(TcsbOrderParentEntity.class, tcsbOrderEntity.getOrderParentId());
			if(StringUtil.isEmpty(op)){
				o.delete(tcsbOrderEntity);
			}
		}	
		
	}

}
