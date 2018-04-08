package com.tcsb.associatorstatistics.service;
import com.tcsb.associatorstatistics.entity.TcsbAssociatorStatisticsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbAssociatorStatisticsServiceI extends CommonService{
	
 	public void delete(TcsbAssociatorStatisticsEntity entity) throws Exception;
 	
 	public Serializable save(TcsbAssociatorStatisticsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbAssociatorStatisticsEntity entity) throws Exception;
 	
}
