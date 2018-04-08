package com.tcsb.usercost.service;
import com.tcsb.usercost.entity.TcsbUserCostEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserCostServiceI extends CommonService{
	
 	public void delete(TcsbUserCostEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserCostEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserCostEntity entity) throws Exception;
 	
}
