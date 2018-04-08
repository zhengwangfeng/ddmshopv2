package com.tcsb.userorder.service;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserOrderServiceI extends CommonService{
	
 	public void delete(TcsbUserOrderEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserOrderEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserOrderEntity entity) throws Exception;
 	
}
