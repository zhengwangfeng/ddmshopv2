package com.tcsb.tcsborderbigdatarecord.service;
import com.tcsb.tcsborderbigdatarecord.entity.TcsbOrderBigdataRecordEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbOrderBigdataRecordServiceI extends CommonService{
	
 	public void delete(TcsbOrderBigdataRecordEntity entity) throws Exception;
 	
 	public Serializable save(TcsbOrderBigdataRecordEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbOrderBigdataRecordEntity entity) throws Exception;
 	
}
