package com.tcsb.memberuser.service;
import com.tcsb.memberuser.entity.TcsbMemberUserEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMemberUserServiceI extends CommonService{
	
 	public void delete(TcsbMemberUserEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMemberUserEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMemberUserEntity entity) throws Exception;
 	
}
