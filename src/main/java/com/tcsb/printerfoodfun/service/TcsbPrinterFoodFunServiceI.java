package com.tcsb.printerfoodfun.service;
import com.tcsb.printerfoodfun.entity.TcsbPrinterFoodFunEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPrinterFoodFunServiceI extends CommonService{
	
 	public void delete(TcsbPrinterFoodFunEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPrinterFoodFunEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPrinterFoodFunEntity entity) throws Exception;
 	
}
