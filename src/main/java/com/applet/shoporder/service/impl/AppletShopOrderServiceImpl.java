/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月10日 上午9:32:57
 */
package com.applet.shoporder.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.applet.shoporder.service.AppletShopOrderServiceI;
import com.applet.shoporder.vo.AppletRequestParamVo;
import com.applet.shoporder.vo.CouponDto;
import com.applet.shoporder.vo.OrderInfoVo;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopsharegoldcoin.entity.TcsbShopShareGoldCoinEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsbshoppaymode.entity.TcsbShopPaymodeEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.usercar.entity.TcsbUserCarEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月10日 上午9:32:57
 */
@Service("appletShopService")
@Transactional
public class AppletShopOrderServiceImpl extends CommonServiceImpl implements AppletShopOrderServiceI{

	@Override
	public TcsbOrderParentEntity getOrderParent(String deskId) {
		String hql = "from TcsbOrderParentEntity where deskId='"+deskId+"' and orderStatus='1'";
		return this.singleResult(hql);
	}

	@Override
	public TcsbOrderEntity getOrder(String deskId) {
		String tcsbOrderupdatehql = "from TcsbOrderEntity where deskId='"+deskId+"' and payStatus=0";
		return this.singleResult(tcsbOrderupdatehql);
	}

	@Override
	public boolean addOrderItemAndSubOrder(String suborderId,TcsbOrderEntity order) {
		//获取购物车信息
		TcsbShopCarEntity shopCar = this.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", order.getDeskId());
		//
		List<TcsbShopCarItemEntity> shopCarItemList = this.findByProperty(TcsbShopCarItemEntity.class, "shopCar", shopCar.getId());
		if(shopCarItemList.size()>0){
			double totalPrice = 0.0;
			for (TcsbShopCarItemEntity shopCar2 : shopCarItemList) {
			TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, shopCar2.getFoodId());
			double price = BigDecimalUtil.mul(tcsbFoodEntity.getPrice(), shopCar2.getCount());
			totalPrice = BigDecimalUtil.add(totalPrice, price);
			//创建接单项
			String orderItemRelation =OrderNumberGenerateUtil.getOrderNumber();
			TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
			tcsbUserOrderItemEntity.setCount(Double.valueOf(shopCar2.getCount()+""));
			tcsbUserOrderItemEntity.setNum(shopCar2.getCount());
			tcsbUserOrderItemEntity.setFoodId(shopCar2.getFoodId());
			tcsbUserOrderItemEntity.setFoodTasteFun(shopCar2.getFoodTasteFun());
			
			if (StringUtil.isNotEmpty(shopCar2.getStandardId()))
	        {
	          TcsbFoodStandardEntity tfs = this.get(TcsbFoodStandardEntity.class, shopCar2.getStandardId());
	          tcsbUserOrderItemEntity.setFoodTasteFun(shopCar2.getFoodTasteFun());
	          tcsbUserOrderItemEntity.setStandardName(tfs.getName());
	          tcsbUserOrderItemEntity.setPrice(tfs.getPrice());
	        }
	        else
	        {
	          tcsbUserOrderItemEntity.setFoodTasteFun(shopCar2.getFoodTasteFun());
	          tcsbUserOrderItemEntity.setPrice(tcsbFoodEntity.getPrice());
	        }
			tcsbUserOrderItemEntity.setOrderId(suborderId);
			tcsbUserOrderItemEntity.setFoodName(tcsbFoodEntity.getName());//设置食品名称
			//tcsbUserOrderItemEntity.setPrice(tcsbFoodEntity.getPrice());//设置食品单价
			tcsbUserOrderItemEntity.setOrderItemRelation(orderItemRelation);
			tcsbUserOrderItemEntity.setStandardId(shopCar2.getStandardId());
			TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
			//创建订单子项
			if(shopCar2.isCurrentPrice() || StringUtil.isNotEmpty(shopCar2.getFoodTasteFun())){
				//时价或规格不为空
				TcsbOrderItemEntity tcsbOrderItem = new TcsbOrderItemEntity();
				tcsbOrderItem.setCount(Double.valueOf(shopCar2.getCount()+""));
				tcsbOrderItem.setNum(shopCar2.getCount());
				tcsbOrderItem.setFoodId(shopCar2.getFoodId());
				tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
				tcsbOrderItem.setRetreatNum(0);
				tcsbOrderItem.setCreateDate(new Date());
				 if (StringUtil.isNotEmpty(shopCar2.getStandardId()))
		          {
		            TcsbFoodStandardEntity tfs = this.get(TcsbFoodStandardEntity.class, shopCar2.getStandardId());
		            tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
		            tcsbOrderItem.setStandardName(tfs.getName());
		            tcsbOrderItem.setPrice(tfs.getPrice());
		          }
		          else
		          {
		            tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
		            tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());
		          }
				tcsbOrderItem.setOrderId(order.getId());//设置订单ID
				tcsbOrderItem.setFoodName(tcsbFoodEntity.getName());//设置食品名称
				//tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());//设置食品单价
				tcsbOrderItem.setStandardId(shopCar2.getStandardId());
				tcsbOrderItem.setUnitName(tcsbFoodUnitEntity.getName());
				if(shopCar2.isCurrentPrice()){
					tcsbOrderItem.setIsFloat("1");
					tcsbOrderItem.setIsUpdatePrice(0);
					tcsbUserOrderItemEntity.setIsFloat("1");
					tcsbUserOrderItemEntity.setIsUpdatePrice(0);
				}else{
					tcsbOrderItem.setIsFloat("0");
					tcsbOrderItem.setIsUpdatePrice(1);
					tcsbUserOrderItemEntity.setIsFloat("0");
					tcsbUserOrderItemEntity.setIsUpdatePrice(1);
				}
				tcsbOrderItem.setOrderItemRelation(orderItemRelation);
				tcsbOrderItem.setIsSetMeal(tcsbFoodEntity.getIsSetMeal());
				this.save(tcsbOrderItem);	
			}else{
				//非时价且规格为空
				
				if (StringUtil.isNotEmpty(shopCar2.getStandardId()))
		          {
		            TcsbOrderItemEntity tcsbOrderItem = new TcsbOrderItemEntity();
		            tcsbOrderItem.setCount(Double.valueOf(shopCar2.getCount()));
		            tcsbOrderItem.setNum(shopCar2.getCount());
		            tcsbOrderItem.setFoodId(shopCar2.getFoodId());
		            if (StringUtil.isNotEmpty(shopCar2.getStandardId()))
		            {
		              TcsbFoodStandardEntity tfs = (TcsbFoodStandardEntity)get(TcsbFoodStandardEntity.class, shopCar2.getStandardId());
		              tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
		              tcsbOrderItem.setStandardName(tfs.getName());
		              tcsbOrderItem.setPrice(tfs.getPrice());
		            }
		            else
		            {
		              tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
		              tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());
		            }
		            tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
		            tcsbOrderItem.setOrderId(order.getId());
		            tcsbOrderItem.setFoodName(tcsbFoodEntity.getName());
		            
		            tcsbOrderItem.setUnitName(tcsbFoodUnitEntity.getName());
		            tcsbOrderItem.setIsFloat("0");
		            tcsbOrderItem.setIsUpdatePrice(Integer.valueOf(1));
		            tcsbOrderItem.setOrderItemRelation(orderItemRelation);
		            tcsbOrderItem.setIsSetMeal(tcsbFoodEntity.getIsSetMeal());
		            save(tcsbOrderItem);
		          }
		          else{
		        	  String tcsbOrderItemupdatehql = "from TcsbOrderItemEntity where orderId='"+order.getId()+"' and foodId='"+shopCar2.getFoodId()+"'";
						List<TcsbOrderItemEntity> tcsbOrderItemupdate = this.findByQueryString(tcsbOrderItemupdatehql);
						if(tcsbOrderItemupdate.size()>0){
							double count =BigDecimalUtil.add(tcsbOrderItemupdate.get(0).getCount(), shopCar2.getCount());
							double num =BigDecimalUtil.add(tcsbOrderItemupdate.get(0).getNum(), shopCar2.getCount());
							int ii = (new Double(num)).intValue(); 
							tcsbOrderItemupdate.get(0).setCount(count);
							tcsbOrderItemupdate.get(0).setNum(ii);
							this.saveOrUpdate(tcsbOrderItemupdate.get(0));
						}else{
							TcsbOrderItemEntity tcsbOrderItem = new TcsbOrderItemEntity();
							tcsbOrderItem.setCount(Double.valueOf(shopCar2.getCount()+""));
							tcsbOrderItem.setNum(shopCar2.getCount());
							tcsbOrderItem.setFoodId(shopCar2.getFoodId());
							tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun());
							tcsbOrderItem.setOrderId(order.getId());
							tcsbOrderItem.setFoodName(tcsbFoodEntity.getName());//设置食品名称
							tcsbOrderItem.setPrice(tcsbFoodEntity.getPrice());//设置食品单价
							tcsbOrderItem.setUnitName(tcsbFoodUnitEntity.getName());
							tcsbOrderItem.setIsFloat("0");
							tcsbOrderItem.setIsUpdatePrice(1);
							tcsbOrderItem.setOrderItemRelation(orderItemRelation);
							tcsbOrderItem.setIsSetMeal(tcsbFoodEntity.getIsSetMeal());
							this.save(tcsbOrderItem);
						}
		          }
					tcsbUserOrderItemEntity.setIsFloat("0");
					tcsbUserOrderItemEntity.setIsUpdatePrice(1);
					
				}
			System.out.println("================");
			System.out.println(tcsbFoodEntity.getIsSetMeal());
				tcsbUserOrderItemEntity.setIsSetMeal(tcsbFoodEntity.getIsSetMeal());
				this.save(tcsbUserOrderItemEntity);
			}
			

			//添加多人购物信息
			List<TcsbUserCarEntity> tcsbUserCarEntity = this.findByProperty(TcsbUserCarEntity.class, "carId", shopCar.getId());
			if(StringUtil.isNotEmpty(tcsbUserCarEntity)){
				for (TcsbUserCarEntity tcsbUserCarEntity2 : tcsbUserCarEntity) {
					String userorderhql = "from TcsbUserOrderEntity where userId='"+tcsbUserCarEntity2.getUserId()+"' and orderParentId='"+order.getOrderParentId()+"'";
					TcsbUserOrderEntity tcsbUserOrderEntitycheck = this.singleResult(userorderhql);
					if(tcsbUserOrderEntitycheck == null){
						TcsbUserOrderEntity tcsbUserOrderEntity = new TcsbUserOrderEntity();
						tcsbUserOrderEntity.setOrderParentId(order.getOrderParentId());
						tcsbUserOrderEntity.setUserId(tcsbUserCarEntity2.getUserId());
						tcsbUserOrderEntity.setCreateDate(new Date());
						tcsbUserOrderEntity.setPayStatus("0");
						this.save(tcsbUserOrderEntity);
					}
				}
			}
			//清空购物车
			this.executeSql("delete from tcsb_shop_car where desk_id = ?", order.getDeskId());
			this.executeSql("delete from tcsb_shop_car_item where shop_car = ?", shopCar.getId());
			this.executeSql("delete from tcsb_user_car where car_id = ?", shopCar.getId());
			
			
			return true;
		}else{
			return false;
		}
	}

	
	@Override
	public TcsbSubOrderEntity createSubOrderReturnSubId(AppletRequestParamVo param,String orderId) {
		TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
		tcsbSubOrderEntity.setShopId(param.getShopId());
		tcsbSubOrderEntity.setDeskId(param.getDeskId());
		tcsbSubOrderEntity.setNote(param.getNote());
		tcsbSubOrderEntity.setTaste(param.getTaste());
		tcsbSubOrderEntity.setIsRoom(param.getIsRoom());
		tcsbSubOrderEntity.setIsJustNowServing(param.getIsJustNowServing());
		//设置默认值
		tcsbSubOrderEntity.setUniversalCouponPrice(0.0);
		tcsbSubOrderEntity.setPlatformDiscountPrice(0.0);
		tcsbSubOrderEntity.setSpecialCouponPrice(0.0);
		tcsbSubOrderEntity.setPayStatus("0");
		tcsbSubOrderEntity.setPayMethod("1");
		tcsbSubOrderEntity.setOrderIstake("N");
		tcsbSubOrderEntity.setOnlinePrice(0.0);//线上价格设置为零
		tcsbSubOrderEntity.setOfflinePrice(0.0);//线下收款默认为实际消费金额
		tcsbSubOrderEntity.setCreateTime(new Date());
		tcsbSubOrderEntity.setOrderNo(orderId);//设置订单号
		this.save(tcsbSubOrderEntity);
		return tcsbSubOrderEntity;
	}

	
	@Override
	public TcsbOrderEntity createOrder(AppletRequestParamVo param,String orderParentId) {
		TcsbOrderEntity tcsbOrderEntity = new TcsbOrderEntity();
		tcsbOrderEntity.setShopId(param.getShopId());
		tcsbOrderEntity.setDeskId(param.getDeskId());
		tcsbOrderEntity.setNote(param.getNote());
		tcsbOrderEntity.setTaste(param.getTaste());
		tcsbOrderEntity.setIsRoom(param.getIsRoom());
		tcsbOrderEntity.setIsJustNowServing(param.getIsJustNowServing());
		//设置默认值
		tcsbOrderEntity.setUniversalCouponPrice(0.0);
		tcsbOrderEntity.setPlatformDiscountPrice(0.0);
		tcsbOrderEntity.setSpecialCouponPrice(0.0);
		tcsbOrderEntity.setVipDiscountPrice(0.0);
		tcsbOrderEntity.setMethod("0");
		tcsbOrderEntity.setStatus("0");
		tcsbOrderEntity.setPayStatus("0");
		tcsbOrderEntity.setPayMethod("1");
		tcsbOrderEntity.setOrderIstake("N");
		tcsbOrderEntity.setOnlinePrice(0.0);//线上价格设置为零
		tcsbOrderEntity.setOfflinePrice(0.0);//线下收款默认为实际消费金额
		tcsbOrderEntity.setCreateTime(new Date());
		tcsbOrderEntity.setCreateDate(new Date());
		tcsbOrderEntity.setOrderParentId(orderParentId);//
		this.save(tcsbOrderEntity);
		return tcsbOrderEntity;
	}


	@Override
	public TcsbOrderParentEntity createOrderParent(AppletRequestParamVo param) {
		TcsbOrderParentEntity newtop = new TcsbOrderParentEntity();
		newtop.setDeskId(param.getDeskId());
		newtop.setShopId(param.getShopId());
		newtop.setOrderStatus("1");
		newtop.setOrderLock("0");
		newtop.setCreateDate(new Date());
		newtop.setFrontMoney(0.0);
		newtop.setPeople(param.getPeopleNum());
		this.save(newtop);
		return newtop;
	}

	
	@Override
	public int getShopPaymode(String shopId) {
		TcsbShopPaymodeEntity tp = this.findUniqueByProperty(TcsbShopPaymodeEntity.class, "shopId", shopId);
		//0无限制1线上付款2线下付款
		if(StringUtil.isNotEmpty(tp)){
			if(tp.getPayMode().equals("0")){
				return 0;
			}else{
				if(tp.getPayMode().equals("1")){
					return 1;
				}else{
					if(tp.getPayMode().equals("2")){
						return 2;
					}else{
						return 0;
					}
				}
			}
		}else{
			return 0;
		}
	}

	@Override
	public OrderInfoVo getShopDeskOrder(String orderParentId) {
		OrderInfoVo oInfo = new OrderInfoVo();
		double finalMoney = 0.0D;
		double noPayMoney = 0.0D;
		double isPayMoney = 0.0D;
		double noDiscountMoney = 0.0D;
		
		List<TcsbOrderItemEntity> tcsbOrderItemArray = new ArrayList<>();//订单总菜单项
        List<TcsbOrderItemEntity> payOrderItemArray = new ArrayList<>();//订单已支付菜单项
        List<TcsbOrderItemEntity> nopayOrderItemArray = new ArrayList<>();//订单未支付菜单项
		
		List<TcsbOrderEntity> TcsbOrder = this.findByProperty(TcsbOrderEntity.class, "orderParentId", orderParentId);
		for (TcsbOrderEntity tcsbOrderEntity : TcsbOrder) {
			List<TcsbOrderItemEntity> tcsbOrderItemEntity = this.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
			if(tcsbOrderEntity.getPayStatus().equals("1")){
				//已支付的订单
				for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
					TcsbFoodEntity tf = this.get(TcsbFoodEntity.class, tcsbOrderItemEntity2.getFoodId());
					 double addmoney = 0.0D;
	                    if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getStandardId())) {
	                        TcsbFoodStandardEntity tfs = this.get(TcsbFoodStandardEntity.class, tcsbOrderItemEntity2.getStandardId());
	                        if(StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())){
	                        	double realyCount = tcsbOrderItemEntity2.getCount().doubleValue()-tcsbOrderItemEntity2.getRetreatNum();
	                        	if(realyCount > 0){
	                        		addmoney = BigDecimalUtil.mul(realyCount, tfs.getPrice().doubleValue());
	                        		tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	            	                payOrderItemArray.add(tcsbOrderItemEntity2);
	                        	}
	                        }else{
	                        	addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue(), tfs.getPrice().doubleValue());
	                        	tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	        	                payOrderItemArray.add(tcsbOrderItemEntity2);
	                        }
	                    } else {
	                    	if(StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())){
	                    		double realyCount = tcsbOrderItemEntity2.getCount().doubleValue()-tcsbOrderItemEntity2.getRetreatNum();
	                    		if(realyCount > 0){
	                    			addmoney = BigDecimalUtil.mul(realyCount, tf.getPrice().doubleValue());
	                    			tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	            	                payOrderItemArray.add(tcsbOrderItemEntity2);
	                    		}
	                    	}else{
	                    		addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue(), tf.getPrice().doubleValue());
	                    		tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	        	                payOrderItemArray.add(tcsbOrderItemEntity2);
	                    	}
	                    }
	                isPayMoney = BigDecimalUtil.add(isPayMoney, addmoney);    
	                if (tf.getIsDis().equals("N")) {
	                	noDiscountMoney = BigDecimalUtil.add(noDiscountMoney, addmoney);
                    }
	                
				}
			}
			if(tcsbOrderEntity.getPayStatus().equals("0")){
				//未支付的订单
				for (TcsbOrderItemEntity tcsbOrderItemEntity2 : tcsbOrderItemEntity) {
					TcsbFoodEntity tf = this.get(TcsbFoodEntity.class, tcsbOrderItemEntity2.getFoodId());
					 double addmoney = 0.0D;
	                    if (StringUtil.isNotEmpty(tcsbOrderItemEntity2.getStandardId())) {
	                        TcsbFoodStandardEntity tfs = this.get(TcsbFoodStandardEntity.class, tcsbOrderItemEntity2.getStandardId());
	                        if(StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())){
	                        	double realyCount = tcsbOrderItemEntity2.getCount().doubleValue()-tcsbOrderItemEntity2.getRetreatNum();
	                        	if(realyCount > 0){
	                        		addmoney = BigDecimalUtil.mul(realyCount, tfs.getPrice().doubleValue());
	                        		tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	            	                nopayOrderItemArray.add(tcsbOrderItemEntity2);
	                        	}
	                        }else{
	                        	addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue(), tfs.getPrice().doubleValue());
	                        	tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	        	                nopayOrderItemArray.add(tcsbOrderItemEntity2);
	                        }
	                    } else {
	                    	if(StringUtil.isNotEmpty(tcsbOrderItemEntity2.getRetreatNum())){
	                    		double realyCount = tcsbOrderItemEntity2.getCount().doubleValue()-tcsbOrderItemEntity2.getRetreatNum();
	                    		if(realyCount > 0){
	                    			addmoney = BigDecimalUtil.mul(realyCount, tf.getPrice().doubleValue());
	                    			tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	            	                nopayOrderItemArray.add(tcsbOrderItemEntity2);
	                    		}
	                    	}else{
	                    		addmoney = BigDecimalUtil.mul(tcsbOrderItemEntity2.getCount().doubleValue(), tf.getPrice().doubleValue());
	                    		tcsbOrderItemArray.add(tcsbOrderItemEntity2);
	        	                nopayOrderItemArray.add(tcsbOrderItemEntity2);
	                    	}
	                    }
	                noPayMoney = BigDecimalUtil.add(noPayMoney, addmoney); 
	                if (tf.getIsDis().equals("N")) {
	                	noDiscountMoney = BigDecimalUtil.add(noDiscountMoney, addmoney);
                    }
	                
				}
				
				tcsbOrderEntity.setTotalPrice(noPayMoney);
				this.saveOrUpdate(tcsbOrderEntity);
			}	
		}
		finalMoney = BigDecimalUtil.add(isPayMoney, noPayMoney);
		
		oInfo.setFinalMoney(BigDecimalUtil.numericRetentionDecimal(finalMoney, 2));
		oInfo.setPayMoney(BigDecimalUtil.numericRetentionDecimal(isPayMoney, 2));
		oInfo.setNoPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
		oInfo.setNoDiscountMoney(BigDecimalUtil.numericRetentionDecimal(noDiscountMoney, 2));
		oInfo.setNeedPayMoney(BigDecimalUtil.numericRetentionDecimal(noPayMoney, 2));
		oInfo.setTcsbOrderEntity(TcsbOrder);
		oInfo.setNopayOrderItemArray(nopayOrderItemArray);
		oInfo.setPayOrderItemArray(nopayOrderItemArray);
		oInfo.setTcsbOrderItemArray(tcsbOrderItemArray);
		return oInfo;	
	}

	@Override
	public double isUseCoupon(List<TcsbOrderEntity> orderList) {
		double couponPrice = 0.0D;
		for (TcsbOrderEntity tcsbOrderEntity2 : orderList) {
			if (tcsbOrderEntity2.getUniversalCouponPrice() > 0) {
				couponPrice = tcsbOrderEntity2.getUniversalCouponPrice();
            }
            
            if(tcsbOrderEntity2.getPlatformDiscountPrice() > 0){
            	couponPrice = tcsbOrderEntity2.getPlatformDiscountPrice();//平台优惠金额
            }
            if(couponPrice > 0){
            	break;
            }
        }
		return couponPrice;
	}

	@Override
	public double isUseShopDiscount(List<TcsbOrderEntity> orderList) {
		double shopDicountPrice = 0.0D;
		for (TcsbOrderEntity tcsbOrderEntity2 : orderList) {
            if (tcsbOrderEntity2.getPayStatus().equals("1")) {
                if (tcsbOrderEntity2.getSpecialCouponPrice() > 0) {
                	shopDicountPrice = tcsbOrderEntity2.getSpecialCouponPrice();
                	break;
                }
            }
        }
		return shopDicountPrice;
	}

	@Override
	public List<TcsbShopFullcutTemplateEntity> getShopDiscountActivity(String shopId) {
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = this.findByProperty(TcsbDiscountActivityEntity.class, "shopId", shopId);
		List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
		if (!tcsbDiscountActivityEntities.isEmpty()) {
            for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
                TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
                tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
            }
             ListSorter.sort(tcsbShopFullcutTemplateEntities, "total", "discount");
         }
		 return tcsbShopFullcutTemplateEntities;
	}

	@Override
	public List<CouponDto> getUserCoupon(String userId,String shopId) {
		CriteriaQuery cq = new CriteriaQuery(TcsbCouponEntity.class);
        Calendar calSrc = null;
		try {
			calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(), DateUtils.datetimeFormat), "yyyy-MM-dd");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			cq.ge("expiryDate", DateUtils.parseDate(DateUtils.date_sdf.format(calSrc.getTime()), "yyyy-MM-dd"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        cq.eq("useStatus", "0");
        cq.eq("userId", userId);
        cq.add();
        List<TcsbCouponEntity> tcsbCouponEntity = this.getListByCriteriaQuery(cq, false);
   	 	List<CouponDto> couponDtoList = new ArrayList<>();
        for (TcsbCouponEntity tcsbCouponEntity2 : tcsbCouponEntity) {
        	if(tcsbCouponEntity2.getCouponType().equals("2")){
        		TcsbPlatformFullcutTemplateEntity tcsbPlatformFullcutTemplate = this.get(TcsbPlatformFullcutTemplateEntity.class, tcsbCouponEntity2.getFullcutTemplateId());
        		CouponDto couponDto = new CouponDto();
        		couponDto.setCouponId(tcsbCouponEntity2.getId());
        		couponDto.setCouponType(tcsbCouponEntity2.getCouponType());
        		couponDto.setDiscount(tcsbPlatformFullcutTemplate.getDiscount());
        		couponDto.setFoodId(tcsbCouponEntity2.getFoodId());
        		couponDto.setFoodTypeId(tcsbCouponEntity2.getFoodTypeId());
        		couponDto.setFullcutTemplateId(tcsbPlatformFullcutTemplate.getId());
        		couponDto.setTotal(tcsbPlatformFullcutTemplate.getTotal());
        		couponDto.setUseRange(tcsbCouponEntity2.getUseRange());
        		couponDtoList.add(couponDto);
        	}else{
        		if(tcsbCouponEntity2.getShopId().equals(shopId)){
        			TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity2.getFullcutTemplateId());
            		CouponDto couponDto = new CouponDto();
            		couponDto.setCouponId(tcsbCouponEntity2.getId());
            		couponDto.setCouponType(tcsbCouponEntity2.getCouponType());
            		couponDto.setDiscount(tcsbFullcutTemplateEntity.getDiscount());
            		couponDto.setFoodId(tcsbCouponEntity2.getFoodId());
            		couponDto.setFoodTypeId(tcsbCouponEntity2.getFoodTypeId());
            		couponDto.setFullcutTemplateId(tcsbFullcutTemplateEntity.getId());
            		couponDto.setTotal(tcsbFullcutTemplateEntity.getTotal());
            		couponDto.setUseRange(tcsbCouponEntity2.getUseRange());
            		couponDtoList.add(couponDto);
        		}
        	}
        }
        
        if (!couponDtoList.isEmpty()) {
            ListSorter.sort(couponDtoList, "total", "discount");
         }
        
		return couponDtoList;
	}

	@Override
	public TcsbShopShareGoldCoinEntity getUserShareGoldCoin(String userId,String shopId) {
		
		TcsbWeixinUserEntity user = this.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", userId);
		
		String hql = "from TcsbShopShareGoldCoinEntity where shopId='"+shopId+"' and unionid='"+user.getUnionid()+"'";
    	TcsbShopShareGoldCoinEntity tcsbShopShareGoldCoinEntity = this.singleResult(hql);
    	return tcsbShopShareGoldCoinEntity;

		
	}
	
	
	

}
