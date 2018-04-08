package com.tcsb.foodmealfun.service;
import com.tcsb.foodmealfun.entity.TcsbFoodMealFunEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodMealFunServiceI extends CommonService{
	
 	public void delete(TcsbFoodMealFunEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodMealFunEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodMealFunEntity entity) throws Exception;
 	
}
