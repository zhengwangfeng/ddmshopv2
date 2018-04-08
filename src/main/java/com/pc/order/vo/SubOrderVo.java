package com.pc.order.vo;

import java.util.List;

import com.tcsb.suborder.entity.TcsbSubOrderEntity;

public class SubOrderVo {
	
	private TcsbSubOrderEntity subOrder;
	
	private List<SubOrderItemVo> subOrderItemVo;

	public TcsbSubOrderEntity getSubOrder() {
		return subOrder;
	}

	public void setSubOrder(TcsbSubOrderEntity subOrder) {
		this.subOrder = subOrder;
	}

	public List<SubOrderItemVo> getSubOrderItemVo() {
		return subOrderItemVo;
	}

	public void setSubOrderItemVo(List<SubOrderItemVo> subOrderItemVo) {
		this.subOrderItemVo = subOrderItemVo;
	}
	

}
