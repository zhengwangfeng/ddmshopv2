package com.tcsb.tcsbassociatorbigdata.service;
import com.tcsb.tcsbassociatorbigdata.entity.TcsbAssociatorBigdataEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbAssociatorBigdataServiceI extends CommonService{
	
 	public void delete(TcsbAssociatorBigdataEntity entity) throws Exception;
 	
 	public Serializable save(TcsbAssociatorBigdataEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbAssociatorBigdataEntity entity) throws Exception;
 	
}
