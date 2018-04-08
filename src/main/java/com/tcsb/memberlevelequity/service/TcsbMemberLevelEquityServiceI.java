package com.tcsb.memberlevelequity.service;
import com.tcsb.memberlevelequity.entity.TcsbMemberLevelEquityEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMemberLevelEquityServiceI extends CommonService{
	
 	public void delete(TcsbMemberLevelEquityEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMemberLevelEquityEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMemberLevelEquityEntity entity) throws Exception;
 	
}
