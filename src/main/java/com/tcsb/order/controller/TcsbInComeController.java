package com.tcsb.order.controller;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.util.JSONPObject;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.beanvalidator.BeanValidators;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.exception.BusinessException;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.common.model.json.DataGrid;
import org.jeecgframework.core.constant.Globals;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ExceptionUtil;
import org.jeecgframework.core.util.JSONHelper;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.ResourceUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.entity.vo.NormalExcelConstants;
import org.jeecgframework.tag.core.easyui.TagUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriComponentsBuilder;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.desk.entity.TcsbDeskEntity;
import com.tcsb.desk.service.TcsbDeskServiceI;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.discountactivity.service.TcsbDiscountActivityServiceI;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.food.service.TcsbFoodServiceI;
import com.tcsb.foodtype.entity.TcsbFoodTypeEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.fullcuttemplate.service.TcsbFullcutTemplateServiceI;
import com.tcsb.order.VO.OrderStatisticVo;
import com.tcsb.order.VO.ShopMainPageOrderStatiticsVo;
import com.tcsb.order.VO.TcsbOrderItemPrintVO;
import com.tcsb.order.VO.TcsbOrderItemVO;
import com.tcsb.order.VO.TcsbOrderPrintVO;
import com.tcsb.order.VO.TcsbOrderVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.page.TcsbOrderPage;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.platformdiscount.service.TcsbPlatformDiscountServiceI;
import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.userorder.entity.TcsbUserOrderEntity;
import com.tcsb.userorder.service.TcsbUserOrderServiceI;
import com.weixin.weixinuser.entity.WeixinUserEntity;

/**
 * 
 * jeecgos
 *
 *	营业收入统计控制层
 * @author Mar_x
 * create on 2017年5月19日 下午2:42:44
 */
@Controller
@RequestMapping("/tcsbInComeController")
public class TcsbInComeController extends BaseController {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(TcsbOrderController.class);

	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private Validator validator;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	@Autowired
	private TcsbPlatformDiscountServiceI tcsbPlatformDiscountService;
	@Autowired
	private TcsbDiscountActivityServiceI tcsbDiscountActivityService;
	@Autowired
	private TcsbFullcutTemplateServiceI tcsbFullcutTemplateService;
	@Autowired
	private TcsbFoodServiceI tcsbFoodService;
	@Autowired
	private TcsbDeskServiceI tcsbDeskService;
	@Autowired
	private TcsbUserOrderServiceI tcsbUserOrderService;
	
	
	/**
	 * 营业收入列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "income")
	public ModelAndView incomt(HttpServletRequest request,String shopId) {
		request.setAttribute("shopId", shopId);
		return new ModelAndView("com/tcsb/incomestatistics/tcsbIncomeStatisticsList");
	}
	
	
	/**
	 * 系统后台营业收入列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "adminincomt")
	public ModelAndView adminincomt(HttpServletRequest request) {
		
		List<TcsbShopEntity> TcsbShopEntityList = tcsbShopService.findByQueryString("from TcsbShopEntity");
		String foodTypeReplace = "";
		for (TcsbShopEntity tcsbShopEntity : TcsbShopEntityList) {
			if (foodTypeReplace.length() > 0) {
				foodTypeReplace += ",";
			}
			foodTypeReplace += tcsbShopEntity.getName() + "_"
					+ tcsbShopEntity.getId();
		}
		request.setAttribute("foodTypeReplace", foodTypeReplace);
		
		
		return new ModelAndView("com/tcsb/incomestatistics/tcsbIncomeStatisticsPlaList");
	}
	

	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws ParseException 
	 */

	@RequestMapping(params = "incomedatagrid")
	public void incomedatagrid(OrderStatisticVo tcsbOrder,String shopId,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) throws IllegalAccessException, InvocationTargetException, ParseException {
		TcsbShopEntity tcsbShopEntity = null;
		if(!StringUtil.isNotEmpty(shopId)){
			TSUser tsUser = getCurrentUser();
			shopId = tsUser.getShopId();
		}
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		if(StringUtil.isNotEmpty(endTime)){
			Calendar calendar2 = new GregorianCalendar(); 
		    calendar2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)); 
		    calendar2.add(calendar2.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
		    endTime = DateUtils.date_sdf.format(calendar2.getTime());
		}
		//总支付
		String sql = "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) as create_time,"
				+"sum( total_price ) as total_price,"//1
				+"sum( online_price ) as online_price,"//2
				+"sum( offline_price ) as offline_price,"//3
				+"sum(universal_coupon_price) as universal,"//4
				+"sum(special_coupon_price) as special,"//5
				+"sum(platform_discount_price) as platform"//6
				+ " FROM tcsb_order where pay_status = 1 and shop_id='"+shopId+"' and create_time BETWEEN '"+startTime
				+"' AND '"+endTime
				+"' GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' ) order by create_time desc";
		List<Object> ovoList1 = tcsbOrderService.findListbySql(sql);

