package com.tcsb.foodsnapshot.service;
import com.tcsb.foodsnapshot.entity.TcsbFoodSnapshotEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodSnapshotServiceI extends CommonService{
	
 	public void delete(TcsbFoodSnapshotEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodSnapshotEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodSnapshotEntity entity) throws Exception;
 	
}
