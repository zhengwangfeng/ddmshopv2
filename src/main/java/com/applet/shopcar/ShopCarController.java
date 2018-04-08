/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月16日 下午2:21:19
 */
package com.applet.shopcar;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.apiservice.common.utils.ReturnMessageEnum;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopcar.VO.TcsbShopCarItemVO;
import com.tcsb.shopcar.VO.TcsbShopCarVO;
import com.tcsb.shopcar.entity.TcsbShopCarEntity;
import com.tcsb.shopcar.service.TcsbShopCarServiceI;
import com.tcsb.shopcaritem.entity.TcsbShopCarItemEntity;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.usercar.entity.TcsbUserCarEntity;
import com.tcsb.weixinAppletAPI.weixinAppletAPIController;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月16日 下午2:21:19
 */
@Controller
@RequestMapping("/appletShopCar")
public class ShopCarController extends BaseController{
	
	
	
	/**
	 * Logger for tcsbShopCarService class
	 */
	private static final Logger logger = Logger.getLogger(weixinAppletAPIController.class);
	
	
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	
	@Autowired
	private TcsbShopCarServiceI tcsbShopCarService;
	
	/**
	 * 根据桌位号获取购物车商品
	 * @param deskId
	 * @return
	 */
	@RequestMapping("/getShopcarItem")
	@ResponseBody
	public AjaxJsonApi getShopCarItem(String deskId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		if(StringUtil.isNotEmpty(deskId)){
			TcsbShopCarEntity shopCar = tcsbShopCarService.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
			if(StringUtil.isNotEmpty(shopCar)){
				String hql = "from TcsbShopCarItemEntity where shopCar=?";
				List<TcsbShopCarItemVo> tcsbShopCarItemVoList = new ArrayList<>();
				TcsbShopCarItemVo tcsbShopCarItemVo;
				List<TcsbShopCarItemEntity> tcsbShopCarItemList = tcsbShopCarService.findHql(hql, shopCar.getId());
				for (TcsbShopCarItemEntity tcsbShopCarItemEntity : tcsbShopCarItemList) {
					tcsbShopCarItemVo = new TcsbShopCarItemVo();
					try {
						BeanUtils.copyProperties(tcsbShopCarItemVo, tcsbShopCarItemEntity);
						if(StringUtil.isNotEmpty(tcsbShopCarItemVo.getFoodTasteFun())){
							tcsbShopCarItemVo.setFoodTaste(true);
						}else{
							tcsbShopCarItemVo.setFoodTaste(false);
						}
						
						TcsbFoodEntity tf = tcsbShopCarService.get(TcsbFoodEntity.class, tcsbShopCarItemEntity.getFoodId());
						if (StringUtil.isNotEmpty(tcsbShopCarItemEntity.getStandardId()))
			            {
			              TcsbFoodStandardEntity tfs = tcsbShopCarService.get(TcsbFoodStandardEntity.class, tcsbShopCarItemEntity.getStandardId());
			              tcsbShopCarItemVo.setFoodname(tf.getName());
			              tcsbShopCarItemVo.setPrice(tfs.getPrice());
			              tcsbShopCarItemVo.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun() + "/" + tfs.getName());
			              tcsbShopCarItemVo.setFoodstandard(true);
			            }
			            else if (StringUtil.isNotEmpty(tf))
			            {
			              tcsbShopCarItemVo.setFoodname(tf.getName());
			              tcsbShopCarItemVo.setPrice(tf.getPrice());
			              tcsbShopCarItemVo.setFoodstandard(false);
			            }
						tcsbShopCarItemVoList.add(tcsbShopCarItemVo);
					} catch (IllegalAccessException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				ajaxJson.setMsg("query success");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(tcsbShopCarItemVoList);
			}else{
				ajaxJson.setMsg("query success:shopcar is NULL or ID is not existent");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(null);
			}
		}else{
			ajaxJson.setMsg("error:ID cannot NULL");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);	
		}
		return ajaxJson;
	}
	
	
	
