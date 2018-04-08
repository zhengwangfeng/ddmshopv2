package com.tcsb.desksnapshot.service;
import com.tcsb.desksnapshot.entity.TcsbDeskSnapshotEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeskSnapshotServiceI extends CommonService{
	
 	public void delete(TcsbDeskSnapshotEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeskSnapshotEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeskSnapshotEntity entity) throws Exception;
 	
}
