package com.tcsb.tcsbfoodfunction.service;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodFunctionServiceI extends CommonService{
	
 	public void delete(TcsbFoodFunctionEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodFunctionEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodFunctionEntity entity) throws Exception;
 	
}
