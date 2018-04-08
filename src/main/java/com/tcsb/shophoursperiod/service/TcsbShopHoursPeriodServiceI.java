package com.tcsb.shophoursperiod.service;
import com.tcsb.shophoursperiod.entity.TcsbShopHoursPeriodEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopHoursPeriodServiceI extends CommonService{
	
 	public void delete(TcsbShopHoursPeriodEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopHoursPeriodEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopHoursPeriodEntity entity) throws Exception;
 	
}
