package com.tcsb.order.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.OrderNumberGenerateUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.oConvertUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.applet.shopcar.TcsbShopCarItemVo;
import com.applet.shopcar.vo.TcsbShopCarTempVO;
import com.applet.shoporder.vo.AppletRequestParamVo;
import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.order.VO.OrderStatisticVo;
import com.tcsb.order.VO.TcsbOrderItemVO;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.usercar.entity.TcsbUserCarEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;


@Service("tcsbOrderService")
@Transactional
public class TcsbOrderServiceImpl extends CommonServiceImpl implements TcsbOrderServiceI {
	
 	public <T> void delete(T entity) {
 		super.delete(entity);
 		//执行删除操作配置的sql增强
		this.doDelSql((TcsbOrderEntity)entity);
 	}
	
	public void addMain(TcsbOrderEntity tcsbOrder,
	        List<TcsbOrderItemEntity> tcsbOrderItemList){
			//保存主信息
			this.save(tcsbOrder);
			//生成接单项
			//后台接单用的数据
			TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
			tcsbSubOrderEntity.setShopId(tcsbOrder.getShopId());
			tcsbSubOrderEntity.setDeskId(tcsbOrder.getDeskId());
			tcsbSubOrderEntity.setTotalPrice(tcsbOrder.getTotalPrice());
			
			tcsbSubOrderEntity.setNote(tcsbOrder.getNote());
			tcsbSubOrderEntity.setTaste(tcsbOrder.getTaste());
			//tcsbSubOrderEntity.setPeople(tcsbOrder.getPeople());
			tcsbSubOrderEntity.setIsRoom(tcsbOrder.getIsRoom());
			tcsbSubOrderEntity.setIsJustNowServing("立即上菜");
			
			//设置默认值
			tcsbSubOrderEntity.setMethod(0);
			tcsbSubOrderEntity.setStatus(0);
			tcsbSubOrderEntity.setPayStatus("0");
			tcsbSubOrderEntity.setPayMethod("1");
			tcsbSubOrderEntity.setOrderIstake("N");
			//线上价格设置为零
			tcsbSubOrderEntity.setOnlinePrice(0.0);
			//线下收款默认为实际消费金额
			tcsbSubOrderEntity.setOfflinePrice(0.0);
			tcsbSubOrderEntity.setCreateTime(new Date());
			//设置订单号
			String orderNoGenerate =OrderNumberGenerateUtil.getOrderNumber();
			tcsbSubOrderEntity.setOrderNo(orderNoGenerate);
			this.save(tcsbSubOrderEntity);
			
			
			/**保存-订单项管理*/
			for(TcsbOrderItemEntity tcsbOrderItem:tcsbOrderItemList){
				//外键设置
				tcsbOrderItem.setOrderId(tcsbOrder.getId());
				this.save(tcsbOrderItem);
				//添加到接单子项
				TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
				tcsbUserOrderItemEntity.setCount(tcsbOrderItem.getCount());
				tcsbUserOrderItemEntity.setFoodId(tcsbOrderItem.getFoodId());
				tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItem.getFoodTypeId());
				tcsbUserOrderItemEntity.setPrice(tcsbOrderItem.getPrice());
				tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
				tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItem.getFoodTasteFun());
				//获取食品单位设置食品的购买数量
				TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItem.getFoodId());
				if(StringUtil.isNotEmpty(tcsbFoodEntity)){
					TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
					if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
						if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
							tcsbUserOrderItemEntity.setNum(1);
							
						}else{
							double d = tcsbOrderItem.getCount();   
							int i = (new Double(d)).intValue(); 
							tcsbUserOrderItemEntity.setNum(i);
						}
					}else{
						double d = tcsbOrderItem.getCount();   
						int i = (new Double(d)).intValue(); 
						tcsbUserOrderItemEntity.setNum(i);
					}
				}
				tcsbUserOrderItemEntity.setIsUpdatePrice(0);

				this.save(tcsbUserOrderItemEntity);	
			}
			//执行新增操作配置的sql增强
 			this.doAddSql(tcsbOrder);
	}

	
	
	public void updateMain(TcsbOrderEntity tcsbOrder,
	        List<TcsbOrderItemEntity> tcsbOrderItemList) {
		if(tcsbOrder.getStatus().equals("1") && tcsbOrder.getPayMethod().equals("1")){
			tcsbOrder.setPlatformDiscountPrice(0.0);
			tcsbOrder.setSpecialCouponPrice(0.0);
			tcsbOrder.setUniversalCouponPrice(0.0);
		}
		//保存主表信息
		this.saveOrUpdate(tcsbOrder);
		//===================================================================================
		//获取参数
		Object id0 = tcsbOrder.getId();
		//===================================================================================
		//1.查询出数据库的明细数据-订单项管理
	    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    List<TcsbOrderItemEntity> tcsbOrderItemOldList = this.findHql(hql0,id0);
		//2.筛选更新明细数据-订单项管理
		if(tcsbOrderItemList!=null&&tcsbOrderItemList.size()>0){
		for(TcsbOrderItemEntity oldE:tcsbOrderItemOldList){
			boolean isUpdate = false;
				for(TcsbOrderItemEntity sendE:tcsbOrderItemList){
					//需要更新的明细数据-订单项管理
					if(oldE.getId().equals(sendE.getId())){
		    			try {
							MyBeanUtils.copyBeanNotNull2Bean(sendE,oldE);
							TcsbFoodEntity foodEntity =  this.get(TcsbFoodEntity.class,oldE.getFoodId()); 
							if(StringUtil.isNotEmpty(foodEntity)){
								if(StringUtil.isNotEmpty(foodEntity.getUnitId())){
									TcsbFoodUnitEntity foodUnitEntity = this.get(TcsbFoodUnitEntity.class, foodEntity.getUnitId());
									if(StringUtil.isNotEmpty(foodUnitEntity)){
										if(foodUnitEntity.getIsfloat().equals("1")){
											//该菜品是时价
											oldE.setNum(1);
											oldE.setIsUpdatePrice(0);
										}else{
											double d = oldE.getCount();   
											int num = (new Double(d)).intValue(); 
											oldE.setNum(num);
											this.saveOrUpdate(oldE);
										}
									}
								}else{
									double d = oldE.getCount();   
									int num = (new Double(d)).intValue(); 
									oldE.setNum(num);
									this.saveOrUpdate(oldE);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							throw new BusinessException(e.getMessage());
						}
						isUpdate= true;
		    			break;
		    		}
		    	}
	    		if(!isUpdate){
		    		//如果数据库存在的明细，前台没有传递过来则是删除-订单项管理
		    		super.delete(oldE);
	    		}
	    		
			}
			//3.持久化新增的数据-订单项管理
		
		
		
		//生成接单项
		//后台接单用的数据
		TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
		tcsbSubOrderEntity.setShopId(tcsbOrder.getShopId());
		tcsbSubOrderEntity.setDeskId(tcsbOrder.getDeskId());
		tcsbSubOrderEntity.setTotalPrice(tcsbOrder.getTotalPrice());
		
		tcsbSubOrderEntity.setNote(tcsbOrder.getNote());
		tcsbSubOrderEntity.setTaste(tcsbOrder.getTaste());
		//tcsbSubOrderEntity.setPeople(tcsbOrder.getPeople());
		tcsbSubOrderEntity.setIsRoom(tcsbOrder.getIsRoom());
		tcsbSubOrderEntity.setIsJustNowServing("立即上菜");
		
		//设置默认值
		tcsbSubOrderEntity.setMethod(0);
		tcsbSubOrderEntity.setStatus(0);
		tcsbSubOrderEntity.setPayStatus("0");
		tcsbSubOrderEntity.setPayMethod("1");
		tcsbSubOrderEntity.setOrderIstake("N");
		//线上价格设置为零
		tcsbSubOrderEntity.setOnlinePrice(0.0);
		//线下收款默认为实际消费金额
		tcsbSubOrderEntity.setOfflinePrice(0.0);
		tcsbSubOrderEntity.setCreateTime(new Date());
		//设置订单号
		String orderNoGenerate =OrderNumberGenerateUtil.getOrderNumber();
		tcsbSubOrderEntity.setOrderNo(orderNoGenerate);
		this.save(tcsbSubOrderEntity);
		
		
		//检测是否食品规格相同
		double itemOrderPrice = 0.00;
			for(TcsbOrderItemEntity tcsbOrderItem:tcsbOrderItemList){
				if(oConvertUtils.isEmpty(tcsbOrderItem.getId())){
					//检测是否时价菜
					TcsbFoodEntity foodEntity =  this.get(TcsbFoodEntity.class,tcsbOrderItem.getFoodId()); 
					if(StringUtil.isNotEmpty(foodEntity)){
						if(StringUtil.isNotEmpty(foodEntity.getUnitId())){
							TcsbFoodUnitEntity foodUnitEntity = this.get(TcsbFoodUnitEntity.class, foodEntity.getUnitId());
							if(StringUtil.isNotEmpty(foodUnitEntity)){
									if(foodUnitEntity.getIsfloat().equals("1")){
										TcsbOrderItemEntity isfOde = new TcsbOrderItemEntity();
										isfOde.setCount(tcsbOrderItem.getCount());
										isfOde.setFoodId(tcsbOrderItem.getFoodId());
										isfOde.setFoodTasteFun(tcsbOrderItem.getFoodTasteFun());
										isfOde.setFoodTypeId(tcsbOrderItem.getFoodTypeId());
										isfOde.setIsUpdatePrice(1);
										isfOde.setNum(1);
										isfOde.setOrderId(tcsbOrder.getId());
										isfOde.setPrice(tcsbOrderItem.getPrice());
										isfOde.setIsUpdatePrice(0);
										this.save(isfOde);
									}else{
										//0
										//判断订单是否存在相同的食品
										String hql = "from TcsbOrderItemEntity where orderId='"+tcsbOrder.getId()+"' and foodId='"+tcsbOrderItem.getFoodId()+"' and foodTasteFun='"+tcsbOrderItem.getFoodTasteFun()+"'";
										TcsbOrderItemEntity tcsbOrderItemcheck = this.singleResult(hql);
										if(StringUtil.isNotEmpty(tcsbOrderItemcheck)){
											//更新菜品数量
											tcsbOrderItemcheck.setCount(tcsbOrderItemcheck.getCount()+tcsbOrderItem.getCount());
											double d = tcsbOrderItemcheck.getCount();   
											int num = (new Double(d)).intValue(); 
											tcsbOrderItemcheck.setNum(num);
											this.saveOrUpdate(tcsbOrderItemcheck);
										}else{
											//外键设置
											tcsbOrderItem.setOrderId(tcsbOrder.getId());
											double d = tcsbOrderItem.getCount();   
											int num = (new Double(d)).intValue(); 
											tcsbOrderItem.setNum(num);
											this.save(tcsbOrderItem);
										}
									}
								}else{
									//0
									//判断订单是否存在相同的食品
									String hql = "from TcsbOrderItemEntity where orderId='"+tcsbOrder.getId()+"' and foodId='"+tcsbOrderItem.getFoodId()+"' and foodTasteFun='"+tcsbOrderItem.getFoodTasteFun()+"'";
									TcsbOrderItemEntity tcsbOrderItemcheck = this.singleResult(hql);
									if(StringUtil.isNotEmpty(tcsbOrderItemcheck)){
										//更新菜品数量
										tcsbOrderItemcheck.setCount(tcsbOrderItemcheck.getCount()+tcsbOrderItem.getCount());
										double d = tcsbOrderItemcheck.getCount();   
										int num = (new Double(d)).intValue(); 
										tcsbOrderItemcheck.setNum(num);
										this.saveOrUpdate(tcsbOrderItemcheck);
									}else{
										//外键设置
										tcsbOrderItem.setOrderId(tcsbOrder.getId());
										double d = tcsbOrderItem.getCount();   
										int num = (new Double(d)).intValue(); 
										tcsbOrderItem.setNum(num);
										this.save(tcsbOrderItem);
									}
								}
							}else{
								//0
								//判断订单是否存在相同的食品
								String hql = "from TcsbOrderItemEntity where orderId='"+tcsbOrder.getId()+"' and foodId='"+tcsbOrderItem.getFoodId()+"' and foodTasteFun='"+tcsbOrderItem.getFoodTasteFun()+"'";
								TcsbOrderItemEntity tcsbOrderItemcheck = this.singleResult(hql);
								if(StringUtil.isNotEmpty(tcsbOrderItemcheck)){
									//更新菜品数量
									tcsbOrderItemcheck.setCount(tcsbOrderItemcheck.getCount()+tcsbOrderItem.getCount());
									double d = tcsbOrderItemcheck.getCount();   
									int num = (new Double(d)).intValue(); 
									tcsbOrderItemcheck.setNum(num);
									this.saveOrUpdate(tcsbOrderItemcheck);
								}else{
									//外键设置
									tcsbOrderItem.setOrderId(tcsbOrder.getId());
									double d = tcsbOrderItem.getCount();   
									int num = (new Double(d)).intValue(); 
									tcsbOrderItem.setNum(num);
									this.save(tcsbOrderItem);
								}
							}
						}else{
							//菜品不存在，抛出异常处理
							continue;
						}
						
					//计算子单添加菜品的总价
					//TODO
					double mulprice =  BigDecimalUtil.mul(tcsbOrderItem.getPrice(), tcsbOrderItem.getCount());
					itemOrderPrice = BigDecimalUtil.add(itemOrderPrice, mulprice);
					
					//添加到接单子项
					TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
					tcsbUserOrderItemEntity.setCount(tcsbOrderItem.getCount());
					tcsbUserOrderItemEntity.setFoodId(tcsbOrderItem.getFoodId());
					tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItem.getFoodTypeId());
					tcsbUserOrderItemEntity.setPrice(tcsbOrderItem.getPrice());
					tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
					tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItem.getFoodTasteFun());
					//获取食品单位设置食品的购买数量
					TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItem.getFoodId());
					if(StringUtil.isNotEmpty(tcsbFoodEntity)){
						TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
						if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
							if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
								tcsbUserOrderItemEntity.setNum(1);
								
							}else{
								double d = tcsbOrderItem.getCount();   
								int i = (new Double(d)).intValue(); 
								tcsbUserOrderItemEntity.setNum(i);
							}
						}else{
							double d = tcsbOrderItem.getCount();   
							int i = (new Double(d)).intValue(); 
							tcsbUserOrderItemEntity.setNum(i);
						}
					}
					tcsbUserOrderItemEntity.setIsUpdatePrice(0);

					this.save(tcsbUserOrderItemEntity);	
					
					
					
					
				}
			}
			//更新接单项的总价
			tcsbSubOrderEntity.setTotalPrice(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(itemOrderPrice, 2)));
			this.saveOrUpdate(tcsbSubOrderEntity);
		}
		//执行更新操作配置的sql增强
 		this.doUpdateSql(tcsbOrder);
	}

	
	public void delMain(TcsbOrderEntity tcsbOrder) {
		//删除主表信息
		this.delete(tcsbOrder);
		//===================================================================================
		//获取参数
		Object id0 = tcsbOrder.getId();
		//===================================================================================
		//删除-订单项管理
	    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    List<TcsbOrderItemEntity> tcsbOrderItemOldList = this.findHql(hql0,id0);
		this.deleteAllEntitie(tcsbOrderItemOldList);
	}
	
 	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TcsbOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TcsbOrderEntity t){
	 	return true;
 	}
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TcsbOrderEntity t){
	 	return true;
 	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @return
	 */
 	public String replaceVal(String sql,TcsbOrderEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{method}",String.valueOf(t.getMethod()));
 		sql  = sql.replace("#{shop_id}",String.valueOf(t.getShopId()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{total_price}",String.valueOf(t.getTotalPrice()));
 		sql  = sql.replace("#{pay_status}",String.valueOf(t.getPayStatus()));
 		sql  = sql.replace("#{pay_method}",String.valueOf(t.getPayMethod()));
 		sql  = sql.replace("#{eat_time}",String.valueOf(t.getEatTime()));
 		//sql  = sql.replace("#{people}",String.valueOf(t.getPeople()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{is_room}",String.valueOf(t.getIsRoom()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}

/*	@Override
	public Map<String, Object> saveOrderReturnOrderNo(TcsbOrderVO tcsbOrderVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		Double totalMoney = 0.0;
		Double finalMoney = 0.0;
		try {
			//(当前桌位是否已经有订单生成了并且未付款的)
			Map<String, Object>  map2 = this.findOneForJdbc("select o.id,o.order_no from tcsb_order o left join tcsb_user_order u on o.id = u.order_id where o.desk_id=? and o.status=0", tcsbOrderVO.getDeskId());
			//如果有的情况
			if (map2!=null) {
				String orderId = (String) map2.get("id");
				String orderNo = (String) map2.get("order_no");
				//查询是否有朋友添加新菜品
				map2= this.findOneForJdbc("select * from tcsb_user_order t where t.user_id = ? and t.order_id = ? ",tcsbOrderVO.getUserId(),orderId);
				if (map2==null) {
					//多用户订单信息保存（每个人都有一份相同的订单）
					TcsbUserOrderEntity tcsbUserOrderEntity = new TcsbUserOrderEntity();
					tcsbUserOrderEntity.setOrderId(orderNo);
					tcsbUserOrderEntity.setUserId(tcsbOrderVO.getUserId());
					tcsbUserOrderEntity.setStatus(0);
					this.save(tcsbUserOrderEntity);
				}
				//订单编号用于返回给前端
				map.put("orderNo", orderNo);
				//获取旧订单
				TcsbOrderEntity tcsbOrderEntity = this.get(TcsbOrderEntity.class, orderNo);
				//获取该订单的总消费
				totalMoney = tcsbOrderEntity.getTotalPrice();
				//获取该订单的折扣价
				finalMoney = tcsbOrderEntity.getDisPrice();
				//1.查询出数据库的明细数据-订单项管理
			    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND orderId = ? ";
			    List<TcsbOrderItemEntity> tcsbOrderItemOldList = this.findHql(hql0,orderId);
			    List<TcsbOrderItemVO>  tcsbOrderItemVOs = tcsbOrderVO.getTcsbOrderItemVOs();
				//2.筛选更新明细数据-订单项管理
				if(tcsbOrderItemOldList!=null&&tcsbOrderItemOldList.size()>0){
						for (TcsbOrderItemEntity oldE : tcsbOrderItemOldList) {
							boolean isUpdate = false;
							for(TcsbOrderItemVO sendE:tcsbOrderItemVOs){
								//需要更新的明细数据-订单项管理
								if(sendE.getFoodId().equals(oldE.getFoodId())){
					    			try {
					    				totalMoney= totalMoney+sendE.getPrice()*sendE.getCount();
										List<TcsbOrderItemEntity> tcsbOrderItemEntities = this.findHql("from TcsbOrderItemEntity where foodId=? and orderId=?", oldE.getFoodId(),oldE.getOrderId());
										//更新数量
										oldE.setCount(tcsbOrderItemEntities.get(0).getCount());
										this.saveOrUpdate(oldE);
									} catch (Exception e) {
										e.printStackTrace();
										throw new BusinessException(e.getMessage());
									}
									isUpdate= true;
					    			break;
					    		}
					    	}
				    		if(!isUpdate){
					    		//如果数据库存在的明细，前台没有传递过来则是删除-订单项管理
				    			//相应的总金额也要做减操作
				    			totalMoney= totalMoney-oldE.getPrice()*oldE.getCount();
					    		super.delete(oldE);
				    		}
				    		
						}
						//3.持久化新增的数据-订单项管理
						for(TcsbOrderItemVO tcsbOrderItemVO:tcsbOrderItemVOs){
							boolean isAdd=true;
							for (TcsbOrderItemEntity oldE : tcsbOrderItemOldList) {
								if (tcsbOrderItemVO.getFoodId().equals(oldE.getFoodId())) {
									isAdd= false;
									break;
								}
							}
							if (isAdd) {
								totalMoney= totalMoney+tcsbOrderItemVO.getPrice()*tcsbOrderItemVO.getCount();
								TcsbOrderItemEntity tcsbOrderItemEntity = new TcsbOrderItemEntity();
								tcsbOrderItemEntity.setCount(tcsbOrderItemVO.getCount());
								tcsbOrderItemEntity.setFoodId(tcsbOrderItemVO.getFoodId());
								tcsbOrderItemEntity.setFoodTypeId(tcsbOrderItemVO.getFoodTypeId());
								tcsbOrderItemEntity.setPrice(tcsbOrderItemVO.getPrice());
								tcsbOrderItemEntity.setOrderId(tcsbOrderEntity.getId());
								this.save(tcsbOrderItemEntity);
							}
						}
				}
				//to do update
				TcsbDiscountActivityEntity tcsbDiscountActivityEntity = this.findUniqueByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbOrderVO.getShopId());
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity  = this.get(TcsbFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				//立减
				if (totalMoney>=tcsbFullcutTemplateEntity.getTotal()) {
					finalMoney=totalMoney-tcsbFullcutTemplateEntity.getDiscount();
				}else {
					finalMoney=totalMoney;
				}
				//to do update 更新状态
				tcsbOrderEntity.setTotalPrice(totalMoney);
				tcsbOrderEntity.setDisPrice(finalMoney);
				this.saveOrUpdate(tcsbOrderEntity);
				
			}else {
				TcsbOrderEntity tcsbOrderEntity = new TcsbOrderEntity();
				MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderVO, tcsbOrderEntity);
				tcsbOrderEntity.setCreateTime(new Date());
				this.save(tcsbOrderEntity);
				//多用户订单信息保存
				TcsbUserOrderEntity tcsbUserOrderEntity = new TcsbUserOrderEntity();
				tcsbUserOrderEntity.setOrderId(tcsbOrderEntity.getId());
				tcsbUserOrderEntity.setUserId(tcsbOrderVO.getUserId());
				tcsbUserOrderEntity.setStatus(0);
				this.save(tcsbUserOrderEntity);
				List<TcsbOrderItemVO> tcsbOrderItemVOs = tcsbOrderVO.getTcsbOrderItemVOs();
				
				for (TcsbOrderItemVO tcsbOrderItemVO : tcsbOrderItemVOs) {
					totalMoney= totalMoney+tcsbOrderItemVO.getPrice()*tcsbOrderItemVO.getCount();
					TcsbOrderItemEntity tcsbOrderItemEntity = new TcsbOrderItemEntity();
					tcsbOrderItemEntity.setCount(tcsbOrderItemVO.getCount());
					tcsbOrderItemEntity.setFoodId(tcsbOrderItemVO.getFoodId());
					tcsbOrderItemEntity.setFoodTypeId(tcsbOrderItemVO.getFoodTypeId());
					
					tcsbOrderItemEntity.setPrice(tcsbOrderItemVO.getPrice());
					tcsbOrderItemEntity.setOrderId(tcsbOrderEntity.getId());
					this.save(tcsbOrderItemEntity);
				}
				//to do update
				TcsbDiscountActivityEntity tcsbDiscountActivityEntity = this.findUniqueByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbOrderVO.getShopId());
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity  = this.get(TcsbFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				//立减
				if (totalMoney>=tcsbFullcutTemplateEntity.getTotal()) {
					finalMoney=totalMoney-tcsbFullcutTemplateEntity.getDiscount();
				}else {
					finalMoney=totalMoney;
				}
				//to do update 更新状态
				tcsbOrderEntity.setTotalPrice(totalMoney);
				tcsbOrderEntity.setDisPrice(finalMoney);
				tcsbOrderEntity.setMethod(0);
				tcsbOrderEntity.setDMethod("0");
				tcsbOrderEntity.setStatus(0);
				tcsbOrderEntity.setPayStatus("0");
				tcsbOrderEntity.setPayMethod("0");
				//设置订单号
				tcsbOrderEntity.setOrderNo(OrderNumberGenerateUtil.getOrderNumber());
				//订单编号
				//设置是否直接跳转
				map.put("orderNo", tcsbOrderEntity.getOrderNo());
				this.saveOrUpdate(tcsbOrderEntity);
				} 
			}catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}*/
	@Override
	public Map<String, Object> saveOrderReturnOrderNo(TcsbOrderVO tcsbOrderVO) {
		Map<String, Object> map = new HashMap<String, Object>();
		Double totalMoney = 0.0;
		String orderNoGenerate =OrderNumberGenerateUtil.getOrderNumber();
		try {
			//(当前桌位是否已经有订单生成了并且未付款的)
			Map<String, Object>  map2 = this.findOneForJdbc("select o.id,o.order_no from tcsb_order o  where o.desk_id=? and o.pay_status='0'", tcsbOrderVO.getDeskId());
			//如果有的情况
			if (map2!=null) {
				String orderId = (String) map2.get("id");
				String orderNo = (String) map2.get("order_no");
				//查询是否有朋友添加(多人订单)新菜品
					//多用户订单信息保存（每个人都有一份相同的订单）
					//根据deskId获取当前购物车小车用户信息
				TcsbShopCarEntity tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class,"deskId", tcsbOrderVO.getDeskId());
				List<TcsbUserCarEntity> tcsbUserCarEntity = this.findByProperty(TcsbUserCarEntity.class, "carId", tcsbShopCarEntity.getId());
				
					if(StringUtil.isNotEmpty(tcsbUserCarEntity)){
						for (TcsbUserCarEntity tcsbUserCarEntity2 : tcsbUserCarEntity) {
							String userorderhql = "from TcsbUserOrderEntity where userId='"+tcsbUserCarEntity2.getUserId()+"' and orderId='"+orderId+"'";
							TcsbUserOrderEntity tcsbUserOrderEntitycheck = this.singleResult(userorderhql);
							if(tcsbUserOrderEntitycheck == null){
								TcsbUserOrderEntity tcsbUserOrderEntity = new TcsbUserOrderEntity();
								tcsbUserOrderEntity.setOrderId(orderId);
								tcsbUserOrderEntity.setUserId(tcsbUserCarEntity2.getUserId());
								tcsbUserOrderEntity.setCreateDate(new Date());
								tcsbUserOrderEntity.setPayStatus("0");
								this.save(tcsbUserOrderEntity);
							}
						}
					}
				
				//更新新购物车的信息到原订单里
				//获取旧订单
				TcsbOrderEntity tcsbOrderEntity = this.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
				//1.查询出数据库的明细数据-订单项管理
			    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND orderId = ? ";
			    List<TcsbOrderItemEntity> tcsbOrderItemOldList = this.findHql(hql0,orderId);
			    List<TcsbOrderItemVO>  tcsbOrderItemVOs = tcsbOrderVO.getTcsbOrderItemVOs();
			  //2.筛选更新明细数据-订单项管理
				if(tcsbOrderVO.getTcsbOrderItemVOs()!=null&&tcsbOrderVO.getTcsbOrderItemVOs().size()>0){
					
					//后台接单用的数据
					TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
					tcsbSubOrderEntity.setShopId(tcsbOrderVO.getShopId());
					tcsbSubOrderEntity.setDeskId(tcsbOrderVO.getDeskId());
					tcsbSubOrderEntity.setTotalPrice(Double.parseDouble(tcsbOrderVO.getTotalMoney()));
					
					tcsbSubOrderEntity.setNote(tcsbOrderVO.getNote());
					tcsbSubOrderEntity.setTaste(tcsbOrderVO.getTaste());
					tcsbSubOrderEntity.setPeople(tcsbOrderVO.getPeople());
					tcsbSubOrderEntity.setIsRoom(tcsbOrderVO.getIsRoom());
					if(tcsbOrderVO.getIsJustNowServing().equals("2")){
						tcsbSubOrderEntity.setIsJustNowServing("备菜");
					}else{
						tcsbSubOrderEntity.setIsJustNowServing("立即上菜");
					}
					
					//设置默认值
					tcsbSubOrderEntity.setMethod(0);
					tcsbSubOrderEntity.setStatus(0);
					tcsbSubOrderEntity.setPayStatus("0");
					tcsbSubOrderEntity.setPayMethod("1");
					tcsbSubOrderEntity.setOrderIstake("N");
					//线上价格设置为零
					tcsbSubOrderEntity.setOnlinePrice(0.0);
					//线下收款默认为实际消费金额
					tcsbSubOrderEntity.setOfflinePrice(0.0);
					tcsbSubOrderEntity.setCreateTime(new Date());
					//设置订单号
					tcsbSubOrderEntity.setOrderNo(orderNoGenerate);
					
					this.save(tcsbSubOrderEntity);
					//设置接单号
					map.put("subOrderNo", tcsbSubOrderEntity.getId());
					
					
					int index = 0;
					String orderIdOld ="";
					String foodIdOld = "";
					for (int i = 0; i < tcsbOrderItemVOs.size(); i++) {
						boolean flag =false;
						for (TcsbOrderItemEntity oldE:tcsbOrderItemOldList) {
							if(StringUtil.isNotEmpty(tcsbOrderItemVOs.get(i).getFoodTasteFun())){
								if (tcsbOrderItemVOs.get(i).getFoodId().equals(oldE.getFoodId()) && tcsbOrderItemVOs.get(i).getFoodTasteFun().equals(oldE.getFoodTasteFun())) {
									flag = true;
									index = i;
									orderIdOld =oldE.getOrderId();
									foodIdOld = oldE.getFoodId();
									break;
								}
							}else{
								if (tcsbOrderItemVOs.get(i).getFoodId().equals(oldE.getFoodId())) {
									flag = true;
									index = i;
									orderIdOld =oldE.getOrderId();
									foodIdOld = oldE.getFoodId();
									break;
								}
							}
						}
					
						if (flag) {
							TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVOs.get(i).getFoodId());
							if(StringUtil.isNotEmpty(tcsbFoodEntity)){
								TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
								if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
									if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
										
										//生成订单项与子项订单项关联标志码
										String orderItemRelation =OrderNumberGenerateUtil.getOrderNumber();
										TcsbOrderItemEntity tcsbOrderItemEntity = new TcsbOrderItemEntity();
										tcsbOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVOs.get(i).getCount()));
										tcsbOrderItemEntity.setFoodId(tcsbOrderItemVOs.get(i).getFoodId());
										tcsbOrderItemEntity.setFoodTypeId(tcsbOrderItemVOs.get(i).getFoodTypeId());
										tcsbOrderItemEntity.setFoodTasteFun(tcsbOrderItemVOs.get(i).getFoodTasteFun());
										//获取食品单位设置食品的购买数量
										tcsbOrderItemEntity.setNum(1);
										tcsbOrderItemEntity.setPrice(tcsbOrderItemVOs.get(i).getPrice());
										tcsbOrderItemEntity.setOrderId(tcsbOrderEntity.getId());
										tcsbOrderItemEntity.setIsUpdatePrice(1);
										tcsbOrderItemEntity.setOrderItemRelation(orderItemRelation);
										this.save(tcsbOrderItemEntity);

										//保存用户子订单项
										TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
										tcsbUserOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVOs.get(i).getCount()));
										tcsbUserOrderItemEntity.setFoodId(tcsbOrderItemVOs.get(i).getFoodId());
										tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItemVOs.get(i).getFoodTypeId());
										
										tcsbUserOrderItemEntity.setPrice(tcsbOrderItemVOs.get(i).getPrice());
										tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
										tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItemVOs.get(i).getFoodTasteFun());
													tcsbUserOrderItemEntity.setNum(1);
													tcsbUserOrderItemEntity.setIsUpdatePrice(1);
										tcsbUserOrderItemEntity.setOrderItemRelation(orderItemRelation);
										this.save(tcsbUserOrderItemEntity);	
										
										
									}else{
										List<TcsbOrderItemEntity> tcsbOrderItemEntities = this.findHql("from TcsbOrderItemEntity where foodId=? and orderId=?", foodIdOld,orderIdOld);
										tcsbOrderItemEntities.get(0).setCount(tcsbOrderItemEntities.get(0).getCount()+tcsbOrderItemVOs.get(index).getCount());
										this.saveOrUpdate(tcsbOrderItemEntities.get(0));
										
										//保存用户子订单项
										TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
										tcsbUserOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVOs.get(i).getCount()));
										tcsbUserOrderItemEntity.setFoodId(tcsbOrderItemVOs.get(i).getFoodId());
										tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItemVOs.get(i).getFoodTypeId());
										
										tcsbUserOrderItemEntity.setPrice(tcsbOrderItemVOs.get(i).getPrice());
										tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
										tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItemVOs.get(i).getFoodTasteFun());
										
										//获取食品单位设置食品的购买数量
										//TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVO.getFoodId());
										if(StringUtil.isNotEmpty(tcsbFoodEntity)){
											//TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
											if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
												if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
													tcsbUserOrderItemEntity.setNum(1);
													tcsbUserOrderItemEntity.setIsUpdatePrice(1);
												}else{
													double d = tcsbUserOrderItemEntity.getCount();   
													int ii = (new Double(d)).intValue(); 
													tcsbUserOrderItemEntity.setNum(ii);
												}
											}else{
												double d = tcsbUserOrderItemEntity.getCount();   
												int ii = (new Double(d)).intValue(); 
												tcsbUserOrderItemEntity.setNum(ii);
											}
										}
										tcsbUserOrderItemEntity.setOrderItemRelation(null);
										this.save(tcsbUserOrderItemEntity);	
										
										
									}
								}else{
									List<TcsbOrderItemEntity> tcsbOrderItemEntities = this.findHql("from TcsbOrderItemEntity where foodId=? and orderId=?", foodIdOld,orderIdOld);
									tcsbOrderItemEntities.get(0).setCount(tcsbOrderItemEntities.get(0).getCount()+tcsbOrderItemVOs.get(index).getCount());
									this.saveOrUpdate(tcsbOrderItemEntities.get(0));
									//保存用户子订单项
									TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
									tcsbUserOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVOs.get(i).getCount()));
									tcsbUserOrderItemEntity.setFoodId(tcsbOrderItemVOs.get(i).getFoodId());
									tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItemVOs.get(i).getFoodTypeId());
									
									tcsbUserOrderItemEntity.setPrice(tcsbOrderItemVOs.get(i).getPrice());
									tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
									tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItemVOs.get(i).getFoodTasteFun());
									
									//获取食品单位设置食品的购买数量
									//TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVO.getFoodId());
									if(StringUtil.isNotEmpty(tcsbFoodEntity)){
										//TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
										if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
											if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
												tcsbUserOrderItemEntity.setNum(1);
												tcsbUserOrderItemEntity.setIsUpdatePrice(1);
											}else{
												double d = tcsbUserOrderItemEntity.getCount();   
												int ii = (new Double(d)).intValue(); 
												tcsbUserOrderItemEntity.setNum(ii);
											}
										}else{
											double d = tcsbUserOrderItemEntity.getCount();   
											int ii = (new Double(d)).intValue(); 
											tcsbUserOrderItemEntity.setNum(ii);
										}
									}
									tcsbUserOrderItemEntity.setOrderItemRelation(null);
									this.save(tcsbUserOrderItemEntity);	
								}
							}
						}else {
							
							
							//生成订单项与子项订单项关联标志码
							String orderItemRelation =OrderNumberGenerateUtil.getOrderNumber();
							
							TcsbOrderItemEntity tcsbOrderItemEntity = new TcsbOrderItemEntity();
							tcsbOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVOs.get(i).getCount()));
							tcsbOrderItemEntity.setFoodId(tcsbOrderItemVOs.get(i).getFoodId());
							tcsbOrderItemEntity.setFoodTypeId(tcsbOrderItemVOs.get(i).getFoodTypeId());
							tcsbOrderItemEntity.setFoodTasteFun(tcsbOrderItemVOs.get(i).getFoodTasteFun());
							//获取食品单位设置食品的购买数量
							TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVOs.get(i).getFoodId());
							if(StringUtil.isNotEmpty(tcsbFoodEntity)){
								TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
								if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
									if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
										tcsbOrderItemEntity.setNum(1);
										tcsbOrderItemEntity.setIsUpdatePrice(1);
									}else{
										double d = tcsbOrderItemEntity.getCount();   
										int num = (new Double(d)).intValue(); 
										tcsbOrderItemEntity.setNum(num);
									}
								}else{
									double d = tcsbOrderItemEntity.getCount();   
									int num = (new Double(d)).intValue(); 
									tcsbOrderItemEntity.setNum(num);
								}
							}
							tcsbOrderItemEntity.setPrice(tcsbOrderItemVOs.get(i).getPrice());
							tcsbOrderItemEntity.setOrderId(tcsbOrderEntity.getId());
							tcsbOrderItemEntity.setOrderItemRelation(orderItemRelation);
							this.save(tcsbOrderItemEntity);
							
							//保存用户子订单项
							TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
							tcsbUserOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVOs.get(i).getCount()));
							tcsbUserOrderItemEntity.setFoodId(tcsbOrderItemVOs.get(i).getFoodId());
							tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItemVOs.get(i).getFoodTypeId());
							
							tcsbUserOrderItemEntity.setPrice(tcsbOrderItemVOs.get(i).getPrice());
							tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
							tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItemVOs.get(i).getFoodTasteFun());
							
							//获取食品单位设置食品的购买数量
							//TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVO.getFoodId());
							if(StringUtil.isNotEmpty(tcsbFoodEntity)){
								TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
								if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
									if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
										tcsbUserOrderItemEntity.setNum(1);
										tcsbUserOrderItemEntity.setIsUpdatePrice(1);
									}else{
										double d = tcsbUserOrderItemEntity.getCount();   
										int ii = (new Double(d)).intValue(); 
										tcsbUserOrderItemEntity.setNum(ii);
									}
								}else{
									double d = tcsbUserOrderItemEntity.getCount();   
									int ii = (new Double(d)).intValue(); 
									tcsbUserOrderItemEntity.setNum(ii);
								}
							}
							tcsbUserOrderItemEntity.setOrderItemRelation(null);
							tcsbUserOrderItemEntity.setOrderItemRelation(orderItemRelation);
							this.save(tcsbUserOrderItemEntity);	
						}
					}
				}
				//重新查询出数据库的明细数据-订单项管理获取总共的金额
			    List<TcsbOrderItemEntity> tcsbOrderItemOldListNew = this.findHql(hql0,orderId);
			    for (int i = 0; i < tcsbOrderItemOldListNew.size(); i++) {
			    	double a = BigDecimalUtil.mul(tcsbOrderItemOldListNew.get(i).getPrice(), tcsbOrderItemOldListNew.get(i).getCount());
			    	totalMoney = BigDecimalUtil.add(totalMoney, a);
			    	totalMoney = Double.valueOf(BigDecimalUtil.numericRetentionDecimal(totalMoney+"", 2));
			    }
				//to do update 更新状态
				tcsbOrderEntity.setTotalPrice(totalMoney);
				//tcsbOrderEntity.setOfflinePrice(totalMoney);
				tcsbOrderEntity.setOrderIstake("N");
				this.saveOrUpdate(tcsbOrderEntity);
				//订单编号用于返回给前端
				map.put("orderNo", orderNo);
				
			}else {
				//生成第一份订单
				TcsbOrderEntity tcsbOrderEntity = new TcsbOrderEntity();
				tcsbOrderEntity.setShopId(tcsbOrderVO.getShopId());
				tcsbOrderEntity.setDeskId(tcsbOrderVO.getDeskId());
				
				//tcsbOrderEntity.setDisPrice(Double.parseDouble(tcsbOrderVO.getFinalMoney()));
				tcsbOrderEntity.setTotalPrice(Double.parseDouble(tcsbOrderVO.getTotalMoney()));
				//通用券和专用券和平台优惠默认为零
				//tcsbOrderEntity.setUniversalCouponPrice(Double.parseDouble(tcsbOrderVO.getUniversalCouponPrice()));
				//tcsbOrderEntity.setPlatformDiscountPrice(Double.parseDouble(tcsbOrderVO.getPlatformDiscountPrice()));
				//修改
				tcsbOrderEntity.setUniversalCouponPrice(0.0);
				tcsbOrderEntity.setPlatformDiscountPrice(0.0);
				tcsbOrderEntity.setSpecialCouponPrice(0.0);
				
				tcsbOrderEntity.setNote(tcsbOrderVO.getNote());
				tcsbOrderEntity.setTaste(tcsbOrderVO.getTaste());
				//tcsbOrderEntity.setPeople(tcsbOrderVO.getPeople());
				tcsbOrderEntity.setIsRoom(tcsbOrderVO.getIsRoom());
				
				//设置默认值
				tcsbOrderEntity.setMethod("0");
				tcsbOrderEntity.setStatus("0");
				tcsbOrderEntity.setPayStatus("0");
				tcsbOrderEntity.setPayMethod("1");
				tcsbOrderEntity.setOrderIstake("N");
				//线上价格设置为零
				tcsbOrderEntity.setOnlinePrice(0.0);
				//线下收款默认为实际消费金额
				tcsbOrderEntity.setOfflinePrice(totalMoney);
				tcsbOrderEntity.setCreateTime(new Date());
				//设置订单号
				tcsbOrderEntity.setOrderNo(orderNoGenerate);
				this.save(tcsbOrderEntity);
				
				
				//后台接单用的数据
				TcsbSubOrderEntity tcsbSubOrderEntity = new TcsbSubOrderEntity();
				tcsbSubOrderEntity.setShopId(tcsbOrderVO.getShopId());
				tcsbSubOrderEntity.setDeskId(tcsbOrderVO.getDeskId());
				tcsbSubOrderEntity.setTotalPrice(Double.parseDouble(tcsbOrderVO.getTotalMoney()));
				
				tcsbSubOrderEntity.setNote(tcsbOrderVO.getNote());
				tcsbSubOrderEntity.setTaste(tcsbOrderVO.getTaste());
				tcsbSubOrderEntity.setPeople(tcsbOrderVO.getPeople());
				tcsbSubOrderEntity.setIsRoom(tcsbOrderVO.getIsRoom());
				if(tcsbOrderVO.getIsJustNowServing().equals("2")){
					tcsbSubOrderEntity.setIsJustNowServing("备菜");
				}else{
					tcsbSubOrderEntity.setIsJustNowServing("立即上菜");
				}
				//设置默认值
				tcsbSubOrderEntity.setMethod(0);
				tcsbSubOrderEntity.setStatus(0);
				tcsbSubOrderEntity.setPayStatus("0");
				tcsbSubOrderEntity.setPayMethod("1");
				tcsbSubOrderEntity.setOrderIstake("N");
				//线上价格设置为零
				tcsbSubOrderEntity.setOnlinePrice(0.0);
				//线下收款默认为实际消费金额
				tcsbSubOrderEntity.setOfflinePrice(0.0);
				tcsbSubOrderEntity.setCreateTime(new Date());
				//设置订单号
				tcsbSubOrderEntity.setOrderNo(orderNoGenerate);
				
				this.save(tcsbSubOrderEntity);
				//返回接单订单号
				map.put("subOrderNo", tcsbSubOrderEntity.getId());
				
				
				for (TcsbOrderItemVO tcsbOrderItemVO : tcsbOrderVO.getTcsbOrderItemVOs()) {
					//生成订单项与子项订单项关联标志码
					String orderItemRelation =OrderNumberGenerateUtil.getOrderNumber();
					//保存订单项
					TcsbOrderItemEntity tcsbOrderItemEntity = new TcsbOrderItemEntity();
					tcsbOrderItemEntity.setCount(Double.valueOf(tcsbOrderItemVO.getCount()));
					tcsbOrderItemEntity.setFoodId(tcsbOrderItemVO.getFoodId());
					tcsbOrderItemEntity.setFoodTypeId(tcsbOrderItemVO.getFoodTypeId());
					tcsbOrderItemEntity.setFoodTasteFun(tcsbOrderItemVO.getFoodTasteFun());
					//获取食品单位设置食品的购买数量
					TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVO.getFoodId());
					if(StringUtil.isNotEmpty(tcsbFoodEntity)){
						TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
						if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
							if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
								tcsbOrderItemEntity.setNum(1);
								tcsbOrderItemEntity.setIsUpdatePrice(1);
							}else{
								double d = tcsbOrderItemEntity.getCount();   
								int i = (new Double(d)).intValue(); 
								tcsbOrderItemEntity.setNum(i);
							}
						}else{
							double d = tcsbOrderItemEntity.getCount();   
							int i = (new Double(d)).intValue(); 
							tcsbOrderItemEntity.setNum(i);
						}
					}
					tcsbOrderItemEntity.setPrice(tcsbOrderItemVO.getPrice());
					tcsbOrderItemEntity.setOrderId(tcsbOrderEntity.getId());
					tcsbOrderItemEntity.setOrderItemRelation(orderItemRelation);
					this.save(tcsbOrderItemEntity);
					
					//保存用户子订单项
					TcsbUserOrderItemEntity tcsbUserOrderItemEntity = new TcsbUserOrderItemEntity();
					tcsbUserOrderItemEntity.setCount(tcsbOrderItemVO.getCount());
					tcsbUserOrderItemEntity.setFoodId(tcsbOrderItemVO.getFoodId());
					tcsbUserOrderItemEntity.setFoodTypeId(tcsbOrderItemVO.getFoodTypeId());
					
					tcsbUserOrderItemEntity.setPrice(tcsbOrderItemVO.getPrice());
					tcsbUserOrderItemEntity.setOrderId(tcsbSubOrderEntity.getId());
					tcsbUserOrderItemEntity.setFoodTasteFun(tcsbOrderItemVO.getFoodTasteFun());
					
					//获取食品单位设置食品的购买数量
					//TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbOrderItemVO.getFoodId());
					if(StringUtil.isNotEmpty(tcsbFoodEntity)){
						TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
						if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
							if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
								tcsbUserOrderItemEntity.setNum(1);
								tcsbUserOrderItemEntity.setIsUpdatePrice(1);
							}else{
								double d = tcsbUserOrderItemEntity.getCount();   
								int i = (new Double(d)).intValue(); 
								tcsbUserOrderItemEntity.setNum(i);
							}
						}else{
							double d = tcsbUserOrderItemEntity.getCount();   
							int i = (new Double(d)).intValue(); 
							tcsbUserOrderItemEntity.setNum(i);
						}
					}
					tcsbUserOrderItemEntity.setOrderItemRelation(orderItemRelation);
					this.save(tcsbUserOrderItemEntity);	
				}
				//多用户订单信息保存
				//TODO
				/**
				 * mar_x
				 */
				//根据deskId获取当前购物车小车用户信息
				TcsbShopCarEntity tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class,"deskId", tcsbOrderVO.getDeskId());
				List<TcsbUserCarEntity> tcsbUserCarEntity = this.findByProperty(TcsbUserCarEntity.class, "carId", tcsbShopCarEntity.getId());
				
				if(StringUtil.isNotEmpty(tcsbUserCarEntity)){
					for (TcsbUserCarEntity tcsbUserCarEntity2 : tcsbUserCarEntity) {
						String userorderhql = "from TcsbUserOrderEntity where userId='"+tcsbUserCarEntity2.getUserId()+"' and orderId='"+tcsbOrderEntity.getId()+"'";
						TcsbUserOrderEntity tcsbUserOrderEntitycheck = this.singleResult(userorderhql);
						if(tcsbUserOrderEntitycheck == null){
							TcsbUserOrderEntity tcsbUserOrderEntity = new TcsbUserOrderEntity();
							tcsbUserOrderEntity.setOrderId(tcsbOrderEntity.getId());
							tcsbUserOrderEntity.setUserId(tcsbUserCarEntity2.getUserId());
							tcsbUserOrderEntity.setCreateDate(new Date());
							tcsbUserOrderEntity.setPayStatus("0");
							this.save(tcsbUserOrderEntity);
						}
					}
				}
				map.put("orderNo", tcsbOrderEntity.getOrderNo());
				} 
			
			
			
			
		/*	for (TcsbOrderItemVO tcsbOrderItemVO : tcsbOrderVO.getTcsbOrderItemVOs()) {
				
			}*/
			//清空购物车
			TcsbShopCarEntity tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", tcsbOrderVO.getDeskId());
			this.executeSql("delete from tcsb_shop_car where desk_id = ?", tcsbOrderVO.getDeskId());
				this.executeSql("delete from tcsb_shop_car_item where shop_car = ?", tcsbShopCarEntity.getId());
				this.executeSql("delete from tcsb_user_car where car_id = ?", tcsbShopCarEntity.getId());
			}catch (Exception e) {
				e.printStackTrace();
		}
		return map;
	}


	@Override
	public List<TcsbOrderVO> getMyOrder(String userId, String status) {
		List<TcsbOrderVO> tcsbOrderVOs = new ArrayList<>();
		
		if (status.equals("3")) {
			status="0";
		}
		if (status.equals("4")) {
			status="1";
		}
		List<TcsbUserOrderEntity> tcsbUserOrderEntities = this.findHql("from TcsbUserOrderEntity where userId = ? order by createDate desc", userId);
		for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderEntities) {
			String orderId = tcsbUserOrderEntity.getOrderId();
			System.out.println("orderId======"+orderId+"status===="+status);
			//查询订单信息和店铺信息
			Map<String, Object> tcsbOrdermap = this.findOneForJdbc("select o.total_price as totalPrice,o.order_no as orderNo,o.desk_id as deskId,s.name,s.headImg,s.id from tcsb_order o left join tcsb_shop s on o.shop_id = s.id where o.id = ? and o.pay_status =? order by o.create_time desc", orderId,status);
			
			boolean isComment = false;
			
			if (tcsbOrdermap!=null) {
				//付款完要查询是否有评论
				if (status.equals("1")) {
					String shopEvaluatehql = "from TcsbShopEvaluateEntity where userId='"+userId+"' and shopId='"+(String)tcsbOrdermap.get("id")+"' and orderId='"+orderId+"'";
					List<TcsbShopEvaluateEntity> TcsbShopEvaluateList = this.findByQueryString(shopEvaluatehql);
							//List<Map<String, Object>> shopEvaluateMaps = systemService.findHql("from TcsbShopEvaluateEntity where userId=? and shopId=? and orderId=？", userId,tcsbOrderEntity.getShopId(),tcsbOrderEntity.getId());
					if (TcsbShopEvaluateList.size()>0) {
						isComment = true;
					}
					/*List<TcsbShopEvaluateEntity> tcsbShopEvaluateEntities =this.findHql("from TcsbShopEvaluateEntity where shopId =? and userId = ? and orderId=?", (String)tcsbOrdermap.get("id"),userId,orderId);
					if (!tcsbShopEvaluateEntities.isEmpty()) {
						isComment = true;
					}*/
				}
				TcsbOrderVO tcsbOrderVO = new TcsbOrderVO();
				tcsbOrderVO.setIsComment(isComment);
				tcsbOrderVO.setOrderNo((String)tcsbOrdermap.get("orderNo"));
				tcsbOrderVO.setDeskId((String)tcsbOrdermap.get("deskId"));
				tcsbOrderVO.setShopId((String)tcsbOrdermap.get("id"));
				
				//tcsbOrderVO.setFinalMoney(tcsbOrdermap.get("finalMoney").toString());
				tcsbOrderVO.setTotalMoney((String)tcsbOrdermap.get("totalPrice").toString());
				tcsbOrderVO.setShopName((String)tcsbOrdermap.get("name"));
				tcsbOrderVO.setShopImg((String)tcsbOrdermap.get("headImg"));
				//根据订单号查找订单项信息
				List<TcsbOrderItemVO> tcsbOrderItemVOs = new ArrayList<>();
				List<Map<String, Object>> tcsbOrderItemsMaps = new ArrayList<>();
				tcsbOrderItemsMaps = this.findForJdbc("select i.count,i.price,f.name from tcsb_order_item i left join tcsb_order o on i.order_id = o.id left join tcsb_food f on i.food_id = f.id  where o.id= ?", orderId);
				if (!tcsbOrderItemsMaps.isEmpty()) {
					for (Map<String, Object> tcsbOrderItemsMap : tcsbOrderItemsMaps) {
						TcsbOrderItemVO tcsbOrderItemVO = new TcsbOrderItemVO();
						tcsbOrderItemVO.setCount((Double)tcsbOrderItemsMap.get("count"));
						tcsbOrderItemVO.setPrice((Double)tcsbOrderItemsMap.get("price"));
						tcsbOrderItemVO.setFoodName((String)tcsbOrderItemsMap.get("name"));
						tcsbOrderItemVOs.add(tcsbOrderItemVO);
					}
				}
				tcsbOrderVO.setShopCarItemsSize(tcsbOrderItemsMaps.size());
				tcsbOrderVO.setTcsbOrderItemVOs(tcsbOrderItemVOs);
				tcsbOrderVOs.add(tcsbOrderVO);
			}
		}
		return tcsbOrderVOs;
	}

	@Override
	public List<OrderStatisticVo> getOrderStatistic(String startTime,String endTime) {
		// TODO Auto-generated method stub
				String sql = "SELECT DATE_FORMAT( create_date, '%Y-%m-%d' ) as create_date , sum( total_price ) as total_price"
						+ " FROM tcsb_order create_date BETWEEN '"+startTime
						+"' AND '"+endTime
						+"' GROUP BY DATE_FORMAT( create_date, '%Y-%m-%d' ) ";
				return commonDao.findListbySql(sql);
	}

	@Override
	public String saveOrderReturnOrderParentId(AppletRequestParamVo param) {
		
		//查看该桌位是否有存在正在使用中的订单
		TcsbOrderParentEntity tcsbOrderParentEntity = queryHasOrderParent(param);
		String orderParentId = "";
		boolean toAdd = false;
		if(StringUtil.isNotEmpty(tcsbOrderParentEntity)){
			if(tcsbOrderParentEntity.getOrderLock().equals("0")){
				orderParentId = tcsbOrderParentEntity.getId();
				//存在正在使用的桌位订单
				//添加子订单
				TcsbOrderEntity order = this.getOrder(param.getDeskId());
				if(StringUtil.isNotEmpty(order)){
					//存在未支付的子订单
					//创建接单信息
					TcsbSubOrderEntity subOrder = this.createSubOrderReturnSubId(param, order.getId());
					//创建子订单项-创建接单子项
					toAdd = this.addOrderItemAndSubOrderItem(subOrder.getId(), order);
				}else{
					//不存在未支付的子订单
					//创建新的子订单信息
					TcsbOrderEntity newOrder = this.createOrder(param, tcsbOrderParentEntity.getId());
					//添加接单项
					TcsbSubOrderEntity subOrder = this.createSubOrderReturnSubId(param, newOrder.getId());
					//创建子订单项-创建接单子项
					toAdd = this.addOrderItemAndSubOrderItem(subOrder.getId(), newOrder);
				}
			}
		}else{
			//不存在使用中的订单
			TcsbOrderParentEntity newOrderParent = this.createOrderParent(param);
			orderParentId = newOrderParent.getId();
			//创建子订单信息
			TcsbOrderEntity newOrder = this.createOrder(param, newOrderParent.getId());
			//添加接单项
			TcsbSubOrderEntity subOrder = this.createSubOrderReturnSubId(param, newOrder.getId());
			//创建子订单项-创建接单子项
			toAdd = this.addOrderItemAndSubOrderItem(subOrder.getId(), newOrder);
		}////0空闲1预定2未下单3用餐中
		if(toAdd){
			TcsbDeskEntity desk = this.get(TcsbDeskEntity.class, param.getDeskId());
			desk.setStatus("3");
			this.saveOrUpdate(desk);
		}
		return orderParentId;
	}

	private TcsbOrderParentEntity createOrderParent(AppletRequestParamVo param) {
		TcsbOrderParentEntity newtop = new TcsbOrderParentEntity();
		newtop.setDeskId(param.getDeskId());
		newtop.setShopId(param.getShopId());
		newtop.setOrderStatus("1");
		newtop.setOrderLock("0");
		newtop.setFlag("0");
		newtop.setCreateDate(new Date());
		newtop.setFrontMoney(0.0);
		newtop.setPeople(param.getPeopleNum());
		this.save(newtop);
		return newtop;	}

	private TcsbOrderEntity createOrder(AppletRequestParamVo param, String orderParentId) {
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
		tcsbOrderEntity.setPayMethod("0");
		tcsbOrderEntity.setOrderIstake("N");
		tcsbOrderEntity.setOnlinePrice(0.0);//线上价格设置为零
		tcsbOrderEntity.setOfflinePrice(0.0);//线下收款默认为实际消费金额
		tcsbOrderEntity.setCreateTime(new Date());
		tcsbOrderEntity.setCreateDate(new Date());
		tcsbOrderEntity.setOrderParentId(orderParentId);//
		this.save(tcsbOrderEntity);
		return tcsbOrderEntity;
	}

	private boolean addOrderItemAndSubOrderItem(String suborderId, TcsbOrderEntity order) {
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
				 if (StringUtil.isNotEmpty(shopCar2.getStandardId()))
		          {
		            TcsbFoodStandardEntity tfs = this.get(TcsbFoodStandardEntity.class, shopCar2.getStandardId());
		            tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun() + "/" + tfs.getName());
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
		              tcsbOrderItem.setFoodTasteFun(shopCar2.getFoodTasteFun() + "/" + tfs.getName());
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

	private TcsbSubOrderEntity createSubOrderReturnSubId(
			AppletRequestParamVo param, String orderId) {
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

	private TcsbOrderEntity getOrder(String deskId) {
		String tcsbOrder = "from TcsbOrderEntity where deskId='"+deskId+"' and payStatus=0";
		TcsbOrderEntity tcsbOrderEntity = this.singleResult(tcsbOrder);
		return tcsbOrderEntity;
	}

	private TcsbOrderParentEntity queryHasOrderParent(AppletRequestParamVo param) {
		String hql = "from TcsbOrderParentEntity where deskId='"+param.getDeskId()+"' and orderStatus='1'";
		TcsbOrderParentEntity tcsbOrderParentEntity = this.singleResult(hql);
		return tcsbOrderParentEntity;
	}

	@Override
	public Map<String, Object> getMoneyByShopCar(AppletRequestParamVo param) {
		TcsbShopCarEntity  tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", param.getDeskId());
		//获取购物车列表
		List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where shopCar =? ", tcsbShopCarEntity.getId());
		
		Double price =0.0;
		for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
			Integer num = tcsbShopCarItemEntity.getCount();
			Double money =0.0;
			if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getStandardId())) {
				//根据规格ID获取实际的价格
				TcsbFoodStandardEntity tcsbFoodStandardEntity = this.get(TcsbFoodStandardEntity.class, tcsbShopCarItemEntity.getStandardId());
				money = BigDecimalUtil.mul(tcsbFoodStandardEntity.getPrice(), num);
			}
			else {
				TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
				money = BigDecimalUtil.mul(tcsbFoodEntity.getPrice(), num);
			}
			price = BigDecimalUtil.add(price, money);
		}
		Double payMoney =0.0;
		payMoney = price;
		//打折情况
		//获取优惠活动
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = this.findByProperty(TcsbDiscountActivityEntity.class, "shopId", param.getShopId());
		if (!tcsbDiscountActivityEntities.isEmpty()) {
			List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
			for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
				TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
			}
			if (!tcsbDiscountActivityEntities.isEmpty()) {
				ListSorter.sort(tcsbShopFullcutTemplateEntities, "total","discount");
				for (int i = tcsbShopFullcutTemplateEntities.size()-1; i >= 0; i--) {
					if (payMoney>=tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
						payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
						if(payMoney<0){
							payMoney=0.01;
						}
						break;
					}
				}
			}
		}
		//获取优惠券信息
		List<TcsbCouponEntity> tcsbCouponEntities = this.findHql("from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?", param.getShopId(),param.getOpenId());
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		if (!tcsbCouponEntities.isEmpty()) {
			for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
				Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
				String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
				//专用券中有效的满减活动
				Calendar calDes = null;
				try {
					calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calSrc = null;
				try {
					calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
					tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
					tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
				};
			}
		}
			//立减
			if (!tcsbFullcutTemplateEntities.isEmpty()) {
				ListSorter.sort(tcsbFullcutTemplateEntities, "total","discount");
				outer:for (int i = tcsbFullcutTemplateEntities.size()-1; i >= 0; i--) {
					TcsbCouponEntity tcsbCouponEntity = this.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
					if(tcsbCouponEntity.getUseRange().equals("0")){
						if (payMoney>=tcsbFullcutTemplateEntities.get(i).getTotal()) {
							payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
							if(payMoney<0){
								payMoney=0.01;
							}
							break outer;
						}
					}else{
						//查找订单项中是否有该道菜
						TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
						for (int j = 0; j < tcsbShopCarItemEntities.size(); j++) {
							if (tcsbFoodEntity.getId().equals(tcsbShopCarItemEntities.get(j).getFoodId())) {
								payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
								if(payMoney<0){
									payMoney=0.01;
								}
								break outer;
							}
						}
					}
				}
			
			}
			Map<String , Object> map = new HashMap<>();
			map.put("payMoney", payMoney.toString());
		//查看是否有父订单号
		TcsbOrderParentEntity tcsbOrderParentEntity = getOrderParent(param.getDeskId());
		if(StringUtil.isNotEmpty(tcsbOrderParentEntity)){
			map.put("orderParentId",tcsbOrderParentEntity.getId());
			
		}else{
			//不存在使用中的订单先创建虚拟订单号
			TcsbOrderParentEntity newOrderParent = this.createOrderParent(param);
			map.put("orderParentId", newOrderParent.getId());
		}
		map.put("shopcarId", tcsbShopCarEntity.getId());
		return map;
	}
	//创建未被占用的父订单
	public TcsbOrderParentEntity getOrderParent(String deskId) {
		String hql = "from TcsbOrderParentEntity where deskId='"+deskId+"' and orderStatus='1'";
		return this.singleResult(hql);
	}

	@Override
	public Map<String, Object> getReservationTotalMoneyByShopCar(
			TcsbShopCarTempVO param) {
		
		//TcsbShopCarEntity  tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", param.getDeskId());
		//生成购物车(不含桌位信息的购物车)
		TcsbShopCarEntity tcsbShopCarEntity = new TcsbShopCarEntity();
		tcsbShopCarEntity.setCreateTime(new Date());
		tcsbShopCarEntity.setIsLock(0);
		this.save(tcsbShopCarEntity);
		
		//添加购物车项
		List<TcsbShopCarItemVo> tcsbShopCarItemList = JSON.parseArray(param.getShopCarItemVo(), TcsbShopCarItemVo.class);
		if(tcsbShopCarItemList.size()>0){
				//购物车不存在
				//创建购物车
			//TcsbShopCarItemEntity tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
				for (TcsbShopCarItemVo tcsbShopCarItemVo : tcsbShopCarItemList) {
					if(tcsbShopCarItemVo.isCurrentPrice()){
						//时价菜品
						if(tcsbShopCarItemVo.getCount()==1){
							savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
						}else{
							for(int i=0;i<tcsbShopCarItemVo.getCount();i++){
								savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
							}
						}
					}else{
						//非时价菜品
						if(tcsbShopCarItemVo.isFoodTaste()){
							//菜品有口味
							savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
						}else{
							//菜品无口味
							if(StringUtil.isNotEmpty(tcsbShopCarItemVo.getStandardId())){
								//菜品有规格
								savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
							}else{
								//菜品无规格
								String hql = "from TcsbShopCarItemEntity where foodId='"+tcsbShopCarItemVo.getFoodId()+"' and shopCar='"+tcsbShopCarEntity.getId()+"'";
								TcsbShopCarItemEntity addtcsbShopCarItem = this.singleResult(hql);
								if(StringUtil.isNotEmpty(addtcsbShopCarItem)){
									addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getCount());
									this.saveOrUpdate(addtcsbShopCarItem);
								}else{
									TcsbShopCarItemEntity savetcsbShopCarItem = new TcsbShopCarItemEntity();
								    savetcsbShopCarItem.setCount(tcsbShopCarItemVo.getCount());
								    savetcsbShopCarItem.setFoodId(tcsbShopCarItemVo.getFoodId());
								    savetcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun());
								    savetcsbShopCarItem.setShopCar(tcsbShopCarEntity.getId());
								    savetcsbShopCarItem.setCurrentPrice(tcsbShopCarItemVo.isCurrentPrice());
								    savetcsbShopCarItem.setStandardId(tcsbShopCarItemVo.getStandardId());
								    this.save(savetcsbShopCarItem);
									//savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
								}
								
							}
						}
					}
				}
		}
		//获取购物车列表
		List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where shopCar =? ", tcsbShopCarEntity.getId());
		Double price =0.0;
		Double nodisMoney =0.0;
		for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemEntities) {
			Integer num = tcsbShopCarItemEntity.getCount();
			Double money =0.0;
			TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
			if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getStandardId())) {
				//根据规格ID获取实际的价格
				TcsbFoodStandardEntity tcsbFoodStandardEntity = this.get(TcsbFoodStandardEntity.class, tcsbShopCarItemEntity.getStandardId());
				money = BigDecimalUtil.mul(tcsbFoodStandardEntity.getPrice(), num);
			}
			else {
				money = BigDecimalUtil.mul(tcsbFoodEntity.getPrice(), num);
			}
			price = BigDecimalUtil.add(price, money);
			
			if(tcsbFoodEntity.getIsDis().equals("N")){
				nodisMoney = BigDecimalUtil.add(nodisMoney, money);	
			}
		}  
		Double payMoney =0.0;
		payMoney = price;
		
		//
		Double disMoney = BigDecimalUtil.sub(price, nodisMoney);
		//打折情况
		//获取优惠活动
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = this.findByProperty(TcsbDiscountActivityEntity.class, "shopId", param.getShopId());
		if (!tcsbDiscountActivityEntities.isEmpty()) {
			List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
			for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
				TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
			}
			if (!tcsbDiscountActivityEntities.isEmpty()) {
				ListSorter.sort(tcsbShopFullcutTemplateEntities, "total","discount");
				for (int i = tcsbShopFullcutTemplateEntities.size()-1; i >= 0; i--) {
					if (disMoney>=tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
						payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
						disMoney = BigDecimalUtil.sub(disMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
						if(payMoney<0){
							payMoney=0.01;
						}
						break;
					}
				}
			}
		}
		//获取优惠券信息
		List<TcsbCouponEntity> tcsbCouponEntities = this.findHql("from TcsbCouponEntity where useStatus='0' and  shopId = ? and userId = ?", param.getShopId(),param.getOpenId());
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		if (!tcsbCouponEntities.isEmpty()) {
			for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
				Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
				String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
				//专用券中有效的满减活动
				Calendar calDes = null;
				try {
					calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Calendar calSrc = null;
				try {
					calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				} catch (ParseException e) {
					e.printStackTrace();
				}
				if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
					tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
					tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
				};
			}
		}
			//立减
			if (!tcsbFullcutTemplateEntities.isEmpty()) {
				ListSorter.sort(tcsbFullcutTemplateEntities, "total","discount");
				outer:for (int i = tcsbFullcutTemplateEntities.size()-1; i >= 0; i--) {
					TcsbCouponEntity tcsbCouponEntity = this.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
					if(tcsbCouponEntity.getUseRange().equals("0")){
						if (disMoney>=tcsbFullcutTemplateEntities.get(i).getTotal()) {
							payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
							disMoney = BigDecimalUtil.sub(disMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
							if(payMoney<0){
								payMoney=0.01;
							}
							break outer;
						}
					}else{
						//查找订单项中是否有该道菜
						TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
						for (int j = 0; j < tcsbShopCarItemEntities.size(); j++) {
							if (tcsbFoodEntity.getId().equals(tcsbShopCarItemEntities.get(j).getFoodId())) {
								payMoney = BigDecimalUtil.sub(payMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
								if(payMoney<0){
									payMoney=0.01;
								}
								break outer;
							}
						}
					}
				}
			
			}
		Map<String , Object> map = new HashMap<>();
		map.put("payMoney", payMoney.toString());
		//该用户有预约订单的情况 （不考虑追加新的预约订单项）
/*		List<TcsbDeskReservationEntity> tcsbDeskReservationEntities = this.findHql("from TcsbDeskReservationEntity where  userId = ? and shopId = ? and status = '1'",param.getOpenId(),param.getShopId());
		if (!tcsbDeskReservationEntities.isEmpty()) {
			map.put("reservationId", tcsbDeskReservationEntities.get(0).getId());
		}*/
		//生成预约空订单
		TcsbDeskReservationEntity tcsbDeskReservationEntity = new TcsbDeskReservationEntity();
		tcsbDeskReservationEntity.setIsDeposit("0");
		tcsbDeskReservationEntity.setPayStatus("0");
		tcsbDeskReservationEntity.setStatus("0");
		//不能删除的状态
		//tcsbDeskReservationEntity.setFlag("0");
		tcsbDeskReservationEntity.setShopId(param.getShopId());
		tcsbDeskReservationEntity.setUserId(param.getOpenId());
		this.save(tcsbDeskReservationEntity);
		map.put("reservationId", tcsbDeskReservationEntity.getId());
		map.put("shopcarId", tcsbShopCarEntity.getId());
		return map;
	}
	
	 public void savetcsbShopCarItem(TcsbShopCarItemVo tcsbShopCarItemVo, String carId)
	  {
	    TcsbShopCarItemEntity savetcsbShopCarItem = new TcsbShopCarItemEntity();
	    savetcsbShopCarItem.setCount(Integer.valueOf(1));
	    savetcsbShopCarItem.setFoodId(tcsbShopCarItemVo.getFoodId());
	    savetcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun());
	    savetcsbShopCarItem.setShopCar(carId);
	    savetcsbShopCarItem.setCurrentPrice(tcsbShopCarItemVo.isCurrentPrice());
	    savetcsbShopCarItem.setStandardId(tcsbShopCarItemVo.getStandardId());
	    this.save(savetcsbShopCarItem);
	  }
	

	@Override
	public Map<String, Object> getReservationDepositMoneyByShopCar(
			TcsbShopCarTempVO param) {
				//生成购物车(不含桌位信息的购物车)
				TcsbShopCarEntity tcsbShopCarEntity = new TcsbShopCarEntity();
				tcsbShopCarEntity.setCreateTime(new Date());
				tcsbShopCarEntity.setIsLock(0);
				this.save(tcsbShopCarEntity);
				//添加购物车项
				List<TcsbShopCarItemVo> tcsbShopCarItemList = JSON.parseArray(param.getShopCarItemVo(), TcsbShopCarItemVo.class);
				if(tcsbShopCarItemList.size()>0){
						for (TcsbShopCarItemVo tcsbShopCarItemVo : tcsbShopCarItemList) {
							if(tcsbShopCarItemVo.isCurrentPrice()){
								//时价菜品
								if(tcsbShopCarItemVo.getCount()==1){
									savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
								}else{
									for(int i=0;i<tcsbShopCarItemVo.getCount();i++){
										savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
									}
								}
							}else{
								//非时价菜品
								if(tcsbShopCarItemVo.isFoodTaste()){
									//菜品有口味
									savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
								}else{
									//菜品无口味
									if(StringUtil.isNotEmpty(tcsbShopCarItemVo.getStandardId())){
										//菜品有规格
										savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
									}else{
										//菜品无规格
										String hql = "from TcsbShopCarItemEntity where foodId='"+tcsbShopCarItemVo.getFoodId()+"' and shopCar='"+tcsbShopCarEntity.getId()+"'";
										TcsbShopCarItemEntity addtcsbShopCarItem = this.singleResult(hql);
										if(StringUtil.isNotEmpty(addtcsbShopCarItem)){
											addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getCount());
											this.saveOrUpdate(addtcsbShopCarItem);
										}else{
											TcsbShopCarItemEntity savetcsbShopCarItem = new TcsbShopCarItemEntity();
										    savetcsbShopCarItem.setCount(tcsbShopCarItemVo.getCount());
										    savetcsbShopCarItem.setFoodId(tcsbShopCarItemVo.getFoodId());
										    savetcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun());
										    savetcsbShopCarItem.setShopCar(tcsbShopCarEntity.getId());
										    savetcsbShopCarItem.setCurrentPrice(tcsbShopCarItemVo.isCurrentPrice());
										    savetcsbShopCarItem.setStandardId(tcsbShopCarItemVo.getStandardId());
										    this.save(savetcsbShopCarItem);
											//savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
										}
										
									}
								}
							}
						}
				}
			

				//获取店铺的押金
				TcsbShopEntity tcsbShopEntity = this.get(TcsbShopEntity.class, param.getShopId());
				Double payMoney = tcsbShopEntity.getReserveDeposit();
				Map<String , Object> map = new HashMap<>();
				map.put("payMoney", payMoney.toString());
				//该用户有预约订单的情况 （不考虑追加新的预约订单项）
		/*		List<TcsbDeskReservationEntity> tcsbDeskReservationEntities = this.findHql("from TcsbDeskReservationEntity where  userId = ? and shopId = ? and status = '1'",param.getOpenId(),param.getShopId());
				if (!tcsbDeskReservationEntities.isEmpty()) {
					map.put("reservationId", tcsbDeskReservationEntities.get(0).getId());
				}*/
				//生成预约空订单
				TcsbDeskReservationEntity tcsbDeskReservationEntity = new TcsbDeskReservationEntity();
				//设置押金的
				tcsbDeskReservationEntity.setIsDeposit("1");
				tcsbDeskReservationEntity.setPayStatus("0");
				tcsbDeskReservationEntity.setStatus("0");
				tcsbDeskReservationEntity.setShopId(param.getShopId());
				tcsbDeskReservationEntity.setUserId(param.getOpenId());
				this.save(tcsbDeskReservationEntity);
				//生成押金
				TcsbDepositDeltaEntity tcsbDepositDeltaEntity = new TcsbDepositDeltaEntity();
				tcsbDepositDeltaEntity.setCreateDate(new Date());
				tcsbDepositDeltaEntity.setOpenid(param.getOpenId());
				tcsbDepositDeltaEntity.setReserveDeposit(payMoney);
				tcsbDepositDeltaEntity.setShopId(param.getShopId());
				tcsbDepositDeltaEntity.setStatus(0);
				this.save(tcsbDepositDeltaEntity);
				tcsbDeskReservationEntity.setDepositDeltaId(tcsbDepositDeltaEntity.getId());
				this.saveOrUpdate(tcsbDeskReservationEntity);
				map.put("reservationId", tcsbDeskReservationEntity.getId());
				map.put("shopcarId", tcsbShopCarEntity.getId());
				return map;
	}


}