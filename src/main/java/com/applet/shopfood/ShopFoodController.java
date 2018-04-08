/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月16日 下午6:00:59
 */
package com.applet.shopfood;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.NumberComparator3;
import org.jeecgframework.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.applet.shopfood.vo.AppletFoodVo;
import com.applet.shopfood.vo.AppletIndexFoodByT;
import com.applet.shopfood.vo.FoodStandardPageVo;
import com.applet.shopfood.vo.FoodStandardVo;
import com.applet.shopfood.vo.FoodTasteAndStandardVo;
import com.applet.shopfood.vo.FoodTypeVo;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.food.vo.FoodTypeVO;
import com.tcsb.food.vo.FoodVO;
import com.tcsb.foodstandard.entity.TcsbFoodStandardEntity;
import com.tcsb.foodstandardrelationship.entity.TcsbFoodStandardRelationshipEntity;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsbfoodfunction.entity.TcsbFoodFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.entity.TcsbFoodTasteFunctionEntity;
import com.tcsb.tcsbfoodtastefunction.vo.FoodTastePageVo;
import com.tcsb.tcsbfoodtastefunction.vo.FoodTasteVo;
import com.tcsb.tcsbfoodunit.entity.TcsbFoodUnitEntity;
import com.tcsb.weixinAppletAPI.weixinAppletAPIController;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月16日 下午6:00:59
 */
@Controller
@RequestMapping("/appletshopfood")
public class ShopFoodController extends BaseController{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(weixinAppletAPIController.class);
	
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	
	
	/**
	 * 
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/getFoodType")
	@ResponseBody
	public AjaxJsonApi getFoodType(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		String hql = "from TcsbFoodTypeEntity where (isDel is not 1 or isDel is null) and shopId ='"+shopId+"' order by orders desc";
		List<FoodTypeVo> foodTypeList = new ArrayList<>();
		FoodTypeVo tpv;
		List<TcsbFoodTypeEntity> tp = tcsbFoodService.findByQueryString(hql);
		for (TcsbFoodTypeEntity tcsbFoodTypeEntity : tp) {
			tpv = new FoodTypeVo();
			tpv.setId(tcsbFoodTypeEntity.getId());
			tpv.setName(tcsbFoodTypeEntity.getName());
			foodTypeList.add(tpv);
		}
		
		ajaxJson.setObj(foodTypeList);
		ajaxJson.setMsg("请求成功");
		ajaxJson.setSuccess(true);
		return ajaxJson;
	}
	
	/**
	 * 
	 * @param typeId
	 * @return
	 */
	@RequestMapping("/getFoodByTypeId")
	@ResponseBody
	public AjaxJsonApi getFoodByTypeId(String typeId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		String hql = "from TcsbFoodEntity where foodTypeId ='" + typeId + "' and status='1' and isdelete=0 order by orders desc";
	    List<TcsbFoodEntity> food = tcsbFoodService.findByQueryString(hql);
	    if (food.size() > 0)
	    {
	      List<AppletFoodVo> foodList = new ArrayList();
	      for (TcsbFoodEntity tf : food)
	      {
	        AppletFoodVo foodVo = new AppletFoodVo();
	        foodCheckStateAndFunAndCurrentPrice(foodVo, tf);
	        foodVo.setId(tf.getId());
	        if (StringUtil.isNotEmpty(tf.getImg())) {
	          //foodVo.setImg(getAppletCkPath() + tf.getImg().replace("images", "_thumbs/Images"));
	          foodVo.setImg(getAppletCkPath() + tf.getImg());
	        } else {
	          foodVo.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
	        }
	        foodVo.setName(tf.getName());
	        foodVo.setNum(0);
	        foodVo.setPrice(tf.getPrice().doubleValue());
	        foodList.add(foodVo);
	      }
			ajaxJson.setObj(food);
			ajaxJson.setMsg("请求成功");
			ajaxJson.setSuccess(true);
		}else{
			ajaxJson.setObj(null);
			ajaxJson.setMsg("请求成功");
			ajaxJson.setSuccess(false);
		}
		
		return ajaxJson;
	}
	
