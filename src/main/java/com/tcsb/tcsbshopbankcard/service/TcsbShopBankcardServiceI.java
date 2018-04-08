package com.tcsb.tcsbshopbankcard.service;
import com.tcsb.tcsbshopbankcard.entity.TcsbShopBankcardEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopBankcardServiceI extends CommonService{
	
 	public void delete(TcsbShopBankcardEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopBankcardEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopBankcardEntity entity) throws Exception;
 	
}
