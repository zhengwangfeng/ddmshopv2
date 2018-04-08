package com.tcsb.tcsbretreatfood.service;
import com.tcsb.tcsbretreatfood.entity.TcsbRetreatFoodEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbRetreatFoodServiceI extends CommonService{
	
 	public void delete(TcsbRetreatFoodEntity entity) throws Exception;
 	
 	public Serializable save(TcsbRetreatFoodEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbRetreatFoodEntity entity) throws Exception;
 	
}
