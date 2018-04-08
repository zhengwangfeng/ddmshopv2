package com.tcsb.settleaccount.service;
import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbSettleAccountServiceI extends CommonService{
	
 	public void delete(TcsbSettleAccountEntity entity) throws Exception;
 	
 	public Serializable save(TcsbSettleAccountEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbSettleAccountEntity entity) throws Exception;
 	
}
