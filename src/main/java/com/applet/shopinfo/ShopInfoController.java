/**
 * ddmShopV2
 * @author Mar_x
 * create on 2017 2017年10月13日 上午9:20:22
 */
package com.applet.shopinfo;

import java.lang.reflect.InvocationTargetException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.crypto.Data;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;
import com.tcsb.order.VO.OrderStatisticVo;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.shopevaluate.entity.TcsbShopEvaluateEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.shophours.entity.TcsbShopHoursEntity;
import com.tcsb.shophoursperiod.entity.TcsbShopHoursPeriodEntity;
import com.tcsb.tcsbweixinuser.entity.TcsbWeixinUserEntity;
import com.tcsb.weixinAppletAPI.weixinAppletAPIController;
import com.weixin.util.DateUtils;

import freemarker.template.utility.DateUtil;

/**
 * ddmShopV2
 *
 *
 * @author Mar_x
 * create on 2017年10月13日 上午9:20:22
 */
@Controller
@RequestMapping("/appletShop")
public class ShopInfoController extends BaseController{
	
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(weixinAppletAPIController.class);
	
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	
	/**
	 * 根据shopId获取店铺的详情信息
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/getShopInfo")
	@ResponseBody
	public AjaxJsonApi getShopInfo(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		
		ShopInfoVo shopInfoVo = new ShopInfoVo();
		if(StringUtil.isNotEmpty(shopId)){
			TcsbShopEntity tcsbShopInfo= tcsbShopService.get(TcsbShopEntity.class, shopId);
			if(StringUtil.isNotEmpty(tcsbShopInfo)){
				TcsbShopEntity tcsbShopDto = new TcsbShopEntity();
				try {
					BeanUtils.copyProperties(tcsbShopDto, tcsbShopInfo);
					//tcsbShopDto.setHeadimg(getAppletCkPath()+tcsbShopDto.getHeadimg().replace("images", "_thumbs/Images"));
					tcsbShopDto.setHeadimg(getAppletCkPath()+tcsbShopDto.getHeadimg());
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				shopInfoVo.setTcsbShop(tcsbShopDto);
				//获取店铺优惠
				
				List<TcsbDiscountActivityEntity> tcsbDiscountActivity = tcsbShopService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", shopId);
				if(tcsbDiscountActivity.size()>0){
					String promotion = "";
					for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivity) {
						TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate = tcsbShopService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
						promotion = promotion + "满"+tcsbShopFullcutTemplate.getTotal()+"减"+tcsbShopFullcutTemplate.getDiscount()+",";
					}
					shopInfoVo.setPromotion(promotion);
				}else{
					shopInfoVo.setPromotion("暂无优惠信息");
				}
				
				//TODO
				shopInfoVo.setDiscountTitle(null);
				
				
				String businessHours = "";
				//获取店铺营业时间
				List<TcsbShopHoursEntity> tcsbShopHours = tcsbShopService.findByProperty(TcsbShopHoursEntity.class, "shopId", shopId);
				if(tcsbShopHours.size()>0){
					for (TcsbShopHoursEntity tcsbShopHoursEntity : tcsbShopHours) {
						String hour = DateUtils.short_time_sdf.format(tcsbShopHoursEntity.getBeginTime()) + "-" +DateUtils.short_time_sdf.format(tcsbShopHoursEntity.getEndTime());
						businessHours = hour +";"+ businessHours;
					}
					shopInfoVo.setBusinessHours(businessHours.substring(0, businessHours.length()-1));
				}else{
					shopInfoVo.setBusinessHours("无");
				}
				
				
				//查找店铺轮播图片
				List<TcsbImagesManagerEntity> tcsbImagesManagerEntities = tcsbShopService.findHql("from TcsbImagesManagerEntity where shopId = ?", shopId);
				List<Object> shopImgs = new ArrayList<>() ;
				if (!tcsbImagesManagerEntities.isEmpty()) {
					for (TcsbImagesManagerEntity tcsbImagesManagerEntity : tcsbImagesManagerEntities) {
						shopImgs.add(getAppletCkPath()+tcsbImagesManagerEntity.getfPath());
					}
				}
				shopInfoVo.setShopImgs(shopImgs);
				ajaxJson.setMsg("查询成功");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(shopInfoVo);
			}else{
				ajaxJson.setMsg("error:ex000002");
				ajaxJson.setSuccess(false);
				ajaxJson.setObj(null);	
			}
		}else{
			ajaxJson.setMsg("error:ex000001");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);
		}
		return ajaxJson;
	}
	
	
	@RequestMapping("/getShopDetail")
	@ResponseBody
	public AjaxJsonApi getShopdetail(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		if(StringUtil.isNotEmpty(shopId)){
			TcsbShopEntity tcsbShopInfo= tcsbShopService.get(TcsbShopEntity.class, shopId);
			if(StringUtil.isNotEmpty(tcsbShopInfo)){
				
				TcsbShopEntity tcsbShopDto = new TcsbShopEntity();
				try {
					BeanUtils.copyProperties(tcsbShopDto, tcsbShopInfo);
					tcsbShopDto.setHeadimg(getAppletCkPath()+tcsbShopDto.getHeadimg());
					//tcsbShopDto.setHeadimg(getAppletCkPath()+tcsbShopDto.getHeadimg().replace("images", "_thumbs/Images"));
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ajaxJson.setMsg("查询成功");
				ajaxJson.setSuccess(true);
				ajaxJson.setObj(tcsbShopDto);
			}else{
				ajaxJson.setMsg("error:ex000002");
				ajaxJson.setSuccess(false);
				ajaxJson.setObj(null);	
			}
		}else{
			ajaxJson.setMsg("error:ex000001");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj(null);
		}
		return ajaxJson;
	}
	
	
	
	
	/**
	 * 根据经纬度获取附件店铺的列表
	 * @param lat 维度
	 * @param lng 经度
	 * @param range 获取范围值（0-12756km）
	 * @return
	 */
	@RequestMapping("/getnearbyshoplist")
	@ResponseBody
	public AjaxJsonApi getNearbyShopList(@RequestParam String lat,@RequestParam String lng,String range){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		if(StringUtil.isEmpty(range)){
			range= "13000";
		}
		String hql="from TcsbShopEntity u where u.latitude is not null and u.longitude is not null and "+
		"(ACOS(SIN(("+lat+" * 3.1415) / 180 ) *SIN((u.latitude * 3.1415) / 180 ) +COS(("+lat+" * 3.1415) / 180 ) * COS((u.latitude * 3.1415) / 180 ) *COS(("+lng+" * 3.1415) / 180 - (u.longitude * 3.1415) / 180 ) ) * 6378.140<"+range+")"+
		" order by ACOS(SIN(("+lat+" * 3.1415) / 180 ) *SIN((u.latitude * 3.1415) / 180 ) +COS(("+lat+" * 3.1415) / 180 ) * COS((u.latitude * 3.1415) / 180 ) *COS(("+lng+" * 3.1415) / 180 - (u.longitude * 3.1415) / 180 ) ) * 6378.140 asc";  
		List<TcsbShopEntity> TcsbShopInfoList= tcsbShopService.findByQueryString(hql);
		if(StringUtil.isNotEmpty(TcsbShopInfoList)){
			ajaxJson.setMsg("success");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(TcsbShopInfoList);
		}else{
			ajaxJson.setMsg("success");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(null);
		}
		return ajaxJson;
	}
	
	
	/**
	 * 根据经纬度获取附件店铺的列表
	 * 分页加载
	 * @param lat 维度
	 * @param lng 经度
	 * @param page 当前页数(0----)
	 * @param limit 每页数量
	 * @return
	 */
	@RequestMapping("/getnearbyshoplistonpage")
	@ResponseBody
	public AjaxJsonApi getNearbyShopListOnpage(@RequestParam String lat,@RequestParam String lng,int page,int limit){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		page = page * limit;
		String sql = "select id,name,headImg,address,acos(sin("+lat+"*3.1415/180)*sin(latitude*3.1415/180)+cos("+lat+"*3.1415/180)*cos(latitude*3.1415/180)*cos("+lng+"*3.1415/180-(longitude*3.1415/180)))*6378.140  from tcsb_shop where status='0' and (latitude is not null)and (longitude is not null) order by acos(sin("+lat+"*3.1415/180)*sin(latitude*3.1415/180)+cos("+lat+"*3.1415/180)*cos(latitude*3.1415/180)*cos("+lng+"*3.1415/180-(longitude*3.1415/180)))*6378.140 asc limit "+page+","+limit;
		List<Object> tcsbShopInfoSqlList= tcsbShopService.findListbySql(sql);
		List<ShopAddreeInfo> shopAddreeInfoList = new ArrayList<>();
		ShopAddreeInfo shopAddreeInfo;
	     for(Iterator iterator = tcsbShopInfoSqlList.iterator();iterator.hasNext();){ 
	            Object[] objects = (Object[]) iterator.next(); 
	            shopAddreeInfo = new ShopAddreeInfo();
	            shopAddreeInfo.setShopId(objects[0].toString());
	            shopAddreeInfo.setShopName(objects[1].toString());
	            if(StringUtil.isNotEmpty(objects[2])){
	            	//shopAddreeInfo.setHeadImg(getAppletCkPath()+objects[2].toString().replace("images", "_thumbs/Images"));
	            	shopAddreeInfo.setHeadImg(getAppletCkPath()+objects[2].toString());
	            }else{
	            	shopAddreeInfo.setHeadImg(null);
	            }
	            shopAddreeInfo.setAddress(objects[3].toString());
	            double dis = Double.valueOf(objects[4].toString());
		        
	            if(dis<1){
	            	if(dis == 0){
			        	shopAddreeInfo.setDistance("100内米");
	            	}else{
	            		dis = dis * 100;
			        	shopAddreeInfo.setDistance(BigDecimalUtil.numericRetentionDecimal(dis, 2) + "米");
	            	}
		        }else{
		        	shopAddreeInfo.setDistance(BigDecimalUtil.numericRetentionDecimal(dis, 0) + "千米");  
		        }
	            
	            //获取店铺优惠
	            
	            List<TcsbDiscountActivityEntity> tcsbDiscountActivity = tcsbShopService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", objects[0].toString());
				if(tcsbDiscountActivity.size()>0){
					String promotion = "";
					for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivity) {
						TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplate = tcsbShopService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
						promotion = promotion + "满"+tcsbShopFullcutTemplate.getTotal()+"减"+tcsbShopFullcutTemplate.getDiscount()+",";
					}
					shopAddreeInfo.setPromotion(promotion);
				}else{
					shopAddreeInfo.setPromotion("暂无优惠信息");
				}
	            
	            
//	    		String hql = "from TcsbShopFullcutTemplateEntity where shopId=?";
//	    		List<TcsbShopFullcutTemplateEntity> TcsbShopFullcutTemplateEntity = tcsbShopService.findHql(hql, objects[0].toString());
//	    		if(StringUtil.isNotEmpty(TcsbShopFullcutTemplateEntity)){
//	    			String promotion = "";
//	    			for (TcsbShopFullcutTemplateEntity tcsbShopFullcutTemplateEntity2 : TcsbShopFullcutTemplateEntity) {
//	    				promotion = promotion + "满"+tcsbShopFullcutTemplateEntity2.getTotal()+"减"+tcsbShopFullcutTemplateEntity2.getDiscount()+",";
//	    			}
//	    			if(StringUtil.isNotEmpty(promotion)){
//	    				promotion = promotion.substring(0, promotion.length()-1);
//	    				shopAddreeInfo.setPromotion(promotion);
//	    			}else{
//	    				shopAddreeInfo.setPromotion("暂无优惠信息");
//	    			}
//	    		}else{
//	    			shopAddreeInfo.setPromotion("暂无优惠信息");
//	    		}
	    		
	    		//获取商家订单信息
	    		 
	    		 String orderhql = "from TcsbOrderEntity where shopId='"+objects[0].toString()+"' and payStatus=1";
	    		List<TcsbOrderEntity> tcsbOrderEntity = tcsbShopService.findByQueryString(orderhql);
	    		if(tcsbOrderEntity.size()>0){
	    			double allsale = 0.0;
	    			for (TcsbOrderEntity tcsbOrderEntity2 : tcsbOrderEntity) {
						allsale  = BigDecimalUtil.add(allsale, tcsbOrderEntity2.getTotalPrice());
					}
	    			shopAddreeInfo.setBuyPeopleNum(tcsbOrderEntity.size()+"");
	    			String avgsale = BigDecimalUtil.divide(allsale+"", tcsbOrderEntity.size()+"", 0);
	    			shopAddreeInfo.setAvgSale(avgsale);
	    		}else{
	    			shopAddreeInfo.setBuyPeopleNum("0");
	    			shopAddreeInfo.setAvgSale("0");
	    		}
	    		
	    		shopAddreeInfoList.add(shopAddreeInfo);
	            
		}
		
		
		//String hql="from TcsbShopEntity u where u.latitude is not null and u.longitude is not null order by ACOS(SIN(("+lat+" * 3.1415) / 180 ) *SIN((u.latitude * 3.1415) / 180 ) +COS(("+lat+" * 3.1415) / 180 ) * COS((u.latitude * 3.1415) / 180 ) *COS(("+lng+" * 3.1415) / 180 - (u.longitude * 3.1415) / 180 ) ) * 6378.140 asc limit 0,10";  
		//List<TcsbShopEntity> TcsbShopInfoList= tcsbShopService.findByQueryString(hql);
		if(StringUtil.isNotEmpty(tcsbShopInfoSqlList)){
			ajaxJson.setMsg("success");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(shopAddreeInfoList);
		}else{
			ajaxJson.setMsg("success");
			ajaxJson.setSuccess(true);
			ajaxJson.setObj(null);
		}
		return ajaxJson;
	}
	
	/**
	 * 获取预定营业时间段
	 * @param shopId
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/getShopHours")
	@ResponseBody
	public AjaxJsonApi getShopHours(String shopId) throws ParseException{

		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		List<ShopHoursVo> shopHoursVoList = new ArrayList<>();
		ShopHoursVo shopHoursVo;
		//获取店铺营业时间
		List<TcsbShopHoursEntity> tcsbShopHours = tcsbShopService.findByProperty(TcsbShopHoursEntity.class, "shopId", shopId);
		if(tcsbShopHours.size()>0){
			for (TcsbShopHoursEntity tcsbShopHoursEntity : tcsbShopHours) {
				shopHoursVo = new ShopHoursVo();
				
				//获取营业时间断名称
				TcsbShopHoursPeriodEntity tcsbShopHoursPeriod = tcsbShopService.get(TcsbShopHoursPeriodEntity.class, tcsbShopHoursEntity.getShopHoursPeriodId());
				
				shopHoursVo.setName(tcsbShopHoursPeriod.getName());
				String hoursString = DateUtils.short_time_sdf.format(tcsbShopHoursEntity.getBeginTime())+"-"+DateUtils.short_time_sdf.format(tcsbShopHoursEntity.getEndTime());
				shopHoursVo.setBusinessHours(hoursString);
				List<Object> hours = new ArrayList<>();
				Calendar calDes = DateUtils.parseCalendar(tcsbShopHoursEntity.getBeginTime().toString(),"yyyy-MM-dd HH:MM:SS");
				Calendar calDes2 = DateUtils.parseCalendar(tcsbShopHoursEntity.getEndTime().toString(),"yyyy-MM-dd HH:MM:SS");
				// System.out.println("时间结束:"+DateUtils.datetimeFormat.format(calDes.getTime()));
				 hours.add(DateUtils.short_time_sdf.format(calDes.getTime()));
				 for(int i=0;i<100;i++){
					Calendar calendar = new GregorianCalendar(); 
				    calendar.setTime(calDes.getTime()); 
				    calendar.add(calendar.MINUTE,30);//把日期往后增加一天.整数往后推,负数往前移动 
				    //System.out.println(DateUtils.datetimeFormat.format(calendar.getTime()));
				   // System.out.println(DateUtils.dateDiff('m',calDes2,calendar));
				    if(DateUtils.dateDiff('m',calDes2,calendar)>0){
				    	hours.add(DateUtils.short_time_sdf.format(calendar.getTime()));
				    	calDes = calendar;
				    	continue;
				    }else{
				    //	System.out.println("时间结束:"+DateUtils.datetimeFormat.format(calDes2.getTime()));
				    	hours.add(DateUtils.short_time_sdf.format(calDes2.getTime()));
				    	break;
				    }
				}
				 shopHoursVo.setHours(hours);
				 shopHoursVoList.add(shopHoursVo);
			}
			ajaxJson.setMsg("请求成功");
			ajaxJson.setSuccess(true);

			ajaxJson.setObj(shopHoursVoList);
		}else{
			ajaxJson.setMsg("请求成功");
			ajaxJson.setSuccess(false);
			ajaxJson.setObj("无");
		}
		

		
		return ajaxJson;
		
	}
	
	/**
	 * 获取店铺评论信息列表
	 * @param shopId
	 * @return
	 */
	@RequestMapping("/getShopEvaluate")
	@ResponseBody
	public AjaxJsonApi getShopEvaluate(String shopId,String state){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		ShopEvaluateVo shopEvaluateVo = new ShopEvaluateVo();
		if(StringUtil.isEmpty(state) || state.equals("1")){
			List<TcsbShopEvaluateEntity> shopEvaluate = tcsbShopService.findByProperty(TcsbShopEvaluateEntity.class, "shopId", shopId);
			double alleva = 0.0;
			double personalleva = 0.0;
			List<EvaluateVo> evaluateVoList = new ArrayList<>();
			EvaluateVo evaluateVo;
			for (TcsbShopEvaluateEntity tcsbShopEvaluateEntity : shopEvaluate) {
				evaluateVo = new EvaluateVo();
				alleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDiningEnvironment(), alleva);
				alleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDishesEvaluation(), alleva);
				alleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getServingSpeed(), alleva);
				personalleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDiningEnvironment(), personalleva);
				personalleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDishesEvaluation(), personalleva);
				personalleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getServingSpeed(), personalleva);
				String personeva = BigDecimalUtil.divide(personalleva+"", "3", 1);
				evaluateVo.setContent(tcsbShopEvaluateEntity.getContent());
				evaluateVo.setEvaluationLevel(personeva);
				TcsbWeixinUserEntity wxuser = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", tcsbShopEvaluateEntity.getUserId());
				if(StringUtil.isNotEmpty(wxuser)){
					evaluateVo.setHeadImg(wxuser.getHeadimgurl());
					evaluateVo.setNickName(wxuser.getNickname());
				}else{
					evaluateVo.setHeadImg("https://ceshi.diandanme.xyz/shop/plug-in/teemlink/image/logo.png");
					evaluateVo.setNickName("");
				}
				evaluateVoList.add(evaluateVo);
			}
			if(shopEvaluate.size()>0){
				String avgeva = BigDecimalUtil.divide(alleva+"", shopEvaluate.size()+"", 1);
				shopEvaluateVo.setAlleva(avgeva);
			}else{
				shopEvaluateVo.setAlleva("5");
			}
			
			shopEvaluateVo.setEvaluate(evaluateVoList);
			ajaxJson.setMsg("获取成功");
			ajaxJson.setObj(shopEvaluateVo);
			ajaxJson.setSuccess(true);
		}else{
			double alleva = 0.0;
			List<TcsbShopEvaluateEntity> allshopEvaluate = tcsbShopService.findByProperty(TcsbShopEvaluateEntity.class, "shopId", shopId);
			if(allshopEvaluate.size()>0){
				for (TcsbShopEvaluateEntity tcsbShopEvaluateEntity : allshopEvaluate) {
					alleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDiningEnvironment(), alleva);
					alleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDishesEvaluation(), alleva);
					alleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getServingSpeed(), alleva);
				}
				String avgeva = BigDecimalUtil.divide(alleva+"", allshopEvaluate.size()+"", 1);
				shopEvaluateVo.setAlleva(avgeva);
			}else{
				shopEvaluateVo.setAlleva("5");
			}
			String hql = "from TcsbShopEvaluateEntity where evaluationLevel='"+state+"' and shopId='"+shopId+"'";
			List<TcsbShopEvaluateEntity> shopEvaluate = tcsbShopService.findByQueryString(hql);
			
			double personalleva = 0.0;
			List<EvaluateVo> evaluateVoList = new ArrayList<>();
			EvaluateVo evaluateVo;
			for (TcsbShopEvaluateEntity tcsbShopEvaluateEntity : shopEvaluate) {
				evaluateVo = new EvaluateVo();
				
				personalleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDiningEnvironment(), personalleva);
				personalleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getDishesEvaluation(), personalleva);
				personalleva = BigDecimalUtil.add(tcsbShopEvaluateEntity.getServingSpeed(), personalleva);
				String personeva = BigDecimalUtil.divide(personalleva+"", "3", 1);
				evaluateVo.setContent(tcsbShopEvaluateEntity.getContent());
				evaluateVo.setEvaluationLevel(personeva);
				TcsbWeixinUserEntity wxuser = tcsbShopService.findUniqueByProperty(TcsbWeixinUserEntity.class, "openid", tcsbShopEvaluateEntity.getUserId());
				if(StringUtil.isNotEmpty(wxuser)){
					evaluateVo.setHeadImg(wxuser.getHeadimgurl());
					evaluateVo.setNickName(wxuser.getNickname());
				}else{
					evaluateVo.setHeadImg("https://ceshi.diandanme.xyz/shop/plug-in/teemlink/image/logo.png");
					evaluateVo.setNickName("");
				}
				evaluateVoList.add(evaluateVo);
			}
			
			shopEvaluateVo.setEvaluate(evaluateVoList);
			ajaxJson.setMsg("获取成功");
			ajaxJson.setObj(shopEvaluateVo);
			ajaxJson.setSuccess(true);
		}
		
		return ajaxJson;
	}
	
	
	/*	
	String startTime="19:00:00";
	String newStartTime = "2013-05-03" + " " + startTime;// 字符串连接
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");// 格式化类型
	Date d2 = null;
	try {
		d2 = format2.parse(newStartTime);
	} catch (Exception e) {
		e.printStackTrace();
	}
	Time startTimeFmt = new Time(d2.getTime());
	System.out.println(startTimeFmt.getHours());*/
}
