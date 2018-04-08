package com.tcsb.shopcaritem.service;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopCarItemServiceI extends CommonService{
	
 	public void delete(TcsbShopCarItemEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopCarItemEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopCarItemEntity entity) throws Exception;
 	
}
