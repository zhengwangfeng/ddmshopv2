package com.tcsb.tcsbshoppaymode.service;
import com.tcsb.tcsbshoppaymode.entity.TcsbShopPaymodeEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopPaymodeServiceI extends CommonService{
	
 	public void delete(TcsbShopPaymodeEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopPaymodeEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopPaymodeEntity entity) throws Exception;
 	
}
