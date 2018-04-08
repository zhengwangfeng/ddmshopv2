package com.tcsb.platformdiscount.service.impl;
import com.tcsb.platformdiscount.service.TcsbPlatformDiscountServiceI;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.tcsb.platformdiscount.entity.TcsbPlatformDiscountEntity;
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

@Service("tcsbPlatformDiscountService")
@Transactional
public class TcsbPlatformDiscountServiceImpl extends CommonServiceImpl implements TcsbPlatformDiscountServiceI {

	
 	public void delete(TcsbPlatformDiscountEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(TcsbPlatformDiscountEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(TcsbPlatformDiscountEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(TcsbPlatformDiscountEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(TcsbPlatformDiscountEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(TcsbPlatformDiscountEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(TcsbPlatformDiscountEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("title", t.getTitle());
		map.put("cash", t.getCash());
		map.put("use_condition", t.getUseCondition());
		map.put("use_range", t.getUseRange());
		map.put("release_time_begin", t.getReleaseTimeBegin());
		map.put("release_time_end", t.getReleaseTimeEnd());
		map.put("expiry_date_begin", t.getExpiryDateBegin());
		map.put("expiry_date_end", t.getExpiryDateEnd());
		map.put("number", t.getNumber());
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
 	public String replaceVal(String sql,TcsbPlatformDiscountEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{title}",String.valueOf(t.getTitle()));
 		sql  = sql.replace("#{cash}",String.valueOf(t.getCash()));
 		sql  = sql.replace("#{use_condition}",String.valueOf(t.getUseCondition()));
 		sql  = sql.replace("#{use_range}",String.valueOf(t.getUseRange()));
 		sql  = sql.replace("#{release_time_begin}",String.valueOf(t.getReleaseTimeBegin()));
 		sql  = sql.replace("#{release_time_end}",String.valueOf(t.getReleaseTimeEnd()));
 		sql  = sql.replace("#{expiry_date_begin}",String.valueOf(t.getExpiryDateBegin()));
 		sql  = sql.replace("#{expiry_date_end}",String.valueOf(t.getExpiryDateEnd()));
 		sql  = sql.replace("#{number}",String.valueOf(t.getNumber()));
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