package com.weixin.weixinaccesstoken.service;
import com.weixin.weixinaccesstoken.entity.WeixinAccesstokenEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinAccesstokenServiceI extends CommonService{
	
 	public void delete(WeixinAccesstokenEntity entity) throws Exception;
 	
 	public Serializable save(WeixinAccesstokenEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinAccesstokenEntity entity) throws Exception;
 	
}
