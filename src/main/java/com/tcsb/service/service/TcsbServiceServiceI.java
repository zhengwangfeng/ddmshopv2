package com.tcsb.service.service;
import com.tcsb.service.entity.TcsbServiceEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbServiceServiceI extends CommonService{
	
 	public void delete(TcsbServiceEntity entity) throws Exception;
 	
 	public Serializable save(TcsbServiceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbServiceEntity entity) throws Exception;
 	
}