		OrderStatisticVo ovo;
		List<OrderStatisticVo> ovoDto1 = new ArrayList<>();
	     for(Iterator iterator = ovoList1.iterator();iterator.hasNext();){ 
	    	 ovo = new OrderStatisticVo();
	            Object[] objects = (Object[]) iterator.next(); 
	            ovo.setId("1");
	            ovo.setCreatedate(objects[0].toString());//订单时间
	           
	            
	            ovo.setTotalprice(BigDecimalUtil.numericRetentionDecimal(objects[1].toString(),2));//营业额
	            
	            //线上营收（线上实际支付金额+平台优惠+商家活动+商家优惠券）
	            double a = BigDecimalUtil.add(Double.valueOf(objects[2].toString()), Double.valueOf(objects[4].toString()));
	            double b = BigDecimalUtil.add(Double.valueOf(objects[5].toString()), Double.valueOf(objects[6].toString()));
	            double onlineIncome = BigDecimalUtil.add(a, b);
	            ovo.setOnlineIncome(BigDecimalUtil.numericRetentionDecimal(onlineIncome, 2));
	            
	            //线上实际收款
	            ovo.setOnlinePayment(objects[2].toString());
	           
	            //线下实际收款
	            ovo.setOfflinePayment(BigDecimalUtil.numericRetentionDecimal(objects[3].toString(),2));
	            
	            double disprice = BigDecimalUtil.add(Double.valueOf(objects[2].toString()), Double.valueOf(objects[3].toString()));
	            ovo.setDisprice(BigDecimalUtil.numericRetentionDecimal(disprice, 2));//实际总收款
	            
	            
	            //店铺活动
	            ovo.setUniversalcouponprice(objects[4].toString());
	            //优惠券
	            ovo.setSpecialcouponprice(objects[5].toString());
	            //平台优惠
	            ovo.setPlatformdiscountprice(objects[6].toString());
	            
	            //平台需结算
	            double platformSettlements = BigDecimalUtil.add(Double.valueOf(objects[2].toString()), Double.valueOf(objects[6].toString()));
	            ovo.setPlatformSettlement(BigDecimalUtil.numericRetentionDecimal(platformSettlements, 2));

	            //根据当天的订单时间获取当天的线下支付方式统计额-----START
	            CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class);
	            cq.eq("payStatus", "1");
	            cq.eq("payMethod", "1");
	            cq.eq("shopId", shopId);
	            cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(objects[0].toString()));
	            
	            Calendar calendar = new GregorianCalendar(); 
			     calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(objects[0].toString())); 
			     calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
	            
	        	cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar.getTime())));
	            cq.add();
	            List<TcsbOrderEntity> tcsbOrderList = tcsbOrderService.getListByCriteriaQuery(cq,false);
	            double offlinepaybycard = 0.00;
	            double offlinepaybywechat = 0.00;
	            double offlinepaybyaply = 0.00;
	            double offlinepaybycash = 0.00;
	            double offlinepaybycredit = 0.00;
	            double offlinepaybyteam = 0.00;
	            
	            //线下营业额
	            double offlineincomeprice = 0.00;
	            //线下折扣金额
	            double offlinediscountprice = 0.00;
	            //抹零
	            double offlineDiscount = 0.00;
	            
	            for (TcsbOrderEntity tcsbOrderEntity : tcsbOrderList) {
	            	
	            	
	            	if(tcsbOrderEntity.getPayMethod().equals("1")){
	            		if(StringUtil.isNotEmpty(tcsbOrderEntity.getOfflineDiscount())){
	            			String realtotal = BigDecimalUtil.divide(tcsbOrderEntity.getTotalPrice()+"", tcsbOrderEntity.getOfflineDiscount(), 2);
	            			double subrealtotal = BigDecimalUtil.sub(Double.valueOf(realtotal), tcsbOrderEntity.getTotalPrice());
	            			offlinediscountprice = BigDecimalUtil.add(offlinediscountprice, subrealtotal);
	            			offlineincomeprice = BigDecimalUtil.add(offlineincomeprice,Double.valueOf(realtotal));
	            		}else{
	            			offlineincomeprice = BigDecimalUtil.add(offlineincomeprice, tcsbOrderEntity.getTotalPrice());
	            		}
	            		
	            		double thisdiscount = BigDecimalUtil.sub(tcsbOrderEntity.getTotalPrice(), tcsbOrderEntity.getOfflinePrice());
	            		offlineDiscount = BigDecimalUtil.add(offlineDiscount, thisdiscount);
	            	}
	            	if (tcsbOrderEntity.getOfflinePayMethod()!=null) {
	            		//刷卡
	            		if(tcsbOrderEntity.getOfflinePayMethod().equals("1")){
	            			offlinepaybycard = BigDecimalUtil.add(offlinepaybycard, tcsbOrderEntity.getOfflinePrice());
	            		}
	            		//线下微信
	            		if(tcsbOrderEntity.getOfflinePayMethod().equals("2")){
	            			offlinepaybywechat = BigDecimalUtil.add(offlinepaybywechat, tcsbOrderEntity.getOfflinePrice());
	            		}
	            		//线下支付宝
	            		if(tcsbOrderEntity.getOfflinePayMethod().equals("3")){
	            			offlinepaybyaply = BigDecimalUtil.add(offlinepaybyaply, tcsbOrderEntity.getOfflinePrice());
	            		}
	            		//现金
	            		if(tcsbOrderEntity.getOfflinePayMethod().equals("4")){
	            			offlinepaybycash = BigDecimalUtil.add(offlinepaybycash, tcsbOrderEntity.getOfflinePrice());
	            		}
	            		//赊账
	            		if(tcsbOrderEntity.getOfflinePayMethod().equals("5")){
	            			offlinepaybycredit = BigDecimalUtil.add(offlinepaybycredit, tcsbOrderEntity.getOfflinePrice());
	            		}
	            		//赊账
	            		if(tcsbOrderEntity.getOfflinePayMethod().equals("6")){
	            			offlinepaybyteam = BigDecimalUtil.add(offlinepaybyteam, tcsbOrderEntity.getOfflinePrice());
	            		}
					}
				}
	            ovo.setOfflinepaybycard(BigDecimalUtil.numericRetentionDecimal(offlinepaybycard, 2));
	            ovo.setOfflinepaybywechat(BigDecimalUtil.numericRetentionDecimal(offlinepaybywechat, 2));
	            ovo.setOfflinepaybyaply(BigDecimalUtil.numericRetentionDecimal(offlinepaybyaply, 2));
	            ovo.setOfflinepaybycash(BigDecimalUtil.numericRetentionDecimal(offlinepaybycash, 2));
	            ovo.setOfflinepaybycredit(BigDecimalUtil.numericRetentionDecimal(offlinepaybycredit, 2));
	            ovo.setOfflinepaybyteam(BigDecimalUtil.numericRetentionDecimal(offlinepaybyteam, 2));
	            //根据当天的订单时间获取当天的线下支付方式统计额-----END
	            
	            //double c = BigDecimalUtil.add(onlineIncome, Double.valueOf(objects[3].toString()));
	            //double offlineDiscount = BigDecimalUtil.sub(Double.valueOf(objects[1].toString()), c);
	            //设置抹零金额
	            ovo.setOfflineDiscount(BigDecimalUtil.numericRetentionDecimal(offlineDiscount, 2));
	           
	            //double offlineIncome = BigDecimalUtil.sub(Double.valueOf(objects[1].toString()), onlineIncome);
	            //线下营收（总额-线上营收）
	            ovo.setOfflineIncome(BigDecimalUtil.numericRetentionDecimal(offlineincomeprice, 2));
	            //线下折扣金额
	            ovo.setOfflinediscountprice(BigDecimalUtil.numericRetentionDecimal(offlinediscountprice, 2));
	            
	            double realtotalprice = BigDecimalUtil.add(Double.valueOf(ovo.getTotalprice()), offlinediscountprice);
	            ovo.setTotalprice(BigDecimalUtil.numericRetentionDecimal(realtotalprice, 2));
	            
	            ovoDto1.add(ovo);
	        } 
	     
	     
	     double totalprice = 0.00;
	     double onlinePayment = 0.00;
	     double offlinePayment = 0.00;
	     double disPrice1 = 0.00;
	     double disPrice2 = 0.00;
	     double disPrice3 = 0.00;
	     double disprice = 0.00;
	     double platformSettlement = 0.00;
	     double offlineDiscount = 0.00;
	     double offlineIncome = 0.00;
	     double onlineIncome = 0.00;
	     double offlinepaybycard = 0.00;
         double offlinepaybywechat = 0.00;
         double offlinepaybyaply = 0.00;
         double offlinepaybycash = 0.00;
         double offlinepaybycredit = 0.00;
         double offlinepaybyteam = 0.00;
         double offlinediscountprice = 0.00;
	     for (OrderStatisticVo orderStatisticVo : ovoDto1) {
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinediscountprice())){
	    		 offlinediscountprice = BigDecimalUtil.add(offlinediscountprice, 0);
	    	 }else{
	    		 offlinediscountprice = BigDecimalUtil.add(offlinediscountprice, Double.valueOf(orderStatisticVo.getOfflinediscountprice()));
	    	 }
	    	 
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getTotalprice())){
	    		 totalprice = BigDecimalUtil.add(totalprice, 0);
	    	 }else{
	    		 totalprice = BigDecimalUtil.add(totalprice, Double.valueOf(orderStatisticVo.getTotalprice()));
	    	 }
	    	
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOnlinePayment())){
	    		 onlinePayment = BigDecimalUtil.add(onlinePayment, 0);
	    
	    	 }else{
	    		 onlinePayment = BigDecimalUtil.add(onlinePayment, Double.valueOf(orderStatisticVo.getOnlinePayment()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinePayment())){
	    		 offlinePayment = BigDecimalUtil.add(offlinePayment, 0);
	    		
	    	 }else{
	    		 offlinePayment = BigDecimalUtil.add(offlinePayment, Double.valueOf(orderStatisticVo.getOfflinePayment()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getDisprice())){
	    		 disprice = BigDecimalUtil.add(disprice, 0);
	    	
	    	 }else{
	    		 disprice = BigDecimalUtil.add(disprice, Double.valueOf(orderStatisticVo.getDisprice()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getPlatformSettlement())){
	    		 platformSettlement = BigDecimalUtil.add(platformSettlement, 0);
	    	 }else{
	    		 platformSettlement = BigDecimalUtil.add(platformSettlement, Double.valueOf(orderStatisticVo.getPlatformSettlement()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getUniversalcouponprice())){
	    		 disPrice1 = BigDecimalUtil.add(disPrice1, 0);
	    	
	    	 }else{
	    		 disPrice1 = BigDecimalUtil.add(disPrice1, Double.valueOf(orderStatisticVo.getUniversalcouponprice()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getSpecialcouponprice())){
	    		 disPrice2 = BigDecimalUtil.add(disPrice2, 0);
	    	 }else{
	    		 disPrice2 = BigDecimalUtil.add(disPrice2, Double.valueOf(orderStatisticVo.getSpecialcouponprice()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getPlatformdiscountprice())){
	    		 disPrice3 = BigDecimalUtil.add(disPrice3, 0);
	    	 }else{
	    		 disPrice3 = BigDecimalUtil.add(disPrice3, Double.valueOf(orderStatisticVo.getPlatformdiscountprice()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflineDiscount())){
	    		 offlineDiscount = BigDecimalUtil.add(offlineDiscount, 0);
	    	 }else{
	    		 offlineDiscount = BigDecimalUtil.add(offlineDiscount, Double.valueOf(orderStatisticVo.getOfflineDiscount()));
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflineIncome())){
	    		 offlineIncome = BigDecimalUtil.add(offlineIncome, 0);
	    	 }else{
	    		 offlineIncome = BigDecimalUtil.add(offlineIncome, Double.valueOf(orderStatisticVo.getOfflineIncome()));
	    	 }
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOnlineIncome())){
	    		 onlineIncome = BigDecimalUtil.add(onlineIncome, 0);
	    	 }else{
	    		 onlineIncome = BigDecimalUtil.add(onlineIncome, Double.valueOf(orderStatisticVo.getOnlineIncome()));
	    	 }
	    	 //1
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinepaybycard())){
	    		 offlinepaybycard = BigDecimalUtil.add(offlinepaybycard, 0);
	    	 }else{
	    		 offlinepaybycard = BigDecimalUtil.add(offlinepaybycard, Double.valueOf(orderStatisticVo.getOfflinepaybycard()));
	    	 }
	    	 //2
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinepaybywechat())){
	    		 offlinepaybywechat = BigDecimalUtil.add(offlinepaybywechat, 0);
	    	 }else{
	    		 offlinepaybywechat = BigDecimalUtil.add(offlinepaybywechat, Double.valueOf(orderStatisticVo.getOfflinepaybywechat()));
	    	 }
	    	 //3
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinepaybyaply())){
	    		 offlinepaybyaply += 0;  
	    	 }else{
	    		 offlinepaybyaply = BigDecimalUtil.add(offlinepaybyaply, Double.valueOf(orderStatisticVo.getOfflinepaybyaply()));
	    	 }
	    	 //4
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinepaybycash())){
	    		 offlinepaybycash = BigDecimalUtil.add(offlinepaybycash, 0);
	    	 }else{
	    		 offlinepaybycash = BigDecimalUtil.add(offlinepaybycash, Double.valueOf(orderStatisticVo.getOfflinepaybycash()));
	    	 }
	    	 //5
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinepaybycredit())){
	    		 offlinepaybycredit = BigDecimalUtil.add(offlinepaybycredit, 0);
	    	 }else{
	    		 offlinepaybycredit = BigDecimalUtil.add(offlinepaybycredit, Double.valueOf(orderStatisticVo.getOfflinepaybycredit()));
	    	 }
	    	 //6
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinepaybyteam())){
	    		 offlinepaybyteam = BigDecimalUtil.add(offlinepaybyteam, 0);
	    	 }else{
	    		 offlinepaybyteam = BigDecimalUtil.add(offlinepaybyteam, Double.valueOf(orderStatisticVo.getOfflinepaybycredit()));
	    	 }
 
		}
	    // offlinediscountprice
	     
