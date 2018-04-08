package com.tcsb.imagesmanager.service;
import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbImagesManagerServiceI extends CommonService{
	
 	public void delete(TcsbImagesManagerEntity entity) throws Exception;
 	
 	public Serializable save(TcsbImagesManagerEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbImagesManagerEntity entity) throws Exception;
 	
}
