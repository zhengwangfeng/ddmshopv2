package com.pc.order.vo;

import java.util.List;

import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;

public class PcOrderVo {
	
	private TcsbOrderParentEntity orderparent;
	
	private List<TcsbOrderEntity> order;
	 
	private List<SubOrderVo> subOrder; 
	 

	public TcsbOrderParentEntity getOrderparent() {
		return orderparent;
	}

	public void setOrderparent(TcsbOrderParentEntity orderparent) {
		this.orderparent = orderparent;
	}

	public List<TcsbOrderEntity> getOrder() {
		return order;
	}

	public void setOrder(List<TcsbOrderEntity> order) {
		this.order = order;
	}

	public List<SubOrderVo> getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(List<SubOrderVo> subOrder) {
		this.subOrder = subOrder;
	}
	

	 
}
