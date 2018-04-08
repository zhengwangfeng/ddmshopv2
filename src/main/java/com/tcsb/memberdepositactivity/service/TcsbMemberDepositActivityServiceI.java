package com.tcsb.memberdepositactivity.service;
import com.tcsb.memberdepositactivity.entity.TcsbMemberDepositActivityEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMemberDepositActivityServiceI extends CommonService{
	
 	public void delete(TcsbMemberDepositActivityEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMemberDepositActivityEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMemberDepositActivityEntity entity) throws Exception;
 	
}
