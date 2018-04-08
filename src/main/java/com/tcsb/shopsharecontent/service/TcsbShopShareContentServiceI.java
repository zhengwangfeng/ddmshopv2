package com.tcsb.shopsharecontent.service;
import com.tcsb.shopsharecontent.entity.TcsbShopShareContentEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopShareContentServiceI extends CommonService{
	
 	public void delete(TcsbShopShareContentEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopShareContentEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopShareContentEntity entity) throws Exception;
 	
}
