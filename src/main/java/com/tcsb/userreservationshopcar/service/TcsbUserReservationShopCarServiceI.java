package com.tcsb.userreservationshopcar.service;
import com.tcsb.userreservationshopcar.entity.TcsbUserReservationShopCarEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserReservationShopCarServiceI extends CommonService{
	
 	public void delete(TcsbUserReservationShopCarEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserReservationShopCarEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserReservationShopCarEntity entity) throws Exception;
 	
}
