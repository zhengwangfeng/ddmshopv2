package com.pc.order.vo;

import java.util.List;

import com.tcsb.order.entity.TcsbOrderEntity;

public class HandPrintVo {
	
	private TcsbOrderEntity order;
	
	private List<OrderItemVo> orderItemList;

	public TcsbOrderEntity getOrder() {
		return order;
	}

	public void setOrder(TcsbOrderEntity order) {
		this.order = order;
	}

	public List<OrderItemVo> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItemVo> orderItemList) {
		this.orderItemList = orderItemList;
	}

}
