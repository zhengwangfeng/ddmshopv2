package com.tcsb.tcsborderparent.service;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbOrderParentServiceI extends CommonService{
	
 	public void delete(TcsbOrderParentEntity entity) throws Exception;
 	
 	public Serializable save(TcsbOrderParentEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbOrderParentEntity entity) throws Exception;
 	
}
