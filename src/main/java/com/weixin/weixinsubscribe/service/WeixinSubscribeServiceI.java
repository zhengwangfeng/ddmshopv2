package com.weixin.weixinsubscribe.service;
import com.weixin.weixinsubscribe.entity.WeixinSubscribeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinSubscribeServiceI extends CommonService{
	
 	public void delete(WeixinSubscribeEntity entity) throws Exception;
 	
 	public Serializable save(WeixinSubscribeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinSubscribeEntity entity) throws Exception;
 	
}
