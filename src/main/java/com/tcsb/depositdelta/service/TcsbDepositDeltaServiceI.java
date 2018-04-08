package com.tcsb.depositdelta.service;
import com.tcsb.depositdelta.entity.TcsbDepositDeltaEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDepositDeltaServiceI extends CommonService{
	
 	public void delete(TcsbDepositDeltaEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDepositDeltaEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDepositDeltaEntity entity) throws Exception;
 	
}