	 public void foodCheckStateAndFunAndCurrentPrice(AppletFoodVo foodVo, TcsbFoodEntity tf)
	  {
	    if ("N".equals(tf.getIsCurrentPrice())) {
	      foodVo.setCurrentPrice(false);
	    }
	    if ("Y".equals(tf.getIsCurrentPrice())) {
	      foodVo.setCurrentPrice(true);
	    }
	    if (StringUtil.isNotEmpty(tf.getUnitId()))
	    {
	      TcsbFoodUnitEntity foodUnit = (TcsbFoodUnitEntity)this.tcsbFoodService.get(TcsbFoodUnitEntity.class, tf.getUnitId());
	      foodVo.setUnitName(foodUnit.getName());
	    }
	    else
	    {
	      foodVo.setUnitName("份");
	    }
	    String sql = "select id from tcsb_food_function where foodid='" + tf.getId() + "'";
	    List<Object> ovoList1 = this.tcsbFoodService.findListbySql(sql);
	    if (ovoList1.size() > 0) {
	      foodVo.setFoodTaste(true);
	    } else {
	      foodVo.setFoodTaste(false);
	    }
	    List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", tf.getId());
	    if (funsize.size() > 0) {
	      foodVo.setFoodstandard(true);
	    } else {
	      foodVo.setFoodstandard(false);
	    }
	  }
	
