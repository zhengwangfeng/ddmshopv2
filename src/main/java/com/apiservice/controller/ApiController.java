package com.apiservice.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.apiservice.common.utils.OutputResult;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodtype.controller.TcsbFoodTypeController;
import com.tcsb.foodtype.service.TcsbFoodTypeServiceI;
import com.tcsb.shop.service.TcsbShopServiceI;

@RestController
@Controller
@RequestMapping("/api/v1")
public class ApiController extends BaseController{
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbFoodTypeController.class);

	@Autowired
	private TcsbFoodTypeServiceI tcsbFoodTypeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	
	@ResponseBody
	@RequestMapping(value = "/listByShopId",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	public OutputResult listByShopId(@RequestParam String shopId,HttpServletResponse response){
		List<Map<String,Object>> listTcsbFoodTypes=tcsbFoodTypeService.findForJdbc("select id,name,type_img as typeImg from tcsb_food_type where shop_id = ?", shopId);
		if(StringUtil.isEmpty(shopId)){
            return new OutputResult(ReturnMessageEnum.LACKVALUEORNULL.getStatus(), ReturnMessageEnum.LACKVALUEORNULL.getMsg(), null);
        }
		if (listTcsbFoodTypes.isEmpty()) {
			return new OutputResult(ReturnMessageEnum.SHOPNOTEXIT.getStatus(), ReturnMessageEnum.SHOPNOTEXIT.getMsg(), null);
		}
		return new OutputResult(ReturnMessageEnum.QUERYTRUE.getStatus(), ReturnMessageEnum.QUERYTRUE.getMsg(), null);
	}
}
