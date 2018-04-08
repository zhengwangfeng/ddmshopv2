package com.pc.income;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.criterion.Restrictions;
import org.jeecgframework.core.common.hibernate.qbc.CriteriaQuery;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.tag.vo.datatable.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcsb.order.VO.OrderStatisticVo;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.settleaccount.entity.TcsbSettleAccountEntity;
import com.tcsb.settleaccount.vo.TcsbSettleAccountEntityVo;
import com.tcsb.shop.entity.TcsbShopEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.weixin.util.DateUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/pcIncome")
public class IncomeController {

	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	
	
	@RequestMapping("/pcIncomeStatics")
	@ResponseBody
	public OrderStatisticVo pcIncomeStatics(String shopId,String startTime,String endTime) throws ParseException{
		 OrderStatisticVo os = new  OrderStatisticVo();
		 CriteriaQuery cq = new CriteriaQuery(TcsbOrderEntity.class);
         cq.eq("payStatus", "1");
         cq.eq("shopId", shopId);
         cq.ge("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(startTime));
     	 cq.le("createTime", new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(endTime));
         cq.add();
         List<TcsbOrderEntity> tcsbOrderList = tcsbOrderService.getListByCriteriaQuery(cq,false);
         if(tcsbOrderList.size()>0){
        	
        		double totalprice = 0.0;//总营业额
        		double realyprice = 0.0;//实际收款
        		
        		double onlineIncome = 0.0;//线上营收
        		double onlinePayment = 0.0;//线上支付——线上实际收款
        		double platformdiscountprice = 0.0;//平台优惠
        		double specialcouponprice = 0.0;//商户优惠
        		double universalcouponprice = 0.0;//通用卷
        		
        		//double platformSettlement = 0.0;//平台需结算
        		
        		double offlineIncome = 0.0;//线下营收
        		double offlinePayment = 0.0;//线下支付	
        		double offlineDiscountprice = 0.0;//抹零
        		double vipDiscountPrice = 0.0;//会员折扣
        		
        		double offlinepaybycard = 0.0;
        		double offlinepaybywechat = 0.0;
        		double offlinepaybyaply = 0.0;
        		double offlinepaybycash = 0.0;
        		double offlinepaybycredit = 0.0;
        		double offlinepaybyteam = 0.0;
        		double offlinepaybyvipcard = 0.0;
        		 for (TcsbOrderEntity tcsbOrderEntity : tcsbOrderList) {
        			 totalprice = BigDecimalUtil.add(totalprice,tcsbOrderEntity.getTotalPrice());
        			 platformdiscountprice = BigDecimalUtil.add(platformdiscountprice,tcsbOrderEntity.getPlatformDiscountPrice());
        			 specialcouponprice = BigDecimalUtil.add(specialcouponprice,tcsbOrderEntity.getSpecialCouponPrice());
        			 universalcouponprice = BigDecimalUtil.add(universalcouponprice,tcsbOrderEntity.getUniversalCouponPrice());
        			
        			 if(StringUtil.isNotEmpty(tcsbOrderEntity.getOfflineDiscount())){
        				 offlineDiscountprice = BigDecimalUtil.add(offlineDiscountprice,tcsbOrderEntity.getHandDiscountPrice());
        			 }
        			 if(StringUtil.isNotEmpty(tcsbOrderEntity.getVipDiscountPrice())){
        				 vipDiscountPrice = BigDecimalUtil.add(vipDiscountPrice,tcsbOrderEntity.getVipDiscountPrice());
        			 }
        				if(tcsbOrderEntity.getPayMethod().equals("0")){
        					realyprice = BigDecimalUtil.add(realyprice,tcsbOrderEntity.getOnlinePrice());
        					onlineIncome = BigDecimalUtil.add(onlineIncome,tcsbOrderEntity.getTotalPrice());
        					onlinePayment = BigDecimalUtil.add(onlinePayment,tcsbOrderEntity.getOnlinePrice());
        				}else{
        					if(tcsbOrderEntity.getStatus().equals("2")){
        						realyprice = BigDecimalUtil.add(realyprice,tcsbOrderEntity.getOnlinePrice());
        						realyprice = BigDecimalUtil.add(realyprice,tcsbOrderEntity.getOfflinePrice());
            					onlineIncome = BigDecimalUtil.add(onlineIncome,tcsbOrderEntity.getOnlinePrice());
            					onlinePayment = BigDecimalUtil.add(onlinePayment,tcsbOrderEntity.getOnlinePrice());
            					offlineIncome = BigDecimalUtil.add(offlineIncome,BigDecimalUtil.sub(tcsbOrderEntity.getTotalPrice(), tcsbOrderEntity.getOnlinePrice()));
            					offlinePayment = BigDecimalUtil.add(offlinePayment,tcsbOrderEntity.getOfflinePrice());
        					}else{
        						realyprice = BigDecimalUtil.add(realyprice,tcsbOrderEntity.getOfflinePrice());
            					offlineIncome = BigDecimalUtil.add(offlineIncome,tcsbOrderEntity.getTotalPrice());
            					offlinePayment = BigDecimalUtil.add(offlinePayment,tcsbOrderEntity.getOfflinePrice());
        					}
            					if(StringUtil.isNotEmpty(tcsbOrderEntity.getOfflinePayMethod())){
            						if(tcsbOrderEntity.getOfflinePayMethod().equals("1")){
                						//现金
                						offlinepaybycash = BigDecimalUtil.add(offlinepaybycash, tcsbOrderEntity.getOfflinePrice());
                					}
                					if(tcsbOrderEntity.getOfflinePayMethod().equals("2")){
                						//银联卡
                						offlinepaybycard = BigDecimalUtil.add(offlinepaybycard, tcsbOrderEntity.getOfflinePrice());
                					}
                					if(tcsbOrderEntity.getOfflinePayMethod().equals("3")){
                						//会员卡
                						offlinepaybyvipcard = BigDecimalUtil.add(offlinepaybyvipcard, tcsbOrderEntity.getOfflinePrice());
                					}
                					if(tcsbOrderEntity.getOfflinePayMethod().equals("4")){
                						//支付宝
                						offlinepaybyaply = BigDecimalUtil.add(offlinepaybyaply, tcsbOrderEntity.getOfflinePrice());
                					}
                					if(tcsbOrderEntity.getOfflinePayMethod().equals("5")){
                						//微信
                						offlinepaybywechat = BigDecimalUtil.add(offlinepaybywechat, tcsbOrderEntity.getOfflinePrice());
                					}
                					if(tcsbOrderEntity.getOfflinePayMethod().equals("6")){
                						//团购
                						offlinepaybyteam = BigDecimalUtil.add(offlinepaybyteam, tcsbOrderEntity.getOfflinePrice());
                					}
                					if(tcsbOrderEntity.getOfflinePayMethod().equals("7")){
                						//赊账
                						offlinepaybycredit = BigDecimalUtil.add(offlinepaybycredit, tcsbOrderEntity.getOfflinePrice());
                					}
            					}else{
            						offlinepaybycash = BigDecimalUtil.add(offlinepaybycash, tcsbOrderEntity.getOfflinePrice());
            					}
        						
        					
        					
        					
        					
        				}
        	         }
        		 
        		  os.setTotalprice(BigDecimalUtil.numericRetentionDecimal(totalprice, 2));
        		  os.setRealyprice(BigDecimalUtil.numericRetentionDecimal(realyprice, 2));
        		  
        		  os.setOnlineIncome(BigDecimalUtil.numericRetentionDecimal(onlineIncome, 2));
        		  os.setOnlinePayment(BigDecimalUtil.numericRetentionDecimal(onlinePayment, 2));
        		  os.setPlatformdiscountprice(BigDecimalUtil.numericRetentionDecimal(platformdiscountprice, 2));
        		  os.setSpecialcouponprice(BigDecimalUtil.numericRetentionDecimal(specialcouponprice, 2));
        		  os.setUniversalcouponprice(BigDecimalUtil.numericRetentionDecimal(universalcouponprice, 2));
        		  
        		  
        		  os.setOfflineIncome(BigDecimalUtil.numericRetentionDecimal(offlineIncome, 2));
        		  os.setOfflinePayment(BigDecimalUtil.numericRetentionDecimal(offlinePayment, 2));
                  os.setOfflinediscountprice(BigDecimalUtil.numericRetentionDecimal(offlineDiscountprice, 2));
                  os.setVipDiscountPrice(BigDecimalUtil.numericRetentionDecimal(vipDiscountPrice, 2));
                  
                  
                 os.setOfflinepaybyaply(BigDecimalUtil.numericRetentionDecimal(offlinepaybyaply, 2));
                 os.setOfflinepaybycard(BigDecimalUtil.numericRetentionDecimal(offlinepaybycard, 2));
                 os.setOfflinepaybycash(BigDecimalUtil.numericRetentionDecimal(offlinepaybycash, 2));
                 os.setOfflinepaybycredit(BigDecimalUtil.numericRetentionDecimal(offlinepaybycredit, 2));
                 os.setOfflinepaybyteam(BigDecimalUtil.numericRetentionDecimal(offlinepaybyteam, 2));
                 os.setOfflinepaybywechat(BigDecimalUtil.numericRetentionDecimal(offlinepaybywechat, 2));
                 os.setOfflinepaybyvipcard(BigDecimalUtil.numericRetentionDecimal(offlinepaybyvipcard,2));   
         }  
		return os;
	}
	
	
	
	@RequestMapping(params = "list")
	public ModelAndView list(String shopId,ModelMap modelMap) throws Exception {
		
		modelMap.addAttribute("shopId", shopId);
		return new ModelAndView("com/pc/incomeStatistics");
	}
	
	
	@RequestMapping("/pcdatagrid")
	@ResponseBody
	public List<OrderStatisticVo> pcdatagrid(String shopId,String startTime,String endTime) throws IllegalAccessException, InvocationTargetException, ParseException {
		System.out.println("==========time=============");
		System.out.println(startTime);
		System.out.println(endTime);
		System.out.println("=======================");
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
			     
			     
				/*	JSONArray jsonData=new JSONArray();
					JSONObject jo=null;
					for (OrderStatisticVo ovoDto2 : ovoDto1) {
						jo=new JSONObject();
			            jo.put("id",  ovoDto2.getId());
			            jo.put("totalprice", ovoDto2.getTotalprice());
			            jo.put("disprice", ovoDto2.getDisprice());
			            jo.put("onlinePayment", ovoDto2.getOnlinePayment());
			            jo.put("offlinePayment", ovoDto2.getOfflinePayment());
			            jo.put("platformSettlement", ovoDto2.getPlatformSettlement());
			            jo.put("platformdiscountprice",ovoDto2.getPlatformdiscountprice());
			            
			            jo.put("specialcouponprice",ovoDto2.getSpecialcouponprice());
			            jo.put("universalcouponprice",ovoDto2.getUniversalcouponprice());
			            jo.put("offlineDiscount",ovoDto2.getOfflineDiscount());
			            jo.put("onlineIncome",ovoDto2.getOnlineIncome());
			            jo.put("offlineIncome",ovoDto2.getOfflineIncome());
			            jo.put("offlinepaybycard",ovoDto2.getOfflinepaybycard());
			            jo.put("offlinepaybywechat",ovoDto2.getOfflinepaybywechat());
			            
			            
			            jo.put("offlinepaybyaply",ovoDto2.getOfflinepaybyaply());
			            jo.put("offlinepaybycash",ovoDto2.getOfflinepaybycash());
			            jo.put("offlinepaybycredit",ovoDto2.getOfflinepaybycredit());
			            jo.put("offlinepaybyteam",ovoDto2.getOfflinepaybyteam());
			            jo.put("createdate",ovoDto2.getCreatedate());
			            jsonData.add(jo);
					}

			        JSONObject jsonObject=new JSONObject();
			        jsonObject.put("rows", jsonData);//JSONArray
			        jsonObject.put("total",ovoDto1.size());//总记录数
*/			     

					return ovoDto1;
	}
	
	
}