	/**
	 * 堂点首页数据
	 * 店铺信息，桌位信息，菜品信息
	 * @param deskId
	 * @param request
	 * @param response
	 * @return
	 */
	@Deprecated
	@RequestMapping("/TDshopIndex")
	@ResponseBody
	public AjaxJsonApi listByShopIdTTTT(@RequestParam String deskId,HttpServletRequest request,HttpServletResponse response){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		TcsbDeskEntity tDesk = tcsbFoodService.get(TcsbDeskEntity.class, deskId);
		
		if(StringUtil.isNotEmpty(tDesk)){
			AppletIndexFoodByT appletIndex = new AppletIndexFoodByT();
			appletIndex.setTcsbDesk(tDesk);
			//获取店铺信息
			TcsbShopEntity tShop = tcsbFoodService.get(TcsbShopEntity.class, tDesk.getShopId());
			TcsbShopEntity tcsbShopDto = new TcsbShopEntity();
			try {
				BeanUtils.copyProperties(tcsbShopDto, tShop);
				tcsbShopDto.setHeadimg(getAppletCkPath()+tcsbShopDto.getHeadimg());
				//tcsbShopDto.setHeadimg(getAppletCkPath()+tcsbShopDto.getHeadimg().toString().replace("images", "_thumbs/Images"));
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			appletIndex.setTcsbShop(tcsbShopDto);
			//获取菜品信息
			List<Map<String, Object>> listTcsbFoodEntitys = tcsbFoodService.findForJdbc("select id, is_current_price as currentPrice,food_type_id as foodTypeId,orders,name,price,img,unitId from tcsb_food where shop_id = ? and status=1 and is_delete=0 order by food_type_id",tDesk.getShopId());
			List<FoodTypeVO> foodTypeVOs = new ArrayList<>();
			List<FoodVO> foodVOs = new ArrayList<>();
			
			for (int i = 0; i < listTcsbFoodEntitys.size(); i++) {
				//是否有重复的
				boolean flag = false;
				Map<String, Object> map =listTcsbFoodEntitys.get(i);
				String foodTypeId = (String) map.get("foodTypeId");
				int  index = 0;
				if (!foodTypeVOs.isEmpty()) {
					for (int j = 0; j < foodTypeVOs.size(); j++) {
						FoodTypeVO foodTypeVO = foodTypeVOs.get(j);
						if (foodTypeVO.getId().equals(foodTypeId)) {
							//查找到相同菜品
							flag = true;
							index = j;
							break;
						}
					}
					//重复的就重新添加
					if (flag) {
						FoodTypeVO foodTypeVO =foodTypeVOs.get(index);
						FoodVO foodVO = new FoodVO();
						foodVO.setFoodTypeId(foodTypeId);
						foodVO.setId((String)map.get("id"));
						System.out.println("===========================");
						System.out.println((String)map.get("id"));
						if(StringUtil.isNotEmpty(map.get("img"))){
							//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
							foodVO.setImg(getAppletCkPath()+map.get("img").toString());
						}else{
							foodVO.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
						}
						//foodVO.setImg(getAppletCkPath()+ map.get("img").toString().replace("images", "_thumbs/Images"));
						foodVO.setName((String)map.get("name"));
						foodVO.setPrice((Double)map.get("price"));
						foodVO.setOrders((Integer)map.get("orders"));
						if ("N".equals((String)map.get("currentPrice"))) {
							foodVO.setCurrentPrice(false);
						}
						if ("Y".equals((String)map.get("currentPrice"))) {
							foodVO.setCurrentPrice(true);
						}
						
						foodVO.setNum(0);
						/***start 获取商品单位以及检查商品是否时价 mar_x*/
						String unitId = (String)map.get("unitId");
						
						if(StringUtil.isNotEmpty(unitId)){
							TcsbFoodUnitEntity foodUnit = tcsbFoodService.get(TcsbFoodUnitEntity.class, unitId);
							if(StringUtil.isNotEmpty(foodUnit)){
								foodVO.setUnitName(foodUnit.getName());
								/*if(foodUnit.getIsfloat().equals("1")){
									foodVO.setCurrentPrice(true);
								}else{
									foodVO.setCurrentPrice(false);
								}*/
							}
						}else{
							//foodVO.setCurrentPrice(false);
						}
						/***end 获取商品单位以及检查商品是否时价 mar_x*/
						/***start 检查商品是否具有可选规格 mar_x*/
						String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
						List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
						if(ovoList1.size()>0){
							foodVO.setFoodTaste(true);
						}else{
							foodVO.setFoodTaste(false);
						}
						List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
			            if (funsize.size() > 0) {
			              foodVO.setFoodstandard(true);
			              foodVO.setFoodTaste(true);
			            } else {
			              foodVO.setFoodstandard(false);
			            }
						foodTypeVO.getList().add(foodVO);
					}else {
						FoodTypeVO foodTypeVO = new FoodTypeVO();
						map =listTcsbFoodEntitys.get(i);
						//根据foodTypeId去获取菜品名称
						TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodService.findUniqueByProperty(TcsbFoodTypeEntity.class, "id", foodTypeId);
						foodTypeVO.setId(foodTypeId);
						foodTypeVO.setName(tcsbFoodTypeEntity.getName());
						foodTypeVO.setOrders(tcsbFoodTypeEntity.getOrders());
						FoodVO foodVO = new FoodVO();
						foodVO.setFoodTypeId(foodTypeId);
						foodVO.setId((String)map.get("id"));
						if(StringUtil.isNotEmpty(map.get("img"))){
							//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
							foodVO.setImg(getAppletCkPath()+map.get("img").toString());
						}else{
							foodVO.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
						}
						
						foodVO.setName((String)map.get("name"));
						foodVO.setPrice((Double)map.get("price"));
						foodVO.setOrders((Integer)map.get("orders"));
						foodVO.setNum(0);
						if ("N".equals((String)map.get("currentPrice"))) {
							foodVO.setCurrentPrice(false);
						}
						if ("Y".equals((String)map.get("currentPrice"))) {
							foodVO.setCurrentPrice(true);
						}
						
						/***start 获取商品单位以及检查商品是否时价 mar_x*/
						String unitId = (String)map.get("unitId");
						if(StringUtil.isNotEmpty(unitId)){
							TcsbFoodUnitEntity foodUnit = tcsbFoodService.get(TcsbFoodUnitEntity.class, unitId);
							if(StringUtil.isNotEmpty(foodUnit)){
								foodVO.setUnitName(foodUnit.getName());
								/*if(foodUnit.getIsfloat().equals("1")){
									foodVO.setCurrentPrice(true);
								}else{
									foodVO.setCurrentPrice(false);
								}*/
							}
						}
						/***end 获取商品单位以及检查商品是否时价 mar_x*/
						/***start 检查商品是否具有可选规格 mar_x*/
						String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
						List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
						if(ovoList1.size()>0){
							foodVO.setFoodTaste(true);
						}else{
							foodVO.setFoodTaste(false);
						}
						List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
			            if (funsize.size() > 0) {
			              foodVO.setFoodstandard(true);
			              foodVO.setFoodTaste(true);
			            } else {
			              foodVO.setFoodstandard(false);
			            }
						List<FoodVO> foodVOsnew = new ArrayList<>();
						foodVOsnew.add(foodVO);
						foodTypeVO.setList(foodVOsnew);
						foodTypeVOs.add(foodTypeVO);
					}
					
				}else {
					FoodTypeVO foodTypeVO = new FoodTypeVO();
					map =listTcsbFoodEntitys.get(i);
					//根据foodTypeId去获取菜品名称
					TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodService.findUniqueByProperty(TcsbFoodTypeEntity.class, "id", foodTypeId);
					foodTypeVO.setId(foodTypeId);
					foodTypeVO.setName(tcsbFoodTypeEntity.getName());
					foodTypeVO.setOrders(tcsbFoodTypeEntity.getOrders());
					FoodVO foodVO = new FoodVO();
					foodVO.setFoodTypeId(foodTypeId);
					foodVO.setId((String)map.get("id"));
					//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
					if(StringUtil.isNotEmpty(map.get("img"))){
						//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
						foodVO.setImg(getAppletCkPath()+map.get("img").toString());
					}else{
						foodVO.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
					}
					foodVO.setName((String)map.get("name"));
					foodVO.setPrice((Double)map.get("price"));
					if ("N".equals((String)map.get("currentPrice"))) {
						foodVO.setCurrentPrice(false);
					}
					if ("Y".equals((String)map.get("currentPrice"))) {
						foodVO.setCurrentPrice(true);
					}
					
					foodVO.setOrders((Integer)map.get("orders"));
					foodVO.setNum(0);
					
					/***start 获取商品单位以及检查商品是否时价 mar_x*/
					String unitId = (String)map.get("unitId");
					if(StringUtil.isNotEmpty(unitId)){
						TcsbFoodUnitEntity foodUnit = tcsbFoodService.get(TcsbFoodUnitEntity.class, unitId);
						if(StringUtil.isNotEmpty(foodUnit)){
							foodVO.setUnitName(foodUnit.getName());
						/*	if(foodUnit.getIsfloat().equals("1")){
								foodVO.setCurrentPrice(true);
							}else{
								foodVO.setCurrentPrice(false);
							}*/
						}
					}
					/***end 获取商品单位以及检查商品是否时价 mar_x*/
					/***start 检查商品是否具有可选规格 mar_x*/
					String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
					List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
					if(ovoList1.size()>0){
						foodVO.setFoodTaste(true);
					}else{
						foodVO.setFoodTaste(false);
					}
					List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
		            if (funsize.size() > 0) {
		              foodVO.setFoodstandard(true);
		              foodVO.setFoodTaste(true);
		            } else {
		              foodVO.setFoodstandard(false);
		            }
					foodVOs.add(foodVO);
					foodTypeVO.setList(foodVOs);
					foodTypeVOs.add(foodTypeVO);
				}
			}
			//排序菜品
				ListSorter.sortDesc(foodTypeVOs, "orders");
			//排序食物
			for (int i = 0; i < foodTypeVOs.size(); i++) {
				List<FoodVO> tem = foodTypeVOs.get(i).getList();
				ListSorter.sortDesc(tem, "orders");
			}
			
			//获取店铺优惠
			String hql = "from TcsbShopFullcutTemplateEntity where shopId=?";
			List<TcsbShopFullcutTemplateEntity> TcsbShopFullcutTemplateEntity = tcsbFoodService.findHql(hql, tDesk.getShopId());
			if(StringUtil.isNotEmpty(TcsbShopFullcutTemplateEntity)){
				String promotion = "";
				for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity2 : TcsbShopFullcutTemplateEntity) {
					promotion = promotion + "满"+tcsbShopFullcutTemplateEntity2.getTotal()+"减"+tcsbShopFullcutTemplateEntity2.getDiscount()+",";
				}
				if(StringUtil.isNotEmpty(promotion)){
					promotion = promotion.substring(0, promotion.length()-1);
				}
				appletIndex.setPromotion(promotion);
			}else{
				appletIndex.setPromotion(null);
			}
			
			appletIndex.setFoodType(foodTypeVOs);
			ajaxJson.setMsg("查询成功");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(appletIndex);
			
			
		}else{
			ajaxJson.setMsg("deskId不存在");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);
		}
		
		
		
