package com.tcsb.shopsharedestailsimages.service;
import com.tcsb.shopsharedestailsimages.entity.TcsbShopShareDestailsImagesEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopShareDestailsImagesServiceI extends CommonService{
	
 	public void delete(TcsbShopShareDestailsImagesEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopShareDestailsImagesEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopShareDestailsImagesEntity entity) throws Exception;
 	
}
