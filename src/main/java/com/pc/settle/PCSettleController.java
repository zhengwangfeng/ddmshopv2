package com.pc.settle;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.jeecgframework.web.system.pojo.base.TSUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;
import com.tcsb.settleaccount.service.TcsbSettleAccountServiceI;
import com.tcsb.settleaccount.vo.TcsbSettleAccountEntityVo;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.tcsb.tcsborderparent.entity.TcsbOrderParentEntity;
import com.weixin.util.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/PCSettle")
public class PCSettleController {
	
	@Autowired
	private TcsbSettleAccountServiceI tcsbSettleAccountService;
	@Autowired
	private TcsbShopServiceI tcsbShopService;
	
	@RequestMapping(params = "list")
	@ResponseBody
	public boolean settle(String offlinePrice,String VipDiscount,String orderParenId,String offlinePayMethod){
		TcsbOrderParentEntity orderparent = tcsbSettleAccountService.get(TcsbOrderParentEntity.class, orderParenId);
		double forontMoney = 0.0;
		if(StringUtil.isNotEmpty(orderparent.getFrontMoney())){
			if(StringUtil.isNotEmpty(orderparent.getUseFrontMoney())){
					if(orderparent.getUseFrontMoney().equals("0")){
						forontMoney = orderparent.getFrontMoney();
					}
				}else{
					forontMoney = orderparent.getFrontMoney();
				}
		}
		try {
			String hql = "from TcsbOrderEntity where orderParentId='"+ orderParenId +"' and payStatus=0";
			TcsbOrderEntity order = tcsbSettleAccountService.singleResult(hql);
			//
			double vipPrice = BigDecimalUtil.mul(order.getTotalPrice(), Double.valueOf(VipDiscount)/10);
			double vipDiscountPrice = BigDecimalUtil.sub(order.getTotalPrice(), vipPrice); 
			String vipDiscountPriceString = BigDecimalUtil.numericRetentionDecimal(vipDiscountPrice, 2);
			//
			double offlineDiscount = BigDecimalUtil.sub(vipPrice, Double.valueOf(offlinePrice));
			
			String offlineDiscountString = BigDecimalUtil.numericRetentionDecimal(BigDecimalUtil.add(offlineDiscount, forontMoney), 2);
			order.setOnlinePrice(0.0);
			order.setOfflinePrice(Double.valueOf(offlinePrice));
			order.setPlatformDiscountPrice(0.0);
			order.setSpecialCouponPrice(0.0);
			order.setUniversalCouponPrice(0.0);
			order.setOfflineDiscount(offlineDiscountString);
			order.setOfflinePayMethod(offlinePayMethod);
			order.setVipDiscountPrice(Double.valueOf(vipDiscountPriceString));
			tcsbSettleAccountService.saveOrUpdate(order);
			orderparent.setUseFrontMoney("1");
			tcsbSettleAccountService.saveOrUpdate(orderparent);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	
	@RequestMapping(params = "list")
	public ModelAndView list(String shopId,ModelMap modelMap) throws Exception {
		
		
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);

		//获取当前系统日期
		Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd");
		//检测是否有生成过结算数据
		CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class);
		cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		cq.addOrder("waitSettleData", SortDirection.desc);
		cq.setPageSize(1);
		List<TcsbSettleAccountEntity> TcsbSettleAccountList = tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
		if(TcsbSettleAccountList.size()>0){
			//有生成过结算数据
				//获取最新一条的结算数据时间
			
			
			Calendar querycalendar = new GregorianCalendar(); 
			querycalendar.setTime(TcsbSettleAccountList.get(0).getWaitSettleData()); 
			querycalendar.add(querycalendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
			//String ordersql = "select DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' ) as createTime, sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=? and PAY_STATUS=1 and CREATE_TIME>=? and CREATE_TIME<? group by DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' )";
			String ordersql = "select DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime,"
					+ " sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=?"
					+ " and PAY_STATUS=1 and pay_method=0 and pay_time>=? and pay_time<?"
					+ " group by DATE_FORMAT( pay_time, '%Y-%m-%d' )";
			
			List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),querycalendar.getTime(),calSrc.getTime());
			for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
				TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
				tcsbSettleAccountEntity.setIsSettle("N");
				Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("paytime").toString(),"yyyy-MM-dd");
				Calendar calendar = new GregorianCalendar(); 
			    calendar.setTime(calDes.getTime()); 
			    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				tcsbSettleAccountEntity.setSettleData(calendar.getTime());
				tcsbSettleAccountEntity.setSettleWay(null);
				tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
				tcsbSettleAccountEntity.setTotal(tcsbOrderEntityList.get(i).get("onlinePrice").toString());
				tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
				tcsbSettleAccountService.save(tcsbSettleAccountEntity);
			}
		}else{
			//没有生成结算数据，重新生成结算数据
			//String ordersql = "select DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' ) as createTime, sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=? and PAY_STATUS=1 and CREATE_TIME<? group by DATE_FORMAT( CREATE_TIME, '%Y-%m-%d' )";
			String ordersql = "select DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime,"
					+ " sum(online_price) as onlinePrice  from tcsb_order where SHOP_ID=?"
					+ " and PAY_STATUS=1 and pay_method=0 and pay_time<?"
					+ " group by DATE_FORMAT( pay_time, '%Y-%m-%d' )";
			List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(ordersql,tcsbShopEntity.getId(),calSrc.getTime());
			for (int i = 0; i < tcsbOrderEntityList.size(); i++) {
				TcsbSettleAccountEntity tcsbSettleAccountEntity = new TcsbSettleAccountEntity();
				tcsbSettleAccountEntity.setIsSettle("N");
				Calendar calDes = DateUtils.parseCalendar(tcsbOrderEntityList.get(i).get("paytime").toString(),"yyyy-MM-dd");
				Calendar calendar = new GregorianCalendar(); 
			    calendar.setTime(calDes.getTime()); 
			    calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动 
				tcsbSettleAccountEntity.setSettleData(calendar.getTime());
				tcsbSettleAccountEntity.setSettleWay(null);
				tcsbSettleAccountEntity.setShopId(tcsbShopEntity.getId());
				tcsbSettleAccountEntity.setTotal(tcsbOrderEntityList.get(i).get("onlinePrice").toString());
				tcsbSettleAccountEntity.setWaitSettleData(calDes.getTime());
				tcsbSettleAccountService.save(tcsbSettleAccountEntity);
			}
		}
		//获取今日待结算的数据
		String todayordersql = "select DATE_FORMAT( pay_time, '%Y-%m-%d' ) as paytime,"
				+ " sum(online_price) as onlinePrice  from tcsb_order"
				+ " where SHOP_ID=? and PAY_STATUS=1 and pay_method=0 and pay_time>=?"
				+ " group by DATE_FORMAT( pay_time, '%Y-%m-%d' )";
		List<Map<String,Object>> tcsbOrderEntityList = tcsbSettleAccountService.findForJdbc(todayordersql,tcsbShopEntity.getId(),calSrc.getTime());
		if(tcsbOrderEntityList.size()>0){
			String thisWaitAllMoney = tcsbOrderEntityList.get(0).get("onlinePrice").toString();    
			Double poundages = BigDecimalUtil.mul(Double.valueOf(tcsbOrderEntityList.get(0).get("onlinePrice").toString()), 0.03); 
			Double waitSettleTotal =  BigDecimalUtil.sub(Double.valueOf(thisWaitAllMoney), poundages);
			modelMap.addAttribute("thisWaitAllMoney", thisWaitAllMoney);
			modelMap.addAttribute("poundage", BigDecimalUtil.numericRetentionDecimal(poundages, 2));//手续费
			modelMap.addAttribute("waitSettleTotal", BigDecimalUtil.numericRetentionDecimal(waitSettleTotal, 2));
			calSrc.add(calSrc.DATE, 1);
			String dataweek = org.jeecgframework.core.util.DateUtils.getDayOfWeekByDate(DateUtils.date_sdf.format(calSrc.getTime()));
			modelMap.addAttribute("nextSettleData", DateUtils.date_sdf.format(calSrc.getTime())+"  "+dataweek);
			
		}else{
			modelMap.addAttribute("thisWaitAllMoney", 0);
			modelMap.addAttribute("waitSettleTotal", 0);
			modelMap.addAttribute("poundage", 0);
			calSrc.add(calSrc.DATE, 1);
			String dataweek = org.jeecgframework.core.util.DateUtils.getDayOfWeekByDate(DateUtils.date_sdf.format(calSrc.getTime()));
			modelMap.addAttribute("nextSettleData", DateUtils.date_sdf.format(calSrc.getTime())+"  "+dataweek);
		}
		
		
		
		modelMap.addAttribute("shopId", shopId);
		return new ModelAndView("com/pc/pcsettleview");
	}
	
	@RequestMapping("/pcdatagrid")
	@ResponseBody
	public JSONObject pcdatagrid(String shopId) throws IllegalAccessException, InvocationTargetException {
		
		TcsbShopEntity tcsbShopEntity = tcsbShopService.get(TcsbShopEntity.class, shopId);

		
		CriteriaQuery cq = new CriteriaQuery(TcsbSettleAccountEntity.class);
		cq.add(Restrictions.eq("shopId", tcsbShopEntity.getId()));
		cq.addOrder("settleData", SortDirection.desc);
		cq.add();
		List<TcsbSettleAccountEntity> TcsbSettleAccountList= tcsbSettleAccountService.getListByCriteriaQuery(cq, true);
		List<TcsbSettleAccountEntityVo> TcsbSettleAccountEntityVoList = new ArrayList<>();
		TcsbSettleAccountEntityVo TcsbSettleAccountEntityVo;
		for (TcsbSettleAccountEntity TcsbSettleAccountEntity : TcsbSettleAccountList) {
			TcsbSettleAccountEntityVo = new TcsbSettleAccountEntityVo();
			BeanUtils.copyProperties(TcsbSettleAccountEntityVo, TcsbSettleAccountEntity);
			double bound = BigDecimalUtil.mul(Double.valueOf(TcsbSettleAccountEntity.getTotal()), 0.03);
			double tatol = BigDecimalUtil.sub(Double.valueOf(TcsbSettleAccountEntity.getTotal()), bound);
			TcsbSettleAccountEntityVo.setTotal(BigDecimalUtil.numericRetentionDecimal(tatol, 2));
			TcsbSettleAccountEntityVo.setShopName(tcsbShopEntity.getName());
			TcsbSettleAccountEntityVo.setWaitSettleDatas(DateUtils.date_sdf.format(TcsbSettleAccountEntity.getWaitSettleData()));
			TcsbSettleAccountEntityVoList.add(TcsbSettleAccountEntityVo);
		}
		JSONArray jsonData=new JSONArray();
		JSONObject jo=null;
		for (TcsbSettleAccountEntityVo tcsbSettleAccountEntityVo : TcsbSettleAccountEntityVoList) {
			jo=new JSONObject();
            jo.put("id",  tcsbSettleAccountEntityVo.getId());
            jo.put("shopName", tcsbSettleAccountEntityVo.getShopName());
            jo.put("waitSettleDatas", tcsbSettleAccountEntityVo.getWaitSettleDatas());
            jo.put("settleData", DateUtils.date_sdf.format(tcsbSettleAccountEntityVo.getSettleData()));
            jo.put("total", tcsbSettleAccountEntityVo.getTotal());
            jo.put("isSettle", tcsbSettleAccountEntityVo.getIsSettle());
            jo.put("settleWay",tcsbSettleAccountEntityVo.getSettleWay());
            jsonData.add(jo);
		}
		
		List<TcsbSettleAccountEntity> TcsbSettleAccountListcount = tcsbSettleAccountService.findByQueryString("from TcsbSettleAccountEntity where shopId='"+tcsbShopEntity.getId()+"'");

        //int TotalCount=SqlADO.getTradeRowsCount(sql);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("rows", jsonData);//JSONArray
        jsonObject.put("total",TcsbSettleAccountListcount.size());//总记录数
        //out.print(jsonObject.toString()); 
		return jsonObject;
	}

}
