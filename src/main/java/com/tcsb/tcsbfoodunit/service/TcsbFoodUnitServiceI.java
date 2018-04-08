package com.tcsb.tcsbfoodunit.service;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodUnitServiceI extends CommonService{
	
 	public void delete(TcsbFoodUnitEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodUnitEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodUnitEntity entity) throws Exception;
 	
}
