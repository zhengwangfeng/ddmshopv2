package com.tcsb.food.service;
import com.tcsb.food.entity.TcsbFoodEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodServiceI extends CommonService{
	
 	public void delete(TcsbFoodEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodEntity entity) throws Exception;
 	
}
