package com.tcsb.fullcuttemplate.service;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFullcutTemplateServiceI extends CommonService{
	
 	public void delete(TcsbFullcutTemplateEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFullcutTemplateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFullcutTemplateEntity entity) throws Exception;
 	
}
