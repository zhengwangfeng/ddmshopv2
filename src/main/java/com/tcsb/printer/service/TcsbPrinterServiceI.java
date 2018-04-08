package com.tcsb.printer.service;
import com.tcsb.printer.entity.TcsbPrinterEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPrinterServiceI extends CommonService{
	
 	public void delete(TcsbPrinterEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPrinterEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPrinterEntity entity) throws Exception;
 	
}
