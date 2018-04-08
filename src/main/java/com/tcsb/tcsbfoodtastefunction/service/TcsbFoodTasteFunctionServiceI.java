package com.tcsb.tcsbfoodtastefunction.service;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodTasteFunctionServiceI extends CommonService{
	
 	public void delete(TcsbFoodTasteFunctionEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodTasteFunctionEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodTasteFunctionEntity entity) throws Exception;
 	
}
