package com.tcsb.tcsborderparent.service.impl;
import com.tcsb.tcsborderparent.service.TcsbOrderParentServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
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

@Service("tcsbOrderParentService")
@Transactional
public class TcsbOrderParentServiceImpl extends CommonServiceImpl implements TcsbOrderParentServiceI {

	
 	public void delete(TcsbOrderParentEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TcsbOrderParentEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TcsbOrderParentEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbOrderParentEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbOrderParentEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TcsbOrderParentEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(TcsbOrderParentEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("shop_id", t.getShopId());
		map.put("desk_id", t.getDeskId());
		map.put("order_status", t.getOrderStatus());
		map.put("update_name", t.getUpdateName());
		map.put("update_date", t.getUpdateDate());
		map.put("update_by", t.getUpdateBy());
		map.put("create_name", t.getCreateName());
		map.put("create_by", t.getCreateBy());
		map.put("create_date", t.getCreateDate());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,TcsbOrderParentEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{shop_id}",String.valueOf(t.getShopId()));
 		sql  = sql.replace("#{desk_id}",String.valueOf(t.getDeskId()));
 		sql  = sql.replace("#{order_status}",String.valueOf(t.getOrderStatus()));
 		sql  = sql.replace("#{update_name}",String.valueOf(t.getUpdateName()));
 		sql  = sql.replace("#{update_date}",String.valueOf(t.getUpdateDate()));
 		sql  = sql.replace("#{update_by}",String.valueOf(t.getUpdateBy()));
 		sql  = sql.replace("#{create_name}",String.valueOf(t.getCreateName()));
 		sql  = sql.replace("#{create_by}",String.valueOf(t.getCreateBy()));
 		sql  = sql.replace("#{create_date}",String.valueOf(t.getCreateDate()));
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