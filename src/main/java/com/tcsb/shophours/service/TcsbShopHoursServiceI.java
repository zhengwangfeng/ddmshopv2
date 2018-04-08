package com.tcsb.shophours.service;
import com.tcsb.shophours.entity.TcsbShopHoursEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopHoursServiceI extends CommonService{
	
 	public void delete(TcsbShopHoursEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopHoursEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopHoursEntity entity) throws Exception;
 	
}
