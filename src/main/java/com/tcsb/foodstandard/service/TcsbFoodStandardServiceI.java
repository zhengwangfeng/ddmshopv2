package com.tcsb.foodstandard.service;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodStandardServiceI extends CommonService{
	
 	public void delete(TcsbFoodStandardEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodStandardEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodStandardEntity entity) throws Exception;
 	
}
