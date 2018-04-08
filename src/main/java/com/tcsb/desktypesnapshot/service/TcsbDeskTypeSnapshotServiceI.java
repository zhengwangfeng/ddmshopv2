package com.tcsb.desktypesnapshot.service;
import com.tcsb.desktypesnapshot.entity.TcsbDeskTypeSnapshotEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbDeskTypeSnapshotServiceI extends CommonService{
	
 	public void delete(TcsbDeskTypeSnapshotEntity entity) throws Exception;
 	
 	public Serializable save(TcsbDeskTypeSnapshotEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbDeskTypeSnapshotEntity entity) throws Exception;
 	
}
