package com.weixin.weixinuser.service;
import com.weixin.weixinuser.entity.WeixinUserEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinUserServiceI extends CommonService{
	
 	public void delete(WeixinUserEntity entity) throws Exception;
 	
 	public Serializable save(WeixinUserEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinUserEntity entity) throws Exception;
 	
 	public WeixinUserEntity getWeixinUserByUserIdAndShopId(String userId,String shopId) throws Exception;
 	
}