	/**
	 * 添加菜品
	 * @param deskId
	 * @param tcsbShopCarItem
	 * @param openId
	 * @return
	 */
	@RequestMapping("/addShopcarItem")
	@ResponseBody
	public AjaxJsonApi addShopCarItem(String deskId,String shopcarItem,String userId){
		//deskId = "8a9ccf875cbf14a4015cbf3bac1e0049";
		//userId = "oZdMf0Y9IAo1NRsZIwOwTnYYSzPY";
		//shopcarItem = "[{'foodId':'8a9ccf875d39d3cd015d3ebf2f3a030b','num':1,'currentPrice':true,'foodTaste':true}]";
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		if(StringUtil.isNotEmpty(deskId) && StringUtil.isNotEmpty(userId)){
			List<TcsbShopCarItemVo>  tcsbShopCarItemList = JSON.parseArray(shopcarItem, TcsbShopCarItemVo.class);
			System.out.println(tcsbShopCarItemList);
			if(tcsbShopCarItemList.size()>0){
				TcsbShopCarEntity shopCar = tcsbShopCarService.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
				if(StringUtil.isNotEmpty(shopCar)){
					//购物车存在
					for (TcsbShopCarItemVo tcsbShopCarItemVo : tcsbShopCarItemList) {
						if(tcsbShopCarItemVo.isCurrentPrice()){
							//时价菜品
							if(tcsbShopCarItemVo.getCount()==1){
								savetcsbShopCarItem(tcsbShopCarItemVo, shopCar.getId());
							}else{
								for(int i=0;i<tcsbShopCarItemVo.getCount();i++){
									 savetcsbShopCarItem(tcsbShopCarItemVo, shopCar.getId());
								}
							}
						}else{
							//非时价菜品
							if(tcsbShopCarItemVo.isFoodTaste()){
								//菜品有规格
								savetcsbShopCarItem(tcsbShopCarItemVo, shopCar.getId());
							}else{
								//菜品无规格
								//此处做非时价商品
								//直接添加
								String hql = "from TcsbShopCarItemEntity where foodId='"+tcsbShopCarItemVo.getFoodId()+"' and shopCar='"+shopCar.getId()+"'";
								TcsbShopCarItemEntity addtcsbShopCarItem = tcsbShopCarService.singleResult(hql);
								if(StringUtil.isNotEmpty(addtcsbShopCarItem)){
									//addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getNum());
									addtcsbShopCarItem.setCount(addtcsbShopCarItem.getCount()+tcsbShopCarItemVo.getCount());
									tcsbShopCarService.saveOrUpdate(addtcsbShopCarItem);
								}else{
									TcsbShopCarItemEntity savetcsbShopCarItem = new TcsbShopCarItemEntity();
									savetcsbShopCarItem.setCount(tcsbShopCarItemVo.getCount());
									savetcsbShopCarItem.setFoodId(tcsbShopCarItemVo.getFoodId());
									savetcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun());
									savetcsbShopCarItem.setShopCar(shopCar.getId());
									savetcsbShopCarItem.setCurrentPrice(tcsbShopCarItemVo.isCurrentPrice());
									savetcsbShopCarItem.setStandardId(tcsbShopCarItemVo.getStandardId());
									tcsbShopCarService.save(savetcsbShopCarItem);
								}
							}
						}
					}
					saveUserCar(shopCar.getId(), userId);
				}else{
					//购物车不存在
					//创建购物车
					TcsbShopCarEntity tcsbShopCarEntity = new TcsbShopCarEntity();
					tcsbShopCarEntity.setDeskId(deskId);
					tcsbShopCarEntity.setIsLock(1);
					try {
						tcsbShopCarService.save(tcsbShopCarEntity);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					for (TcsbShopCarItemVo tcsbShopCarItemVo : tcsbShopCarItemList) {
						if(tcsbShopCarItemVo.isCurrentPrice()){
							//时价菜品
							if(tcsbShopCarItemVo.getCount()==1){
								savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
							}else{
								for(int i=0;i<tcsbShopCarItemVo.getCount();i++){
									savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
								}
							}
						}else{
							//非时价菜品
							if(tcsbShopCarItemVo.isFoodTaste()){
								//菜品有规格
								savetcsbShopCarItem(tcsbShopCarItemVo, tcsbShopCarEntity.getId());
							}else{
								//菜品无规格
								TcsbShopCarItemEntity savetcsbShopCarItem = new TcsbShopCarItemEntity();
								savetcsbShopCarItem.setCount(tcsbShopCarItemVo.getCount());
								savetcsbShopCarItem.setFoodId(tcsbShopCarItemVo.getFoodId());
								savetcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun());
								savetcsbShopCarItem.setShopCar(tcsbShopCarEntity.getId());
								savetcsbShopCarItem.setCurrentPrice(tcsbShopCarItemVo.isCurrentPrice());
								savetcsbShopCarItem.setStandardId(tcsbShopCarItemVo.getStandardId());
								tcsbShopCarService.save(savetcsbShopCarItem);
							}
						}
					}
					saveUserCar(tcsbShopCarEntity.getId(), userId);
				}
				
				ajaxJson.setMsg("添加菜单项成功");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(true);
			}else{
				ajaxJson.setMsg("添加菜单项为空");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(false);
			}
		}else{
			ajaxJson.setMsg("deskId or userId为空");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(false);	
		}
		return ajaxJson;
	}
	
	
	 public void savetcsbShopCarItem(TcsbShopCarItemVo tcsbShopCarItemVo, String carId)
	  {
	    TcsbShopCarItemEntity savetcsbShopCarItem = new TcsbShopCarItemEntity();
	    savetcsbShopCarItem.setCount(Integer.valueOf(1));
	    savetcsbShopCarItem.setFoodId(tcsbShopCarItemVo.getFoodId());
	    savetcsbShopCarItem.setFoodTasteFun(tcsbShopCarItemVo.getFoodTasteFun());
	    savetcsbShopCarItem.setShopCar(carId);
	    savetcsbShopCarItem.setCurrentPrice(tcsbShopCarItemVo.isCurrentPrice());
	    savetcsbShopCarItem.setStandardId(tcsbShopCarItemVo.getStandardId());
	    tcsbShopCarService.save(savetcsbShopCarItem);
	  }
	  
	  public void saveUserCar(String carId, String userId)
	  {
	    String hql = "from TcsbUserCarEntity where carId ='" + carId + "' and userId ='" + userId + "'";
	    TcsbUserCarEntity tcsbUserCar = tcsbShopCarService.singleResult(hql);
	    if (StringUtil.isEmpty(tcsbUserCar))
	    {
	      TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
	      tcsbUserCarEntity.setCreateTime(new Date());
	      tcsbUserCarEntity.setUserId(userId);
	      tcsbUserCarEntity.setCarId(carId);
	      tcsbShopCarService.save(tcsbUserCarEntity);
	    }
	  }
	  
	
	
	
	/**
	 * 添加更新菜品购物车
	 * @param userId
	 * @param deskId
	 * @param itemId
	 * @param status 0删1加
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/addOrupdateshopcar")
	@ResponseBody
	public AjaxJsonApi addOrUpdateShopCar(@RequestParam String userId,@RequestParam String deskId,@RequestParam String itemId,@RequestParam String status,HttpServletRequest request,HttpServletResponse response){
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
		if(StringUtil.isNotEmpty(tcsbShopCarEntity)){
			if(tcsbShopCarEntity.getIsLock().equals(1)){
				//删除操作
				if(status.equals("0")){
					TcsbShopCarItemEntity tcsbShopCarItem = tcsbShopCarService.get(TcsbShopCarItemEntity.class, itemId);
					if(StringUtil.isNotEmpty(tcsbShopCarItem)){
						if(tcsbShopCarItem.getCount()>1){
							tcsbShopCarItem.setCount(tcsbShopCarItem.getCount()-1);
							tcsbShopCarService.saveOrUpdate(tcsbShopCarItem);
						}else{
							tcsbShopCarService.delete(tcsbShopCarItem);
						}
						//查询是否已保存到多人购物信息中
						String hql = "from TcsbUserCarEntity where carId ='"+tcsbShopCarEntity.getId()+"' and userId ='"+userId+"'";
						TcsbUserCarEntity tcsbUserCar = tcsbShopCarService.singleResult(hql);
						//不存在就做增加
						if (StringUtil.isEmpty(tcsbUserCar)) {
							TcsbUserCarEntity tcsbUserCarEntity = new TcsbUserCarEntity();
							tcsbUserCarEntity.setCreateTime(new Date());
							tcsbUserCarEntity.setUserId(userId);
							tcsbUserCarEntity.setCarId(tcsbShopCarEntity.getId());
							tcsbShopCarService.save(tcsbUserCarEntity);
						}
						ajaxJsonApi.setMsg("删除失败成功");
						ajaxJsonApi.setSuccess(true);
					}else{
						ajaxJsonApi.setMsg("删除失败，菜品不存在");
						ajaxJsonApi.setSuccess(false);
					}
				}
				
				//添加操作
				if(status.equals("1")){
					TcsbShopCarItemEntity tcsbShopCarItem = tcsbShopCarService.get(TcsbShopCarItemEntity.class, itemId);
			        if (StringUtil.isNotEmpty(tcsbShopCarItem))
			        {
			          TcsbFoodEntity tcsbFoodEntity = tcsbShopCarService.get(TcsbFoodEntity.class, tcsbShopCarItem.getFoodId());
			          if (StringUtil.isNotEmpty(tcsbFoodEntity))
			          {
			            if ("N".equals(tcsbFoodEntity.getIsCurrentPrice())) {
			              if (StringUtil.isNotEmpty(tcsbShopCarItem.getFoodTasteFun()))
			              {
			                saveFoodItem(tcsbShopCarItem);
			              }
			              else if (StringUtil.isNotEmpty(tcsbShopCarItem.getStandardId()))
			              {
			                saveFoodItem(tcsbShopCarItem);
			              }
			              else
			              {
			                tcsbShopCarItem.setCount(Integer.valueOf(tcsbShopCarItem.getCount().intValue() + 1));
			                tcsbShopCarService.saveOrUpdate(tcsbShopCarItem);
			              }
			            }
			            if ("Y".equals(tcsbFoodEntity.getIsCurrentPrice())) {
			              saveFoodItem(tcsbShopCarItem);
			            }
			            saveUserCar(tcsbShopCarEntity.getId(), userId);
							ajaxJsonApi.setMsg("添加成功");
							ajaxJsonApi.setSuccess(true);
						}else{
							ajaxJsonApi.setMsg("添加失败，菜品不存在");
							ajaxJsonApi.setSuccess(false);
						}
					}else{
						ajaxJsonApi.setMsg("添加失败，菜品已被删除");
						ajaxJsonApi.setSuccess(false);
					}
				}
				if(!status.equals("1") && !status.equals("0")){
					ajaxJsonApi.setMsg("操作指令出错");
					ajaxJsonApi.setSuccess(false);
				}
				
			}else{
				ajaxJsonApi.setMsg("订单正在处理中");
				ajaxJsonApi.setSuccess(false);
			}
		}else{
			ajaxJsonApi.setMsg("购物车不存在");
			ajaxJsonApi.setSuccess(false);
		}
		return ajaxJsonApi;
	}
	
	
	 public void saveFoodItem(TcsbShopCarItemEntity tcsbShopCarItem)
	  {
	    TcsbShopCarItemEntity addShopCarItem = new TcsbShopCarItemEntity();
	    addShopCarItem.setFoodId(tcsbShopCarItem.getFoodId());
	    addShopCarItem.setFoodTasteFun(tcsbShopCarItem.getFoodTasteFun());
	    addShopCarItem.setCount(Integer.valueOf(1));
	    addShopCarItem.setShopCar(tcsbShopCarItem.getShopCar());
	    addShopCarItem.setStandardId(tcsbShopCarItem.getStandardId());
	    tcsbShopCarService.save(addShopCarItem);
	  }
	
	/**
	 * 检测是否有更新菜品
	 * @param deskId
	 * @return
	 */
	@RequestMapping("/checkShopCarNew")
	@ResponseBody
	public AjaxJsonApi checkShopCarNew(String shopCarItem,String deskId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		
		List<TcsbShopCarItemVo> tcsbShopCarVO = JSON.parseArray(shopCarItem, TcsbShopCarItemVo.class);
		//是否是最新
		boolean flag = true;
		//根据deskId获取购物车信息
		//List<Map<String, Object>> mapShopCarItem = tcsbShopCarService.findForJdbc("select i.food_id as foodId,i.count from tcsb_shop_car_item i left join tcsb_shop_car c on c.id=i.shop_car where c.desk_id = ?", deskId);
		TcsbShopCarEntity tcsbShopCarEntity = tcsbShopCarService.findUniqueByProperty(TcsbShopCarEntity.class, "deskId", deskId);
		if(StringUtil.isNotEmpty(tcsbShopCarEntity)){
			String hql = "from TcsbShopCarItemEntity where shopCar=?";
			List<TcsbShopCarItemEntity> tcsbShopCarItemList = tcsbShopCarService.findHql(hql, tcsbShopCarEntity.getId());
			if (StringUtil.isEmpty(tcsbShopCarItemList)) {
				flag = false;
			}else {
				if (tcsbShopCarVO.isEmpty()) {
					flag = false;
				}else {
					if (tcsbShopCarVO.size()==tcsbShopCarItemList.size()) {
						for (int i = 0; i < tcsbShopCarVO.size(); i++) {
							for (int j = 0; j < tcsbShopCarItemList.size(); j++) {
								if (tcsbShopCarVO.get(i).getId().equals(tcsbShopCarItemList.get(j).getId())) {
									if (!tcsbShopCarVO.get(i).getCount().equals(tcsbShopCarItemList.get(j).getCount())) {
										flag = false;
										break;
									}
								}
							}
						}
					}
					else {
						flag = false;
					}
				}
			}
		}else{
			flag = false;
	}


		if(flag){
			ajaxJson.setMsg("菜单相同");
		}else{
			ajaxJson.setMsg("菜单有更新");
		}
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(flag);

		return ajaxJson;
	}
	
	
	/***
	 * 修改购物车菜品规格
	 * @param id
	 * @param foodTasteFun
	 * @return
	 */
	@RequestMapping("/updateCarFoodFunstate")
	@ResponseBody
	public AjaxJsonApi updateCarFoodFunstate(String id,String foodTasteFun,String standardId){
		
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
	    TcsbShopCarItemEntity tcsbShopCarItemEntity = tcsbShopCarService.get(TcsbShopCarItemEntity.class, id);
	    if (StringUtil.isNotEmpty(tcsbShopCarItemEntity))
	    {
	      if ((!foodTasteFun.equals("N")) && (StringUtil.isNotEmpty(foodTasteFun))) {
	        tcsbShopCarItemEntity.setFoodTasteFun(foodTasteFun);
	      }
	      if ((!standardId.equals("N")) && (StringUtil.isNotEmpty(standardId))) {
	        tcsbShopCarItemEntity.setStandardId(standardId);
	      }
	      this.tcsbShopCarService.saveOrUpdate(tcsbShopCarItemEntity);
			ajaxJson.setMsg("修改成功");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(true);
		}else{
			ajaxJson.setMsg("修改失败");
			ajaxJson.setObj(null);
			ajaxJson.setSuccess(false);
		}
		return ajaxJson;
	}
	
}
