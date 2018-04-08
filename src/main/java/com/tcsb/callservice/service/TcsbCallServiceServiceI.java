package com.tcsb.callservice.service;
import com.tcsb.callservice.entity.TcsbCallServiceEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbCallServiceServiceI extends CommonService{
	
 	public void delete(TcsbCallServiceEntity entity) throws Exception;
 	
 	public Serializable save(TcsbCallServiceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbCallServiceEntity entity) throws Exception;
 	
}
