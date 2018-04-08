package com.tcsb.tcsbdeskreservationitem.service;
import com.tcsb.tcsbdeskreservationitem.entity.TcsbDeskReservationItemEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeskReservationItemServiceI extends CommonService{
	
 	public void delete(TcsbDeskReservationItemEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeskReservationItemEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeskReservationItemEntity entity) throws Exception;
 	
}
