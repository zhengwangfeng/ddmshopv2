package com.tcsb.shopcar.service;
import com.tcsb.shopcar.VO.TcsbShopCarVO;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;

import org.jeecgframework.core.common.service.CommonService;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface TcsbShopCarServiceI extends CommonService{
	
 	public void delete(TcsbShopCarEntity entity) throws Exception;
 	
 	public Serializable save(TcsbShopCarEntity entity) throws Exception;
 	
 	public void saveOrUpdate(TcsbShopCarEntity entity) throws Exception;

	public void addOrUpdateShopCar(String userId, String deskId, String foodId,
			String status) throws Exception ;
	
	public void addOrUpdateTasteShopCar(String userId, String deskId, String foodId,
			String status,String foodTasteFun) throws Exception ;
	

	public Map<String , Object> getShopCar(String userId,String deskId, String shopId) throws Exception;

	public Boolean checkShopCarNew(TcsbShopCarVO tcsbShopCarVO) throws Exception;

	public List<TcsbShopCarVO> getMyShopCar(String userId) throws Exception;
 	
}
