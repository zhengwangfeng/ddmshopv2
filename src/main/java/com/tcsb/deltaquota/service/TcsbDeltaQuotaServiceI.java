package com.tcsb.deltaquota.service;
import com.tcsb.deltaquota.entity.TcsbDeltaQuotaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeltaQuotaServiceI extends CommonService{
	
 	public void delete(TcsbDeltaQuotaEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeltaQuotaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeltaQuotaEntity entity) throws Exception;
 	
}
