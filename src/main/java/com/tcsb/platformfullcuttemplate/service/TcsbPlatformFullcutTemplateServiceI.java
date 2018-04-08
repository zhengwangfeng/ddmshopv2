package com.tcsb.platformfullcuttemplate.service;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPlatformFullcutTemplateServiceI extends CommonService{
	
 	public void delete(TcsbPlatformFullcutTemplateEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPlatformFullcutTemplateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPlatformFullcutTemplateEntity entity) throws Exception;
 	
}
