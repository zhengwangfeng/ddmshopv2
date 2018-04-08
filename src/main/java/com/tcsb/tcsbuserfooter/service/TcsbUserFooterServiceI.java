package com.tcsb.tcsbuserfooter.service;
import com.tcsb.tcsbuserfooter.entity.TcsbUserFooterEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserFooterServiceI extends CommonService{
	
 	public void delete(TcsbUserFooterEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserFooterEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserFooterEntity entity) throws Exception;
 	
}
