/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月10日 上午9:32:10
 */
package com.applet.shoporder.service;

import java.util.List;

import org.jeecgframework.core.common.service.CommonService;

import com.applet.shoporder.vo.AppletRequestParamVo;
import com.applet.shoporder.vo.CouponDto;
import com.applet.shoporder.vo.OrderInfoVo;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.platformfullcuttemplate.entity.TcsbPlatformFullcutTemplateEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shopsharegoldcoin.entity.TcsbShopShareGoldCoinEntity;
import com.tcsb.suborder.entity.TcsbSubOrderEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月10日 上午9:32:10
 */
public interface AppletShopOrderServiceI extends CommonService{
	
	/**
	 * 获取当前桌位正在消费父级订单信息
	 * @param deskId
	 * @return
	 */
	public TcsbOrderParentEntity getOrderParent(String deskId);
	
	/**
	 * 获取当前桌位未付款的子订单
	 * @param deskId
	 * @return
	 */
	public TcsbOrderEntity getOrder(String deskId);
	
	/**
	 * 创建子订单项-创建接单子项
	 * @param suborderId
	 * @param order
	 * @return
	 */
	public boolean addOrderItemAndSubOrder(String suborderId,TcsbOrderEntity order);
	/**
	 * 创建接单信息
	 * @param param
	 * @param orderId
	 * @return
	 */
	public TcsbSubOrderEntity createSubOrderReturnSubId(AppletRequestParamVo param,String orderId);
	/***
	 * 创建子订单
	 * @param param
	 * @param orderParentId
	 * @return
	 */
	public TcsbOrderEntity createOrder(AppletRequestParamVo param,String orderParentId);

	/**
	 * 创建父级订单
	 * @param param
	 * @return
	 */
	public TcsbOrderParentEntity createOrderParent(AppletRequestParamVo param);
	/**
	 * 获取店铺支付方式
	 * @param shopId
	 * @return
	 */
	public int getShopPaymode(String shopId) ;
	
	
	/**
	 * 根据orderParentId获取订单的详情
	 * @param orderParentId
	 * @return
	 */
	public OrderInfoVo getShopDeskOrder(String orderParentId);
	
	/**
	 * 是否使用优惠券， true使用，false不适用或已使用
	 * @param orderList
	 * @return
	 */
	public double isUseCoupon(List<TcsbOrderEntity> orderList);
	
	/**
	 * 是否使用店铺活动， <=0使用，>0不适用或已使用
	 * @param orderList
	 * @return
	 */
	public double isUseShopDiscount(List<TcsbOrderEntity> orderList);
	
	/**
	 * 获取店铺活动
	 * @param shopId
	 * @return
	 */
	public List<TcsbShopFullcutTemplateEntity> getShopDiscountActivity(String shopId);
	
	/**
	 * 获取用户优惠券
	 * @param userId
	 */
	public List<CouponDto> getUserCoupon(String userId,String shopId);
	
	
	public TcsbShopShareGoldCoinEntity getUserShareGoldCoin(String userId,String shopId);
	
}
