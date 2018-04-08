package com.weixin.weixinmenu.service;
import com.weixin.weixinmenu.entity.WeixinMenuEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface WeixinMenuServiceI extends CommonService{
	
 	public void delete(WeixinMenuEntity entity) throws Exception;
 	
 	public Serializable save(WeixinMenuEntity entity) throws Exception;
 	
 	public void saveOrUpdate(WeixinMenuEntity entity) throws Exception;
 	
}
