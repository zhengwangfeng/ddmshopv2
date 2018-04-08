package com.tcsb.desktype.service;
import com.tcsb.desktype.entity.TcsbDeskTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeskTypeServiceI extends CommonService{
	
 	public void delete(TcsbDeskTypeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeskTypeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeskTypeEntity entity) throws Exception;
 	
}
