package com.tcsb.shopsmsservice.service;
import com.tcsb.shopsmsservice.entity.TcsbShopSmsServiceEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopSmsServiceServiceI extends CommonService{
	
 	public void delete(TcsbShopSmsServiceEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopSmsServiceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopSmsServiceEntity entity) throws Exception;
 	
}
