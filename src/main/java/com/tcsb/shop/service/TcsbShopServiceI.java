package com.tcsb.shop.service;
import java.io.Serializable;
import java.util.List;

import org.jeecgframework.core.common.service.CommonService;
import org.jeecgframework.web.system.pojo.base.TSUser;

import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;
import com.tcsb.shop.entity.TcsbShopEntity;

public interface TcsbShopServiceI extends CommonService{
	
 	public void delete(TcsbShopEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopEntity entity) throws Exception;
 	
 	public TcsbShopEntity getTcsbShopEntityByUserid(String userid);

	public void addMain(TcsbShopEntity tcsbShop,
			List<TcsbImagesManagerEntity> imagesManagerList);

	public void updateMain(TcsbShopEntity t,
			List<TcsbImagesManagerEntity> imagesManagerList);

	public void addMain(TcsbShopEntity tcsbShop, TSUser user,
			List<TcsbImagesManagerEntity> imagesManagerList);

	public String getWeixinAppletShopQrcode(TcsbShopEntity entity, TSUser user);
 	
	
}
