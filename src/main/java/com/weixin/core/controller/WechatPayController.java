package com.weixin.core.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLSocketFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.tagext.PageData;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.model.json.AjaxJson;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.jeecgframework.core.util.BigDecimalUtil;
import org.jeecgframework.core.util.DateUtils;
import org.jeecgframework.core.util.ListSorter;
import org.jeecgframework.core.util.MD5;
import org.jeecgframework.core.util.MyBeanUtils;
import org.jeecgframework.core.util.StringUtil;
import org.jeecgframework.core.util.UUIDGenerator;
import org.jeecgframework.web.system.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.tcsb.coupon.entity.TcsbCouponEntity;
import com.tcsb.discountactivity.entity.TcsbDiscountActivityEntity;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.fullcuttemplate.entity.TcsbFullcutTemplateEntity;
import com.tcsb.order.VO.TcsbOrderItemVO;
import com.tcsb.order.entity.TcsbOrderEntity;
import com.tcsb.order.service.TcsbOrderServiceI;
import com.tcsb.orderitem.entity.TcsbOrderItemEntity;
import com.tcsb.shopfullcuttemplate.entity.TcsbShopFullcutTemplateEntity;
import com.tcsb.tcsborderbigdatarecord.entity.TcsbOrderBigdataRecordEntity;
import com.tcsb.tcsbpaynotice.entity.TcsbPayNoticeEntity;
import com.weixin.core.util.CommonUtil;
import com.weixin.core.util.ConfigUtil;
import com.weixin.core.util.JsonResult;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.WxPayUtil;
import com.weixin.core.util.XMLUtil;

@Controller
@RequestMapping("/wechatPayController")
public class WechatPayController {

	@Autowired
	private TcsbOrderServiceI tcsbOrderService;
	@Autowired
	private SystemService systemService;

