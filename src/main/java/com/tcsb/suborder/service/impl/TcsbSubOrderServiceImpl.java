package com.tcsb.suborder.service.impl;
import com.tcsb.suborder.service.TcsbSubOrderServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("tcsbSubOrderService")
@Transactional
public class TcsbSubOrderServiceImpl extends CommonServiceImpl implements TcsbSubOrderServiceI {

	
 	public void delete(TcsbSubOrderEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TcsbSubOrderEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TcsbSubOrderEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbSubOrderEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbSubOrderEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TcsbSubOrderEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(TcsbSubOrderEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("order_no", t.getOrderNo());
		map.put("method", t.getMethod());
		map.put("shop_id", t.getShopId());
		map.put("desk_id", t.getDeskId());
		map.put("create_time", t.getCreateTime());
		map.put("status", t.getStatus());
		map.put("total_price", t.getTotalPrice());
		map.put("online_price", t.getOnlinePrice());
		map.put("offline_price", t.getOfflinePrice());
		map.put("pay_status", t.getPayStatus());
		map.put("pay_method", t.getPayMethod());
		map.put("eat_time", t.getEatTime());
		map.put("people", t.getPeople());
		map.put("note", t.getNote());
		map.put("is_room", t.getIsRoom());
		map.put("taste", t.getTaste());
		map.put("platform_discount_price", t.getPlatformDiscountPrice());
		map.put("special_coupon_price", t.getSpecialCouponPrice());
		map.put("universal_coupon_price", t.getUniversalCouponPrice());
		map.put("order_istake", t.getOrderIstake());
		map.put("update_name", t.getUpdateName());
		map.put("update_date", t.getUpdateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		map.put("user_id", t.getUserId());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TcsbSubOrderEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{order_no}",String.valueOf(t.getOrderNo()));
 		sql  = sql.replace("#{method}",String.valueOf(t.getMethod()));
 		sql  = sql.replace("#{shop_id}",String.valueOf(t.getShopId()));
 		sql  = sql.replace("#{desk_id}",String.valueOf(t.getDeskId()));
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{status}",String.valueOf(t.getStatus()));
 		sql  = sql.replace("#{total_price}",String.valueOf(t.getTotalPrice()));
 		sql  = sql.replace("#{online_price}",String.valueOf(t.getOnlinePrice()));
 		sql  = sql.replace("#{offline_price}",String.valueOf(t.getOfflinePrice()));
 		sql  = sql.replace("#{pay_status}",String.valueOf(t.getPayStatus()));
 		sql  = sql.replace("#{pay_method}",String.valueOf(t.getPayMethod()));
 		sql  = sql.replace("#{eat_time}",String.valueOf(t.getEatTime()));
 		sql  = sql.replace("#{people}",String.valueOf(t.getPeople()));
 		sql  = sql.replace("#{note}",String.valueOf(t.getNote()));
 		sql  = sql.replace("#{is_room}",String.valueOf(t.getIsRoom()));
 		sql  = sql.replace("#{taste}",String.valueOf(t.getTaste()));
 		sql  = sql.replace("#{platform_discount_price}",String.valueOf(t.getPlatformDiscountPrice()));
 		sql  = sql.replace("#{special_coupon_price}",String.valueOf(t.getSpecialCouponPrice()));
 		sql  = sql.replace("#{universal_coupon_price}",String.valueOf(t.getUniversalCouponPrice()));
 		sql  = sql.replace("#{order_istake}",String.valueOf(t.getOrderIstake()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
 		sql  = sql.replace("#{user_id}",String.valueOf(t.getUserId()));
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
}