package com.weixin.appletaccesstoken.service;
import com.weixin.appletaccesstoken.entity.WeixinAppletAccesstokenEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinAppletAccesstokenServiceI extends CommonService{
	
 	public void delete(WeixinAppletAccesstokenEntity entity) throws Exception;
 	
 	public Serializable save(WeixinAppletAccesstokenEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinAppletAccesstokenEntity entity) throws Exception;
 	
}
