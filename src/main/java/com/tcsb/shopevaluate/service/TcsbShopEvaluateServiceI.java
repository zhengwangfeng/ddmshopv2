package com.tcsb.shopevaluate.service;
import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopEvaluateServiceI extends CommonService{
	
 	public void delete(TcsbShopEvaluateEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopEvaluateEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopEvaluateEntity entity) throws Exception;
 	
}
