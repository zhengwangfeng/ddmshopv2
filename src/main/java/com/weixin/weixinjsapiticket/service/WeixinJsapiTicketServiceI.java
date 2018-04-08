package com.weixin.weixinjsapiticket.service;
import com.weixin.weixinjsapiticket.entity.WeixinJsapiTicketEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinJsapiTicketServiceI extends CommonService{
	
 	public void delete(WeixinJsapiTicketEntity entity) throws Exception;
 	
 	public Serializable save(WeixinJsapiTicketEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinJsapiTicketEntity entity) throws Exception;
 	
}
