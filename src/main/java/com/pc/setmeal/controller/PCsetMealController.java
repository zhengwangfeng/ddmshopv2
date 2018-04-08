package com.pc.setmeal.controller;

import java.util.Date;

import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodmealfun.entity.TcsbFoodMealFunEntity;
import com.tcsb.service.service.TcsbServiceServiceI;

@Controller
@RequestMapping("/setMeal")
public class PCsetMealController {
	
	@Autowired
	private TcsbServiceServiceI iservice;
	
	@RequestMapping("/creatSetMeal")
	@ResponseBody
	public AjaxJsonApi creatSetMealAndReturnFoodid(String shopId,String foodName,String foodPrice,String foodIdString){
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		
		if(StringUtil.isNotEmpty(foodName) && StringUtil.isNotEmpty(foodPrice) && StringUtil.isNotEmpty(foodIdString)){
			//创建食品
			TcsbFoodEntity food = new TcsbFoodEntity();
			food.setCreateBy("admin");
			food.setCreateDate(new Date());
			food.setCreateName("admin");
			food.setDiscountPrice(0.0);
			food.setExpiryDate(null);
			food.setFoodTypeId("402881ed5afe078c015afe096c8a0008");
			food.setImg("/zhangyang/images/1b05f38475a9495aa62152b20082a675.jpg");
			food.setIschara("0");
			food.setIsCurrentPrice("0");
			food.setIsdelete(1);
			food.setIsDis("0");
			food.setIsSetMeal("1");
			food.setName(foodName);
			food.setOrders(1);
			food.setPrice(Double.valueOf(foodPrice));
			food.setShopId(shopId);
			food.setStatus("0");
			food.setUnitId("402880e95cc883c8015cc88ba163000d");
			food.setUnitName("份");
			iservice.save(food);
			if(foodIdString.contains(",")){
				String[] foodidarray = foodIdString.split(",");
				for(int i =0 ;i<foodidarray.length;i++){
					TcsbFoodMealFunEntity tff = new TcsbFoodMealFunEntity();
					tff.setParentId(food.getId());
					tff.setFoodId(foodidarray[i]);
					iservice.save(tff);
				}
			}else{
				TcsbFoodMealFunEntity tff = new TcsbFoodMealFunEntity();
				tff.setParentId(food.getId());
				tff.setFoodId(foodIdString);
				iservice.save(tff);
			}
			
			ajaxJsonApi.setObj(food);
			ajaxJsonApi.setSuccess(true);
			ajaxJsonApi.setMsg("创建成功");
		}else{
			ajaxJsonApi.setSuccess(false);
			ajaxJsonApi.setMsg("参数存在空值");
		}
		return ajaxJsonApi;
	}
	
	
	
	

}
