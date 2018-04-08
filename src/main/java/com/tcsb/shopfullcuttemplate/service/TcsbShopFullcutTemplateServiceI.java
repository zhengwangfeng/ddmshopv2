package com.tcsb.shopfullcuttemplate.service;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopFullcutTemplateServiceI extends CommonService{
	
 	public void delete(TcsbShopFullcutTemplateEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopFullcutTemplateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopFullcutTemplateEntity entity) throws Exception;
 	
}
