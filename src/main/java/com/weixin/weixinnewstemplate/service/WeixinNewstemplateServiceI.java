package com.weixin.weixinnewstemplate.service;
import com.weixin.weixinnewstemplate.entity.WeixinNewstemplateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinNewstemplateServiceI extends CommonService{
	
 	public void delete(WeixinNewstemplateEntity entity) throws Exception;
 	
 	public Serializable save(WeixinNewstemplateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinNewstemplateEntity entity) throws Exception;
 	
}
