package com.tcsb.shopsharegoldcoin.service;
import com.tcsb.shopsharegoldcoin.entity.TcsbShopShareGoldCoinEntity;
import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;

public interface TcsbShopShareGoldCoinServiceI extends CommonService{
	
 	public void delete(TcsbShopShareGoldCoinEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopShareGoldCoinEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopShareGoldCoinEntity entity) throws Exception;
 	
}
