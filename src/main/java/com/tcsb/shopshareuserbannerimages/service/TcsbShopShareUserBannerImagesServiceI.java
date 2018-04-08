package com.tcsb.shopshareuserbannerimages.service;
import com.tcsb.shopshareuserbannerimages.entity.TcsbShopShareUserBannerImagesEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopShareUserBannerImagesServiceI extends CommonService{
	
 	public void delete(TcsbShopShareUserBannerImagesEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopShareUserBannerImagesEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopShareUserBannerImagesEntity entity) throws Exception;
 	
}
