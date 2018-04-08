package com.tcsb.suborder.service;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbSubOrderServiceI extends CommonService{
	
 	public void delete(TcsbSubOrderEntity entity) throws Exception;
 	
 	public Serializable save(TcsbSubOrderEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbSubOrderEntity entity) throws Exception;
 	
}
