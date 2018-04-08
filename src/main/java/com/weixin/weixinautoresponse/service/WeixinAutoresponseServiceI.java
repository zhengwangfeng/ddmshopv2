package com.weixin.weixinautoresponse.service;
import com.weixin.weixinautoresponse.entity.WeixinAutoresponseEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinAutoresponseServiceI extends CommonService{
	
 	public void delete(WeixinAutoresponseEntity entity) throws Exception;
 	
 	public Serializable save(WeixinAutoresponseEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinAutoresponseEntity entity) throws Exception;
 	
}
