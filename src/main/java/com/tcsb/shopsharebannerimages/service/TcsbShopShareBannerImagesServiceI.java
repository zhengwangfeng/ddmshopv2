package com.tcsb.shopsharebannerimages.service;
import com.tcsb.shopsharebannerimages.entity.TcsbShopShareBannerImagesEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopShareBannerImagesServiceI extends CommonService{
	
 	public void delete(TcsbShopShareBannerImagesEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopShareBannerImagesEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopShareBannerImagesEntity entity) throws Exception;
 	
}
