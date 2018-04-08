package com.tcsb.printerfoodtype.service;
import com.tcsb.printerfoodtype.entity.TcsbPrinterFoodTypeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPrinterFoodTypeServiceI extends CommonService{
	
 	public void delete(TcsbPrinterFoodTypeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPrinterFoodTypeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPrinterFoodTypeEntity entity) throws Exception;
 	
}
