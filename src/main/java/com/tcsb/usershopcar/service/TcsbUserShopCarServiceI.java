package com.tcsb.usershopcar.service;
import com.tcsb.usershopcar.entity.TcsbUserShopCarEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbUserShopCarServiceI extends CommonService{
	
 	public void delete(TcsbUserShopCarEntity entity) throws Exception;
 	
 	public Serializable save(TcsbUserShopCarEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbUserShopCarEntity entity) throws Exception;
 	
}
