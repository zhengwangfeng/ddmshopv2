package com.tcsb.mealfee.service;
import com.tcsb.mealfee.entity.TcsbMealFeeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMealFeeServiceI extends CommonService{
	
 	public void delete(TcsbMealFeeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMealFeeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMealFeeEntity entity) throws Exception;
 	
}
