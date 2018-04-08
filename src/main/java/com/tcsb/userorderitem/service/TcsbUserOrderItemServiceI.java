package com.tcsb.userorderitem.service;
import com.tcsb.userorderitem.entity.TcsbUserOrderItemEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserOrderItemServiceI extends CommonService{
	
 	public void delete(TcsbUserOrderItemEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserOrderItemEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserOrderItemEntity entity) throws Exception;
 	
}
