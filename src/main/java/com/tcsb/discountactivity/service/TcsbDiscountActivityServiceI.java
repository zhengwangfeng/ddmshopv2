package com.tcsb.discountactivity.service;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDiscountActivityServiceI extends CommonService{
	
 	public void delete(TcsbDiscountActivityEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDiscountActivityEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDiscountActivityEntity entity) throws Exception;
 	
}
