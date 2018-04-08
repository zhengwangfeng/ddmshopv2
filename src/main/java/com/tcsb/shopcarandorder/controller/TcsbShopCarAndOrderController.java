package com.tcsb.shopcarandorder.controller;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.shopcar.VO.TcsbShopCarVO;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;

/**   
 * @Title: Controller  
 * @Description: 购物车
 * @author onlineGenerator
 * @date 2017-04-26 21:44:06
 * @version V1.0   
 *
 */
@Controller
@RequestMapping("/tcsbShopCarAndOrderController")
public class TcsbShopCarAndOrderController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbShopCarAndOrderController.class);

	@Autowired
	private TcsbShopCarServiceI tcsbShopCarService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	
	/**
	 * 获取我的购物车
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getMyShopCar",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyShopCar(@RequestParam String userId,@RequestParam String status,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<TcsbShopCarVO> mapShopCar = new ArrayList<>();
		List<TcsbOrderVO> mapOrder = new ArrayList<>();
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		try {
			//1代表是购物车 3未付款，4已付款
			if (status.equals("1")) {
				mapShopCar = tcsbShopCarService.getMyShopCar(userId);
				for (TcsbShopCarVO tcsbShopCarVO : mapShopCar) {
					tcsbShopCarVO.setShopImg(getCkPath()+tcsbShopCarVO.getShopImg());
				}
				ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
				ajaxJsonApi.setSuccess(true);
				ajaxJsonApi.setObj(mapShopCar);
			}
			else {
				mapOrder = tcsbOrderService.getMyOrder(userId,status);
				for (TcsbOrderVO tcsbOrderVO : mapOrder) {
					tcsbOrderVO.setShopImg(getCkPath()+tcsbOrderVO.getShopImg());
				}
				ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
				ajaxJsonApi.setSuccess(true);
				ajaxJsonApi.setObj(mapOrder);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
}
