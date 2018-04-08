package com.applet.yuandanactivity;

import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jackson.map.util.JSONPObject;
import org.jeecgframework.core.common.controller.BaseController;
import org.jeecgframework.core.common.model.json.AjaxJsonApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.applet.yuandanactivity.vo.NewYearVo;
import com.tcsb.food.entity.TcsbFoodEntity;
import com.tcsb.imagesmanager.entity.TcsbImagesManagerEntity;
import com.tcsb.shop.service.TcsbShopServiceI;
import com.weixin.core.entity.common.AccessToken;
import com.weixin.core.entity.common.JsapiTicket;
import com.weixin.core.util.PayCommonUtil;
import com.weixin.core.util.SignUtil;
import com.weixin.core.util.WeixinUtil;
import com.weixin.weixinjsapiticket.entity.WeixinJsapiTicketEntity;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/shareActivity")
public class ShareActivityController extends BaseController{

	@Autowired
	private TcsbShopServiceI tcsbShopService;

	public final static String sign_ticket_create_url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi";

	
	@RequestMapping(params = "getConfigInfo")
    @ResponseBody
    public JSONPObject getConfigInfo(HttpServletRequest request,String shareId,String shopId,String weixinOpenId,String unionid) {
		String configUrl = ResourceBundle.getBundle("sysConfig").getString("configUrl");
		String url = configUrl+"index.html?shopId="+shopId+"&shareId="+shareId+"&weixinOpenId="+weixinOpenId+"&unionid="+unionid;
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
    	try {
    		//AccessToken accessToken = WeixinUtil.getAccessToken(systemService, ConfigUtil.APPID, ConfigUtil.APP_SECRECT);
    		String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid 
    		//String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect 
    		AccessToken accessToken = WeixinUtil.getAccessToken();
        	JsapiTicket ticket = WeixinUtil.getTicket2( accessToken.getToken());
        	System.out.println("ticket::::::;" + JSON.toJSONString(ticket));
        	if(ticket != null){
        		long timestamp = new Date().getTime();
        		String noncestr = PayCommonUtil.CreateNoncestr();
        		StringBuilder sb = new StringBuilder("jsapi_ticket=");
        		sb.append(ticket.getTicket()).append("&noncestr=").append(noncestr)
        		.append("&timestamp=").append(timestamp).append("&url=").append(url);
        		MessageDigest md = MessageDigest.getInstance("SHA-1");
    			// 对接后的字符串进行sha1加密
    			byte[] digest = md.digest(sb.toString().getBytes());
    			String signature = SignUtil.byteToStr(digest).toLowerCase();
    	    	Map<String, String> map = new HashMap<String, String>();
    	    	map.put("jsapi_ticket", ticket.getTicket());
    	    	//map.put("appId",  ConfigUtil.APPID);
    	    	map.put("appId",  APPID);
    	    	map.put("timestamp", String.valueOf(timestamp));
    	    	map.put("nonceStr", noncestr);
    	    	map.put("signature", signature);
    	    	map.put("originalStr", sb.toString());
    	    	ajaxJsonApi.setMsg("成功");
    	    	ajaxJsonApi.setSuccess(true);
    	    	ajaxJsonApi.setObj(map);
    	    	return new JSONPObject(callbackFunName, ajaxJsonApi); 
        	}
        	ajaxJsonApi.setMsg("失败");
        	ajaxJsonApi.setSuccess(false);
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
	
	
	@RequestMapping(params = "getConfigInfo2")
    @ResponseBody
    public JSONPObject getConfigInfo2(HttpServletRequest request,String fullTemplateId,String shareUnionid,String shopId,String unionid,String discount,String total) {
		String configUrl = ResourceBundle.getBundle("sysConfig").getString("configUrl");
		String url = configUrl + "getTicket.html?fullTemplateId="+fullTemplateId+"&shareUnionid="+shareUnionid+"&unionid="+unionid+"&shopId="+shopId+"&discount="+discount+"&total="+total;
		String callbackFunName =request.getParameter("callbackparam");//得到js函数名称 	
    	AjaxJsonApi ajaxJsonApi = new AjaxJsonApi();
    	try {
    		//AccessToken accessToken = WeixinUtil.getAccessToken(systemService, ConfigUtil.APPID, ConfigUtil.APP_SECRECT);
    		String APPID = ResourceBundle.getBundle("sysConfig").getString("APPID");//服务号的appid 
    		//String APP_SECRECT = ResourceBundle.getBundle("sysConfig").getString("APP_SECRECT");//服务号的appSecrect 
    		AccessToken accessToken = WeixinUtil.getAccessToken();
        	JsapiTicket ticket = WeixinUtil.getTicket2( accessToken.getToken());
        	System.out.println("ticket::::::;" + JSON.toJSONString(ticket));
        	if(ticket != null){
        		long timestamp = new Date().getTime();
        		String noncestr = PayCommonUtil.CreateNoncestr();
        		StringBuilder sb = new StringBuilder("jsapi_ticket=");
        		sb.append(ticket.getTicket()).append("&noncestr=").append(noncestr)
        		.append("&timestamp=").append(timestamp).append("&url=").append(url);
        		MessageDigest md = MessageDigest.getInstance("SHA-1");
    			// 对接后的字符串进行sha1加密
    			byte[] digest = md.digest(sb.toString().getBytes());
    			String signature = SignUtil.byteToStr(digest).toLowerCase();
    	    	Map<String, String> map = new HashMap<String, String>();
    	    	map.put("jsapi_ticket", ticket.getTicket());
    	    	//map.put("appId",  ConfigUtil.APPID);
    	    	map.put("appId",  APPID);
    	    	map.put("timestamp", String.valueOf(timestamp));
    	    	map.put("nonceStr", noncestr);
    	    	map.put("signature", signature);
    	    	map.put("originalStr", sb.toString());
    	    	ajaxJsonApi.setMsg("成功");
    	    	ajaxJsonApi.setSuccess(true);
    	    	ajaxJsonApi.setObj(map);
    	    	return new JSONPObject(callbackFunName, ajaxJsonApi); 
        	}
        	ajaxJsonApi.setMsg("失败");
        	ajaxJsonApi.setSuccess(false);
        	
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return new JSONPObject(callbackFunName, ajaxJsonApi);
    }
	
	
        
	
	
	
	
	@RequestMapping()
	@ResponseBody
	public AjaxJsonApi getShopInfo(String shopId){
		AjaxJsonApi ajaxJson = new AjaxJsonApi();
		List<NewYearVo> newYear = new ArrayList<>();
		//查找店铺轮播图片
		List<TcsbImagesManagerEntity> tcsbImagesManagerEntities = tcsbShopService.findHql("from TcsbImagesManagerEntity where shopId = ?", shopId);
		List<Object> shopImgs = new ArrayList<>() ;
		if (!tcsbImagesManagerEntities.isEmpty()) {
			for (TcsbImagesManagerEntity tcsbImagesManagerEntity : tcsbImagesManagerEntities) {
				shopImgs.add(getAppletCkPath()+tcsbImagesManagerEntity.getfPath());
			}
		}
		
		//获取店铺菜品
		 List<TcsbFoodEntity> foodList = tcsbShopService.findByProperty(TcsbFoodEntity.class, "shopId", shopId);
		
		 
		 
		return ajaxJson;
	}
	
}
