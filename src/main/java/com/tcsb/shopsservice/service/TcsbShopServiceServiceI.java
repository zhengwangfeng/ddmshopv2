package com.tcsb.shopsservice.service;
import com.tcsb.shopsservice.entity.TcsbShopServiceEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;

public interface TcsbShopServiceServiceI extends CommonService{
	
 	public void delete(TcsbShopServiceEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopServiceEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopServiceEntity entity) throws Exception;
 	
 	public TcsbShopServiceEntity getTcsbShopServiceEntityByServiceIdAndShopId(String serviceId,String shopId);
 	
 	public List<TcsbShopServiceEntity> getTcsbShopServiceEntityListByShopId(String shopId);
 	
}
