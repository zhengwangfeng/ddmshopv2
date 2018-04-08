package com.tcsb.desk.service;
import com.tcsb.desk.entity.TcsbDeskEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeskServiceI extends CommonService{
	
 	public void delete(TcsbDeskEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeskEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeskEntity entity) throws Exception;

	public void doApletQrcode(TcsbDeskEntity entity) throws Exception;

}
