package com.tcsb.membershiplevel.service;
import com.tcsb.membershiplevel.entity.TcsbMembershipLevelEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMembershipLevelServiceI extends CommonService{
	
 	public void delete(TcsbMembershipLevelEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMembershipLevelEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMembershipLevelEntity entity) throws Exception;
 	
}
