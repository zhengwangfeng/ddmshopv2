package com.tcsb.shopcar.service.impl;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.hibernate.criterion.DetachedCriteria;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.platformdiscount.entity.TcsbPlatformDiscountEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopcar.VO.TcsbShopCarItemVO;
import com.tcsb.shopcar.VO.TcsbShopCarVO;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.usercar.entity.TcsbUserCarEntity;

@Service("tcsbShopCarService")
@Transactional
public class TcsbShopCarServiceImpl extends CommonServiceImpl implements TcsbShopCarServiceI {

	
 	public void delete(TcsbShopCarEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TcsbShopCarEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TcsbShopCarEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbShopCarEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbShopCarEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TcsbShopCarEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(TcsbShopCarEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("create_time", t.getCreateTime());
		map.put("desk_id", t.getDeskId());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TcsbShopCarEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{desk_id}",String.valueOf(t.getDeskId()));
 		sql  = sql.replace("#{UUID}",UUID.randomUUID().toString());
 		return sql;
 	}
 	
 	/**
	 * 执行JAVA增强
	 */
 	private void executeJavaExtend(String cgJavaType,String cgJavaValue,Map<String,Object> data) throws Exception {
 		if(StringUtil.isNotEmpty(cgJavaValue)){
			Object obj = null;
			try {
				if("class".equals(cgJavaType)){
					//因新增时已经校验了实例化是否可以成功，所以这块就不需要再做一次判断
					obj = MyClassLoader.getClassByScn(cgJavaValue).newInstance();
				}else if("spring".equals(cgJavaType)){
					obj = ApplicationContextUtil.getContext().getBean(cgJavaValue);
				}
				if(obj instanceof CgformEnhanceJavaInter){
					CgformEnhanceJavaInter javaInter = (CgformEnhanceJavaInter) obj;
					javaInter.execute(data);
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new Exception("执行JAVA增强出现异常！");
			} 
		}
 	}

	@Override
	public void addOrUpdateShopCar(String userId, String deskId, String foodId,
			String status) throws Exception {
			TcsbShopCarEntity tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
			TcsbShopCarItemEntity tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
			//做减的操作
			if (status.equals("0")) {
				//购物车不为空的时候
				if (tcsbShopCarEntity!=null) {
					List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where foodId = ? and shopCar = ?", foodId, tcsbShopCarEntity.getId());
					//购物车明细不为空的情况
					if (!tcsbShopCarItemEntities.isEmpty()) {
						tcsbShopCarItemEntity = tcsbShopCarItemEntities.get(0);
						if (tcsbShopCarItemEntity.getCount()>1) {
							tcsbShopCarItemEntity.setCount(tcsbShopCarItemEntity.getCount()-1);
						}else {
							this.delete(tcsbShopCarItemEntity);
						}
						
					}
				}
				
			}
			//做增加的操作
			if (status.equals("1")) {
				//购物车不存在的情况
				if (tcsbShopCarEntity==null) {
						tcsbShopCarEntity = new TcsbShopCarEntity();
						tcsbShopCarEntity.setCreateTime(new Date());
						tcsbShopCarEntity.setDeskId(deskId);
						this.save(tcsbShopCarEntity);
						tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
						tcsbShopCarItemEntity.setCount(1);
						tcsbShopCarItemEntity.setCreateTime(new Date());
						tcsbShopCarItemEntity.setFoodId(foodId);
						tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
						this.save(tcsbShopCarItemEntity);
						//保存多人购物信息
						TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
						tcsbUserCarEntity.setCreateTime(new Date());
						tcsbUserCarEntity.setUserId(userId);
						tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
						this.save(tcsbUserCarEntity);
				}else {
					//检测商品是否时价
					//新增
					TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, foodId);
					if(StringUtil.isNotEmpty(tcsbFoodEntity)){
						TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
						if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
							if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
								//时价
								tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
								tcsbShopCarItemEntity.setCount(1);
								tcsbShopCarItemEntity.setCreateTime(new Date());
								tcsbShopCarItemEntity.setFoodId(foodId);
								tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
								this.save(tcsbShopCarItemEntity);
								//查询是否已保存到多人购物信息中
								List<TcsbUserCarEntity> tcsbUserCarEntitys = this.findHql("from TcsbUserCarEntity where carId =? and userId =?", tcsbShopCarEntity.getId(),userId);
								//不存在就做增加
								if (tcsbUserCarEntitys.isEmpty()) {
									TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
									tcsbUserCarEntity.setCreateTime(new Date());
									tcsbUserCarEntity.setUserId(userId);
									tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
									this.save(tcsbUserCarEntity);
								}
							}else{
								//非时价
								List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where foodId = ? and shopCar = ?", foodId, tcsbShopCarEntity.getId());
								//新增
								if (!tcsbShopCarItemEntities.isEmpty()) {
									TcsbShopCarItemEntity tcsbShopCarItemEntity2  =tcsbShopCarItemEntities.get(0);
									tcsbShopCarItemEntity2.setCount(tcsbShopCarItemEntity2.getCount()+1);
									tcsbShopCarItemEntity2.setUpdateTime(new Date());
									this.saveOrUpdate(tcsbShopCarItemEntity2);
								}else {
									tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
									tcsbShopCarItemEntity.setCount(1);
									tcsbShopCarItemEntity.setCreateTime(new Date());
									tcsbShopCarItemEntity.setFoodId(foodId);
									tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
									this.save(tcsbShopCarItemEntity);
								}
								//查询是否已保存到多人购物信息中
								List<TcsbUserCarEntity> tcsbUserCarEntitys = this.findHql("from TcsbUserCarEntity where carId =? and userId =?", tcsbShopCarEntity.getId(),userId);
								//不存在就做增加
								if (tcsbUserCarEntitys.isEmpty()) {
									TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
									tcsbUserCarEntity.setCreateTime(new Date());
									tcsbUserCarEntity.setUserId(userId);
									tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
									this.save(tcsbUserCarEntity);
								}
							}
						}else{
							List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where foodId = ? and shopCar = ?", foodId, tcsbShopCarEntity.getId());
							//新增
							if (!tcsbShopCarItemEntities.isEmpty()) {
								TcsbShopCarItemEntity tcsbShopCarItemEntity2  =tcsbShopCarItemEntities.get(0);
								tcsbShopCarItemEntity2.setCount(tcsbShopCarItemEntity2.getCount()+1);
								tcsbShopCarItemEntity2.setUpdateTime(new Date());
								this.saveOrUpdate(tcsbShopCarItemEntity2);
							}else {
								tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
								tcsbShopCarItemEntity.setCount(1);
								tcsbShopCarItemEntity.setCreateTime(new Date());
								tcsbShopCarItemEntity.setFoodId(foodId);
								tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
								this.save(tcsbShopCarItemEntity);
							}
							//查询是否已保存到多人购物信息中
							List<TcsbUserCarEntity> tcsbUserCarEntitys = this.findHql("from TcsbUserCarEntity where carId =? and userId =?", tcsbShopCarEntity.getId(),userId);
							//不存在就做增加
							if (tcsbUserCarEntitys.isEmpty()) {
								TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
								tcsbUserCarEntity.setCreateTime(new Date());
								tcsbUserCarEntity.setUserId(userId);
								tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
								this.save(tcsbUserCarEntity);
							}
						}
					}			
				}
			}
		
	}

	
	
	/**
	 *	增加有规格商品的购车添加更新接口实现方法
	 *@param foodTasteFun 规格拼接串（1，2，3，...）
	 *
	 * mar_x
	 */
	@Override
	public void addOrUpdateTasteShopCar(String userId, String deskId, String foodId,
			String status,String foodTasteFun) throws Exception {
			TcsbShopCarEntity tcsbShopCarEntity = this.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
			TcsbShopCarItemEntity tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
			//做减的操作
			if (status.equals("0")) {
				//购物车不为空的时候
				if (tcsbShopCarEntity!=null) {
					String hql = "from TcsbShopCarItemEntity where foodId ='"+foodId+"' and shopCar ='"+tcsbShopCarEntity.getId()+"' and foodTasteFun='"+foodTasteFun+"'";
					List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findByQueryString(hql);
					//购物车明细不为空的情况
					if (!tcsbShopCarItemEntities.isEmpty()) {
						tcsbShopCarItemEntity = tcsbShopCarItemEntities.get(0);
						if (tcsbShopCarItemEntity.getCount()>1) {
							tcsbShopCarItemEntity.setCount(tcsbShopCarItemEntity.getCount()-1);
						}else {
							this.delete(tcsbShopCarItemEntity);
						}
						
					}
				}
				
			}
			//做增加的操作
			if (status.equals("1")) {
				//购物车不存在的情况
				if (tcsbShopCarEntity==null) {
						tcsbShopCarEntity = new TcsbShopCarEntity();
						tcsbShopCarEntity.setCreateTime(new Date());
						tcsbShopCarEntity.setDeskId(deskId);
						this.save(tcsbShopCarEntity);
						tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
						tcsbShopCarItemEntity.setCount(1);
						tcsbShopCarItemEntity.setCreateTime(new Date());
						tcsbShopCarItemEntity.setFoodId(foodId);
						tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
						tcsbShopCarItemEntity.setFoodTasteFun(foodTasteFun);
						this.save(tcsbShopCarItemEntity);
						//保存多人购物信息
						TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
						tcsbUserCarEntity.setCreateTime(new Date());
						tcsbUserCarEntity.setUserId(userId);
						tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
						this.save(tcsbUserCarEntity);
				}else {
					//检测商品是否时价
					//新增
					TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, foodId);
					if(StringUtil.isNotEmpty(tcsbFoodEntity)){
						TcsbFoodUnitEntity tcsbFoodUnitEntity =this.get(TcsbFoodUnitEntity.class, tcsbFoodEntity.getUnitId());
						if(StringUtil.isNotEmpty(tcsbFoodUnitEntity)){
							if(tcsbFoodUnitEntity.getIsfloat().equals("1")){
								//TODO
								tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
								tcsbShopCarItemEntity.setCount(1);
								tcsbShopCarItemEntity.setCreateTime(new Date());
								tcsbShopCarItemEntity.setFoodId(foodId);
								tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
								tcsbShopCarItemEntity.setFoodTasteFun(foodTasteFun);
								this.save(tcsbShopCarItemEntity);
								//查询是否已保存到多人购物信息中
								List<TcsbUserCarEntity> tcsbUserCarEntitys = this.findHql("from TcsbUserCarEntity where carId =? and userId =?", tcsbShopCarEntity.getId(),userId);
								//不存在就做增加
								if (tcsbUserCarEntitys.isEmpty()) {
									TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
									tcsbUserCarEntity.setCreateTime(new Date());
									tcsbUserCarEntity.setUserId(userId);
									tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
									this.save(tcsbUserCarEntity);
								}
							}else{
								String hql = "from TcsbShopCarItemEntity where foodId ='"+foodId+"' and shopCar ='"+tcsbShopCarEntity.getId()+"' and foodTasteFun='"+foodTasteFun+"'";
								List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findByQueryString(hql);
								//List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where foodId = ? and shopCar = ?", foodId, tcsbShopCarEntity.getId());
								//新增
								if (!tcsbShopCarItemEntities.isEmpty()) {
									TcsbShopCarItemEntity tcsbShopCarItemEntity2  =tcsbShopCarItemEntities.get(0);
									tcsbShopCarItemEntity2.setCount(tcsbShopCarItemEntity2.getCount()+1);
									tcsbShopCarItemEntity2.setUpdateTime(new Date());
									this.saveOrUpdate(tcsbShopCarItemEntity2);
								}else {
									tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
									tcsbShopCarItemEntity.setCount(1);
									tcsbShopCarItemEntity.setCreateTime(new Date());
									tcsbShopCarItemEntity.setFoodId(foodId);
									tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
									tcsbShopCarItemEntity.setFoodTasteFun(foodTasteFun);
									this.save(tcsbShopCarItemEntity);
								}
								//查询是否已保存到多人购物信息中
								List<TcsbUserCarEntity> tcsbUserCarEntitys = this.findHql("from TcsbUserCarEntity where carId =? and userId =?", tcsbShopCarEntity.getId(),userId);
								//不存在就做增加
								if (tcsbUserCarEntitys.isEmpty()) {
									TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
									tcsbUserCarEntity.setCreateTime(new Date());
									tcsbUserCarEntity.setUserId(userId);
									tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
									this.save(tcsbUserCarEntity);
								}
							}
						}else{
							String hql = "from TcsbShopCarItemEntity where foodId ='"+foodId+"' and shopCar ='"+tcsbShopCarEntity.getId()+"' and foodTasteFun='"+foodTasteFun+"'";
							List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findByQueryString(hql);
							//List<TcsbShopCarItemEntity> tcsbShopCarItemEntities = this.findHql("from TcsbShopCarItemEntity where foodId = ? and shopCar = ?", foodId, tcsbShopCarEntity.getId());
							//新增
							if (!tcsbShopCarItemEntities.isEmpty()) {
								TcsbShopCarItemEntity tcsbShopCarItemEntity2  =tcsbShopCarItemEntities.get(0);
								tcsbShopCarItemEntity2.setCount(tcsbShopCarItemEntity2.getCount()+1);
								tcsbShopCarItemEntity2.setUpdateTime(new Date());
								this.saveOrUpdate(tcsbShopCarItemEntity2);
							}else {
								tcsbShopCarItemEntity = new TcsbShopCarItemEntity();
								tcsbShopCarItemEntity.setCount(1);
								tcsbShopCarItemEntity.setCreateTime(new Date());
								tcsbShopCarItemEntity.setFoodId(foodId);
								tcsbShopCarItemEntity.setShopCar(tcsbShopCarEntity.getId());
								tcsbShopCarItemEntity.setFoodTasteFun(foodTasteFun);
								this.save(tcsbShopCarItemEntity);
							}
							//查询是否已保存到多人购物信息中
							List<TcsbUserCarEntity> tcsbUserCarEntitys = this.findHql("from TcsbUserCarEntity where carId =? and userId =?", tcsbShopCarEntity.getId(),userId);
							//不存在就做增加
							if (tcsbUserCarEntitys.isEmpty()) {
								TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
								tcsbUserCarEntity.setCreateTime(new Date());
								tcsbUserCarEntity.setUserId(userId);
								tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
								this.save(tcsbUserCarEntity);
							}
						}
					}
					
					
					
					
				
					
					
				}
			}
		
	}
	
	@Override
	public Map<String , Object> getShopCar(String userId,String deskId, String shopId)
			throws Exception {
		Map<String , Object> map = new HashMap<>();
		//查询店铺名称和图片
		TcsbShopEntity tcsbShopEntity = this.get(TcsbShopEntity.class, shopId);
		map.put("shopName", tcsbShopEntity.getName());
		List<Map<String, Object>> maplist = this.findForJdbc("select i.food_id as foodId,i.count,i.foodTasteFun from tcsb_shop_car_item i left join tcsb_shop_car c on c.id=i.shop_car where c.desk_id = ?", deskId);
		Double totalMoney = 0.0;
		Double finalMoney = 0.0;
		for (Map<String, Object> mapItem : maplist) {
			//检测商品是否有规格
			String foodTasteFun = (String) mapItem.get("foodTasteFun");
			if(StringUtil.isNotEmpty(foodTasteFun)){
				mapItem.put("istaste", true);
			}else{
				mapItem.put("istaste", false);
			}
			String foodId = (String) mapItem.get("foodId");
			//根据食物ID获取食物的价格
			TcsbFoodEntity tcsbFoodEntity = this.get(TcsbFoodEntity.class, foodId);
			mapItem.put("name", tcsbFoodEntity.getName());
			mapItem.put("foodTypeId", tcsbFoodEntity.getFoodTypeId());
			mapItem.put("price", tcsbFoodEntity.getPrice());
			totalMoney += (Integer)mapItem.get("count")*tcsbFoodEntity.getPrice();
		}
		map.put("order", maplist);
		map.put("orderItemsSize", maplist.size());
		map.put("totalMoney", totalMoney);
		//todo优化---begin
		List<TcsbPlatformDiscountEntity > tcsbPlatformDiscountEntitys = this.getList(TcsbPlatformDiscountEntity.class);
		if (tcsbPlatformDiscountEntitys.isEmpty()) {
			map.put("platformDiscountPrice", "0");
		}
		//todo优化----end
		//获取优惠活动
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = this.findByProperty(TcsbDiscountActivityEntity.class, "shopId", shopId);
		String shopDiscountMsg = "";
		if (!tcsbDiscountActivityEntities.isEmpty()) {
			for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
				//update by jimmy begin
				TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				shopDiscountMsg =shopDiscountMsg+ "满"+ tcsbFullcutTemplateEntity.getTotal() + "减" + tcsbFullcutTemplateEntity.getDiscount() + ",";
				//update by jimmy end
			}
			if (!StringUtil.isEmpty(shopDiscountMsg)) {
				shopDiscountMsg = shopDiscountMsg.substring(0, shopDiscountMsg.length()-1);
			}
			map.put("shopDiscountMsg", shopDiscountMsg);
		}else {
			map.put("shopDiscountMsg", "无");
		}
		//获取优惠卷
		String hql = "from TcsbCouponEntity where shopId='"+shopId+"' and userId='"+userId+"' and useStatus='0'";
		List<TcsbCouponEntity> tcsbCouponEntities = this.findByQueryString(hql);
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		String shopUserDiscountMsg = "";
		if (!tcsbCouponEntities.isEmpty()) {
			for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
				TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = this.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
				Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
				String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
				int days = 0;
				//todo
				if (dateUnit.equals("year")) {
					days = userPeriod*DateUtils.getCurrentYearDays();
				}else if (dateUnit.equals("month")) {
					days = userPeriod*DateUtils.getCurrentMonthDay();
				}else {
					days = userPeriod;
				}
				//专用券中有效的满减活动
				Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
					//优化update by jimmy
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity2 = new TcsbFullcutTemplateEntity();
					MyBeanUtils.copyBeanNotNull2Bean(tcsbFullcutTemplateEntity, tcsbFullcutTemplateEntity2);
					tcsbFullcutTemplateEntity2.setCouponId(tcsbCouponEntity.getId());
					tcsbFullcutTemplateEntity2.setUseRange(tcsbCouponEntity.getUseRange());
					tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity2);
				};
			}
			if (!tcsbFullcutTemplateEntities.isEmpty()) {
				ListSorter.sort(tcsbFullcutTemplateEntities, "total","discount");
				for (TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity : tcsbFullcutTemplateEntities) {
					if ("0".equals(tcsbFullcutTemplateEntity.getUseRange())) {
						shopUserDiscountMsg = shopUserDiscountMsg+ "满"+ tcsbFullcutTemplateEntity.getTotal() + "减" + tcsbFullcutTemplateEntity.getDiscount() + ",";
					}else {
						//获取所属专用券
						TcsbCouponEntity tcsbCouponEntity = this.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntity.getCouponId());
						TcsbFoodEntity tcsbFoodEntity = get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
						shopUserDiscountMsg = shopUserDiscountMsg+ "满"+ tcsbFullcutTemplateEntity.getTotal() + "减" + tcsbFullcutTemplateEntity.getDiscount() +"(只限"+ tcsbFoodEntity.getName()+ "),";
					}
				}
				map.put("shopUserDiscountMsg", shopUserDiscountMsg);
			}else {
				map.put("shopUserDiscountMsg", "无");
			}
		}
		
		
		Map<String, Object>  map2 = this.findOneForJdbc("select o.id,o.order_no from tcsb_order o  where o.desk_id=? and o.pay_status='0'", deskId);
		if(map2!=null){
			map.put("peopleshow", false);
		}else{
			map.put("peopleshow", true);
		}
		
		return map;
	}

	@Override
	public Boolean checkShopCarNew(TcsbShopCarVO tcsbShopCarVO)
			throws Exception {
		//是否是最新
		boolean flag = true;
		//根据deskId获取购物车信息
		List<Map<String, Object>> mapShopCarItem = this.findForJdbc("select i.food_id as foodId,i.count from tcsb_shop_car_item i left join tcsb_shop_car c on c.id=i.shop_car where c.desk_id = ?", tcsbShopCarVO.getDeskId());
		if (mapShopCarItem.isEmpty()) {
			flag = false;
		}else {
			if (tcsbShopCarVO.getTcsbShopCarItemVOs().isEmpty()) {
				flag = false;
			}else {
				List<TcsbShopCarItemVO>  tcsbShopCarItemVOs = tcsbShopCarVO.getTcsbShopCarItemVOs();
				if (tcsbShopCarItemVOs.size()==mapShopCarItem.size()) {
					for (int i = 0; i < tcsbShopCarItemVOs.size(); i++) {
						for (int j = 0; j < mapShopCarItem.size(); j++) {
							if (tcsbShopCarItemVOs.get(i).getFoodId().equals(mapShopCarItem.get(j).get("foodId"))) {
								if (!tcsbShopCarItemVOs.get(i).getCount().equals(mapShopCarItem.get(j).get("count"))) {
									flag = false;
									break;
								}
							}
						}
					}
				}
				else {
					flag = false;
				}
			}
		}
		return flag;
	}

	@Override
	public List<TcsbShopCarVO> getMyShopCar(String userId) throws Exception {
		List<TcsbShopCarVO> tcsbShopCarVOs = new ArrayList<>();
		//一个人每个商店可能都有购物车
		List<TcsbUserCarEntity> tcsbUserCarEntities = this.findHql("from TcsbUserCarEntity where userId = ?", userId);
		Double totalMoney = 0.0;
		Double finalMoney = 0.0;
		TcsbShopCarVO tcsbShopCarVO = new TcsbShopCarVO();
		List<TcsbShopCarItemVO> tcsbShopCarItemVOs = new ArrayList<>();
		if (!tcsbUserCarEntities.isEmpty()) {
			String shopId = "";
		    for (TcsbUserCarEntity tcsbUserCarEntity : tcsbUserCarEntities) {
				//根据订单号查找所有订单项信息
		    	tcsbShopCarVO.setTotalMoney(totalMoney);
		    	//查找购物车信息
		    	TcsbShopCarEntity tcsbShopCarEntity = this.get(TcsbShopCarEntity.class, tcsbUserCarEntity.getCarId());
		    	//根据购物车桌位信息查找店铺信息
		    	Map<String, Object> tcsbShopEntityMap = this.findOneForJdbc("select s.id,s.name,s.headImg from tcsb_shop s left join tcsb_desk d on s.id = d.shop_id where d.number = ?", tcsbShopCarEntity.getDeskId());
		    	tcsbShopCarVO.setShopImg((String)tcsbShopEntityMap.get("headImg"));
		    	tcsbShopCarVO.setShopName((String)tcsbShopEntityMap.get("name"));
		    	shopId = (String)tcsbShopEntityMap.get("id");
		    	//查找购物车明细
		    	List<Map<String, Object>> maps = this.findForJdbc("select i.count,f.price,i.food_id as foodId,f.name from tcsb_shop_car_item i left join tcsb_food f on i.food_id = f.id where i.shop_car = ? ", tcsbShopCarEntity.getId());
		    	tcsbShopCarVO.setShopCarItemsSize(maps.size());
		    	
		    	for (Map<String, Object> map : maps) {
		    		totalMoney = totalMoney+ (Integer)map.get("count")*(Double)map.get("price");
		    		TcsbShopCarItemVO tcsbShopCarItemVO = new TcsbShopCarItemVO();
		    		tcsbShopCarItemVO.setCount((Integer)map.get("count"));
		    		tcsbShopCarItemVO.setFoodName((String)map.get("name"));
		    		tcsbShopCarItemVO.setFoodId((String)map.get("foodId"));
		    		tcsbShopCarItemVOs.add(tcsbShopCarItemVO);
				}
		    	
		    }
		    //to do update
			tcsbShopCarVO.setTotalMoney(totalMoney);
			//tcsbShopCarVO.setFinalMoney(finalMoney);
			//tcsbShopCarVO.setTcsbShopCarItemVOs(tcsbShopCarItemVOs);
			tcsbShopCarVO.setTcsbOrderItemVOs(tcsbShopCarItemVOs);
			tcsbShopCarVOs.add(tcsbShopCarVO);
		}
		return tcsbShopCarVOs;
	}
}