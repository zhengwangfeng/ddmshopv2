package com.tcsb.tcsbdeskreservation.service;
import com.tcsb.tcsbdeskreservation.entity.TcsbDeskReservationEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeskReservationServiceI extends CommonService{
	
 	public void delete(TcsbDeskReservationEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeskReservationEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeskReservationEntity entity) throws Exception;

	TcsbDeskReservationEntity updateRefund(TcsbDeskReservationEntity entity) throws Exception;
 	
}
