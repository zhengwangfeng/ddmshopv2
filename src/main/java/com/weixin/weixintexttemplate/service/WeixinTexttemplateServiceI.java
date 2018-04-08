package com.weixin.weixintexttemplate.service;
import com.weixin.weixintexttemplate.entity.WeixinTexttemplateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinTexttemplateServiceI extends CommonService{
	
 	public void delete(WeixinTexttemplateEntity entity) throws Exception;
 	
 	public Serializable save(WeixinTexttemplateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinTexttemplateEntity entity) throws Exception;
 	
}
