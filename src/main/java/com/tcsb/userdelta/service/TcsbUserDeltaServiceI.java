package com.tcsb.userdelta.service;
import com.tcsb.userdelta.entity.TcsbUserDeltaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserDeltaServiceI extends CommonService{
	
 	public void delete(TcsbUserDeltaEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserDeltaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserDeltaEntity entity) throws Exception;
 	
}
