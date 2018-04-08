package com.tcsb.membertitle.service;
import com.tcsb.membertitle.entity.TcsbMemberTitleEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbMemberTitleServiceI extends CommonService{
	
 	public void delete(TcsbMemberTitleEntity entity) throws Exception;
 	
 	public Serializable save(TcsbMemberTitleEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbMemberTitleEntity entity) throws Exception;
 	
}
