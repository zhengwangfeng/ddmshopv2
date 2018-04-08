package com.tcsb.tcsbretreatorder.service;
import com.tcsb.tcsbretreatorder.entity.TcsbRetreatOrderEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbRetreatOrderServiceI extends CommonService{
	
 	public void delete(TcsbRetreatOrderEntity entity) throws Exception;
 	
 	public Serializable save(TcsbRetreatOrderEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbRetreatOrderEntity entity) throws Exception;
 	
}
