package com.tcsb.shopsharegoldcoinrecord.service;
import com.tcsb.shopsharegoldcoinrecord.entity.TcsbShopShareGoldCoinRecordEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopShareGoldCoinRecordServiceI extends CommonService{
	
 	public void delete(TcsbShopShareGoldCoinRecordEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopShareGoldCoinRecordEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopShareGoldCoinRecordEntity entity) throws Exception;
 	
}
