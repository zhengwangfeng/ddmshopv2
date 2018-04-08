package com.tcsb.tcsbweixinuser.service;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbWeixinUserServiceI extends CommonService{
	
 	public void delete(TcsbWeixinUserEntity entity) throws Exception;
 	
 	public Serializable save(TcsbWeixinUserEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbWeixinUserEntity entity) throws Exception;
 	
}
