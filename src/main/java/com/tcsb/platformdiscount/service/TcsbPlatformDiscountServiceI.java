package com.tcsb.platformdiscount.service;
import com.tcsb.platformdiscount.entity.TcsbPlatformDiscountEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPlatformDiscountServiceI extends CommonService{
	
 	public void delete(TcsbPlatformDiscountEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPlatformDiscountEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPlatformDiscountEntity entity) throws Exception;
 	
}
