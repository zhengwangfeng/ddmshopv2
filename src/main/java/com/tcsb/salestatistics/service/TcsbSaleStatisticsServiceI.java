package com.tcsb.salestatistics.service;
import com.tcsb.salestatistics.entity.TcsbSaleStatisticsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbSaleStatisticsServiceI extends CommonService{
	
 	public void delete(TcsbSaleStatisticsEntity entity) throws Exception;
 	
 	public Serializable save(TcsbSaleStatisticsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbSaleStatisticsEntity entity) throws Exception;
 	
}
