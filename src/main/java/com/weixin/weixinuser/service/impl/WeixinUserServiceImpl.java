package com.weixin.weixinuser.service.impl;
import com.weixin.weixinuser.service.WeixinUserServiceI;

import jodd.bean.BeanUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.jeecgframework.core.common.service.impl.CommonServiceImpl;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.io.Serializable;
import org.jeecgframework.core.util.ApplicationContextUtil;
import org.jeecgframework.core.util.MyClassLoader;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.cgform.enhance.CgformEnhanceJavaInter;

@Service("weixinUserService")
@Transactional
public class WeixinUserServiceImpl extends CommonServiceImpl implements WeixinUserServiceI {

	
 	public void delete(WeixinUserEntity entity) throws Exception{
 		super.delete(entity);
 		//执行删除操作增强业务
		this.doDelBus(entity);
 	}
 	
 	public Serializable save(WeixinUserEntity entity) throws Exception{
 		Serializable t = super.save(entity);
 		//执行新增操作增强业务
 		this.doAddBus(entity);
 		return t;
 	}
 	
 	public void saveOrUpdate(WeixinUserEntity entity) throws Exception{
 		super.saveOrUpdate(entity);
 		//执行更新操作增强业务
 		this.doUpdateBus(entity);
 	}
 	
 	/**
	 * 新增操作增强业务
	 * @param t
	 * @return
	 */
	private void doAddBus(WeixinUserEntity t) throws Exception{
 	}
 	/**
	 * 更新操作增强业务
	 * @param t
	 * @return
	 */
	private void doUpdateBus(WeixinUserEntity t) throws Exception{
 	}
 	/**
	 * 删除操作增强业务
	 * @param id
	 * @return
	 */
	private void doDelBus(WeixinUserEntity t) throws Exception{
 	}
 	
 	private Map<String,Object> populationMap(WeixinUserEntity t){
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("id", t.getId());
		map.put("openid", t.getOpenid());
		/*map.put("nickname", t.getNickname());
		map.put("sex", t.getSex());
		map.put("province", t.getProvince());
		map.put("city", t.getCity());
		map.put("country", t.getCountry());
		map.put("headimgurl", t.getHeadimgurl());
		map.put("privilege", t.getPrivilege());*/
		map.put("create_time", t.getCreateTime());
		map.put("shop_id", t.getShopId());
		return map;
	}
 	
 	/**
	 * 替换sql中的变量
	 * @param sql
	 * @param t
	 * @return
	 */
 	public String replaceVal(String sql,WeixinUserEntity t){
 		sql  = sql.replace("#{id}",String.valueOf(t.getId()));
 		sql  = sql.replace("#{openid}",String.valueOf(t.getOpenid()));
 		/*sql  = sql.replace("#{nickname}",String.valueOf(t.getNickname()));
 		sql  = sql.replace("#{sex}",String.valueOf(t.getSex()));
 		sql  = sql.replace("#{province}",String.valueOf(t.getProvince()));
 		sql  = sql.replace("#{city}",String.valueOf(t.getCity()));
 		sql  = sql.replace("#{country}",String.valueOf(t.getCountry()));
 		sql  = sql.replace("#{headimgurl}",String.valueOf(t.getHeadimgurl()));
 		sql  = sql.replace("#{privilege}",String.valueOf(t.getPrivilege()));*/
 		sql  = sql.replace("#{create_time}",String.valueOf(t.getCreateTime()));
 		sql  = sql.replace("#{shop_id}",String.valueOf(t.getShopId()));
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
	public WeixinUserEntity getWeixinUserByUserIdAndShopId(String userId, String shopId) throws Exception {
		String hql = "from WeixinUserEntity w where w.openid='"+userId+"' and w.shopId='"+shopId+"'";
		WeixinUserEntity w =  super.singleResult(hql);
		return w;
	}
}