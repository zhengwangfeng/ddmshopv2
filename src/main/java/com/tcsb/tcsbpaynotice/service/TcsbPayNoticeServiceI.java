package com.tcsb.tcsbpaynotice.service;
import com.tcsb.tcsbpaynotice.entity.TcsbPayNoticeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPayNoticeServiceI extends CommonService{
	
 	public void delete(TcsbPayNoticeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPayNoticeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPayNoticeEntity entity) throws Exception;
 	
}
