/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月2日 下午5:51:39
 */
package com.applet.userOrder;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.server.PathParam;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;

import jodd.bean.BeanUtil;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月2日 下午5:51:39
 */
@Controller
@RequestMapping("/appletUserOrderController")
public class AppletUserOrderController extends BaseController{
	
	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	/**
	 * 我的全部订单
	 * @param userId
	 * @return
	 */
	@RequestMapping("/myAllOrder")
	@ResponseBody
	public AjaxJsonApi myOrder(String userId,Integer page,Integer limit){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		
		CriteriaQuery cq = new CriteriaQuery(TcsbUserOrderEntity.class);
		cq.add(Restrictions.eq("userId", userId));
		cq.addOrder("createDate", SortDirection.desc);
		if(StringUtil.isNotEmpty(page) && StringUtil.isNotEmpty(limit)){
			cq.setPageSize(limit);
			cq.setCurPage(page*limit);
		}
		cq.add();
		List<TcsbUserOrderEntity> tcsbUserOrderList = tcsbOrderService.getListByCriteriaQuery(cq, true);
		
		//List<TcsbUserOrderEntity> tcsbUserOrderList = tcsbOrderService.findByProperty(TcsbUserOrderEntity.class, "userId", userId);
		
		List<UserOrderVo> userOrderVoList = new ArrayList<>();
		UserOrderVo userOrderVo;
		if(tcsbUserOrderList.size()>0){
			//根据orderparentid获取订单
			for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderList) {
				userOrderVo = new UserOrderVo();
				//父类订单信息
				TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, tcsbUserOrderEntity.getOrderParentId());
				System.out.println("============");
				System.out.println(tcsbUserOrderEntity.getOrderParentId());
				System.out.println(tcsbOrderParentEntity.getShopId());
				System.out.println("============");
				userOrderVo.setTcsbOrderParent(tcsbOrderParentEntity);
				//店铺信息
				TcsbShopEntity tcsbShopEntity =tcsbOrderService.get(TcsbShopEntity.class, tcsbOrderParentEntity.getShopId());
				TcsbShopEntity tcsbShopEntity2 = new TcsbShopEntity();
				try {
					BeanUtils.copyProperties(tcsbShopEntity2, tcsbShopEntity);
					tcsbShopEntity2.setHeadimg(getAppletCkPath()+tcsbShopEntity2.getHeadimg().replace("images", "_thumbs/Images"));
					userOrderVo.setTcsbShop(tcsbShopEntity2);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				//获取订单中的菜单项
				double allMoney = 0.0;
				double itemNum = 0.0;
				List<TcsbOrderEntity> tcsbOrderEntity = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderParentId", tcsbUserOrderEntity.getOrderParentId());
				List<TcsbOrderItemEntity> tcsbOrderItemEntityList2 = new ArrayList<>();
				for (TcsbOrderEntity tcsbOrderEntity2 : tcsbOrderEntity) {
					List<TcsbOrderItemEntity> TcsbOrderItemEntityList = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
						for (TcsbOrderItemEntity tcsbOrderItemEntity : TcsbOrderItemEntityList) {
							tcsbOrderItemEntityList2.add(tcsbOrderItemEntity);
							double mulMoney = BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice());
							allMoney = BigDecimalUtil.add(allMoney, mulMoney);
							itemNum =  BigDecimalUtil.add(itemNum,tcsbOrderItemEntity.getNum());
						}
				}
				userOrderVo.setAllMoney(BigDecimalUtil.numericRetentionDecimal(allMoney, 2));
				userOrderVo.setItemNum(BigDecimalUtil.numericRetentionDecimal(itemNum, 0));
				
				List<TcsbOrderItemEntity> tcsbOrderItemEntityList3 = new ArrayList<>();
				if(tcsbOrderItemEntityList2.size()>2){
					for (int i = 0; i<3;i++) {
						tcsbOrderItemEntityList3.add(tcsbOrderItemEntityList2.get(i));
					}
					userOrderVo.setTcsbOrderItemList(tcsbOrderItemEntityList3);
				}else{
					userOrderVo.setTcsbOrderItemList(tcsbOrderItemEntityList2);
				}
				
				
				
				String hql = "from TcsbShopEvaluateEntity where orderId='"+tcsbUserOrderEntity.getOrderParentId()+"' and userId='"+userId+"'";
				TcsbShopEvaluateEntity tcsbShopEvaluateEntity = tcsbOrderService.singleResult(hql);
				if(StringUtil.isNotEmpty(tcsbShopEvaluateEntity)){
					userOrderVo.setEvaluate(true);
				}else{
					userOrderVo.setEvaluate(false);
				}
				
				
				if(tcsbOrderParentEntity.getOrderStatus().equals("0")){
					//订单完成
					userOrderVo.setOrderStateName("订单完成");
					userOrderVo.setOrderStateCode(1);
				}else{
					userOrderVo.setOrderStateName("消费中");
					userOrderVo.setOrderStateCode(2);
				}	
				userOrderVoList.add(userOrderVo);
			}
			
