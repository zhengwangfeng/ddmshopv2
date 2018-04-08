package com.tcsb.usercar.service;
import com.tcsb.usercar.entity.TcsbUserCarEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserCarServiceI extends CommonService{
	
 	public void delete(TcsbUserCarEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserCarEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserCarEntity entity) throws Exception;
 	
}