//	     dataGrid.setFooter("id:<font color ='black'>统计</font>,"+
//	    		 "totalprice:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(totalprice, 2)+"</font>,"+
//	    		 "onlineIncome:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(onlineIncome, 2)+"</font>,"+
//	    		 "onlinePayment:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(onlinePayment, 2)+"</font>,"+
//	    		 "offlinePayment:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinePayment, 2)+"</font>,"+
//	    		 "offlinediscountprice:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinediscountprice, 2)+"</font>,"+
//	    		 "offlineDiscount:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlineDiscount, 2)+"</font>,"+
//	    		 "universalcouponprice:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(disPrice1, 2)+"</font>,"+
//	    		 "specialcouponprice:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(disPrice2, 2)+"</font>,"+
//	    		 "platformdiscountprice:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(disPrice3, 2)+"</font>,"+
//	    		 "offlineIncome:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlineIncome, 2)+"</font>,"+
//					"offlinepaybycard:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinepaybycard, 2)+"</font>,"+
//					"offlinepaybywechat:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinepaybywechat, 2)+"</font>,"+
//					"offlinepaybyaply:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinepaybyaply, 2)+"</font>,"+
//					"offlinepaybycash:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinepaybycash, 2)+"</font>,"+
//					"offlinepaybycredit:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinepaybycredit, 2)+"</font>,"+
//					"offlinepaybyteam:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(offlinepaybyteam, 2)+"</font>,"+
//	    		 "disprice:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(disprice, 2)+"</font>,"+
//	    		 "platformSettlement:<font color ='red'>"+BigDecimalUtil.numericRetentionDecimal(platformSettlement, 2)+"</font>");
			dataGrid.setTotal(ovoDto1.size());
			dataGrid.setResults(ovoDto1);
			TagUtil.datagrid(response, dataGrid); 
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/**
	 * 订单管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "list")
	public ModelAndView list(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/order/tcsbOrderList");
	}

	/**
	 * 订单管理列表 页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "listAccept")
	public ModelAndView listAccept(HttpServletRequest request) {
		return new ModelAndView("com/tcsb/order/tcsbOrderAcceptList");
	}
	
	
	@RequestMapping(params = "incomedetaillist")
	public ModelAndView incomedetaillist(String createTime_begin,String createTime_end,HttpServletRequest request,ModelMap modelMap) {
		modelMap.addAttribute("createTime_begin", createTime_begin);
		modelMap.addAttribute("createTime_end", createTime_end);
		return new ModelAndView("com/tcsb/incomestatistics/tcsbOrderList");
	}
	
	
	@RequestMapping(params = "incomedetaildatagrid")
	public void incomedetaildatagrid(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) throws IllegalAccessException, InvocationTargetException {
		tcsbOrder.setDeskId(null);
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
		try{
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			cq.eq("shopId", user.getShopId());
			cq.eq("payStatus", "1");
		}
		//自定义追加查询条件
		String query_createTime_begin = request.getParameter("createTime_begin");
		String query_createTime_end = request.getParameter("createTime_end");
		if(StringUtil.isNotEmpty(query_createTime_begin)){
			cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
		}
		if(StringUtil.isNotEmpty(query_createTime_end)){
			cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
		}
		
		
		String deskId = request.getParameter("deskId");
		if(StringUtil.isNotEmpty(deskId)){
			CriteriaQuery deskcq = new CriteriaQuery(TcsbDeskEntity.class);
			deskcq.add(Restrictions.like("deskName", "%"+deskId+"%"));
			List<TcsbDeskEntity> deskList = tcsbShopService.getListByCriteriaQuery(deskcq, false);
			if(deskList.size()>0){
				
					Set set = new HashSet<>();
					for (TcsbDeskEntity object : deskList) {
						System.out.println(object.getNumber());
						set.add(object.getNumber());
					}
					cq.add(Restrictions.in("deskId", set));
			}
		}
		
		
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbOrderService.getDataGridReturn(cq, true);
		List<TcsbOrderEntity> TcsbOrderList = dataGrid.getResults();
		List<TcsbOrderEntity> TcsbOrderListVo = new ArrayList<>();
		TcsbOrderEntity TcsbOrder;
		for (TcsbOrderEntity tcsbOrderEntity : TcsbOrderList) {
			TcsbOrder = new TcsbOrderEntity();
			BeanUtils.copyProperties(TcsbOrder, tcsbOrderEntity);
			
			if(StringUtil.isNotEmpty(tcsbOrderEntity.getDeskId())){
				TcsbDeskEntity TcsbDes = tcsbOrderService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrderEntity.getDeskId());
				if(StringUtil.isNotEmpty(TcsbDes)){
					TcsbOrder.setDeskId(TcsbDes.getDeskName());
				}
			}
			TcsbOrderListVo.add(TcsbOrder);

		}
		
		dataGrid.setResults(TcsbOrderListVo);
		TagUtil.datagrid(response, dataGrid);
	}
	
	/**
	 * easyui AJAX请求数据
	 * 
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param user
	 */

	@RequestMapping(params = "datagrid")
	public void datagrid(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
		try{
		//自定义追加查询条件
		String query_createTime_begin = request.getParameter("createTime_begin");
		String query_createTime_end = request.getParameter("createTime_end");
		if(StringUtil.isNotEmpty(query_createTime_begin)){
			cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
		}
		if(StringUtil.isNotEmpty(query_createTime_end)){
			cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}
	
	@RequestMapping(params = "datagridAccept")
	public void datagridAccept(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid) {
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
		//查询条件组装器
		org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
		try{
		//自定义追加查询条件
			cq.eq("orderIstake", "N");	
		String query_createTime_begin = request.getParameter("createTime_begin");
		String query_createTime_end = request.getParameter("createTime_end");
		if(StringUtil.isNotEmpty(query_createTime_begin)){
			cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_begin));
		}
		if(StringUtil.isNotEmpty(query_createTime_end)){
			cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(query_createTime_end));
		}
		}catch (Exception e) {
			throw new BusinessException(e.getMessage());
		}
		cq.add();
		this.tcsbOrderService.getDataGridReturn(cq, true);
		TagUtil.datagrid(response, dataGrid);
	}


	
	@RequestMapping(params = "getshopName")
	@ResponseBody
	public List<TcsbShopEntity> getshopName(HttpServletRequest request,String keyword) {
		keyword = keyword.replace("'","");
		List<TcsbShopEntity> tcsbShopEntityList = tcsbShopService.findByQueryString("from TcsbShopEntity WHERE name like '%" + keyword + "%'");
		/*String foodTypeReplace = "";
		for (TcsbShopEntity tcsbShopEntity : TcsbShopEntityList) {
			if (foodTypeReplace.length() > 0) {
				foodTypeReplace += ",";
			}
			foodTypeReplace += tcsbShopEntity.getName() + "_"
					+ tcsbShopEntity.getId();
		}
		request.setAttribute("foodTypeReplace", foodTypeReplace);*/
		return tcsbShopEntityList;
		
		
		//return new ModelAndView("com/tcsb/incomestatistics/tcsbIncomeStatisticsPlaList");
	}
	
	
	
	
	
	/**
	 * 
	 * @param tcsbOrder
	 * @param request
	 * @param response
	 * @param dataGrid
	 * @param session
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws ParseException
	 */
	@RequestMapping(params = "adminincomedatagrid")
	public void adminincomedatagrid(OrderStatisticVo tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,HttpSession session) throws IllegalAccessException, InvocationTargetException, ParseException {
		String shopName = request.getParameter("foodTypeId");
		
		TcsbShopEntity tcsbShop = tcsbShopService.singleResult("from TcsbShopEntity WHERE name='" + shopName + "'");
		String tcsbShopid = "";
		if(StringUtil.isNotEmpty(tcsbShop)){
			tcsbShopid = tcsbShop.getId();
		}
		String startTime = request.getParameter("searchTime_begin");
		String endTime = request.getParameter("searchTime_end");
		if(StringUtil.isNotEmpty(endTime)){
			Calendar calendar2 = new GregorianCalendar(); 
		    calendar2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(endTime)); 
		    calendar2.add(calendar2.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
		    endTime = DateUtils.date_sdf.format(calendar2.getTime());
		}
		//cq.add(Restrictions.ge("createTime",new SimpleDateFormat("yyyy-MM-dd").parse(data)));
		//cq.add(Restrictions.lt("createTime",new SimpleDateFormat("yyyy-MM-dd").parse( DateUtils.date_sdf.format(calendar2.getTime()))));
		//TODO
		//总支付
		String sql = "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) as create_time , sum( total_price ) as total_price,"
				+"sum( online_price ) as online_price,sum( offline_price ) as offline_price,sum(universal_coupon_price) as universal,sum(special_coupon_price) as special,"
				+"sum(platform_discount_price) as platform"
				+ " FROM tcsb_order where pay_status = 1 and shop_id='"+tcsbShopid+"' and create_time BETWEEN '"+startTime
				+"' AND '"+endTime
				+"' GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' ) ";
		List<Object> ovoList1 = tcsbOrderService.findListbySql(sql);

		OrderStatisticVo ovo;
		List<OrderStatisticVo> ovoDto1 = new ArrayList<>();
	     for(Iterator iterator = ovoList1.iterator();iterator.hasNext();){ 
	    	 ovo = new OrderStatisticVo();
	            Object[] objects = (Object[]) iterator.next(); 
	            ovo.setCreatedate(objects[0].toString());//订单时间
	            ovo.setTotalprice(objects[1].toString());//营业额
	            double disprice = Double.valueOf(objects[2].toString())+Double.valueOf(objects[3].toString());
	            ovo.setDisprice(disprice + "");//实际收款
	            ovo.setOnlinePayment(objects[2].toString());
	            ovo.setOfflinePayment(objects[3].toString());
	            ovo.setUniversalcouponprice(objects[4].toString());
	            ovo.setSpecialcouponprice(objects[5].toString());
	            ovo.setPlatformdiscountprice(objects[6].toString());//平台优惠
	            double platformSettlements = Double.valueOf(objects[2].toString())+Double.valueOf(objects[6].toString());
	            ovo.setPlatformSettlement(platformSettlements +"");
	            ovoDto1.add(ovo);
	        } 
	     
	     
	     double totalprice = 0.00;
	     double onlinePayment = 0.00;
	     double offlinePayment = 0.00;
	     double disPrice1 = 0.00;
	     double disPrice2 = 0.00;
	     double disPrice3 = 0.00;
	     double disprice = 0.00;
	     double platformSettlement = 0.00;
	     
	     for (OrderStatisticVo orderStatisticVo : ovoDto1) {
	    	 	totalprice += Double.valueOf(orderStatisticVo.getTotalprice()); 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOnlinePayment())){
	    		 onlinePayment += 0;  
	    	 }else{
	    		 onlinePayment += Double.valueOf(orderStatisticVo.getOnlinePayment());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getOfflinePayment())){
	    		 offlinePayment += 0;  
	    	 }else{
	    		 offlinePayment += Double.valueOf(orderStatisticVo.getOfflinePayment());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getDisprice())){
	    		 disprice += 0;  
	    	 }else{
	    		 disprice += Double.valueOf(orderStatisticVo.getDisprice());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getPlatformSettlement())){
	    		 platformSettlement += 0;  
	    	 }else{
	    		 platformSettlement += Double.valueOf(orderStatisticVo.getPlatformSettlement());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getUniversalcouponprice())){
	    		 disPrice1 += 0;  
	    	 }else{
	    		 disPrice1 += Double.valueOf(orderStatisticVo.getUniversalcouponprice());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getSpecialcouponprice())){
	    		 disPrice2 += 0;  
	    	 }else{
	    		 disPrice2 += Double.valueOf(orderStatisticVo.getSpecialcouponprice());  
	    	 }
	    	 
	    	 if(StringUtil.isEmpty(orderStatisticVo.getPlatformdiscountprice())){
	    		 disPrice3 += 0;  
	    	 }else{
	    		 disPrice3 += Double.valueOf(orderStatisticVo.getPlatformdiscountprice());  
	    	 }
	    	 
		}
	   //获取店铺会员数
	     String usercounthql = "from WeixinUserEntity where shopId='"+tcsbShopid+"' and isSale=1" ;
	     List<WeixinUserEntity> WeixinUserEntityCount = tcsbOrderService.findByQueryString(usercounthql);
	     System.out.println(WeixinUserEntityCount.size());
	     dataGrid.setFooter("id:<font color ='black'>统计  会员数：<font color ='red'>"+WeixinUserEntityCount.size()+"</font>,"+
	    		 "totalprice:<font color ='red'>"+totalprice+"</font>,"+
	    		 "onlinePayment:<font color ='red'>"+onlinePayment+"</font>,"+
	    		 "offlinePayment:<font color ='red'>"+offlinePayment+"</font>,"+
	    		 "universalcouponprice:<font color ='red'>"+disPrice1+"</font>,"+
	    		 "specialcouponprice:<font color ='red'>"+disPrice2+"</font>,"+
	    		 "platformdiscountprice:<font color ='red'>"+disPrice3+"</font>,"+
	    		 "disprice:<font color ='red'>"+disprice+"</font>,"+
	    		 "platformSettlement:<font color ='red'>"+platformSettlement+"</font>");
			dataGrid.setTotal(ovoDto1.size());
			dataGrid.setResults(ovoDto1);
			TagUtil.datagrid(response, dataGrid); 
	}
	
	
	
	/**
	 * 
	 * @param session
	 * @throws ParseException
	 */
	@RequestMapping(params = "getInComeStatistic")
	@ResponseBody
	public ShopMainPageOrderStatiticsVo getInComeStatistic(HttpSession session) throws ParseException {
		// TODO
		// 获取登入用户信息
		TSUser user = null;
		if (session.getAttribute(ResourceUtil.LOCAL_CLINET_USER) != null) {
			user = (TSUser) session.getAttribute(ResourceUtil.LOCAL_CLINET_USER);
		}
		// 获取用户店铺信息
		TcsbShopEntity tcsbShopEntity = tcsbShopService.getTcsbShopEntityByUserid(user.getId());

		// 获取店铺未消费的用户
		CriteriaQuery cq3 = new CriteriaQuery(WeixinUserEntity.class);
		cq3.add(Restrictions.eq("isSale", "0"));
		cq3.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		//cq3.add(Restrictions.isNotNull("mobile"));
		List<WeixinUserEntity> weixinUserEntityList = tcsbShopService.getListByCriteriaQuery(cq3, false);
		// 检测用户是否有消费
		for (WeixinUserEntity weixinUserEntity : weixinUserEntityList) {
			int point = 0;
			// 根据用户openid获取用户订单
			String userorderhql = "from TcsbUserOrderEntity where userId='" + weixinUserEntity.getOpenid() + "'";
			List<TcsbUserOrderEntity> TcsbUserOrderList = tcsbShopService.findByQueryString(userorderhql);
			// 检查该orderid是否是付款
			for (TcsbUserOrderEntity tcsbUserOrderEntity : TcsbUserOrderList) {
				String orderHql = "from TcsbOrderEntity where id='" + tcsbUserOrderEntity.getOrderId()
						+ "' and shopId='" + tcsbShopEntity.getId() + "'";
				TcsbOrderEntity TcsbOrder = tcsbShopService.singleResult(orderHql);
				if(StringUtil.isNotEmpty(TcsbOrder)){
					if (TcsbOrder.getPayStatus().equals("1")) {
						point = 1;
						break;
					}
				}
			}
			if (point == 1) {
				// 用户有消费过
				weixinUserEntity.setIsSale("1");
				tcsbShopService.saveOrUpdate(weixinUserEntity);
			}

		}

		// 获取今日营业额
		CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class);
		cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		cq.add(Restrictions.eq("payStatus", "1"));
		String data = DateUtils.date2Str(new Date(), DateUtils.date_sdf);
		Calendar calendar2 = new GregorianCalendar();
		calendar2.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(data));
		calendar2.add(calendar2.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		cq.add(Restrictions.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(data)));
		cq.add(Restrictions.lt("createTime",
				new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.date_sdf.format(calendar2.getTime()))));
		List<TcsbOrderEntity> TcsbOrderEntityList = tcsbShopService.getListByCriteriaQuery(cq, true);
		double todayIncome = 0.00;
		for (TcsbOrderEntity tcsbOrderEntity : TcsbOrderEntityList) {
			todayIncome += tcsbOrderEntity.getTotalPrice();
		}
		// 获取总营业额 线上 线下
		String allhql = "from TcsbOrderEntity where payStatus=1 and shopId='"+tcsbShopEntity.getId()+"'";
		List<TcsbOrderEntity> TcsbOrderEntity = tcsbShopService.findByQueryString(allhql);
		double totalIncme = 0.00;// 总营业额
		double onlineIncome = 0.00;
		double offlineIncome = 0.00;
		for (TcsbOrderEntity tcsbOrderEntity2 : TcsbOrderEntity) {
			totalIncme += tcsbOrderEntity2.getTotalPrice();
			onlineIncome += tcsbOrderEntity2.getOnlinePrice();
			offlineIncome += tcsbOrderEntity2.getOfflinePrice();
		}
		//获取日均营业额
		double avgDataIncome = 0.00;
		String sql = "SELECT DATE_FORMAT( create_time, '%Y-%m-%d' ) as create_time , sum( total_price ) as total_price"
				+ " FROM tcsb_order where pay_status = 1 and shop_id='"+tcsbShopEntity.getId()+"'"
				+" GROUP BY DATE_FORMAT( create_time, '%Y-%m-%d' ) ";
		List<Object> ovoList1 = tcsbOrderService.findListbySql(sql);
		if(ovoList1.size()>0){
			avgDataIncome = totalIncme / ovoList1.size();
		}else{
			avgDataIncome = totalIncme / 1;
		}
		// 获取月均营业额
		double monthTotalIncme = 0.00;
		double monthAvgIncme = 0.00;
		int month = 0;
		String monthssql = "select DATE_FORMAT(create_time,'%Y%m') as months,sum(total_price) from tcsb_order where shop_id='"
				+ tcsbShopEntity.getId() + "' and pay_status=1 group by months";
		List<Object> object = tcsbShopService.findListbySql(monthssql);
		for (Iterator iterator = object.iterator(); iterator.hasNext();) {
			Object[] object2 = (Object[]) iterator.next();
			month++;
			monthTotalIncme += Double.valueOf(object2[1].toString());
		}
		if(month == 0){
			monthAvgIncme = 0;
		}else{
			monthAvgIncme = monthTotalIncme / month;
		}
		// 获取今日新争会员数
		CriteriaQuery cq2 = new CriteriaQuery(WeixinUserEntity.class);
		cq2.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		cq2.add(Restrictions.eq("isSale", "1"));
		String data2 = DateUtils.date2Str(new Date(), DateUtils.date_sdf);
		Calendar calendar3 = new GregorianCalendar();
		calendar3.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(data));
		calendar3.add(calendar3.DATE, 1);// 把日期往后增加一天.整数往后推,负数往前移动
		cq2.add(Restrictions.ge("createTime", new SimpleDateFormat("yyyy-MM-dd").parse(data)));
		cq2.add(Restrictions.lt("createTime",
				new SimpleDateFormat("yyyy-MM-dd").parse(DateUtils.date_sdf.format(calendar2.getTime()))));
		List<WeixinUserEntity> WeixinUserEntity = tcsbShopService.getListByCriteriaQuery(cq2, true);
		
		ShopMainPageOrderStatiticsVo shopMainPageOrderStatiticsVo = new ShopMainPageOrderStatiticsVo();
		shopMainPageOrderStatiticsVo.setTodayIncome(todayIncome);
		BigDecimal b = new BigDecimal(avgDataIncome);
		// 保留2位小数
		double f1 = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		shopMainPageOrderStatiticsVo.setAvgDataIncome(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(f1, 2)));
		shopMainPageOrderStatiticsVo.setMonthAvgIncme(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(monthAvgIncme, 2)));
		shopMainPageOrderStatiticsVo.setTotalIncme(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(totalIncme, 2)));
		shopMainPageOrderStatiticsVo.setOnlineIncome(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(onlineIncome, 2)));
		shopMainPageOrderStatiticsVo.setOfflineIncome(Double.valueOf(BigDecimalUtil.numericRetentionDecimal(offlineIncome, 2)));
		if(StringUtil.isNotEmpty(WeixinUserEntity)){
			shopMainPageOrderStatiticsVo.setTodayJoinVip(WeixinUserEntity.size());
		}else{
			shopMainPageOrderStatiticsVo.setTodayJoinVip(0);
		}

		return shopMainPageOrderStatiticsVo;
		
	}
	
	
	
	
	
	
	
	
	
	
	/**
	 * 删除订单管理
	 * 
	 * @return
	 */
	@RequestMapping(params = "doDel")
	@ResponseBody
	public AjaxJson doDel(TcsbOrderEntity tcsbOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		tcsbOrder = systemService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
		String message = "订单管理删除成功";
		try{
			tcsbOrderService.delMain(tcsbOrder);
			systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	
	
	/**
	 * 查询还没有接单的记录
	 * @param tcsbOrder
	 * @param request
	 * @return
	 */
	@RequestMapping(params = "getNotReadOrder")
	@ResponseBody
	public AjaxJson getNotReadOrder(TcsbOrderEntity tcsbOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "订单管理查询成功";
		List<TcsbOrderEntity> tcsbOrderEntities = new ArrayList<>();
		try{
			tcsbOrderEntities = tcsbOrderService.findByProperty(TcsbOrderEntity.class, "orderIstake", "N");
		}catch(Exception e){
			e.printStackTrace();
			message = "订单管理查询成功";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		j.setSuccess(true);
		j.setObj(tcsbOrderEntities.size());
		return j;
	}

	/**
	 * 批量删除订单管理
	 * 
	 * @return
	 */
	 @RequestMapping(params = "doBatchDel")
	@ResponseBody
	public AjaxJson doBatchDel(String ids,HttpServletRequest request){
		AjaxJson j = new AjaxJson();
		String message = "订单管理删除成功";
		try{
			for(String id:ids.split(",")){
				TcsbOrderEntity tcsbOrder = systemService.getEntity(TcsbOrderEntity.class,
				id
				);
				tcsbOrderService.delMain(tcsbOrder);
				systemService.addLog(message, Globals.Log_Type_DEL, Globals.Log_Leavel_INFO);
			}
		}catch(Exception e){
			e.printStackTrace();
			message = "订单管理删除失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}

	/**
	 * 添加订单管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doAdd")
	@ResponseBody
	public AjaxJson doAdd(TcsbOrderEntity tcsbOrder,TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		AjaxJson j = new AjaxJson();
		String message = "添加成功";
		try{
			tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);
			systemService.addLog(message, Globals.Log_Type_INSERT, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "订单管理添加失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	/**
	 * 更新订单管理
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "doUpdate")
	@ResponseBody
	public AjaxJson doUpdate(TcsbOrderEntity tcsbOrder,TcsbOrderPage tcsbOrderPage, HttpServletRequest request) {
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		AjaxJson j = new AjaxJson();
		String message = "更新成功";
		try{
			tcsbOrderService.updateMain(tcsbOrder, tcsbOrderItemList);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}
	
	/**
	 * 接单更改订单接收状态
	 * 
	 * @param ids
	 * @return
	 */
	@RequestMapping(params = "receiveOrder")
	@ResponseBody
	public AjaxJson receiveOrder(TcsbOrderEntity tcsbOrder, HttpServletRequest request) {
		AjaxJson j = new AjaxJson();
		String message = "恭喜你接单成功";
		try{
			if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
				tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			}
			tcsbOrder.setOrderIstake("Y");
			tcsbOrderService.saveOrUpdate(tcsbOrder);
			systemService.addLog(message, Globals.Log_Type_UPDATE, Globals.Log_Leavel_INFO);
		}catch(Exception e){
			e.printStackTrace();
			message = "更新订单管理失败";
			throw new BusinessException(e.getMessage());
		}
		j.setMsg(message);
		return j;
	}


	/**
	 * 订单管理新增页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goAdd")
	public ModelAndView goAdd(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
			tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			req.setAttribute("tcsbOrderPage", tcsbOrder);
		}
		//根据 shopId获取所有食物
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
			req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
		}
		return new ModelAndView("com/tcsb/order/tcsbOrder-add");
	}
	
	/**
	 * 订单管理编辑页面跳转
	 * 
	 * @return
	 */
	@RequestMapping(params = "goUpdate")
	public ModelAndView goUpdate(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
			tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			req.setAttribute("tcsbOrderPage", tcsbOrder);
		}
		//根据 shopId获取所有食物
		TSUser user = getCurrentUser();
		if (!checkAdmin()) {
			TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
			List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
			req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
		}
		return new ModelAndView("com/tcsb/order/tcsbOrder-update");
	}
	
	/**
	 * 订单打印预览跳转
	 * @param 
	 * @param req
	 * @return
	 */
	@RequestMapping(params = "print")
	public ModelAndView print(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
		if (StringUtil.isNotEmpty(tcsbOrder.getId())) {
			tcsbOrder = tcsbOrderService.getEntity(TcsbOrderEntity.class, tcsbOrder.getId());
			TcsbOrderPrintVO tcsbOrderPrintVO = new TcsbOrderPrintVO();
			if (tcsbOrder!=null) {
				tcsbOrderPrintVO.setCreateTime(tcsbOrder.getCreateTime());
				TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrder.getDeskId());
				tcsbOrderPrintVO.setDeskName(tcsbDeskEntity.getDeskName());
				//tcsbOrderPrintVO.setFinalMoney(tcsbOrder.getDisPrice());
				tcsbOrderPrintVO.setOrderNo(tcsbOrder.getOrderNo());
				tcsbOrderPrintVO.setPlatformDiscountPrice(tcsbOrder.getPlatformDiscountPrice());
				TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbOrder.getShopId());
				tcsbOrderPrintVO.setShopName(tcsbShopEntity.getName());
				tcsbOrderPrintVO.setSpecialCouponPrice(tcsbOrder.getSpecialCouponPrice());
				tcsbOrderPrintVO.setTotalMoney(tcsbOrder.getTotalPrice());
				tcsbOrderPrintVO.setUniversalCouponPrice(tcsbOrder.getUniversalCouponPrice());
				List<TcsbOrderItemPrintVO> tcsbOrderItemPrintVOs = new ArrayList<>();
				List<Map<String, Object>> orderItemMaps = systemService.findForJdbc("select f.name,i.count,f.price from tcsb_order_item i LEFT JOIN tcsb_order o on o.id = i.order_id LEFT JOIN tcsb_food f on f.id = i.food_id where i.order_id = ?", tcsbOrder.getId());
				for (Map<String, Object> map : orderItemMaps) {
					TcsbOrderItemPrintVO itemPrintVO = new TcsbOrderItemPrintVO();
					itemPrintVO.setCount((Integer)map.get("count"));
					itemPrintVO.setFoodName((String)map.get("name"));
					itemPrintVO.setPrice((Double)map.get("price"));
					tcsbOrderItemPrintVOs.add(itemPrintVO);
				}
				tcsbOrderPrintVO.setTcsbOrderItemPrintVOs(tcsbOrderItemPrintVOs);
			}
			req.setAttribute("tcsbOrderPage", tcsbOrderPrintVO);
		}
		return new ModelAndView("com/tcsb/order/tcsbOrderPrint");
	}
	
	/**
	 * 加载明细列表[订单项管理]
	 * 
	 * @return
	 */
	@RequestMapping(params = "tcsbOrderItemList")
	public ModelAndView tcsbOrderItemList(TcsbOrderEntity tcsbOrder, HttpServletRequest req) {
	
		//===================================================================================
		//获取参数
		Object id0 = tcsbOrder.getId();
		//===================================================================================
		//查询-订单项管理
	    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
	    try{
	    	List<TcsbOrderItemEntity> tcsbOrderItemEntityList = systemService.findHql(hql0,id0);
			req.setAttribute("tcsbOrderItemList", tcsbOrderItemEntityList);
			//根据 shopId获取所有食物
			TSUser user = getCurrentUser();
			if (!checkAdmin()) {
				TcsbShopEntity tcsbShopEntity = tcsbShopService.findUniqueByProperty(TcsbShopEntity.class, "userId", user.getId());
				List<TcsbFoodEntity> tcsbFoodEntities = tcsbFoodService.findHql("from TcsbFoodEntity where shopId = ?", tcsbShopEntity.getId());
				req.setAttribute("tcsbFoodEntities", tcsbFoodEntities);
			}
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		return new ModelAndView("com/tcsb/orderitem/tcsbOrderItemList");
	}

    /**
    * 导出excel
    *
    * @param request
    * @param response
    */
    @RequestMapping(params = "exportXls")
    public String exportXls(TcsbOrderEntity tcsbOrder,HttpServletRequest request, HttpServletResponse response, DataGrid dataGrid,ModelMap map) {
    	CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class, dataGrid);
    	//查询条件组装器
    	org.jeecgframework.core.extend.hqlsearch.HqlGenerateUtil.installHql(cq, tcsbOrder);
    	try{
    	//自定义追加查询条件
    	}catch (Exception e) {
    		throw new BusinessException(e.getMessage());
    	}
    	cq.add();
    	List<TcsbOrderEntity> list=this.tcsbOrderService.getListByCriteriaQuery(cq, false);
    	List<TcsbOrderPage> pageList=new ArrayList<TcsbOrderPage>();
        if(list!=null&&list.size()>0){
        	for(TcsbOrderEntity entity:list){
        		try{
        		TcsbOrderPage page=new TcsbOrderPage();
        		   MyBeanUtils.copyBeanNotNull2Bean(entity,page);
            	    Object id0 = entity.getId();
				    String hql0 = "from TcsbOrderItemEntity where 1 = 1 AND oRDER_ID = ? ";
        	        List<TcsbOrderItemEntity> tcsbOrderItemEntityList = systemService.findHql(hql0,id0);
            		page.setTcsbOrderItemList(tcsbOrderItemEntityList);
            		pageList.add(page);
            	}catch(Exception e){
            		logger.info(e.getMessage());
            	}
            }
        }
        map.put(NormalExcelConstants.FILE_NAME,"订单管理");
        map.put(NormalExcelConstants.CLASS,TcsbOrderPage.class);
        map.put(NormalExcelConstants.PARAMS,new ExportParams("订单管理列表", "导出人:Jeecg",
            "导出信息"));
        map.put(NormalExcelConstants.DATA_LIST,pageList);
        return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}

    /**
	 * 通过excel导入数据
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(params = "importExcel", method = RequestMethod.POST)
	@ResponseBody
	public AjaxJson importExcel(HttpServletRequest request, HttpServletResponse response) {
		AjaxJson j = new AjaxJson();
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();
		for (Map.Entry<String, MultipartFile> entity : fileMap.entrySet()) {
			MultipartFile file = entity.getValue();// 获取上传文件对象
			ImportParams params = new ImportParams();
			params.setTitleRows(2);
			params.setHeadRows(2);
			params.setNeedSave(true);
			try {
				List<TcsbOrderPage> list =  ExcelImportUtil.importExcel(file.getInputStream(), TcsbOrderPage.class, params);
				TcsbOrderEntity entity1=null;
				for (TcsbOrderPage page : list) {
					entity1=new TcsbOrderEntity();
					MyBeanUtils.copyBeanNotNull2Bean(page,entity1);
		            tcsbOrderService.addMain(entity1, page.getTcsbOrderItemList());
				}
				j.setMsg("文件导入成功！");
			} catch (Exception e) {
				j.setMsg("文件导入失败！");
				logger.error(ExceptionUtil.getExceptionMessage(e));
			}finally{
				try {
					file.getInputStream().close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			}
			return j;
	}
	/**
	* 导出excel 使模板
	*/
	@RequestMapping(params = "exportXlsByT")
	public String exportXlsByT(ModelMap map) {
		map.put(NormalExcelConstants.FILE_NAME,"订单管理");
		map.put(NormalExcelConstants.CLASS,TcsbOrderPage.class);
		map.put(NormalExcelConstants.PARAMS,new ExportParams("订单管理列表", "导出人:"+ ResourceUtil.getSessionUserName().getRealName(),
		"导出信息"));
		map.put(NormalExcelConstants.DATA_LIST,new ArrayList());
		return NormalExcelConstants.JEECG_EXCEL_VIEW;
	}
	/**
	* 导入功能跳转
	*
	* @return
	*/
	@RequestMapping(params = "upload")
	public ModelAndView upload(HttpServletRequest req) {
		req.setAttribute("controller_name", "tcsbOrderController");
		return new ModelAndView("common/upload/pub_excel_upload");
	}

 	
 	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public List<TcsbOrderEntity> list() {
		List<TcsbOrderEntity> listTcsbOrders=tcsbOrderService.getList(TcsbOrderEntity.class);
		return listTcsbOrders;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> get(@PathVariable("id") String id) {
		TcsbOrderEntity task = tcsbOrderService.get(TcsbOrderEntity.class, id);
		if (task == null) {
			return new ResponseEntity(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity(task, HttpStatus.OK);
	}
 	
 	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<?> create(@RequestBody TcsbOrderPage tcsbOrderPage, UriComponentsBuilder uriBuilder) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbOrderPage>> failures = validator.validate(tcsbOrderPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		
		TcsbOrderEntity tcsbOrder = new TcsbOrderEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(tcsbOrder,tcsbOrderPage);
		}catch(Exception e){
            logger.info(e.getMessage());
        }
		tcsbOrderService.addMain(tcsbOrder, tcsbOrderItemList);

		//按照Restful风格约定，创建指向新任务的url, 也可以直接返回id或对象.
		String id = tcsbOrderPage.getId();
		URI uri = uriBuilder.path("/rest/tcsbOrderController/" + id).build().toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(uri);

		return new ResponseEntity(headers, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@RequestBody TcsbOrderPage tcsbOrderPage) {
		//调用JSR303 Bean Validator进行校验，如果出错返回含400错误码及json格式的错误信息.
		Set<ConstraintViolation<TcsbOrderPage>> failures = validator.validate(tcsbOrderPage);
		if (!failures.isEmpty()) {
			return new ResponseEntity(BeanValidators.extractPropertyAndMessage(failures), HttpStatus.BAD_REQUEST);
		}

		//保存
		List<TcsbOrderItemEntity> tcsbOrderItemList =  tcsbOrderPage.getTcsbOrderItemList();
		
		TcsbOrderEntity tcsbOrder = new TcsbOrderEntity();
		try{
			MyBeanUtils.copyBeanNotNull2Bean(tcsbOrder,tcsbOrderPage);
		}catch(Exception e){
            logger.info(e.getMessage());
        }
		tcsbOrderService.updateMain(tcsbOrder, tcsbOrderItemList);

		//按Restful约定，返回204状态码, 无内容. 也可以返回200状态码.
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") String id) {
		TcsbOrderEntity tcsbOrder = tcsbOrderService.get(TcsbOrderEntity.class, id);
		tcsbOrderService.delMain(tcsbOrder);
	}
	
	

	
	/**
	 * 更新购物车并获取原先的订单
	 * @param ids
	 * @return
	 *//*
	@RequestMapping(value = "makeOrderUpdate",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject makeOrderUpdate(HttpServletRequest request,HttpServletResponse response) {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		String order= request.getParameter("order");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				TcsbOrderVO tcsbOrderVO = JSONHelper.fromJsonToObject(order, TcsbOrderVO.class);
				map = tcsbOrderService.makeOrderUpdate(tcsbOrderVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(map);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}*/
	
	/**
	 * 生成订单
	 * 只返回订单号
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "saveOrderReturnOrderNo",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject saveOrderReturnOrderNo(HttpServletRequest request) {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		String order= request.getParameter("order");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
				TcsbOrderVO tcsbOrderVO = JSONHelper.fromJsonToObject(order, TcsbOrderVO.class);
				map = tcsbOrderService.saveOrderReturnOrderNo(tcsbOrderVO);
		} catch (Exception e) {
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(map);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}
	
	
	
	
	@RequestMapping(value = "getOrderByOrderNo",consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getOrderByOrderNo(@RequestParam String orderNo,@RequestParam String userId,HttpServletRequest request,HttpServletResponse response) {
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		TcsbOrderEntity tcsbOrderEntity = tcsbOrderService.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
		//根据userId和shopId判断是否已收藏该店铺
		List<Map<String, Object>> shopCollectMaps = systemService.findHql("from TcsbShopCollectEntity where userId=? and shopId=? and isDel=0", userId,tcsbOrderEntity.getShopId());
		boolean isCollect = false;
		if (!shopCollectMaps.isEmpty()) {
			isCollect = true;
		}
		//根据userId和shopId判断是否已有评论该店铺
		boolean isEvaluate = false;
		List<Map<String, Object>> shopEvaluateMaps = systemService.findHql("from TcsbShopEvaluateEntity where userId=? and shopId=?", userId,tcsbOrderEntity.getShopId());
		if (!shopEvaluateMaps.isEmpty()) {
			isEvaluate = true;
		}
		Map<String, Object> map = new HashMap<>();
		map.put("isCollect", isCollect);
		map.put("isEvaluate", isEvaluate);
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, tcsbOrderEntity.getShopId());
		map.put("shopName", tcsbShopEntity.getName());
		map.put("shopImg", getCkPath()+tcsbShopEntity.getHeadimg());
		map.put("createTime", tcsbOrderEntity.getCreateTime());
		TcsbDeskEntity tcsbDeskEntity = tcsbDeskService.findUniqueByProperty(TcsbDeskEntity.class, "number", tcsbOrderEntity.getDeskId());
		if (tcsbDeskEntity==null) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			ajaxJsonApi.setMsg("请传正确的deskId");
			ajaxJsonApi.setSuccess(false);
			return new JSONPObject(callbackFunName, ajaxJsonApi);
		}
		map.put("platformDiscountPrice", tcsbOrderEntity.getPlatformDiscountPrice());
		map.put("deskName", tcsbDeskEntity.getDeskName());
		TcsbOrderVO tcsbOrderVO = new TcsbOrderVO();
		List<TcsbOrderItemVO> tcsbOrderItemVOs = new ArrayList<>();
		try {
			tcsbOrderVO.setDeskId(tcsbOrderEntity.getDeskId());
			tcsbOrderVO.setShopId(tcsbOrderEntity.getShopId());
			List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
			for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
				TcsbOrderItemVO tcsbOrderItemVO = new TcsbOrderItemVO();
				MyBeanUtils.copyBeanNotNull2Bean(tcsbOrderItemEntity, tcsbOrderItemVO);
				TcsbFoodEntity tcsbFoodEntity = tcsbFoodService.get(TcsbFoodEntity.class, tcsbOrderItemEntity.getFoodId());
				tcsbOrderItemVO.setName(tcsbFoodEntity.getName());
				tcsbOrderItemVOs.add(tcsbOrderItemVO);
			}
			tcsbOrderVO.setTcsbOrderItemVOs(tcsbOrderItemVOs);
			map.put("order", tcsbOrderVO);
			
			//map.put("finalMoney", tcsbOrderEntity.getDisPrice());
			map.put("totalMoney", tcsbOrderEntity.getTotalPrice());
			Double finalMoney =0.0;
			//获取优惠活动
			List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = tcsbDiscountActivityService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbOrderEntity.getShopId());
			if (!tcsbDiscountActivityEntities.isEmpty()) {
				//获取优惠活动中满减服务中有效的活动
				//List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntitysIsActives = new ArrayList<>();
				String shopDiscountMsg = "";
				String shopDiscountMsgDes ="";
				for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
					//获取活动生效的时间
					Date beginTime = tcsbDiscountActivityEntity.getCreateDate();
					
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = tcsbFullcutTemplateService.get(TcsbFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
					Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
					String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); 
					int days = 0;
					//todo
					if (dateUnit.equals("year")) {
						days = userPeriod*DateUtils.getCurrentYearDays();
					}else if (dateUnit.equals("month")) {
						days = userPeriod*DateUtils.getCurrentMonthDay();
					}else {
						days = userPeriod;
					}
					//优惠活动中有效的满减活动
					if (DateUtils.getDaysByDate(new Date(), beginTime)<=days) {
						//tcsbDiscountActivityEntitysIsActives.add(tcsbDiscountActivityEntity);
						shopDiscountMsg =shopDiscountMsg+ "满"+ tcsbFullcutTemplateEntity.getTotal() + "减" + tcsbFullcutTemplateEntity.getDiscount() + ",";
						shopDiscountMsgDes = shopDiscountMsgDes + tcsbFullcutTemplateEntity.getTotal()+"_"+ tcsbFullcutTemplateEntity.getDiscount() + ",";
					};
				}
			   /*map.put("shopDiscountTotal", tcsbFullcutTemplateEntity.getTotal());
				map.put("shopDiscountDiscount", tcsbFullcutTemplateEntity.getDiscount());*/
				if (!StringUtil.isEmpty(shopDiscountMsg)) {
					shopDiscountMsg = shopDiscountMsg.substring(0, shopDiscountMsg.length()-1);
				}
				
				//立减
				List<String> totalList = new ArrayList<>();
				List<String> discountList = new ArrayList<>();
				if (!StringUtil.isEmpty(shopDiscountMsgDes)) {
					if (shopDiscountMsgDes.contains(",")) {
						String dis[] = shopDiscountMsgDes.split(",");
						for (int i = 0; i < dis.length; i++) {
							String tem[] = dis[i].split("_");
							for (int j = 0; j < tem.length; j++) {
								totalList.add(tem[0]);
								discountList.add(tem[1]);
							}
						}
					}
					//升序
					Collections.sort(totalList);
					Collections.sort(discountList);
					boolean isCut = false;
					int index = 0;
					for (int i = totalList.size()-1; i >= 0; i--) {
						if (tcsbOrderEntity.getTotalPrice()>Double.parseDouble(totalList.get(i)) ) {
							finalMoney = tcsbOrderEntity.getTotalPrice() - Double.parseDouble(discountList.get(i));
							//有满减时
							index=i;
							isCut = true;
							break;
						}
					}
					if (!isCut) {
						finalMoney=tcsbOrderEntity.getTotalPrice();
						tcsbOrderEntity.setUniversalCouponPrice(0.0);
					}else {
						tcsbOrderEntity.setUniversalCouponPrice(Double.parseDouble(discountList.get(index)));
					}
				}else {
					tcsbOrderEntity.setUniversalCouponPrice(0.0);
					finalMoney=tcsbOrderEntity.getTotalPrice();
				}
				
				map.put("finalMoney", finalMoney);
				map.put("shopDiscountMsg", shopDiscountMsg);
				map.put("shopDiscount", "true");
			}else {
				map.put("shopDiscount", "false");
			}
			
			//获取专用券（userId）
			List<TcsbCouponEntity> tcsbCouponEntities = systemService.findHql("from TcsbCouponEntity where shopId = ? and userId = ?", tcsbOrderEntity.getShopId(),userId);
			String shopUserDiscountMsgDes = "";
			if (!tcsbCouponEntities.isEmpty()) {
				//获取专用券中满减服务中有效的活动
				for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
					//获取活动生效的时间
					Date beginTime = tcsbCouponEntity.getCreateDate();
					TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
					Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();
					String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); 
					int days = 0;
					//todo
					if (dateUnit.equals("year")) {
						days = userPeriod*DateUtils.getCurrentYearDays();
					}else if (dateUnit.equals("month")) {
						days = userPeriod*DateUtils.getCurrentMonthDay();
					}else {
						days = userPeriod;
					}
					//专用券中有效的满减活动
					if (DateUtils.getDaysByDate(new Date(), beginTime)<=days) {
						shopUserDiscountMsgDes = shopUserDiscountMsgDes + tcsbFullcutTemplateEntity.getTotal()+"_"+ tcsbFullcutTemplateEntity.getDiscount() + ",";
					};
				}
				if (!StringUtil.isEmpty(shopUserDiscountMsgDes)) {
					shopUserDiscountMsgDes = shopUserDiscountMsgDes.substring(0,shopUserDiscountMsgDes.length()-1);
				}
				
			}
			//立减
			List<String> totalList = new ArrayList<>();
			List<String> discountList = new ArrayList<>();
			if (!StringUtil.isEmpty(shopUserDiscountMsgDes)) {
				if (shopUserDiscountMsgDes.contains(",")) {
					String dis[] = shopUserDiscountMsgDes.split(",");
					for (int i = 0; i < dis.length; i++) {
						String tem[] = dis[i].split("_");
						for (int j = 0; j < tem.length; j++) {
							totalList.add(tem[0]);
							discountList.add(tem[1]);
						}
					}
				}
				//升序
				Collections.sort(totalList);
				Collections.sort(discountList);
				boolean isCut = false;
				int index = 0;
				for (int i = totalList.size()-1; i >= 0; i--) {
					if (finalMoney>Double.parseDouble(totalList.get(i)) ) {
						finalMoney = finalMoney - Double.parseDouble(discountList.get(i));
						//有满减时
						index=i;
						isCut = true;
						break;
					}
				}
				if (!isCut) {
					//finalMoney=finalMoney;
					tcsbOrderEntity.setSpecialCouponPrice(0.0);
				}else {
					tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(discountList.get(index)));
				}
				map.put("finalMoney", finalMoney);
				map.put("shopUserDiscountMsgDes", shopUserDiscountMsgDes);
				map.put("shopUserDiscount", "true");
			}else {
				tcsbOrderEntity.setSpecialCouponPrice(0.0);
				map.put("shopUserDiscount", "false");
				//finalMoney=finalMoney;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(map);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
		//return ajaxJsonApi;
	}
	
	/**
	 * 获取我消费的订单
	 * @param userId
	 * @param request
	 * @param response
	 * @return
	 *//*
	@RequestMapping(value = "/getMyOrder",method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public JSONPObject getMyOrder(@RequestParam String userId,@RequestParam String status,HttpServletRequest request,HttpServletResponse response){
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
		List<TcsbOrderVO> maps = new ArrayList<>();
		try {
			maps = tcsbOrderService.getMyOrder(userId,status);
			for (TcsbOrderVO tcsbOrderVO : maps) {
				tcsbOrderVO.setShopImg(getCkPath()+tcsbOrderVO.getShopImg());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
		ajaxJsonApi.setMsg(ReturnMessageEnum.QUERYTRUE.getMsg());
		ajaxJsonApi.setSuccess(true);
		ajaxJsonApi.setObj(maps);
		return new JSONPObject(callbackFunName, ajaxJsonApi);
	}*/
}