			ajaxJson.setMsg("加载成功");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(userOrderVoList);	
		}else{
			ajaxJson.setMsg("无订单");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);	
		}
		
		return ajaxJson;
	}
	
	/**
	 * 消费中订单
	 * @param userId
	 * @return orderStateCode(0:未消费,1:订单完成,2:消费中)isEvaluate(true已评价false未评价)
	 */
	@RequestMapping("/mySaleOrder")
	@ResponseBody
	public AjaxJsonApi mySaleOrder(String userId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		List<TcsbUserOrderEntity> tcsbUserOrderList = tcsbOrderService.findByProperty(TcsbUserOrderEntity.class, "userId", userId);
		
		List<UserOrderVo> userOrderVoList = new ArrayList<>();
		UserOrderVo userOrderVo;
		if(tcsbUserOrderList.size()>0){
			//根据orderparentid获取订单
			for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderList) {
				userOrderVo = new UserOrderVo();
				//父类订单信息
				TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, tcsbUserOrderEntity.getOrderParentId());


				if(tcsbOrderParentEntity.getOrderStatus().equals("0")){
					//订单完成
					continue;
				}else{
					userOrderVo.setTcsbOrderParent(tcsbOrderParentEntity);
					//店铺信息
					TcsbShopEntity tcsbShopEntity =tcsbOrderService.get(TcsbShopEntity.class, tcsbOrderParentEntity.getShopId());
					TcsbShopEntity tcsbShopEntity2 = new TcsbShopEntity();
					try {
						BeanUtils.copyProperties(tcsbShopEntity2, tcsbShopEntity);
						tcsbShopEntity2.setHeadimg(getAppletCkPath()+tcsbShopEntity2.getHeadimg().replace("images", "_thumbs/Images"));
						userOrderVo.setTcsbShop(tcsbShopEntity2);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					//获取订单中的菜单项
					double allMoney = 0.0;
					double itemNum = 0.0;
					List<TcsbOrderEntity> tcsbOrderEntity = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderParentId", tcsbUserOrderEntity.getOrderParentId());
					List<TcsbOrderItemEntity> tcsbOrderItemEntityList2 = new ArrayList<>();
					for (TcsbOrderEntity tcsbOrderEntity2 : tcsbOrderEntity) {
						List<TcsbOrderItemEntity> TcsbOrderItemEntityList = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
							for (TcsbOrderItemEntity tcsbOrderItemEntity : TcsbOrderItemEntityList) {
								tcsbOrderItemEntityList2.add(tcsbOrderItemEntity);
								double mulMoney = BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice());
								allMoney = BigDecimalUtil.add(allMoney, mulMoney);
								itemNum =  BigDecimalUtil.add(itemNum,tcsbOrderItemEntity.getNum());
							}
					}
					userOrderVo.setAllMoney(BigDecimalUtil.numericRetentionDecimal(allMoney, 2));
					userOrderVo.setItemNum(BigDecimalUtil.numericRetentionDecimal(itemNum, 0));

					List<TcsbOrderItemEntity> tcsbOrderItemEntityList3 = new ArrayList<>();
					if(tcsbOrderItemEntityList2.size()>2){
						for (int i = 0; i<3;i++) {
							tcsbOrderItemEntityList3.add(tcsbOrderItemEntityList2.get(i));
						}
						userOrderVo.setTcsbOrderItemList(tcsbOrderItemEntityList3);
					}else{
						userOrderVo.setTcsbOrderItemList(tcsbOrderItemEntityList2);
					}
					String hql = "from TcsbShopEvaluateEntity where orderId='"+tcsbUserOrderEntity.getOrderParentId()+"' and userId='"+userId+"'";
					TcsbShopEvaluateEntity tcsbShopEvaluateEntity = tcsbOrderService.singleResult(hql);
					if(StringUtil.isNotEmpty(tcsbShopEvaluateEntity)){
						userOrderVo.setEvaluate(true);
					}else{
						userOrderVo.setEvaluate(false);
					}
					userOrderVo.setOrderStateName("消费中");
					userOrderVo.setOrderStateCode(2);
					userOrderVoList.add(userOrderVo);
				}
			}
			ajaxJson.setMsg("加载成功");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(userOrderVoList);	
		}else{
			ajaxJson.setMsg("无订单");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);	
		}
		
		return ajaxJson;
	}
	
	/**
	 * 未评论订单
	 * @param userId
	 * @return
	 */
	@RequestMapping("/myNoEvaluateOrder")
	@ResponseBody
	public AjaxJsonApi myNoEvaluateOrder(String userId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		List<TcsbUserOrderEntity> tcsbUserOrderList = tcsbOrderService.findByProperty(TcsbUserOrderEntity.class, "userId", userId);
		
		List<UserOrderVo> userOrderVoList = new ArrayList<>();
		UserOrderVo userOrderVo;
		if(tcsbUserOrderList.size()>0){
			//根据orderparentid获取订单
			for (TcsbUserOrderEntity tcsbUserOrderEntity : tcsbUserOrderList) {
				userOrderVo = new UserOrderVo();
				//父类订单信息
				TcsbOrderParentEntity tcsbOrderParentEntity = tcsbOrderService.get(TcsbOrderParentEntity.class, tcsbUserOrderEntity.getOrderParentId());
				
				
				if(tcsbOrderParentEntity.getOrderStatus().equals("0")){
					//订单完成
					String hql = "from TcsbShopEvaluateEntity where orderId='"+tcsbOrderParentEntity.getId()+"' and userId='"+userId+"'";
					TcsbShopEvaluateEntity tcsbShopEvaluateEntity = tcsbOrderService.singleResult(hql);
					if(StringUtil.isNotEmpty(tcsbShopEvaluateEntity)){
						continue;
					}else{
						userOrderVo.setTcsbOrderParent(tcsbOrderParentEntity);
						//店铺信息
						TcsbShopEntity tcsbShopEntity =tcsbOrderService.get(TcsbShopEntity.class, tcsbOrderParentEntity.getShopId());
						TcsbShopEntity tcsbShopEntity2 = new TcsbShopEntity();
						try {
							BeanUtils.copyProperties(tcsbShopEntity2, tcsbShopEntity);
							tcsbShopEntity2.setHeadimg(getAppletCkPath()+tcsbShopEntity2.getHeadimg().replace("images", "_thumbs/Images"));
							userOrderVo.setTcsbShop(tcsbShopEntity2);
						} catch (IllegalAccessException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (InvocationTargetException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						//获取订单中的菜单项
						double allMoney = 0.0;
						double itemNum = 0.0;
						List<TcsbOrderEntity> tcsbOrderEntity = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderParentId", tcsbUserOrderEntity.getOrderParentId());
						List<TcsbOrderItemEntity> tcsbOrderItemEntityList2 = new ArrayList<>();
						for (TcsbOrderEntity tcsbOrderEntity2 : tcsbOrderEntity) {
							List<TcsbOrderItemEntity> TcsbOrderItemEntityList = tcsbOrderService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity2.getId());
								for (TcsbOrderItemEntity tcsbOrderItemEntity : TcsbOrderItemEntityList) {
									tcsbOrderItemEntityList2.add(tcsbOrderItemEntity);
									double mulMoney = BigDecimalUtil.mul(tcsbOrderItemEntity.getCount(), tcsbOrderItemEntity.getPrice());
									allMoney = BigDecimalUtil.add(allMoney, mulMoney);
									itemNum =  BigDecimalUtil.add(itemNum,tcsbOrderItemEntity.getNum());
								}
						}
						userOrderVo.setAllMoney(BigDecimalUtil.numericRetentionDecimal(allMoney, 2));
						userOrderVo.setItemNum(BigDecimalUtil.numericRetentionDecimal(itemNum, 0));
						
						List<TcsbOrderItemEntity> tcsbOrderItemEntityList3 = new ArrayList<>();
						if(tcsbOrderItemEntityList2.size()>2){
							for (int i = 0; i<3;i++) {
								tcsbOrderItemEntityList3.add(tcsbOrderItemEntityList2.get(i));
							}
							userOrderVo.setTcsbOrderItemList(tcsbOrderItemEntityList3);
						}else{
							userOrderVo.setTcsbOrderItemList(tcsbOrderItemEntityList2);
						}
						userOrderVo.setEvaluate(false);
						userOrderVo.setOrderStateName("订单完成");
						userOrderVo.setOrderStateCode(1);
						userOrderVoList.add(userOrderVo);
					}
				}else{
					continue;
				}
			}
			ajaxJson.setMsg("加载成功");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(userOrderVoList);	
		}else{
			ajaxJson.setMsg("无订单");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);	
		}
		
		return ajaxJson;
	}
	
	
	/**
	 * 预约订单
	 * @param userId
	 * 
	 */
	@RequestMapping("/appointmentOrder")
	@ResponseBody
	public AjaxJsonApi appointmentOrder(String userId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		
		ajaxJson.setMsg("无订单");
		ajaxJson.setSuccess(false);
		ajaxJson.setObj(null);	
		return ajaxJson;
	}
	
	/**
	 * 订单评论
	 * @param userId
	 * @param orderId
	 * @param content 评论内容
	 * @param dishesEvaluation 菜品口味
	 * @param diningEnvironment 就餐环境
	 * @param servingSpeed 上菜速度
	 * @param evaluationLevel 是否好评1好评0坏评
	 * @return
	 */
	@RequestMapping("/evaluateOrder")
	@ResponseBody
	public AjaxJsonApi evaluateOrder(@RequestParam String shopId,@RequestParam String userId,@RequestParam String orderId,String content,@RequestParam int dishesEvaluation,@RequestParam int diningEnvironment,@RequestParam int servingSpeed,@RequestParam int evaluationLevel){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		String hql = "from TcsbShopEvaluateEntity where orderId='"+orderId+"' and userId='"+userId+"'";
		TcsbShopEvaluateEntity tcsbShopEvaluateEntity = tcsbOrderService.singleResult(hql);
		if(StringUtil.isNotEmpty(tcsbShopEvaluateEntity)){
			ajaxJson.setMsg("已提交过评价");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);	
		}else{
			
			if(content.indexOf("共产党") != -1){
				ajaxJson.setMsg("评价中内容过于敏感");
				ajaxJson.setSuccess(false);
				ajaxJson.setObj(null);	
			}else{
				TcsbShopEvaluateEntity tcsbShopEvaluate = new TcsbShopEvaluateEntity();
				tcsbShopEvaluate.setContent(content);
				tcsbShopEvaluate.setCreateTime(new Date());
				tcsbShopEvaluate.setDiningEnvironment(diningEnvironment);
				tcsbShopEvaluate.setDishesEvaluation(dishesEvaluation);
				tcsbShopEvaluate.setEvaluationLevel(evaluationLevel);
				tcsbShopEvaluate.setOrderId(orderId);
				tcsbShopEvaluate.setServingSpeed(servingSpeed);
				tcsbShopEvaluate.setShopId(shopId);
				tcsbShopEvaluate.setUserId(userId);
				tcsbOrderService.save(tcsbShopEvaluate);
				ajaxJson.setMsg("评价成功");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(null);	
			}
		}
		return ajaxJson;
	}
	
	
	

}
