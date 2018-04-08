package com.tcsb.shopcollect.service;
import com.tcsb.shopcollect.entity.TcsbShopCollectEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopCollectServiceI extends CommonService{
	
 	public void delete(TcsbShopCollectEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopCollectEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopCollectEntity entity) throws Exception;

	public void addShopCollection(String userId, String shopId);
 	
}
