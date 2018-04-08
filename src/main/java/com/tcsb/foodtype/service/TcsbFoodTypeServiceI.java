package com.tcsb.foodtype.service;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodTypeServiceI extends CommonService{
	
 	public void delete(TcsbFoodTypeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodTypeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodTypeEntity entity) throws Exception;
 	
}
