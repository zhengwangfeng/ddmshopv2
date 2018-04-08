package com.tcsb.memberlevelconditions.service;
import com.tcsb.memberlevelconditions.entity.TcsbMemberLevelConditionsEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMemberLevelConditionsServiceI extends CommonService{
	
 	public void delete(TcsbMemberLevelConditionsEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMemberLevelConditionsEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMemberLevelConditionsEntity entity) throws Exception;
 	
}
