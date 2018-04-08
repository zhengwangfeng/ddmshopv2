package com.tcsb.platformcoupon.service;
import com.tcsb.platformcoupon.entity.TcsbPlatformCouponEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbPlatformCouponServiceI extends CommonService{
	
 	public void delete(TcsbPlatformCouponEntity entity) throws Exception;
 	
 	public Serializable save(TcsbPlatformCouponEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbPlatformCouponEntity entity) throws Exception;
 	
}
