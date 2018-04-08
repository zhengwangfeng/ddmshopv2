/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年11月2日 下午6:06:16
 */
package com.applet.userOrder;

import java.util.List;

import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年11月2日 下午6:06:16
 */
public class UserOrderVo {
	
	private TcsbOrderParentEntity tcsbOrderParent;
	
	private TcsbOrderEntity tcsbOrder;
	
	private List<TcsbOrderItemEntity> tcsbOrderItemList;
	
	private TcsbShopEntity tcsbShop;
	
	private String orderStateName;
	private int orderStateCode;
	
	private String allMoney;
	private String itemNum;
	
	
	private boolean evaluate;

	public TcsbOrderEntity getTcsbOrder() {
		return tcsbOrder;
	}

	public void setTcsbOrder(TcsbOrderEntity tcsbOrder) {
		this.tcsbOrder = tcsbOrder;
	}

	public List<TcsbOrderItemEntity> getTcsbOrderItemList() {
		return tcsbOrderItemList;
	}

	public void setTcsbOrderItemList(List<TcsbOrderItemEntity> tcsbOrderItemList) {
		this.tcsbOrderItemList = tcsbOrderItemList;
	}

	public TcsbOrderParentEntity getTcsbOrderParent() {
		return tcsbOrderParent;
	}

	public void setTcsbOrderParent(TcsbOrderParentEntity tcsbOrderParent) {
		this.tcsbOrderParent = tcsbOrderParent;
	}

	public String getOrderStateName() {
		return orderStateName;
	}

	public void setOrderStateName(String orderStateName) {
		this.orderStateName = orderStateName;
	}

	public int getOrderStateCode() {
		return orderStateCode;
	}

	public void setOrderStateCode(int orderStateCode) {
		this.orderStateCode = orderStateCode;
	}

	public String getAllMoney() {
		return allMoney;
	}

	public void setAllMoney(String allMoney) {
		this.allMoney = allMoney;
	}

	

	public TcsbShopEntity getTcsbShop() {
		return tcsbShop;
	}

	public void setTcsbShop(TcsbShopEntity tcsbShop) {
		this.tcsbShop = tcsbShop;
	}

	public boolean isEvaluate() {
		return evaluate;
	}

	public void setEvaluate(boolean evaluate) {
		this.evaluate = evaluate;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}








}
