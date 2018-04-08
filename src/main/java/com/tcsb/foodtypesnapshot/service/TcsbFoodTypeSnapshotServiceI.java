package com.tcsb.foodtypesnapshot.service;
import com.tcsb.foodtypesnapshot.entity.TcsbFoodTypeSnapshotEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbFoodTypeSnapshotServiceI extends CommonService{
	
 	public void delete(TcsbFoodTypeSnapshotEntity entity) throws Exception;
 	
 	public Serializable save(TcsbFoodTypeSnapshotEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbFoodTypeSnapshotEntity entity) throws Exception;
 	
}
