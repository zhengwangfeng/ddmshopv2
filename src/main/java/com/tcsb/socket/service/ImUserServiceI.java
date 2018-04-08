package com.tcsb.socket.service;
import java.io.Serializable;

import org.jeecgframework.core.common.service.CommonService;

import com.tcsb.socket.entity.ImUser;

public interface ImUserServiceI extends CommonService{
	
 	public void delete(ImUser entity) throws Exception;
 	
 	public Serializable save(ImUser entity) throws Exception;
 	
 	public void saveOrUpdate(ImUser entity) throws Exception;
 	
}
