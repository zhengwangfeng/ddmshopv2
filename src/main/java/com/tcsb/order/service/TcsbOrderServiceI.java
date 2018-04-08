package com.tcsb.order.service;

import java.util.List;
import java.util.Map;

import org.jeecgframework.core.common.service.CommonService;

import com.applet.shopcar.vo.TcsbShopCarTempVO;
import com.applet.shoporder.vo.AppletRequestParamVo;
import com.tcsb.order.VO.OrderStatisticVo;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;

public interface TcsbOrderServiceI extends CommonService{
	
 	public <T> void delete(T entity);
	/**
	 * 添加一对多
	 * 
	 */
	public void addMain(TcsbOrderEntity tcsbOrder,
	        List<TcsbOrderItemEntity> tcsbOrderItemList) ;
	/**
	 * 修改一对多
	 * 
	 */
	public void updateMain(TcsbOrderEntity tcsbOrder,
	        List<TcsbOrderItemEntity> tcsbOrderItemList);
	public void delMain (TcsbOrderEntity tcsbOrder);
	
 	/**
	 * 默认按钮-sql增强-新增操作
	 * @param id
	 * @return
	 */
 	public boolean doAddSql(TcsbOrderEntity t);
 	/**
	 * 默认按钮-sql增强-更新操作
	 * @param id
	 * @return
	 */
 	public boolean doUpdateSql(TcsbOrderEntity t);
 	/**
	 * 默认按钮-sql增强-删除操作
	 * @param id
	 * @return
	 */
 	public boolean doDelSql(TcsbOrderEntity t);
	public Map<String, Object> saveOrderReturnOrderNo(TcsbOrderVO tcsbOrderVO);
	public List<TcsbOrderVO> getMyOrder(String userId, String status);
	public List<OrderStatisticVo> getOrderStatistic(String startTime,String endTime);
	public String saveOrderReturnOrderParentId(AppletRequestParamVo param);
	public Map<String , Object> getMoneyByShopCar(AppletRequestParamVo param);
	public Map<String, Object> getReservationTotalMoneyByShopCar(
			TcsbShopCarTempVO param);
	public Map<String, Object> getReservationDepositMoneyByShopCar(
			TcsbShopCarTempVO tcsbShopCarVO);
	
}