	/**
	 * 
	 * couponsConfirm(调起微信支付)
	 * 
	 * @param request
	 * @param totalFee
	 *            //支付金额
	 * @param body
	 *            //支付描述
	 * @return
	 * @throws ParseException 
	 * @throws Exception
	 */
	@RequestMapping(params = "couponsConfirm")
	@ResponseBody
	public JSONPObject couponsConfirm(HttpServletRequest request,
			@RequestParam("openid") String openid,
			@RequestParam("orderNo") String orderNo) throws ParseException {
		String callbackFunName = request.getParameter("callbackparam");// 得到js函数名称
		String body = "点单么平台";
		// 获取订单
		TcsbOrderEntity tcsbOrderEntity = tcsbOrderService
				.findUniqueByProperty(TcsbOrderEntity.class, "orderNo", orderNo);
		//String money = tcsbOrderEntity.getTotalPrice().toString();// 获取订单金额
		//获取通用券和专用券和店铺优惠折上折才是最后的金额
		Double finalMoney = tcsbOrderEntity.getTotalPrice();
		//获取优惠活动
		List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbOrderEntity.getShopId());
		if (!tcsbDiscountActivityEntities.isEmpty()) {
			List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
			for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
				TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
				tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
			}
			if (!tcsbDiscountActivityEntities.isEmpty()) {
				ListSorter.sort(tcsbShopFullcutTemplateEntities, "total","discount");
				boolean hasCut = false;
				for (int i = tcsbShopFullcutTemplateEntities.size()-1; i >= 0; i--) {
					if (finalMoney>=tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
						finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
						//finalMoney = finalMoney - Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+"");
						tcsbOrderEntity.setUniversalCouponPrice(Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
						hasCut = true;
						break;
					}
				}
				if (!hasCut) {
					tcsbOrderEntity.setUniversalCouponPrice(0.0);
				}
			}else {
				tcsbOrderEntity.setUniversalCouponPrice(0.0);
			}
		}else {
			tcsbOrderEntity.setUniversalCouponPrice(0.0);
		}
		//获取专用券（userId）
		List<TcsbCouponEntity> tcsbCouponEntities = systemService.findHql("from TcsbCouponEntity where useStatus=0 and shopId = ? and userId = ? and useStatus='0' ", tcsbOrderEntity.getShopId(),openid);
		List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
		if (!tcsbCouponEntities.isEmpty()) {
			//获取专用券中满减服务中有效的活动
			for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
				//获取活动生效的时间
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
				Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
				if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
					tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
					tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
				};
			}
		}
		//立减
		if (!tcsbFullcutTemplateEntities.isEmpty()) {
			ListSorter.sort(tcsbFullcutTemplateEntities, "total","discount");
			boolean hasCut = false;
			outer:for (int i = tcsbFullcutTemplateEntities.size()-1; i >= 0; i--) {
				if (finalMoney>=tcsbFullcutTemplateEntities.get(i).getTotal()) {
					TcsbCouponEntity tcsbCouponEntity = systemService.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
					if ("0".equals(tcsbCouponEntity.getUseRange())) {
						finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
						//finalMoney = finalMoney - Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+"");
						tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
						hasCut = true;
						break outer;
					}if ("1".equals(tcsbCouponEntity.getUseRange())) {
						TcsbFoodEntity tcsbFoodEntity = systemService.get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
						List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
						//查找订单项中是否有该道菜
						for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
							if (tcsbOrderItemEntity.getFoodId().equals(tcsbFoodEntity.getId())) {
								finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
								//finalMoney = finalMoney - Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+"");
								tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
								hasCut = true;
								break outer;
							}
						}
					}
				}
			}
			if (!hasCut) {
				tcsbOrderEntity.setSpecialCouponPrice(0.0);
			}
		}else {
			tcsbOrderEntity.setSpecialCouponPrice(0.0);
		}
		
		String money = finalMoney.toString();// 获取订单金额
		//保留两位小数位
		money = BigDecimalUtil.numericRetentionDecimal(money, 2);
		// 金额转化为分为单位
		float sessionmoney = Float.parseFloat(money);
		String finalmoney = String.format("%.2f", sessionmoney);
		finalmoney = finalmoney.replace(".", "");
		// 商户订单号
		String out_trade_no = orderNo + "_" + System.currentTimeMillis();// 订单编号加时间戳防止重复订单，如取消订单后重复订单不能提交。注意要小于等于32位这是微信的坑。
		int intMoney = Integer.parseInt(finalmoney);
		// 总金额以分为单位，不带小数点
		String totalFee = String.valueOf(intMoney);
		String ipAddress = request.getRemoteAddr();
		String prepayId;
		SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
		try {
			prepayId = WxPayUtil.getPrepayId(totalFee, ipAddress, out_trade_no,
					body, openid);
			// 2 给H5页面传递参数 调起微信支付
			signParams = WxPayUtil.createPackageValue(prepayId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		AjaxJson ajaxJson = new AjaxJson();
		ajaxJson.setObj(signParams);
		ajaxJson.setSuccess(true);
		return new JSONPObject(callbackFunName, ajaxJson);
	}


	/**
	 * 微信支付异步回调
	 * 通知url必须为直接可访问的url示例：notify_url：“https://pay.weixin.qq.com/wxpay/pay.action“（微信坑不支持带参的，否则接收不到微信的流信息）
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "payNotifyUrl")
	@ResponseBody
	public JsonResult payNotifyUrl(HttpServletRequest request,
			HttpServletResponse response,String userId) throws Exception {
		BufferedReader reader = null;
		reader = request.getReader();
		String line = "";
		String xmlString = null;
		StringBuffer inputString = new StringBuffer();

		while ((line = reader.readLine()) != null) {
			inputString.append(line);
		}
		xmlString = inputString.toString();
		request.getReader().close();
		System.out.println("----接收到的数据如下：---" + xmlString);
		Map<String, String> map = new HashMap<String, String>();
		map = XMLUtil.doXMLParse(xmlString);
		if (checkSign(xmlString)) {
			String orderNumber = (String) map.get("out_trade_no");
			String[] arg = orderNumber.split("_");
			if (orderNumber != null) {
				// 调用微信查询订单接口，
				Map<String, String> orderMap = WxPayUtil.queryWeiXinOrder(orderNumber);
				if (orderMap.get("return_code") != null&& orderMap.get("return_code").equalsIgnoreCase("SUCCESS")) {
					if (orderMap.get("result_code") != null&& orderMap.get("result_code").equalsIgnoreCase("SUCCESS")) {
						if (orderMap.get("trade_state") != null&& orderMap.get("trade_state").equalsIgnoreCase("SUCCESS")) {
							System.out.println("openid ======="+orderMap.get("openid"));
							// 支付成功。。。。开始更新你的订单状态吧 根据自己的业务去修改
							TcsbOrderEntity tcsbOrderEntity = tcsbOrderService
									.findUniqueByProperty(TcsbOrderEntity.class, "orderNo",
											arg[0]);
							if("0".equals(tcsbOrderEntity.getPayStatus())){
								//订单为支付执行回调更新操作
								//获取通用券和专用券和店铺优惠折上折才是最后的金额
								Double finalMoney = tcsbOrderEntity.getTotalPrice();
								//获取优惠活动
								List<TcsbDiscountActivityEntity> tcsbDiscountActivityEntities = systemService.findByProperty(TcsbDiscountActivityEntity.class, "shopId", tcsbOrderEntity.getShopId());
								if (!tcsbDiscountActivityEntities.isEmpty()) {
									List<TcsbShopFullcutTemplateEntity> tcsbShopFullcutTemplateEntities = new ArrayList<>();
									for (TcsbDiscountActivityEntity tcsbDiscountActivityEntity : tcsbDiscountActivityEntities) {
										TcsbShopFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbShopFullcutTemplateEntity.class, tcsbDiscountActivityEntity.getFullcutTemplateId());
										tcsbShopFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
									}
									if (!tcsbDiscountActivityEntities.isEmpty()) {
										ListSorter.sort(tcsbShopFullcutTemplateEntities, "total","discount");
										boolean hasCut = false;
										for (int i = tcsbShopFullcutTemplateEntities.size()-1; i >= 0; i--) {
											if (finalMoney>=tcsbShopFullcutTemplateEntities.get(i).getTotal()) {
												finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
												tcsbOrderEntity.setUniversalCouponPrice(Double.parseDouble(tcsbShopFullcutTemplateEntities.get(i).getDiscount()+""));
												hasCut = true;
												break;
											}
										}
										if (!hasCut) {
											tcsbOrderEntity.setUniversalCouponPrice(0.0);
										}
									}else {
										tcsbOrderEntity.setUniversalCouponPrice(0.0);
									}
								}else {
									tcsbOrderEntity.setUniversalCouponPrice(0.0);
								}
								//获取专用券（userId）
								List<TcsbCouponEntity> tcsbCouponEntities = systemService.findHql("from TcsbCouponEntity where useStatus=0 and shopId = ? and userId = ? and useStatus='0' ", tcsbOrderEntity.getShopId(),orderMap.get("openid"));
								List<TcsbFullcutTemplateEntity> tcsbFullcutTemplateEntities = new ArrayList<>();
								if (!tcsbCouponEntities.isEmpty()) {
									//获取专用券中满减服务中有效的活动
									for (TcsbCouponEntity tcsbCouponEntity : tcsbCouponEntities) {
										TcsbFullcutTemplateEntity tcsbFullcutTemplateEntity = systemService.get(TcsbFullcutTemplateEntity.class, tcsbCouponEntity.getFullcutTemplateId());
										Integer userPeriod = tcsbFullcutTemplateEntity.getUsePeriod();//使用期限
										String dateUnit = tcsbFullcutTemplateEntity.getDateUnit(); //日期单位
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
										Calendar calDes = DateUtils.parseCalendar(DateUtils.date2Str(tcsbCouponEntity.getExpiryDate(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
										Calendar calSrc = DateUtils.parseCalendar(DateUtils.date2Str(new Date(),DateUtils.datetimeFormat),"yyyy-MM-dd HH:mm:ss");
										if (DateUtils.dateDiff('s', calSrc, calDes)<0) {
											tcsbFullcutTemplateEntity.setCouponId(tcsbCouponEntity.getId());
											tcsbFullcutTemplateEntities.add(tcsbFullcutTemplateEntity);
										};
									}
									
								}
								//立减
								if (!tcsbFullcutTemplateEntities.isEmpty()) {
									ListSorter.sort(tcsbFullcutTemplateEntities, "total","discount");
									boolean hasCut = false;
									outer:for (int i = tcsbFullcutTemplateEntities.size()-1; i >= 0; i--) {
										if (finalMoney>=tcsbFullcutTemplateEntities.get(i).getTotal()) {
											TcsbCouponEntity tcsbCouponEntity = systemService.get(TcsbCouponEntity.class, tcsbFullcutTemplateEntities.get(i).getCouponId());
											if ("0".equals(tcsbCouponEntity.getUseRange())) {
												finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
												tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
												tcsbCouponEntity.setUseStatus("1");
												systemService.saveOrUpdate(tcsbCouponEntity);
												hasCut = true;
												break outer;
											}if ("1".equals(tcsbCouponEntity.getUseRange())) {
												TcsbFoodEntity tcsbFoodEntity = systemService.get(TcsbFoodEntity.class, tcsbCouponEntity.getFoodId());
												List<TcsbOrderItemEntity> tcsbOrderItemEntities = systemService.findByProperty(TcsbOrderItemEntity.class, "orderId", tcsbOrderEntity.getId());
												//查找订单项中是否有该道菜
												for (TcsbOrderItemEntity tcsbOrderItemEntity : tcsbOrderItemEntities) {
													if (tcsbOrderItemEntity.getFoodId().equals(tcsbFoodEntity.getId())) {
														finalMoney = BigDecimalUtil.sub(finalMoney, Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
														tcsbOrderEntity.setSpecialCouponPrice(Double.parseDouble(tcsbFullcutTemplateEntities.get(i).getDiscount()+""));
														tcsbCouponEntity.setUseStatus("1");
														systemService.saveOrUpdate(tcsbCouponEntity);
														hasCut = true;
														break outer;
													}
												}
											}
											
										}
									}
									if (!hasCut) {
										tcsbOrderEntity.setSpecialCouponPrice(0.0);
									}
								}else {
									tcsbOrderEntity.setSpecialCouponPrice(0.0);
								}
								//更新订单信息
							    tcsbOrderEntity.setOnlinePrice(finalMoney);
								tcsbOrderEntity.setOfflinePrice(0.0);
								//改为线上支付方式
								tcsbOrderEntity.setPayMethod("0");
								tcsbOrderEntity.setPayStatus("1");
								tcsbOrderEntity.setStatus("1");
								tcsbOrderService.saveOrUpdate(tcsbOrderEntity);
								//更新多用户订单信息
								systemService.executeSql("update tcsb_user_order set pay_status ='1' where order_id = ?", arg[0]);

								TcsbPayNoticeEntity checktcsbPayNotice = systemService.findUniqueByProperty(TcsbPayNoticeEntity.class, "orderNo", arg[0]);
								if(!StringUtil.isNotEmpty(checktcsbPayNotice)){
									if(StringUtil.isNotEmpty(tcsbOrderEntity)){
										if(StringUtil.isNotEmpty(arg[0])){
											TcsbPayNoticeEntity tcsbPayNoticeEntity = new TcsbPayNoticeEntity();
											tcsbPayNoticeEntity.setDeskId(tcsbOrderEntity.getDeskId());
											tcsbPayNoticeEntity.setIsread("0");
											tcsbPayNoticeEntity.setPayMoney(tcsbOrderEntity.getOnlinePrice()+"");
											tcsbPayNoticeEntity.setOrderNo(arg[0]);
											tcsbPayNoticeEntity.setShopId(tcsbOrderEntity.getShopId());
											systemService.save(tcsbPayNoticeEntity);
										}
									}
								}
								
								TcsbOrderBigdataRecordEntity tcsbOrderBigdataRecordEntity = systemService.findUniqueByProperty(TcsbOrderBigdataRecordEntity.class,"orderno",arg[0]);
								if(!StringUtil.isNotEmpty(tcsbOrderBigdataRecordEntity)){
									TcsbOrderBigdataRecordEntity bigbatasave = new TcsbOrderBigdataRecordEntity();
									bigbatasave.setOrderno(arg[0]);
									bigbatasave.setIsExecute(1);
									systemService.save(bigbatasave);
								}
								return JsonResult.ok();
							}else{
								//订单已支付订单已支付成功
								return JsonResult.ok();
							}
							
						}
					}
				}else {
					return JsonResult.fail("");
				}
			}
			
		} 
		return JsonResult.ok();
	}

	private boolean checkSign(String xmlString) {

		Map<Object, Object> map = null;

		try {

			map = XMLUtil.doXMLParse(xmlString);

		} catch (Exception e) {
			e.printStackTrace();
		}

		String signFromAPIResponse = map.get("sign").toString();

		if (signFromAPIResponse == "" || signFromAPIResponse == null) {

			System.out.println("API返回的数据签名数据不存在，有可能被第三方篡改!!!");

			return false;

		}
		System.out.println("服务器回包里面的签名是:" + signFromAPIResponse);

		// 清掉返回数据对象里面的Sign数据（不能把这个数据也加进去进行签名），然后用签名算法进行签名

		map.put("sign", "");

		// 将API返回的数据根据用签名算法进行计算新的签名，用来跟API返回的签名进行比较

		String signForAPIResponse = getSign(map);

		if (!signForAPIResponse.equals(signFromAPIResponse)) {

			// 签名验不过，表示这个API返回的数据有可能已经被篡改了

			System.out
					.println("API返回的数据签名验证不通过，有可能被第三方篡改!!! signForAPIResponse生成的签名为"
							+ signForAPIResponse);

			return false;

		}

		System.out.println("恭喜，API返回的数据签名验证通过!!!");

		return true;

	}
	public String getSign(Map<Object, Object> map) {
		SortedMap<Object, Object> signParams = new TreeMap<Object, Object>();
		for (Map.Entry<Object, Object> stringStringEntry : map.entrySet()) {
			signParams.put(stringStringEntry.getKey(),
					stringStringEntry.getValue());
		}
		signParams.remove("sign");
		String sign = PayCommonUtil.createSign(signParams);
		return sign;
	}
	
	@RequestMapping(params = "refund")
	public void refund(HttpServletRequest request,HttpServletResponse response){
		System.out.println("++++++++++++++++++++++++++++++");
        
        
        System.out.println("==============================");
	    
	        /*PageData pd = new PageData();
	        pd = this.getPageData();*/

	        /*--------1.初始化数据参数----------*/
	        String appId= "wxfd33b1c6f460c400";
	        String secret= "708d13ef1f6aeee90697c4f47eb493d3";
	        String shh= "1433028902";
	        String key= "diandanmexiamenguotongbaoyouxian";
	        String filePath ="‪D:/ddm/apiclient_cert.pem"; //退款需要提供证书数据，所以需要根据证书路径读取证书
	        //需要退款的商户订单号，对应提交订单中的out_trade_no
	        String orderId = "201709131011113626_1505272299478";
	        String total_fee="200";  //也可以根据业务场景从数据库中获取总金额和退款金额
	        String refund_fee="100";
	        Map<String,String> result = (Map<String, String>) wxRefund(request,response,appId,secret,shh,key,orderId,total_fee,refund_fee,filePath);
	        System.out.println("++++++++++++++++++++++++++++++");
	        
	        
	        System.out.println("++++++++++++++++++++++++++++++");
	        /*
	        根据result的返回状态，处理你的业务逻辑
	        .....
	        */
		
	}

	private Object wxRefund(HttpServletRequest request, HttpServletResponse response, String appId, String secret,
			String shh, String key, String orderId, String total_fee, String refund_fee, String path) {
		Map<String, String> result = new HashMap<String, String>();
		/*PageData pd = new PageData();
		pd = this.getPageData();*/
		String refundid = UUIDGenerator.generate();
		String nonce_str = MD5.getMessageDigest(String.valueOf(new Random().nextInt(10000)).getBytes());

		/*-----  1.生成预支付订单需要的的package数据-----*/
		SortedMap<Object,Object> packageParams = new TreeMap<Object,Object>();
		packageParams.put("appid", appId);
		packageParams.put("mch_id", shh);
		packageParams.put("nonce_str", nonce_str);
		packageParams.put("op_user_id", shh);
		packageParams.put("out_trade_no", orderId);
		packageParams.put("out_refund_no", refundid);
		packageParams.put("total_fee", total_fee);
		packageParams.put("refund_fee", refund_fee);
		/*----2.根据package生成签名sign---- */
		
	/*	SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
		parameters.put("appid", ConfigUtil.APPID);
		parameters.put("mch_id", ConfigUtil.MCH_ID);
		parameters.put("nonce_str",  PayCommonUtil.CreateNoncestr());
		parameters.put("out_trade_no", orderNumber);*/
		String sign = PayCommonUtil.createSign(packageParams);
		
		//RequestHandler reqHandler = new RequestHandler(request, response);
		//reqHandler.init(appId, secret, key);
		//String sign = reqHandler.createSign(packageParams);

		/*----3.拼装需要提交到微信的数据xml---- */
		String xml = "<xml>" + "<appid>" + appId + "</appid>" + "<mch_id>" + shh + "</mch_id>" + "<nonce_str>"
				+ nonce_str + "</nonce_str>" + "<op_user_id>" + shh + "</op_user_id>" + "<out_trade_no>" + orderId
				+ "</out_trade_no>" + "<out_refund_no>" + refundid + "</out_refund_no>" + "<refund_fee>" + refund_fee
				+ "</refund_fee>" + "<total_fee>" + total_fee + "</total_fee>" + "<sign>" + sign + "</sign>" + "</xml>";
		try {
			/*----4.读取证书文件,这一段是直接从微信支付平台提供的demo中copy的，所以一般不需要修改---- */
			System.out.println(path.substring(1));
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			FileInputStream instream = new FileInputStream(path.substring(1));
			try {
				keyStore.load(instream, shh.toCharArray());
			} finally {
				instream.close();
			}
			
			// 实例化密钥库
			KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());  
			// 初始化密钥工厂
			kmf.init(keyStore, shh.toCharArray());
			
			// 创建SSLContext
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			sslContext.init(kmf.getKeyManagers(), null, new SecureRandom());
			// 获取SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();
			
			// Trust own CA and all self-signed certs
			//SSLContext sslcontext = SSLContext.custom().loadKeyMaterial(keyStore, shh.toCharArray()).build();
			// Allow TLSv1 protocol only
			/*SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" },
					null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);*/
			//CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
			String requestUrl = "https://api.mch.weixin.qq.com/secapi/pay/refund";
			URL url = new URL(requestUrl);
			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			// 设置当前实例使用的SSLSocketFactory
			conn.setSSLSocketFactory(ssf);
			conn.setDoOutput(true);
			conn.setDoInput(true);
			
			if (null != xml) { 
				OutputStream outputStream = conn.getOutputStream(); 
				// 注意编码格式 
				outputStream.write(xml.getBytes("UTF-8")); 
				outputStream.close(); 
				} 
			
			
			// 从输入流读取返回内容 
			InputStream inputStream = conn.getInputStream(); 
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8"); 
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader); 
			String str = null; 
			StringBuffer buffer = new StringBuffer(); 
			while ((str = bufferedReader.readLine()) != null) { 
			buffer.append(str); 
			} 
			// 释放资源 
			bufferedReader.close(); 
			inputStreamReader.close(); 
			inputStream.close(); 
			inputStream = null; 
			conn.disconnect(); 
			//return buffer.toString(); 
			
			
			//conn.connect();
			
			
			/*----5.发送数据到微信的退款接口---- */
			
			//HttpPost httpost = HttpClientConnectionManager.getPostMethod(url);
			//httpost.setEntity(new StringEntity(xml, "UTF-8"));
			//HttpResponse weixinResponse = httpClient.execute(httpost);
			//String jsonStr = EntityUtils.toString(weixinResponse.getEntity(), "UTF-8");
			//logger.info(jsonStr);

			//Map map = GetWxOrderno.doXMLParse(jsonStr);
			
			//String result2 =CommonUtil.httpsRequest(url, "POST", xml);
			System.out.println(buffer.toString());
			Map<String, String> map = XMLUtil.doXMLParse(buffer.toString());//解析微信返回的信息，以Map形式存储便于取值
			if ("success".equalsIgnoreCase((String) map.get("return_code"))) {
				
				result.put("returncode", "ok");
				result.put("returninfo", "退款成功");
			} else {
			
				result.put("returncode", "error");
				result.put("returninfo", "退款失败");
			}
		} catch (Exception e) {
			System.out.println(e);
			result.put("returncode", "error");
			result.put("returninfo", "退款失败");
		}
		return result;

	}
	
	
}