		return ajaxJson;
	}
	
	
	
	/**
	 * 预览菜品
	 * @param shopid
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/shopfoodlist")
	@ResponseBody
	public AjaxJsonApi listByShopId(@RequestParam String shopid,HttpServletRequest request,HttpServletResponse response){
		List<Map<String, Object>> listTcsbFoodEntitys = tcsbFoodService.findForJdbc("select id,is_current_price as currentPrice,food_type_id as foodTypeId,orders,name,price,img,unitId from tcsb_food where shop_id = ? and status=1 and is_delete=0 order by food_type_id",shopid);
		List<FoodTypeVO> foodTypeVOs = new ArrayList<>();
		List<FoodVO> foodVOs = new ArrayList<>();
		AppletIndexFoodByT appletIndexFoodByT = new AppletIndexFoodByT();
		for (int i = 0; i < listTcsbFoodEntitys.size(); i++) {
			//是否有重复的
			boolean flag = false;
			Map<String, Object> map =listTcsbFoodEntitys.get(i);
			String foodTypeId = (String) map.get("foodTypeId");
			int  index = 0;
			if (!foodTypeVOs.isEmpty()) {
				for (int j = 0; j < foodTypeVOs.size(); j++) {
					FoodTypeVO foodTypeVO = foodTypeVOs.get(j);
					if (foodTypeVO.getId().equals(foodTypeId)) {
						//查找到相同菜品
						flag = true;
						index = j;
						break;
					}
				}
				//重复的就重新添加
				if (flag) {
					FoodTypeVO foodTypeVO =foodTypeVOs.get(index);
					FoodVO foodVO = new FoodVO();
					foodVO.setFoodTypeId(foodTypeId);
					foodVO.setId((String)map.get("id"));
					if(StringUtil.isNotEmpty(map.get("img"))){
						//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
						foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString());
					}else{
						foodVO.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
					}
					foodVO.setName((String)map.get("name"));
					foodVO.setPrice((Double)map.get("price"));
					foodVO.setOrders((Integer)map.get("orders"));
					if ("N".equals((String)map.get("currentPrice"))) {
						foodVO.setCurrentPrice(false);
					}
					if ("Y".equals((String)map.get("currentPrice"))) {
						foodVO.setCurrentPrice(true);
					}
					
					foodVO.setNum(0);
					/***start 获取商品单位以及检查商品是否时价 mar_x*/
					String unitId = (String)map.get("unitId");
					if(StringUtil.isNotEmpty(unitId)){
						TcsbFoodUnitEntity foodUnit = tcsbFoodService.get(TcsbFoodUnitEntity.class, unitId);
						if(StringUtil.isNotEmpty(foodUnit)){
							foodVO.setUnitName(foodUnit.getName());
						}
					}
					/***end 获取商品单位以及检查商品是否时价 mar_x*/
					/***start 检查商品是否具有可选规格 mar_x*/
					String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
					List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
					if(ovoList1.size()>0){
						foodVO.setFoodTaste(true);
					}else{
						foodVO.setFoodTaste(false);
					}
					List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
		            if (funsize.size() > 0) {
		              foodVO.setFoodstandard(true);
		              foodVO.setFoodTaste(true);
		            } else {
		              foodVO.setFoodstandard(false);
		            }
					foodTypeVO.getList().add(foodVO);
				}else {
					FoodTypeVO foodTypeVO = new FoodTypeVO();
					map =listTcsbFoodEntitys.get(i);
					//根据foodTypeId去获取菜品名称
					TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodService.findUniqueByProperty(TcsbFoodTypeEntity.class, "id", foodTypeId);
					foodTypeVO.setId(foodTypeId);
					foodTypeVO.setName(tcsbFoodTypeEntity.getName());
					foodTypeVO.setOrders(tcsbFoodTypeEntity.getOrders());
					FoodVO foodVO = new FoodVO();
					foodVO.setFoodTypeId(foodTypeId);
					foodVO.setId((String)map.get("id"));
					//TODO
					if(StringUtil.isNotEmpty(map.get("img"))){
						//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
						foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString());
					}else{
						foodVO.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
					}
					foodVO.setName((String)map.get("name"));
					foodVO.setPrice((Double)map.get("price"));
					if ("N".equals((String)map.get("currentPrice"))) {
						foodVO.setCurrentPrice(false);
					}
					if ("Y".equals((String)map.get("currentPrice"))) {
						foodVO.setCurrentPrice(true);
					}
					
					foodVO.setOrders((Integer)map.get("orders"));
					foodVO.setNum(0);
					/***start 获取商品单位以及检查商品是否时价 mar_x*/
					String unitId = (String)map.get("unitId");
					if(StringUtil.isNotEmpty(unitId)){
						TcsbFoodUnitEntity foodUnit = tcsbFoodService.get(TcsbFoodUnitEntity.class, unitId);
						if(StringUtil.isNotEmpty(foodUnit)){
							foodVO.setUnitName(foodUnit.getName());
						}
					}
					/***end 获取商品单位以及检查商品是否时价 mar_x*/
					/***start 检查商品是否具有可选规格 mar_x*/
					String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
					List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
					if(ovoList1.size()>0){
						foodVO.setFoodTaste(true);
					}else{
						foodVO.setFoodTaste(false);
					}
					List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
		            if (funsize.size() > 0) {
		              foodVO.setFoodstandard(true);
		              foodVO.setFoodTaste(true);
		            } else {
		              foodVO.setFoodstandard(false);
		            }
					List<FoodVO> foodVOsnew = new ArrayList<>();
					foodVOsnew.add(foodVO);
					foodTypeVO.setList(foodVOsnew);
					foodTypeVOs.add(foodTypeVO);
				}
				
			}else {
				FoodTypeVO foodTypeVO = new FoodTypeVO();
				map =listTcsbFoodEntitys.get(i);
				//根据foodTypeId去获取菜品名称
				TcsbFoodTypeEntity tcsbFoodTypeEntity = tcsbFoodService.findUniqueByProperty(TcsbFoodTypeEntity.class, "id", foodTypeId);
				foodTypeVO.setId(foodTypeId);
				foodTypeVO.setName(tcsbFoodTypeEntity.getName());
				foodTypeVO.setOrders(tcsbFoodTypeEntity.getOrders());
				FoodVO foodVO = new FoodVO();
				foodVO.setFoodTypeId(foodTypeId);
				foodVO.setId((String)map.get("id"));
				if(StringUtil.isNotEmpty(map.get("img"))){
					//foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString().replace("images", "_thumbs/Images"));
					foodVO.setImg(getAppletCkPath()+(String)map.get("img").toString());
				}else{
					foodVO.setImg("https://ceshi.diandanme.xyz/ddan/appletImg/none.png");
				}
				foodVO.setName((String)map.get("name"));
				foodVO.setPrice((Double)map.get("price"));
				foodVO.setOrders((Integer)map.get("orders"));
				if ("N".equals((String)map.get("currentPrice"))) {
					foodVO.setCurrentPrice(false);
				}
				if ("Y".equals((String)map.get("currentPrice"))) {
					foodVO.setCurrentPrice(true);
				}
				
				foodVO.setNum(0);
				
				/***start 获取商品单位以及检查商品是否时价 mar_x*/
				String unitId = (String)map.get("unitId");
				if(StringUtil.isNotEmpty(unitId)){
					TcsbFoodUnitEntity foodUnit = tcsbFoodService.get(TcsbFoodUnitEntity.class, unitId);
					if(StringUtil.isNotEmpty(foodUnit)){
						foodVO.setUnitName(foodUnit.getName());
					}
				}
				/***end 获取商品单位以及检查商品是否时价 mar_x*/
				/***start 检查商品是否具有可选规格 mar_x*/
				String sql = "select id from tcsb_food_function where foodid='"+(String)map.get("id")+"'";
				List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
				if(ovoList1.size()>0){
					foodVO.setFoodTaste(true);
				}else{
					foodVO.setFoodTaste(false);
				}
				List<TcsbFoodStandardRelationshipEntity> funsize = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", (String)map.get("id"));
	            if (funsize.size() > 0) {
	              foodVO.setFoodstandard(true);
	              foodVO.setFoodTaste(true);
	            } else {
	              foodVO.setFoodstandard(false);
	            }
				foodVOs.add(foodVO);
				foodTypeVO.setList(foodVOs);
				foodTypeVOs.add(foodTypeVO);
			}
		}
		//排序菜品
			ListSorter.sortDesc(foodTypeVOs, "orders");
		//排序食物
		for (int i = 0; i < foodTypeVOs.size(); i++) {
			List<FoodVO> tem = foodTypeVOs.get(i).getList();
			ListSorter.sortDesc(tem, "orders");
		}
		
		
		//获取店铺优惠
		String hql = "from TcsbShopFullcutTemplateEntity where shopId=?";
		List<TcsbShopFullcutTemplateEntity> TcsbShopFullcutTemplateEntity = tcsbFoodService.findHql(hql, shopid);
		if(StringUtil.isNotEmpty(TcsbShopFullcutTemplateEntity)){
			String promotion = "";
			for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity2 : TcsbShopFullcutTemplateEntity) {
				promotion = promotion + "满"+tcsbShopFullcutTemplateEntity2.getTotal()+"减"+tcsbShopFullcutTemplateEntity2.getDiscount()+",";
			}
			if(StringUtil.isNotEmpty(promotion)){
				promotion = promotion.substring(0, promotion.length()-1);
			}
			appletIndexFoodByT.setPromotion(promotion);
		}else{
			appletIndexFoodByT.setPromotion(null);
		}
		
		appletIndexFoodByT.setFoodType(foodTypeVOs);
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		ajaxJson.setMsg("查询成功");
		ajaxJson.setSuccess(true);
		ajaxJson.setObj(appletIndexFoodByT);
		return ajaxJson;
	}
	
	/**
	 * 获取食品规格
	 * @param foodId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/getfoodTasteFun")
	@ResponseBody
	public AjaxJsonApi getfoodTasteFun(@RequestParam String foodId,HttpServletRequest request,HttpServletResponse response){
		//String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<TcsbFoodFunctionEntity> tcsbFoodFunctionEntities = tcsbFoodService.findHql("from TcsbFoodFunctionEntity t where t.tcsbFoodEntity.id = ?", foodId);
		//存主规格信息
		List<TcsbFoodFunctionEntity> tcsbFoodFunctionEntities2 = new ArrayList<>();
		//存不是主规格的信息
		List<TcsbFoodFunctionEntity> tcsbFoodFunctionEntities3 = new ArrayList<>();
		//获取规格信息
		for (TcsbFoodFunctionEntity tcsbFoodFunctionEntity : tcsbFoodFunctionEntities) {
			TcsbFoodTasteFunctionEntity tcsbFoodTasteFunctionEntity = tcsbFoodFunctionEntity.getTcsbFoodTasteFunctionEntity();
			//主规格情况
			if (tcsbFoodTasteFunctionEntity.getFunctionlevel()==0) {
				tcsbFoodFunctionEntities2.add(tcsbFoodFunctionEntity);
			}else {
				tcsbFoodFunctionEntities3.add(tcsbFoodFunctionEntity);
			}
		}
		//读主规格信息
		List<FoodTastePageVo> foodTastePageVoList = new ArrayList<>();
		
		for (int i = 0; i < tcsbFoodFunctionEntities2.size(); i++) {
			FoodTastePageVo foodTastePageVo = new FoodTastePageVo();
			foodTastePageVo.setFirstFun(tcsbFoodFunctionEntities2.get(i).getTcsbFoodTasteFunctionEntity().getStatename());
			List<TcsbFoodTasteFunctionEntity> tcsbFoodTasteFunctionEntities  = tcsbFoodFunctionEntities2.get(i).getTcsbFoodTasteFunctionEntity().getTcsbFoodTasteFunctionEntitys();
			List<String> neList = new ArrayList<>();
			for (TcsbFoodTasteFunctionEntity t : tcsbFoodTasteFunctionEntities) {
				neList.add(t.getId());
			}
			List<FoodTasteVo> foodTasteVolist = new ArrayList<>();
			for (int j = 0; j < tcsbFoodFunctionEntities3.size(); j++) {
				if (neList.contains(tcsbFoodFunctionEntities3.get(j).getTcsbFoodTasteFunctionEntity().getId())) {
					FoodTasteVo foodTasteVo = new FoodTasteVo();
					foodTasteVo.setId(tcsbFoodFunctionEntities2.get(i).getId());
					foodTasteVo.setTasteName(tcsbFoodFunctionEntities3.get(j).getTcsbFoodTasteFunctionEntity().getStatename());
					foodTasteVolist.add(foodTasteVo);
					
				}
			}
			foodTastePageVo.setFoodTasteVo(foodTasteVolist);
			foodTastePageVoList.add(foodTastePageVo);
		}
		
		List<TcsbFoodStandardRelationshipEntity> standard = tcsbFoodService.findByProperty(TcsbFoodStandardRelationshipEntity.class, "foodId", foodId);
	    FoodStandardPageVo foodStandard = new FoodStandardPageVo();
	    List<FoodStandardVo> fstandardList = new ArrayList();
	    if (standard.size() > 0)
	    {
	      foodStandard.setStandaraFrist("规格");
	      for (TcsbFoodStandardRelationshipEntity s : standard)
	      {
	        FoodStandardVo fs = new FoodStandardVo();
	        TcsbFoodStandardEntity ts = tcsbFoodService.get(TcsbFoodStandardEntity.class, s.getFoodStandardId());
	        fs.setStandardId(s.getFoodStandardId());
	        fs.setStandardName(ts.getName());
	        fs.setStandardPrice(ts.getPrice().doubleValue());
	        fstandardList.add(fs);
	      }
	      foodStandard.setFoodStandard(fstandardList);
	    }
	    FoodTasteAndStandardVo fts = new FoodTasteAndStandardVo();
	    fts.setFoodTaste(foodTastePageVoList);
	    fts.setFoodStandard(foodStandard);
	    
	    AjaxJsonApi ajaxJson = new AjaxJsonApi();
	    ajaxJson.setMsg("查询成功");
	    ajaxJson.setSuccess(true);
	    ajaxJson.setObj(fts);
		
		return ajaxJson;
	}
	
	@RequestMapping(params = "checkfoodTasteFun")
	@ResponseBody
	public int checkfoodTasteFun(String foodId,HttpServletRequest request,HttpServletResponse response){
		String sql = "select id from tcsb_food_function where foodid='"+foodId+"'";
		List<Object> ovoList1 = tcsbFoodService.findListbySql(sql);
		if(ovoList1.size()>0){
			return 1;
		}else{
			return 0;
		}
		
	}

}
