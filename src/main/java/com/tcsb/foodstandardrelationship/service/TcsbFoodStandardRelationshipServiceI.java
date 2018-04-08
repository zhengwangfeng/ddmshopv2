package com.tcsb.foodstandardrelationship.service;
import com.tcsb.foodstandardrelationship.entity.TcsbFoodStandardRelationshipEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodStandardRelationshipServiceI extends CommonService{
	
 	public void delete(TcsbFoodStandardRelationshipEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodStandardRelationshipEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodStandardRelationshipEntity entity) throws Exception;
 	
}
